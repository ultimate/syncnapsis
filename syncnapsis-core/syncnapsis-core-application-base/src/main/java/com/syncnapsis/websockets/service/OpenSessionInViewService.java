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

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import com.syncnapsis.utils.HibernateUtil;
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

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.BaseService#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(sessionFactory, "sessionFactory must not be null!");
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
			HibernateUtil.openBoundSession();
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
				logger.debug("Closing Hibernate Session in OpenSessionInViewService");
				HibernateUtil.closeBoundSession();
			}
		}
		return result;
	}
}
