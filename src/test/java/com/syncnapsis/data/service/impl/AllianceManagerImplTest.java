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

import com.syncnapsis.data.dao.AllianceDao;
import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.AllianceManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({AllianceManager.class, AllianceManagerImpl.class})
public class AllianceManagerImplTest extends GenericNameManagerImplTestCase<Alliance, Long, AllianceManager, AllianceDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Alliance());
		setDaoClass(AllianceDao.class);
		setMockDao(mockContext.mock(AllianceDao.class));
		setMockManager(new AllianceManagerImpl(mockDao));
	}
	
	public void testGetByEmpire() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByEmpire", new ArrayList<BaseObject<?>>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
}
