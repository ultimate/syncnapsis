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
