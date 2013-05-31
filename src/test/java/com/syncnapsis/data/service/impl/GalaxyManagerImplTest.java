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
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.dao.GalaxyDao;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.data.service.PlayerManager;
import com.syncnapsis.data.service.SolarSystemManager;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.providers.PlayerProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.data.ExtendedRandom;
import com.syncnapsis.utils.data.VectorGenerator;

@TestCoversClasses({ GalaxyManager.class, GalaxyManagerImpl.class })
@TestExcludesMethods({ "afterPropertiesSet", "*etSecurityManager" })
public class GalaxyManagerImplTest extends GenericNameManagerImplTestCase<Galaxy, Long, GalaxyManager, GalaxyDao>
{
	private SolarSystemManager	solarSystemManager;
	private PlayerProvider		playerProvider;
	private SessionProvider		sessionProvider;
	private PlayerManager		playerManager;
	private BaseGameManager		securityManager;

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

	@TestCoversMethods("create")
	public void testCreate_fromList() throws Exception
	{

		final String name = "new galaxy";
		final long seed = "my seed".hashCode();
		final Player creator = playerManager.getAll().get(0);
		final long time = 12345678;
		final int systemCount = 10;
		final ExtendedRandom random1 = new ExtendedRandom(seed);
		final ExtendedRandom random2 = new ExtendedRandom(time);

		Vector.Integer size = new Vector.Integer(100, 100, 100);
		VectorGenerator generator = new VectorGenerator(size);
		final List<Vector.Integer> systemCoords = new ArrayList<Vector.Integer>();
		for(int i = 0; i < systemCount; i++)
			systemCoords.add(generator.generate());

		final List<String> systemNames = Arrays.asList(new String[] { "alpha", "beta", "gamma", "delta", "epsilon", "eta", "zeta" });

		BaseGameManager securityManager = new BaseGameManager(this.securityManager);
		securityManager.setTimeProvider(new MockTimeProvider(time));

		final SolarSystemManager mockSolarSystemManager = mockContext.mock(SolarSystemManager.class);
		GalaxyManagerImpl mockManager = new GalaxyManagerImpl(mockDao, mockSolarSystemManager);
		mockManager.setSecurityManager(securityManager);
		mockManager.afterPropertiesSet();

		sessionProvider.set(new MockHttpSession());
		playerProvider.set(creator);
		
		// seed given
		final Galaxy expected = new Galaxy();
		expected.setActivated(true);
		expected.setCreationDate(new Date(time));
		expected.setCreator(creator);
		expected.setName(name);
		expected.setSeed(seed);
		expected.setSize(size);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(expected);
				will(returnValue(expected));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).get(expected.getId());
				will(returnValue(expected));
			}
		});
		for(int i = 0; i < systemCount; i++)
		{
			final int j = i;
			mockContext.checking(new Expectations() {
				{
					// random is not same... (or equal)
					oneOf(mockSolarSystemManager).create(expected, systemCoords, systemNames, j, random1);
					will(returnValue(new SolarSystem()));
				}
			});
		}

		Galaxy result1 = ((GalaxyManagerImpl) mockManager).create(name, systemCoords, systemNames, seed, size);

		mockContext.assertIsSatisfied();
		assertNotNull(result1);
		assertEquals(expected, result1);
		
		// NO seed given
		expected.setSeed(time);
		
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(expected);
				will(returnValue(expected));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).get(expected.getId());
				will(returnValue(expected));
			}
		});
		for(int i = 0; i < systemCount; i++)
		{
			final int j = i;
			mockContext.checking(new Expectations() {
				{
					oneOf(mockSolarSystemManager).create(expected, systemCoords, systemNames, j, random2);
					will(returnValue(new SolarSystem()));
				}
			});
		}
		
		Galaxy galaxy = ((GalaxyManagerImpl) mockManager).create(name, systemCoords, systemNames, null, size);
		
		mockContext.assertIsSatisfied();
		assertNotNull(galaxy);
		assertEquals(expected, galaxy);

	}

	@TestCoversMethods("create")
	public void testCreate_fromConfiguration() throws Exception
	{
		// TODO real test when implemented
		try
		{
			mockManager.create(null);
			fail("expected Exception not occurred");
		}
		catch(UnsupportedOperationException e)
		{
			assertNotNull(e);
			assertEquals("not implemented yet", e.getMessage());
		}
	}

	public void testCalculateSize() throws Exception
	{
		// example size...
		int xSize = 110;
		int ySize = 220;
		int zSize = 50;

		List<Vector.Integer> coords = new LinkedList<Vector.Integer>();
		coords.add(new Vector.Integer(-34, -98, -78)); // extreme corner #1
		coords.add(new Vector.Integer(68, 117, -29)); // extreme corner #2

		Vector.Integer size = ((GalaxyManagerImpl) mockManager).calculateSize(coords);
		assertEquals(xSize, (int) size.getX());
		assertEquals(ySize, (int) size.getY());
		assertEquals(zSize, (int) size.getZ());
	}
}
