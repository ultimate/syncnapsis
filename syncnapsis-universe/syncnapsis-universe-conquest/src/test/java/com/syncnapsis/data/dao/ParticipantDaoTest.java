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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.syncnapsis.data.dao.hibernate.ParticipantDaoHibernate;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ ParticipantDao.class, ParticipantDaoHibernate.class })
public class ParticipantDaoTest extends GenericDaoTestCase<Participant, Long>
{
	private EmpireDao		empireDao;
	private MatchDao		matchDao;
	private ParticipantDao	participantDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = participantDao.getAll().get(0).getId();

		Participant participant = new Participant();
		participant.setDestructionDate(new Date(timeProvider.get()));
		participant.setDestructionType(EnumDestructionType.destroyed);
		participant.setEmpire(empireDao.getByName("empire1"));
		participant.setJoinedDate(new Date(timeProvider.get()));
		participant.setMatch(matchDao.getAll().get(0));
		participant.setRank(1);
		participant.setRivals(new ArrayList<Participant>());
		// set individual properties here

		setEntity(participant);

		setEntityProperty("rank");
		setEntityPropertyValue(2);

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(participantDao);
	}

	public void testGetByMatch() throws Exception
	{
		long match = 1L;
		
		List<Participant> result = participantDao.getByMatch(match);

		assertNotNull(result);
		assertEquals(2, result.size());

		for(Participant p : result)
		{
			assertEquals(match, (long) p.getMatch().getId());
		}
	}

	public void testGetByMatchAndEmpire() throws Exception
	{
		long match = 1L;
		long empire = 20L;
		
		Participant result = participantDao.getByMatchAndEmpire(match, empire);

		assertNotNull(result);
		assertEquals(match, (long) result.getMatch().getId());
		assertEquals(empire, (long) result.getEmpire().getId());
	}

	// insert individual Tests here
}
