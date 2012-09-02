package com.syncnapsis.websockets;

import java.util.Collection;

/**
 * The WebSocketListener interface used to handle received messages.
 * 
 * @author ultimate
 */
public interface Service extends ServiceMXBean
{
	/**
	 * Called when a new websocket connection is accepted.
	 * 
	 * @param connection - the connection used
	 */
	public void onOpen(Connection connection);

	/**
	 * Called when an established websocket connection closes.
	 * 
	 * @param connection - the connection used
	 * @param closeCode - the close code
	 * @param message - a close message
	 */
	public void onClose(Connection connection, int closeCode, String message);

	/**
	 * Called on handshake
	 * 
	 * @param connection - the connection used to send messages
	 */
	public void onHandshake(Connection connection);

	/**
	 * Called when a complete text message has been received.
	 * Text messages are assembled from multiple following frames with a buffer.
	 * The maximum size of text message is set with
	 * WebSocketConnection#setMaxTextMessageSize(int).
	 * 
	 * @param connection - the connection used
	 * @param data - the message
	 */
	public void onMessage(Connection connection, String data);

	/**
	 * Called when a complete binary message has been received.
	 * Binary messages are assembled from multiple following frames with a buffer.
	 * The maximum size of text message is set with
	 * WebSocketConnection#setMaxBinaryMessageSize(int).
	 * 
	 * @param connection - the connection used
	 * @param data - the message content
	 * @param offset - an offset
	 * @param length - message length
	 */
	public void onMessage(Connection connection, byte[] data, int offset, int length);

	/**
	 * Convenient method that may be called when complete message has been received.
	 * 
	 * @see Service#onMessage(String)
	 * @see Service#onMessage(byte[], int, int)
	 * @param connection - the connection used
	 * @param message - the message
	 */
	public void onMessage(Connection connection, Message message);

	/**
	 * Called when a control frame has been received.
	 * 
	 * @param connection - the connection used
	 * @param controlCode - the control code
	 * @param data - the message content
	 * @param offset - an offset
	 * @param length - message length
	 * @return true if the message has been fully handled and no further processing is needed
	 */
	public boolean onControl(Connection connection, byte controlCode, byte[] data, int offset, int length);

	/**
	 * Called when any frame is received.
	 * 
	 * @param connection - the connection used
	 * @param flags - flags set in the frame
	 * @param opcode - the opcode
	 * @param data - the message content
	 * @param offset - an offset
	 * @param length - message length
	 * @return true if the message has been fully handled and no further processing is needed
	 */
	public boolean onFrame(Connection connection, byte flags, byte opcode, byte[] data, int offset, int length);

	/**
	 * Register a Connection for this Service. The Connection is than available for broadcasting an
	 * message sending by other Connections. On close Connections should unregister.
	 * 
	 * @see Service#removeConnection(Connection)
	 * @param connection - the Connection
	 */
	public void addConnection(Connection connection);

	/**
	 * Unregister a Connection fr this Service.
	 * On open Connections should register
	 * 
	 * @see Service#addConnection(Connection)
	 * @param connection - the Connection
	 */
	public void removeConnection(Connection connection);

	/**
	 * All currently connected connections as an unmodifiable set.
	 * 
	 * @return the connections
	 */
	public Collection<Connection> getConnections();
	
	/**
	 * Disconnect all current connections.
	 * 
	 * @see Connection#close()
	 * @param closeCode - the close code
	 * @param message - a message
	 */
	public void disconnectAll(int closeCode, String message);
}
