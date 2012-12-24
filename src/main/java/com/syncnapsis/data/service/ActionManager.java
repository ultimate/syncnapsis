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
