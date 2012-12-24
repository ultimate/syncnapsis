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
package com.syncnapsis.utils.spring;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Class used to delegate a Servlet mapped in the web.xml to Bean.
 * The Name of the bean is set via the init-param "targetBean", if not specified the servlet name
 * will be used.
 * 
 * @author ultimate
 */
public class ServletToBeanProxy extends BeanProxy<Servlet> implements Servlet
{
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(final ServletConfig servletConfig) throws ServletException
	{
		delegate(servletConfig.getInitParameter("targetBean"), servletConfig.getServletName(), servletConfig.getServletContext());
		this.delegate.init(servletConfig);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#getServletConfig()
	 */
	@Override
	public ServletConfig getServletConfig()
	{
		return this.delegate.getServletConfig();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#service(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse)
	 */
	@Override
	public void service(final ServletRequest servletRequest, final ServletResponse servletResponse) throws ServletException, IOException
	{
		this.delegate.service(servletRequest, servletResponse);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#getServletInfo()
	 */
	@Override
	public String getServletInfo()
	{
		return this.delegate.getServletInfo();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	@Override
	public void destroy()
	{
		if(this.delegate != null)
		{
			this.delegate.destroy();
			this.delegate = null;
		}
	}
}
