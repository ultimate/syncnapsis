package com.syncnapsis.tests;

import java.io.Serializable;
import java.util.ArrayList;

import com.syncnapsis.data.dao.RankDao;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Rank;
import com.syncnapsis.data.service.RankManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;

public class BaseRankManagerImplTestCase<R extends Rank<T>, T extends BaseObject<PK>, PK extends Serializable, M extends RankManager<R, T, PK>, D extends RankDao<R, T, PK>> extends
		GenericManagerImplTestCase<R, Long, M, D>
{

	public void testGetByEntity() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByEntity", entity, 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetHistoryByEntity() throws Exception
	{
		MethodCall managerCall = new MethodCall("getHistoryByEntity", new ArrayList<R>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetByCriterion() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByCriterion", new ArrayList<R>(), "crit");
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetByDefaultPrimaryCriterion() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByDefaultPrimaryCriterion", new ArrayList<R>());
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}
