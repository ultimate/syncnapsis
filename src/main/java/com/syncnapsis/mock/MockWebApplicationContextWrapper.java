package com.syncnapsis.mock;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

public class MockWebApplicationContextWrapper extends MockApplicationContextWrapper implements WebApplicationContext
{
	private ServletContext servletContext;
	
	@Override
	public ServletContext getServletContext()
	{
		return servletContext;
	}
	
	public void setServletContext(ServletContext servletContext)
	{
		this.servletContext = servletContext;
	}
}
