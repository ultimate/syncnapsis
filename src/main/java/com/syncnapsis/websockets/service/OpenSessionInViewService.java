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

import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.Message;
import com.syncnapsis.websockets.Service;

/**
 * This is a delegating service that opens a hibernate session before processing the message for
 * enabling the open-session-in-view design-pattern.<br>
 * Transaction and Session management copied from {@link OpenSessionInViewFilter}
 * 
 * @author ultimate
 */
public class OpenSessionInViewService extends BaseService implements InitializingBean
{
	/**
	 * The Service to delegate the messages to
	 */
	protected Service			delegate;

	/**
	 * The SessionFactory to use
	 */
	protected SessionFactory	sessionFactory;

	/**
	 * The FlushMode used for the hibernate session
	 */
	protected FlushMode			flushMode;

	/**
	 * The Service to delegate the messages to
	 * 
	 * @return delegate
	 */
	public Service getDelegate()
	{
		return delegate;
	}

	/**
	 * The Service to delegate the messages to
	 * 
	 * @param delegate - the Service
	 */
	public void setDelegate(Service delegate)
	{
		this.delegate = delegate;
	}

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
		Assert.notNull(delegate, "delegate must not be null!");
		Assert.notNull(sessionFactory, "sessionFactory must not be null!");
		Assert.notNull(flushMode, "flushMode must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onOpen(com.syncnapsis.websockets.Connection)
	 */
	@Override
	public void onOpen(Connection connection)
	{
		boolean participate = false;

		if(TransactionSynchronizationManager.hasResource(sessionFactory))
		{
			participate = true;
		}
		else
		{
			logger.debug("Opening Hibernate Session in OpenSessionInViewFilter");
			Session session = openSession(sessionFactory);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}

		try
		{
			delegate.onOpen(connection);
		}
		finally
		{
			if(!participate)
			{
				SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
				logger.debug("Closing Hibernate Session in OpenSessionInViewFilter");
				SessionFactoryUtils.closeSession(sessionHolder.getSession());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onClose(com.syncnapsis.websockets.Connection,
	 * int, java.lang.String)
	 */
	@Override
	public void onClose(Connection connection, int closeCode, String message)
	{
		boolean participate = false;

		if(TransactionSynchronizationManager.hasResource(sessionFactory))
		{
			participate = true;
		}
		else
		{
			logger.debug("Opening Hibernate Session in OpenSessionInViewFilter");
			Session session = openSession(sessionFactory);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}

		try
		{
			delegate.onClose(connection, closeCode, message);
		}
		finally
		{
			if(!participate)
			{
				SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
				logger.debug("Closing Hibernate Session in OpenSessionInViewFilter");
				SessionFactoryUtils.closeSession(sessionHolder.getSession());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onHandshake(com.syncnapsis.websockets.Connection
	 * )
	 */
	@Override
	public void onHandshake(Connection connection)
	{
		boolean participate = false;

		if(TransactionSynchronizationManager.hasResource(sessionFactory))
		{
			participate = true;
		}
		else
		{
			logger.debug("Opening Hibernate Session in OpenSessionInViewFilter");
			Session session = openSession(sessionFactory);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}

		try
		{
			delegate.onHandshake(connection);
		}
		finally
		{
			if(!participate)
			{
				SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
				logger.debug("Closing Hibernate Session in OpenSessionInViewFilter");
				SessionFactoryUtils.closeSession(sessionHolder.getSession());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onMessage(com.syncnapsis.websockets.Connection,
	 * java.lang.String)
	 */
	@Override
	public void onMessage(Connection connection, String data)
	{
		boolean participate = false;

		if(TransactionSynchronizationManager.hasResource(sessionFactory))
		{
			participate = true;
		}
		else
		{
			logger.debug("Opening Hibernate Session in OpenSessionInViewFilter");
			Session session = openSession(sessionFactory);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}

		try
		{
			delegate.onMessage(connection, data);
		}
		finally
		{
			if(!participate)
			{
				SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
				logger.debug("Closing Hibernate Session in OpenSessionInViewFilter");
				SessionFactoryUtils.closeSession(sessionHolder.getSession());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onMessage(com.syncnapsis.websockets.Connection,
	 * byte[], int, int)
	 */
	@Override
	public void onMessage(Connection connection, byte[] data, int offset, int length)
	{
		boolean participate = false;

		if(TransactionSynchronizationManager.hasResource(sessionFactory))
		{
			participate = true;
		}
		else
		{
			logger.debug("Opening Hibernate Session in OpenSessionInViewFilter");
			Session session = openSession(sessionFactory);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}

		try
		{
			delegate.onMessage(connection, data, offset, length);
		}
		finally
		{
			if(!participate)
			{
				SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
				logger.debug("Closing Hibernate Session in OpenSessionInViewFilter");
				SessionFactoryUtils.closeSession(sessionHolder.getSession());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onMessage(com.syncnapsis.websockets.Connection,
	 * com.syncnapsis.websockets.Message)
	 */
	@Override
	public void onMessage(Connection connection, Message message)
	{
		boolean participate = false;

		if(TransactionSynchronizationManager.hasResource(sessionFactory))
		{
			participate = true;
		}
		else
		{
			logger.debug("Opening Hibernate Session in OpenSessionInViewFilter");
			Session session = openSession(sessionFactory);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}

		try
		{
			delegate.onMessage(connection, message);
		}
		finally
		{
			if(!participate)
			{
				SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
				logger.debug("Closing Hibernate Session in OpenSessionInViewFilter");
				SessionFactoryUtils.closeSession(sessionHolder.getSession());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onControl(com.syncnapsis.websockets.Connection,
	 * byte, byte[], int, int)
	 */
	@Override
	public boolean onControl(Connection connection, byte controlCode, byte[] data, int offset, int length)
	{
		boolean participate = false;

		if(TransactionSynchronizationManager.hasResource(sessionFactory))
		{
			participate = true;
		}
		else
		{
			logger.debug("Opening Hibernate Session in OpenSessionInViewFilter");
			Session session = openSession(sessionFactory);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}
		boolean result;
		try
		{
			result = delegate.onControl(connection, controlCode, data, offset, length);
		}
		finally
		{
			if(!participate)
			{
				SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
				logger.debug("Closing Hibernate Session in OpenSessionInViewFilter");
				SessionFactoryUtils.closeSession(sessionHolder.getSession());
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onFrame(com.syncnapsis.websockets.Connection,
	 * byte, byte, byte[], int, int)
	 */
	@Override
	public boolean onFrame(Connection connection, byte flags, byte opcode, byte[] data, int offset, int length)
	{
		boolean participate = false;

		if(TransactionSynchronizationManager.hasResource(sessionFactory))
		{
			participate = true;
		}
		else
		{
			logger.debug("Opening Hibernate Session in OpenSessionInViewFilter");
			Session session = openSession(sessionFactory);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}
		boolean result;
		try
		{
			result = delegate.onFrame(connection, flags, opcode, data, offset, length);
		}
		finally
		{
			if(!participate)
			{
				SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
				logger.debug("Closing Hibernate Session in OpenSessionInViewFilter");
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
