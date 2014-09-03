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
package com.syncnapsis.utils.spring;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.FileSystemResource;

/**
 * This context loader provides simplified support for creating {@link ApplicationContext}s
 * including support for adding logic to {@link ContextLoader#beforeLoadBeans()} and
 * {@link ContextLoader#afterLoadBeans()}
 * 
 * @author ultimate
 */
public class ContextLoader
{
	/**
	 * Logger-instance
	 */
	protected transient final Logger	logger		= LoggerFactory.getLogger(getClass());

	/**
	 * A counter for {@link PropertyPlaceholderConfigurer}s injected.<br>
	 * Used to generate a bean name for those.
	 */
	private int							ppcCount	= 0;

	/**
	 * The location {@link List} used for loading beans for this {@link ContextLoader}
	 */
	protected List<String>				locations;
	/**
	 * Get the {@link GenericApplicationContext} held by this context loader
	 */
	protected GenericApplicationContext	context;

	/**
	 * Create a new {@link ContextLoader} from a location {@link List}
	 * 
	 * @param locations - the location {@link List} used for loading beans for this
	 *            {@link ContextLoader}
	 */
	public ContextLoader(List<String> locations)
	{
		this.locations = Collections.unmodifiableList(locations);
		this.context = null;
	}

	/**
	 * Create a new {@link ContextLoader} from a location list as an varargs array
	 * 
	 * @param locations - the locations used for loading beans for this {@link ContextLoader} as an
	 *            varargs array
	 */
	public ContextLoader(String... locations)
	{
		this(Arrays.asList(locations));
	}

	/**
	 * The location {@link List} used for loading beans for this {@link ContextLoader}
	 * 
	 * @return the list of locations
	 */
	public List<String> getLocations()
	{
		return locations;
	}

	/**
	 * Get the {@link GenericApplicationContext} held by this context loader
	 * 
	 * @return the context
	 */
	public GenericApplicationContext getContext()
	{
		return context;
	}

	/**
	 * Method executed by {@link ContextLoader#loadContext()} <b>before</b>
	 * {@link ContextLoader#loadBeans()} is called.<br>
	 * Override this method for example if you want to inject beans prior to loading the beans from
	 * the locations.
	 */
	public void beforeLoadBeans()
	{

	}

	/**
	 * Method executed by {@link ContextLoader#loadContext()} <b>after</b>
	 * {@link ContextLoader#loadBeans()} is called.<br>
	 * Override this method for example if you want to inject beans after to loading the beans from
	 * the locations or if you want to check the application context for validity / completeness.
	 */
	public void afterLoadBeans()
	{

	}

	/**
	 * Load the {@link ApplicationContext} from the locations given on initialization with regard to
	 * {@link ContextLoader#beforeLoadBeans()} and {@link ContextLoader#afterLoadBeans()}
	 * 
	 * @return the context created
	 */
	public GenericApplicationContext loadContext()
	{
		if(this.context != null)
			throw new IllegalStateException("context already initialized!");

		this.context = new GenericApplicationContext();

		// call beforeLoadBeans() for override purposes
		beforeLoadBeans();

		// load beans from the locations
		loadBeans();

		// call afterLoadBeans() for override purposes
		afterLoadBeans();

		return getContext();
	}

	/**
	 * Closes the underlying context in order to be able to reuse this {@link ContextLoader}
	 * 
	 * @see AbstractApplicationContext#close()
	 */
	public void closeContext()
	{
		this.context.close();
		this.context = null;
	}

