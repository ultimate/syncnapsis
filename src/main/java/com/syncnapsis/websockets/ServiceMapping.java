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

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for mapping WebSocketServices to custom WebSocket protocol names.
 * 
 * @author ultimate
 */
public class ServiceMapping
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

	/**
	 * The list of protocols and their mapped WebSocketServices
	 */
	private Map<String, Service>		protocols;

	/**
	 * Default Constructor creating an empty Mapping
	 */
	public ServiceMapping()
	{
		this.protocols = new TreeMap<String, Service>();
	}

	/**
	 * Create a new WebSocketMapping with a given protocol-service-Map
	 * 
	 * @param protocols - the list of protocols and their mapped WebSocketServices for this manager
	 *            (entries will be copied to internal map)
	 */
	public ServiceMapping(Map<String, Service> protocols)
	{
		super();
		this.addProtocols(protocols);
	}

	/**
	 * Set the protocols.
	 * Unsafe version for spring-application-contexts via xml, incorrect classes will be logged.
	 * 
	 * @see WebSocketManager#addProtocol(String, Service)
	 * @param protocols - the protocols
	 * @throws ClassNotFoundException
	 */
	public void setProtocols(Map<String, Service> protocols)
	{
		this.protocols.clear();
		this.addProtocols(protocols);
	}

	/**
	 * Add a list of protocols
	 * 
	 * @see WebSocketManager#addProtocol(String, Service)
	 * @param protocols - the protocols
	 */
	public void addProtocols(Map<String, Service> protocols)
	{
		for(Entry<String, Service> entry : protocols.entrySet())
		{
			this.addProtocol(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Add a specific WebSocketService and map it to a subprotocol name
	 * 
	 * @param subprotocol - the subprotocol name
	 * @param serviceClass - the WebSocketService
	 */
	public void addProtocol(String subprotocol, Service service)
	{
		logger.info("adding protocol '" + subprotocol + "': " + service.getClass().getName());
		this.protocols.put(subprotocol, service);
	}

	/**
	 * Remove a WebSocketService from the mapping to subprotocol names
	 * 
	 * @param subprotocol - the subprotocol name
	 */
	public void removeProtocol(String subprotocol)
	{
		this.protocols.remove(subprotocol);
	}

	/**
	 * Get all supported protocol names
	 * 
	 * @return the supported protocols
	 */
	public Collection<String> getProtocolNames()
	{
		return Collections.unmodifiableCollection(this.protocols.keySet());
	}

	/**
	 * Get a WebSocketService based on the given request and protocol name.
	 * This WebSocketManager ignores the request and chooses the WebSocketService from the list of
	 * services mapped to specific protocols.
	 * 
	 * @param protocol - the protocol name
	 * @param extensions - optional extensions requested by the client
	 * @return the service
	 */
	public Service getService(String subprotocol, Enumeration<String> extensions)
	{
		return this.protocols.get(subprotocol);
	}
}
