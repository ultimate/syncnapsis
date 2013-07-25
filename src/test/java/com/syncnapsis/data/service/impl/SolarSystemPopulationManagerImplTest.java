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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.jmock.Expectations;

import com.syncnapsis.data.dao.SolarSystemPopulationDao;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.utils.data.ExtendedRandom;

@TestCoversClasses({ SolarSystemPopulationManager.class, SolarSystemPopulationManagerImpl.class })
public class SolarSystemPopulationManagerImplTest extends
		GenericManagerImplTestCase<SolarSystemPopulation, Long, SolarSystemPopulationManager, SolarSystemPopulationDao>
{
	private SolarSystemInfrastructureManager	solarSystemInfrastructureManager;
	private ParameterManager					parameterManager;
	private BaseGameManager						securityManager;
	private final long							referenceTime	= 1234;

	private ExtendedRandom						random			= new ExtendedRandom();

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new SolarSystemPopulation());
		setDaoClass(SolarSystemPopulationDao.class);
		setMockDao(mockContext.mock(SolarSystemPopulationDao.class));
		setMockManager(new SolarSystemPopulationManagerImpl(mockDao, solarSystemInfrastructureManager, parameterManager));

		BaseGameManager securityManager = new BaseGameManager(this.securityManager);
		securityManager.setTimeProvider(new MockTimeProvider(referenceTime));
		((SolarSystemPopulationManagerImpl) mockManager).setSecurityManager(securityManager);
	}

	public void testGetByMatch() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByMatch", new ArrayList<SolarSystemPopulation>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetByParticipant() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByParticipant", new ArrayList<SolarSystemPopulation>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testSelectStartSystem() throws Exception
	{
		fail("unimplemented");
	}

	public void testRandomSelectStartSystems() throws Exception
	{
		fail("unimplemented");
	}

	public void testSpinoff() throws Exception
	{
		fail("unimplemented");
	}

	public void testResettle() throws Exception
	{
		fail("unimplemented");
	}

	public void testDestroy() throws Exception
	{
		final Date destructionDate = new Date(referenceTime);
		final EnumDestructionType destructionType = EnumDestructionType.destroyed;

		final SolarSystemPopulation in = new SolarSystemPopulation();
		in.setActivated(true);
		in.setDestructionDate(null);
		in.setDestructionType(null);

		final SolarSystemPopulation out = new SolarSystemPopulation();
		out.setActivated(false);
		out.setDestructionDate(destructionDate);
		out.setDestructionType(destructionType);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(out);
				will(returnValue(out));
			}
		});
		SolarSystemPopulation result = mockManager.destroy(in, destructionType, destructionDate);
		mockContext.assertIsSatisfied();
		assertEquals(out, result);
	}

	public void testMerge() throws Exception
	{
		long inf = 500;

		SolarSystemInfrastructure infrastructure = new SolarSystemInfrastructure();
		infrastructure.setPopulations(new ArrayList<SolarSystemPopulation>());
		infrastructure.setInfrastructure(inf);

		Participant p1 = new Participant();
		p1.setId(1L);
		Participant p2 = new Participant();
		p2.setId(2L);

		long pop11 = 100000;
		long pop12 = 2000;
		long pop13 = 3000;
		long pop21 = 10000;
		long pop22 = 5000;
		long pop23 = 4000;

		long inf12 = 1000;

		infrastructure.getPopulations().add(getPopulation(1L, pop11, 0, 1000, p1, true));
		infrastructure.getPopulations().add(getPopulation(2L, pop12, inf12, 1200, p1, true));
		infrastructure.getPopulations().add(getPopulation(3L, pop13, 0, 1400, p1, true));
		infrastructure.getPopulations().add(getPopulation(4L, pop21, 0, 1100, p2, true));
		infrastructure.getPopulations().add(getPopulation(5L, pop22, 0, 1150, p2, false));
		infrastructure.getPopulations().add(getPopulation(6L, pop23, 0, 1600, p2, true));

		int populationsToBeSaved = 0;
		for(final SolarSystemPopulation p : infrastructure.getPopulations())
		{
			if(p.isActivated() && p.getColonizationDate().before(new Date(referenceTime)))
			{
				populationsToBeSaved++;
				mockContext.checking(new Expectations() {
					{
						oneOf(mockDao).save(p);
						will(returnValue(p));
					}
				});
			}
		}
		assertEquals(3, populationsToBeSaved);

		Collections.shuffle(infrastructure.getPopulations());

		mockManager.merge(infrastructure);
		mockContext.assertIsSatisfied();

		Collections.sort(infrastructure.getPopulations(), new Comparator<SolarSystemPopulation>() {
			@Override
			public int compare(SolarSystemPopulation o1, SolarSystemPopulation o2)
			{
				return o1.getId().compareTo(o2.getId());
			}
		});

		// check the update of the infrastructure from storedInfrastructure
		assertEquals(inf12, infrastructure.getInfrastructure());
		// check general manipulations on populations
		for(long i = 0; i < infrastructure.getPopulations().size(); i++)
		{
			// assure correct order (for explicit population tests below)
			assertEquals(i + 1, (long) infrastructure.getPopulations().get((int) i).getId());
			// assure all stored infrastructure is removed
			assertEquals(0L, infrastructure.getPopulations().get((int) i).getStoredInfrastructure());

			// assure populations with value are not destroyed and vice-versa
			if(infrastructure.getPopulations().get((int) i).getPopulation() > 0)
			{
				assertNull(infrastructure.getPopulations().get((int) i).getDestructionDate());
				assertNull(infrastructure.getPopulations().get((int) i).getDestructionType());
			}
			else
			{
				assertEquals(infrastructure.getPopulations().get((int) i).getColonizationDate(), infrastructure.getPopulations().get((int) i).getDestructionDate());
				assertEquals(EnumDestructionType.merged, infrastructure.getPopulations().get((int) i).getDestructionType());
			}
		}
		// explicitly check the new pop-values
		assertEquals(pop11 + pop12, infrastructure.getPopulations().get(0).getPopulation());
		assertEquals(0, infrastructure.getPopulations().get(1).getPopulation());
		assertEquals(pop13, infrastructure.getPopulations().get(2).getPopulation());
		assertEquals(pop21, infrastructure.getPopulations().get(3).getPopulation());
		assertEquals(pop22, infrastructure.getPopulations().get(4).getPopulation());
		assertEquals(pop23, infrastructure.getPopulations().get(5).getPopulation());
	}

	public void testUpdateTravelSpeed() throws Exception
	{
		// don't test public updateTravelSpeed just test proctected version
		// TODO use speedTest
		fail("unimplemented");
	}

	private void speedTest(long timeToTravel, long timeToArrival, int travelSpeed, int travelDistance, double expectedProgress) throws Exception
	{
		final SolarSystemPopulation origin = getPopulation(0, 0, 0);
		final SolarSystemPopulation population = getPopulation(travelDistance, 0, 0);
		population.setOrigin(origin);

		long now = 1234;
		double startProgress = random.nextDouble(0, 1 - expectedProgress);
		Date speedChangeDate = new Date(now + timeToTravel);
		int newSpeed = random.nextInt(10, 100);

		population.setColonizationDate(new Date(now + timeToArrival));
		population.setTravelProgress(startProgress);
		population.setTravelProgressDate(new Date(now));
		population.setTravelSpeed(travelSpeed);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(population);
				will(returnValue(population));
			}
		});

		((SolarSystemPopulationManagerImpl) mockManager).updateTravelSpeed(population, newSpeed, speedChangeDate);

		mockContext.assertIsSatisfied();

		if(timeToTravel < timeToArrival)
		{
			assertEquals(startProgress + expectedProgress, population.getTravelProgress());
			assertEquals(speedChangeDate, population.getTravelProgressDate());
			assertEquals(newSpeed, population.getTravelSpeed());
		}
		else
		{
			assertEquals(1.0, population.getTravelProgress());
			assertEquals(population.getColonizationDate(), population.getTravelProgressDate());
			assertEquals(0, population.getTravelSpeed());
		}
	}

	private SolarSystemPopulation getPopulation(int x, int y, int z)
	{
		SolarSystemPopulation population = new SolarSystemPopulation();
		population.setInfrastructure(new SolarSystemInfrastructure());
		population.getInfrastructure().setSolarSystem(new SolarSystem());
		population.getInfrastructure().getSolarSystem().setCoords(new Vector.Integer(x, y, z));
		return population;
	}

	private SolarSystemPopulation getPopulation(long id, long pop, long inf, long colonizationDate, Participant participant, boolean activated)
	{
		SolarSystemPopulation population = new SolarSystemPopulation();
		population.setId(id);
		population.setActivated(activated);
		population.setPopulation(pop);
		population.setColonizationDate(new Date(colonizationDate));
		population.setParticipant(participant);
		population.setStoredInfrastructure(inf);
		return population;
	}
}
