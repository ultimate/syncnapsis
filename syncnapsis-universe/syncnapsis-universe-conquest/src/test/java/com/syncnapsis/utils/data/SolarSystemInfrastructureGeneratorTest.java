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
package com.syncnapsis.utils.data;

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.universe.Calculator;

/**
 * @author ultimate
 */
public class SolarSystemInfrastructureGeneratorTest extends BaseSpringContextTestCase
{
	private Calculator			calculator;

	public void testGenerate() throws Exception
	{
		SolarSystemInfrastructureGenerator generator = new SolarSystemInfrastructureGenerator(calculator);

		int heatMax = UniverseConquestConstants.PARAM_SOLARSYSTEM_HEAT_MAX.asInt();
		int sizeMax = UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX.asInt();
		int habitabilityMax = UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX.asInt();
		long absoluteMaxPopulation = calculator.getMaxPopulation();
		long maxPopulation;
		int expectedHabitability;

		ExtendedRandom random11 = new ExtendedRandom(1234);
		ExtendedRandom random12 = new ExtendedRandom(1234);
		ExtendedRandom random2 = new ExtendedRandom(5678);

		SolarSystemInfrastructure infrastructure;

		Match match = new Match();
		match.setId(-1L);
		SolarSystem solarSystem = new SolarSystem();
		solarSystem.setId(-1L);

		int[] heats = new int[heatMax + 1];
		int[] sizes = new int[sizeMax + 1];
		int[] habitabilities = new int[habitabilityMax + 1];
		int[] maxPopulations = new int[1001];
		int[] infrastructures = new int[101];
		
		int zeroCapacitySystems = 0;

		for(int i = 0; i < 1000; i++)
		{
			infrastructure = generator.generate(random11, match, solarSystem);
			assertNotNull(infrastructure);
			assertSame(match, infrastructure.getMatch());
			assertSame(solarSystem, infrastructure.getSolarSystem());
			
			expectedHabitability = calculator.calculateHabitability(infrastructure.getSize(), infrastructure.getHeat());
			maxPopulation = calculator.getMaxPopulation(infrastructure);			
			logger.info("maxPop = " + maxPopulation + " / " + absoluteMaxPopulation + " ( " + infrastructure.getSize() + " , " + infrastructure.getHeat() + " , " + infrastructure.getHabitability() + " )");

			assertTrue(infrastructure.getHeat() >= 0);
			assertTrue(infrastructure.getHeat() <= heatMax);
			assertTrue(infrastructure.getSize() >= 0);
			assertTrue(infrastructure.getSize() <= sizeMax);
			
			assertEquals(expectedHabitability, infrastructure.getHabitability());
			
			assertTrue(infrastructure.getInfrastructure() >= 0);
			assertTrue(infrastructure.getInfrastructure() <= maxPopulation);
			assertTrue(infrastructure.getInfrastructure() <= absoluteMaxPopulation );

			heats[infrastructure.getHeat()]++;
			sizes[infrastructure.getSize()]++;
			habitabilities[infrastructure.getHabitability()]++;
			maxPopulations[(int) (100 * maxPopulation / absoluteMaxPopulation )]++;
			infrastructures[(int) (100 * infrastructure.getInfrastructure() / absoluteMaxPopulation )]++;
			
			if(maxPopulation == 0)
				zeroCapacitySystems++;

			assertTrue(infrastructure.equals(generator.generate(random12, match, solarSystem)));
			assertFalse(infrastructure.equals(generator.generate(random2, match, solarSystem)));
		}

		logger.debug("heat-distribution:");
		printDistribution(heats, 5, 10);
		logger.debug("size-distribution:");
		printDistribution(sizes, 5, 10);
		logger.debug("habitability-distribution:");
		printDistribution(habitabilities, 5, 10);
		logger.debug("maxPopulation-distribution:");
		printDistribution(maxPopulations, 10, 1);
		logger.debug("infrastructure-distribution:");
		printDistribution(infrastructures, 10, 1);
		logger.debug("zero-capacity systems: " + zeroCapacitySystems);
	}

	public void testGenerate_invalid()
	{
		SolarSystemInfrastructureGenerator generator = new SolarSystemInfrastructureGenerator(calculator);

		ExtendedRandom random = new ExtendedRandom();

		// no match, no solarsystem
		try
		{
			generator.generate(random);
			fail("expected exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		// no solarsystem
		try
		{
			generator.generate(random, new Match());
			fail("expected exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
	}
}
