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
package com.syncnapsis.data.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;

import com.syncnapsis.data.dao.SolarSystemDao;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.data.service.SolarSystemManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.data.ExtendedRandom;

@TestCoversClasses({ SolarSystemManager.class, SolarSystemManagerImpl.class })
@TestExcludesMethods("*etNameGenerator")
public class SolarSystemManagerImplTest extends GenericManagerImplTestCase<SolarSystem, Long, SolarSystemManager, SolarSystemDao>
{
	private GalaxyManager	galaxyManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new SolarSystem());
		setDaoClass(SolarSystemDao.class);
		setMockDao(mockContext.mock(SolarSystemDao.class));
		setMockManager(new SolarSystemManagerImpl(mockDao));
	}

	public void testGetByGalaxy() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByGalaxy", new ArrayList<SolarSystem>(), 1L);
		MethodCall daoCall = new MethodCall("getByGalaxy", new ArrayList<SolarSystem>(), 1L);
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetName() throws Exception
	{
		String[] names = new String[] { "alpha", "beta", "gamma" };
		List<String> namesL = Arrays.asList(names);

		String[] expected3 = names;
		String[] expected4 = new String[] { names[0] + " I", names[1], names[2], names[0] + " II" };
		String[] expected5 = new String[] { names[0] + " I", names[1] + " I", names[2], names[0] + " II", names[1] + " II" };
		String[] expected6 = new String[] { names[0] + " I", names[1] + " I", names[2] + " I", names[0] + " II", names[1] + " II", names[2] + " II",
				names[0] + " III", names[1] + " III", names[2] + " III" };

		List<Vector.Integer> coords = new ArrayList<Vector.Integer>(10);
		coords.add(new Vector.Integer(0, 0, 0));

		SolarSystemManagerImpl m = (SolarSystemManagerImpl) mockManager;

		// 2 coords
		coords.add(new Vector.Integer(1, 1, 1));
		for(int i = 0; i < 2; i++)
			assertEquals(expected3[i], m.getName(coords, namesL, i, null));
		// 3 coords
		coords.add(new Vector.Integer(2, 2, 2));
		for(int i = 0; i < 3; i++)
			assertEquals(expected3[i], m.getName(coords, namesL, i, null));
		// 4 coords
		coords.add(new Vector.Integer(3, 3, 3));
		for(int i = 0; i < 4; i++)
			assertEquals(expected4[i], m.getName(coords, namesL, i, null));
		// 5 coords
		coords.add(new Vector.Integer(4, 4, 4));
		for(int i = 0; i < 5; i++)
			assertEquals(expected5[i], m.getName(coords, namesL, i, null));
		// 6 coords
		coords.add(new Vector.Integer(5, 5, 5));
		for(int i = 0; i < 6; i++)
			assertEquals(expected6[i], m.getName(coords, namesL, i, null));
		// 7 coords
		coords.add(new Vector.Integer(6, 6, 6));
		for(int i = 0; i < 7; i++)
			assertEquals(expected6[i], m.getName(coords, namesL, i, null));
		// 8 coords
		coords.add(new Vector.Integer(7, 7, 7));
		for(int i = 0; i < 8; i++)
			assertEquals(expected6[i], m.getName(coords, namesL, i, null));
		// 9 coords
		coords.add(new Vector.Integer(8, 8, 8));
		for(int i = 0; i < 9; i++)
			assertEquals(expected6[i], m.getName(coords, namesL, i, null));
	}

	public void testCreate() throws Exception
	{
		logger.info("" + applicationContext.getBean("galaxyManager"));
		Galaxy galaxy = galaxyManager.getAll().get(0);
		List<Vector.Integer> systemCoords = Arrays.asList(new Vector.Integer(0, 0, 0), new Vector.Integer(1, 1, 1), new Vector.Integer(2, 2, 2));
		List<String> systemNames = Arrays.asList("a", "b", "c");
		ExtendedRandom random = new ExtendedRandom();

		for(int i = 0; i < 3; i++)
		{
			final SolarSystem expected = new SolarSystem();
			expected.setActivated(true);
			expected.setCoords(systemCoords.get(i));
			expected.setGalaxy(galaxy);
			expected.setName(systemNames.get(i));

			mockContext.checking(new Expectations() {
				{
					oneOf(mockDao).save(with(equal(expected)));
					will(returnValue(expected));
				}
			});

			SolarSystem result = mockManager.create(galaxy, systemCoords, systemNames, i, random);
			mockContext.assertIsSatisfied();

			assertNotNull(result);
			assertEquals(expected, result);
		}
	}
}
