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
package com.syncnapsis.websockets;

import java.io.IOException;

import javax.servlet.http.HttpSession;

/**
 * The WebSocketConnection interface used to send messages and control message receiving.
 * 
 * @author ultimate
 */
public interface Connection
{
	/**
	 * The service that handles the messages
	 * 
	 * @return the serivce
	 */
	public Service getService();

	/**
	 * The service that handles the messages
	 * 
	 * @param service - the service
	 */
	public void setService(Service service);

	/**
	 * The connection protocol used
	 * 
	 * @return the protocol
	 */
	public Protocol getProtocol();

	/**
	 * The connection protocol used
	 * 
	 * @param protocol - the protocol
	 */
	public void setProtocol(Protocol protocol);

	/**
	 * The custom subprotocol name specified in the websocket handshake
	 * 
	 * @return the subprotocol
	 */
	public String getSubprotocol();

	/**
	 * The custom subprotocol name specified in the websocket handshake
	 * 
	 * @param subprotocol - the subprotocol
	 */
	public void setSubprotocol(String subprotocol);

	/**
	 * The WebSocketManager that holds this Service
	 * 
	 * @param manager - the manager
	 */
	public void setManager(WebSocketManager manager);

	/**
	 * The WebSocketManager that holds this Service
	 * 
	 * @return the manager
	 */
	public WebSocketManager getManager();

	/**
	 * A unique id within all services of a given manager
	 * 
	 * @param id - the id
	 */
	public void setId(String id);

	/**
	 * A unique id within all services of a given manager
	 * 
	 * @return the id
	 */
	public String getId();

	/**
	 * Send a text message using the connection
	 * 
	 * @param message - the message
	 * @throws IOException - if sending fails
	 */
	public void sendMessage(String data) throws IOException;

	/**
	 * Send a binary message using the connection
	 * 
	 * @param data - the message content
	 * @param offset - an offset
	 * @param length - message length
	 * @throws IOException - if sending fails
	 */
	public void sendMessage(byte[] data, int offset, int length) throws IOException;

	/**
	 * Send a message using the connection.
	 * 
	 * @param message - the message
	 * @throws IOException - if sending fails
	 */
	public void sendMessage(Message message) throws IOException;

	/**
	 * Send a control frame
	 * 
	 * @param controlCode - the control code
	 * @param data - the message content
	 * @param offset - an offset
	 * @param length - message length
	 * @throws IOException - if sending fails
	 */
	public void sendControl(byte controlCode, byte[] data, int offset, int length) throws IOException;

	/**
	 * Send a any frame
	 * 
	 * @param flags - flags to set in the frame
	 * @param opCode - the opcode
	 * @param data - the message content
	 * @param offset - an offset
	 * @param length - message length
	 * @throws IOException - if sending fails
	 */
	public void sendFrame(byte flags, byte opCode, byte[] data, int offset, int length) throws IOException;

	/**
	 * Is the connection open?
	 * 
	 * @return true or false
	 */
	public boolean isOpen();

	/**
	 * Close the connection with normal close code.
	 */
	void close();

	/**
	 * Close the connection.
	 * 
	 * @param closeCode - the close code
	 * @param message - a message
	 */
	public void close(int closeCode, String message);

	/**
	 * Are frames larger than the frame buffer handled with local fragmentations?
	 * 
	 * @param allowFragmentation - true or false
	 */
	public void setAllowFrameFragmentation(boolean allowFragmentation);

	/**
	 * Are frames larger than the frame buffer handled with local fragmentations?
	 * 
	 * @return true or false
	 */
	public boolean isAllowFrameFragmentation();

	/**
	 * The HttpSession associated with this connection
	 * 
	 * @return the HttpSession
	 */
	public HttpSession getSession();

	/**
	 * The HttpSession associated with this connection
	 * 
	 * @param session - the HttpSession
	 */
	public void setSession(HttpSession session);

	/**
	 * Map-like functionality for setting an attribute for the Connection.
	 * 
	 * @param key - the key
	 * @param value - the value
	 * @return the old value if the was alread in use
	 */
	public Object setAttribute(String key, Object value);

	/**
	 * Map-like functionality for getting an attribute for the Connection.
	 * 
	 * @param key - the key
	 * @return the value
	 */
	public Object getAttribute(String key);
}
