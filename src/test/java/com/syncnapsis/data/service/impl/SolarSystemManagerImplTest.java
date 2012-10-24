package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.SolarSystemDao;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.service.SolarSystemManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { SolarSystemManager.class, SolarSystemManagerImpl.class })
public class SolarSystemManagerImplTest extends GenericManagerImplTestCase<SolarSystem, Long, SolarSystemManager, SolarSystemDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new SolarSystem());
		setDaoClass(SolarSystemDao.class);
		setMockDao(mockContext.mock(SolarSystemDao.class));
		setMockManager(new SolarSystemManagerImpl(mockDao));
	}
}
