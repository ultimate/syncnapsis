package com.syncnapsis.universe;

import com.syncnapsis.data.service.MatchManager;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class UniverseWorkerTest extends BaseSpringContextTestCase
{
	private long								referenceTime	= 1234L;
	private TimeProvider						timeProvider	= new MockTimeProvider(referenceTime);

	private MatchManager						matchManager;
	private SolarSystemPopulationManager		solarSystemPopulationManager;
	private SolarSystemInfrastructureManager	solarSystemInfrastructureManager;

	@TestCoversMethods({ "afterPropertiesSet", "*etConstandLoader", "*etSessionFactoryUtil", "*etMatchManager", "*etSolarSystemPopulationManager",
			"*etSolarSystemInfrastructureManager", "*etRandom"})
	public void testInit() throws Exception
	{
		
		UniverseWorker worker;
		
		// all missing
		worker = new UniverseWorker(100, timeProvider);
		try
		{
			worker.afterPropertiesSet();
			fail("expected exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		
		// all managers set
		worker = new UniverseWorker(100, timeProvider);
		worker.setMatchManager(matchManager);
		worker.setSolarSystemPopulationManager(solarSystemPopulationManager);
		worker.setSolarSystemInfrastructureManager(solarSystemInfrastructureManager);
		worker.afterPropertiesSet();
		
		assertTrue(worker.isRunning());
		worker.stop();
	}

	public void testIntervals() throws Exception
	{

	}

	// TODO
}
