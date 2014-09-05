/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
package com.syncnapsis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.syncnapsis.security.SecurityManager;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.security.annotations.Authority;
import com.syncnapsis.providers.ConnectionProvider;
import com.syncnapsis.utils.ApplicationContextUtil;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.service.rpc.RPCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestServer
{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<Connection, PingThread>	pingThreads	= new HashMap<Connection, PingThread>();
	
	private SecurityManager securityManager;	

	public SecurityManager getSecurityManager()
	{
		return securityManager;
	}

	public void setSecurityManager(SecurityManager securityManager)
	{
		this.securityManager = securityManager;
	}

	@Accessible(defaultAccessible = false, accessible = {@Authority(name="admin")})
	public void startPinging()
	{
		Connection connection = ApplicationContextUtil.getBean(ConnectionProvider.class).get();
		logger.info(connection.getId());
		RPCService rpcService = ApplicationContextUtil.getBean(RPCService.class);

		TestClient client = (TestClient) rpcService.getClientInstance("client", connection);

		PingThread pingThread = new PingThread(client, connection);
		pingThread.start();
		
		pingThreads.put(connection, pingThread);
	}

	@Accessible(defaultAccessible = false, accessible = {@Authority(name="admin")})
	public void stopPinging()
	{
		Connection connection = ApplicationContextUtil.getBean(ConnectionProvider.class).get();
		logger.info(connection.getId());
		PingThread pingThread = pingThreads.get(connection);
		pingThread.running = false;
		try
		{
			pingThread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	@Accessible(defaultAccessible = true, notAccessible = {@Authority(name="demo")})
	public void ping(String message)
	{
		print("ping: " + message);
	}
	
	public void post(TestEntity entity)
	{
		print("post: " + entity == null ? null : entity.toString());
	}
	
	public void setAuthorities(Object[] authorities)
	{
		logger.debug("setting authorities: " + Arrays.asList(authorities));
		securityManager.getAuthorityProvider().set(authorities);	
	}
	
	private void print(String message)
	{
		Connection connection = ApplicationContextUtil.getBean(ConnectionProvider.class).get();
		logger.info(connection.getId() + ": " + message);		
	}

	private class PingThread extends Thread
	{
		private boolean		running	= true;
		private TestClient	client;
		private Connection connection;

		public PingThread(TestClient client, Connection connection)
		{
			this.client = client;
			this.connection = connection;
		}

		@Override
		public void run()
		{
			while(running)
			{
				logger.debug("pinging: " + connection.getId());
				client.ping("this is a ping");
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