	/**
	 * Load the beans from the locations given when this {@link ContextLoader} was created
	 * 
	 * @see ContextLoader#ContextLoader(List)
	 * @see ContextLoader#getLocations()
	 */
	protected void loadBeans()
	{
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this.context);
		int loaded;
		if(this.locations != null)
		{
			for(String location : this.locations)
			{
				try
				{
					loaded = reader.loadBeanDefinitions(location);
					logger.debug(loaded + " bean definitions loaded: '" + location + "'");
				}
				catch(BeanDefinitionStoreException e)
				{
					logger.error("could not load bean definition: '" + location + "'", e);
				}
			}
		}
		this.context.refresh();
	}

	/**
	 * Get a specific bean from the underlying {@link ApplicationContext}
	 * 
	 * @see ApplicationContext#getBean(String, Class)
	 * @param name - the name of the bean
	 * @param type - the type of the bean
	 * @return the bean
	 */
	protected <T> T getBean(String name, Class<T> type)
	{
		return this.context.getBean(name, type);
	}

	/**
	 * Init and inject the given bean into the application context.
	 * 
	 * @see ConfigurableListableBeanFactory#initializeBean(Object, String)
	 * @see ConfigurableListableBeanFactory#registerSingleton(String, Object)
	 * 
	 * @param name - the name of the bean to inject
	 * @param bean - the bean to inject
	 */
	protected void injectBean(String name, Object bean)
	{
		ConfigurableListableBeanFactory bf = this.context.getBeanFactory();
		// init & inject bean
		bf.registerSingleton(name, bf.initializeBean(bean, name));
	}

	/**
	 * Init and inject a {@link PropertyPlaceholderConfigurer} for a {@link FileSystemResource} into
	 * the application context.<br>
	 * Calls {@link ContextLoader#injectPropertyPlaceholderConfigurer(File, boolean)} with
	 * <code>ignoreUnresolvablePlaceholders=false</code>
	 * 
	 * @param file - the file to use for the {@link FileSystemResource}
	 */
	protected void injectPropertyPlaceholderConfigurer(File file)
	{
		injectPropertyPlaceholderConfigurer(file, false);
	}

	/**
	 * Init and inject a {@link PropertyPlaceholderConfigurer} for a {@link FileSystemResource} into
	 * the application context.
	 * 
	 * @see {@link PropertyPlaceholderConfigurer#setIgnoreUnresolvablePlaceholders(boolean)}
	 * @param file - the file to use for the {@link FileSystemResource}
	 * @param ignoreUnresolvablePlaceholders - ignore unresolvable placeholders (default is false)
	 */
	protected void injectPropertyPlaceholderConfigurer(File file, boolean ignoreUnresolvablePlaceholders)
	{
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocation(new FileSystemResource(file));
		ppc.setIgnoreUnresolvablePlaceholders(ignoreUnresolvablePlaceholders);

		injectBean("ppc#" + (++ppcCount), ppc);
	}

	/**
	 * Init and inject a {@link PropertyPlaceholderConfigurer} for a given {@link Properties} into
	 * the application context.<br>
	 * Calls {@link ContextLoader#injectPropertyPlaceholderConfigurer(String, boolean)} with
	 * <code>ignoreUnresolvablePlaceholders=false</code>
	 * 
	 * @param properties - the properties to use
	 */
	protected void injectPropertyPlaceholderConfigurer(String propertiesBeanName)
	{
		injectPropertyPlaceholderConfigurer(propertiesBeanName, false);
	}

	/**
	 * Init and inject a {@link PropertyPlaceholderConfigurer} for a given {@link Properties} into
	 * the application context.
	 * 
	 * @see {@link PropertyPlaceholderConfigurer#setIgnoreUnresolvablePlaceholders(boolean)}
	 * @param properties - the properties to use
	 * @param ignoreUnresolvablePlaceholders - ignore unresolvable placeholders (default is false)
	 */
	protected void injectPropertyPlaceholderConfigurer(String propertiesBeanName, boolean ignoreUnresolvablePlaceholders)
	{
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setProperties(getBean(propertiesBeanName, Properties.class));
		ppc.setIgnoreUnresolvablePlaceholders(ignoreUnresolvablePlaceholders);

		injectBean("ppc#" + (++ppcCount), ppc);
	}

	/**
	 * Init and inject a {@link Properties} into the application context by use of
	 * {@link PropertiesFactoryBean}.
	 * 
	 * @param name - the name for the properties bean
	 * @param properties - the {@link Properties} to use
	 */
	protected void injectProperties(String name, Properties properties)
	{
		PropertiesFactoryBean bean = new PropertiesFactoryBean();
		bean.setProperties(properties);

		injectBean(name, bean);
	}
}
