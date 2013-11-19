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
import java.util.List;

import org.jmock.Expectations;

import com.syncnapsis.data.dao.SolarSystemPopulationDao;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.model.help.Vector.Integer;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.enums.EnumPopulationPriority;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.universe.Calculator;
import com.syncnapsis.utils.MathUtil;
import com.syncnapsis.utils.StringUtil;
import com.syncnapsis.utils.data.ExtendedRandom;

@TestCoversClasses({ SolarSystemPopulationManager.class, SolarSystemPopulationManagerImpl.class })
@TestExcludesMethods({ "isAccessible", "*etSecurityManager", "afterPropertiesSet", "*etCalculator" })
public class SolarSystemPopulationManagerImplTest extends
		GenericManagerImplTestCase<SolarSystemPopulation, Long, SolarSystemPopulationManager, SolarSystemPopulationDao>
{
	private SolarSystemInfrastructureManager	solarSystemInfrastructureManager;
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
		setMockManager(new SolarSystemPopulationManagerImpl(mockDao, solarSystemInfrastructureManager));

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

	public void testSpinoff() throws Exception
	{
		final SolarSystemInfrastructureManager mockSolarSystemInfrastructureManager = mockContext.mock(SolarSystemInfrastructureManager.class);
		SolarSystemPopulationManagerImpl mockManager = new SolarSystemPopulationManagerImpl(mockDao, mockSolarSystemInfrastructureManager);
		mockManager.setSecurityManager(((SolarSystemPopulationManagerImpl) this.mockManager).getSecurityManager());
		MockCalculator mockCalculator = new MockCalculator();
		((SolarSystemPopulationManagerImpl) mockManager).setCalculator(mockCalculator);
		mockCalculator.maxTravelDistance = 1000;

		int dist = 1000;
		int speed = 1000;
		long pop = 1000000;
		long deltaPop = pop * 2 / 3;
		long travelTime = 100000; // see MockCalculator
		EnumPopulationPriority attackPriority = EnumPopulationPriority.population;
		EnumPopulationPriority buildPriority = EnumPopulationPriority.balanced;

		Participant p1 = new Participant();
		p1.setId(1L);

		final SolarSystemInfrastructure infrastructure1 = new SolarSystemInfrastructure();
		infrastructure1.setId(1L);
		infrastructure1.setSolarSystem(new SolarSystem());
		infrastructure1.getSolarSystem().setId(1L);
		infrastructure1.getSolarSystem().setCoords(new Vector.Integer(0, 0, 0));
		infrastructure1.setInfrastructure(1000 * 1000 * 1000000L);

		final SolarSystemInfrastructure infrastructure2 = new SolarSystemInfrastructure();
		infrastructure2.setId(2L);
		infrastructure2.setSolarSystem(new SolarSystem());
		infrastructure2.getSolarSystem().setId(2L);
		infrastructure2.getSolarSystem().setCoords(new Vector.Integer(0, dist, 0));

		final SolarSystemInfrastructure infrastructure3 = new SolarSystemInfrastructure();
		infrastructure3.setId(3L);
		infrastructure3.setSolarSystem(new SolarSystem());
		infrastructure3.getSolarSystem().setId(3L);
		infrastructure3.getSolarSystem().setCoords(new Vector.Integer(0, dist * 2, 0));

		final SolarSystemPopulation origin = new SolarSystemPopulation();
		origin.setId(101L);
		origin.setActivated(true);
		origin.setAttackPriority(EnumPopulationPriority.balanced);
		origin.setBuildPriority(EnumPopulationPriority.infrastructure);
		origin.setColonizationDate(new Date(0));
		origin.setDestructionDate(null);
		origin.setDestructionType(null);
		origin.setInfrastructure(infrastructure1);
		origin.setLastUpdateDate(new Date(0));
		origin.setOrigin(null);
		origin.setParticipant(p1);
		origin.setPopulation(pop);
		origin.setStoredInfrastructure(0);
		origin.setTravelProgress(0);
		origin.setTravelProgressDate(null);
		origin.setTravelSpeed(0);

		final SolarSystemPopulation originModified = new SolarSystemPopulation();
		originModified.setId(101L);
		originModified.setActivated(true);
		originModified.setAttackPriority(EnumPopulationPriority.balanced);
		originModified.setBuildPriority(EnumPopulationPriority.infrastructure);
		originModified.setColonizationDate(new Date(0));
		originModified.setDestructionDate(null);
		originModified.setDestructionType(null);
		originModified.setInfrastructure(infrastructure1);
		originModified.setLastUpdateDate(new Date(referenceTime));
		originModified.setOrigin(null);
		originModified.setParticipant(p1);
		originModified.setPopulation(pop - deltaPop);
		originModified.setStoredInfrastructure(0);
		originModified.setTravelProgress(0);
		originModified.setTravelProgressDate(null);
		originModified.setTravelSpeed(0);

		final SolarSystemPopulation spinoffExpected = new SolarSystemPopulation();
		spinoffExpected.setActivated(true);
		spinoffExpected.setAttackPriority(attackPriority);
		spinoffExpected.setBuildPriority(buildPriority);
		spinoffExpected.setColonizationDate(new Date(referenceTime + travelTime));
		spinoffExpected.setDestructionDate(null);
		spinoffExpected.setDestructionType(null);
		spinoffExpected.setInfrastructure(infrastructure2);
		spinoffExpected.setLastUpdateDate(new Date(referenceTime));
		spinoffExpected.setOrigin(originModified);
		spinoffExpected.setOriginationDate(new Date(referenceTime));
		spinoffExpected.setParticipant(p1);
		spinoffExpected.setPopulation(deltaPop);
		spinoffExpected.setStoredInfrastructure(0);
		spinoffExpected.setTravelProgress(0);
		spinoffExpected.setTravelProgressDate(new Date(referenceTime));
		spinoffExpected.setTravelSpeed(speed);

		SolarSystemPopulation spinoff;

		// spinoff: distance ok
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(originModified);
				will(returnValue(originModified));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(spinoffExpected);
				will(returnValue(spinoffExpected));
			}
		});
		logger.debug("---------------------");
		logger.debug("origin          = " + StringUtil.toString(origin, 0));
		logger.debug("originModified  = " + StringUtil.toString(originModified, 0));
		logger.debug("spinoffExpected = " + StringUtil.toString(spinoffExpected, 0));
		spinoff = mockManager.spinoff(origin, infrastructure2, speed, deltaPop, attackPriority, buildPriority);
		mockContext.assertIsSatisfied();
		assertNotNull(spinoff);
		assertEquals(spinoffExpected, spinoff);
		assertEquals(originModified, origin);

		reset(origin, pop);

		// spinoff: distance too long
		try
		{
			spinoff = mockManager.spinoff(origin, infrastructure3, speed, deltaPop, attackPriority, buildPriority);
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		mockContext.assertIsSatisfied();
	}

	public void testResettle() throws Exception
	{
		final SolarSystemInfrastructureManager mockSolarSystemInfrastructureManager = mockContext.mock(SolarSystemInfrastructureManager.class);
		SolarSystemPopulationManagerImpl mockManager = new SolarSystemPopulationManagerImpl(mockDao, mockSolarSystemInfrastructureManager);
		mockManager.setSecurityManager(((SolarSystemPopulationManagerImpl) this.mockManager).getSecurityManager());
		MockCalculator mockCalculator = new MockCalculator();
		((SolarSystemPopulationManagerImpl) mockManager).setCalculator(mockCalculator);
		mockCalculator.maxTravelDistance = 1000;

		int dist = 1000;
		int speed = 1000;
		long pop = 1000000;
		long travelTime = 100000; // see MockCalculator

		Participant p1 = new Participant();
		p1.setId(1L);
		Participant p2 = new Participant();
		p2.setId(2L);

		final SolarSystemInfrastructure infrastructure1 = new SolarSystemInfrastructure();
		infrastructure1.setId(1L);
		infrastructure1.setSolarSystem(new SolarSystem());
		infrastructure1.getSolarSystem().setId(1L);
		infrastructure1.getSolarSystem().setCoords(new Vector.Integer(0, 0, 0));
		infrastructure1.setInfrastructure(1000 * 1000 * 1000000L);

		final SolarSystemInfrastructure infrastructure2 = new SolarSystemInfrastructure();
		infrastructure2.setId(2L);
		infrastructure2.setSolarSystem(new SolarSystem());
		infrastructure2.getSolarSystem().setId(2L);
		infrastructure2.getSolarSystem().setCoords(new Vector.Integer(0, dist, 0));

		final SolarSystemInfrastructure infrastructure3 = new SolarSystemInfrastructure();
		infrastructure3.setId(3L);
		infrastructure3.setSolarSystem(new SolarSystem());
		infrastructure3.getSolarSystem().setId(3L);
		infrastructure3.getSolarSystem().setCoords(new Vector.Integer(0, dist * 2, 0));

		final SolarSystemPopulation origin = new SolarSystemPopulation();
		origin.setId(101L);
		origin.setActivated(true);
		origin.setAttackPriority(EnumPopulationPriority.balanced);
		origin.setBuildPriority(EnumPopulationPriority.infrastructure);
		origin.setColonizationDate(new Date(0));
		origin.setDestructionDate(null);
		origin.setDestructionType(null);
		origin.setInfrastructure(infrastructure1);
		origin.setLastUpdateDate(new Date(0));
		origin.setOrigin(null);
		origin.setParticipant(p1);
		origin.setPopulation(pop);
		origin.setStoredInfrastructure(0);
		origin.setTravelProgress(0);
		origin.setTravelProgressDate(null);
		origin.setTravelSpeed(0);

		final SolarSystemPopulation neighbour = new SolarSystemPopulation();
		neighbour.setId(102L);
		neighbour.setActivated(true);
		neighbour.setAttackPriority(EnumPopulationPriority.population);
		neighbour.setBuildPriority(EnumPopulationPriority.population);
		neighbour.setColonizationDate(new Date(100));
		neighbour.setDestructionDate(null);
		neighbour.setDestructionType(null);
		neighbour.setInfrastructure(infrastructure1);
		neighbour.setOrigin(null);
		neighbour.setParticipant(p2);
		neighbour.setPopulation(2000000);
		neighbour.setStoredInfrastructure(0);
		neighbour.setTravelProgress(0);
		neighbour.setTravelProgressDate(null);
		neighbour.setTravelSpeed(0);

		infrastructure1.setPopulations(new ArrayList<SolarSystemPopulation>(2));
		infrastructure1.getPopulations().add(origin);
		infrastructure1.getPopulations().add(neighbour);

		final SolarSystemPopulation originModified = new SolarSystemPopulation();
		originModified.setId(101L);
		originModified.setActivated(false);
		originModified.setAttackPriority(EnumPopulationPriority.balanced);
		originModified.setBuildPriority(EnumPopulationPriority.infrastructure);
		originModified.setColonizationDate(new Date(0));
		originModified.setDestructionDate(new Date(referenceTime));
		originModified.setDestructionType(EnumDestructionType.givenUp);
		originModified.setInfrastructure(infrastructure1);
		originModified.setLastUpdateDate(new Date(referenceTime));
		originModified.setOrigin(null);
		originModified.setParticipant(p1);
		originModified.setPopulation(0);
		originModified.setStoredInfrastructure(0);
		originModified.setTravelProgress(0);
		originModified.setTravelProgressDate(null);
		originModified.setTravelSpeed(0);

		final SolarSystemPopulation resettledExpected = new SolarSystemPopulation();
		resettledExpected.setActivated(true);
		resettledExpected.setAttackPriority(EnumPopulationPriority.balanced);
		resettledExpected.setBuildPriority(EnumPopulationPriority.infrastructure);
		resettledExpected.setColonizationDate(new Date(referenceTime + travelTime));
		resettledExpected.setDestructionDate(null);
		resettledExpected.setDestructionType(null);
		resettledExpected.setInfrastructure(infrastructure2);
		resettledExpected.setLastUpdateDate(new Date(referenceTime));
		resettledExpected.setOrigin(originModified);
		resettledExpected.setOriginationDate(new Date(referenceTime));
		resettledExpected.setParticipant(p1);
		resettledExpected.setPopulation(origin.getPopulation());
		resettledExpected.setStoredInfrastructure(0);
		resettledExpected.setTravelProgress(0);
		resettledExpected.setTravelProgressDate(new Date(referenceTime));
		resettledExpected.setTravelSpeed(speed);

		SolarSystemPopulation resettled;

		// resettle: home, no exodus, distance ok
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(originModified);
				will(returnValue(originModified));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(resettledExpected);
				will(returnValue(resettledExpected));
			}
		});
		resettled = mockManager.resettle(origin, infrastructure2, speed, false);
		mockContext.assertIsSatisfied();
		assertNotNull(resettled);
		assertEquals(resettledExpected, resettled);
		assertEquals(originModified, origin);

		reset(origin, pop);

		// resettle: home, no exodus, distance too long
		try
		{
			resettled = mockManager.resettle(origin, infrastructure3, speed, false);
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		mockContext.assertIsSatisfied();

		// resettle: home, exodus, long distance ok
		resettledExpected.setInfrastructure(infrastructure3);
		resettledExpected.setStoredInfrastructure(origin.getInfrastructure().getInfrastructure() / 2);
		resettledExpected.setColonizationDate(new Date(referenceTime + 2 * travelTime));
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(originModified);
				will(returnValue(originModified));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(resettledExpected);
				will(returnValue(resettledExpected));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockSolarSystemInfrastructureManager).save(infrastructure1);
				will(returnValue(infrastructure1));
			}
		});
		resettled = mockManager.resettle(origin, infrastructure3, speed, true);
		mockContext.assertIsSatisfied();
		assertNotNull(resettled);
		assertEquals(resettledExpected, resettled);
		assertEquals(originModified, origin);
		assertEquals(0, infrastructure1.getInfrastructure());

		reset(origin, pop);
	}

	private void reset(SolarSystemPopulation population, long pop)
	{
		population.setPopulation(pop);
		population.setActivated(true);
		population.setDestructionDate(null);
		population.setDestructionType(null);
	}

	public void testDestroy() throws Exception
	{
		final Date destructionDate = new Date(referenceTime);
		final EnumDestructionType destructionType = EnumDestructionType.destroyed;

		final SolarSystemPopulation in = new SolarSystemPopulation();
		in.setActivated(true);
		in.setDestructionDate(null);
		in.setDestructionType(null);
		in.setLastUpdateDate(null);

		final SolarSystemPopulation out = new SolarSystemPopulation();
		out.setActivated(false);
		out.setDestructionDate(destructionDate);
		out.setDestructionType(destructionType);
		out.setLastUpdateDate(destructionDate);

		// no save
		// mockContext.checking(new Expectations() {
		// {
		// oneOf(mockDao).save(out);
		// will(returnValue(out));
		// }
		// });
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
				assertEquals(infrastructure.getPopulations().get((int) i).getColonizationDate(), infrastructure.getPopulations().get((int) i)
						.getDestructionDate());
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
		MockCalculator mockCalculator = new MockCalculator();
		((SolarSystemPopulationManagerImpl) mockManager).setCalculator(mockCalculator);

		// don't test public updateTravelSpeed just test proctected version

		speedTest(10000, 100000, 1000, 1000, 0.1);
		speedTest(10000, 11000, 1000, 1000, 0.1);
		speedTest(10000, 10000, 1000, 1000, 0.1);
		speedTest(10000, 5000, 1000, 1000, 0.05);

		speedTest(10000, 100000, 100, 1000, 0.01);
		speedTest(10000, 11000, 100, 1000, 0.01);
		speedTest(10000, 10000, 100, 1000, 0.01);
		speedTest(10000, 5000, 100, 1000, 0.005);

		speedTest(10000, 100000, 1000, 10000, 0.01);
		speedTest(10000, 11000, 1000, 10000, 0.01);
		speedTest(10000, 10000, 1000, 10000, 0.01);
		speedTest(10000, 5000, 1000, 10000, 0.005);
	}

	public void testSelectStartSystem() throws Exception
	{
		Participant p1 = new Participant();
		p1.setId(1L);
		Participant p2 = new Participant();
		p2.setId(2L);

		SolarSystemInfrastructure infrastructure = new SolarSystemInfrastructure();
		infrastructure.setId(1234L);
		infrastructure.setPopulations(new ArrayList<SolarSystemPopulation>(5));

		final SolarSystemPopulation pop1 = new SolarSystemPopulation();
		pop1.setActivated(true);
		pop1.setAttackPriority(EnumPopulationPriority.balanced);
		pop1.setBuildPriority(EnumPopulationPriority.balanced);
		pop1.setInfrastructure(infrastructure);
		pop1.setParticipant(p1);
		pop1.setPopulation(1000);
		final SolarSystemPopulation pop2 = new SolarSystemPopulation();
		pop2.setActivated(true);
		pop2.setAttackPriority(EnumPopulationPriority.balanced);
		pop2.setBuildPriority(EnumPopulationPriority.balanced);
		pop2.setInfrastructure(infrastructure);
		pop2.setParticipant(p2);
		pop2.setPopulation(2000);

		infrastructure.getPopulations().add(pop1);
		infrastructure.getPopulations().add(pop2);

		long population = 10000;
		SolarSystemPopulation changed;

		// update present population
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(pop1);
				will(returnValue(pop1));
			}
		});
		changed = mockManager.selectStartSystem(p1, infrastructure, population);
		mockContext.assertIsSatisfied();
		assertSame(pop1, changed);
		assertEquals(population, changed.getPopulation());
		
		// remove present population
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).delete(pop1);
			}
		});
		changed = mockManager.selectStartSystem(p1, infrastructure, 0);
		mockContext.assertIsSatisfied();
		assertNull(changed);
		
		// no population present
		infrastructure.getPopulations().clear();
		
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(pop1);
				will(returnValue(pop1));
			}
		});
		changed = mockManager.selectStartSystem(p1, infrastructure, population);
		mockContext.assertIsSatisfied();
		assertEquals(pop1, changed);
	}

	private void speedTest(long timeToTravel, long timeToArrival, int travelSpeed, int travelDistance, double expectedProgress) throws Exception
	{
		final SolarSystemPopulation origin = getPopulation(0, 0, 0);
		final SolarSystemPopulation population = getPopulation(travelDistance, 0, 0);
		population.setOrigin(origin);

		logger.debug("distance created = "
				+ MathUtil.distance(origin.getInfrastructure().getSolarSystem().getCoords(), population.getInfrastructure().getSolarSystem()
						.getCoords()));

		logger.debug("expected progress = " + expectedProgress);
		double startProgress = random.nextDouble(0, 1 - expectedProgress);
		if(timeToArrival <= timeToTravel)
			startProgress = 1 - expectedProgress;
		logger.debug("startprogress (randomly set to) = " + startProgress);
		Date speedChangeDate = new Date(referenceTime + timeToTravel);
		logger.debug("changing speed at time = " + speedChangeDate.getTime());
		int newSpeed = random.nextInt(10, 100);
		logger.debug("newspeed (randomly set to) = " + newSpeed);
		logger.debug("oldspeed = " + travelSpeed);

		population.setColonizationDate(new Date(referenceTime + timeToArrival));
		population.setTravelProgress(startProgress);
		population.setTravelProgressDate(new Date(referenceTime));
		population.setTravelSpeed(travelSpeed);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(population);
				will(returnValue(population));
			}
		});

		((SolarSystemPopulationManagerImpl) mockManager).updateTravelSpeed(population, newSpeed, speedChangeDate);

		mockContext.assertIsSatisfied();

		logger.debug("pop.progress = " + population.getTravelProgress());
		logger.debug("pop.progressDate = " + population.getTravelProgressDate().getTime());
		logger.debug("pop.speed = " + population.getTravelSpeed());

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
		population.setId((long) x + y + z);
		population.setInfrastructure(new SolarSystemInfrastructure());
		population.getInfrastructure().setId((long) x + y + z);
		population.getInfrastructure().setSolarSystem(new SolarSystem());
		population.getInfrastructure().getSolarSystem().setId((long) x + y + z);
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

	public void testGetHomePopulation() throws Exception
	{
		SolarSystemInfrastructure infrastructure = new SolarSystemInfrastructure();
		infrastructure.setPopulations(new ArrayList<SolarSystemPopulation>(20));

		Participant p1 = new Participant();
		Participant p2 = new Participant();
		Participant p3 = new Participant();

		infrastructure.getPopulations().add(getPopulation(1, 0, 0, 1001, p1, false));
		infrastructure.getPopulations().add(getPopulation(2, 0, 0, 1002, p2, false));
		infrastructure.getPopulations().add(getPopulation(3, 0, 0, 1003, p3, false));
		infrastructure.getPopulations().add(getPopulation(4, 0, 0, 1004, p1, false));
		infrastructure.getPopulations().add(getPopulation(5, 1000000, 0, 1005, p2, true));
		infrastructure.getPopulations().add(getPopulation(6, 0, 0, 1006, p2, false));
		infrastructure.getPopulations().add(getPopulation(7, 5000, 0, 1007, p1, true));
		infrastructure.getPopulations().add(getPopulation(8, 10000, 0, 1008, p3, true));
		infrastructure.getPopulations().add(getPopulation(9, 1000, 5, 1500, p1, true));

		Collections.shuffle(infrastructure.getPopulations());

		assertEquals(5L, (long) mockManager.getHomePopulation(infrastructure).getId());
	}
	
	public void testSimulate() throws Exception
	{
		// TODO
		fail("unimplemented");
	}

	private class MockCalculator implements Calculator
	{
		private Vector.Integer	size;
		private int				maxGap;
		private int				minGap;
		private int				avgGap;
		private int				standardTravelDistance;
		private double			maxTravelDistance;
		private long			maxMovablePopulation;

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.universe.Calculator#calculateSize(java.util.List)
		 */
		@Override
		public Vector.Integer calculateSize(List<Vector.Integer> coords)
		{
			return size;
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.universe.Calculator#calculateMaxGap(java.util.List)
		 */
		@Override
		public int calculateMaxGap(List<Vector.Integer> coords)
		{
			return maxGap;
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.universe.Calculator#calculateMinGap(java.util.List)
		 */
		@Override
		public int calculateMinGap(List<Integer> coords)
		{
			return minGap;
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.universe.Calculator#calculateAvgGap(java.util.List)
		 */
		@Override
		public int calculateAvgGap(List<Vector.Integer> coords)
		{
			return avgGap;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.syncnapsis.universe.Calculator#getStandardTravelDistance(com.syncnapsis.data.model
		 * .Galaxy)
		 */
		@Override
		public int getStandardTravelDistance(Galaxy galaxy)
		{
			return standardTravelDistance;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.syncnapsis.universe.Calculator#calculateMaxTravelDistance(com.syncnapsis.data.model
		 * .SolarSystemPopulation, long, boolean)
		 */
		@Override
		public double calculateMaxTravelDistance(SolarSystemPopulation origin, long movedPopulation, boolean exodus)
		{
			return exodus ? 2 * maxTravelDistance : maxTravelDistance;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.syncnapsis.universe.Calculator#calculateMaxMovablePopulation(com.syncnapsis.data.
		 * model.SolarSystemPopulation, double)
		 */
		@Override
		public long calculateMaxMovablePopulation(SolarSystemPopulation origin, double travelDistance)
		{
			return maxMovablePopulation;
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.universe.Calculator#calculateTravelTime(com.syncnapsis.data.model.
		 * SolarSystemInfrastructure, com.syncnapsis.data.model.SolarSystemInfrastructure, int)
		 */
		@Override
		public long calculateTravelTime(SolarSystemInfrastructure origin, SolarSystemInfrastructure target, int travelSpeed)
		{
			double dist = MathUtil.distance(origin.getSolarSystem().getCoords(), target.getSolarSystem().getCoords());
			logger.debug("dist=" + dist);
			long time = (long) (100000.0 * dist / (double) travelSpeed);
			logger.debug("time=" + time);
			return time;
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.universe.Calculator#getMaxPopulation()
		 */
		@Override
		public long getMaxPopulation()
		{
			return 1000000000000L;
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.universe.Calculator#getMaxPopulation(com.syncnapsis.data.model.
		 * SolarSystemInfrastructure)
		 */
		@Override
		public long getMaxPopulation(SolarSystemInfrastructure infrastructure)
		{
			return 1000000L * infrastructure.getHabitability() * infrastructure.getSize();
		}

		/* (non-Javadoc)
		 * @see com.syncnapsis.universe.Calculator#calculateAttackStrength(double, long)
		 */
		@Override
		public double calculateAttackStrength(double speedFactor, long population)
		{
			return 0;
		}

		/* (non-Javadoc)
		 * @see com.syncnapsis.universe.Calculator#calculateBuildStrength(double, long, long)
		 */
		@Override
		public double calculateBuildStrength(double speedFactor, long population, long maxPopulation)
		{
			return 0;
		}

		/* (non-Javadoc)
		 * @see com.syncnapsis.universe.Calculator#calculateInfrastructureBuildInfluence(long, long)
		 */
		@Override
		public double calculateInfrastructureBuildInfluence(long population, long infrastructure)
		{
			return 0;
		}

		/* (non-Javadoc)
		 * @see com.syncnapsis.universe.Calculator#getSpeedFactor(int)
		 */
		@Override
		public double getSpeedFactor(int speed)
		{
			return 0;
		}
	}
}
