package com.syncnapsis.data.service.impl;

import java.util.ArrayList;

import com.syncnapsis.data.dao.ContactGroupDao;
import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.ContactGroupManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ContactGroupManager.class, ContactGroupManagerImpl.class})
public class ContactGroupManagerImplTest extends GenericManagerImplTestCase<ContactGroup, Long, ContactGroupManager, ContactGroupDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new ContactGroup());
		setDaoClass(ContactGroupDao.class);
		setMockDao(mockContext.mock(ContactGroupDao.class));
		setMockManager(new ContactGroupManagerImpl(mockDao));
	}
	
	public void testGetByEmpire() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByEmpire", new ArrayList<BaseObject<?>>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testGetByAlliance() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByAlliance", new ArrayList<BaseObject<?>>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}
