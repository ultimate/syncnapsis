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

import java.io.Serializable;
import java.util.HashMap;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.exceptions.ConversionException;

/**
 * Util class for static access to the {@link SessionFactoryUtil} and the underlying
 * {@link SessionFactory}
 * 
 * @author ultimate
 */
public class HibernateUtil implements InitializingBean
{
	/**
	 * Logger-Instance
	 */
	private static transient final Logger	logger		= LoggerFactory.getLogger(HibernateUtil.class);

	/**
	 * The local HibernateUtil
	 */
	private static HibernateUtil			instance	= new HibernateUtil();

	/**
	 * Hibernate SessionFactoryUtil singleton
	 */
	private static SessionFactoryUtil		sessionFactoryUtil;

	/**
	 * Only allow creation via getInstance
	 */
	private HibernateUtil()
	{

	}

	/**
	 * Get the singleton instance allowing access to this Util
	 * 
	 * @return the HibernateUtil
	 */
	public static HibernateUtil getInstance()
	{
		return instance;
	}

	/**
	 * The SessionFactory to be used by the HibernateUtil
	 * 
	 * @param sessionFactory - the SessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		logger.debug("setting SessionFactoryUtil for: " + (sessionFactory == null ? null : sessionFactory.getClass().getName()));
		if(HibernateUtil.sessionFactoryUtil != null)
			logger.warn("SessionFactoryUtil is not null but will be overwritten!");
		HibernateUtil.sessionFactoryUtil = new SessionFactoryUtil(sessionFactory);
	}

	/**
	 * Directly set the SessionFactoryUtil to be used by the HibernateUtil
	 * 
	 * @param sessionFactoryUtil - the SessionFactoryUtil
	 */
	public void setSessionFactoryUtil(SessionFactoryUtil sessionFactoryUtil)
	{
		logger.debug("setting SessionFactoryUtil...");
		if(HibernateUtil.sessionFactoryUtil != null)
			logger.warn("SessionFactoryUtil is not null but will be overwritten!");
		HibernateUtil.sessionFactoryUtil = sessionFactoryUtil;
	}

	/**
	 * The SessionFactoryUtil used by the HibernateUtil.
	 * 
	 * @return the SessionFactory
	 */
	public SessionFactoryUtil getSessionFactoryUtil()
	{
		return sessionFactoryUtil;
	}

	/**
	 * The SessionFactory to be used by the HibernateUtil.
	 * 
	 * @return the SessionFactory
	 */
	public SessionFactory getSessionFactory()
	{
		return getSessionFactoryUtil().getSessionFactory();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(sessionFactoryUtil, "sessionFactoryUtil must not be null!");
	}

	/**
	 * Static access to {@link SessionFactoryUtil#currentSession()}
	 * 
	 * @return the Hibernate-Session
	 */
	public static Session currentSession() throws HibernateException
	{
		return getInstance().getSessionFactoryUtil().currentSession();
	}

	/**
	 * Static access to {@link SessionFactoryUtil#closeSession()}
	 * 
	 * @see Session#close()
	 */
	public static void closeSession() throws HibernateException
	{
		getInstance().getSessionFactoryUtil().closeSession();
	}

	/**
	 * Static access to {@link SessionFactoryUtil#openBoundSession()}
	 * 
	 * @return the session opened
	 */
	public static Session openBoundSession()
	{
		return getInstance().getSessionFactoryUtil().openBoundSession();
	}

	/**
	 * Static access to {@link SessionFactoryUtil#closeBoundSession()}
	 * 
	 * @return true when the Session has been closed successfully, false otherwise
	 */
	public static boolean closeBoundSession()
	{
		return getInstance().getSessionFactoryUtil().closeBoundSession();
	}

	/**
	 * Static access to {@link SessionFactoryUtil#isSessionBound()}
	 * 
	 * @return true or false
	 */
	public static boolean isSessionBound()
	{
		return getInstance().getSessionFactoryUtil().isSessionBound();
	}

	/**
	 * @see SessionFactoryUtil#initSessionFactory(String)
	 */
	@Deprecated
	public static SessionFactory initSessionFactory(String resource)
	{
		return SessionFactoryUtil.initSessionFactory(resource);
	}

	/**
	 * The map caching the id types for all used model classes.
	 */
	private static HashMap<Class<?>, Class<? extends Serializable>>	idTypes	= new HashMap<Class<?>, Class<? extends Serializable>>();

	/**
	 * Check an id for a required type and convert it if necessary.<br>
	 * This check is helpful before calling {@link Session#get(Class, Serializable)} from hibernate,
	 * since hibernate will perform a id check as well, which may fail for some combinations which
	 * are not really expected to fail (e.g. Integer vs. Long).
	 * 
	 * @see ReflectionsUtil#convert(Class, Object)
	 * @param clazz - the model type (used to determine the required id
	 * @param id - the given id
	 * @return the converted id
	 * @throws ConversionException if conversion fails
	 */
	public static Serializable checkIdType(Class<?> clazz, Serializable id)
	{
		Class<? extends Serializable> requiredType = getIdType(clazz);
		if(requiredType.isInstance(id))
			return id;
		try
		{
			return (Serializable) ReflectionsUtil.convert(requiredType, id);
		}
		catch(ConversionException e)
		{
			logger.error(e.getMessage());
			return id;
		}
	}

	/**
	 * Get the ID type for an entity class
	 * 
	 * @param clazz - the entity class
	 * @return the ID type
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends Serializable> getIdType(Class<?> clazz)
	{
		if(!idTypes.containsKey(clazz))
			idTypes.put(clazz, sessionFactoryUtil.getSessionFactory().getClassMetadata(clazz).getIdentifierType().getReturnedClass());
		return idTypes.get(clazz);
	}
}
