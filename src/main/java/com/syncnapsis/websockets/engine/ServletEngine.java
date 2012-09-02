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
