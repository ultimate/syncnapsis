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
package com.syncnapsis.experimental;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.dao.SolarSystemInfrastructureDao;
import com.syncnapsis.data.dao.SolarSystemPopulationDao;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.impl.SolarSystemInfrastructureManagerImpl;
import com.syncnapsis.mock.util.ReturnArgAction;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.universe.Calculator;
import com.syncnapsis.utils.data.ExtendedRandom;
import com.syncnapsis.utils.math.Statistics;
import com.syncnapsis.utils.serialization.Serializer;

/**
 * @author ultimate
 * 
 */
@TestExcludesMethods({ "set*", "get*", "afterPropertiesSet" })
public class SimulationServletTest extends BaseSpringContextTestCase
{
	private SolarSystemInfrastructureManager		solarSystemInfrastructureManager;
	private SolarSystemPopulationDao				solarSystemPopulationDao;
	private Calculator								calculator;
	private Serializer<String>						serializer;

	private SolarSystemInfrastructureDao			mockSolarSystemInfrastructureDao;
	private SolarSystemPopulationDao				mockSolarSystemPopulationDao;
	private SolarSystemInfrastructureManagerImpl	mockSolarSystemInfrastructureManager;
	private MockServlet								mockServlet1;
	private SimulationServlet						mockServlet2;

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.tests.BaseSpringContextTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		mockServlet1 = new MockServlet();
		mockServlet1.setCalculator(calculator);
		mockServlet1.setSolarSystemInfrastructureManager(solarSystemInfrastructureManager);
		mockServlet1.setSolarSystemPopulationDao(solarSystemPopulationDao);
		mockServlet1.setSerializer(serializer);
		mockServlet1.afterPropertiesSet();

		mockSolarSystemInfrastructureDao = mockContext.mock(SolarSystemInfrastructureDao.class);
		mockSolarSystemPopulationDao = mockContext.mock(SolarSystemPopulationDao.class);
		mockSolarSystemInfrastructureManager = new SolarSystemInfrastructureManagerImpl(mockSolarSystemInfrastructureDao);

