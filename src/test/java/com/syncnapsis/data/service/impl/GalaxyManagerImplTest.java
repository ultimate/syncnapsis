package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.GalaxyDao;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { GalaxyManager.class, GalaxyManagerImpl.class })
public class GalaxyManagerImplTest extends GenericNameManagerImplTestCase<Galaxy, Long, GalaxyManager, GalaxyDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Galaxy());
		setDaoClass(GalaxyDao.class);
		setMockDao(mockContext.mock(GalaxyDao.class));
		setMockManager(new GalaxyManagerImpl(mockDao));
	}
}
