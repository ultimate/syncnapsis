package com.syncnapsis.http;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * Abstract Base for Servlets holding the ServletConfig
 * 
 * @author ultimate
 */
public abstract class ServletBase implements Servlet
{
	/**
	 * The ServletConfig object
	 */
	protected ServletConfig	servletConfig;

	/**
	 * Empty default construtor
	 */
	public ServletBase()
	{
		super();
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
