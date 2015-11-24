package com.syncnapsis.universe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;

import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.service.MatchManager;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumMatchState;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.utils.SessionFactoryUtil;

public class UniverseWorkerTest extends BaseSpringContextTestCase
{
	private long								referenceTime	= 1234L;
	private TimeProvider						timeProvider	= new MockTimeProvider(referenceTime);

	private MatchManager						matchManager;
	private SolarSystemPopulationManager		solarSystemPopulationManager;
	private SolarSystemInfrastructureManager	solarSystemInfrastructureManager;
	private SessionFactoryUtil					sessionFactoryUtil;

	@TestCoversMethods({ "afterPropertiesSet", "*etConstandLoader", "*etSessionFactoryUtil", "*etMatchManager", "*etSolarSystemPopulationManager",
			"*etSolarSystemInfrastructureManager", "*etRandom" })
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

	@TestCoversMethods({ "*etRankUpdateInterval", "*etSystemUpdateInterval", "*etMovementUpdateInterval" })
	public void testIntervals() throws Throwable
	{
		final MatchManager mockMatchManager = mockContext.mock(MatchManager.class);
		final SolarSystemPopulationManager mockSolarSystemPopulationManager = mockContext.mock(SolarSystemPopulationManager.class);
		final SolarSystemInfrastructureManager mockSolarSystemInfrastructureManager = mockContext.mock(SolarSystemInfrastructureManager.class);
		// SessionFactoryUtil sessionFactoryUtil = mockContext.mock(SessionFactoryUtil.class);

		// use primes for the intervals for easier testing
		int rankUpdateInterval = 2;
		int systemUpdateInterval = 3;
		int movementUpdateInterval = 5;

		// init worker, but don't start!
		UniverseWorker worker = new UniverseWorker(100, timeProvider);
		worker.setMatchManager(mockMatchManager);
		worker.setSolarSystemPopulationManager(mockSolarSystemPopulationManager);
		worker.setSolarSystemInfrastructureManager(mockSolarSystemInfrastructureManager);
		worker.setSessionFactoryUtil(null);
		worker.setRankUpdateInterval(rankUpdateInterval);
		worker.setSystemUpdateInterval(systemUpdateInterval);
		worker.setMovementUpdateInterval(movementUpdateInterval);

		// mock return values
		final Match match = new Match();
		match.setInfrastructures(new ArrayList<SolarSystemInfrastructure>());
		final List<Match> matches = Arrays.asList(match); 

		// general expectations
		mockContext.checking(new Expectations() {
			{
				allowing(mockMatchManager).getByState(EnumMatchState.active);
				will(returnValue(matches));
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(mockMatchManager).updateRanking(match);
				will(returnValue(match));
			}
		});

		// check if channels are only updated for specific execution IDs
		// for wrong execution ID
		mockContext.checking(new Expectations() {
			{
				allowing(mockMatchManager).updateChannels(match, false, false, false);
			}
		});
		worker.work(1L);
		mockContext.assertIsSatisfied();
		// for cycle of rank update
		mockContext.checking(new Expectations() {
			{
				allowing(mockMatchManager).updateChannels(match, true, false, false);
			}
		});
		worker.work(rankUpdateInterval);
		mockContext.assertIsSatisfied();
		// for cycle of system update
		mockContext.checking(new Expectations() {
			{
				allowing(mockMatchManager).updateChannels(match, false, true, false);
			}
		});
		worker.work(systemUpdateInterval);
		mockContext.assertIsSatisfied();
		// for cycle of movement update
		mockContext.checking(new Expectations() {
			{
				allowing(mockMatchManager).updateChannels(match, false, false, true);
			}
		});
		worker.work(movementUpdateInterval);
		mockContext.assertIsSatisfied();
		// for all updates (product of all cycles
		mockContext.checking(new Expectations() {
			{
				allowing(mockMatchManager).updateChannels(match, true, true, true);
			}
		});
		worker.work(rankUpdateInterval * systemUpdateInterval * movementUpdateInterval);
		mockContext.assertIsSatisfied();
	}

	// TODO
}
