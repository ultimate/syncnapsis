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

import java.util.List;

import com.syncnapsis.data.dao.hibernate.BlackListEntryDaoHibernate;
import com.syncnapsis.data.model.BlackList;
import com.syncnapsis.data.model.BlackListEntry;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ BlackListEntryDao.class, BlackListEntryDaoHibernate.class })
public class BlackListEntryDaoTest extends GenericDaoTestCase<BlackListEntry, Long>
{
	private BlackListEntryDao	blackListEntryDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = blackListEntryDao.getAll().get(0).getId();

		BlackListEntry blackListEntry = new BlackListEntry();
		// set individual properties here (especially not-null properties!)

		setEntity(blackListEntry);

		setEntityProperty("value");
		setEntityPropertyValue("new value");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(blackListEntryDao);
	}

	public void testGetByBlackList() throws Exception
	{
		BlackList blackList = blackListEntryDao.getAll().get(0).getBlackList();
		List<BlackListEntry> result = blackListEntryDao.getByBlackList(blackList.getId());

		assertNotNull(result);
		assertTrue(result.size() > 0);

		for(BlackListEntry b : result)
		{
			assertEquals(blackList, b.getBlackList());
		}
	}
	
	public void testGetValuesByBlackListName() throws Exception
	{
		BlackList blackList = blackListEntryDao.getAll().get(0).getBlackList();
		List<BlackListEntry> entries = blackListEntryDao.getByBlackList(blackList.getId());
		
		List<String> values = blackListEntryDao.getValuesByBlackListName(blackList.getName());
		
		assertNotNull(values);
		assertTrue(values.size() > 0);
		assertEquals(entries.size(), values.size());
		
		for(BlackListEntry b : entries)
		{
			assertTrue(values.contains(b.getValue()));
		}
	}

	// insert individual Tests here
}
