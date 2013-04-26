/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
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
		solarSystem.getCoords().setX(1);
		solarSystem.getCoords().setY(2);
		solarSystem.getCoords().setZ(3);
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
	
	public void testCoords() throws Exception
	{
		SolarSystem sys = solarSystemDao.get(1L);
		
		assertNotNull(sys);
		assertNotNull(sys.getCoords());
		
		assertNotNull(sys.getCoords().getX());
		assertTrue(sys.getCoords().getX() instanceof Integer);
		assertTrue(sys.getCoords().getX() == 100);
		
		assertNotNull(sys.getCoords().getY());
		assertTrue(sys.getCoords().getY() instanceof Integer);
		assertTrue(sys.getCoords().getY() == 100);
		
		assertNotNull(sys.getCoords().getZ());
		assertTrue(sys.getCoords().getZ() instanceof Integer);
		assertTrue(sys.getCoords().getZ() == 100);
	}

	// insert individual Tests here
}
