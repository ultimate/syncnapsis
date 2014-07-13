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
package com.syncnapsis.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.syncnapsis.websockets.engine.FilterEngine;

/**
 * Filter that forwards request by rewriting the URL
 * 
 * @author ultimate
 */
public abstract class URLRewriteFilter extends FilterEngine
{
	/**
	 * Use {@link RequestDispatcher#include(ServletRequest, ServletResponse)} instead of
	 * {@link RequestDispatcher#forward(ServletRequest, ServletResponse)}
	 */
	private boolean	useInclude	= false;

	/**
	 * Use {@link RequestDispatcher#include(ServletRequest, ServletResponse)} instead of
	 * {@link RequestDispatcher#forward(ServletRequest, ServletResponse)}
	 * 
	 * @return true or false
	 */
	public boolean isUseInclude()
	{
		return useInclude;
	}

	/**
	 * Use {@link RequestDispatcher#include(ServletRequest, ServletResponse)} instead of
	 * {@link RequestDispatcher#forward(ServletRequest, ServletResponse)}
	 * 
	 * @param useInclude - true or false
	 */
	public void setUseInclude(boolean useInclude)
	{
		this.useInclude = useInclude;
	}

	/**
	 * Empty Default Constructor
	 */
	public URLRewriteFilter()
	{
		super();
	}

	/**
	 * Construct a new RedirectingFilter with a given path.
	 * 
	 * @param path - an optional path for the Filter to listen at
	 */
	public URLRewriteFilter(String path)
	{
		super(path);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		if(!(request instanceof HttpServletRequest))
		{
			throw new ServletException("Can only process HttpServletRequest");
		}
		if(!(response instanceof HttpServletResponse))
		{
			throw new ServletException("Can only process HttpServletResponse");
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String newURL = rewriteURL(httpRequest.getServletPath(), httpRequest, httpResponse);

		if(newURL != null)
		{
			RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(newURL);

			String mimeType = httpRequest.getServletContext().getMimeType(newURL);
			logger.debug("content type for file is '" + mimeType + "'");
			httpResponse.setContentType(mimeType);
			httpResponse.setCharacterEncoding("UTF-8");

			if(!useInclude)
			{
				dispatcher.forward(httpRequest, httpResponse);
			}
			else
			{
				dispatcher.include(httpRequest, httpResponse);
			}
		}
		else
		{
			logger.debug("continuing filter chain...");
			chain.doFilter(httpRequest, httpResponse);
		}

		logger.debug("content type is '" + httpResponse.getContentType() + "'");
	}

	/**
	 * Rewrite the given URL for forwarding the request
	 * 
	 * @param url - the URL to rewrite
	 * @param request - the underlying HttpServletRequest
	 * @param response - the underlying HttpServletResponse
	 * @return the rewritten URL
	 * @throws ServletException - if rewriting the URL fails
	 */
	protected abstract String rewriteURL(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException;
}
