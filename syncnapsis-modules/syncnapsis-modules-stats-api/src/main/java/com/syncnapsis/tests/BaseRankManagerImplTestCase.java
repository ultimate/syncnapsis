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
