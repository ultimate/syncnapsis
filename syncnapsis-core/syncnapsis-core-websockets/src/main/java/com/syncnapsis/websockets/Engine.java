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

import java.util.Enumeration;
import java.util.List;

import com.syncnapsis.enums.EnumEngineSupport;
import com.syncnapsis.exceptions.WebSocketEngineException;
import com.syncnapsis.utils.graphs.GenericTreeNode;

/**
 * General interface for WebSocketEngines.
 * Basic implementation by BaseEngine
 * 
 * @author ultimate
 */
public interface Engine extends GenericTreeNode<Engine>, EngineMXBean
{
	/**
	 * The WebSocketManager this engine works for and which handles the WebSocketServices.
	 * The WebSocketManager will be set via initialize(..)
	 * 
	 * @return the manager
	 */
	public WebSocketManager getManager();

	/**
	 * The WebSocketManager this engine works for and which handles the WebSocketServices
	 * 
	 * @return the manager
	 */
	public void setManager(WebSocketManager manager);

	/**
	 * Get the parent WebSocketEngine of this engine.
	 * Engines can be ordered in tree-like structures. With Multiple-Engines forking nodes can be
	 * realised.
	 * The parent engine will be returned, if it exists. This can be a WebSocketManager as well.
	 * WebSocketManagers itself normally don't have a parent.
	 * 
	 * @return the parent engine
	 */
	@Override
	public Engine getParent();

	/**
	 * Set the parent of this engine.
	 * Will be set by parent.
	 * 
	 * @param engine - the parent engine
	 */
	@Override
	public void setParent(Engine engine);

	/**
	 * The underlying WebSocketEngines
	 * 
	 * @return - a copy of the list of engines
	 * @throws WebSocketEngineException - if children are not supported
	 */
	@Override
	public List<Engine> getChildren() throws WebSocketEngineException;

	/**
	 * Set the underlying WebSocketEngines. All old engines will be removed
	 * 
	 * @param engines - the engines
	 * @throws WebSocketEngineException - if children are not supported
	 */
	@Override
	public void setChildren(List<Engine> engines) throws WebSocketEngineException;

	/**
	 * Add a WebSocketEngine
	 * 
	 * @param engine - the engine
	 * @throws WebSocketEngineException - if children are not supported
	 */
	public void addChild(Engine engine) throws WebSocketEngineException;

	/**
	 * Remove a WebSocketEngine
	 * 
	 * @param engine - the engine
	 * @throws WebSocketEngineException - if children are not supported
	 */
	public void removeChild(Engine engine) throws WebSocketEngineException;

	/**
	 * Return wether this type of WebSocketEngine supports children?
	 * 
	 * @see EnumEngineSupport
	 * @return the enum value
	 */
	public EnumEngineSupport getEngineSupport();

	/**
	 * The mapping of WebSocketServices to protocol names
	 * 
	 * @return - the serviceMapping
	 */
	public ServiceMapping getServiceMapping();

	/**
	 * The mapping of WebSocketServices to protocol names
	 * 
	 * @param serviceMapping - the serviceMapping
	 */
	public void setServiceMapping(ServiceMapping serviceMapping);

	/**
	 * Is this engine running?
	 * 
	 * @return true or false
	 */
	public boolean isRunning();

	/**
	 * Is this engine disabled?
	 * If an engine is disabled it won't be started if the parent is started.
	 * Engines cannot be disabled while running.
	 * 
	 * @return true or false
	 */
	@Override
	public boolean isDisabled();

	/**
	 * Is this engine disabled?
	 * If an engine is disabled it won't be started if the parent is started.
	 * Engines cannot be disabled while running.
	 * 
	 * @param disabled - true or false
	 */
	@Override
	public void setDisabled(boolean disabled);

	/**
	 * Start this engine and all required Components like Servlets, Sockets and underlying engines.
	 * Underlying engines will be started first.
	 * 
	 * @throws WebSocketEngineException - if the WebSocketEngine could not be started
	 */
	public void start() throws WebSocketEngineException;

	/**
	 * Stop this Engine.
	 * This engine will be stopped before it's children.
	 * 
	 * @throws WebSocketEngineException - if the WebSocketEngine could not be stopped
	 */
	public void stop() throws WebSocketEngineException;

	/**
	 * Forwards this call to getService(..) from the given WebSocketServiceMapping
	 * 
	 * @param subprotocol - the requested subprotocol name
	 * @param enumeration - optional extensions requested by the client
	 * @return the WebSocketService
	 */
	public Service getService(String subprotocol, Enumeration<String> enumeration);
}
