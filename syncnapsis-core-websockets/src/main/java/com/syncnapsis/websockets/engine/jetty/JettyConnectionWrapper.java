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
package com.syncnapsis.websockets.engine.jetty;

import java.io.IOException;

import org.eclipse.jetty.websocket.WebSocket.Connection;
import org.eclipse.jetty.websocket.WebSocket.FrameConnection;
import com.syncnapsis.exceptions.WebSocketEngineException;
import com.syncnapsis.websockets.Message;
import com.syncnapsis.websockets.Protocol;
import com.syncnapsis.websockets.engine.BaseConnection;

/**
 * Wrapper class for mapping WebSocketConnection and WebSocketProtocol methods to a given Jetty
 * WebSocketConnection
 * 
 * @author ultimate
 */
public class JettyConnectionWrapper extends BaseConnection implements Protocol
{
	/**
	 * The given Jetty Connection or FrameConnection
	 */
	private Connection	connection;

	/**
	 * The opCode for close messages
	 */
	private Byte												closeOpCode	= null;
	/**
	 * The opCode for ping messages
	 */
	private Byte												pingOpCode	= null;
	/**
	 * The opCode for pong messages
	 */
	private Byte												pongOpCode	= null;

	/**
	 * Constructs a new JettyConnectionWrapper with the given Jetty Connection
	 * 
	 * @param connection - the connection
	 */
	public JettyConnectionWrapper(Connection connection)
	{
		super();
		this.connection = connection;
		this.setProtocol(this);
	}

	/**
	 * Internal check for FrameConnection ist Jetty FrameConnection methods are called
	 * 
	 * @param operation
	 */
	private void checkFrameConnection(String operation)
	{
		if(this.connection instanceof FrameConnection)
			throw new WebSocketEngineException("connection does not support " + operation);
	}

