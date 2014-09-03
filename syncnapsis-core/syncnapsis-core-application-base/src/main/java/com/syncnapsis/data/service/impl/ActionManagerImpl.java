/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.dao.ActionDao;
import com.syncnapsis.data.model.Action;
import com.syncnapsis.data.service.ActionManager;
import com.syncnapsis.exceptions.DeserializationException;
import com.syncnapsis.exceptions.SerializationException;
import com.syncnapsis.security.BaseApplicationManager;
import com.syncnapsis.utils.data.DefaultData;
import com.syncnapsis.utils.data.ExtendedRandom;
import com.syncnapsis.utils.serialization.Serializer;
import com.syncnapsis.websockets.service.rpc.RPCCall;

/**
 * Manager-Implementation for access to Action.
 * 
 * @author ultimate
 */
public class ActionManagerImpl extends GenericNameManagerImpl<Action, Long> implements ActionManager, InitializingBean
{
	/**
	 * The character source for generating codes
	 */
	public static final String		CODE_SOURCE	= DefaultData.STRING_ASCII_LETTERS_UPPER + DefaultData.STRING_ASCII_NUMBERS;
	/**
	 * ActionDao for database access
	 */
	protected ActionDao				actionDao;

	/**
	 * The Serializer used to deserialize the stored RPC arguments
	 */
	protected Serializer<String>	serializer;
	/**
	 * The BaseApplicationManager
	 */
	protected BaseApplicationManager		securityManager;
	/**
	 * The extended random number generator for creating codes
	 */
	protected final ExtendedRandom	random		= new ExtendedRandom();

	/**
	 * Standard Constructor
	 * 
	 * @param actionDao - ActionDao for database access
	 */
	public ActionManagerImpl(ActionDao actionDao)
	{
		super(actionDao);
		this.actionDao = actionDao;
	}

	/**
	 * The Serializer used to deserialize the stored RPC arguments
	 * 
	 * @return serializer
	 */
	public Serializer<String> getSerializer()
	{
		return serializer;
	}

	/**
	 * The Serializer used to deserialize the stored RPC arguments
	 * 
	 * @param serializer - the Serializer
	 */
	public void setSerializer(Serializer<String> serializer)
	{
		this.serializer = serializer;
	}

	/**
	 * The BaseApplicationManager
	 * 
	 * @return securityManager
	 */
	public BaseApplicationManager getSecurityManager()
	{
		return securityManager;
	}

	/**
	 * The BaseApplicationManager
	 * 
	 * @param securityManager - the SecurityManager
	 */
	public void setSecurityManager(BaseApplicationManager securityManager)
	{
		this.securityManager = securityManager;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(serializer, "serializer must not be null!");
		Assert.notNull(securityManager, "securityManager must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ActionManager#getByCode(java.lang.String)
	 */
	@Override
	public Action getByCode(String code)
	{
		return this.getByName(code);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ActionManager#getRPCCall(java.lang.String)
	 */
	@Override
	public RPCCall getRPCCall(String code)
	{
		Action action = this.getByCode(code);
		Date now = new Date(securityManager.getTimeProvider().get());
		if(action != null)
		{
			if(action.getUses() < action.getMaxUses() && isValid(action, now))

			{
				action.setUses(action.getUses() + 1);
				if(action.getUses() == action.getMaxUses())
					this.remove(action);
				else
					action = this.save(action);

				String object = action.getRPCCall().getObject();
				String method = action.getRPCCall().getMethod();
				Object[] args = null;
				try
				{
					args = serializer.deserialize(action.getRPCCall().getArgs(), new Object[0], (Object[]) null);
				}
				catch(DeserializationException e)
				{
					logger.error("could not deserialize RPCCall arguments: " + e.getMessage());
					e.printStackTrace();
				}
				return new RPCCall(object, method, args);
			}
			else
			{
				this.remove(action);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.ActionManager#createAction(com.syncnapsis.websockets.service.
	 * rpc.RPCCall, int, java.util.Date, java.util.Date)
	 */
	@Override
	public Action createAction(RPCCall rpcCall, int maxUses, Date validFrom, Date validUntil)
	{
		com.syncnapsis.data.model.help.RPCCall rpcCall2 = new com.syncnapsis.data.model.help.RPCCall();
		rpcCall2.setObject(rpcCall.getObject());
		rpcCall2.setMethod(rpcCall.getMethod());
		try
		{
			rpcCall2.setArgs(serializer.serialize(rpcCall.getArgs(), (Object[]) null));
		}
		catch(SerializationException e)
		{
			logger.error("could not serialize RPCCall arguments: " + e.getMessage());
			e.printStackTrace();
		}

		Action action = new Action();
		action.setRPCCall(rpcCall2);
		action.setMaxUses(maxUses);
		action.setUses(0);
		action.setCode(generateCode());
		action.setValidFrom(validFrom);
		action.setValidUntil(validUntil);

		return save(action);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ActionManager#generateCode()
	 */
	@Override
	public String generateCode()
	{
		String code;
		Action action;

		do
		{
			code = random.nextString(ApplicationBaseConstants.PARAM_ACTION_CODE_LENGTH.asInt(), CODE_SOURCE);
			action = getByCode(code);
		} while(action != null);

		return code;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ActionManager#isValid(com.syncnapsis.data.model.Action,
	 * java.util.Date)
	 */
	@Override
	public boolean isValid(Action action, Date refDate)
	{
		if(action.getValidFrom() != null && action.getValidFrom().after(refDate))
			return false;
		if(action.getValidUntil() != null && action.getValidUntil().before(refDate))
			return false;
		return true;
	}

}
