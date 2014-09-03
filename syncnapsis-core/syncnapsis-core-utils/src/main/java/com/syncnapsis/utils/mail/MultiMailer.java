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
package com.syncnapsis.utils.mail;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.syncnapsis.utils.PropertiesUtil;

/**
 * This class is a special {@link Mailer} offering support for multiple Mailers in one each
 * associated with a specified key. Actually this class is not an extension of Mailer and only
 * offers access to the underlying mailers but not real mailing functions.<br>
 * <br>
 * Using the given javax.mail-properties this {@link MultiMailer} will instantiate all required
 * underlying mailers in the following way:
 * <ul>
 * <li>for each entry of
 * <code>mailer.<i>key</i>=<i>name-of-the-individual-properties</i> an underlying Mailer will be created using {@link MultiMailer#createMailer(Object)}
 * </li>
 * </ul>
 * 
 * @author ultimate
 */
public class MultiMailer<M extends Mailer>
{
	/**
	 * The prefix for the list of underlying mailers
	 */
	protected static final String		KEY_PREFIX	= "mailer.";
	/**
	 * The key for defining the default mailer
	 */
	protected static final String		KEY_DEFAULT	= "mailer.default";

	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger		= LoggerFactory.getLogger(getClass());

	/**
	 * The map of key associated mailers
	 */
	protected Map<String, M>			mailers;

	/**
	 * The Class used to instantiate the Mailers
	 */
	protected Class<? extends M>		mailerClass;

	/**
	 * The default key for this MultiMailer
	 */
	protected String					defaultKey;

	/**
	 * The javax.mail properties
	 */
	protected Properties				properties;

	private M							all;

	/**
	 * Construct a new MultiMailer and instatiate all required Mailers
	 * 
	 * @param mailerClass - The Class used to instantiate the Mailers
	 * @param propertiesFile - the properties-file
	 * @throws IOException if loading the properties fails
	 */
	public MultiMailer(Class<? extends M> mailerClass, String propertiesFile) throws IOException
	{
		this(mailerClass, PropertiesUtil.loadProperties(propertiesFile));
	}

	/**
	 * Construct a new MultiMailer and instatiate all required Mailers
	 * 
	 * @param mailerClass - The Class used to instantiate the Mailers
	 * @param propertiesFile - the properties-file
	 * @throws IOException if loading the properties fails
	 */
	public MultiMailer(Class<? extends M> mailerClass, File propertiesFile) throws IOException
	{
		this(mailerClass, PropertiesUtil.loadProperties(propertiesFile));
	}

	/**
	 * Construct a new MultiMailer and instatiate all required Mailers
	 * 
	 * @param mailerClass - The Class used to instantiate the Mailers
	 * @param properties - The Properties
	 */
	public MultiMailer(Class<? extends M> mailerClass, Properties properties)
	{
		this.mailerClass = mailerClass;
		this.properties = properties;
		this.mailers = new HashMap<String, M>();
		createMailers();
	}

	protected void createMailers()
	{
		String key;
		String configValue;
		M mailer;
		for(String property : properties.stringPropertyNames())
		{
			if(KEY_DEFAULT.equals(property))
			{
				logger.debug("setting default key: " + properties.getProperty(property));
				setDefaultKey(properties.getProperty(property));
			}
			else if(property.startsWith(KEY_PREFIX))
			{
				key = property.substring(KEY_PREFIX.length());
				configValue = properties.getProperty(property);
				logger.debug("loading mailer for key '" + key + "' with config value '" + configValue + "'");

				try
				{
					mailer = createMailer(key, configValue);
					if(checkMailer(key, mailer))
					{
						set(key, mailer);
					}
					else
					{
						logger.warn("mailer '" + key + "' did not pass the check and will be ignored (and not be available for usage)");
					}
				}
				catch(IOException e)
				{
					logger.error("could not create mailer for key '" + key + "'", e);
				}
				catch(InstantiationException e)
				{
					logger.error("could not create mailer for key '" + key + "'", e);
				}
				catch(IllegalAccessException e)
				{
					logger.error("could not create mailer for key '" + key + "'", e);
				}
				catch(IllegalArgumentException e)
				{
					logger.error("could not create mailer for key '" + key + "'", e);
				}
				catch(SecurityException e)
				{
					logger.error("could not create mailer for key '" + key + "'", e);
				}
				catch(InvocationTargetException e)
				{
					logger.error("could not create mailer for key '" + key + "'", e);
				}
			}
		}
	}

