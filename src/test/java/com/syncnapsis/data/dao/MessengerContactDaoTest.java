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
package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.dao.hibernate.MessengerContactDaoHibernate;
import com.syncnapsis.data.model.MessengerContact;
import com.syncnapsis.data.model.User;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({MessengerContactDao.class, MessengerContactDaoHibernate.class})
public class MessengerContactDaoTest extends GenericDaoTestCase<MessengerContact, Long>
{
	private UserDao userDao;
	private MessengerContactDao messengerContactDao;
	private MessengerDao messengerDao;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		User user1 = userDao.getByName("user1");
		
		Long existingId = user1.getMessengerContacts().get(0).getId();
		
		MessengerContact messengerContact = new MessengerContact();
		messengerContact.setUser(user1);
		messengerContact.setMessenger(messengerDao.getByName("Skype"));
		
		setEntity(messengerContact);
		
		setEntityProperty("user");
		setEntityPropertyValue(userDao.getByName("user2"));
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		
		setGenericDao(messengerContactDao);
	}
	
	public void testGetByUser() throws Exception
	{		
		User user = userDao.getByName("user1");
		List<MessengerContact> result = messengerContactDao.getByUser(user.getId());
		
		assertNotNull(result);
		assertTrue(result.size() > 0);
		
		for(MessengerContact m: result)
		{
			assertEquals(user, m.getUser());
		}
	}
}
