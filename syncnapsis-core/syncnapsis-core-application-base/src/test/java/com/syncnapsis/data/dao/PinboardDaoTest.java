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

import com.syncnapsis.data.dao.hibernate.PinboardDaoHibernate;
import com.syncnapsis.data.model.Pinboard;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({PinboardDao.class, PinboardDaoHibernate.class})
public class PinboardDaoTest extends GenericNameDaoTestCase<Pinboard, Long>
{
	private UserManager userManager;
	private PinboardDao pinboardDao;
	private TimeProvider timeProvider;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		String existingName = pinboardDao.getAll().get(0).getName();
		Long existingId = pinboardDao.getByName(existingName).getId();
		
		Pinboard pinboard = new Pinboard();
		pinboard.setCreationDate(new Date(timeProvider.get()));
		pinboard.setName("testboard");		
		pinboard.setCreator(userManager.getAll().get(0));
		// set individual properties here
		
		setEntity(pinboard);
		
		setEntityProperty("description");
		setEntityPropertyValue("a descriptive text...");
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);
		
		setGenericNameDao(pinboardDao);
	}
	
	// insert individual Tests here
}