	/**
	 * Create a new Mailer for the given key.<br>
	 * When not overwritten this method will return a new instance of the type
	 * {@link MultiMailer#mailerClass} using the constructor that matches the parameter types
	 * returned by {@link MultiMailer}
	 * 
	 * @param key - the key to create the Mailer for
	 * @param properties - the Properties for the Mailer
	 * @return the new Mailer
	 * @throws IllegalAccessException - if accessing the constructor fails
	 * @throws InstantiationException - if instantiation fails
	 * @throws InvocationTargetException - if the constructor throws an exception
	 * @throws SecurityException - if a security manager denies access
	 * @throws IOException
	 */
	protected M createMailer(String key, String configValue) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			SecurityException, InvocationTargetException, IOException
	{
		try
		{
			return this.mailerClass.getConstructor(getMailerContructorClasses()).newInstance(
					getMailerContructorArgs(key, getMailerProperties(key, configValue)));
		}
		catch(NoSuchMethodException e)
		{
			logger.warn("no constructor with Properties found", e);
			return this.mailerClass.newInstance();
		}
	}

	/**
	 * Check the given Mailer during initialization.<br>
	 * <br>
	 * This method may validate if the mailer is configured properly (e. g. if all settings have
	 * been made through the properties).<br>
	 * <br>
	 * By default true is returned.<br>
	 * <br>
	 * If false is returned {@link MultiMailer#createMailers()} will ignore this Mailer and not add
	 * it to the underlying map of mailers.
	 * 
	 * @param key - the key for the mailer to check
	 * @param mailer - the mailer to check
	 * @return true if the mailer is valid, false otherwise
	 */
	protected boolean checkMailer(String key, M mailer)
	{
		return true;
	}

	/**
	 * Get the parameter types to identify the constructor to use in
	 * {@link MultiMailer#createMailer(String, Properties)}.<br>
	 * <br>
	 * By default this method will return<br>
	 * <code> new Class<?>[] { Properties.class } </code>
	 * 
	 * @return the parameter types
	 */
	protected Class<?>[] getMailerContructorClasses()
	{
		return new Class<?>[] { Properties.class };
	}

	/**
	 * Get the arguments to pass to the constructor for instantiation in
	 * {@link MultiMailer#createMailer(String, Properties)}.<br>
	 * <br>
	 * To be able to construct different mailers for the keys the key the mailer will be created for
	 * and the properties loaded for this mailer are passed.<br>
	 * <br>
	 * By default this class will return<br>
	 * <code> new Object[] { properties } </code>
	 * 
	 * @param key - the key for the mailer to create
	 * @param properties - the properties loaded and initialized for the mailer to create
	 * @return the arguments to pass to the constructor
	 */
	protected Object[] getMailerContructorArgs(String key, Properties properties)
	{
		return new Object[] { properties };
	}

	/**
	 * Get the properties for the mailer with a given key and the config value defined in the global
	 * properties for this multi mailer.<br>
	 * <br>
	 * By default a new Properties Object will be loaded from resources defined by the config value
	 * with the global properties as it's parent:<br>
	 * <br>
	 * <code>
	 * &nbsp;&nbsp;&nbsp;&nbsp;Properties mailerProperties = new Properties(properties);<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;mailerProperties.putAll(PropertiesUtil.loadProperties(configValue));<br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;return mailerProperties;<br>
	 * </code>
	 * 
	 * @param key - the key for the mailer to get the properties for
	 * @param configName - the config value defined in the global properties
	 * @return
	 * @throws IOException
	 */
	protected Properties getMailerProperties(String key, String configValue) throws IOException
	{
		Properties mailerProperties = new Properties(properties);
		mailerProperties.putAll(PropertiesUtil.loadProperties(configValue));
		return mailerProperties;
	}

