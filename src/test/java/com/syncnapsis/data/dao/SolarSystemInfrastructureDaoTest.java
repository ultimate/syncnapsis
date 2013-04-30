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

import com.syncnapsis.data.dao.hibernate.SolarSystemInfrastructureDaoHibernate;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ SolarSystemInfrastructureDao.class, SolarSystemInfrastructureDaoHibernate.class })
public class SolarSystemInfrastructureDaoTest extends GenericDaoTestCase<SolarSystemInfrastructure, Long>
{
	private SolarSystemDao					solarSystemDao;
	private SolarSystemInfrastructureDao	solarSystemInfrastructureDao;
	private MatchDao						matchDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = solarSystemInfrastructureDao.getAll().get(0).getId();

		SolarSystemInfrastructure solarSystemInfrastructure = new SolarSystemInfrastructure();
		solarSystemInfrastructure.setFirstColonizationDate(new Date(timeProvider.get()));
		solarSystemInfrastructure.setInfrastructure(200);
		solarSystemInfrastructure.setSolarSystem(solarSystemDao.getAll().get(0));
		// set individual properties here

		setEntity(solarSystemInfrastructure);

		setEntityProperty("infrastructure");
		setEntityPropertyValue(100);

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(solarSystemInfrastructureDao);
	}

	public void testGetByMatch() throws Exception
	{
		long matchId = 1L;
		Match match = matchDao.get(matchId);
		
		List<SolarSystemInfrastructure> result = solarSystemInfrastructureDao.getByMatch(matchId);

		assertNotNull(result);
		assertTrue(result.size() > 0);

		for(SolarSystemInfrastructure i : result)
		{
			assertEquals(matchId, (long) i.getMatch().getId());
			assertEquals(match.getGalaxy().getId(), i.getSolarSystem().getGalaxy().getId());
		}
		
		// normally there should be an infrastructure for every SolarSystem
		// of the match's galaxy, but test data is not complete for this... 
//		boolean found;
//		for(SolarSystem s: match.getGalaxy().getSolarSystems())
//		{
//			found = false;
//			for(SolarSystemInfrastructure i : result)
//			{
//				if(i.getSolarSystem().getId().equals(s.getId()))
//				{
//					found = true;
//					break;
//				}
//			}
//			assertTrue(found);
//		}
	}

	// insert individual Tests here
}
