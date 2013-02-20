/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Util-Class the allows easy access to the ApplicationContext.<br>
 * Using this class the type-safe access to Beans is more simple.<br>
 * Following additional features are also offered:<br>
 * - autowiring of properties of any object using the ApplicationContext<br>
 * - creating a new ApplicationContext from config<br>
 * 
 * @author ultimate
 */
public class ApplicationContextUtil implements ApplicationContextAware
{
	/**
	 * Logger-Instance
	 */
	private static transient final Logger	logger						= LoggerFactory.getLogger(ApplicationContextUtil.class);

	/**
	 * The standard set of xml-Files used to build the ApplicationContext represented by the
	 * patterns:<br>
	 * <code>classpath*:/ctx-**.xml</code>
	 */
	public static final String				CONTEXT_LOCATION_ALL		= "classpath*:/ctx-**.xml";

	/**
	 * The standard set of xml-Files used to build the ApplicationContext represented by the
	 * patterns:<br>
	 * <code>classpath*:/ctx-default.xml</code>
	 */
	public static final String				CONTEXT_LOCATION_DEFAULT	= "classpath*:/ctx-default.xml";

	/**
	 * The standard set of xml-Files used to build the ApplicationContext represented by the
	 * patterns:<br>
	 * <code>classpath:/ctx-test.xml</code>
	 */
	public static final String				CONTEXT_LOCATION_TEST		= "classpath:/ctx-test.xml";

	/**
	 * The local ApplicationContextUtil
	 */
	private static ApplicationContextUtil	instance					= new ApplicationContextUtil();

	/**
	 * The ApplicationContext
	 */
	private static ApplicationContext		applicationContext;

