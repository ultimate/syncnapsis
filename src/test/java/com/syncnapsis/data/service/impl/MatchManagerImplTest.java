package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.MatchDao;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.service.MatchManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { MatchManager.class, MatchManagerImpl.class })
public class MatchManagerImplTest extends GenericNameManagerImplTestCase<Match, Long, MatchManager, MatchDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Match());
		setDaoClass(MatchDao.class);
		setMockDao(mockContext.mock(MatchDao.class));
		setMockManager(new MatchManagerImpl(mockDao));
	}
}