	/**
	 * The Class used to instantiate the Mailers
	 * 
	 * @return mailerClass
	 */
	public Class<? extends M> getMailerClass()
	{
		return mailerClass;
	}

	/**
	 * Get the Mailer associated with the given key
	 * 
	 * @param key - the key to get the Mailer for
	 * @return the Mailer
	 */
	public M get(String key)
	{
		if(mailers.containsKey(key))
			return mailers.get(key);
		return getDefault();
	}

	/**
	 * Get the Mailer associated with the default key
	 * 
	 * @return the default Mailer
	 */
	public M getDefault()
	{
		return mailers.get(defaultKey);
	}

	/**
	 * Associate a Mailer with the given key
	 * 
	 * @param key - the key to set the Mailer for
	 * @param mailer - the Mailer to set
	 */
	protected void set(String key, M mailer)
	{
		mailers.put(key, mailer);
	}

	/**
	 * The default key for this MultiMailer
	 * 
	 * @return defaultKey
	 */
	public String getDefaultKey()
	{
		return defaultKey;
	}

	/**
	 * The default key for this MultiMailer
	 * 
	 * @param key - the defaultKey
	 */
	public void setDefaultKey(String defaultKey)
	{
		Assert.notNull(defaultKey, "defaultKey must not be null!");
		this.defaultKey = defaultKey;
	}

	/**
	 * Get all available keys for this multimailer.
	 * 
	 * @return the set of keys
	 */
	public Set<String> getKeys()
	{
		return Collections.unmodifiableSet(mailers.keySet());
	}

	/**
	 * Return a Mailer-Proxy representing <b>all</b> underlying Mailers.<br>
	 * Every method called on this Mailer-Proxy will be forwarded to <b>all</b> underlying Mailers.
	 * Since this will result in multiple executions of the same method but on different mailers,
	 * use this method with care. It is designed for configuring all Mailers at once and <b>not</b>
	 * for sending mails.<br>
	 * If the method called for the Mailer-Proxy is not void the result of invoking the method for
	 * the default-mailer will be returned. (The method will be invoked for the default mailer
	 * first.)
	 * 
	 * @return the 'all-Mailer'-Proxy
	 */
	@SuppressWarnings("unchecked")
	public M all()
	{
		if(all == null)
		{
			try
			{
				ProxyFactory factory = new ProxyFactory();
				factory.setSuperclass(mailerClass);
				factory.setInterfaces(mailerClass.getInterfaces());
				all = (M) factory.create(getMailerContructorClasses(), getMailerContructorArgs(getDefaultKey(), properties), new MethodHandler() {
					/*
					 * (non-Javadoc)
					 * @see javassist.util.proxy.MethodHandler#invoke(java.lang.Object,
					 * java.lang.reflect.Method, java.lang.reflect.Method, java.lang.Object[])
					 */
					@Override
					public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable
					{
						Set<String> keys = new HashSet<String>(getKeys());
						keys.remove(getDefaultKey());

						Object result = thisMethod.invoke(getDefault(), args);
						for(String key : keys)
						{
							thisMethod.invoke(get(key), args);
						}
						return result;
					}
				});
			}
			catch(InstantiationException e)
			{
				logger.error("could not create 'all-Mailer'-Proxy", e);
			}
			catch(IllegalArgumentException e)
			{
				logger.error("could not create 'all-Mailer'-Proxy", e);
			}
			catch(NoSuchMethodException e)
			{
				logger.error("could not create 'all-Mailer'-Proxy", e);
			}
			catch(IllegalAccessException e)
			{
				logger.error("could not create 'all-Mailer'-Proxy", e);
			}
			catch(InvocationTargetException e)
			{
				logger.error("could not create 'all-Mailer'-Proxy", e);
			}
		}
		return all;
	}
}
