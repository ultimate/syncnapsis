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
