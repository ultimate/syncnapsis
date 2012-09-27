package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.SolarSystemInfrastructureDao;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { SolarSystemInfrastructureManager.class, SolarSystemInfrastructureManagerImpl.class })
public class SolarSystemInfrastructureManagerImplTest extends GenericManagerImplTestCase<SolarSystemInfrastructure, Long, SolarSystemInfrastructureManager, SolarSystemInfrastructureDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new SolarSystemInfrastructure());
		setDaoClass(SolarSystemInfrastructureDao.class);
		setMockDao(mockContext.mock(SolarSystemInfrastructureDao.class));
		setMockManager(new SolarSystemInfrastructureManagerImpl(mockDao));
	}
}
