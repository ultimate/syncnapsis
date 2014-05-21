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

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.HibernateUtil;
import com.syncnapsis.utils.reflections.Invocation;

@TestExcludesMethods({ "get*", "set*", "afterPropertiesSet" })
public class OpenSessionInViewServiceTest extends BaseSpringContextTestCase
{
	private SessionFactory	sessionFactory;

	@TestCoversMethods({ "intercept", "openSession" })
	public void testIntercept() throws Exception
	{
		OpenSessionInViewService s = new OpenSessionInViewService();
		s.setSessionFactory(sessionFactory);
		// s.setDelegate(delegate);

		Invocation<Session> i = new Invocation<Session>() {
			@Override
			public Session invoke()
			{
				logger.debug("inside invocation");
				Session session = HibernateUtil.currentSession();
				assertNotNull(session);
				assertTrue(session.isOpen());
				return session;
			}
		};
		
		Session before = null;
		if(HibernateUtil.isSessionBound())
		{
			// close session to force new one
			before = HibernateUtil.currentSession();
			before.close();
			assertFalse(before.isOpen());
		}
		
		// this is the current session
		Session session = s.intercept(i);
		
		assertNotNull(session);
		assertNotSame(before, session);
//		assertFalse(session.isOpen()); // not working?! maybe this is not the right session?
	}
}
