package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.GUIActionDao;
import com.syncnapsis.data.model.GUIAction;
import com.syncnapsis.data.service.GUIActionManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { GUIActionManager.class, GUIActionManagerImpl.class })
public class GUIActionManagerImplTest extends GenericNameManagerImplTestCase<GUIAction, Long, GUIActionManager, GUIActionDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new GUIAction());
		setDaoClass(GUIActionDao.class);
		setMockDao(mockContext.mock(GUIActionDao.class));
		setMockManager(new GUIActionManagerImpl(mockDao));
	}

	public void testGetByWindowId() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByWindowId", entity, "id");
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}
