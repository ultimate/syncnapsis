package com.syncnapsis.utils.spring;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Class used to delegate a Filter mapped in the web.xml to Bean.
 * The Name of the bean is set via the init-param "targetBean", if not specified the filter name
 * will be used.
 * 
 * @author ultimate
 */
public class FilterToBeanProxy extends BeanProxy<Filter> implements Filter
{
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		delegate(filterConfig.getInitParameter("targetBean"), filterConfig.getFilterName(), filterConfig.getServletContext());
		this.delegate.init(filterConfig);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		this.delegate.doFilter(request, response, chain);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
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
