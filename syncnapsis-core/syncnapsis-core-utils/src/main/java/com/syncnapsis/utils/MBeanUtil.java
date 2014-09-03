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
package com.syncnapsis.utils;

import java.lang.management.ManagementFactory;
import java.util.Hashtable;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util-Class that helps to add Objects to the JavaX ManagementFactory MBeanServer.
 * It offers Methods allowing easy registering without caring about the creation of the ObjectName.
 * 
 * @author ultimate
 */
public abstract class MBeanUtil
{
	/**
	 * Logger-Instance
	 */
	private static transient final Logger	logger		= LoggerFactory.getLogger(MBeanUtil.class);

	/**
	 * The PlatformMBeanServer
	 */
	private static MBeanServer				mBeanServer	= ManagementFactory.getPlatformMBeanServer();

	/**
	 * Register a Bean at the PlatformMBeanServer.
	 * A new ObjectName will be created automatically from the Object to register the Bean.
	 * 
	 * @see MBeanUtil#getDomain(Object)
	 * @see MBeanUtil#getType(Object)
	 * @see ObjectName#ObjectName(String)
	 * @see MBeanServer#registerMBean(Object, ObjectName)
	 * @param bean - the Bean to register
	 * @return the registered ObjectInstance
	 */
	public static ObjectInstance registerMBean(Object bean)
	{
		return registerMBean(bean, getDomain(bean), getType(bean), getName(bean));
	}

	/**
	 * Register a Bean at the PlatformMBeanServer.
	 * A new ObjectName will be created automatically from the given domain and type to register the
	 * Bean.
	 * See also {@link MBeanUtil#registerMBean(Object)}
	 * 
	 * @see ObjectName#ObjectName(String)
	 * @see MBeanServer#registerMBean(Object, ObjectName)
	 * @param bean - the Bean to register
	 * @param domain - the domain to register the Bean at
	 * @param type - the type to register the Bean for
	 * @param name - the name to register the Bean for
	 * @return the registered ObjectInstance
	 */
	public static ObjectInstance registerMBean(Object bean, String domain, String type, String name)
	{
		ObjectInstance objectInstance = null;
		try
		{
			Hashtable<String, String> table = new Hashtable<String, String>();
			if(type != null)
				table.put("type", type);
			if(name != null)
				table.put("name", name);
			ObjectName objectName = new ObjectName(domain, table);
			try
			{
				objectInstance = getMBeanServer().registerMBean(bean, objectName);
				logger.info("MBean registered: '" + objectName.getCanonicalName() + "'");
			}
			catch(InstanceAlreadyExistsException e)
			{
				logger.error("Unable to register '" + objectName.getCanonicalName() + "': Already registered another instance.");
			}
			catch(MBeanRegistrationException e)
			{
				logger.error("Unable to register '" + objectName.getCanonicalName() + "': Registration error: " + e.getMessage(), e);
			}
			catch(NotCompliantMBeanException e)
			{
				logger.error("Unable to register '" + objectName.getCanonicalName() + "': Bean compliance error: " + e.getMessage(), e);
			}
		}
		catch(MalformedObjectNameException e)
		{
			logger.error("Unable to register bean: Malformed object name: " + e.getMessage(), e);
		}
		catch(NullPointerException e)
		{
			logger.error("Unable to register bean: Null pointer exeception.", e);
		}
		return objectInstance;
	}

	/**
	 * Get the domain of a bean.<br>
	 * Will return the package in which the class is declared.<br>
	 * If the class is declared inside another class, the package of the enclosing class will be
	 * returned.<br>
	 * If the class or enclosing class is declared in the default package "*" will be returned<br>
	 * 
	 * @param bean - the bean
	 * @return the domain name
	 */
	public static String getDomain(Object bean)
	{
		Class<?> cls = bean.getClass();

		// for inner classes or anonymous classes
		while(cls.getEnclosingClass() != null)
			cls = cls.getEnclosingClass();

		if(cls.getPackage() != null)
		{
			// valid class in package
			return cls.getPackage().getName();
		}
		else
		{
			// valid class in default package
			return "*";
		}
	}

	/**
	 * Get the type name of a bean.<br>
	 * Will return the getSimpleName() of the class if the class is declared in an own file.<br>
	 * If the Class is declared inside another class getName() is returned.<br>
	 * 
	 * @param bean - the bean
	 * @return the type name
	 */
	public static String getType(Object bean)
	{
		if(bean.getClass().getEnclosingClass() == null)
		{
			// valid class in package or default package
			return bean.getClass().getSimpleName();
		}
		else
		{
			// inner class or anonymous class
			return bean.getClass().getName().substring(bean.getClass().getName().lastIndexOf(".")+1);
		}
	}

	/**
	 * Get the name of a bean.<br>
	 * In order to guarantee safe MBean registration even inside a running web server with multiple
	 * running applications beans of same type have to be differentiable.<br>
	 * Therefore bean name will be the unique id of the used ApplicationContext which normally will
	 * be set to the context path used inside the web server.<br>
	 * The id of the ApplicationContext is obtained via ApplicationContextUtil and will be reduced
	 * to the relevant context path without prefixing Class names.
	 * 
	 * @param bean - the bean (currently unused: for future use)
	 * @return the ApplicationContext id
	 */
	public static String getName(Object bean)
	{
		String ctxId = "";
		if(ApplicationContextUtil.getApplicationContext() != null)
		{
			ctxId = ApplicationContextUtil.getApplicationContext().getId();
			if(ctxId != null)
			{
				ctxId = ctxId.substring(ctxId.indexOf(":")+1);
			}
			else
			{
				ctxId = "/" + ApplicationContextUtil.getApplicationContext().getStartupDate();
			}
		}
		return ctxId + "@" + Integer.toHexString(bean.hashCode());
	}

	/**
	 * Get the PlatformMBeanServer
	 * 
	 * @see ManagementFactory#getPlatformMBeanServer()
	 * @return the MBeanServer
	 */
	public static MBeanServer getMBeanServer()
	{
		return mBeanServer;
	}
}