	/**
	 * Get the underlying Jetty Connection
	 * 
	 * @return connection
	 */
	public Connection getConnection()
	{
		return this.connection;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketConnection#sendMessage(java.lang.String)
	 */
	@Override
	public void sendMessage(String message) throws IOException
	{
		this.connection.sendMessage(message != null ? message : "");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketConnection#sendMessage(byte[], int, int)
	 */
	@Override
	public void sendMessage(byte[] data, int offset, int length) throws IOException
	{
		this.connection.sendMessage(data != null ? data : new byte[0], offset, length);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketConnection#sendMessage(com.syncnapsis.websockets.WebSocketMessage)
	 */
	@Override
	public void sendMessage(Message message) throws IOException
	{
		checkFrameConnection("sendMessage(..)");

		if(((FrameConnection) this.connection).isText(message.getOpCode()))
			this.connection.sendMessage(message.getDataString());
		else
			this.connection.sendMessage(message.getData(), message.getOffset(), message.getLength());
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketConnection#sendControl(byte, byte[], int, int)
	 */
	@Override
	public void sendControl(byte controlCode, byte[] data, int offset, int length) throws IOException
	{
		checkFrameConnection("sendControl(..)");
		((FrameConnection) this.connection).sendControl(controlCode, data, offset, length);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketConnection#sendFrame(byte, byte, byte[], int, int)
	 */
	@Override
	public void sendFrame(byte flags, byte opCode, byte[] data, int offset, int length) throws IOException
	{
		checkFrameConnection("sendFrame(..)");
		((FrameConnection) this.connection).sendFrame(flags, opCode, data, offset, length);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketConnection#isOpen()
	 */
	@Override
	public boolean isOpen()
	{
		return this.connection.isOpen();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketConnection#close()
	 */
	public void close()
	{
		this.connection.close();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketConnection#close(int, java.lang.String)
	 */
	@Override
	public void close(int closeCode, String message)
	{
		try
		{
			this.connection.close(closeCode, message);
		}
		catch(UnsupportedOperationException e)
		{
			this.connection.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketConnection#setAllowFrameFragmentation(boolean)
	 */
	@Override
	public void setAllowFrameFragmentation(boolean allowFragmentation)
	{
		checkFrameConnection("setAllowFrameFragmentation(..)");
		((FrameConnection) this.connection).setAllowFrameFragmentation(allowFragmentation);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketConnection#isAllowFrameFragmentation()
	 */
	@Override
	public boolean isAllowFrameFragmentation()
	{
		checkFrameConnection("isAllowFrameFragmentation(..)");
		return ((FrameConnection) this.connection).isAllowFrameFragmentation();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#isText(byte)
	 */
	@Override
	public boolean isText(byte opCode)
	{
		checkFrameConnection("isText(..)");
		return ((FrameConnection) this.connection).isText(opCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#isBinary(byte)
	 */
	@Override
	public boolean isBinary(byte opCode)
	{
		checkFrameConnection("isBinary(..)");
		return ((FrameConnection) this.connection).isBinary(opCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#isControl(byte)
	 */
	@Override
	public boolean isControl(byte opCode)
	{
		checkFrameConnection("isControl(..)");
		return ((FrameConnection) this.connection).isControl(opCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#isContinuation(byte)
	 */
	@Override
	public boolean isContinuation(byte opCode)
	{
		checkFrameConnection("isContinuation(..)");
		return ((FrameConnection) this.connection).isContinuation(opCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#isClose(byte)
	 */
	@Override
	public boolean isClose(byte opCode)
	{
		checkFrameConnection("isClose(..)");
		return ((FrameConnection) this.connection).isClose(opCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#isPing(byte)
	 */
	@Override
	public boolean isPing(byte opCode)
	{
		checkFrameConnection("isPing(..)");
		return ((FrameConnection) this.connection).isPing(opCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#isPong(byte)
	 */
	@Override
	public boolean isPong(byte opCode)
	{
		checkFrameConnection("isPong(..)");
		return ((FrameConnection) this.connection).isPong(opCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#textOpCode()
	 */
	@Override
	public byte textOpCode()
	{
		checkFrameConnection("textOpCode(..)");
		return ((FrameConnection) this.connection).textOpcode();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#binaryOpCode()
	 */
	@Override
	public byte binaryOpCode()
	{
		checkFrameConnection("binaryOpCode(..)");
		return ((FrameConnection) this.connection).binaryOpcode();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#continuationOpCode()
	 */
	@Override
	public byte continuationOpCode()
	{
		checkFrameConnection("continuationOpCode(..)");
		return ((FrameConnection) this.connection).continuationOpcode();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#closeOpCode()
	 */
	@Override
	public byte closeOpCode()
	{
		checkFrameConnection("closeOpCode(..)");
		if(this.closeOpCode == null)
		{
			// determine opCode
			for(byte b = 0; b < 16; b++)
			{
				if(isClose(b))
					this.closeOpCode = b;
			}
		}
		return this.closeOpCode;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#pingOpCode()
	 */
	@Override
	public byte pingOpCode()
	{
		checkFrameConnection("pingOpCode(..)");
		if(this.pingOpCode == null)
		{
			// determine opCode
			for(byte b = 0; b < 16; b++)
			{
				if(isPing(b))
					this.pingOpCode = b;
			}
		}
		return this.pingOpCode;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#pongOpCode()
	 */
	@Override
	public byte pongOpCode()
	{
		checkFrameConnection("pongOpCode(..)");
		if(this.pongOpCode == null)
		{
			// determine opCode
			for(byte b = 0; b < 16; b++)
			{
				if(isPong(b))
					this.pongOpCode = b;
			}
		}
		return this.pongOpCode;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#finMask()
	 */
	@Override
	public byte finMask()
	{
		checkFrameConnection("finMask(..)");
		return ((FrameConnection) this.connection).finMask();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#isMessageComplete(byte)
	 */
	@Override
	public boolean isMessageComplete(byte flags)
	{
		checkFrameConnection("isMessageComplete(..)");
		return ((FrameConnection) this.connection).isMessageComplete(flags);
	}
}
