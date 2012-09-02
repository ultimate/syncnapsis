package com.syncnapsis.data.service.impl;

import java.util.ArrayList;

import com.syncnapsis.data.dao.AllianceDao;
import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.AllianceManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({AllianceManager.class, AllianceManagerImpl.class})
public class AllianceManagerImplTest extends GenericNameManagerImplTestCase<Alliance, Long, AllianceManager, AllianceDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Alliance());
		setDaoClass(AllianceDao.class);
		setMockDao(mockContext.mock(AllianceDao.class));
		setMockManager(new AllianceManagerImpl(mockDao));
	}
	
	public void testGetByEmpire() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByEmpire", new ArrayList<BaseObject<?>>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}
