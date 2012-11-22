package com.syncnapsis.websockets.engine;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * Extension of BaseWebEngine for egines using Filters
 * 
 * @author ultimate
 */
public abstract class FilterEngine extends BaseWebEngine implements Filter
{
	/**
	 * The FilterConfig object
	 */
	protected FilterConfig	filterConfig;

	/**
	 * Empty default construtor
	 */
	public FilterEngine()
	{
		super();
	}

	/**
	 * Construct a new BaseFilterEngine with a given path.
	 * 
	 * @param path - an optional path for the Filter to listen at
	 */
	public FilterEngine(String path)
	{
		super(path);
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
