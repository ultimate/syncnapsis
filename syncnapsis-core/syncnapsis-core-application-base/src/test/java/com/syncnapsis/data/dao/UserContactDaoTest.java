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
package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.dao.hibernate.UserContactDaoHibernate;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserContact;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({UserContactDao.class, UserContactDaoHibernate.class})
public class UserContactDaoTest extends GenericDaoTestCase<UserContact, Long>
{
	private UserDao userDao;
	private UserContactDao userContactDao;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		Long existingId = 1L;
		
		UserContact userContact = new UserContact();
		userContact.setUser1(userDao.getByName("user1"));
		userContact.setUser2(userDao.getByName("user2"));
		
		setEntity(userContact);
		
		setEntityProperty("user2");
		setEntityPropertyValue(userDao.getByName("user1"));
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		
		setGenericDao(userContactDao);
	}
	
	public void testGetByUser() throws Exception
	{		
		User user = userContactDao.getAll().get(0).getUser1();
		List<UserContact> result = userContactDao.getByUser(user.getId());
		
		assertNotNull(result);
		assertTrue(result.size() > 0);
		
		for(UserContact u: result)
		{
			assertTrue(user.equals(u.getUser1()) || user.equals(u.getUser2()));
		}
	}
	
	public void testGetUserContact() throws Exception
	{		
		User user1 = userDao.getByName("user1");
		User user2 = userDao.getByName("user2");
		UserContact userContact;
		
		userContact = userContactDao.getUserContact(user1.getId(), user2.getId());		
		assertNotNull(userContact);
		assertEquals(user1.getId(), userContact.getUser1().getId());
		assertEquals(user2.getId(), userContact.getUser2().getId());
		
		userContact = userContactDao.getUserContact(user2.getId(), user1.getId());		
		assertNotNull(userContact);
		assertEquals(user1.getId(), userContact.getUser1().getId());
		assertEquals(user2.getId(), userContact.getUser2().getId());
		
		userContact = userContactDao.getUserContact(user1.getId(), user1.getId());		
		assertNull(userContact);
	}
}
