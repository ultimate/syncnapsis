package com.syncnapsis.data.service;

import com.syncnapsis.data.model.Action;
import com.syncnapsis.websockets.service.rpc.RPCCall;

/**
 * Manager-Interface for access to Action.
 * 
 * @author ultimate
 */
public interface ActionManager extends GenericNameManager<Action, Long>
{
	/**
	 * Get an action by it's code
	 * 
	 * @param code - the action's code
	 * @return the action
	 */
	public Action getByCode(String code);

	/**
	 * Get the RPCCall of an action by it's code
	 * 
	 * @param code - the action's code
	 * @return the RPCCall
	 */
	public RPCCall getRPCCall(String code);
}
