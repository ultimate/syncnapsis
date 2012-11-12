package com.syncnapsis.http;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * Abstract Base for Filters holding the FilterConfig
 * 
 * @author ultimate
 */
public abstract class FilterBase implements Filter
{
	/**
	 * The FilterConfig object
	 */
	protected FilterConfig	filterConfig;

	/**
	 * Empty default construtor
	 */
	public FilterBase()
	{
		super();
	}

	/**
	 * Construct a new basic Filter
	 */
	public FilterBase(String path)
	{
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.filterConfig = filterConfig;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy()
	{
		// nothing
	}
}
