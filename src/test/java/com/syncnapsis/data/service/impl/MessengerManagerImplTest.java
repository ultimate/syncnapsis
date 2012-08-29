package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.MessengerDao;
import com.syncnapsis.data.model.Messenger;
import com.syncnapsis.data.service.MessengerManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { MessengerManager.class, MessengerManagerImpl.class })
public class MessengerManagerImplTest extends GenericNameManagerImplTestCase<Messenger, Long, MessengerManager, MessengerDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Messenger());
		setDaoClass(MessengerDao.class);
		setMockDao(mockContext.mock(MessengerDao.class));
		setMockManager(new MessengerManagerImpl(mockDao));
	}
}
