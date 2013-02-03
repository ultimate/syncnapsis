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
package com.syncnapsis.websockets.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.syncnapsis.utils.MBeanUtil;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.Message;
import com.syncnapsis.websockets.Service;

/**
 * Basic implementation of WebSocketListener to be used for extending own WebSocketListeners
 * 
 * @author ultimate
 */
public class BaseService implements Service, InitializingBean
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger			= LoggerFactory.getLogger(getClass());

	/**
	 * The current count of connections
	 */
	protected int						numberOfConnections;

	/**
	 * The list of currently active Connections for this Service
	 */
	protected Map<String, Connection>	connections;

	/**
	 * The maximum amout of connections
	 */
	protected int						maxConnections	= -1;

	/**
	 * The TimeProvider used to access the current time
	 */
	// protected TimeProvider timeProvider;

	/**
	 * Constructs a new basic WebSocketService to be used in Connections
	 */
	public BaseService()
	{
		init();
	}

	/**
	 * internal initialization used in constructors
	 */
	protected void init()
	{
		this.connections = new TreeMap<String, Connection>();

		MBeanUtil.registerMBean(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		// nothing specific here
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#addConnection(com.syncnapsis.websockets.Connection)
	 */
	public void addConnection(Connection connection)
	{
		String subprotocol = connection.getSubprotocol();
		connection.setId(connection.getManager().generateId(subprotocol));
		synchronized(this)
		{
			this.connections.put(connection.getId(), connection);
			this.numberOfConnections++;
			this.notifyAll();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#removeConnection(com.syncnapsis.websockets.Connection)
	 */
	public void removeConnection(Connection connection)
	{
		synchronized(this)
		{
			this.connections.remove(connection.getId());
			this.numberOfConnections--;
			this.notifyAll();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#getConnections()
	 */
	@Override
	public Collection<Connection> getConnections()
	{
		return Collections.unmodifiableCollection(connections.values());
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#disconnectAll(int, java.lang.String)
	 */
	public void disconnectAll(int closeCode, String message)
	{
		// buffer them to prevent ConcurrentModificationException
		List<Connection> tmp = new ArrayList<Connection>(connections.values());
		// now really close them
		for(Connection connection : tmp)
		{
			logger.info("  closing connection: " + connection.getId());
			connection.close(closeCode, message);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#getNumberOfConnections()
	 */
	@Override
	public int getNumberOfConnections()
	{
		return this.numberOfConnections;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#getMaxConnections()
	 */
	@Override
	public int getMaxConnections()
	{
		return maxConnections;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#setMaxConnections(int)
	 */
	@Override
	public void setMaxConnections(int maxConnections)
	{
		this.maxConnections = maxConnections;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#getFreeConnections()
	 */
	@Override
	public int getFreeConnections()
	{
		int free = this.maxConnections - this.connections.size();
		if(free <= 0)
			free = 0;
		if(this.maxConnections < 0)
			free = -1;
		return free;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#broadcast(java.lang.String)
	 */
	@Override
	public void broadcast(String data)
	{
		for(String id : this.connections.keySet())
		{
			try
			{
				sendMessage(id, data);
			}
			catch(IOException e)
			{
				logger.error("could not send message to connection '" + id + "': " + e.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#broadcast(byte[], int, int)
	 */
	@Override
	public void broadcast(byte[] data, int offset, int length)
	{
		for(String id : this.connections.keySet())
		{
			try
			{
				sendMessage(id, data, offset, length);
			}
			catch(IOException e)
			{
				logger.error("could not send message to connection '" + id + "': " + e.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#sendMessage(java.lang.String, java.lang.String)
	 */
	@Override
	public void sendMessage(String id, String data) throws IOException
	{
		Connection connection = this.connections.get(id);
		if(connection != null)
		{
			if(connection.isOpen())
			{
				connection.sendMessage(data);
			}
			else
			{
				this.removeConnection(connection);
				throw new IOException("connection not connected: '" + id + "'");
			}
		}
		else
		{
			throw new IOException("unknown connection: '" + id + "'");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#sendMessage(java.lang.String, byte[], int, int)
	 */
	@Override
	public void sendMessage(String id, byte[] data, int offset, int length) throws IOException
	{
		Connection connection = this.connections.get(id);
		if(connection != null)
		{
			if(connection.isOpen())
			{
				connection.sendMessage(data, offset, length);
			}
			else
			{
				this.removeConnection(connection);
				throw new IOException("connection not connected: '" + id + "'");
			}
		}
		else
		{
			throw new IOException("unknown connection: '" + id + "'");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onOpen(com.syncnapsis.websockets.Connection)
	 */
	@Override
	public void onOpen(Connection connection)
	{
		this.addConnection(connection);
		logger.debug("connection '" + connection.getId() + "' opened");
	}

	/*
	 * j
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onClose(com.syncnapsis.websockets.Connection, int,
	 * java.lang.String)
	 */
	@Override
	public void onClose(Connection connection, int closeCode, String message)
	{
		logger.debug("connection '" + connection.getId() + "' closed: " + closeCode + (message != null ? " '" + message + "'" : ""));
		this.removeConnection(connection);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onHandshake(com.syncnapsis.websockets.Connection)
	 */
	@Override
	public void onHandshake(Connection connection)
	{
		logger.trace("handshake received");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onMessage(com.syncnapsis.websockets.Connection,
	 * java.lang.String)
	 */
	@Override
	public void onMessage(Connection connection, String data)
	{
		logger.trace("received text message: '" + data + "'");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onMessage(com.syncnapsis.websockets.Connection,
	 * byte[], int, int)
	 */
	@Override
	public void onMessage(Connection connection, byte[] data, int offset, int length)
	{
		logger.trace("received binary message: coded as String '" + new String(data, offset, length) + "'");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onMessage(com.syncnapsis.websockets.Connection,
	 * com.syncnapsis.websockets.Message)
	 */
	@Override
	public void onMessage(Connection connection, Message message)
	{
		if(connection.getProtocol().isText(message.getOpCode()))
			this.onMessage(connection, message.getDataString());
		else
			this.onMessage(connection, message.getData(), message.getOffset(), message.getLength());
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onControl(com.syncnapsis.websockets.Connection, byte,
	 * byte[], int, int)
	 */
	@Override
	public boolean onControl(Connection connection, byte controlCode, byte[] data, int offset, int length)
	{
		logger.trace("received control message: code=" + controlCode + ", coded as String '" + new String(data, offset, length) + "'");
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Service#onFrame(com.syncnapsis.websockets.Connection, byte,
	 * byte, byte[], int,
	 * int)
	 */
	@Override
	public boolean onFrame(Connection connection, byte flags, byte opcode, byte[] data, int offset, int length)
	{
		logger.trace("received control message: flags=" + flags + ", code=" + opcode + ", coded as String '" + new String(data, offset, length) + "'");
		return false;
	}
}
