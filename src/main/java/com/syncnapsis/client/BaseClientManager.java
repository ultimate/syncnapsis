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
package com.syncnapsis.client;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.providers.ConnectionProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.service.rpc.RPCService;

/**
 * Interface representing the client and server UIManager functions
 * 
 * @author ultimate
 */
public class BaseClientManager
{
	/**
	 * The Logger-Instance
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(BaseClientManager.class);

	/**
	 * The SecurityManager (BaseGameManager) for accessing providers
	 */
	protected BaseGameManager			securityManager;

	/**
	 * The ConnectionProvider for accessing the current connection
	 */
	protected ConnectionProvider		connectionProvider;

	/**
	 * The RPCService used to obtain the client instance
	 */
	protected RPCService				rpcService;

	/**
	 * The name for the instance of this client manager
	 */
	protected String					instanceName;

	/**
	 * Construct a new BaseClientManager with the instance name
	 * 
	 * @param instanceName - The name for the instance of this client manager
	 */
	public BaseClientManager(String instanceName)
	{
		this.instanceName = instanceName;
	}

	/**
	 * The SecurityManager (BaseGameManager) for accessing the session locale
	 * 
	 * @return securityManager
	 */
	public BaseGameManager getSecurityManager()
	{
		return securityManager;
	}

	/**
	 * The SecurityManager (BaseGameManager) for accessing the session locale
	 * 
	 * @param securityManager - the SecurityManager
	 */
	public void setSecurityManager(BaseGameManager securityManager)
	{
		this.securityManager = securityManager;
	}

	/**
	 * The ConnectionProvider for accessing the current connection
	 * 
	 * @return connectionProvider
	 */
	public ConnectionProvider getConnectionProvider()
	{
		return connectionProvider;
	}

	/**
	 * The ConnectionProvider for accessing the current connection
	 * 
	 * @param connectionProvider - the ConnectionProvider
	 */
	public void setConnectionProvider(ConnectionProvider connectionProvider)
	{
		this.connectionProvider = connectionProvider;
	}

	/**
	 * The RPCService used to obtain the client instance
	 * 
	 * @return rpcService
	 */
	public RPCService getRpcService()
	{
		return rpcService;
	}

	/**
	 * The RPCService used to obtain the client instance
	 * 
	 * @param rpcService - the RPCService
	 */
	public void setRpcService(RPCService rpcService)
	{
		this.rpcService = rpcService;
	}

	/**
	 * The name for the instance of this client manager
	 * 
	 * @return the instance name
	 */
	protected String getInstanceName()
	{
		return this.instanceName;
	}

	/**
	 * Forwarding to {@link RPCService#getClientInstance(String, Connection)}
	 * 
	 * @param objectName - the name of the object to get the client-instance for
	 * @param connection - the connection representing the client
	 * @return the client-instance
	 */
	protected Object getClientInstance(String objectName, Connection connection)
	{
		return rpcService.getClientInstance(objectName, connection);
	}

	/**
	 * Forwarding to {@link RPCService#getConnections()}
	 * 
	 * @return the list of connections
	 */
	protected Collection<Connection> getConnections()
	{
		return rpcService.getConnections();
	}
}
