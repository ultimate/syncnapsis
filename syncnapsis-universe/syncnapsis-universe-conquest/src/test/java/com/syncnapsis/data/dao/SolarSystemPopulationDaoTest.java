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

import java.util.Date;
import java.util.List;

import com.syncnapsis.data.dao.hibernate.SolarSystemPopulationDaoHibernate;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.enums.EnumPopulationPriority;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ SolarSystemPopulationDao.class, SolarSystemPopulationDaoHibernate.class })
public class SolarSystemPopulationDaoTest extends GenericDaoTestCase<SolarSystemPopulation, Long>
{
	private ParticipantDao					participantDao;
	private SolarSystemInfrastructureDao	solarSystemInfrastructureDao;
	private SolarSystemPopulationDao		solarSystemPopulationDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = solarSystemPopulationDao.getAll().get(0).getId();

		SolarSystemPopulation solarSystemPopulation = new SolarSystemPopulation();
		solarSystemPopulation.setAttackPriority(EnumPopulationPriority.balanced);
		solarSystemPopulation.setBuildPriority(EnumPopulationPriority.balanced);
		solarSystemPopulation.setColonizationDate(new Date(timeProvider.get()));
		solarSystemPopulation.setDestructionDate(new Date(timeProvider.get()));
		solarSystemPopulation.setDestructionType(EnumDestructionType.destroyed);
		solarSystemPopulation.setInfrastructure(solarSystemInfrastructureDao.getAll().get(0));
		solarSystemPopulation.setLastUpdateDate(new Date(timeProvider.get()));
		solarSystemPopulation.setOrigin(solarSystemPopulationDao.get(existingId));
		solarSystemPopulation.setOriginationDate(new Date(timeProvider.get()));
		solarSystemPopulation.setParticipant(participantDao.getAll().get(0));
		solarSystemPopulation.setPopulation(200L);
		// set individual properties here

		setEntity(solarSystemPopulation);

		setEntityProperty("population");
		setEntityPropertyValue(100L);

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(solarSystemPopulationDao);
	}

	public void testGetByParticipant() throws Exception
	{
		long participant = 11L;
		List<SolarSystemPopulation> result = solarSystemPopulationDao.getByParticipant(participant);

		assertNotNull(result);
		assertTrue(result.size() > 0);

		for(SolarSystemPopulation p : result)
		{
			assertEquals(participant, (long) p.getParticipant().getId());
		}
	}

	public void testGetByMatch() throws Exception
	{
		long match = 1L;
		List<SolarSystemPopulation> result = solarSystemPopulationDao.getByMatch(match);

		assertNotNull(result);
		assertTrue(result.size() > 0);

		for(SolarSystemPopulation p : result)
		{
			assertEquals(match, (long) p.getInfrastructure().getMatch().getId());
		}
	}

	// insert individual Tests here
}
