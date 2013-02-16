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
package com.syncnapsis.websockets.engine;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.syncnapsis.enums.EnumEngineSupport;
import com.syncnapsis.exceptions.WebSocketEngineException;
import com.syncnapsis.utils.MBeanUtil;
import com.syncnapsis.utils.PropertiesUtil;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.ConnectionProperties;
import com.syncnapsis.websockets.Engine;
import com.syncnapsis.websockets.Protocol;
import com.syncnapsis.websockets.Service;
import com.syncnapsis.websockets.ServiceMapping;
import com.syncnapsis.websockets.WebSocketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Basic implementation for WebSocketEngines used to open the underlying socket connection, handle
 * incoming request and forward them to WebSocketServices.
 * 
 * @author ultimate
 */
public abstract class BaseEngine implements Engine, InitializingBean
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger						= LoggerFactory.getLogger(getClass());

	/**
	 * The maximum time to wait for client to terminate connections
	 */
	private static final int			DISCONNECT_MAX_WAIT_TIME	= 2000;
	/**
	 * The time to wait for next check of connected clients during disconnection
	 */
	private static final int			DISCONNECT_LOOP_WAIT_TIME	= 500;

	/**
	 * Is this engine running?
	 */
	private boolean						running						= false;
	/**
	 * Is this engine disabled?
	 */
	private boolean						disabled					= false;
	/**
	 * Is this engine currently locked. Locked=true is set during start and stop procedure
	 */
	private boolean						locked						= false;
	/**
	 * The WebSocketManager this engine works for and which handles the WebSocketServices
	 */
	protected WebSocketManager			manager;
	/**
	 * The parent of this engine
	 */
	protected Engine					parent;
	/**
	 * The list of child engines handled by this engine
	 */
	protected List<Engine>				children;
	/**
	 * Does this type of WebSocketEngine supports children?
	 */
	protected EnumEngineSupport			engineSupport;
	/**
	 * The global ConnectionProperties
	 */
	protected ConnectionProperties		connectionProperties;
	/**
	 * The mapping of WebSocketServices to protocol names
	 */
	protected ServiceMapping			serviceMapping;

	/**
	 * Default Constructor.
	 * Engines have to be set via setEngines(..) or addEngine(..)
	 * 
	 * @see BaseEngine#setEngines(List)
	 * @see BaseEngine#addEngine(Engine)
	 * @param engineSupport - does this engine require children engines?
	 */
	public BaseEngine(EnumEngineSupport engineSupport)
	{
		super();
		this.engineSupport = engineSupport;
		this.children = new LinkedList<Engine>();
		init();
	}

	/**
	 * Construct a new engine that holds a single other engine
	 * Additional engines may be set via setEngines(..) or addEngine(..)
	 * 
	 * @see BaseEngine#setEngines(List)
	 * @see BaseEngine#addEngine(Engine)
	 * @param engineSupport - does this engine require children engines?
	 * @param engine - the engine
	 */
	public BaseEngine(EnumEngineSupport engineSupport, Engine engine)
	{
		this(engineSupport);
		this.addChild(engine);
	}

	/**
	 * Construct a new engine that holds a list of other engines
	 * Additional engines may be set via setEngines(..) or addEngine(..)
	 * 
	 * @see BaseEngine#setEngines(List)
	 * @see BaseEngine#addEngine(Engine)
	 * @param engineSupport - does this engine require children engines?
	 * @param engines - the engines
	 */
	public BaseEngine(EnumEngineSupport engineSupport, List<Engine> engines)
	{
		this(engineSupport);
		this.setChildren(engines);
	}

	/**
	 * internal initialization used in constructors
	 */
	protected void init()
	{
		MBeanUtil.registerMBean(this);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#getManager()
	 */
	@Override
	public WebSocketManager getManager()
	{
		return manager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#setManager(com.syncnapsis.websockets.WebSocketManager)
	 */
	@Override
	public void setManager(WebSocketManager manager)
	{
		this.manager = manager;
		for(Engine child : this.children)
		{
			child.setManager(manager);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.ServiceMXBean#getConnectionProperties()
	 */
	@Override
	public ConnectionProperties getConnectionProperties()
	{
		if(this.connectionProperties != null)
			return connectionProperties;
		else if(this.parent != null)
			return this.parent.getConnectionProperties();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.ServiceMXBean#setConnectionProperties(com.syncnapsis.websockets.ConnectionProperties)
	 */
	@Override
	public void setConnectionProperties(ConnectionProperties connectionProperties)
	{
		this.connectionProperties = connectionProperties;
	}

	/**
	 * The global connection properties used for all Connections and Engines.
	 * 
	 * @param fileName - the path and filename to a *.properties-File
	 */
	public void setConnectionProperties(String fileName) throws IOException
	{
		this.setConnectionProperties(PropertiesUtil.loadProperties(new File(fileName)));
	}

	/**
	 * The global connection properties used for all Connections and Engines.
	 * 
	 * @param p - the Properties-Object
	 */
	public void setConnectionProperties(Properties p)
	{
		this.setConnectionProperties(new ConnectionProperties(p));
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#getServiceMapping()
	 */
	@Override
	public ServiceMapping getServiceMapping()
	{
		return serviceMapping;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.Engine#setServiceMapping(com.syncnapsis.websockets.WebSocketServiceMapping)
	 */
	@Override
	public void setServiceMapping(ServiceMapping serviceMapping)
	{
		this.serviceMapping = serviceMapping;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#getParent()
	 */
	@Override
	public Engine getParent()
	{
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#setParent(com.syncnapsis.websockets.Engine)
	 */
	@Override
	public void setParent(Engine parentEngine)
	{
		this.parent = parentEngine;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#getChildren()
	 */
	@Override
	public List<Engine> getChildren()
	{
		if(this.getEngineSupport().equals(EnumEngineSupport.NOT_SUPPORTED))
			throw new WebSocketEngineException("children not supported");
		return new LinkedList<Engine>(this.children);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#setChildren(java.util.List)
	 */
	@Override
	public void setChildren(List<Engine> engines)
	{
		if(this.getEngineSupport().equals(EnumEngineSupport.NOT_SUPPORTED))
			throw new WebSocketEngineException("children not supported");
		if(this.isRunning())
			throw new WebSocketEngineException("engines cannot be changed while running");
		this.children.clear();

		for(Engine engine : engines)
			this.addChild(engine);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#addChild(com.syncnapsis.websockets.Engine)
	 */
	@Override
	public void addChild(Engine engine)
	{
		if(this.getEngineSupport().equals(EnumEngineSupport.NOT_SUPPORTED))
			throw new WebSocketEngineException("children not supported");
		if(this.isRunning())
			throw new WebSocketEngineException("engines cannot be changed while running");
		this.children.add(engine);
		engine.setParent(this);
		engine.setManager(this.manager);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#removeChild(com.syncnapsis.websockets.Engine)
	 */
	@Override
	public void removeChild(Engine engine)
	{
		if(this.getEngineSupport().equals(EnumEngineSupport.NOT_SUPPORTED))
			throw new WebSocketEngineException("children not supported");
		if(this.isRunning())
			throw new WebSocketEngineException("engines cannot be changed while running");
		this.children.remove(engine);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#getEngineSupport()
	 */
	@Override
	public EnumEngineSupport getEngineSupport()
	{
		return this.engineSupport;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#isRunning()
	 */
	@Override
	public boolean isRunning()
	{
		return this.running;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#isDisabled()
	 */
	@Override
	public boolean isDisabled()
	{
		return disabled;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#start()
	 */
	public final void start() throws WebSocketEngineException
	{
		synchronized(this)
		{
			if(this.locked || this.running)
				return;
			if(this.disabled)
			{
				logger.info("engine is disabled. Engine and all children will be ignored");
				return;
			}
			this.locked = true;
			try
			{
				afterPropertiesSet();
			}
			catch(Exception e)
			{
				throw new WebSocketEngineException(e);
			}
			for(Engine engine : this.children)
				engine.start();
			this.start0();
			this.running = true;
			this.locked = false;
			this.notifyAll();
		}
	}

	/**
	 * Internal start procedure.
	 * 
	 * @throws WebSocketEngineException - if the WebSocketEngine could not be started
	 */
	protected abstract void start0() throws WebSocketEngineException;

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#stop()
	 */
	public final synchronized void stop() throws WebSocketEngineException
	{
		synchronized(this)
		{
			while(this.locked)
			{
				try
				{
					this.wait();
				}
				catch(InterruptedException e)
				{
					logger.error(e.getMessage());
				}
			}
			this.locked = true;
			if(!this.running)
				return;
			this.disconnectAll(Protocol.CLOSE_CODE_GOING_AWAY, "WebSocket-Engine stopped.");
			this.stop0();
			for(Engine engine : this.children)
				engine.stop();
			this.running = false;
			this.locked = false;
			this.notifyAll();
		}
	}

	/**
	 * Internal stop procedure.
	 * 
	 * @throws WebSocketEngineException - if the WebSocketEngine could not be stopped
	 */
	protected abstract void stop0() throws WebSocketEngineException;

	/**
	 * Disconnect all current connections.
	 * 
	 * @see Connection#close()
	 * @see Service#disconnectAll(int, String)
	 * @param closeCode - the close code
	 * @param message - a message
	 */
	protected void disconnectAll(int closeCode, String message)
	{
		if(this.serviceMapping != null)
		{
			Service service;
			for(String protocol : this.serviceMapping.getProtocolNames())
			{
				service = this.serviceMapping.getService(protocol, null);
				int connectionsToClose = service.getNumberOfConnections();
				logger.info("Closing all connections for protocol '" + protocol + "': " + connectionsToClose + " connections to close");

				service.disconnectAll(closeCode, message);

				if(connectionsToClose > 0)
				{
					logger.info("Waiting for clients to terminate connection");
					synchronized(this)
					{
						long now = this.manager.getTimeProvider().get();
						while(service.getNumberOfConnections() > 0)
						{
							logger.info("  connections open: " + service.getNumberOfConnections());
							try
							{
								this.wait(DISCONNECT_LOOP_WAIT_TIME);
								if(this.manager.getTimeProvider().get() - now > DISCONNECT_MAX_WAIT_TIME)
								{
									logger.warn("  not all connections could be closed: " + service.getNumberOfConnections());
									break;
								}
							}
							catch(InterruptedException e)
							{
								logger.error("Could not wait for connections beeing closed");
								break;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * When overriding ensure super.afterPropertiesSet() is called!
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception
	{
		// only check at start procedure
		if(this.locked)
		{
			// check parent
			if(!(this instanceof WebSocketManager))
			{
				Assert.notNull(this.parent, "this engine requires a parent");
				Assert.notNull(this.manager, "this engine requires a WebSocketManager");
			}
			// check children
			if(this.engineSupport.equals(EnumEngineSupport.CHILDREN_REQUIRED))
			{
				Assert.isTrue(this.children.size() > 0, "this engine requires children engines");
				// check number of enabled children
				int enabledChildren = 0;
				for(Engine child : this.children)
				{
					if(!child.isDisabled())
						enabledChildren++;
				}
				Assert.isTrue(enabledChildren > 0, "this engine requires children engines, but all children are disabled");
			}
			// check service mapping
			// check connection properties
			else if(this.engineSupport.equals(EnumEngineSupport.NOT_SUPPORTED) && this.serviceMapping == null)
			{
				Engine parent = this;
				boolean mappingFound = false;
				boolean connectionPropertiesFound = false;
				while((parent = parent.getParent()) != null)
				{
					if(parent.getServiceMapping() != null)
						mappingFound = true;
					if(parent.getConnectionProperties() != null)
						connectionPropertiesFound = true;
				}
				Assert.isTrue(mappingFound,
						"a service mapping is required for each engine that does not support children either directly or indirectly via parent engines");
				Assert.isTrue(connectionPropertiesFound,
						"connection properties are required for each engine that does not support children either directly or indirectly via parent engines");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Engine#getService(java.lang.String, java.util.Enumeration)
	 */
	@Override
	public Service getService(String subprotocol, Enumeration<String> extensions)
	{
		if(!this.isRunning())
		{
			logger.error("engine is not running. connection refused.");
			return null;
		}

		Service service = null;
		if(this.serviceMapping != null)
			service = this.serviceMapping.getService(subprotocol, extensions);
		if(service == null && this.parent != null)
			service = this.parent.getService(subprotocol, extensions);
		if(service != null && service.getFreeConnections() != 0)
			return service;
		return null;
	}
}
