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
package com.syncnapsis.utils.data;

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.tests.BaseSpringContextTestCase;

/**
 * @author ultimate
 */
public class SolarSystemInfrastructureGeneratorTest extends BaseSpringContextTestCase
{
	private ParameterManager	parameterManager;

	public void testGenerate() throws Exception
	{
		SolarSystemInfrastructureGenerator generator = new SolarSystemInfrastructureGenerator(parameterManager);

		int habitabilityMax = parameterManager.getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX);
		int sizeMax = parameterManager.getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX);
		long infrastructureMax = habitabilityMax * sizeMax
				* parameterManager.getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR);

		ExtendedRandom random11 = new ExtendedRandom(1234);
		ExtendedRandom random12 = new ExtendedRandom(1234);
		ExtendedRandom random2 = new ExtendedRandom(5678);

		SolarSystemInfrastructure infrastructure;

		Match match = new Match();
		match.setId(-1L);
		SolarSystem solarSystem = new SolarSystem();
		solarSystem.setId(-1L);

		int[] habitabilities = new int[habitabilityMax + 1];
		int[] sizes = new int[sizeMax + 1];
		int[] infrastructures = new int[101];

		for(int i = 0; i < 100; i++)
		{
			infrastructure = generator.generate(random11, match, solarSystem);
			assertNotNull(infrastructure);
			assertSame(match, infrastructure.getMatch());
			assertSame(solarSystem, infrastructure.getSolarSystem());
			assertTrue(infrastructure.getHabitability() >= 0);
			assertTrue(infrastructure.getHabitability() <= habitabilityMax);
			assertTrue(infrastructure.getSize() >= 0);
			assertTrue(infrastructure.getSize() <= sizeMax);
			infrastructureMax = (long) infrastructure.getHabitability() * infrastructure.getSize()
					* parameterManager.getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR);

//			logger.debug("hab=" + infrastructure.getHabitability() + " size=" + infrastructure.getSize() + " inf-max=" + infrastructureMax + " inf="
//					+ infrastructure.getInfrastructure());
			assertTrue(infrastructure.getInfrastructure() >= 0);
			assertTrue(infrastructure.getInfrastructure() <= infrastructureMax);

			habitabilities[infrastructure.getHabitability()]++;
			sizes[infrastructure.getSize()]++;
			infrastructures[(int) (100 * infrastructure.getInfrastructure() / infrastructureMax)]++;
			
			assertTrue(infrastructure.equals(generator.generate(random12, match, solarSystem)));
			assertFalse(infrastructure.equals(generator.generate(random2, match, solarSystem)));
		}

		logger.debug("habitability-distribution:");
		printDistribution(habitabilities, 5, 10);
		logger.debug("size-distribution:");
		printDistribution(sizes, 5, 10);
		logger.debug("infrastructure-distribution:");
		printDistribution(infrastructures, 5, 1);
	}
	
	public void testGenerate_invalid()
	{
		SolarSystemInfrastructureGenerator generator = new SolarSystemInfrastructureGenerator(parameterManager);
		
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
