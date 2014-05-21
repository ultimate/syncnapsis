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
import java.util.List;

import com.syncnapsis.data.dao.hibernate.EmpireDaoHibernate;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ EmpireDao.class, EmpireDaoHibernate.class })
public class EmpireDaoTest extends GenericNameDaoTestCase<Empire, Long>
{
	private PlayerDao		playerDao;
	private EmpireDao		empireDao;
	private TimeProvider	timeProvider;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		String existingName = "E0";
		Long existingId = empireDao.getByName(existingName).getId();

		Empire empire = new Empire();
		empire.setShortName("any name");
		empire.setPlayer(playerDao.getByUsername("user1"));
		empire.setFoundationDate(new Date(timeProvider.get()));

		setEntity(empire);

		setEntityProperty("shortName");
		setEntityPropertyValue("any name2");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);

		setGenericNameDao(empireDao);
	}

	public void testGetByPlayer() throws Exception
	{
		Player player = playerDao.getByUsername("user1");
		List<Empire> result = empireDao.getByPlayer(player.getId());

		assertNotNull(result);
		assertEquals(1, result.size());

		for(Empire e : result)
		{
			assertEquals(player, e.getPlayer());
		}
	}
}
