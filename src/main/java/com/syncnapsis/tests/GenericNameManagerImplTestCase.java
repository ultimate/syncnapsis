/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
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
	
	public void testIsNameValid() throws Exception
	{
		// TODO test 
//		MethodCall managerCall = new MethodCall("isNameAvailable", true, "name");
//		MethodCall daoCall = managerCall;
//		simpleGenericTest(managerCall, daoCall);
	}
}