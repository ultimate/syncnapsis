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
import org.springframework.util.Assert;

/**
 * Util class for extended operations on hibernate {@link SessionFactory}.<br>
 * This class is used by {@link HibernateUtil}
 * 
 * @author ultimate
 */
public class SessionFactoryUtil
{
	/**
	 * Logger-Instance
	 */
	protected static transient final Logger	logger	= LoggerFactory.getLogger(SessionFactoryUtil.class);

	/**
	 * Hibernate SessionFactory singleton
	 */
	protected SessionFactory				sessionFactory;

	/**
	 * Create a new {@link SessionFactoryUtil} with the given {@link SessionFactory}
	 * 
	 * @param sessionFactory - the {@link SessionFactory} to use
	 */
	public SessionFactoryUtil(SessionFactory sessionFactory)
	{
		super();
		Assert.notNull(sessionFactory, "sessionFactory must not be null!");
		this.sessionFactory = sessionFactory;
	}

	/**
	 * The SessionFactory used.
	 * 
	 * @return the SessionFactory
	 */
	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	/**
	 * Opens a new Session
	 * 
	 * @see SessionFactory#openSession()
	 */
	public Session openSession() throws HibernateException
	{
		return sessionFactory.openSession();
	}

	/**
	 * The current Session.
	 * 
	 * @see SessionFactory#getCurrentSession()
	 * @return the Hibernate-Session
	 */
	public Session currentSession() throws HibernateException
	{
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Closes the current Session
	 * 
	 * @see SessionFactoryUtil#currentSession()
	 * @see Session#close()
	 */
	public void closeSession() throws HibernateException
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
	public Session openBoundSession()
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
	public boolean closeBoundSession()
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
	public boolean isSessionBound()
	{
		return TransactionSynchronizationManager.hasResource(sessionFactory);
	}

	/**
	 * Create a new SessionFactory from the given resource
	 * 
	 * @param resource - an optional path to a Resource from which the SessionFactory will be
	 *            initialized.
	 * @see Configuration#configure(String)
	 * @see Configuration#configure()
	 * @return the newly created SessionFactory
	 */
	public static SessionFactory initSessionFactory(String resource)
	{
		try
		{
			Configuration configuration;
			if(resource == null)
				configuration = new Configuration().configure();
			else
				configuration = new Configuration().configure(resource);

			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();

			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			logger.info("Hibernate configuration file loaded: " + (resource == null ? "hibernate.cfg.xml" : resource));

			return sessionFactory;
		}
		catch(Throwable ex)
		{
			logger.error("SessionFactory creation failed: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}
