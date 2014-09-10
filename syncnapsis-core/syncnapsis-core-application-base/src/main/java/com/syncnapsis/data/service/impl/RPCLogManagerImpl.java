/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.data.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.data.dao.RPCLogDao;
import com.syncnapsis.data.model.RPCLog;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.base.Model;
import com.syncnapsis.data.service.RPCLogManager;
import com.syncnapsis.exceptions.SerializationException;
import com.syncnapsis.security.annotations.LogFilter;
import com.syncnapsis.utils.ReflectionsUtil;
import com.syncnapsis.utils.ServletUtil;
import com.syncnapsis.utils.serialization.Serializer;
import com.syncnapsis.websockets.service.rpc.RPCCall;

/**
 * Manager-Implementation for access to RPCLog.
 * 
 * @author ultimate
 */
public class RPCLogManagerImpl extends GenericManagerImpl<RPCLog, Long> implements RPCLogManager, InitializingBean
{
	/**
	 * The additional RPC-Logger
	 */
	protected transient final Logger rpcLogger = LoggerFactory.getLogger("rpclogger");
	
	/**
	 * Value for the User-Agent session attribute if value is null
	 */
	public static final String				USER_AGENT_NULL = "unknown";

	/**
	 * The DateFormat used for RPC-Logging
	 */
	protected ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>() {
		/*
		 * (non-Javadoc)
		 * @see java.lang.ThreadLocal#initialValue()
		 */
		@Override
		protected DateFormat initialValue()
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		}
	};
	
	/**
	 * RPCLogDao for database access
	 */
	protected RPCLogDao				rpcLogDao;

	/**
	 * The Serializer used to (de)-serialize the rpc-information
	 */
	protected Serializer<String>	serializer;

	/**
	 * Standard Constructor
	 * 
	 * @param rpcLogDao - RPCLogDao for database access
	 */
	public RPCLogManagerImpl(RPCLogDao rpcLogDao)
	{
		super(rpcLogDao);
		this.rpcLogDao = rpcLogDao;
	}

	/**
	 * The Serializer used to (de)-serialize the rpc-information
	 * 
	 * @return serializer
	 */
	public Serializer<String> getSerializer()
	{
		return serializer;
	}

	/**
	 * The Serializer used to (de)-serialize the rpc-information
	 * 
	 * @param serializer - the Serializer
	 */
	public void setSerializer(Serializer<String> serializer)
	{
		this.serializer = serializer;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(serializer, "serializer must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.RPCLogManager#log(com.syncnapsis.websockets.service.rpc.RPCCall, java.lang.Object, java.util.Date, com.syncnapsis.data.model.User, javax.servlet.http.HttpSession, java.lang.Object[])
	 */
	@Override
	public RPCLog log(RPCCall rpcCall, Object result, Date executionDate, User user, HttpSession session, Object... authorities)
	{
		String resultString;
		try
		{
			if(result == null)
				resultString = "null";
			else if(result == Void.TYPE)
				resultString = "void";
			else
				resultString = serializer.serialize(result, authorities);
		}
		catch(SerializationException e)
		{
			logger.error("Could not serialize RPC result: " + e.getMessage());
			resultString = e.getClass().getName() + ": " + e.getMessage();
		}
		return this.log(rpcCall, resultString, false, executionDate, user, session, authorities);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.RPCLogManager#log(com.syncnapsis.websockets.service.rpc.RPCCall, java.lang.Throwable, java.util.Date, com.syncnapsis.data.model.User, javax.servlet.http.HttpSession, java.lang.Object[])
	 */
	@Override
	public RPCLog log(RPCCall rpcCall, Throwable throwable, Date executionDate, User user, HttpSession session, Object... authorities)
	{
		String resultString;
		if(throwable != null)
			resultString = throwable.getClass().getName() + ": " + throwable.getMessage();
		else
			resultString = "null";
	
		RPCLog log = this.log(rpcCall, resultString, true, executionDate, user, session, authorities);
		
		rpcLogger.warn("Exception occured when doing RPC", throwable);
		
		return log;
	}

	/**
	 * Summary function for log(..., Object, ...) and log(..., Exception, ...) which handles the
	 * forwarded (partially already serialized) objects.
	 * 
	 * @see RPCLogManager#log(RPCCall, Object, User, HttpSession, Object...)
	 * @see RPCLogManager#log(RPCCall, Exception, User, HttpSession, Object...)
	 * @param rpcCall - the RPCCall performed
	 * @param result - the result or exception returned serialized as a String
	 * @param exceptionThrown - did the RPCCall throw an Exception
	 * @param executionDate - the execution date
	 * @param user - the User that performed the RPCCall
	 * @param session - the session in which the RPCCall was executed
	 * @param authorities - the authorities used to perform the RPCCall
	 */
	protected RPCLog log(RPCCall rpcCall, String result, boolean exceptionThrown, Date executionDate, User user, HttpSession session, Object... authorities)
	{
		com.syncnapsis.data.model.help.RPCCall call = new com.syncnapsis.data.model.help.RPCCall();
		call.setObject(rpcCall.getObject());
		call.setMethod(rpcCall.getMethod());
		try
		{
			// filter (serialized) arguments (e.g. passwords)
			if(rpcCall.getInvocationInfo() != null && rpcCall.getInvocationInfo().getMethod() != null)
			{
				LogFilter filter = ReflectionsUtil.getAnnotation(rpcCall.getInvocationInfo().getMethod(), LogFilter.class);
				if(filter != null)
				{
					for(int filteredArg: filter.filteredArgs())
					{
						rpcCall.getArgs()[filteredArg] = "****"; 
					}
				}
			}

			call.setArgs(serializer.serialize(rpcCall.getArgs(), authorities));
		}
		catch(SerializationException e)
		{
			logger.error("Could not serialize RPC args: " + e.getMessage());
			call.setArgs(e.getClass().getName() + ": " + e.getMessage());
		}
		
		String resultS = result;
		if(resultS.length() > Model.LENGTH_TEXT)
		{
			resultS = resultS.substring(0, Model.LENGTH_TEXT - 5) + " ...";
			if(resultS.startsWith("{"))
				resultS +=  "}";
			else if(resultS.startsWith("["))
				resultS +=  "]";
		}
		
		RPCLog log = new RPCLog();
		log.setExecutionDate(executionDate);
		log.setRemoteAddr(ServletUtil.getRemoteAddr(session));
		log.setResult(resultS);
		log.setRPCCall(call);
		log.setUser(user);
		log.setUserAgent(ServletUtil.getUserAgent(session));
		if(log.getUserAgent() == null)
			log.setUserAgent(USER_AGENT_NULL);
		
		if(rpcLogger.isInfoEnabled())
		{
			String d = dateFormat.get().format(executionDate);
			rpcLogger.info(d + "  " + log.getRemoteAddr() + " -> " + call.getObject() + "." + call.getMethod() + "(" + call.getArgs() + ")" + (exceptionThrown ? " threw " : " returned ") + log.getResult());
		}
		
		return save(log);
	}
}
