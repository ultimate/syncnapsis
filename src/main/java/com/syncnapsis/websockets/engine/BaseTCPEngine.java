package com.syncnapsis.websockets.engine;

import com.syncnapsis.enums.EnumEngineSupport;
import org.springframework.util.Assert;

/**
 * Extension of BaseEngine for egines using TCP-Sockets
 * 
 * @author ultimate
 */
public abstract class BaseTCPEngine extends BaseEngine
{
	/**
	 * The port for the WebSocketEngine to listen at.
	 * Either port or sslPort must be set.
	 */
	protected Integer	port;

	/**
	 * The ssl-port for the WebSocketEngine to listen at.
	 * Either port or sslPort must be set.
	 */
	protected Integer	sslPort;
	
	/**
	 * @see BaseEngine#BaseEngine(EnumEngineSupport)
	 * @param engineSupport - does this engine require children engines?
	 */
	public BaseTCPEngine(EnumEngineSupport engineSupport)
	{
		super(engineSupport);
	}
	
	/**
	 * @see BaseEngine#BaseEngine(EnumEngineSupport)
	 * @param engineSupport - does this engine require children engines?
	 * @param port - the port for the WebSocketEngine to listen at. Either port or sslPort must be set.
	 * @param sslPort - the sslPort for the WebSocketEngine to listen at. Either port or sslPort must be set.
	 */
	public BaseTCPEngine(EnumEngineSupport engineSupport, Integer port, Integer sslPort)
	{
		super(engineSupport);
		this.port = port;
		this.sslPort = sslPort;
	}

	/**
	 * The port for the WebSocketEngine to listen at.
	 * Either port or sslPort must be set.
	 * 
	 * @return the port
	 */
	public Integer getPort()
	{
		return this.port;
	}

	/**
	 * The port for the WebSocketEngine to listen at.
	 * Either port or sslPort must be set.
	 * 
	 * @param port - the port
	 */
	public void setPort(Integer port)
	{
		this.port = port;
	}

	/**
	 * The ssl-port for the WebSocketEngine to listen at.
	 * Either port or sslPort must be set.
	 * 
	 * @return the sslPort
	 */
	public Integer getSslPort()
	{
		return this.sslPort;
	}

	/**
	 * The ssl-port for the WebSocketEngine to listen at.
	 * Either port or sslPort must be set.
	 * 
	 * @param sslPort - the sslPort
	 */
	public void setSslPort(Integer sslPort)
	{
		this.sslPort = sslPort;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet()
	{
		super.afterPropertiesSet();
		Assert.isTrue(this.port != null || this.sslPort != null, "either port or sslPort must not be null");
		Assert.isTrue(this.port == null || this.port >= 0, "port must be 0 or positive");
		Assert.isTrue(this.sslPort == null || this.sslPort >= 0, "sslPort must be 0 or positive");
	}
}
