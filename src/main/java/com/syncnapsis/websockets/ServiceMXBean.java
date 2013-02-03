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
	 * @throws IOException - if no connection with the given ID is not connected or sending fails
	 */
	public void sendMessage(String id, String data) throws IOException;

	/**
	 * Send a binary message to a given client within this service
	 * 
	 * @param id - the id of the service to send the message to
	 * @param data - the message content
	 * @param offset - an offset
	 * @param length - message length
	 * @throws IOException - if no connection with the given ID is not connected or sending fails
	 */
	public void sendMessage(String id, byte[] data, int offset, int length) throws IOException;

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
