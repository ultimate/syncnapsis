package com.syncnapsis.data.service.impl;

import java.util.ArrayList;

import com.syncnapsis.data.dao.MessengerContactDao;
import com.syncnapsis.data.model.MessengerContact;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.MessengerContactManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { MessengerContactManager.class, MessengerContactManagerImpl.class })
public class MessengerContactManagerImplTest extends GenericManagerImplTestCase<MessengerContact, Long, MessengerContactManager, MessengerContactDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new MessengerContact());
		setDaoClass(MessengerContactDao.class);
		setMockDao(mockContext.mock(MessengerContactDao.class));
		setMockManager(new MessengerContactManagerImpl(mockDao));
	}
	
	public void testGetByUser() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByUser", new ArrayList<BaseObject<?>>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}
