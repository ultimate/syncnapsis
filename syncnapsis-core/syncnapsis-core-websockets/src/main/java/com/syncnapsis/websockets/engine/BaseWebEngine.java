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
import com.syncnapsis.utils.JettyUtil;
import com.syncnapsis.websockets.Engine;
import com.syncnapsis.websockets.engine.jetty.JettyServerEngine;

/**
 * Abstract base Class for ServletEngine and Filter/ServletEngine offering the support for a context path
 * and the neccessary checks for a running WebServer.
 * 
 * @see ServletEngine
 * @see Filter/ServletEngine
 * @author ultimate
 */
public abstract class BaseWebEngine extends BaseEngine
{
	/**
	 * The path for the Engine to listen at
	 */
	protected String	path;
	
	/**
	 * Empty default construtor
	 */
	public BaseWebEngine()
	{
		super(EnumEngineSupport.NOT_SUPPORTED);
	}

	/**
	 * Construct a new BaseWebEngine with a given Filter/Servlet and path.
	 * 
	 * @param path - an optional path for the Filter/Servlet to listen at
	 */
	public BaseWebEngine(String path)
	{
		super(EnumEngineSupport.NOT_SUPPORTED);
		this.path = path;
	}

	/**
	 * The path for the Filter/Servlet to listen at
	 * 
	 * @return the path
	 */
	public String getPath()
	{
		return this.path;
	}

	/**
	 * The path for the Filter/Servlet to listen at
	 * 
	 * @param path - the path
	 */
	public void setPath(String path)
	{
		this.path = path;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		if(this.path == null)
		{
			logger.debug("path is null, will be set to '/*'");
			this.path = "/*";
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketEngine#start0()
	 */
	@Override
	protected void start0()
	{
		Engine parent = this;
		boolean jettyServerEngineFound = false;
		while((parent = parent.getParent()) != null)
		{
			if(parent instanceof JettyServerEngine)
			{
				jettyServerEngineFound = true;
				break;
			}
		}
		if(!jettyServerEngineFound)
		{
			if(!JettyUtil.jettyFound())
			{
				logger.warn("This engine requires a running web server. Please add a JettyServerEngine to the WebSocketManager or make sure this application runs inside a webserver and the Filter/Servlet is properly mapped.");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketEngine#stop0()
	 */
	@Override
	protected void stop0()
	{
	}
}
