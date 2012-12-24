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

import java.util.List;

import com.syncnapsis.data.dao.hibernate.AllianceDaoHibernate;
import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.AllianceMemberRank;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { AllianceDao.class, AllianceDaoHibernate.class })
public class AllianceDaoTest extends GenericNameDaoTestCase<Alliance, Long>
{
	private AllianceDao	allianceDao;
	private EmpireDao empireDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		String existingName = "A0";
		Long existingId = allianceDao.getByName(existingName).getId();

		Alliance alliance = new Alliance();
		alliance.setShortName("any name");

		setEntity(alliance);

		setEntityProperty("shortName");
		setEntityPropertyValue("any name2");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);

		setGenericNameDao(allianceDao);
	}
	
	public void testGetByEmpire() throws Exception
	{
		Empire empire = empireDao.getByName("E1");
		List<Alliance> result = allianceDao.getByEmpire(empire.getId());
		
		assertNotNull(result);
		assertEquals(1, result.size());
		
		boolean found;
		for(Alliance alliance: result)
		{
			found = false;
			for(AllianceMemberRank allianceMemberRank: alliance.getAllianceMemberRanks())
			{
				for(Empire empire2: allianceMemberRank.getEmpires())
				{
					if(empire2.equals(empire))
					{
						found = true;
						break;
					}
				}
				if(found)
				{
					break;
				}
			}
			assertTrue(found);
		}
	}
}
