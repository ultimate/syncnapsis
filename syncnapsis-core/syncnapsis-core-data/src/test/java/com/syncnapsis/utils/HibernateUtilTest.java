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

import com.syncnapsis.data.model.IntegerModel;
import com.syncnapsis.data.model.LongModel;
import com.syncnapsis.data.model.StringModel;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods("afterPropertiesSet")
public class HibernateUtilTest extends BaseSpringContextTestCase
{
	@TestCoversMethods({ "getInstance", "*etSessionFactory", "*etSessionFactoryUtil", "initSessionFactory", "currentSession", "closeSession",
			"*BoundSession", "isSessionBound" })
	public void testInstantiation()
	{
		HibernateUtil h = HibernateUtil.getInstance();
		HibernateUtil h2 = HibernateUtil.getInstance();

		assertNotNull(h);
		assertSame(h, h2);

		// Spring does not open the session automatically here, since we are not within
		// a bean and a @Transactional annotation
		HibernateUtil.openBoundSession();

		Session session = HibernateUtil.currentSession();

		assertNotNull(session);
		assertSame(h.getSessionFactory().getCurrentSession(), session);
		assertTrue(session.isOpen());

		HibernateUtil.closeBoundSession();

		assertFalse(session.isOpen());
	}

	public void testGetIdType() throws Exception
	{
		SessionFactory sf = HibernateUtil.getInstance().getSessionFactory();
		
		assertSame(Long.class, HibernateUtil.getIdType(sf, LongModel.class));
		assertSame(Integer.class, HibernateUtil.getIdType(sf, IntegerModel.class));
		assertSame(String.class, HibernateUtil.getIdType(sf, StringModel.class));
	}

	@TestCoversMethods({ "checkIdType*" })
	public void testCheckIdType() throws Exception
	{
		SessionFactory sf = HibernateUtil.getInstance().getSessionFactory();
		
		assertEquals((Long) 1L, HibernateUtil.checkIdType(sf, LongModel.class, 1L));
		assertSame(Long.class, HibernateUtil.checkIdType(sf, LongModel.class, 1L).getClass());

		assertEquals((Long) 1L, HibernateUtil.checkIdType(sf, LongModel.class, 1));
		assertSame(Long.class, HibernateUtil.checkIdType(sf, LongModel.class, 1).getClass());

		assertEquals((Integer) 1, HibernateUtil.checkIdType(sf, IntegerModel.class, 1L));
		assertSame(Integer.class, HibernateUtil.checkIdType(sf, IntegerModel.class, 1L).getClass());

		assertEquals((Integer) 1, HibernateUtil.checkIdType(sf, IntegerModel.class, 1));
		assertSame(Integer.class, HibernateUtil.checkIdType(sf, IntegerModel.class, 1).getClass());

		assertEquals("1", HibernateUtil.checkIdType(sf, StringModel.class, "1"));
		assertSame(String.class, HibernateUtil.checkIdType(sf, StringModel.class, "1").getClass());
	}
}
