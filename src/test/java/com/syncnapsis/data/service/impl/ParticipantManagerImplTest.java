package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.ParticipantDao;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.service.ParticipantManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { ParticipantManager.class, ParticipantManagerImpl.class })
public class ParticipantManagerImplTest extends GenericManagerImplTestCase<Participant, Long, ParticipantManager, ParticipantDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Participant());
		setDaoClass(ParticipantDao.class);
		setMockDao(mockContext.mock(ParticipantDao.class));
		setMockManager(new ParticipantManagerImpl(mockDao));
	}
}
