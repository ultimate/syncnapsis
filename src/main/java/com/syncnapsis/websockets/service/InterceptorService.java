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
package com.syncnapsis.websockets.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.exceptions.WebSocketServiceException;
import com.syncnapsis.utils.interception.Interceptor;
import com.syncnapsis.utils.reflections.Invocation;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.Message;
import com.syncnapsis.websockets.Service;

/**
 * Service enabling interception of events incoming from the client.<br>
 * The call of the "on...()"-method will be wrapped into an {@link Invocation} and forwarded to
 * {@link Interceptor#intercept(Invocation)}.<br>
 * The following methods will be intercepted:<br>
 * <ul>
 * <li>{@link Service#broadcast(String)}</li>
 * <li>{@link Service#broadcast(byte[], int, int)}</li>
 * <li>{@link Service#sendMessage(String, String)}</li>
 * <li>{@link Service#sendMessage(String, byte[], int, int)}</li>
 * <li>{@link Service#onOpen(Connection)}</li>
 * <li>{@link Service#onClose(Connection, int, String)}</li>
 * <li>{@link Service#onHandshake(Connection)}</li>
 * <li>{@link Service#onMessage(Connection, String)}</li>
 * <li>{@link Service#onMessage(Connection, byte[], int, int)}</li>
 * <li>{@link Service#onMessage(Connection, Message)}</li>
 * <li>{@link Service#onControl(Connection, byte, byte[], int, int)}</li>
 * <li>{@link Service#onFrame(Connection, byte, byte, byte[], int, int)}</li>
 * </ul>
 * The following methods will NOT be intercepted:<br>
 * <ul>
 * <li>{@link Service#addConnection(Connection)}</li>
 * <li>{@link Service#removeConnection(Connection)}</li>
 * <li>{@link Service#disconnectAll(int, String)}</li>
 * <li>{@link Service#getConnections()}</li>
 * <li>{@link Service#getNumberOfConnections()}</li>
 * <li>{@link Service#setMaxConnections(int)}</li>
 * <li>{@link Service#getMaxConnections()}</li>
 * <li>{@link Service#getFreeConnections()}</li>
 * </ul>
 * 
 * @author ultimate
 */
