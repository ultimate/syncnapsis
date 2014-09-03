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

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class SessionFactoryUtilTest extends BaseSpringContextTestCase
{
	private SessionFactory sessionFactory;
	
	@TestCoversMethods({ "getInstance", "*etSessionFactory", "initSessionFactory", "currentSession", "openSession", "closeSession", "*BoundSession",
			"isSessionBound" })
	public void testInstantiation()
	{
		SessionFactoryUtil sfu = new SessionFactoryUtil(sessionFactory);

		// Spring does not open the session automatically here, since we are not within
		// a bean and a @Transactional annotation
		sfu.openBoundSession();

		Session session = sfu.currentSession();

		assertNotNull(session);
		assertSame(sfu.getSessionFactory().getCurrentSession(), session);
		assertTrue(session.isOpen());

		sfu.closeBoundSession();

		assertFalse(session.isOpen());
	}
}
