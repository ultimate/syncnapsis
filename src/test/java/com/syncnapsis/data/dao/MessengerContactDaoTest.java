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
