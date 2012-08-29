package com.syncnapsis.data.dao;

import com.syncnapsis.data.dao.hibernate.MessengerDaoHibernate;
import com.syncnapsis.data.model.Messenger;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({MessengerDao.class, MessengerDaoHibernate.class})
public class MessengerDaoTest extends GenericNameDaoTestCase<Messenger, Long>
{
	private MessengerDao messengerDao;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		String existingName = "Skype";
		Long existingId = messengerDao.getByName(existingName).getId();
		
		Messenger messenger = new Messenger();
		messenger.setName("any name");
		
		setEntity(messenger);
		
		setEntityProperty("name");
		setEntityPropertyValue("any name2");
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);
		
		setGenericNameDao(messengerDao);
	}
}
