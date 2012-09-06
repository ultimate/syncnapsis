package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.AllianceRankDao;
import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.AllianceRank;
import com.syncnapsis.data.service.AllianceRankManager;
import com.syncnapsis.tests.BaseRankManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { AllianceRankManager.class, AllianceRankManagerImpl.class })
public class AllianceRankManagerImplTest extends BaseRankManagerImplTestCase<AllianceRank, Alliance, Long, AllianceRankManager, AllianceRankDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new AllianceRank());
		setDaoClass(AllianceRankDao.class);
		setMockDao(mockContext.mock(AllianceRankDao.class));
		setMockManager(new AllianceRankManagerImpl(mockDao));
	}
}
