package com.syncnapsis.universe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import com.syncnapsis.utils.data.ExtendedRandom;

public class UniverseWorkerTest extends BaseSpringContextTestCase
{
	private long								referenceTime	= 1234L;
	private TimeProvider						timeProvider	= new MockTimeProvider(referenceTime);

	private MatchManager						matchManager;
	private SolarSystemPopulationManager		solarSystemPopulationManager;
	private SolarSystemInfrastructureManager	solarSystemInfrastructureManager;
	private SessionFactory						sessionFactory;

	@TestCoversMethods({ "afterPropertiesSet", "destroy", "*etConstantLoader", "*etSessionFactoryUtil", "*etMatchManager",
			"*etSolarSystemPopulationManager", "*etSolarSystemInfrastructureManager", "*etRandom" })
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
		worker.destroy();
		assertFalse(worker.isRunning());
	}

	@TestCoversMethods({ "*etRankUpdateInterval", "*etSystemUpdateInterval", "*etMovementUpdateInterval" })
	public void testIntervals() throws Throwable
	{
		final MatchManager mockMatchManager = mockContext.mock(MatchManager.class);
		final SolarSystemPopulationManager mockSolarSystemPopulationManager = mockContext.mock(SolarSystemPopulationManager.class);
		final SolarSystemInfrastructureManager mockSolarSystemInfrastructureManager = mockContext.mock(SolarSystemInfrastructureManager.class);

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
	
	public void testWork() throws Throwable
	{

		final MatchManager mockMatchManager = mockContext.mock(MatchManager.class);
		final SolarSystemPopulationManager mockSolarSystemPopulationManager = mockContext.mock(SolarSystemPopulationManager.class);
		final SolarSystemInfrastructureManager mockSolarSystemInfrastructureManager = mockContext.mock(SolarSystemInfrastructureManager.class);
		final ExtendedRandom random = new ExtendedRandom(1234);

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
		worker.setRandom(random);
		worker.setRankUpdateInterval(rankUpdateInterval);
		worker.setSystemUpdateInterval(systemUpdateInterval);
		worker.setMovementUpdateInterval(movementUpdateInterval);

		// mock return values
		// @formatter:off
		final SolarSystemInfrastructure inf11 = new SolarSystemInfrastructure(); inf11.setId(11L);
		final SolarSystemInfrastructure inf12 = new SolarSystemInfrastructure(); inf11.setId(12L);
		final SolarSystemInfrastructure inf21 = new SolarSystemInfrastructure(); inf11.setId(21L);
		final SolarSystemInfrastructure inf22 = new SolarSystemInfrastructure(); inf11.setId(22L);
		final Match match1 = new Match(); match1.setInfrastructures(Arrays.asList(inf11, inf12));
		final Match match2 = new Match(); match2.setInfrastructures(Arrays.asList(inf21, inf22));
		final List<Match> matches = Arrays.asList(match1, match2);
		// @formatter:on

		// expectations
		mockContext.checking(new Expectations() {
			{
				oneOf(mockMatchManager).getByState(EnumMatchState.active);
				will(returnValue(matches));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockSolarSystemPopulationManager).simulate(inf11, random);
				will(returnValue(null));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockSolarSystemPopulationManager).simulate(inf12, random);
				will(returnValue(null));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockSolarSystemPopulationManager).simulate(inf21, random);
				will(returnValue(null));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockSolarSystemPopulationManager).simulate(inf22, random);
				will(returnValue(null));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockMatchManager).updateRanking(match1);
				will(returnValue(match1));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockMatchManager).updateChannels(match1, true, true, true);
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockMatchManager).updateRanking(match2);
				will(returnValue(match2));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockMatchManager).updateChannels(match2, true, true, true);
			}
		});
		
		
		worker.work(0L);
		mockContext.assertIsSatisfied();
	}

	public void testRun() throws Exception
	{
		final AtomicBoolean openCalled = new AtomicBoolean(false);
		final AtomicBoolean closeCalled = new AtomicBoolean(false);

		SessionFactoryUtil mockSessionFactoryUtil = new SessionFactoryUtil(sessionFactory) {
			public Session openBoundSession()
			{
				openCalled.set(true);
				return null;
			}

			public boolean closeBoundSession()
			{
				closeCalled.set(true);
				return true;
			}
		};

		UniverseWorker worker = new UniverseWorker(100, timeProvider);
		worker.setSessionFactoryUtil(mockSessionFactoryUtil);

		// start stop in suspended mode to prevent real execution
		// this way we can test only the session logic in run()
		worker.start(true);
		worker.stop();

		assertTrue(openCalled.get());
		assertTrue(closeCalled.get());
	}
	// TODO
}
