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
package com.syncnapsis.utils;

import java.io.Serializable;
import java.util.HashMap;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.syncnapsis.exceptions.ConversionException;

/**
 * Util-Klasse für den Zugriff auf die Hibernate-Session
 * 
 * @author ultimate
 */
public class HibernateUtil
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
	 * Hibernate SessionFactory singleton
	 */
	private static SessionFactory			sessionFactory;

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
		logger.debug("setting SessionFactory: " + (sessionFactory == null ? null : sessionFactory.getClass().getName()));
		if(HibernateUtil.sessionFactory != null)
			logger.warn("SessionFactory is not null but will be overwritten!");
		HibernateUtil.sessionFactory = sessionFactory;
	}

	/**
	 * The SessionFactory to be used by the HibernateUtil.
	 * If the inner SessionFactory is null a new one will be initiated by default.
	 * 
	 * @return the SessionFactory
	 */
	public SessionFactory getSessionFactory()
	{
		return initSessionFactory(null);
	}

	/**
	 * The current Session.
	 * 
	 * @see HibernateUtil#getSessionFactory()
	 * @see SessionFactory#getCurrentSession()
	 * @return the Hibernate-Session
	 */
	public static Session currentSession() throws HibernateException
	{
		return instance.getSessionFactory().getCurrentSession();
	}

	/**
	 * Closes the current Session
	 * 
	 * @see HibernateUtil#currentSession()
	 * @see Session#close()
	 */
	public static void closeSession() throws HibernateException
	{
		currentSession().close();
	}

	/**
	 * Open a Session that is bound to the {@link TransactionSynchronizationManager}.<br>
	 * The Session will be configured with {@link FlushMode#COMMIT}
	 * 
	 * @param useSessionHolder - select wether to use a SessionHolder for binding or not
	 * @return the session opened
	 */
	public static Session openBoundSession()
	{
		Session session = sessionFactory.openSession();
		session.setFlushMode(FlushMode.COMMIT);
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		return session;
	}

	/**
	 * Close the session bound to the sessionFactory.
	 * 
	 * @return true when the Session has been closed successfully, false otherwise
	 */
	public static boolean closeBoundSession()
	{
		if(!isSessionBound())
			return false;
		Object resource = TransactionSynchronizationManager.unbindResource(sessionFactory);
		if(resource instanceof Session)
		{
			((Session) resource).close();
			return true;
		}
		else if(resource instanceof SessionHolder)
		{
			((SessionHolder) resource).getSession().close();
			return true;
		}
		return false;
	}

	/**
	 * Is there a session bound for the SessionFactory?
	 * 
	 * @see TransactionSynchronizationManager#hasResource(Object)
	 * @return true or false
	 */
	public static boolean isSessionBound()
	{
		return TransactionSynchronizationManager.hasResource(sessionFactory);
	}

	/**
	 * Initialize the SessionFactory if no SessionFactory is set.
	 * 
	 * @param resource - an optional path to a Resource from which the SessionFactory will be
	 *            initialized.
	 * @see Configuration#configure(String)
	 * @see Configuration#configure()
	 * @return the newly created SessionFactory
	 */
	public SessionFactory initSessionFactory(String resource)
	{
		if(sessionFactory == null)
		{
			try
			{
				Configuration configuration;
				if(resource == null)
					configuration = new Configuration().configure();
				else
					configuration = new Configuration().configure(resource);

				ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();

				sessionFactory = configuration.buildSessionFactory(serviceRegistry);

				logger.info("Hibernate configuration file loaded: " + (resource == null ? "hibernate.cfg.xml" : resource));
			}
			catch(Throwable ex)
			{
				logger.error("Initial SessionFactory creation failed: " + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
		return sessionFactory;
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
			idTypes.put(clazz, sessionFactory.getClassMetadata(clazz).getIdentifierType().getReturnedClass());
		return idTypes.get(clazz);
	}
}
