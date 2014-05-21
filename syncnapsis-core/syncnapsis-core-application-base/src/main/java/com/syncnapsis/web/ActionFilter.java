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
package com.syncnapsis.web;

import java.lang.reflect.InvocationTargetException;

import org.springframework.util.Assert;

import com.syncnapsis.data.model.Action;
import com.syncnapsis.data.service.ActionManager;
import com.syncnapsis.websockets.service.rpc.RPCCall;
import com.syncnapsis.websockets.service.rpc.RPCFilter;

/**
 * Extension of {@link RPCFilter} obtaining the required RPCCall from the {@link Action} associated
 * with the given code via an {@link ActionManager}
 * 
 * @author ultimate
 */
public class ActionFilter extends RPCFilter
{
	/**
	 * The ActionMananager
	 */
	protected ActionManager	actionManager;

	/**
	 * The ActionMananager
	 * 
	 * @return actionManager
	 */
	public ActionManager getActionManager()
	{
		return actionManager;
	}

	/**
	 * The ActionMananager
	 * 
	 * @param actionManager - the ActionManager
	 */
	public void setActionManager(ActionManager actionManager)
	{
		this.actionManager = actionManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.rpc.RPCFilter#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(actionManager, "actionManager must not be null");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.rpc.RPCFilter#getRPCCall(java.lang.String)
	 */
	@Override
	protected RPCCall getRPCCall(String code)
	{
		return actionManager.getRPCCall(code);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.rpc.RPCFilter#doRPC(com.syncnapsis.websockets.service.rpc
	 * .RPCCall)
	 */
	@Override
	protected Object doRPC(RPCCall call)
	{
		Object result;
		try
		{
			result = super.doRPC(call);
		}
		catch(IllegalArgumentException e)
		{
			logger.error("exception doing RPC: " + e.getMessage());
			result = "exception doing RPC: " + e.getMessage();
		}
		catch(IllegalAccessException e)
		{
			logger.error("exception doing RPC: " + e.getMessage());
			result = "exception doing RPC: " + e.getMessage();
		}
		catch(InvocationTargetException e)
		{
			logger.error("exception doing RPC: " + e.getCause().getMessage());
			result = "exception doing RPC: " + e.getCause().getMessage();
		}
		// TODO localize
		return result;
	}
}
