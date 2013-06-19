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

import com.syncnapsis.data.dao.SolarSystemInfrastructureDao;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { SolarSystemInfrastructureManager.class, SolarSystemInfrastructureManagerImpl.class })
public class SolarSystemInfrastructureManagerImplTest extends GenericManagerImplTestCase<SolarSystemInfrastructure, Long, SolarSystemInfrastructureManager, SolarSystemInfrastructureDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new SolarSystemInfrastructure());
		setDaoClass(SolarSystemInfrastructureDao.class);
		setMockDao(mockContext.mock(SolarSystemInfrastructureDao.class));
		setMockManager(new SolarSystemInfrastructureManagerImpl(mockDao));
	}

	public void testGetByMatch() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByMatch", new ArrayList<SolarSystemInfrastructure>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testInitialize() throws Exception
	{
		fail("unimplemented");
	}
}