	/**
	 * Only allow creation via getInstance
	 */
	private ApplicationContextUtil()
	{
		// nothing
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework
	 * .context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException
	{
		ApplicationContextUtil.applicationContext = ctx;
	}

	/**
	 * Get the singleton instance allowing access to this Util
	 * 
	 * @return the ApplicationContextUtil
	 */
	public static ApplicationContextUtil getInstance()
	{
		return instance;
	}

	/**
	 * The held ApplicationContext
	 * 
	 * @return the ApplicationContext
	 */
	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	/**
	 * Gets a Bean by the given ClassName from the ApplicationContext.
	 * 
	 * @see ApplicationContextUtil#getBean(Class)
	 * @param <T> - the type of the Bean
	 * @param cls - the class of the Bean
	 * @return the Bean
	 */
	public static <T> T getBean(Class<T> cls)
	{
		return getBean(getApplicationContext(), cls);
	}

	/**
	 * Gets a Bean by the given name from the ApplicationContext.
	 * 
	 * @see ApplicationContextUtil#getBean(ApplicationContext, String)
	 * @param name - the name of the Bean
	 * @return the Bean
	 */
	public static Object getBean(String name)
	{
		return getBean(getApplicationContext(), name);
	}

	/**
	 * Gets a Bean by the given name and class from the ApplicationContext
	 * 
	 * @see ApplicationContextUtil#getBean(ApplicationContext, Class)
	 * @param name - the name of the Bean
	 * @param cls - the class of the Bean
	 * @return the Bean
	 */
	public static <T> T getBean(String name, Class<T> cls)
	{
		return getBean(getApplicationContext(), name, cls);
	}

	/**
	 * Gets a Bean by it's class from the ApplicationContext.
	 * E.g.: UserManager userManager = ApplicationContextUtil.getBean(UserManager.class);
	 * alternatively to:
	 * UserManager userManager = (UserManager) ApplicationContextUtil.getBean("userManager");
	 * 
	 * @param <T> - the type of the Bean
	 * @param ctx - the ApplicationContext to use
	 * @param cls - the class of the Bean
	 * @return the Bean
	 */
	public static <T> T getBean(ApplicationContext ctx, Class<T> cls)
	{
		String beanName = Character.toLowerCase(cls.getSimpleName().charAt(0)) + cls.getSimpleName().substring(1);
		T o = getBean(ctx, beanName, cls);
		if(o != null)
			return o;
		// standard name conventions did not match: e.g. GUIManager --> guiManager
		// try other cases of upper and lower case writing by simply ignoring case
		for(String name : ctx.getBeanDefinitionNames())
		{
			if(name.equalsIgnoreCase(cls.getSimpleName()))
				return getBean(ctx, name, cls);
		}
		return null;
	}

	/**
	 * Gets a Bean by it's name from the ApplicationContext.
	 * E.g.: UserManager userManager = (UserManager) ApplicationContextUtil.getBean("userManager");
	 * 
	 * @param ctx - the ApplicationContext to use
	 * @param name - the name of the Bean
	 * @return the Bean
	 */
	public static Object getBean(ApplicationContext ctx, String name)
	{
		Object o = null;
		try
		{
			o = ctx.getBean(name);
		}
		catch(NoSuchBeanDefinitionException ex)
		{
			// ignore
		}
		return o;
	}

	/**
	 * Gets a Bean by it's name and class from the ApplicationContext
	 * Bsp: UserManager userManager = (UserManager) ApplicationContextUtil.getBean(ctx,
	 * "userManager", UserManagerImpl.class);
	 * 
	 * @param ctx - the ApplicationContext to use
	 * @param name - the name of the Bean
	 * @param cls - the class of the Bean
	 * @return the Bean
	 */
	public static <T> T getBean(ApplicationContext ctx, String name, Class<T> cls)
	{
		T o = null;
		try
		{
			o = ctx.getBean(name, cls);
		}
		catch(NoSuchBeanDefinitionException ex)
		{
			// ignore
		}
		return o;
	}

	/**
	 * Returns the name of the given bean, if the bean is found in the ApplicationContext, otherwise
	 * returns null.
	 * If the bean is registered with multiple names the original bean name will be returned
	 * 
	 * @param bean - the Bean to determine the name for
	 * @return the name of the bean
	 */
	public static <T> String getBeanName(T bean)
	{
		if(bean == null)
			throw new IllegalArgumentException("bean must not be null!");
		String[] names = applicationContext.getBeanNamesForType(bean.getClass());
		for(String name : names)
		{
			if(getBean(name).equals(bean))
				return name;
		}
		return null;
	}

	/**
	 * Initialize an Object with the given ApplicationContext.<br>
	 * Calls {@link ApplicationContextUtil#autowire(ApplicationContext, Object, Class)} for each
	 * super Class.
	 * 
	 * @see ApplicationContextUtil#autowire(ApplicationContext, Object, Class)
	 * @param ctx - the ApplicationContext to use
	 * @param o - the Object to initialize
	 */
	public static <T> void autowire(ApplicationContext ctx, T o)
	{
		logger.trace("autowiring: " + o);
		@SuppressWarnings("unchecked")
		Class<? super T> c = (Class<? super T>) o.getClass();
		while(!c.equals(Object.class))
		{
			autowire(ctx, o, c);
			c = c.getSuperclass();
		}
	}

	/**
	 * Initialize an Object of the Type cls with the given ApplicationContext ctx.<br>
	 * For all Fields and set-Methods of the Objects given Type (including private) the
	 * ApplicationContext will be checked for Beans with matchin names. If a Bean is found the
	 * according Field or Method will be initiated with the according Bean via Reflections.
	 * 
	 * @param ctx - the ApplicationContext to use
	 * @param o - the Object to initialize
	 * @param cls - the Object Type used to determine the Fields and Methods
	 */
	public static <T> void autowire(ApplicationContext ctx, T o, Class<? super T> cls)
	{
		boolean fieldDone;
		for(Field f : cls.getDeclaredFields())
		{
			fieldDone = false;
			for(String name : ctx.getBeanNamesForType(f.getType()))
			{
				if(name.equals(f.getName()))
				{
					boolean old = f.isAccessible();
					try
					{
						ReflectionsUtil.setAccessible(f, true);
						f.set(o, ctx.getBean(name));
						logger.trace("set property by field: " + f.getName() + " -> " + ctx.getBean(name));
						fieldDone = true;
						break;
					}
					catch(BeansException e)
					{
					}
					catch(IllegalArgumentException e)
					{
					}
					catch(IllegalAccessException e)
					{
					}
					finally
					{
						ReflectionsUtil.setAccessible(f, old);
					}
				}
			}
			if(!fieldDone)
				logger.trace("could not set property by field: " + f.getName());
		}
		boolean methodDone;
		for(Method m : cls.getDeclaredMethods())
		{
			methodDone = false;
			if(!m.getName().startsWith("set"))
				continue;
			if(m.getParameterTypes() == null || m.getParameterTypes().length != 1)
				continue;
			String propertyName = Character.toUpperCase(m.getName().charAt(3)) + m.getName().substring(4);
			for(String name : ctx.getBeanNamesForType(m.getParameterTypes()[0]))
			{
				if(name.equals(propertyName))
				{
					boolean old = m.isAccessible();
					try
					{
						ReflectionsUtil.setAccessible(m, true);
						m.invoke(o, ctx.getBean(name));
						logger.trace("set property by method: " + m.getName() + " -> " + ctx.getBean(name));
						methodDone = true;
						break;
					}
					catch(BeansException e)
					{
					}
					catch(IllegalAccessException e)
					{
					}
					catch(IllegalArgumentException e)
					{
					}
					catch(InvocationTargetException e)
					{
					}
					finally
					{
						ReflectionsUtil.setAccessible(m, old);
					}
				}
			}
			if(!methodDone)
				logger.trace("could not set property by method: " + m.getName());
		}
	}

	/**
	 * Create a new ApplicationContext from the given Config-Locations.
	 * If default Config-Locations are used, getDefaultConfigLocations() can be used here.
	 * 
	 * @see ApplicationContextUtil#getDefaultConfigLocations()
	 * @param locations - the Config-Locations
	 * @return the new ApplicationContext
	 */
	public static ConfigurableApplicationContext createApplicationContext(String... locations)
	{
		GenericApplicationContext context = new GenericApplicationContext();
		// GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
		int loaded;
		for(String location : locations)
		{
			try
			{
				loaded = reader.loadBeanDefinitions(location);
				logger.debug(loaded + " bean definitions loaded: '" + location + "'");
			}
			catch(BeanDefinitionStoreException e)
			{
				logger.error("could not load bean definition: '" + location + "'");
			}
		}
		context.refresh();
		// ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
		return context;
	}
}
