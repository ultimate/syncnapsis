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
