package com.syncnapsis.websockets.engine;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.syncnapsis.enums.EnumEngineSupport;
import com.syncnapsis.exceptions.WebSocketEngineException;
import com.syncnapsis.websockets.Engine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Special engine that holds a list of underlying engines which are dependant on the Jetty-Server
 * initialized by this engine.
 * 
 * @author ultimate
 */
public class JettyServerEngine extends BaseTCPEngine implements InitializingBean
{
	/**
	 * The internal Jetty-Server
	 */
	protected Server	server;
	/**
	 * An optional contextPath for the Jetty-Server to listen at
	 */
	protected String	contextPath;

	/**
	 * Empty default construtor
	 */
	public JettyServerEngine()
	{
		super(EnumEngineSupport.CHILDREN_REQUIRED);
	}

	/**
	 * Construct a new JettyServerWrapper with the standard parameters.
	 * 
	 * @param contextPath - an optional contextPath for the Jetty-Server to listen at
	 * @param port - the port for the WebSocketEngine to listen at (either port or sslPort must be
	 *            set)
	 * @param sslPort - the ssl-port for the Server to listen at (either port or sslPort
	 *            must be set)
	 */
	public JettyServerEngine(String contextPath, Integer port, Integer sslPort)
	{
		super(EnumEngineSupport.CHILDREN_REQUIRED);
		this.contextPath = contextPath;
		this.port = port;
		this.sslPort = sslPort;
		afterPropertiesSet();
	}

	/**
	 * An optional contextPath for the Jetty-Server to listen at
	 * 
	 * @return the context path
	 */
	public String getContextPath()
	{
		return contextPath;
	}

	/**
	 * An optional contextPath for the Jetty-Server to listen at
	 * 
	 * @param contextPath - the context path
	 */
	public void setContextPath(String contextPath)
	{
		this.contextPath = contextPath;
	}

	/**
	 * The internal Jetty-Server
	 * 
	 * @return
	 */
	public Server getServer()
	{
		return this.server;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet()
	{
		super.afterPropertiesSet();
		if(this.contextPath == null)
		{
			this.contextPath = "/";
			logger.info("contextPath is null, will be set to '/'");
		}
		Assert.isTrue(!(this.contextPath.length() > 1 && this.contextPath.endsWith("/")), "contextPath must not end with /");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.engine.BaseEngine#start0()
	 */
	protected void start0()
	{
		try
		{
			logger.debug("starting JettyServerEngine");
			this.server = new Server();

			if(this.port != null)
			{
				// init normal port
				logger.debug("adding connector: port " + this.port);
				SelectChannelConnector connector = new SelectChannelConnector();
				connector.setPort(this.port);
				this.server.addConnector(connector);
			}

			if(this.sslPort != null)
			{
				// init ssl port
				logger.debug("adding ssl-connector: port " + this.sslPort);
				SslSelectChannelConnector connector = new SslSelectChannelConnector();
				connector.setPort(this.sslPort);
				// connector.setKeystore(lKeyStore); // TODO required?
				// connector.setPassword("jWebSocket"); // TODO required?
				// connector.setKeyPassword("jWebSocket"); // TODO required?
				this.server.addConnector(connector);
			}

			ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
			handler.setContextPath(this.contextPath);

			addChildren(handler, this);

			this.server.setHandler(handler);
			this.server.setStopAtShutdown(true);
			this.server.start();
			logger.debug("JettyServer started");
		}
		catch(Exception e)
		{
			throw new WebSocketEngineException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.engine.BaseEngine#stop0()
	 */
	protected void stop0()
	{
		try
		{
			this.server.stop();
		}
		catch(Exception e)
		{
			throw new WebSocketEngineException(e);
		}
	}

	/**
	 * Add the required ServletHolders and FilterHolders to the given ServletContextHandler.<br>
	 * This method recursively scans all children engines for ServletEngines and FilterEngines.
	 * 
	 * @param handler - the ServletContextHandler
	 * @param parent - the Engine to scan the childrens from
	 */
	protected void addChildren(ServletContextHandler handler, Engine parent)
	{
		FilterEngine fe;
		ServletEngine se;
		for(Engine engine : parent.getChildren())
		{
			if(engine instanceof FilterEngine && !engine.isDisabled())
			{
				fe = (FilterEngine) engine;
				logger.debug("adding filter @ '" + fe.getPath() + "': " + fe.getClass().getName());
				handler.addFilter(new FilterHolder(fe), fe.getPath(), null);
			}
			else if(engine instanceof ServletEngine && !engine.isDisabled())
			{
				se = (ServletEngine) engine;
				logger.debug("adding servlet @ '" + se.getPath() + "': " + se.getClass().getName());
				handler.addServlet(new ServletHolder(se), se.getPath());
			}
			else if(engine instanceof BaseWebEngine)
			{
				logger.error("unsupported BaseWebEngine: " + engine.getClass().getName());
			}
			else
			{
				addChildren(handler, engine);
			}
		}
	}
}
