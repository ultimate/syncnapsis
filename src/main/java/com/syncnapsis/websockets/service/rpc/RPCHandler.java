package com.syncnapsis.websockets.service.rpc;

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
	 */
	public Object doRPC(RPCCall call, Object... authorities);

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
