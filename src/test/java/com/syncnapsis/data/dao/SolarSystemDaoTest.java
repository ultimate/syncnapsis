package com.syncnapsis.data.dao;

import com.syncnapsis.data.dao.hibernate.SolarSystemDaoHibernate;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ SolarSystemDao.class, SolarSystemDaoHibernate.class })
public class SolarSystemDaoTest extends GenericNameDaoTestCase<SolarSystem, Long>
{
	private GalaxyDao		galaxyDao;
	private SolarSystemDao	solarSystemDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		String existingName = solarSystemDao.getAll().get(0).getName();
		Long existingId = solarSystemDao.getByName(existingName).getId();

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
		setExistingEntityName(existingName);

		setGenericNameDao(solarSystemDao);
	}

	// insert individual Tests here
}
