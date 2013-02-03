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
package com.syncnapsis.websockets.service;

import java.lang.reflect.InvocationTargetException;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import com.syncnapsis.utils.reflections.Invocation;

/**
 * This is a delegating service that opens a hibernate session before processing the message for
 * enabling the open-session-in-view design-pattern.<br>
 * Transaction and Session management copied from {@link OpenSessionInViewFilter}
 * 
 * @author ultimate
 */
public class OpenSessionInViewService extends InterceptorService implements InitializingBean
{
	/**
	 * The SessionFactory to use
	 */
	protected SessionFactory	sessionFactory;

	/**
	 * The FlushMode used for the hibernate session
	 */
	protected FlushMode			flushMode;

	/**
	 * The SessionFactory to use
	 * 
	 * @return sessionFactory
	 */
	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	/**
	 * The SessionFactory to use
	 * 
	 * @param sessionFactory - the SessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	/**
	 * The FlushMode used for the hibernate session
	 * 
	 * @return flushMode
	 */
	public FlushMode getFlushMode()
	{
		return flushMode;
	}

	/**
	 * The FlushMode used for the hibernate session
	 * 
	 * @param flushMode - the FlushMode
	 */
	public void setFlushMode(FlushMode flushMode)
	{
		this.flushMode = flushMode;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.BaseService#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(sessionFactory, "sessionFactory must not be null!");
		Assert.notNull(flushMode, "flushMode must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.interception.Interceptor#intercept(com.syncnapsis.utils.reflections.Invocation)
	 */
	@Override
	public <T> T intercept(Invocation<T> invocation) throws InvocationTargetException
	{
		boolean participate = false;

		if(TransactionSynchronizationManager.hasResource(sessionFactory))
		{
			participate = true;
		}
		else
		{
			logger.debug("Opening Hibernate Session in OpenSessionInViewService");
			Session session = openSession(sessionFactory);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}
		T result;
		try
		{
			result = invocation.invoke();
		}
		finally
		{
			if(!participate)
			{
				SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
				logger.debug("Closing Hibernate Session in OpenSessionInViewService");
				SessionFactoryUtils.closeSession(sessionHolder.getSession());
			}
		}
		return result;
	}

	/**
	 * Open a Session for the SessionFactory that this filter uses.
	 * <p>
	 * The default implementation delegates to the <code>SessionFactory.openSession</code> method
	 * and sets the <code>Session</code>'s flush mode to the requested flushMode.
	 * 
	 * @see OpenSessionInViewService#setFlushMode(FlushMode)
	 * @param sessionFactory - the SessionFactory that this filter uses
	 * @return the Session to use
	 * @throws DataAccessResourceFailureException if the Session could not be created
	 */
	protected Session openSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException
	{
		try
		{
			Session session = SessionFactoryUtils.openSession(sessionFactory);
			session.setFlushMode(flushMode);
			return session;
		}
		catch(HibernateException ex)
		{
			throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);
		}
	}
}
