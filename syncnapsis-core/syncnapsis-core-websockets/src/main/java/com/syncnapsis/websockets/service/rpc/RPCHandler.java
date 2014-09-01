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

import java.lang.reflect.InvocationTargetException;

import com.syncnapsis.websockets.Connection;

/**
 * Interface for Handlers that actually do the RPC for the given method on the given object. The
 * handler is responsible for obtaining the method and the target entity and finally to do the
 * method call.
 * 
 * @author ultimate
 */
public interface RPCHandler
{
	/**
	 * Do the method call with the given arguments.
	 * 
	 * @param call - the RPCCall
	 * @param authorities - the authorities used for transforming the input arguments
	 * 
	 * @return the resut of the RPC
	 */
	public Object doRPC(RPCCall call, Object... authorities) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

	/**
	 * Get a virtual Client-instance for the given target name and the Client represented by the
	 * Connection.
	 * For example a Proxy may be created that handles the method calls an creates the RPCMessage to
	 * be handled by this Service.
	 * 
	 * @param objectName - the name of the Object to get the Client-Instance for
	 * @param connection - the Connection representing the Client
	 * @return the Client-Instance
	 */
	public Object getClientInstance(String objectName, Connection connection);
}
