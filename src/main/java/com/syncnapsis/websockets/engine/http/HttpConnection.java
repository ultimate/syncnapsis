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
package com.syncnapsis.websockets.engine.http;

import java.io.IOException;
import java.util.LinkedList;

import com.syncnapsis.websockets.Message;
import com.syncnapsis.websockets.Protocol;
import com.syncnapsis.websockets.engine.BaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the WebSocketConnection Interface for the HTTP fallback via
 * HttpWebSocketServlet
 * 
 * @author ultimate
 */
public class HttpConnection extends BaseConnection
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());
	/**
	 * The Buffer for outgoing messages
	 */
	private LinkedList<Message>			messageBuffer;
	/**
	 * Is this connection open?
	 * Will be false if closing is in progress
	 */
	private boolean						open;

	/**
	 * Default Constructor
	 * 
	 * @param subprotocol - the subprotocol name
	 */
	public HttpConnection(String subprotocol)
	{
		super();
		this.messageBuffer = new LinkedList<Message>();
		this.setSubprotocol(subprotocol);
		this.open = true;
	}

	/**
	 * The Buffer for outgoing messages
	 * 
	 * @return the messageBuffer
	 */
	public LinkedList<Message> getMessageBuffer()
	{
		return this.messageBuffer;
	}

	/**
	 * Remove an amount of messages from the messageBuffer after sending them to the client
	 * 
	 * @param messages - the number of messages to remove
	 */
	public void clearMessageBuffer(int messages)
	{
		synchronized(this)
		{
			while(messages > 0)
			{
				messages--;
				this.messageBuffer.removeFirst();
			}
		}
	}

	/**
	 * Add a message to the messageBuffer
	 * 
	 * @param message - the message
	 * @throws IOException
	 */
	protected void addMessage(Message message) throws IOException
	{
		if(!this.isOpen())
			throw new IOException("Connection already closed by server!");
		synchronized(this)
		{
			this.messageBuffer.add(message);
			this.notifyAll();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#sendMessage(java.lang.String)
	 */
	@Override
	public void sendMessage(String data) throws IOException
	{
		this.addMessage(new Message(data, this.protocol));
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#sendMessage(byte[], int, int)
	 */
	@Override
	public void sendMessage(byte[] data, int offset, int length) throws IOException
	{
		this.addMessage(new Message(data, offset, length, this.protocol));
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#sendMessage(com.syncnapsis.websockets.Message)
	 */
	@Override
	public void sendMessage(Message message) throws IOException
	{
		this.addMessage(message);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#sendControl(byte, byte[], int, int)
	 */
	@Override
	public void sendControl(byte controlCode, byte[] data, int offset, int length) throws IOException
	{
		this.addMessage(new Message(data, offset, length, controlCode));
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#sendFrame(byte, byte, byte[], int, int)
	 */
	@Override
	public void sendFrame(byte flags, byte opCode, byte[] data, int offset, int length) throws IOException
	{
		this.addMessage(new Message(data, offset, length, opCode));
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#isOpen()
	 */
	@Override
	public boolean isOpen()
	{
		return open;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#close()
	 */
	@Override
	public void close()
	{
		this.close(Protocol.CLOSE_CODE_NORMAL, null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#close(int, java.lang.String)
	 */
	@Override
	public void close(int closeCode, String message)
	{
		if(this.isOpen())
		{
			try
			{
				Message closeMessage = new Message(message, getProtocol().closeOpCode());
				closeMessage.setCloseCode(closeCode);
				this.addMessage(closeMessage);
			}
			catch(IOException e)
			{
				logger.error(e.getMessage(), e);
			}
			this.open = false;
		}
	}
}
