package com.syncnapsis.data.dao;

import java.util.ArrayList;
import java.util.Date;

import com.syncnapsis.data.dao.hibernate.ParticipantDaoHibernate;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ ParticipantDao.class, ParticipantDaoHibernate.class })
public class ParticipantDaoTest extends GenericDaoTestCase<Participant, Long>
{
	private EmpireDao		empireDao;
	private MatchDao matchDao;
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
	// insert individual Tests here
}
