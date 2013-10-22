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
package com.syncnapsis.universe;

import java.util.LinkedList;
import java.util.List;

import org.jmock.Expectations;

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

/**
 * @author ultimate
 */
public class CalculatorImplTest extends LoggerTestCase
{
	private ParameterManager	parameterManager;
	private CalculatorImpl		calculator;

	private static final long	popMax	= 1000000000000L;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		parameterManager = mockContext.mock(ParameterManager.class);
		mockContext.checking(new Expectations() {
			{
				allowing(parameterManager).getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX);
				will(returnValue(1000));
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(parameterManager).getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX);
				will(returnValue(1000));
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(parameterManager).getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR);
				will(returnValue(1000000));
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(parameterManager).getDouble(UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR);
				will(returnValue(10.0));
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(parameterManager).getDouble(UniverseConquestConstants.PARAM_TRAVEL_EXODUS_FACTOR);
				will(returnValue(20.0));
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(parameterManager).getDouble(UniverseConquestConstants.PARAM_FACTOR_ATTACK);
				will(returnValue(0.01));
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(parameterManager).getDouble(UniverseConquestConstants.PARAM_FACTOR_BUILD);
				will(returnValue(5.0));
			}
		});

		calculator = new CalculatorImpl(parameterManager);
	}

	public void testGetStandardTravelDistance() throws Exception
	{
		Galaxy galaxy = new Galaxy();
		galaxy.setMaxGap(1234);

		assertEquals(galaxy.getMaxGap(), calculator.getStandardTravelDistance(galaxy));
	}

	public void testCalculateMaxTravelDistance() throws Exception
	{
		SolarSystemPopulation origin = new SolarSystemPopulation();
		origin.setInfrastructure(new SolarSystemInfrastructure());
		origin.getInfrastructure().setSolarSystem(new SolarSystem());
		origin.getInfrastructure().getSolarSystem().setGalaxy(new Galaxy());

		// gap = 1
		origin.getInfrastructure().getSolarSystem().getGalaxy().setMaxGap(1);

		origin.getInfrastructure().setInfrastructure(1);
		assertEquals(0.0, calculator.calculateMaxTravelDistance(origin, popMax, false));
		assertEquals(5.0, calculator.calculateMaxTravelDistance(origin, 1, false));
		assertEquals(0.0, calculator.calculateMaxTravelDistance(origin, popMax, true));
		assertEquals(10.0, calculator.calculateMaxTravelDistance(origin, 1, true));

		origin.getInfrastructure().setInfrastructure(popMax);
		assertEquals(10.0, calculator.calculateMaxTravelDistance(origin, 1, false));
		assertEquals(5.0, calculator.calculateMaxTravelDistance(origin, popMax, false));
		assertEquals(20.0, calculator.calculateMaxTravelDistance(origin, 1, true));
		assertEquals(10.0, calculator.calculateMaxTravelDistance(origin, popMax, true));

		// other gap
		int gap = 1234;
		origin.getInfrastructure().getSolarSystem().getGalaxy().setMaxGap(gap);

		origin.getInfrastructure().setInfrastructure(1);
		assertEquals(gap * 0.0, calculator.calculateMaxTravelDistance(origin, popMax, false));
		assertEquals(gap * 5.0, calculator.calculateMaxTravelDistance(origin, 1, false));
		assertEquals(gap * 0.0, calculator.calculateMaxTravelDistance(origin, popMax, true));
		assertEquals(gap * 10.0, calculator.calculateMaxTravelDistance(origin, 1, true));

		origin.getInfrastructure().setInfrastructure(popMax);
		assertEquals(gap * 10.0, calculator.calculateMaxTravelDistance(origin, 1, false));
		assertEquals(gap * 5.0, calculator.calculateMaxTravelDistance(origin, popMax, false));
		assertEquals(gap * 20.0, calculator.calculateMaxTravelDistance(origin, 1, true));
		assertEquals(gap * 10.0, calculator.calculateMaxTravelDistance(origin, popMax, true));
	}

	public void testCalculateMaxMovablePopulation() throws Exception
	{
		SolarSystemPopulation origin = new SolarSystemPopulation();
		origin.setInfrastructure(new SolarSystemInfrastructure());
		origin.getInfrastructure().setSolarSystem(new SolarSystem());
		origin.getInfrastructure().getSolarSystem().setGalaxy(new Galaxy());
		origin.setPopulation(popMax);

		// gap = 1
		origin.getInfrastructure().getSolarSystem().getGalaxy().setMaxGap(1);

		origin.getInfrastructure().setInfrastructure(1);
		assertEquals(popMax, calculator.calculateMaxMovablePopulation(origin, 0));
		assertEquals(1, calculator.calculateMaxMovablePopulation(origin, 5));
		assertEquals(0, calculator.calculateMaxMovablePopulation(origin, 10));

		origin.getInfrastructure().setInfrastructure(popMax);
		assertEquals(popMax, calculator.calculateMaxMovablePopulation(origin, 0));
		assertEquals(popMax, calculator.calculateMaxMovablePopulation(origin, 5));
		assertEquals(1, calculator.calculateMaxMovablePopulation(origin, 10));

		// other gap
		int gap = 1234;
		origin.getInfrastructure().getSolarSystem().getGalaxy().setMaxGap(gap);

		origin.getInfrastructure().setInfrastructure(1);
		assertEquals(popMax, calculator.calculateMaxMovablePopulation(origin, 0));
		assertEquals(1, calculator.calculateMaxMovablePopulation(origin, 5 * gap));
		assertEquals(0, calculator.calculateMaxMovablePopulation(origin, 10 * gap));

		origin.getInfrastructure().setInfrastructure(popMax);
		assertEquals(popMax, calculator.calculateMaxMovablePopulation(origin, 0));
		assertEquals(popMax, calculator.calculateMaxMovablePopulation(origin, 5 * gap));
		assertEquals(1, calculator.calculateMaxMovablePopulation(origin, 10 * gap));

		// other population
		origin.setPopulation(123456789);

		origin.getInfrastructure().setInfrastructure(1);
		assertEquals(origin.getPopulation(), calculator.calculateMaxMovablePopulation(origin, 0));
		assertEquals(1, calculator.calculateMaxMovablePopulation(origin, 5 * gap));
		assertEquals(0, calculator.calculateMaxMovablePopulation(origin, 10 * gap));

		origin.getInfrastructure().setInfrastructure(popMax);
		assertEquals(origin.getPopulation(), calculator.calculateMaxMovablePopulation(origin, 0));
		assertEquals(origin.getPopulation(), calculator.calculateMaxMovablePopulation(origin, 5 * gap));
		assertEquals(1, calculator.calculateMaxMovablePopulation(origin, 10 * gap));
	}

	public void testCalculateTravelTime() throws Exception
	{
		fail("unimplemented");
	}

	public void testGetMaxPopulation() throws Exception
	{
		assertEquals(1000000000000L, calculator.getMaxPopulation());

		int hab = 234;
		int size = 567;
		SolarSystemInfrastructure infrastructure = new SolarSystemInfrastructure();
		infrastructure.setHabitability(hab);
		infrastructure.setSize(size);

		assertEquals(hab * size * 1000000L, calculator.getMaxPopulation(infrastructure));
	}

	public void testGetMaxPopulationExponent() throws Exception
	{
		assertEquals(12.0, calculator.getMaxPopulationExponent());
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

		Vector.Integer size = calculator.calculateSize(coords);
		assertEquals(xSize, (int) size.getX());
		assertEquals(ySize, (int) size.getY());
		assertEquals(zSize, (int) size.getZ());
	}

	@TestCoversMethods({ "calculateMaxGap", "calculateMinGap" })
	public void testCalculateMaxMinGap() throws Exception
	{
		List<Vector.Integer> coords = new LinkedList<Vector.Integer>();
		coords.add(new Vector.Integer(100, 0, 100));
		coords.add(new Vector.Integer(-100, 0, 100));

		assertEquals(200, calculator.calculateMaxGap(coords));
		assertEquals(200, calculator.calculateMinGap(coords));

		coords.add(new Vector.Integer(100, 0, 0));

		assertEquals(200, calculator.calculateMaxGap(coords));
		assertEquals(100, calculator.calculateMinGap(coords));

		coords.add(new Vector.Integer(1000, 0, 0));

		assertEquals(900, calculator.calculateMaxGap(coords));
		assertEquals(100, calculator.calculateMinGap(coords));

		coords.add(new Vector.Integer(1000000000, 0, 0)); // test there is no integer overflow

		assertEquals(999999000, calculator.calculateMaxGap(coords));
		assertEquals(100, calculator.calculateMinGap(coords));

	}

	public void testCalculateAvgGap() throws Exception
	{
		List<Vector.Integer> coords = new LinkedList<Vector.Integer>();
		coords.add(new Vector.Integer(100, 0, 100));
		coords.add(new Vector.Integer(-100, 0, -100));

		assertEquals(283, calculator.calculateAvgGap(coords)); // sqrt(2)*200

		coords.add(new Vector.Integer(100, 0, -100));

		assertEquals(200, calculator.calculateAvgGap(coords));

		coords.add(new Vector.Integer(-100, 0, 100));

		assertEquals(200, calculator.calculateAvgGap(coords));
	}

	public void testGetSpeedFactor() throws Exception
	{
		assertEquals(0.1, calculator.getSpeedFactor(-1));
		assertEquals(1.0, calculator.getSpeedFactor(0));
		assertEquals(10.0, calculator.getSpeedFactor(1));
		assertEquals(100.0, calculator.getSpeedFactor(2));
		assertEquals(1000.0, calculator.getSpeedFactor(3));
	}

	public void testCalculateAttackStrength()
	{
		assertEquals(0.0, calculator.calculateAttackStrength(10, 0));

		assertEquals(100000.0, calculator.calculateAttackStrength(10, 1000000));
		assertEquals(1000000.0, calculator.calculateAttackStrength(100, 1000000));
		assertEquals(10000000.0, calculator.calculateAttackStrength(1000, 1000000));

		assertEquals(2000.0, calculator.calculateAttackStrength(100, 2000));
		assertEquals(20000.0, calculator.calculateAttackStrength(100, 20000));
		assertEquals(200000.0, calculator.calculateAttackStrength(100, 200000));
	}

	public void testCalculateBuildStrength()
	{
		long maxPop = 25000000;
		assertEquals(0.0, calculator.calculateBuildStrength(1000, 0, maxPop));
		assertEquals(0.0, calculator.calculateBuildStrength(1000, maxPop, maxPop));
		assertEquals(maxPop / 4.0, calculator.calculateBuildStrength(1000, maxPop / 2, maxPop));
		assertEquals(calculator.calculateBuildStrength(1000, maxPop / 2 + 1, maxPop), calculator.calculateBuildStrength(1000, maxPop / 2 - 1, maxPop));
		assertTrue(calculator.calculateBuildStrength(1000, maxPop / 2 - 1, maxPop) < calculator.calculateBuildStrength(1000, maxPop / 2, maxPop));

		assertEquals(maxPop / 40.0, calculator.calculateBuildStrength(100, maxPop / 2, maxPop));
		assertEquals(maxPop / 0.4, calculator.calculateBuildStrength(10000, maxPop / 2, maxPop));
	}

	public void testCalculateBuildStrength2()
	{
		long maxMaxPopulation = calculator.getMaxPopulation();
		for(int i = 0; i <= 5; i++)
		{
			simulateBuildStrength(i, maxMaxPopulation);
			simulateBuildStrength(i, maxMaxPopulation / 2);
			simulateBuildStrength(i, maxMaxPopulation / 4);
		}
	}

	private void simulateBuildStrength(int speed, long maxPop)
	{
		logger.debug("simulating build strength - speed " + speed + " - maxPop = " + maxPop);
		long pop = 10000000;
		double speedFactor = calculator.getSpeedFactor(speed);
		long start = System.currentTimeMillis();

		long minute = 10L * 60;
		long hour = minute * 60;
		long year = hour * 24 * 365;

		int tick = 0;
		boolean reached01 = false;
		boolean reached10 = false;
		boolean reached25 = false;
		boolean reached50 = false;
		boolean reached75 = false;
		boolean reached90 = false;
		boolean reached99 = false;
		while(!reached99)
		{
			// if(tick % minute == 0)
			// logger.debug(tick + " : " + pop);

			pop += calculator.calculateBuildStrength(speedFactor, pop, maxPop);
			tick++;

			if(!reached01 && pop >= maxPop * 0.01)
			{
				reached01 = true;
				logger.info("01% reached @ tick " + tick);
			}
			else if(!reached10 && pop >= maxPop * 0.10)
			{
				reached10 = true;
				logger.info("10% reached @ tick " + tick);
			}
			else if(!reached25 && pop >= maxPop * 0.25)
			{
				reached25 = true;
				logger.info("25% reached @ tick " + tick);
			}
			else if(!reached50 && pop >= maxPop * 0.50)
			{
				reached50 = true;
				logger.info("50% reached @ tick " + tick);
			}
			else if(!reached75 && pop >= maxPop * 0.75)
			{
				reached75 = true;
				logger.info("75% reached @ tick " + tick);
			}
			else if(!reached90 && pop >= maxPop * 0.90)
			{
				reached90 = true;
				logger.info("90% reached @ tick " + tick);
			}
			else if(!reached99 && pop >= maxPop * 0.99)
			{
				reached99 = true;
				logger.info("99% reached @ tick " + tick);
			}
			if(tick > year)
				break;
		}
		long end = System.currentTimeMillis();
		logger.debug("time needed: " + (end - start));
		logger.debug("speed: " + ((double) tick * 1000 / (end-start)) + " ticks/second");
	}

	public void testCalculateInfrastructureBuildInfluence()
	{
		long maxPop = 25000000;
		assertEquals(1.5, calculator.calculateInfrastructureBuildInfluence(0, maxPop));

		assertEquals(1.25, calculator.calculateInfrastructureBuildInfluence((long) (maxPop / 1e6), maxPop));

		assertEquals(1.0, calculator.calculateInfrastructureBuildInfluence(maxPop, maxPop));
		assertEquals(1.0, calculator.calculateInfrastructureBuildInfluence(maxPop / 2, maxPop / 2));

		assertEquals(0.75, calculator.calculateInfrastructureBuildInfluence(maxPop, (long) (maxPop / 1e6)));

		assertEquals(0.5, calculator.calculateInfrastructureBuildInfluence(maxPop, 0));
	}
}
