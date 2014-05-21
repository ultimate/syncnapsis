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
package com.syncnapsis.utils.spring;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Proxy class allowing Method delegation to a underlying Bean.
 * 
 * @author ultimate
 * @param <B> - the type of the Bean to delegate to
 */
public class BeanProxy<B>
{
	/**
	 * The Bean delegate
	 */
	protected B	delegate;

	/**
	 * Get the ApplicationContext
	 * 
	 * @param servletContext - the ServletContext
	 * @return the ApplicationContext
	 */
	protected ApplicationContext getContext(ServletContext servletContext)
	{
		return WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	}

	/**
	 * Link this class to the underlying delegate.<br>
	 * Using the given ServletContext the required WebApplicationContext is obtained and scanned for
	 * the required Bean. If beanName is null beanFallbackName will be used.
	 * 
	 * @see BeanProxy#getContext(ServletContext)
	 * @param beanName - the name of the Bean to delegate to
	 * @param beanFallbackName - the fallback name if beanName is null
	 * @param servletContext - the ServletContext to get the ApplicationContext from
	 * @return the Bean to delegate to
	 */
	@SuppressWarnings("unchecked")
	protected B delegate(String beanName, String beanFallbackName, ServletContext servletContext)
	{
		ApplicationContext ctx = getContext(servletContext);

		if(beanName == null)
			beanName = beanFallbackName;

		this.delegate = (B) ctx.getBean(beanName);
		return this.delegate;
	}

	/**
	 * The underlying delegate.
	 * 
	 * @return the Bean
	 */
	public B getDelegate()
	{
		return delegate;
	}
}
