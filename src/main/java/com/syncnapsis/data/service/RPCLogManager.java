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
package com.syncnapsis.data.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.syncnapsis.data.model.RPCLog;
import com.syncnapsis.data.model.User;
import com.syncnapsis.websockets.service.rpc.RPCCall;

/**
 * Manager-Interface for access to RPCLog.
 * 
 * @author ultimate
 */
public interface RPCLogManager extends GenericManager<RPCLog, Long>
{
	/**
	 * Log an RPCCall performed by the client in the given session with the result it returned.
	 * 
	 * @param rpcCall - the RPCCall performed
	 * @param result - the result returned
	 * @param executionDate - the execution date
	 * @param user - the User that performed the RPCCall
	 * @param session - the session in which the RPCCall was executed
	 * @param authorities - the authorities used to perform the RPCCall
	 * @return the RPCLog created and stored in the database
	 */
	public RPCLog log(RPCCall rpcCall, Object result, Date executionDate, User user, HttpSession session, Object... authorities);

	/**
	 * Log an RPCCall performed by the client in the given session with the exception it threw.
	 * 
	 * @param rpcCall - the RPCCall performed
	 * @param exception - the Exception thrown
	 * @param executionDate - the execution date
	 * @param user - the User that performed the RPCCall
	 * @param session - the session in which the RPCCall was executed
	 * @param authorities - the authorities used to perform the RPCCall
	 * @return the RPCLog created and stored in the database
	 */
	public RPCLog log(RPCCall rpcCall, Exception exception, Date executionDate, User user, HttpSession session, Object... authorities);
}
