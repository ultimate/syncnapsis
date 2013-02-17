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
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.isTrue(this.port != null || this.sslPort != null, "either port or sslPort must not be null");
		Assert.isTrue(this.port == null || (this.port >= 0 && this.port < 65536), "port must be between 0 (inclusive) and 65536 (exclusive)");
		Assert.isTrue(this.sslPort == null || (this.sslPort >= 0 && this.sslPort < 65536), "sslPort must be between 0 (inclusive) and 65536 (exclusive)");
	}
}
