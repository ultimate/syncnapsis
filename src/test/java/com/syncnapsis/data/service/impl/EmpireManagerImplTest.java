package com.syncnapsis.data.service.impl;

import java.util.ArrayList;

import com.syncnapsis.data.dao.EmpireDao;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.EmpireManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({EmpireManager.class, EmpireManagerImpl.class})
public class EmpireManagerImplTest extends GenericNameManagerImplTestCase<Empire, Long, EmpireManager, EmpireDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Empire());
		setDaoClass(EmpireDao.class);
		setMockDao(mockContext.mock(EmpireDao.class));
		setMockManager(new EmpireManagerImpl(mockDao));
	}
	
	public void testGetByPlayer() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByPlayer", new ArrayList<BaseObject<?>>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}
