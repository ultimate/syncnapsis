package com.syncnapsis.data.dao;

import com.syncnapsis.data.dao.hibernate.SolarSystemDaoHibernate;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ SolarSystemDao.class, SolarSystemDaoHibernate.class })
public class SolarSystemDaoTest extends GenericDaoTestCase<SolarSystem, Long>
{
	private GalaxyDao		galaxyDao;
	private SolarSystemDao	solarSystemDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = solarSystemDao.getAll().get(0).getId();

		SolarSystem solarSystem = new SolarSystem();
		solarSystem.setCoordinateX(1);
		solarSystem.setCoordinateY(1);
		solarSystem.setCoordinateZ(1);
		solarSystem.setGalaxy(galaxyDao.getByName("galaxy1"));
		solarSystem.setHabitability(100);
		solarSystem.setName("any name");
		solarSystem.setSize(100);
		// set individual properties here

		setEntity(solarSystem);

		setEntityProperty("name");
		setEntityPropertyValue("another name");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(solarSystemDao);
	}

	// insert individual Tests here
}
