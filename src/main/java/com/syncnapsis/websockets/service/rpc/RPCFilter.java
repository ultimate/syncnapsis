/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.websockets.service.rpc;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.exceptions.SerializationException;
import com.syncnapsis.providers.AuthorityProvider;
import com.syncnapsis.security.SecurityManager;
import com.syncnapsis.utils.serialization.Serializer;
import com.syncnapsis.websockets.engine.FilterEngine;

/**
 * Filter allowing mapping of RPCCalls to URLs.<br>
 * When a specific URL is called, the associated RPCCall will be loaded and executed via the
 * RPCHandler.
 * 
 * @author ultimate
 */
public abstract class RPCFilter extends FilterEngine implements InitializingBean
{
	/**
	 * The name for the code-parameter
	 */
	public static final String			PARAM_CODE	= "code";

	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger		= LoggerFactory.getLogger(getClass());
	/**
	 * The FilterConfig object
	 */
	protected FilterConfig				filterConfig;

	/**
	 * The Serializer used to (de)-serialize messages.
	 */
	protected Serializer<String>		serializer;
	/**
	 * The RPCHandler handling the RPCCall associated with the URL
	 */
	protected RPCHandler				rpcHandler;

	/**
	 * The Serializer used to (de)-serialize messages.
	 * 
	 * @return serializer
	 */
	public Serializer<String> getSerializer()
	{
		return serializer;
	}

	/**
	 * The Serializer used to (de)-serialize messages.
	 * 
	 * @param serializer - the Serializer
	 */
	public void setSerializer(Serializer<String> serializer)
	{
		this.serializer = serializer;
	}

	/**
	 * The RPCHandler handling the RPCCall associated with the URL
	 * 
	 * @return rpcHandler
	 */
	public RPCHandler getRpcHandler()
	{
		return rpcHandler;
	}

	/**
	 * The RPCHandler handling the RPCCall associated with the URL
	 * 
	 * @param rpcHandler - the RPCHandler
	 */
	public void setRpcHandler(RPCHandler rpcHandler)
	{
		this.rpcHandler = rpcHandler;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(serializer, "serializer must not be null");
		Assert.notNull(rpcHandler, "rpcHandler must not be null");
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.filterConfig = filterConfig;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy()
	{
		// nothing
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		if(!(request instanceof HttpServletRequest))
			throw new ServletException("Can only handle HttpServletRequests!");
		if(!(response instanceof HttpServletResponse))
			throw new ServletException("Can only handle HttpServletResponses!");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		if(!req.getMethod().equals("POST") && !req.getMethod().equals("GET"))
		{
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return;
		}

		String code = req.getParameter(PARAM_CODE);

		logger.debug("Handling RPC for code '" + code + "'");

		RPCCall call = getRPCCall(code);

		if(call != null)
		{
			Object result = rpcHandler.doRPC(call, getAuthorities());

			if(result != null)
			{
				try
				{
					resp.getOutputStream().write(serializer.serialize(result, getAuthorities()).getBytes());
				}
				catch(SerializationException e)
				{
					throw new IOException("SerializationException", e);
				}
			}

			resp.setStatus(HttpServletResponse.SC_OK);
		}
		else
		{
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	/**
	 * Get the RPCCall associated with the current URL
	 * 
	 * @param url - the URL to get the associated RPCCall for
	 * @return the RPCCall to invoke
	 */
	protected abstract RPCCall getRPCCall(String code);

	/**
	 * Get the authorities provided by the AuthorityProvider
	 * 
	 * @see RPCService#getAuthorityProvider()
	 * @return the authorities
	 */
	protected Object[] getAuthorities()
	{
		AuthorityProvider authorityProvider = getAuthorityProvider();
		return authorityProvider == null ? new Object[0] : authorityProvider.get();
	}

	/**
	 * The Provider used to determine the authorities for (de)-serialization.
	 * 
	 * @see SecurityManager#getAuthorityProvider()
	 * @return authorityProvider
	 */
	protected AuthorityProvider getAuthorityProvider()
	{
		if(serializer.getMapper().getSecurityManager() != null)
			return serializer.getMapper().getSecurityManager().getAuthorityProvider();
		return null;
	}
}
