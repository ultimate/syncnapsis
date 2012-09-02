package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.EmpireRankDao;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.EmpireRank;
import com.syncnapsis.data.service.EmpireRankManager;
import com.syncnapsis.tests.BaseRankManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { EmpireRankManager.class, EmpireRankManagerImpl.class })
public class EmpireRankManagerImplTest extends BaseRankManagerImplTestCase<EmpireRank, Empire, Long, EmpireRankManager, EmpireRankDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new EmpireRank());
		setDaoClass(EmpireRankDao.class);
		setMockDao(mockContext.mock(EmpireRankDao.class));
		setMockManager(new EmpireRankManagerImpl(mockDao));
	}
}
