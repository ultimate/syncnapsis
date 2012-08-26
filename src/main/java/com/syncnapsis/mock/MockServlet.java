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
