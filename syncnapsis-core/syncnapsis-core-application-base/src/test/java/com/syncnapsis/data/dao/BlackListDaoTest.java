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

import com.syncnapsis.data.dao.hibernate.BlackListDaoHibernate;
import com.syncnapsis.data.model.BlackList;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({BlackListDao.class, BlackListDaoHibernate.class})
public class BlackListDaoTest extends GenericNameDaoTestCase<BlackList, Long>
{
	private BlackListDao blackListDao;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		String existingName = blackListDao.getAll().get(0).getName();
		Long existingId = blackListDao.getByName(existingName).getId();
		
		BlackList blackList = new BlackList();
		// set individual properties here (especially not-null properties!)
		
		setEntity(blackList);
		
		setEntityProperty("name");
		setEntityPropertyValue("a new name");
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);
		
		setGenericNameDao(blackListDao);
	}
	
	// insert individual Tests here
}
