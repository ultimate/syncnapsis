package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.GUIWindowParamsDao;
import com.syncnapsis.data.model.GUIWindowParams;
import com.syncnapsis.data.service.GUIWindowParamsManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({GUIWindowParamsManager.class, GUIWindowParamsManagerImpl.class})
public class GUIWindowParamsManagerImplTest extends GenericManagerImplTestCase<GUIWindowParams, Long, GUIWindowParamsManager, GUIWindowParamsDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new GUIWindowParams());
		setDaoClass(GUIWindowParamsDao.class);
		setMockDao(mockContext.mock(GUIWindowParamsDao.class));
		setMockManager(new GUIWindowParamsManagerImpl(mockDao));
	}
}
