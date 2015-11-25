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
package com.syncnapsis.universe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.enums.EnumPopulationPriority;
import com.syncnapsis.enums.EnumVictoryCondition;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.utils.StringUtil;
import com.syncnapsis.utils.constants.Constant;
import com.syncnapsis.utils.constants.ConstantLoader;
import com.syncnapsis.utils.data.ExtendedRandom;

/**
 * @author ultimate
 */
public class CalculatorImplTest extends LoggerTestCase
{
	private CalculatorImpl		calculator;

	private boolean				testCalculateBuildStrength2	= false;

	private static final long	popMax						= 1000000000000L;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		ConstantLoader<String> constantLoader = new ConstantLoader<String>(String.class) {
			@Override
			public void load(Constant<String> constant)
			{
				if(constant == UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX)
					constant.define("1000");
				else if(constant == UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX)
					constant.define("1000");
				else if(constant == UniverseConquestConstants.PARAM_SOLARSYSTEM_HEAT_MAX)
					constant.define("1000");
				else if(constant == UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR)
					constant.define("1000000");
				else if(constant == UniverseConquestConstants.PARAM_SOLARSYSTEM_HEAT_FACTOR)
					constant.define("3.2");
				else if(constant == UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR)
					constant.define("10.0");
				else if(constant == UniverseConquestConstants.PARAM_TRAVEL_EXODUS_FACTOR)
					constant.define("20.0");
				else if(constant == UniverseConquestConstants.PARAM_FACTOR_ATTACK)
					constant.define("0.06");
				else if(constant == UniverseConquestConstants.PARAM_FACTOR_BUILD)
					constant.define("0.08");
				else if(constant == UniverseConquestConstants.PARAM_TRAVEL_SPEED_MAX)
					constant.define("1000");
				else if(constant == UniverseConquestConstants.PARAM_TRAVEL_SPEED_MIN)
					constant.define("100");
				else if(constant == UniverseConquestConstants.PARAM_TRAVEL_TIME_FACTOR)
					constant.define("0.7");
				else if(constant == UniverseConquestConstants.PARAM_FACTOR_ATTACK_RANDOMIZE)
					constant.define("0.25");
				else if(constant == UniverseConquestConstants.PARAM_FACTOR_BUILD_RANDOMIZE)
					constant.define("1.1");
				else if(constant == UniverseConquestConstants.PARAM_NORM_TICK_LENGTH)
					constant.define("100");
				else if(constant == UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABLE_HEAT_MIN)
					constant.define("0.2");
				else if(constant == UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABLE_HEAT_MAX)
					constant.define("0.8");
				else if(constant == UniverseConquestConstants.PARAM_PRIORITY_FULL)
					constant.define("1.0");
				else if(constant == UniverseConquestConstants.PARAM_PRIORITY_HIGH)
					constant.define("0.75");
				else if(constant == UniverseConquestConstants.PARAM_PRIORITY_MEDIUM)
					constant.define("0.5");
				else if(constant == UniverseConquestConstants.PARAM_PRIORITY_LOW)
					constant.define("0.25");
				else if(constant == UniverseConquestConstants.PARAM_PRIORITY_NONE)
					constant.define("0.0");
			}
		};

		constantLoader.setConstantClasses(Arrays.asList(new Class<?>[] { UniverseConquestConstants.class }));
		constantLoader.afterPropertiesSet();

		calculator = new CalculatorImpl();
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
		int minSpeed = UniverseConquestConstants.PARAM_TRAVEL_SPEED_MIN.asInt();
		int maxSpeed = UniverseConquestConstants.PARAM_TRAVEL_SPEED_MAX.asInt();

		int maxGap = 50;
		int stepSize = maxGap / 2;

		Galaxy galaxy = new Galaxy();
		galaxy.setMaxGap(maxGap);

		Match match = new Match();
		match.setSpeed(0);

		SolarSystem system1 = new SolarSystem();
		system1.setGalaxy(galaxy);
		SolarSystem system2 = new SolarSystem();
		system2.setGalaxy(galaxy);

