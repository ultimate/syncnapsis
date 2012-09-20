package com.syncnapsis.websockets;

import java.util.List;

import com.syncnapsis.enums.EnumEngineSupport;
import com.syncnapsis.exceptions.WebSocketEngineException;
import com.syncnapsis.providers.ConnectionProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.security.SecurityManager;
import com.syncnapsis.websockets.engine.BaseEngine;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * The WebSocketManager that holds the WebSocketEngine and creates the WebSockets on incoming
 * connections forwarded from the engine.
 * 
 * @author ultimate
 */
public class WebSocketManager extends BaseEngine implements InitializingBean, DisposableBean
{
	/**
	 * Should this manager start automatically on afterPropertiesSet()?
	 */
	protected boolean				autoStart	= true;

	/**
	 * The SecurityManager used to access several providers.
	 */
	protected SecurityManager		securityManager;

	/**
	 * The ConnectionProvider used to access the current Connection
	 */
	protected ConnectionProvider	connectionProvider;

	/**
	 * Default Constructor that creates an empty manager which has to be filled via setters.
	 */
	public WebSocketManager()
	{
		super(EnumEngineSupport.CHILDREN_REQUIRED);
	}

	/**
	 * Construct a new WebSocketManager that holds a single engine
	 * Additional engines may be set via setEngines(..) or addEngine(..)
	 * 
	 * @see MultipleEngine#setEngines(List)
	 * @see MultipleEngine#addEngine(Engine)
	 * @param engine - the engine
	 */
	public WebSocketManager(Engine engine)
	{
		super(EnumEngineSupport.CHILDREN_REQUIRED, engine);
	}

	/**
	 * Construct a new WebSocketManager that holds a list of engines
	 * Additional engines may be set via setEngines(..) or addEngine(..)
	 * 
	 * @see MultipleEngine#setEngines(List)
	 * @see MultipleEngine#addEngine(Engine)
	 * @param engine - the engine
	 */
	public WebSocketManager(List<Engine> engines)
	{
		super(EnumEngineSupport.CHILDREN_REQUIRED, engines);
	}

	/**
	 * internal initialization used in constructors
	 */
	@Override
	protected void init()
	{
		super.init();
		this.manager = this;
		this.connectionProperties = new ConnectionProperties();
	}

	/**
	 * Should this manager start automatically on afterPropertiesSet()?
	 * 
	 * @return true or false
	 */
	public boolean isAutoStart()
	{
		return autoStart;
	}

	/**
	 * Should this manager start automatically on afterPropertiesSet()?
	 * 
	 * @param autoStart - true or false
	 */
	public void setAutoStart(boolean autoStart)
	{
		this.autoStart = autoStart;
	}

	/**
	 * The TimeProvider used to access the current time
	 * 
	 * @return timeProvider
	 */
	public TimeProvider getTimeProvider()
	{
		return securityManager.getTimeProvider();
	}

	/**
	 * The SessionProvider used to access the current Session
	 * 
	 * @return sessionProvider
	 */
	public SessionProvider getSessionProvider()
	{
		return securityManager.getSessionProvider();
	}

	/**
	 * The SecurityManager used to access several providers.
	 * 
	 * @return securityManager
	 */
	public SecurityManager getSecurityManager()
	{
		return securityManager;
	}

	/**
	 * The SecurityManager used to access several providers.
	 * 
	 * @param securityManager - the SecurityManager
	 */
	public void setSecurityManager(SecurityManager securityManager)
	{
		this.securityManager = securityManager;
	}

	/**
	 * The ConnectionProvider used to access the current Connection
	 * 
	 * @return connectionProvider
	 */
	public ConnectionProvider getConnectionProvider()
	{
		return connectionProvider;
	}

	/**
	 * The ConnectionProvider used to access the current Connection
	 * 
	 * @param connectionProvider - the ConnectionProvider
	 */
	public void setConnectionProvider(ConnectionProvider connectionProvider)
	{
		this.connectionProvider = connectionProvider;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();

		Assert.notNull(securityManager.getTimeProvider(), "timeProvider of securityManager must not be null");
		Assert.notNull(securityManager.getSessionProvider(), "sessionProvider of securityManager  must not be null");
		Assert.notNull(connectionProvider, "connectionProvider must not be null");
		if(connectionProperties == null)
		{
			logger.warn("No ConnectionProperties set. Using default.");
			setConnectionProperties(new ConnectionProperties());
		}

		if(this.autoStart)
			this.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception
	{
		try
		{
			this.stop();
		}
		catch(Exception e)
		{
			// errors are not detailedly printed by spring
			logger.error("Error destroying WebSocketManager", e);
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.engine.BaseEngine#start0()
	 */
	@Override
	protected void start0() throws WebSocketEngineException
	{
		logger.info("WebSocketManager started");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.engine.BaseEngine#stop0()
	 */
	@Override
	protected void stop0() throws WebSocketEngineException
	{
		logger.info("WebSocketManager stopped");
	}

	/**
	 * Generate a unique ID within the given subprotocol and this service to identify a
	 * WebSocketService.
	 * 
	 * @param subprotocol - the subprotocol
	 * @return the id
	 */
	public String generateId(String subprotocol)
	{
		return getTimeProvider().get() + "@" + subprotocol;
	}
}
