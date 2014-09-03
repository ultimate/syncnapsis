/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import com.syncnapsis.exceptions.WebSocketEngineException;

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
	 * @throws WebSocketEngineException - if the Engine could not be started
	 */
	public void start() throws WebSocketEngineException;

	/**
	 * Stop this Engine and all of it's engines.
	 * 
	 * @throws WebSocketEngineException - if the Engine could not be stopped
	 */
	public void stop() throws WebSocketEngineException;

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
