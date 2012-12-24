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
package com.syncnapsis.data.service.impl;

import java.util.ArrayList;

import com.syncnapsis.data.dao.EmpireDao;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.EmpireManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({EmpireManager.class, EmpireManagerImpl.class})
public class EmpireManagerImplTest extends GenericNameManagerImplTestCase<Empire, Long, EmpireManager, EmpireDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Empire());
		setDaoClass(EmpireDao.class);
		setMockDao(mockContext.mock(EmpireDao.class));
		setMockManager(new EmpireManagerImpl(mockDao));
	}
	
	public void testGetByPlayer() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByPlayer", new ArrayList<BaseObject<?>>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}
