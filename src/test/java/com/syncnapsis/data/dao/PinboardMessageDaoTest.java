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

import com.syncnapsis.data.dao.hibernate.PinboardMessageDaoHibernate;
import com.syncnapsis.data.model.PinboardMessage;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({PinboardMessageDao.class, PinboardMessageDaoHibernate.class})
public class PinboardMessageDaoTest extends GenericDaoTestCase<PinboardMessage, Long>
{
	private PinboardMessageDao pinboardMessageDao;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		Long existingId = pinboardMessageDao.getAll().get(0).getId();
		
		PinboardMessage pinboardMessage = new PinboardMessage();
		// set individual properties here
		
		setEntity(pinboardMessage);
		
		setEntityProperty("content");
		setEntityPropertyValue("the message content...");
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		
		setGenericDao(pinboardMessageDao);
	}
	
	// insert individual Tests here
}
