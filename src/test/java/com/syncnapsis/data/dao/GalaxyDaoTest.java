package com.syncnapsis.data.dao;

import java.util.Date;

import com.syncnapsis.data.dao.hibernate.GalaxyDaoHibernate;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({GalaxyDao.class, GalaxyDaoHibernate.class})
public class GalaxyDaoTest extends GenericNameDaoTestCase<Galaxy, Long>
{
	private GalaxyDao galaxyDao;
	private PlayerDao playerDao;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		Long existingId = galaxyDao.getByName("Cube").getId();
		String existingName = galaxyDao.getAll().get(0).getName();
		
		Galaxy galaxy = new Galaxy();
		galaxy.setCreationDate(new Date(timeProvider.get()));
		galaxy.setCreator(playerDao.getByUsername("user1"));
		galaxy.setName("any name");
		galaxy.setSizeX(1000);
		galaxy.setSizeY(1000);
		galaxy.setSizeZ(1000);
		// set individual properties here
		
		setEntity(galaxy);
		
		setEntityProperty("name");
		setEntityPropertyValue("another name");
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);
		
		setGenericNameDao(galaxyDao);
	}
	
	// insert individual Tests here
}
