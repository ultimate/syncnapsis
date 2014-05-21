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

import javax.servlet.http.HttpSession;

import org.eclipse.jetty.websocket.WebSocket;
import com.syncnapsis.websockets.Service;
import com.syncnapsis.websockets.WebSocketManager;

/**
 * Wrapper class for mapping Jetty methods to a given WebSocketListener
 * 
 * @author ultimate
 */
public class JettyWebSocketWrapper implements WebSocket, WebSocket.OnBinaryMessage, WebSocket.OnTextMessage, WebSocket.OnControl, WebSocket.OnFrame
{
	/**
	 * The Service to map the Jetty methods to
	 */
	private Service						service;
	/**
	 * The WebSocketManager to use
	 */
	private WebSocketManager			manager;
	/**
	 * The HttpSession of the incoming WebSocketHandshake HttpRequest
	 */
	private HttpSession					session;
	/**
	 * The Connection used to send messages
	 */
	private com.syncnapsis.websockets.Connection	connection;

	/**
	 * Constructs a new Wrapper mapping all Jetty methods to the given Service
	 * 
	 * @param service - the Service
	 * @param manager - the WebSocketManager to use
	 * @param session - the HttpSession of the incoming WebSocketHandshake HttpRequest
	 */
	public JettyWebSocketWrapper(Service service, WebSocketManager manager, HttpSession session)
	{
		this.service = service;
		this.manager = manager;
		this.session = session;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jetty.websocket.WebSocket#onOpen(org.eclipse.jetty.websocket.WebSocket.Connection
	 * )
	 */
	@Override
	public void onOpen(Connection connection)
	{
		if(this.connection == null)
			this.connection = wrapConnection(connection);

		provideSession();
		this.service.onOpen(this.connection);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket#onClose(int, java.lang.String)
	 */
	@Override
	public void onClose(int closeCode, String message)
	{
		provideSession();
		this.service.onClose(this.connection, closeCode, message);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket.OnFrame#onFrame(byte, byte, byte[], int, int)
	 */
	@Override
	public boolean onFrame(byte flags, byte opcode, byte[] data, int offset, int length)
	{
		provideSession();
		return this.service.onFrame(this.connection, flags, opcode, data, offset, length);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jetty.websocket.WebSocket.OnFrame#onHandshake(org.eclipse.jetty.websocket.WebSocket
	 * .FrameConnection)
	 */
	@Override
	public void onHandshake(FrameConnection connection)
	{
		if(this.connection == null)
			this.connection = wrapConnection(connection);

		provideSession();
		this.service.onHandshake(this.connection);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket.OnControl#onControl(byte, byte[], int, int)
	 */
	@Override
	public boolean onControl(byte controlCode, byte[] data, int offset, int length)
	{
		provideSession();
		return this.service.onControl(this.connection, controlCode, data, offset, length);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket.OnTextMessage#onMessage(java.lang.String)
	 */
	@Override
	public void onMessage(String data)
	{
		provideSession();
		this.service.onMessage(this.connection, data);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jetty.websocket.WebSocket.OnBinaryMessage#onMessage(byte[], int, int)
	 */
	@Override
	public void onMessage(byte[] data, int offset, int length)
	{
		provideSession();
		this.service.onMessage(this.connection, data, offset, length);
	}

	/**
	 * Wrap the given jetty connection to the own Connection type
	 * 
	 * @see JettyConnectionWrapper
	 * @param connection - the connection to wrap
	 * @return the wrapped connection
	 */
	protected JettyConnectionWrapper wrapConnection(Connection connection)
	{
		JettyConnectionWrapper wrapper = new JettyConnectionWrapper(connection);
		wrapper.setSubprotocol(connection.getProtocol());
		wrapper.setService(this.service);
		wrapper.setManager(this.manager);
		wrapper.setSession(this.session);
		return wrapper;
	}

	/**
	 * Provide the current Session to the SessionProvider
	 */
	protected void provideSession()
	{
		this.manager.getSessionProvider().set(this.session);
		this.manager.getConnectionProvider().set(this.connection);
	}
}
