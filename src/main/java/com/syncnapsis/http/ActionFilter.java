package com.syncnapsis.http;

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
}
