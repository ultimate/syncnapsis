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
package com.syncnapsis.mock;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MockServlet implements Servlet
{
	private ServletConfig	servletConfig;
	private ServletRequest	lastRequest;
	private ServletResponse	lastResponse;

	@Override
	public void init(ServletConfig config) throws ServletException
	{
		this.servletConfig = config;
	}

	@Override
	public ServletConfig getServletConfig()
	{
		return this.servletConfig;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException
	{
		this.lastRequest = req;
		this.lastResponse = res;
	}

	@Override
	public String getServletInfo()
	{
		return "MockServlet";
	}

	@Override
	public void destroy()
	{
	}

	public ServletRequest getLastRequest()
	{
		return lastRequest;
	}

	public ServletResponse getLastResponse()
	{
		return lastResponse;
	}

}
