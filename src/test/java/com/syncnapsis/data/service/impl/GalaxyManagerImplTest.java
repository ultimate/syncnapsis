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
import java.util.LinkedList;
import java.util.List;

import com.syncnapsis.data.dao.GalaxyDao;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.data.service.SolarSystemManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.data.VectorGenerator;

@TestCoversClasses({ GalaxyManager.class, GalaxyManagerImpl.class })
@TestExcludesMethods({ "afterPropertiesSet", "*etSecurityManager" })
public class GalaxyManagerImplTest extends GenericNameManagerImplTestCase<Galaxy, Long, GalaxyManager, GalaxyDao>
{
	private SolarSystemManager	solarSystemManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Galaxy());
		setDaoClass(GalaxyDao.class);
		setMockDao(mockContext.mock(GalaxyDao.class));
		setMockManager(new GalaxyManagerImpl(mockDao, solarSystemManager));
	}

	public void testGetByCreator() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByCreator", new ArrayList<Galaxy>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testCreate() throws Exception
	{

	}

	public void testCalculateSize() throws Exception
	{
		List<Vector.Integer> coords = new LinkedList<Vector.Integer>();
		int xSize = 1100;
		int ySize = 2200;
		int zSize = 500;
		VectorGenerator generator = new VectorGenerator(xSize, ySize, zSize);
		int max = 1100;
		for(int i = 0; i < 10000; i++)
		{
			coords.add(new Vector.Integer(x, y, z));
		}
	}
}
