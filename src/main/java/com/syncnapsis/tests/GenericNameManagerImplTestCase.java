package com.syncnapsis.tests;

import java.io.Serializable;
import java.util.ArrayList;

import com.syncnapsis.data.dao.GenericNameDao;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.GenericNameManager;

public abstract class GenericNameManagerImplTestCase<T extends BaseObject<PK>, PK extends Serializable, M extends GenericNameManager<T, PK>, D extends GenericNameDao<T, PK>> extends GenericManagerImplTestCase<T, PK, M, D>
{
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
		mockManager = null;
		mockDao = null;
	}
	
	public void testGetByName() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByName", entity, "name");
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetOrderedByName() throws Exception
	{
		MethodCall managerCall = new MethodCall("getOrderedByName", new ArrayList<T>(), true);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
		
	public void testGetByPrefix() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByPrefix", new ArrayList<T>(), "prefix", -1, true);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testIsNameAvailable() throws Exception
	{
		MethodCall managerCall = new MethodCall("isNameAvailable", true, "name");
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}