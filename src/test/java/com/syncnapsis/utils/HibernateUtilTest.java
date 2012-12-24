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

import org.hibernate.Session;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class HibernateUtilTest extends LoggerTestCase
{
	@TestCoversMethods({ "getInstance", "*etSessionFactory", "initSessionFactory", "currentSession", "closeSession" })
	public void testInstantiation()
	{
		HibernateUtil h = HibernateUtil.getInstance();
		HibernateUtil h2 = HibernateUtil.getInstance();

		assertNotNull(h);
		assertSame(h, h2);

		/*
		SessionFactory overrideSF = createSessionFactory();
		SessionFactory defaultSF = h.getSessionFactory();

		assertNotNull(defaultSF);
		h.setSessionFactory(overrideSF);
		assertNotSame(overrideSF, defaultSF);
		assertSame(overrideSF, h.getSessionFactory());
		*/
		
		Session session = HibernateUtil.currentSession();
		
		assertNotNull(session);
		assertSame(h.getSessionFactory().getCurrentSession(), session);
		assertTrue(session.isOpen());
		
		HibernateUtil.closeSession();
		
		assertFalse(session.isOpen());
		assertNotSame(session, HibernateUtil.currentSession());
		assertTrue(HibernateUtil.currentSession().isOpen());
	}

	/*
	private SessionFactory createSessionFactory()
	{
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		return configuration.buildSessionFactory(serviceRegistry);
	}
	*/
}