		SolarSystemInfrastructure origin = new SolarSystemInfrastructure();
		origin.setSolarSystem(system1);
		origin.setMatch(match);
		SolarSystemInfrastructure target = new SolarSystemInfrastructure();
		target.setSolarSystem(system2);
		target.setMatch(match);

		system1.setCoords(new Vector.Integer(0, 0, 0));

		long travelTime;
		long expected;
		for(int travelSpeed = minSpeed; travelSpeed <= maxSpeed; travelSpeed += minSpeed)
		{
			long stdTravelTime = calculator.calculateStandardTravelTime(match, travelSpeed);

			for(int dist = stepSize; dist <= maxGap * 10; dist += stepSize)
			{
				system2.setCoords(new Vector.Integer(dist, 0, 0));

				expected = (long) (((double) dist / maxGap) * stdTravelTime);
				travelTime = calculator.calculateTravelTime(origin, target, travelSpeed);
				logger.debug("dist = " + dist + " speed = " + travelSpeed + " travel time = " + travelTime + " (" + StringUtil.toString(travelTime)
						+ ") expected = " + expected);
				assertTrue(Math.abs(expected - travelTime) <= 1);
			}
		}
	}
	
	public void testCalculateStandardTravelTime() throws Exception
	{

		int minSpeed = UniverseConquestConstants.PARAM_TRAVEL_SPEED_MIN.asInt();
		int maxSpeed = UniverseConquestConstants.PARAM_TRAVEL_SPEED_MAX.asInt();

		int maxGap = 50;

		Galaxy galaxy = new Galaxy();
		galaxy.setMaxGap(maxGap);

		Match match = new Match();
		match.setSpeed(0);
		
		long stdTime = (long) (125000000L * UniverseConquestConstants.PARAM_TRAVEL_TIME_FACTOR.asDouble());
		
		long travelTime;
		long expected;
		for(int travelSpeed = minSpeed; travelSpeed <= maxSpeed; travelSpeed += minSpeed)
		{
				expected = (long) (stdTime / ((double) travelSpeed / maxSpeed));
				travelTime = calculator.calculateStandardTravelTime(match, travelSpeed);
				logger.debug("speed = " + travelSpeed + " travel time = " + travelTime + " (" + StringUtil.toString(travelTime)
						+ ") expected = " + expected);
				assertTrue(Math.abs(expected - travelTime) <= 1);
		}
	}
	
	public void testCalculateVictoryTimeout() throws Exception
	{
		CalculatorImpl mockCalculator = new CalculatorImpl() {
			public long calculateStandardTravelTime(Match match, int maxSpeed)
			{
				return 1000;
			}
		};
		
		double dominationFactor = 10.0;
		double exterminationFactor = 15.0;
		// define constants here, since this is no DB test and hence parameters are not loaded
		UniverseConquestConstants.PARAM_VICTORY_DOMINATION_TIMEOUT.define("" + dominationFactor);
		UniverseConquestConstants.PARAM_VICTORY_EXTERMINATION_TIMEOUT.define("" + exterminationFactor);
		
		Match match = new Match();
		
		long stdTravelTime = mockCalculator.calculateStandardTravelTime(match, 1000);
		long timeout;
		
		// vendetta => no timeout
		match.setVictoryCondition(EnumVictoryCondition.vendetta);
		timeout = mockCalculator.calculateVictoryTimeout(match);
		assertEquals(0, timeout);
		
		// domination => stdTravelTime * 10
		match.setVictoryCondition(EnumVictoryCondition.domination);
		timeout = mockCalculator.calculateVictoryTimeout(match);
		assertEquals((long) (stdTravelTime * dominationFactor), timeout);

		// extermination => stdTravelTime * 10
		match.setVictoryCondition(EnumVictoryCondition.extermination);
		timeout = mockCalculator.calculateVictoryTimeout(match);
		assertEquals((long) (stdTravelTime * exterminationFactor), timeout);
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
	
	public void testCalculateHabitability() throws Exception
	{
		int heatStepSize = 10;
		int heatSteps = UniverseConquestConstants.PARAM_SOLARSYSTEM_HEAT_MAX.asInt()/heatStepSize;
		int sizeStepSize = 10;
		int sizeSteps = UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX.asInt()/sizeStepSize;
		
		int[][] hab = new int[sizeSteps+1][heatSteps+1];
		
		for(int heat = 0; heat <= heatSteps; heat++)
		{
			for(int size = 0; size <= sizeSteps; size++)
			{
				hab[size][heat] = calculator.calculateHabitability(size*sizeStepSize, heat*heatStepSize);
			}
		}
		
		// PRINT ARRAY FOR DEBUGGING
		
		logger.debug("---");
		for(int heat = 0; heat <= heatSteps; heat++)
		{
			System.out.print(heat + "\t|\t");
			for(int size = 0; size <= sizeSteps; size++)
			{
				if(hab[size][heat] >= 1000)
					System.out.print("1k\t");
				else
					System.out.print(hab[size][heat] + "\t");
			}
			System.out.println("");
		}
		for(int size = 0; size <= sizeSteps+2; size++)
			System.out.print("--------");
		System.out.println("");
		System.out.print("\t\t");
		for(int size = 0; size <= sizeSteps; size++)
		{
			System.out.print(size + "\t");
		}
		System.out.println("");
		
		// VERIFY
		
		// heat = 0 -> hab = 0
		for(int size = 0; size <= sizeSteps; size++)
			assertEquals(0, hab[size][0]);
		// size = 0 -> hab = 0
		for(int h = 0; h <= heatSteps; h++)
			assertEquals(0, hab[h][0]);
		
		// hab has max somewhere in the middle, continously falling to each side
		for(int size = 1; size <= sizeSteps; size++)
		{
			// find max
			int hMax = 0;
			for(int h = 0; h <= heatSteps; h++)
			{
				if(hab[size][h] > hab[size][hMax])
					hMax = h;
			}
			// assert max is 1k
			assertEquals(1000, hab[size][hMax]);
			
			// check continously falling
			for(int h = hMax+1; h <= heatSteps; h++)
				assertTrue(hab[size][h] <= hab[size][h-1]);
			for(int h = hMax-1; h >= 0; h--)
				assertTrue(hab[size][h] <= hab[size][h+1]);
			
			// check distribution
			// h > hMax --> hab[size] >= hab[size-1]
			for(int h = hMax+1; h <= heatSteps; h++)
				assertTrue(hab[size][h] >= hab[size-1][h]);
			// h < hMax --> hab[size] <= hab[size-1]
			for(int h = hMax-1; h >= 0; h--)
				assertTrue(hab[size][h] <= hab[size-1][h]);
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
		double fac_attack = UniverseConquestConstants.PARAM_FACTOR_ATTACK.asDouble();
		long fac_pop = UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR.asLong();

		assertEquals(0.0, calculator.calculateAttackStrength(10, 0));

		long pop = fac_pop;

		assertEquals(fac_attack / 2 * pop / fac_pop * 10, calculator.calculateAttackStrength(10, pop), 0.01);
		assertEquals(fac_attack / 2 * pop / fac_pop * 100, calculator.calculateAttackStrength(100, pop), 0.01);
		assertEquals(fac_attack / 2 * pop / fac_pop * 1000, calculator.calculateAttackStrength(1000, pop), 0.01);

		pop = 2000;

		assertEquals(fac_attack / 2 * pop / fac_pop * 10, calculator.calculateAttackStrength(10, pop), 0.01);
		assertEquals(fac_attack / 2 * pop / fac_pop * 100, calculator.calculateAttackStrength(100, pop), 0.01);
		assertEquals(fac_attack / 2 * pop / fac_pop * 1000, calculator.calculateAttackStrength(1000, pop), 0.01);
	}

	public void testCalculateAttackStrength2()
	{
		long maxPop = popMax;
		double speedFactor = 100;

		double build = calculator.calculateBuildStrength(speedFactor, maxPop / 2, maxPop);
		double attack = calculator.calculateAttackStrength(speedFactor, maxPop / 2);

		double ratio = UniverseConquestConstants.PARAM_FACTOR_ATTACK.asDouble() / UniverseConquestConstants.PARAM_FACTOR_BUILD.asDouble();

		logger.debug("build = " + build);
		logger.debug("attack = " + attack);
		logger.debug("expected ratio = " + ratio);
		assertEquals(build * ratio, attack, 0.01);
	}

	public void testCalculateBuildStrength()
	{
		double fac = UniverseConquestConstants.PARAM_FACTOR_BUILD.asDouble();
		long popFac = UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR.asLong();

		int speedFac = 1000;
		long maxPop = popMax;
		double expectedGrowthAt50 = maxPop / 4.0 * fac * speedFac / popFac;
		assertEquals(0.0, calculator.calculateBuildStrength(speedFac, 0, maxPop));
		assertEquals(0.0, calculator.calculateBuildStrength(speedFac, maxPop, maxPop));
		assertEquals(expectedGrowthAt50, calculator.calculateBuildStrength(speedFac, maxPop / 2, maxPop), 0.01);

		int deviation = 1000000;
		logger.debug("maxPop/2+1000000 -> " + calculator.calculateBuildStrength(1000, maxPop / 2 + deviation, maxPop));
		logger.debug("maxPop/2     -> " + calculator.calculateBuildStrength(1000, maxPop / 2, maxPop));
		logger.debug("maxPop/2-1000000 -> " + calculator.calculateBuildStrength(1000, maxPop / 2 - deviation, maxPop));

		assertEquals(calculator.calculateBuildStrength(1000, maxPop / 2 + deviation, maxPop),
				calculator.calculateBuildStrength(1000, maxPop / 2 - deviation, maxPop));
		assertTrue(calculator.calculateBuildStrength(1000, maxPop / 2 - deviation, maxPop) < calculator.calculateBuildStrength(1000, maxPop / 2,
				maxPop));

		long pop = 100000000;
		assertEquals(calculator.calculateBuildStrength(100, pop, maxPop), calculator.calculateBuildStrength(100, pop, maxPop / 2));
		assertEquals(calculator.calculateBuildStrength(100, maxPop - pop, maxPop),
				calculator.calculateBuildStrength(100, maxPop / 2 - pop, maxPop / 2));

		assertEquals(expectedGrowthAt50 / 10, calculator.calculateBuildStrength(100, maxPop / 2, maxPop), 0.01);
		assertEquals(expectedGrowthAt50 * 10, calculator.calculateBuildStrength(10000, maxPop / 2, maxPop), 0.01);
	}

	public void testCalculateBuildStrength2()
	{
		// test is very expensive so there is an extra option to enable it during development
		if(testCalculateBuildStrength2)
		{
			long maxMaxPopulation = calculator.getMaxPopulation();
			for(int i = 0; i <= 5; i++)
			{
				simulateBuildStrength(i, maxMaxPopulation);
				simulateBuildStrength(i, maxMaxPopulation / 2);
				simulateBuildStrength(i, maxMaxPopulation / 4);
			}
		}
		else
		{
			logger.warn("test skipped!");
		}
	}

	private void simulateBuildStrength(int speed, long maxPop)
	{
		logger.debug("simulating build strength - speed " + speed + " - maxPop = " + maxPop);
		long pop = 10000000;
		double delta;
		double deltaMax = Double.MIN_VALUE;
		double deltaMin = Double.MAX_VALUE;
		long deltaMaxTick = -1;
		long deltaMinTick = -1;
		long deltaMaxPop = -1;
		long deltaMinPop = -1;
		double speedFactor = calculator.getSpeedFactor(speed);
		long start = System.currentTimeMillis();
		long start2 = System.nanoTime();

		long ticksPerSecond = 10L;
		long minute = ticksPerSecond * 60;
		long hour = minute * 60;
		long day = hour * 24;
		long year = day * 365;

		long tick = 0;
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

			delta = (long) calculator.calculateBuildStrength(speedFactor, pop, maxPop);
			if(delta > deltaMax)
			{
				deltaMax = delta;
				deltaMaxTick = tick;
				deltaMaxPop = pop;
			}
			if(delta < deltaMin)
			{
				deltaMin = delta;
				deltaMinTick = tick;
				deltaMinPop = pop;
			}

			pop += delta;

			tick++;

			//@formatter:off
			if(!reached01 && pop >= maxPop * 0.01)
			{
				reached01 = true;
				logger.info("01% reached @ tick " + tick + " (" + StringUtil.toString(tick*1000/ticksPerSecond) + ")");
			}
			else if(!reached10 && pop >= maxPop * 0.10)
			{
				reached10 = true;
				logger.info("10% reached @ tick " + tick + " (" + StringUtil.toString(tick*1000/ticksPerSecond) + ")");
			}
			else if(!reached25 && pop >= maxPop * 0.25)
			{
				reached25 = true;
				logger.info("25% reached @ tick " + tick + " (" + StringUtil.toString(tick*1000/ticksPerSecond) + ")");
			}
			else if(!reached50 && pop >= maxPop * 0.50)
			{
				reached50 = true;
				logger.info("50% reached @ tick " + tick + " (" + StringUtil.toString(tick*1000/ticksPerSecond) + ")");
			}
			else if(!reached75 && pop >= maxPop * 0.75)
			{
				reached75 = true;
				logger.info("75% reached @ tick " + tick + " (" + StringUtil.toString(tick*1000/ticksPerSecond) + ")");
			}
			else if(!reached90 && pop >= maxPop * 0.90)
			{
				reached90 = true;
				logger.info("90% reached @ tick " + tick + " (" + StringUtil.toString(tick*1000/ticksPerSecond) + ")");
			}
			else if(!reached99 && pop >= maxPop * 0.99)
			{
				reached99 = true;
				logger.info("99% reached @ tick " + tick + " (" + StringUtil.toString(tick*1000/ticksPerSecond) + ")");
			}
			//@formatter:on
			if(tick > year)
				break;
		}
		long end2 = System.nanoTime();
		long end = System.currentTimeMillis();
		logger.debug("time needed: " + (end - start) + "ms");
		logger.debug("time needed: " + (end2 - start2) + "ns");
		logger.debug("speed: " + ((double) tick * 1000 / (end - start)) + " ticks/second");
		logger.debug("speed: " + ((double) tick * 1000000000 / (end2 - start2)) + " ticks/second");

		logger.debug("deltaMax = " + deltaMax + " (" + ((long) deltaMax) + ") @ tick " + deltaMaxTick + " with pop = " + deltaMaxPop);
		logger.debug("deltaMin = " + deltaMin + " (" + ((long) deltaMin) + ") @ tick " + deltaMinTick + " with pop = " + deltaMinPop);
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

	public void testCalculateDeltas() throws Exception
	{
		final ExtendedRandom random = new ExtendedRandom(1234);
		final int habitability = 500;
		final int size = 500;
		final long tick = 100;
		final int speed = 0;
		final long popValue = 1000000000L;

		final Galaxy galaxy = new Galaxy();

		final SolarSystem solarSystem = new SolarSystem();
		solarSystem.setActivated(true);
		solarSystem.setCoords(new Vector.Integer(123, 456, 789));
		solarSystem.setGalaxy(galaxy);

		final Match match = new Match();
		match.setGalaxy(galaxy);
		match.setSpeed(speed);

		final SolarSystemInfrastructure infrastructure = new SolarSystemInfrastructure();
		infrastructure.setActivated(true);
		infrastructure.setHabitability(habitability);
		infrastructure.setInfrastructure(0);
		infrastructure.setMatch(match);
		infrastructure.setSize(size);
		infrastructure.setPopulations(new ArrayList<SolarSystemPopulation>());

		final Participant participant1 = new Participant();
		participant1.setId(1L);
		participant1.setMatch(match);
		final Participant participant2 = new Participant();
		participant2.setId(2L);
		participant2.setMatch(match);
		final Participant participant3 = new Participant();
		participant3.setId(3L);
		participant3.setMatch(match);

		SolarSystemPopulation pop1 = getPopulation(participant1, 0);
		SolarSystemPopulation pop2 = getPopulation(participant2, 1);
		SolarSystemPopulation pop3 = getPopulation(participant3, 2);

		long avg1, avg2, avg3, last_avg1, last_avg2, last_avg3;
		int cycles = 1000;

		// just 1 pop
		infrastructure.getPopulations().clear();
		infrastructure.getPopulations().add(pop1);
		infrastructure.setHomePopulation(pop1);
		for(int fac = 1; fac <= 1000; fac += (fac < 100 ? 10 : 100))
		{
			avg1 = 0;
			for(int i = 0; i < cycles; i++)
			{
				pop1.setLastUpdateDate(new Date(0));
				pop1.setPopulation(popValue * fac);
				calculator.calculateDeltas(infrastructure, random, new Date(tick));
				avg1 += (pop1.getPopulation() - popValue * fac);
			}
			avg1 /= cycles;
			logger.debug("pop1 all alone - fac=" + StringUtil.fillup("" + fac, 4, ' ', true) + ":        pop1 avg delta = " + avg1);
			if(fac < infrastructure.getHabitability() * infrastructure.getSize() / 1000)
				assertTrue(avg1 > 0);
			else
				assertTrue(avg1 < 0);
		}

		// pop1 + pop2
		infrastructure.getPopulations().clear();
		infrastructure.getPopulations().add(pop1);
		infrastructure.getPopulations().add(pop2);
		infrastructure.setHomePopulation(pop1);
		int facAtWhich2IsStronger = 3;
		for(int fac = 1; fac <= 10; fac++)
		{
			// pop1 >= pop2
			avg1 = 0;
			avg2 = 0;
			for(int i = 0; i < cycles; i++)
			{
				pop1.setLastUpdateDate(new Date(0));
				pop2.setLastUpdateDate(new Date(1));
				pop1.setPopulation(popValue * fac);
				pop2.setPopulation(popValue);
				calculator.calculateDeltas(infrastructure, random, new Date(tick));
				avg1 += (pop1.getPopulation() - popValue * fac);
				avg2 += (pop2.getPopulation() - popValue);
			}
			avg1 /= cycles;
			avg2 /= cycles;
			logger.debug("pop1&pop2 ; pop1>pop2 ; fac=" + fac + ":    pop1 avg delta = " + avg1 + "     pop2 avg delta = " + avg2);
			assertTrue(avg1 > 0);
			assertTrue(avg2 < 0);

			last_avg1 = avg1 + 1; // 1 tolerance
			last_avg2 = avg2;

			// pop1 <= pop2
			avg1 = 0;
			avg2 = 0;
			for(int i = 0; i < cycles; i++)
			{
				pop1.setLastUpdateDate(new Date(0));
				pop2.setLastUpdateDate(new Date(1));
				pop1.setPopulation(popValue);
				pop2.setPopulation(popValue * fac);
				calculator.calculateDeltas(infrastructure, random, new Date(tick));
				avg1 += (pop1.getPopulation() - popValue);
				avg2 += (pop2.getPopulation() - popValue * fac);
			}
			avg1 /= cycles;
			avg2 /= cycles;
			logger.debug("pop1&pop2 ; pop1<pop2 ; fac=" + fac + ":    pop1 avg delta = " + avg1 + "     pop2 avg delta = " + avg2);
			assertTrue(avg1 <= last_avg1);
			assertTrue(avg2 >= last_avg2);
			if(fac >= facAtWhich2IsStronger)
				assertTrue(avg1 < 0);
			assertTrue(avg2 < 0);
		}
		// pop1 + pop2 + pop3
		infrastructure.getPopulations().clear();
		infrastructure.getPopulations().add(pop1);
		infrastructure.getPopulations().add(pop2);
		infrastructure.getPopulations().add(pop3);
		infrastructure.setHomePopulation(pop1);
		for(int fac = 1; fac <= 10; fac++)
		{
			// pop1 >= pop2, pop3
			avg1 = 0;
			avg2 = 0;
			avg3 = 0;
			for(int i = 0; i < cycles; i++)
			{
				pop1.setLastUpdateDate(new Date(0));
				pop2.setLastUpdateDate(new Date(1));
				pop3.setLastUpdateDate(new Date(2));
				pop1.setPopulation(popValue * fac);
				pop2.setPopulation(popValue);
				pop3.setPopulation(popValue);
				calculator.calculateDeltas(infrastructure, random, new Date(tick));
				avg1 += (pop1.getPopulation() - popValue * fac);
				avg2 += (pop2.getPopulation() - popValue);
				avg3 += (pop3.getPopulation() - popValue);
			}
			avg1 /= cycles;
			avg2 /= cycles;
			avg3 /= cycles;
			logger.debug("pop1&pop2&pop3 ; pop1>pop2,pop3 ; fac=" + fac + ":    pop1 avg delta = " + avg1 + "     pop2 avg delta = " + avg2
					+ "     pop3 avg delta = " + avg3);
			assertTrue(avg1 > 0);
			assertTrue(avg2 < 0);
			assertTrue(avg3 < 0);

			last_avg1 = avg1;
			last_avg2 = avg2;
			last_avg3 = avg3;

			// pop1 >= pop2 >= pop3
			avg1 = 0;
			avg2 = 0;
			avg3 = 0;
			for(int i = 0; i < cycles; i++)
			{
				pop1.setLastUpdateDate(new Date(0));
				pop2.setLastUpdateDate(new Date(1));
				pop3.setLastUpdateDate(new Date(2));
				pop1.setPopulation(popValue * fac);
				pop2.setPopulation(popValue);
				pop3.setPopulation(popValue / fac);
				calculator.calculateDeltas(infrastructure, random, new Date(tick));
				avg1 += (pop1.getPopulation() - popValue * fac);
				avg2 += (pop2.getPopulation() - popValue);
				avg3 += (pop3.getPopulation() - popValue / fac);
			}
			avg1 /= cycles;
			avg2 /= cycles;
			avg3 /= cycles;
			logger.debug("pop1&pop2&pop3 ; pop1>pop2>pop3 ; fac=" + fac + ":    pop1 avg delta = " + avg1 + "     pop2 avg delta = " + avg2
					+ "     pop3 avg delta = " + avg3);
			assertTrue(avg1 > 0);
			assertTrue(avg2 < 0);
			assertTrue(avg3 < 0);
			if(fac >= 2)
			{
				assertTrue(avg1 > last_avg1);
				assertTrue(avg2 < last_avg2);
				assertTrue(avg3 > last_avg3);
			}
			assertTrue(avg3 >= avg2);

			last_avg1 = avg1;
			last_avg2 = avg2;
			last_avg3 = avg3;
			
			// pop2 >= pop1 >= pop3 
			avg1 = 0;
			avg2 = 0;
			avg3 = 0;
			for(int i = 0; i < cycles; i++)
			{
				pop1.setLastUpdateDate(new Date(0));
				pop2.setLastUpdateDate(new Date(1));
				pop3.setLastUpdateDate(new Date(2));
				pop1.setPopulation(popValue * fac);
				pop2.setPopulation(popValue);
				pop3.setPopulation(popValue / fac);
				calculator.calculateDeltas(infrastructure, random, new Date(tick));
				avg1 += (pop1.getPopulation() - popValue * fac);
				avg2 += (pop2.getPopulation() - popValue);
				avg3 += (pop3.getPopulation() - popValue / fac);
			}
			avg1 /= cycles;
			avg2 /= cycles;
			avg3 /= cycles;
			logger.debug("pop1&pop2&pop3 ; pop2,pop3>pop1 ; fac=" + fac + ":    pop1 avg delta = " + avg1 + "     pop2 avg delta = " + avg2
					+ "     pop3 avg delta = " + avg3);
			assertTrue(avg1 > 0);
			assertTrue(avg2 < 0);
			assertTrue(avg3 < 0);
			
			last_avg1 = avg1;
			last_avg2 = avg2;
			last_avg3 = avg3;

			// pop2, pop3 >= pop1
			avg1 = 0;
			avg2 = 0;
			avg3 = 0;
			for(int i = 0; i < cycles; i++)
			{
				pop1.setLastUpdateDate(new Date(0));
				pop2.setLastUpdateDate(new Date(1));
				pop3.setLastUpdateDate(new Date(2));
				pop1.setPopulation(popValue);
				pop2.setPopulation(popValue * fac);
				pop3.setPopulation(popValue * fac);
				calculator.calculateDeltas(infrastructure, random, new Date(tick));
				avg1 += (pop1.getPopulation() - popValue);
				avg2 += (pop2.getPopulation() - popValue * fac);
				avg3 += (pop3.getPopulation() - popValue * fac);
			}
			avg1 /= cycles;
			avg2 /= cycles;
			avg3 /= cycles;
			logger.debug("pop1&pop2&pop3 ; pop2,pop3>pop1 ; fac=" + fac + ":    pop1 avg delta = " + avg1 + "     pop2 avg delta = " + avg2
					+ "     pop3 avg delta = " + avg3);
			assertTrue(avg1 > 0);
			assertTrue(avg2 < 0);
			assertTrue(avg3 < 0);
			
			last_avg1 = avg1;
			last_avg2 = avg2;
			last_avg3 = avg3;
			
			// pop2 >= pop3 >= pop1
			avg1 = 0;
			avg2 = 0;
			avg3 = 0;
			for(int i = 0; i < cycles; i++)
			{
				pop1.setLastUpdateDate(new Date(0));
				pop2.setLastUpdateDate(new Date(1));
				pop3.setLastUpdateDate(new Date(2));
				pop1.setPopulation(popValue / fac);
				pop2.setPopulation(popValue * fac);
				pop3.setPopulation(popValue);
				calculator.calculateDeltas(infrastructure, random, new Date(tick));
				avg1 += (pop1.getPopulation() - popValue / fac);
				avg2 += (pop2.getPopulation() - popValue * fac);
				avg3 += (pop3.getPopulation() - popValue);
			}
			avg1 /= cycles;
			avg2 /= cycles;
			avg3 /= cycles;
			logger.debug("pop1&pop2&pop3 ; pop2>pop3>pop1 ; fac=" + fac + ":    pop1 avg delta = " + avg1 + "     pop2 avg delta = " + avg2
					+ "     pop3 avg delta = " + avg3);
			if(fac <= 3)
				assertTrue(avg1 >= 0);
			else
				assertTrue(avg1 < 0);
			assertTrue(avg2 < 0);
			assertTrue(avg3 < 0);
			if(fac >= 2)
			{
				assertTrue(avg1 < last_avg1);
				assertTrue(avg2 > last_avg2);
				assertTrue(avg3 > last_avg3);
				assertTrue(avg3 < avg2);
			}
			
			last_avg1 = avg1;
			last_avg2 = avg2;
			last_avg3 = avg3;

		}
	}

	private SolarSystemPopulation getPopulation(Participant participant, long date)
	{
		SolarSystemPopulation pop = new SolarSystemPopulation();
		pop.setId(participant.getId());
		pop.setActivated(true);
		pop.setColonizationDate(new Date(date));
		pop.setLastUpdateDate(new Date(date));
		pop.setParticipant(participant);
		pop.setBuildPriority(EnumPopulationPriority.balanced);
		pop.setAttackPriority(EnumPopulationPriority.balanced);
		return pop;
	}
}
