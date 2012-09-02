package com.syncnapsis.websockets;

import com.syncnapsis.exceptions.WebSocketEngineException;
import com.syncnapsis.exceptions.WebSocketManagerException;

/**
 * MXBean-Interface for Engines.
 * All relevant Methods for configuring the Engines are declared here.
 * 
 * @author ultimate
 */
public interface EngineMXBean
{
	/**
	 * Start this Engine and all of it's engines.
	 * 
	 * @throws WebSocketEngineException - if the WebSocketManager could not be started
	 */
	public void start() throws WebSocketManagerException;

	/**
	 * Stop this Engine and all of it's engines.
	 * 
	 * @throws WebSocketEngineException - if the WebSocketManager could not be stopped
	 */
	public void stop() throws WebSocketManagerException;

	/**
	 * Is this Engine running?
	 * 
	 * @return true or false
	 */
	public boolean isRunning();

	/**
	 * The global connection properties used for all Connections and Engines.
	 * 
	 * @return the ConnectionProperties
	 */
	public ConnectionProperties getConnectionProperties();

	/**
	 * The global connection properties used for all Connections and Engines.
	 * 
	 * @param connectionProperties - the ConnectionProperties
	 */
	public void setConnectionProperties(ConnectionProperties connectionProperties);
}