		mockServlet2 = new SimulationServlet();
		mockServlet2.setCalculator(calculator);
		mockServlet2.setSolarSystemInfrastructureManager(mockSolarSystemInfrastructureManager);
		mockServlet2.setSolarSystemPopulationDao(mockSolarSystemPopulationDao);
		mockServlet2.setSerializer(serializer);
		mockServlet2.afterPropertiesSet();
	}

	public void testService() throws Exception
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		Map<String, String> parameters = new HashMap<String, String>();

		int speed = 2;
		long duration = 1234;
		long tickLength = 123;
		double randomizeAttack = 0.12;
		double randomizeBuild = 0.34;
		logger.debug("mockServlet1:" + mockServlet1);
		logger.debug("PARAM_FACTOR_ATTACK_RANDOMIZE = " + UniverseConquestConstants.PARAM_FACTOR_ATTACK_RANDOMIZE.asDouble());
		logger.debug("PARAM_FACTOR_BUILD_RANDOMIZE = " + UniverseConquestConstants.PARAM_FACTOR_BUILD_RANDOMIZE);
		assertTrue(randomizeAttack != UniverseConquestConstants.PARAM_FACTOR_ATTACK_RANDOMIZE.asDouble());
		assertTrue(randomizeBuild != UniverseConquestConstants.PARAM_FACTOR_BUILD_RANDOMIZE.asDouble());

		parameters.put(SimulationServlet.P_SPEED, "" + speed);
		parameters.put(SimulationServlet.P_DURATION, "" + duration);
		parameters.put(SimulationServlet.P_TICKLENGTH, "" + tickLength);
		parameters.put(SimulationServlet.P_RANDOMIZE_ATTACK, "" + randomizeAttack);
		parameters.put(SimulationServlet.P_RANDOMIZE_BUILD, "" + randomizeBuild);

		SolarSystemInfrastructure scenario = createScenario(2, 2);

		Match dummyMatch = new Match();
		dummyMatch.setSpeed(speed);

		long seed = 456;
		ExtendedRandom random = new ExtendedRandom(seed);

		request.setParameter(SimulationServlet.PARAM_SEED, "" + seed);
		request.setParameter(SimulationServlet.PARAM_PARAMETERS, serializer.serialize(parameters, new Object[0]));
		request.setParameter(SimulationServlet.PARAM_SCENARIO, serializer.serialize(scenario, new Object[0]));

		mockServlet1.service(request, response);

		assertEquals(duration, mockServlet1.duration);
		assertEquals(tickLength, mockServlet1.tickLength);
		assertEquals(random, mockServlet1.random);

		assertEquals(dummyMatch, mockServlet1.scenario.getMatch());
		mockServlet1.scenario.setMatch(null); // clear match for next equals (prevent nullpointer)
		assertEquals(scenario, mockServlet1.scenario);

		assertTrue(randomizeAttack == UniverseConquestConstants.PARAM_FACTOR_ATTACK_RANDOMIZE.asDouble());
		assertTrue(randomizeBuild == UniverseConquestConstants.PARAM_FACTOR_BUILD_RANDOMIZE.asDouble());
	}

	public void testSimulate() throws Exception
	{
		final int speed = 0;
		final long duration = 40000; // 80000;
		final long tickLength = 100;
		ExtendedRandom random = new ExtendedRandom(1234);
		int participants = 3;
		int ticks = (int) (duration / tickLength + 1);

		SolarSystemInfrastructure scenario;
		Map<String, Long[]> result;
		String partKey;

		scenario = createScenario(participants + 2, participants);
		scenario.setMatch(new Match());
		scenario.getMatch().setSpeed(speed);

		mockContext.checking(new Expectations() {
			{
				allowing(mockSolarSystemInfrastructureDao).save(with(any(SolarSystemInfrastructure.class)));
				will(new ReturnArgAction());
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(mockSolarSystemPopulationDao).save(with(any(SolarSystemPopulation.class)));
				will(new ReturnArgAction());
			}
		});

		result = mockServlet2.simulate(duration, tickLength, scenario, random);
		assertNotNull(result);
		assertEquals(participants + 2, result.size());

		assertTrue(result.containsKey(SimulationServlet.RESULT_TIME));
		assertEquals(ticks, result.get(SimulationServlet.RESULT_TIME).length);
		assertTrue(result.containsKey(SimulationServlet.RESULT_INFRASTRUCTURE));
		assertEquals(ticks, result.get(SimulationServlet.RESULT_INFRASTRUCTURE).length);

		for(SolarSystemPopulation pop : scenario.getPopulations())
		{
			partKey = SimulationServlet.RESULT_PARTICIPANT_I + pop.getParticipant().getId();
			assertTrue(result.containsKey(partKey));
			assertEquals(ticks, result.get(partKey).length);
		}

		logger.debug("result: " + result);
		logger.debug("result serialized: " + mockServlet2.getSerializer().serialize(result, new Object[0]));
		for(String key : result.keySet())
		{
			logger.debug("  " + key + ": " + mockServlet2.getSerializer().serialize(result.get(key), new Object[0]));
		}
	}

	private SolarSystemInfrastructure createScenario(int pops, int parts)
	{
		List<SolarSystemPopulation> populations = new ArrayList<SolarSystemPopulation>(5);
		SolarSystemPopulation pop;
		for(int i = 0; i < pops; i++)
		{
			pop = new SolarSystemPopulation();
			// pop.setId(i * 100L);
			pop.setParticipant(new Participant());
			pop.getParticipant().setId((i % parts + 1) * 100L);
			pop.setColonizationDate(new Date(i * 10000));
			pop.setPopulation(Statistics.binomial(pops, i) * 1000000);
			populations.add(pop);
		}

		SolarSystemInfrastructure scenario = new SolarSystemInfrastructure();
		scenario.setSize(642);
		scenario.setHabitability(876);
		scenario.setInfrastructure(258000);
		scenario.setPopulations(populations);

		return scenario;
	}

	private class MockServlet extends SimulationServlet
	{
		private long						duration;
		private long						tickLength;
		private SolarSystemInfrastructure	scenario;
		private ExtendedRandom				random;

		@Override
		public Map<String, Long[]> simulate(long duration, long tickLength, SolarSystemInfrastructure scenario, ExtendedRandom random)
		{
			this.duration = duration;
			this.tickLength = tickLength;
			this.scenario = scenario;
			this.random = random;
			return new HashMap<String, Long[]>();
		}
	}
}
