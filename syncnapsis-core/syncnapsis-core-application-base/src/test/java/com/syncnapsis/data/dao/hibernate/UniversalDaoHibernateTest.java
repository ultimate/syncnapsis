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
package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.UniversalDao;
import com.syncnapsis.data.model.User;
import com.syncnapsis.tests.BaseDaoTestCase;

/**
 * @author ultimate
 *
 */
public class UniversalDaoHibernateTest extends BaseDaoTestCase
{
	private UniversalDao universalDao;
	
	public void testGet()
	{	
		assertTrue(universalDao instanceof UniversalDaoHibernate);
		
		User user1 = universalDao.get(User.class, (long) 1);
		User user2 = universalDao.get(User.class, (int) 1);
		User user3 = universalDao.get(User.class, new Long(1));
		User user4 = universalDao.get(User.class, new Integer(1));
		assertNotNull(user1);
		assertNotNull(user2);
		assertNotNull(user3);
		assertNotNull(user4);
		assertEquals(user1, user2);
		assertEquals(user1, user3);
		assertEquals(user1, user4);
	}
}
