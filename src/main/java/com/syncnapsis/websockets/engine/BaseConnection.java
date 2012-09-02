package com.syncnapsis.websockets.engine;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.Protocol;
import com.syncnapsis.websockets.Service;
import com.syncnapsis.websockets.WebSocketManager;

public abstract class BaseConnection implements Connection
{
	/**
	 * The manager that handles all connections
	 */
	protected WebSocketManager			manager;
	/**
	 * The service that handles the messages
	 */
	protected Service					service;
	/**
	 * The protocol used
	 */
	protected Protocol					protocol;
	/**
	 * The subprotocol specified in the websocket handshake
	 */
	protected String					subprotocol;
	/**
	 * The id set by the manager.
	 */
	protected String					id;
	/**
	 * @see Connection#isAllowFrameFragmentation()
	 * @see Connection#setAllowFrameFragmentation(boolean)
	 */
	protected boolean					allowFrameFragmentation;

	/**
	 * The HttpSession associated with this connection
	 */
	protected HttpSession				session;

	/**
	 * The map for the attributes
	 * 
	 * @see Connection#setAttribute(String, Object)
	 * @see Connection#getAttribute(String)
	 */
	protected final Map<String, Object>	attributes	= new HashMap<String, Object>();

	/**
	 * Basic Constructor empty Constructor
	 */
	public BaseConnection()
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#getManager()
	 */
	@Override
	public WebSocketManager getManager()
	{
		return this.manager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#setManager(com.syncnapsis.websockets.WebSocketManager)
	 */
	@Override
	public void setManager(WebSocketManager manager)
	{
		this.manager = manager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#getService()
	 */
	@Override
	public Service getService()
	{
		return service;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#setService(com.syncnapsis.websockets.Service)
	 */
	@Override
	public void setService(Service service)
	{
		this.service = service;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#getProtocol()
	 */
	@Override
	public Protocol getProtocol()
	{
		return this.protocol;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#setProtocol(com.syncnapsis.websockets.Protocol)
	 */
	@Override
	public void setProtocol(Protocol protocol)
	{
		this.protocol = protocol;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#getSubprotocol()
	 */
	@Override
	public String getSubprotocol()
	{
		return this.subprotocol;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#setSubprotocol(java.lang.String)
	 */
	@Override
	public void setSubprotocol(String subprotocol)
	{
		this.subprotocol = subprotocol;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#getId()
	 */
	@Override
	public String getId()
	{
		return id;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#setId(java.lang.String)
	 */
	@Override
	public void setId(String id)
	{
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#setAllowFrameFragmentation(boolean)
	 */
	@Override
	public void setAllowFrameFragmentation(boolean allowFragmentation)
	{
		this.allowFrameFragmentation = allowFragmentation;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#isAllowFrameFragmentation()
	 */
	@Override
	public boolean isAllowFrameFragmentation()
	{
		return allowFrameFragmentation;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#getSession()
	 */
	@Override
	public HttpSession getSession()
	{
		return session;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#setSession(javax.servlet.http.HttpSession)
	 */
	@Override
	public void setSession(HttpSession session)
	{
		this.session = session;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#setAttribute(java.lang.String, java.lang.Object)
	 */
	@Override
	public synchronized Object setAttribute(String key, Object value)
	{
		return this.attributes.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Connection#getAttribute(java.lang.String)
	 */
	@Override
	public synchronized Object getAttribute(String key)
	{
		return this.attributes.get(key);
	}
}