public abstract class InterceptorService implements Service, Interceptor, InitializingBean
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());
	
	/**
	 * The Service to intercept and to forward the method calls to
	 */
	protected Service					interceptedService;

	/**
	 * The Service to intercept and to forward the method calls to
	 * 
	 * @return interceptedService
	 */
	public Service getInterceptedService()
	{
		return interceptedService;
	}

	/**
	 * The Service to intercept and to forward the method calls to
	 * 
	 * @param interceptedService - the Service
	 */
	public void setInterceptedService(Service interceptedService)
	{
		this.interceptedService = interceptedService;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(interceptedService, "interceptedService must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#broadcast(java.lang.String)
	 */
	@Override
	public void broadcast(final String data)
	{
		intercept(new Invocation<Object>() {
			@Override
			public Object invoke()
			{
				interceptedService.broadcast(data);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#broadcast(byte[], int, int)
	 */
	@Override
	public void broadcast(final byte[] data, final int offset, final int length)
	{
		intercept(new Invocation<Object>() {
			@Override
			public Object invoke()
			{
				interceptedService.broadcast(data, offset, length);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#sendMessage(java.lang.String, java.lang.String)
	 */
	@Override
	public void sendMessage(final String id, final String data) throws WebSocketServiceException
	{
		intercept(new Invocation<Object>() {
			@Override
			public Object invoke()
			{
				interceptedService.sendMessage(id, data);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#sendMessage(java.lang.String, byte[], int, int)
	 */
	@Override
	public void sendMessage(final String id, final byte[] data, final int offset, final int length) throws WebSocketServiceException
	{
		intercept(new Invocation<Object>() {
			@Override
			public Object invoke()
			{
				interceptedService.sendMessage(id, data, offset, length);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onOpen(com.syncnapsis.websockets.Connection)
	 */
	@Override
	public void onOpen(final Connection connection)
	{
		intercept(new Invocation<Object>() {
			@Override
			public Object invoke()
			{
				interceptedService.onOpen(connection);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onClose(com.syncnapsis.websockets.Connection, int,
	 * java.lang.String)
	 */
	@Override
	public void onClose(final Connection connection, final int closeCode, final String message)
	{
		intercept(new Invocation<Object>() {
			@Override
			public Object invoke()
			{
				interceptedService.onClose(connection, closeCode, message);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onHandshake(com.syncnapsis.websockets.Connection)
	 */
	@Override
	public void onHandshake(final Connection connection)
	{
		intercept(new Invocation<Object>() {
			@Override
			public Object invoke()
			{
				interceptedService.onHandshake(connection);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onMessage(com.syncnapsis.websockets.Connection,
	 * java.lang.String)
	 */
	@Override
	public void onMessage(final Connection connection, final String data)
	{
		intercept(new Invocation<Object>() {
			@Override
			public Object invoke()
			{
				interceptedService.onMessage(connection, data);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onMessage(com.syncnapsis.websockets.Connection,
	 * byte[], int, int)
	 */
	@Override
	public void onMessage(final Connection connection, final byte[] data, final int offset, final int length)
	{
		intercept(new Invocation<Object>() {
			@Override
			public Object invoke()
			{
				interceptedService.onMessage(connection, data, offset, length);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onMessage(com.syncnapsis.websockets.Connection,
	 * com.syncnapsis.websockets.Message)
	 */
	@Override
	public void onMessage(final Connection connection, final Message message)
	{
		intercept(new Invocation<Object>() {
			@Override
			public Object invoke()
			{
				interceptedService.onMessage(connection, message);
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onControl(com.syncnapsis.websockets.Connection, byte,
	 * byte[], int, int)
	 */
	@Override
	public boolean onControl(final Connection connection, final byte controlCode, final byte[] data, final int offset, final int length)
	{
		return intercept(new Invocation<Boolean>() {
			@Override
			public Boolean invoke()
			{
				return interceptedService.onControl(connection, controlCode, data, offset, length);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onFrame(com.syncnapsis.websockets.Connection, byte,
	 * byte, byte[], int, int)
	 */
	@Override
	public boolean onFrame(final Connection connection, final byte flags, final byte opcode, final byte[] data, final int offset, final int length)
	{
		return intercept(new Invocation<Boolean>() {
			@Override
			public Boolean invoke()
			{
				return interceptedService.onFrame(connection, flags, opcode, data, offset, length);
			}
		});
	}

	// NOT INTERCEPTED

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#addConnection(com.syncnapsis.websockets.Connection)
	 */
	@Override
	public void addConnection(Connection connection)
	{
		interceptedService.addConnection(connection);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#removeConnection(com.syncnapsis.websockets.Connection)
	 */
	@Override
	public void removeConnection(Connection connection)
	{
		interceptedService.removeConnection(connection);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#disconnectAll(int, java.lang.String)
	 */
	@Override
	public void disconnectAll(int closeCode, String message)
	{
		interceptedService.disconnectAll(closeCode, message);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#getConnections()
	 */
	@Override
	public Collection<Connection> getConnections()
	{
		return interceptedService.getConnections();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#getNumberOfConnections()
	 */
	@Override
	public int getNumberOfConnections()
	{
		return interceptedService.getNumberOfConnections();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#setMaxConnections(int)
	 */
	@Override
	public void setMaxConnections(int maxConnections)
	{
		interceptedService.setMaxConnections(maxConnections);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#getMaxConnections()
	 */
	@Override
	public int getMaxConnections()
	{
		return interceptedService.getMaxConnections();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#getFreeConnections()
	 */
	@Override
	public int getFreeConnections()
	{
		return interceptedService.getFreeConnections();
	}
}
