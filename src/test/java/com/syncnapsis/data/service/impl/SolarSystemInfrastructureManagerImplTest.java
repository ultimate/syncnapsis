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
import java.util.Date;

import org.jmock.Expectations;

import com.syncnapsis.data.dao.SolarSystemInfrastructureDao;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.data.ExtendedRandom;
import com.syncnapsis.utils.data.Generator;

@TestCoversClasses({ SolarSystemInfrastructureManager.class, SolarSystemInfrastructureManagerImpl.class })
@TestExcludesMethods({ "afterPropertiesSet", "*etInfrastructureGenerator", "*etSecurityManager"})
public class SolarSystemInfrastructureManagerImplTest extends
		GenericManagerImplTestCase<SolarSystemInfrastructure, Long, SolarSystemInfrastructureManager, SolarSystemInfrastructureDao>
{
	private BaseGameManager		securityManager;
	private static final long	referenceTime	= 1234;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new SolarSystemInfrastructure());
		setDaoClass(SolarSystemInfrastructureDao.class);
		setMockDao(mockContext.mock(SolarSystemInfrastructureDao.class));
		setMockManager(new SolarSystemInfrastructureManagerImpl(mockDao));

		BaseGameManager securityManager = new BaseGameManager(this.securityManager);
		securityManager.setTimeProvider(new MockTimeProvider(referenceTime));
		((SolarSystemInfrastructureManagerImpl) mockManager).setSecurityManager(securityManager);
	}

	public void testGetByMatch() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByMatch", new ArrayList<SolarSystemInfrastructure>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	@SuppressWarnings("unchecked")
	public void testInitialize() throws Exception
	{
		final Generator<SolarSystemInfrastructure> mockInfrastructureGenerator = mockContext.mock(Generator.class);
		((SolarSystemInfrastructureManagerImpl) mockManager).setInfrastructureGenerator(mockInfrastructureGenerator);

		final ExtendedRandom random = new ExtendedRandom();
		final Match match = new Match();
		final SolarSystem system = new SolarSystem();
		final SolarSystemInfrastructure infrastructure = new SolarSystemInfrastructure();

		mockContext.checking(new Expectations() {
			{
				oneOf(mockInfrastructureGenerator).generate(random, match, system);
				will(returnValue(infrastructure));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(infrastructure);
				will(returnValue(infrastructure));
			}
		});

		SolarSystemInfrastructure result = mockManager.initialize(match, system, random);
		assertSame(infrastructure, result);
		assertEquals(new Date(referenceTime), result.getLastUpdateDate());
	}
}
