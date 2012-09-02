package com.syncnapsis.data.service.impl;

import java.util.ArrayList;

import com.syncnapsis.data.dao.AllianceMemberRankDao;
import com.syncnapsis.data.model.AllianceMemberRank;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.AllianceMemberRankManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({AllianceMemberRankManager.class, AllianceMemberRankManagerImpl.class})
public class AllianceMemberRankManagerImplTest extends GenericManagerImplTestCase<AllianceMemberRank, Long, AllianceMemberRankManager, AllianceMemberRankDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new AllianceMemberRank());
		setDaoClass(AllianceMemberRankDao.class);
		setMockDao(mockContext.mock(AllianceMemberRankDao.class));
		setMockManager(new AllianceMemberRankManagerImpl(mockDao));
	}
	
	public void testGetByEmpire() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByEmpire", new ArrayList<BaseObject<?>>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}
