package com.syncnapsis.data.dao;

import java.util.Date;

import com.syncnapsis.data.dao.hibernate.SolarSystemInfrastructureDaoHibernate;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ SolarSystemInfrastructureDao.class, SolarSystemInfrastructureDaoHibernate.class })
public class SolarSystemInfrastructureDaoTest extends GenericDaoTestCase<SolarSystemInfrastructure, Long>
{
	private SolarSystemDao					solarSystemDao;
	private SolarSystemInfrastructureDao	solarSystemInfrastructureDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = solarSystemInfrastructureDao.getAll().get(0).getId();

		SolarSystemInfrastructure solarSystemInfrastructure = new SolarSystemInfrastructure();
		solarSystemInfrastructure.setFirstColonizationDate(new Date(timeProvider.get()));
		solarSystemInfrastructure.setInfrastructure(200);
		solarSystemInfrastructure.setSolarSystem(solarSystemDao.getAll().get(0));
		// set individual properties here

		setEntity(solarSystemInfrastructure);

		setEntityProperty("infrastructure");
		setEntityPropertyValue(100);

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(solarSystemInfrastructureDao);
	}

	// insert individual Tests here
}
