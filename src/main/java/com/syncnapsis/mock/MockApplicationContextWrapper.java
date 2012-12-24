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
package com.syncnapsis.mock;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

public class MockApplicationContextWrapper implements ApplicationContext
{
	private ApplicationContext applicationContext;	

	public ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
	{
		this.applicationContext = applicationContext;
	}

	@Override
	public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException
	{
		return applicationContext.getAutowireCapableBeanFactory();
	}

	@Override
	public String getDisplayName()
	{
		return applicationContext.getDisplayName();
	}

	@Override
	public String getId()
	{
		return applicationContext.getId();
	}

	@Override
	public ApplicationContext getParent()
	{
		return applicationContext.getParent();
	}

	@Override
	public long getStartupDate()
	{
		return applicationContext.getStartupDate();
	}

	@Override
	public boolean containsBeanDefinition(String beanName)
	{
		return applicationContext.containsBeanDefinition(beanName);
	}

	@Override
	public int getBeanDefinitionCount()
	{
		return applicationContext.getBeanDefinitionCount();
	}

	@Override
	public String[] getBeanDefinitionNames()
	{
		return applicationContext.getBeanDefinitionNames();
	}

	@Override
	public String[] getBeanNamesForType(Class<?> type)
	{
		return applicationContext.getBeanNamesForType(type);
	}

	@Override
	public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit)
	{
		return applicationContext.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException
	{
		return applicationContext.getBeansOfType(type);
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException
	{
		return applicationContext.getBeansOfType(type, includeNonSingletons, allowEagerInit);
	}

	@Override
	public boolean containsBean(String name)
	{
		return applicationContext.containsBean(name);
	}

	@Override
	public String[] getAliases(String name)
	{
		return applicationContext.getAliases(name);
	}

	@Override
	public Object getBean(String name) throws BeansException
	{
		return applicationContext.getBean(name);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException
	{
		return applicationContext.getBean(name, requiredType);
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException
	{
		return applicationContext.getBean(name, args);
	}
	
	@Override
	public <T> T getBean(Class<T> requiredType) throws BeansException
	{
		return applicationContext.getBean(requiredType);
	}

	@Override
	public Class<?> getType(String name) throws NoSuchBeanDefinitionException
	{
		return applicationContext.getType(name);
	}

	@Override
	public boolean isPrototype(String name) throws NoSuchBeanDefinitionException
	{
		return applicationContext.isPrototype(name);
	}

	@Override
	public boolean isSingleton(String name) throws NoSuchBeanDefinitionException
	{
		return applicationContext.isSingleton(name);
	}

	@Override
	public boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException
	{
		return applicationContext.isTypeMatch(name, targetType);
	}

	@Override
	public boolean containsLocalBean(String name)
	{
		return applicationContext.containsLocalBean(name);
	}

	@Override
	public BeanFactory getParentBeanFactory()
	{
		return applicationContext.getParentBeanFactory();
	}

	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException
	{
		return applicationContext.getMessage(resolvable, locale);
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException
	{
		return applicationContext.getMessage(code, args, locale);
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale)
	{
		return applicationContext.getMessage(code, args, defaultMessage, locale);
	}

	@Override
	public void publishEvent(ApplicationEvent event)
	{
		applicationContext.publishEvent(event);
	}

	@Override
	public Resource[] getResources(String locationPattern) throws IOException
	{
		return applicationContext.getResources(locationPattern);
	}

	@Override
	public ClassLoader getClassLoader()
	{
		return applicationContext.getClassLoader();
	}

	@Override
	public Resource getResource(String location)
	{
		return applicationContext.getResource(location);
	}

	@Override
	public Environment getEnvironment()
	{
		return applicationContext.getEnvironment();
	}
	
	@Override
	public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException
	{
		return applicationContext.getBeansWithAnnotation(annotationType);
	}

	@Override
	public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType)
	{
		return applicationContext.findAnnotationOnBean(beanName, annotationType);
	}
}
