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
