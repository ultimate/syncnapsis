package com.syncnapsis.mock;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MockFilter implements Filter
{
	private FilterConfig	filterConfig;
	private ServletRequest	lastRequest;
	private ServletResponse	lastResponse;
	private FilterChain		lastChain;

	private int destroyCalled;
	private int doFilterCalled;
	private int initCalled;
	
	public MockFilter()
	{
		resetCallCounters();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.filterConfig = filterConfig;

		initCalled++;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		this.lastRequest = request;
		this.lastResponse = response;
		this.lastChain = chain;

		doFilterCalled++;
	}

	@Override
	public void destroy()
	{
		destroyCalled++;
	}

	public FilterConfig getFilterConfig()
	{
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig)
	{
		this.filterConfig = filterConfig;
	}

	public ServletRequest getLastRequest()
	{
		return lastRequest;
	}

	public void setLastRequest(ServletRequest lastRequest)
	{
		this.lastRequest = lastRequest;
	}

	public ServletResponse getLastResponse()
	{
		return lastResponse;
	}

	public void setLastResponse(ServletResponse lastResponse)
	{
		this.lastResponse = lastResponse;
	}

	public FilterChain getLastChain()
	{
		return lastChain;
	}

	public void setLastChain(FilterChain lastChain)
	{
		this.lastChain = lastChain;
	}
	
	public int getDestroyCalled()
	{
		return destroyCalled;
	}

	public int getDoFilterCalled()
	{
		return doFilterCalled;
	}

	public int getInitCalled()
	{
		return initCalled;
	}
	
	public void resetCallCounters()
	{
		destroyCalled = 0;
		doFilterCalled = 0;
		initCalled = 0;
	}
}
