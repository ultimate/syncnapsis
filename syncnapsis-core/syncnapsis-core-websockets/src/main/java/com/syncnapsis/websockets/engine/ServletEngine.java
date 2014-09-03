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
package com.syncnapsis.websockets.engine;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * Extension of BaseWebEngine for egines using Servlets
 * 
 * @author ultimate
 */
public abstract class ServletEngine extends BaseWebEngine implements Servlet
{
	/**
	 * The ServletConfig object
	 */
	protected ServletConfig	servletConfig;

	/**
	 * Empty default construtor
	 */
	public ServletEngine()
	{
		super();
	}

	/**
	 * Construct a new BaseServletEngine with a given Servlet and path.
	 * 
	 * @param path - an optional path for the Servlet to listen at
	 */
	public ServletEngine(String path)
	{
		super(path);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		this.servletConfig = config;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#getServletConfig()
	 */
	@Override
	public ServletConfig getServletConfig()
	{
		return this.servletConfig;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#getServletInfo()
	 */
	@Override
	public String getServletInfo()
	{
		return "";
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	@Override
	public void destroy()
	{
		// nothing
	}
}
