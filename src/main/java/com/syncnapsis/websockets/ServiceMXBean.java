package com.syncnapsis.websockets;

import java.io.IOException;

import com.syncnapsis.exceptions.WebSocketServiceException;

/**
 * MXBean-Interface for Services.
 * All relevant Methods for configuring the Service are declared here.
 * 
 * @author ultimate
 */
public interface ServiceMXBean
{
	/**
	 * Send a text message to all connected clients within this service
	 * 
	 * @param data - the message
	 * @throws IOException - if sending fails
	 */
	public void broadcast(String data);

	/**
	 * Send a binary message to all connected clients within this service
	 * 
	 * @param data - the message content
	 * @param offset - an offset
	 * @param length - message length
	 * @throws IOException - if sending fails
	 */
	public void broadcast(byte[] data, int offset, int length);

	/**
	 * Send a text message to a given client within this service
	 * 
	 * @param id - the id of the connection to send the message to
	 * @param data - the message
	 * @throws WebSocketServiceException - if no connection with the given is connected or sending
	 *             fails
	 */
	public void sendMessage(String id, String data) throws WebSocketServiceException;

	/**
	 * Send a binary message to a given client within this service
	 * 
	 * @param id - the id of the service to send the message to
	 * @param data - the message content
	 * @param offset - an offset
	 * @param length - message length
	 * @throws WebSocketServiceException - if no service with the given is connected or sending
	 *             fails
	 */
	public void sendMessage(String id, byte[] data, int offset, int length) throws WebSocketServiceException;

	/**
	 * Get the amount of currently connected connections.
	 * 
	 * @return the number of connections
	 */
	public int getNumberOfConnections();

	/**
	 * The maximum amount of connections.
	 * All negative numbers result in infinite allowed connections.
	 * 
	 * @param maxConnections - maxConnections
	 */
	public void setMaxConnections(int maxConnections);

	/**
	 * The maximum amount of connections.
	 * All negative numbers result in infinite allowed connections.
	 * 
	 * @return maxConnections
	 */
	public int getMaxConnections();

	/**
	 * The amount of connections that is free.
	 * Negative return value means infinite connections allowed.
	 * 
	 * @return the number of free connections
	 */
	public int getFreeConnections();
}
