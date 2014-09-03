/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import com.syncnapsis.data.dao.hibernate.MessengerDaoHibernate;
import com.syncnapsis.data.model.Messenger;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({MessengerDao.class, MessengerDaoHibernate.class})
public class MessengerDaoTest extends GenericNameDaoTestCase<Messenger, Long>
{
	private MessengerDao messengerDao;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		String existingName = "Skype";
		Long existingId = messengerDao.getByName(existingName).getId();
		
		Messenger messenger = new Messenger();
		messenger.setName("any name");
		
		setEntity(messenger);
		
		setEntityProperty("name");
		setEntityPropertyValue("any name2");
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);
		
		setGenericNameDao(messengerDao);
	}
}
