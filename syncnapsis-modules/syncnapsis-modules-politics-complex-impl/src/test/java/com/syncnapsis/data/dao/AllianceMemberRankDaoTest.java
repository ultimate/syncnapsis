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

import com.syncnapsis.data.dao.hibernate.AllianceMemberRankDaoHibernate;
import com.syncnapsis.data.model.AllianceMemberRank;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { AllianceMemberRankDao.class, AllianceMemberRankDaoHibernate.class })
public class AllianceMemberRankDaoTest extends GenericDaoTestCase<AllianceMemberRank, Long>
{
	private AllianceMemberRankDao	allianceMemberRankDao;
	private AllianceDao		allianceDao;
	private EmpireDao empireDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = allianceMemberRankDao.getAll().get(0).getId();

		AllianceMemberRank allianceMemberRank = new AllianceMemberRank();
		allianceMemberRank.setRankName("name");
		allianceMemberRank.setAlliance(allianceDao.getAll().get(0));

		setEntity(allianceMemberRank);

		setEntityProperty("rankName");
		setEntityPropertyValue("any name2");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(allianceMemberRankDao);
	}
	
	public void testGetByEmpire() throws Exception
	{
		Empire empire = empireDao.getByName("E1");
		List<AllianceMemberRank> result = allianceMemberRankDao.getByEmpire(empire.getId());
		
		assertNotNull(result);
		assertEquals(1, result.size());
		
		for(AllianceMemberRank allianceMemberRank: result)
		{
			assertTrue(allianceMemberRank.getEmpires().contains(empire));			
		}
	}
}
