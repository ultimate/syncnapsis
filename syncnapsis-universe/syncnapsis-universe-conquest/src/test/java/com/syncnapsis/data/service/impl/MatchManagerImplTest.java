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
package com.syncnapsis.data.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.client.ConquestManager;
import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.dao.MatchDao;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.data.model.help.Rank;
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.data.service.MatchManager;
import com.syncnapsis.data.service.ParticipantManager;
import com.syncnapsis.data.service.PlayerManager;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumJoinType;
import com.syncnapsis.enums.EnumMatchState;
import com.syncnapsis.enums.EnumStartCondition;
import com.syncnapsis.enums.EnumVictoryCondition;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.mock.util.ReturnArgAction;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.security.accesscontrol.MatchAccessController;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.universe.Calculator;
import com.syncnapsis.utils.data.ExtendedRandom;
import com.syncnapsis.utils.serialization.BaseMapper;

@TestCoversClasses({ MatchManager.class, MatchManagerImpl.class })
@TestExcludesMethods({ "isAccessible", "*etSecurityManager", "getMailer", "*etCalculator" })
public class MatchManagerImplTest extends GenericNameManagerImplTestCase<Match, Long, MatchManager, MatchDao>
{
	private GalaxyManager						galaxyManager;
	private ParticipantManager					participantManager;
	private SolarSystemPopulationManager		solarSystemPopulationManager;
	private SolarSystemInfrastructureManager	solarSystemInfrastructureManager;
	private PlayerManager						playerManager;
	private ConquestManager						conquestManager;

	private BaseMapper							mapper;
	private BaseGameManager						securityManager;
	private Calculator							calculator;

	private long								referenceTime	= 1234;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Match());
		setDaoClass(MatchDao.class);
		setMockDao(mockContext.mock(MatchDao.class));
		setMockManager(new MatchManagerImpl(mockDao, galaxyManager, participantManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager, conquestManager));

		BaseGameManager securityManager = new BaseGameManager(this.securityManager);
		securityManager.setTimeProvider(new MockTimeProvider(referenceTime));

		((MatchManagerImpl) mockManager).setSecurityManager(securityManager);
	}

	public void testAfterPropertiesSet() throws Exception
	{
		final List<Match> matches = Arrays.asList(new Match[] { new Match(), new Match(), new Match() });
		final List<Match> channelsCreated = new ArrayList<Match>();

		final ConquestManager mockConquestManager = mockContext.mock(ConquestManager.class);
		MatchManagerImpl mockManager = new MatchManagerImpl(mockDao, galaxyManager, participantManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager, mockConquestManager) {
			public void createChannels(Match match)
			{
				channelsCreated.add(match);
			}
		};
		mockManager.setSecurityManager(securityManager);
		mockManager.setCalculator(calculator);

		// mockDao must return a match list
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getAll();
				will(returnValue(matches));
			}
		});
		// expecting "match created" channel to be created
		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).createChannel(UniverseConquestConstants.CHANNEL_MATCH_CREATED, null);
				will(returnValue(true));
			}
		});

		mockManager.afterPropertiesSet();
		mockContext.assertIsSatisfied();

		assertEquals(channelsCreated, matches);
	}

	public void testGetByCreator() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByCreator", new ArrayList<Match>(), 1L, true, true, true, true);
		MethodCall daoCall = new MethodCall("getByCreator", new ArrayList<Match>(), 1L, true, true, true, true, new Date(referenceTime));
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetByPlayer() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByPlayer", new ArrayList<Match>(), 1L, true, true, true, true);
		MethodCall daoCall = new MethodCall("getByPlayer", new ArrayList<Match>(), 1L, true, true, true, true, new Date(referenceTime));
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetByGalaxy() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByGalaxy", new ArrayList<Match>(), 1L, true, true, true, true);
		MethodCall daoCall = new MethodCall("getByGalaxy", new ArrayList<Match>(), 1L, true, true, true, true, new Date(referenceTime));
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetByState() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByState", new ArrayList<Match>(), EnumMatchState.active);
		MethodCall daoCall = new MethodCall("getByState", new ArrayList<Match>(), EnumMatchState.active);
		simpleGenericTest(managerCall, daoCall);
	}

	public void testCreateMatch() throws Exception
	{
		final Long matchId = 123L;
		final Long galaxyId = 1L;
		final Player creator = playerManager.getByUsername("user1");
		final int participantsMax = 5;
		final int participantsMin = 3;
		final EnumJoinType plannedJoinType = EnumJoinType.invitationsOnly;
		final long seed = 123456789;
		final int speed = 2;
		final EnumStartCondition startCondition = EnumStartCondition.manually;
		final Date startDate = null;
		final EnumJoinType startedJoinType = EnumJoinType.none;
		final int startSystemCount = 2;
		final int startPopulation = 1000;
		final boolean startSystemSelectionEnabled = true;
		final String title = "test-battle";
		final EnumVictoryCondition victoryCondition = EnumVictoryCondition.domination;
		final int victoryParameter = 100;

		final Galaxy galaxy = galaxyManager.get(galaxyId);

		final Match tmpMatch = new Match();
		tmpMatch.setActivated(true);
		tmpMatch.setCreationDate(new Date(referenceTime));
		tmpMatch.setCreator(creator);
		tmpMatch.setFinishedDate(null);
		tmpMatch.setGalaxy(galaxy);
		tmpMatch.setParticipants(new ArrayList<Participant>());
		tmpMatch.setParticipantsMax(participantsMax);
		tmpMatch.setParticipantsMin(participantsMin);
		tmpMatch.setPlannedJoinType(EnumJoinType.joiningEnabled);
		tmpMatch.setSeed(seed);
		tmpMatch.setSpeed(speed);
		tmpMatch.setState(EnumMatchState.created);
		tmpMatch.setStartCondition(startCondition);
		tmpMatch.setStartDate(startDate);
		tmpMatch.setStartedJoinType(startedJoinType);
		tmpMatch.setStartPopulation(startPopulation);
		tmpMatch.setStartSystemCount(startSystemCount);
		tmpMatch.setStartSystemSelectionEnabled(startSystemSelectionEnabled);
		tmpMatch.setTitle(title);
		tmpMatch.setVictoryCondition(victoryCondition);
		tmpMatch.setVictoryParameter(victoryParameter);

		// dirty copy match using mapper ;-)
		final Match tmpMatch2 = mapper.fromMap(new Match(), mapper.toMap(tmpMatch));
		tmpMatch2.setId(matchId);

		// dirty copy match using mapper ;-)
		final Match finalMatch = mapper.fromMap(new Match(), mapper.toMap(tmpMatch));
		finalMatch.setId(matchId);
		finalMatch.setState(EnumMatchState.planned);
		finalMatch.setPlannedJoinType(plannedJoinType);

		final Long[] playerIds = new Long[] { 1L, 2L };

		final ExtendedRandom random = new ExtendedRandom(seed);

		securityManager.getSessionProvider().set(new MockHttpSession());
		securityManager.getPlayerProvider().set(creator);

		final String chCreate = UniverseConquestConstants.CHANNEL_MATCH_CREATED;

		Match result;

		final ConquestManager mockConquestManager = mockContext.mock(ConquestManager.class);
		final SolarSystemInfrastructureManager mockInfrastructureManager = mockContext.mock(SolarSystemInfrastructureManager.class);
		final ParticipantManager mockParticipantManager = mockContext.mock(ParticipantManager.class);
		MatchManagerImpl mockManager = new MatchManagerImpl(mockDao, galaxyManager, mockParticipantManager, solarSystemPopulationManager,
				mockInfrastructureManager, mockConquestManager);
		mockManager.setSecurityManager(((MatchManagerImpl) this.mockManager).getSecurityManager());

		// expectatins are a bit more complicated here...
		mockContext.checking(new Expectations() {
			{
				// first save (with planned join type = joining enabled)
				oneOf(mockDao).save(with(new BaseMatcher<Match>() {
					@Override
					public boolean matches(Object arg0)
					{
						boolean match = tmpMatch.equals(arg0);
						((Match) arg0).setId(matchId);
						return match;
					}

					@Override
					public void describeTo(Description arg0)
					{
					}
				}));
				will(new ReturnArgAction());
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(galaxy.getSolarSystems().size()).of(mockInfrastructureManager).initialize(with(equal(tmpMatch2)),
						with(new BaseMatcher<SolarSystem>() {
							private List<SolarSystem>	used	= new LinkedList<SolarSystem>();

							@Override
							public boolean matches(Object arg0)
							{
								boolean matches = galaxy.getSolarSystems().contains(arg0) && !used.contains(arg0);
								used.add((SolarSystem) arg0);
								return matches;
							}

							@Override
							public void describeTo(Description arg0)
							{
							}
						}), with(equal(random)));
				will(returnValue(new SolarSystemInfrastructure()));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(playerIds.length).of(mockParticipantManager).addParticipant(with(equal(tmpMatch2)), with(new BaseMatcher<Long>() {
					private List<Long>	used	= new LinkedList<Long>();

					@Override
					public boolean matches(Object arg0)
					{
						boolean matches = Arrays.asList(playerIds).contains(arg0) && !used.contains(arg0);
						used.add((Long) arg0);
						return matches;
					}

					@Override
					public void describeTo(Description arg0)
					{
					}
				}));
				will(returnValue(new Participant()));
			}
		});
		mockContext.checking(new Expectations() {
			{
				// second save (with updated planned join type)
				oneOf(mockDao).save(finalMatch);
				will(new ReturnArgAction());
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).get(matchId);
				will(returnValue(finalMatch));
			}
		});
		mockContext.checking(new Expectations() {
			{
				allowing(mockParticipantManager).getNumberOfParticipants(with(any(Match.class)));
				will(returnValue(playerIds.length));
			}
		});
		// check that the "create match" channel has been fired
		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).update(chCreate, finalMatch);
			}
		});
		result = mockManager.createMatch(title, galaxyId, speed, seed, startCondition, startDate, startSystemSelectionEnabled, startSystemCount,
				startPopulation, victoryCondition, victoryParameter, participantsMax, participantsMin, playerIds, plannedJoinType, startedJoinType);
		mockContext.assertIsSatisfied();
		assertNotNull(result);
		assertEquals(finalMatch, result);
	}

	public void testStartMatch() throws Exception
	{
		final ThreadLocal<Boolean> performed = new ThreadLocal<Boolean>();

		MatchManagerImpl matchManager = new MatchManagerImpl(mockDao, galaxyManager, participantManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager, conquestManager) {
			@Override
			protected Match performStartMatch(Match match)
			{
				performed.set(true);
				return match;
			}
		};
		matchManager.setSecurityManager(securityManager);
		securityManager.getSessionProvider().set(new MockHttpSession());

		Player admin = new Player();
		admin.setUser(new User());
		admin.getUser().setId(1L);
		admin.getUser().setRole(new UserRole());
		admin.getUser().getRole().setMask(ApplicationBaseConstants.ROLE_ADMIN);

		Player creator = new Player();
		creator.setUser(new User());
		creator.getUser().setId(2L);
		creator.getUser().setRole(new UserRole());
		creator.getUser().getRole().setMask(ApplicationBaseConstants.ROLE_NORMAL_USER);

		Player other = new Player();
		other.setUser(new User());
		other.getUser().setId(3L);
		other.getUser().setRole(new UserRole());
		other.getUser().getRole().setMask(ApplicationBaseConstants.ROLE_NORMAL_USER);

		Match match = new Match();
		match.setCreator(creator);
		match.setState(EnumMatchState.planned);

		securityManager.getPlayerProvider().set(admin);
		performed.set(false);
		matchManager.startMatch(match);
		assertTrue(performed.get());

		securityManager.getPlayerProvider().set(creator);
		performed.set(false);
		matchManager.startMatch(match);
		assertTrue(performed.get());

		try
		{
			securityManager.getPlayerProvider().set(other);
			performed.set(false);
			matchManager.startMatch(match);
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
			assertTrue(e.getMessage().startsWith("no access rights"));
			assertFalse(performed.get());
		}
	}

	public void testStartMatchIfNecessary() throws Exception
	{
		final ThreadLocal<Boolean> performed = new ThreadLocal<Boolean>();

		MatchManagerImpl matchManager = new MatchManagerImpl(mockDao, galaxyManager, participantManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager, conquestManager) {
			@Override
			protected Match performStartMatch(Match match)
			{
				performed.set(true);
				return match;
			}
		};
		BaseGameManager securityManager = new BaseGameManager(this.securityManager);
		matchManager.setSecurityManager(securityManager);
		securityManager.getSessionProvider().set(new MockHttpSession());
		securityManager.setTimeProvider(new MockTimeProvider(referenceTime));

		Match match = new Match();
		match.setParticipants(new ArrayList<Participant>());
		match.setState(EnumMatchState.planned);
		assertTrue(matchManager.isStartPossible(match));

		// immediately
		match.setStartCondition(EnumStartCondition.immediately);
		performed.set(false);
		assertTrue(matchManager.isStartNecessary(match));
		matchManager.startMatchIfNecessary(match);
		assertTrue(performed.get());

		// manually
		match.setStartCondition(EnumStartCondition.manually);
		performed.set(false);
		assertFalse(matchManager.isStartNecessary(match));
		matchManager.startMatchIfNecessary(match);
		assertFalse(performed.get());

		// planned
		match.setStartCondition(EnumStartCondition.planned);
		match.setStartDate(new Date(referenceTime + 10));
		performed.set(false);
		assertFalse(matchManager.isStartNecessary(match));
		matchManager.startMatchIfNecessary(match);
		assertFalse(performed.get());
		match.setStartDate(new Date(referenceTime - 10));
		assertTrue(matchManager.isStartNecessary(match));
		matchManager.startMatchIfNecessary(match);
		assertTrue(performed.get());
	}

	public void testIsStartNecessary() throws Exception
	{
		Match match = new Match();

		match.setStartCondition(EnumStartCondition.immediately);
		assertTrue(mockManager.isStartNecessary(match));

		match.setStartCondition(EnumStartCondition.manually);
		assertFalse(mockManager.isStartNecessary(match));

		match.setStartCondition(EnumStartCondition.planned);
		{
			match.setStartDate(new Date(referenceTime - 100));
			assertTrue(mockManager.isStartNecessary(match));

			match.setStartDate(new Date(referenceTime));
			assertTrue(mockManager.isStartNecessary(match));

			match.setStartDate(new Date(referenceTime + 100));
			assertFalse(mockManager.isStartNecessary(match));
		}
	}

	public void testIsStartPossible() throws Exception
	{
		Match match = new Match();

		match.setState(EnumMatchState.planned);
		match.setParticipantsMin(2);
		match.setParticipantsMax(4);

		match.setParticipants(new ArrayList<Participant>());

		for(int i = 1; i < match.getParticipantsMax() + 3; i++)
		{
			Participant p = new Participant();
			p.setActivated(true);
			match.getParticipants().add(p);

			if(i < match.getParticipantsMin() || i > match.getParticipantsMax())
				assertFalse(mockManager.isStartPossible(match));
			else
				assertTrue(mockManager.isStartPossible(match));
		}

		while(match.getParticipants().size() > match.getParticipantsMax())
			match.getParticipants().remove(0);

		assertTrue(mockManager.isStartPossible(match));

		match.setState(EnumMatchState.active);

		assertFalse(mockManager.isStartPossible(match));
	}

	public void testGetMatchStartNotPossibleReasons() throws Exception
	{
		Match match = new Match();

		match.setState(EnumMatchState.planned);
		match.setParticipantsMin(2);
		match.setParticipantsMax(4);

		match.setParticipants(new ArrayList<Participant>());

		List<String> reasons;

		for(int i = 1; i < match.getParticipantsMax() + 3; i++)
		{
			Participant p = new Participant();
			p.setActivated(true);
			match.getParticipants().add(p);

			reasons = mockManager.getMatchStartNotPossibleReasons(match);

			assertNotNull(reasons);
			if(i < match.getParticipantsMin())
			{
				assertEquals(1, reasons.size());
				assertTrue(reasons.contains(UniverseConquestConstants.REASON_NOT_ENOUGH_PARTICIPANTS));
			}
			else if(i > match.getParticipantsMax())
			{
				assertEquals(1, reasons.size());
				assertTrue(reasons.contains(UniverseConquestConstants.REASON_TOO_MANY_PARTICIPANTS));
			}
			else
			{
				assertEquals(0, reasons.size());
			}
		}

		while(match.getParticipants().size() > match.getParticipantsMax())
			match.getParticipants().remove(0);

		reasons = mockManager.getMatchStartNotPossibleReasons(match);
		assertNotNull(reasons);
		assertEquals(0, reasons.size());

		match.setState(EnumMatchState.active);

		reasons = mockManager.getMatchStartNotPossibleReasons(match);
		assertNotNull(reasons);
		assertEquals(1, reasons.size());
		assertTrue(reasons.contains(UniverseConquestConstants.REASON_ALREADY_STARTED));
	}

	public void testPerformStartMatch() throws Exception
	{
		final SolarSystemPopulationManager mockSolarSystemPopulationManager = mockContext.mock(SolarSystemPopulationManager.class);
		final ParticipantManager mockParticipantManager = mockContext.mock(ParticipantManager.class);
		MatchManagerImpl mockManager = new MatchManagerImpl(mockDao, galaxyManager, mockParticipantManager, mockSolarSystemPopulationManager,
				solarSystemInfrastructureManager, conquestManager);
		mockManager.setSecurityManager(((MatchManagerImpl) this.mockManager).getSecurityManager());
		mockManager.setCalculator(calculator);

		final int participantCount = 5;
		final int victoryParameter = 70;

		final Match match = new Match();
		match.setParticipants(new ArrayList<Participant>(participantCount));
		Participant p;
		for(int i = 0; i < participantCount + 1; i++)
		{
			p = new Participant();
			p.setId((long) i + 1);
			p.setEmpire(new Empire());
			p.getEmpire().setId((long) i + 1);
			p.getEmpire().setPlayer(new Player());
			p.getEmpire().getPlayer().setUser(new User());
			p.getEmpire().getPlayer().getUser().setUsername("user" + (i + 1));
			p.setPopulations(new ArrayList<SolarSystemPopulation>());
			p.setActivated(i < participantCount);
			match.getParticipants().add(p);
		}
		Collections.shuffle(match.getParticipants());
		match.setStartSystemCount(3);
		match.setSeed(123L);

		final List<SolarSystemPopulation> populations = new ArrayList<SolarSystemPopulation>(match.getStartSystemCount());
		for(int i = 0; i < match.getStartSystemCount(); i++)
			populations.add(new SolarSystemPopulation());

		final ExtendedRandom random = new ExtendedRandom(match.getSeed() + 1);

		// general expectation
		mockContext.checking(new Expectations() {
			{
				allowing(mockParticipantManager).getNumberOfParticipants(match);
				will(returnValue(participantCount));
			}
		});
		for(int i = 0; i < participantCount; i++)
		{
			final int fi = i;
			mockContext.checking(new Expectations() {
				{
					allowing(mockParticipantManager).get((long) fi + 1);
					will(returnValue(match.getParticipants().get(fi)));
				}
			});
		}

		// test without start date (manually) // domination
		match.setVictoryParameter(victoryParameter);
		match.setVictoryCondition(EnumVictoryCondition.domination);
		match.setStartCondition(EnumStartCondition.manually);
		match.setState(EnumMatchState.planned);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(match);
				will(returnValue(match));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).get(null);
				will(returnValue(match));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(participantCount).of(mockParticipantManager).randomSelectStartSystems(with(any(Participant.class)), with(equal(random)));
				will(returnValue(populations));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(participantCount).of(mockParticipantManager).startParticipating(with(any(Participant.class)),
						with(equal(new Date(referenceTime))));
				will(new ReturnArgAction(0));
			}
		});
		Match result1 = ((MatchManagerImpl) mockManager).performStartMatch(match);
		assertSame(match, result1);
		mockContext.assertIsSatisfied();
		assertNotNull(match);
		assertNotNull(match.getStartDate());
		assertEquals(new Date(referenceTime), match.getStartDate());
		assertEquals(victoryParameter, match.getVictoryParameter());
		assertEquals(calculator.calculateVictoryTimeout(match), match.getVictoryTimeout());
		for(Participant p1 : match.getParticipants())
		{
			assertNull(p1.getRivals());
		}

		// test with start date // extermination
		match.setVictoryParameter(victoryParameter);
		match.setVictoryCondition(EnumVictoryCondition.extermination);
		Date aStartDate = new Date(4321);
		match.setStartDate(aStartDate);
		match.setStartCondition(EnumStartCondition.planned);
		match.setState(EnumMatchState.planned); // reset state
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(match);
				will(returnValue(match));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).get(null);
				will(returnValue(match));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(participantCount).of(mockParticipantManager).randomSelectStartSystems(with(any(Participant.class)), with(equal(random)));
				will(returnValue(populations));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(participantCount).of(mockParticipantManager).startParticipating(with(any(Participant.class)),
						with(equal(match.getStartDate())));
				will(new ReturnArgAction(0));
			}
		});
		Match result2 = ((MatchManagerImpl) mockManager).performStartMatch(match);
		assertSame(match, result2);
		mockContext.assertIsSatisfied();
		assertNotNull(match);
		assertNotNull(match.getStartDate());
		assertEquals(aStartDate, match.getStartDate());
		assertEquals(victoryParameter, match.getVictoryParameter());
		assertEquals(calculator.calculateVictoryTimeout(match), match.getVictoryTimeout());
		for(Participant p1 : match.getParticipants())
		{
			assertNull(p1.getRivals());
		}

		// test the third victory condition // vendetta
		match.setVictoryCondition(EnumVictoryCondition.vendetta);
		match.setState(EnumMatchState.planned); // reset state
		match.setStartCondition(EnumStartCondition.immediately);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(match);
				will(returnValue(match));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).get(null);
				will(returnValue(match));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(participantCount).of(mockParticipantManager).randomSelectStartSystems(with(any(Participant.class)), with(equal(random)));
				will(returnValue(populations));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(participantCount).of(mockParticipantManager).startParticipating(with(any(Participant.class)),
						with(equal(new Date(referenceTime))));
				will(new ReturnArgAction(0));
			}
		});
		Match result3 = ((MatchManagerImpl) mockManager).performStartMatch(match);
		assertSame(match, result3);
		mockContext.assertIsSatisfied();
		assertNotNull(match);
		assertNotNull(match.getStartDate());
		assertEquals(new Date(referenceTime), match.getStartDate());
		assertEquals(UniverseConquestConstants.PARAM_VICTORY_VENDETTA_PARAM_DEFAULT.asInt(), match.getVictoryParameter());
		assertEquals(calculator.calculateVictoryTimeout(match), match.getVictoryTimeout());
		int rivals = mockManager.getNumberOfRivals(match);
		for(Participant p1 : match.getParticipants())
		{
			logger.debug("checking rivals for participant #" + p1.getId() + " (activated?" + p1.isActivated() + ") rivals:"
					+ (p1.getRivals() == null ? null : p1.getRivals().size()));
			if(p1.isActivated())
			{
				assertNotNull(p1.getRivals());
				assertEquals(rivals, p1.getRivals().size());
			}
			else
			{
				assertNull(p1.getRivals());
			}
		}

		// test with illegal state
		match.setState(EnumMatchState.active);
		try
		{
			((MatchManagerImpl) mockManager).performStartMatch(match);
			fail("expected exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
	}

	public void testAssignRivals() throws Exception
	{
		int maxParticipants = 10;

		List<Participant> participants = new ArrayList<Participant>(10);

		Participant newP;
		List<Long> rivalList = new ArrayList<Long>(maxParticipants);
		Map<Long, Integer> isRivalMap = new HashMap<Long, Integer>();
		// increase participants one by one
		for(int i = 1; i <= maxParticipants; i++)
		{
			newP = new Participant();
			newP.setId((long) i);
			newP.setEmpire(new Empire());
			newP.getEmpire().setId((long) i);
			participants.add(newP);

			logger.debug("participants: " + participants.size());

			// do the check for each possible amount of rivals
			for(int rivals = 1; rivals < participants.size(); rivals++)
			{
				logger.debug("  rivals: " + rivals);
				// redo procedure 10 times
				for(int j = 0; j < 10; j++)
				{
					mockManager.assignRivals(participants, rivals, new ExtendedRandom());

					isRivalMap.clear();
					for(Participant p : participants)
					{
						rivalList.clear();
						// convert rival list to int-list
						for(Participant rival : p.getRivals())
						{
							if(!rivalList.contains(rival.getId()))
								rivalList.add(rival.getId());

							if(!isRivalMap.containsKey(rival.getId()))
								isRivalMap.put(rival.getId(), 1);
							else
								isRivalMap.put(rival.getId(), isRivalMap.get(rival.getId()) + 1);
						}

						logger.debug(p.getId() + " -> " + rivalList);

						// if size matches there have been no duplicates
						assertEquals(rivals, rivalList.size());
						// check for the participant not to be it's own rival
						assertFalse(rivalList.contains(p.getId()));

						// clear rivals for next iteration
						p.getRivals().clear();
					}

					assertEquals(participants.size(), isRivalMap.size());
					for(Integer isRival : isRivalMap.values())
					{
						assertEquals(rivals, (int) isRival);
					}
				}
			}
		}

		// check reproducability

		long seed = 123456789;
		int numberOfRivals = 4;
		boolean[][] rivals1 = new boolean[participants.size()][participants.size()];
		boolean[][] rivals2 = new boolean[participants.size()][participants.size()];
		// create two rival-matrixes with the same seed and compare them
		mockManager.assignRivals(participants, numberOfRivals, new ExtendedRandom(seed));
		for(Participant p : participants)
		{
			for(Participant r : p.getRivals())
			{
				rivals1[(int) (long) p.getId() - 1][(int) (long) r.getId() - 1] = true;
			}
			p.getRivals().clear();
		}
		Collections.shuffle(participants);
		mockManager.assignRivals(participants, numberOfRivals, new ExtendedRandom(seed));
		for(Participant p : participants)
		{
			for(Participant r : p.getRivals())
			{
				rivals2[(int) (long) p.getId() - 1][(int) (long) r.getId() - 1] = true;
			}
			p.getRivals().clear();
		}

		for(int i1 = 0; i1 < rivals1.length; i1++)
		{
			for(int i2 = 0; i2 < rivals1.length; i2++)
			{
				assertEquals(rivals1[i1][i2], rivals2[i1][i2]);
			}
		}
	}

	public void testGetNumberOfRivals() throws Exception
	{
		// @formatter:off
		// participants					      0  1  2  3  4  5  6  7  8  9 10
		// opponents				          0  0  1  2  3  4  5  6  7  8  9
		// @formatter:on
		int[] expectedRivals_40 = new int[] { 0, 0, 1, 1, 2, 2, 2, 3, 3, 4, 4 };
		int[] expectedRivals_60 = new int[] { 0, 0, 1, 2, 2, 3, 3, 4, 5, 5, 6 };

		Match match = new Match();
		match.setParticipants(new ArrayList<Participant>(expectedRivals_40.length));
		match.setVictoryCondition(EnumVictoryCondition.vendetta);

		Participant p;
		for(int i = 1; i < expectedRivals_40.length; i++)
		{
			p = new Participant();
			p.setActivated(true);
			match.getParticipants().add(p);

			match.setVictoryParameter(40);
			assertEquals(expectedRivals_40[i], mockManager.getNumberOfRivals(match));

			match.setVictoryParameter(60);
			assertEquals(expectedRivals_60[i], mockManager.getNumberOfRivals(match));
		}

		match.setVictoryCondition(EnumVictoryCondition.extermination);
		assertEquals(0, mockManager.getNumberOfRivals(match));
	}

	public void testCancelMatch() throws Exception
	{
		int participants = 5;

		Player creator = playerManager.getByUsername("user1");
		Player other = playerManager.getByUsername("user2");
		Player admin = playerManager.getByUsername("admin");

		final Match match = new Match();
		match.setState(EnumMatchState.planned);
		match.setCreator(creator);
		match.setParticipants(new ArrayList<Participant>(participants));
		Participant p;
		for(int i = 0; i < participants; i++)
		{
			p = new Participant();
			p.setActivated(true);
			match.getParticipants().add(p);
		}

		securityManager.getSessionProvider().set(new MockHttpSession());

		// test with creator
		securityManager.getPlayerProvider().set(creator);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(match);
				will(returnValue(match));
			}
		});
		mockManager.cancelMatch(match);
		mockContext.assertIsSatisfied();
		checkAndResetCanceledOrFinishedMatch(match, true, EnumMatchState.planned);

		// test with admin
		securityManager.getPlayerProvider().set(admin);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(match);
				will(returnValue(match));
			}
		});
		mockManager.cancelMatch(match);
		mockContext.assertIsSatisfied();
		checkAndResetCanceledOrFinishedMatch(match, true, EnumMatchState.planned);

		// test with other player
		securityManager.getPlayerProvider().set(other);
		assertFalse(((MatchManagerImpl) mockManager).isAccessible(match, MatchAccessController.OPERATION_CANCEL));
		try
		{
			mockManager.cancelMatch(match);
			fail("expected exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}

		// test with illegal state (already started)
		securityManager.getPlayerProvider().set(creator);
		match.setState(EnumMatchState.active);
		assertFalse(((MatchManagerImpl) mockManager).isAccessible(match, MatchAccessController.OPERATION_CANCEL));
		try
		{
			mockManager.cancelMatch(match);
			fail("expected exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}

		// but for an admin this should be possible
		securityManager.getPlayerProvider().set(admin);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(match);
				will(returnValue(match));
			}
		});
		mockManager.cancelMatch(match);
		mockContext.assertIsSatisfied();
		checkAndResetCanceledOrFinishedMatch(match, true, EnumMatchState.active);
	}

	public void testFinishMatch() throws Exception
	{
		int participants = 5;

		final Match match = new Match();
		match.setVictoryParameter(participants * 10);
		match.setParticipants(new ArrayList<Participant>(participants));
		Participant p;
		for(int i = 0; i < participants; i++)
		{
			p = new Participant();
			p.getRank().setRank(i + 1);
			p.getRank().setValue((participants - 1 - i) * 10);
			match.getParticipants().add(p);
		}

		// pre-check
		assertFalse(mockManager.isVictoryConditionMet(match));

		// match is not active
		match.setState(EnumMatchState.planned);
		mockManager.finishMatch(match);
		mockContext.assertIsSatisfied();
		assertNull(match.getFinishedDate());

		// now match is active // but victory condition still not met
		match.setState(EnumMatchState.active);
		mockManager.finishMatch(match);
		mockContext.assertIsSatisfied();
		assertNull(match.getFinishedDate());

		// now victory condition will be met
		match.setVictoryParameter((participants - 1) * 10);
		match.setVictoryTimeout(0);
		match.getParticipants().get(0).getRank().setVictoryDate(new Date(0));
		assertTrue(mockManager.isVictoryConditionMet(match));

		// check with victory condition met
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(match);
				will(returnValue(match));
			}
		});
		mockManager.finishMatch(match);
		mockContext.assertIsSatisfied();
		checkAndResetCanceledOrFinishedMatch(match, false, EnumMatchState.active);
	}

	private void checkAndResetCanceledOrFinishedMatch(Match match, boolean trueForCanceledFalseForFinished, EnumMatchState resetToState)
	{
		Date date, other;

		if(trueForCanceledFalseForFinished)
		{
			date = match.getCanceledDate();
			other = match.getFinishedDate();
			assertEquals(EnumMatchState.canceled, match.getState());
		}
		else
		{
			date = match.getFinishedDate();
			other = match.getCanceledDate();
			assertEquals(EnumMatchState.finished, match.getState());
		}

		assertNotNull(date);
		assertEquals(referenceTime, date.getTime());
		assertNull(other);

		for(Participant p : match.getParticipants())
		{
			assertTrue(p.getRank().isFinal());
			if(trueForCanceledFalseForFinished)
			{
				if(resetToState == EnumMatchState.planned)
					assertFalse(p.isActivated());
				else
					assertTrue(p.isActivated());
			}
		}

		if(resetToState != null)
		{
			match.setCanceledDate(null);
			match.setFinishedDate(null);
			match.setState(resetToState);

			for(Participant p : match.getParticipants())
			{
				p.getRank().setFinal(false);
				p.setActivated(true);
			}
		}
	}

	public void testIsVictoryConditionMet() throws Exception
	{
		int participants = 5;

		Match match = new Match();
		match.setParticipants(new ArrayList<Participant>(participants));

		Participant p;
		for(int i = 0; i < participants; i++)
		{
			p = new Participant();
			p.getRank().setRank(i + 1);
			p.getRank().setValue((participants - 1 - i) * 10);
			match.getParticipants().add(p);
		}
		Collections.shuffle(match.getParticipants());

		((MatchManagerImpl) mockManager).getSecurityManager().getTimeProvider().set(referenceTime);

		// no participant with victory date
		match.setVictoryTimeout(0); // no timeout
		assertFalse(mockManager.isVictoryConditionMet(match));
		match.setVictoryTimeout(1000); // with timeout
		assertFalse(mockManager.isVictoryConditionMet(match));
		match.setVictoryTimeout(10000); // with timeout
		assertFalse(mockManager.isVictoryConditionMet(match));

		// 1 participant with victory date
		match.getParticipants().get(0).getRank().setVictoryDate(new Date(0));
		match.setVictoryTimeout(0); // no timeout
		assertTrue(mockManager.isVictoryConditionMet(match));
		match.setVictoryTimeout(1000); // timeout passed
		assertTrue(mockManager.isVictoryConditionMet(match));
		match.setVictoryTimeout(10000); // timeout not passed
		assertFalse(mockManager.isVictoryConditionMet(match));

		// 2 participants with victory date
		match.getParticipants().get(0).getRank().setVictoryDate(new Date(1000));
		match.getParticipants().get(1).getRank().setVictoryDate(new Date(0));
		match.setVictoryTimeout(0); // no timeout
		assertTrue(mockManager.isVictoryConditionMet(match));
		match.setVictoryTimeout(1000); // timeout passed
		assertTrue(mockManager.isVictoryConditionMet(match));
		match.setVictoryTimeout(10000); // timeout not passed
		assertFalse(mockManager.isVictoryConditionMet(match));
	}

	public void testUpdateRanking() throws Exception
	{
		int participants = 5;
		int systems = 20;

		final Match match = new Match();
		// add dummy galaxy and systems
		match.setGalaxy(new Galaxy());
		match.getGalaxy().setId(0L);
		match.getGalaxy().setSolarSystems(new ArrayList<SolarSystem>(systems));
		for(int i = 0; i < systems; i++)
			match.getGalaxy().getSolarSystems().add(new SolarSystem());
		// init participants
		match.setParticipants(new ArrayList<Participant>(participants));
		// 12 pops: 10 existing, 2 destroyed - 1 part destroyed (#2)
		match.getParticipants().add(createParticipant(100, 200, 50, 20, 300, 500, 0, 10, 400, 150, 350, 0));
		int pop0 = 2080;
		// 8 pops: 6 existing, 2 destroyed - 2 parts destroyed (#2,#3)
		match.getParticipants().add(createParticipant(10, 20, 50, 0, 200, 100, 0, 400));
		int pop1 = 780;
		// 5 pops: all destroyed
		match.getParticipants().add(createParticipant(0, 0, 0, 0, 0));
		int pop2 = 0;
		// 3 pops: all destroyed
		match.getParticipants().add(createParticipant(0, 0, 0));
		int pop3 = 0;
		// 5 pops: all existing
		match.getParticipants().add(createParticipant(200, 400, 500, 350, 100));
		int pop4 = 1550;
		// assign rivals (for each participants the next 2 are rivals)
		for(int i = 0; i < match.getParticipants().size(); i++)
		{
			match.getParticipants().get(i).getRivals().add(match.getParticipants().get((i + 1) % match.getParticipants().size()));
			match.getParticipants().get(i).getRivals().add(match.getParticipants().get((i + 2) % match.getParticipants().size()));
		}

		int totalPop = pop0 + pop1 + pop2 + pop3 + pop4;
		logger.debug("total population = " + totalPop);

		// mark one participant's rank as final
		int destroyedParticipant = 3;
		match.getParticipants().get(destroyedParticipant).getRank().setRank(5);
		match.getParticipants().get(destroyedParticipant).getRank().setFinal(true);

		// extermination
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).get(null);
				will(returnValue(match));
			}
		});
		match.setVictoryCondition(EnumVictoryCondition.extermination);
		mockManager.updateRanking(match);
		mockContext.assertIsSatisfied();
		checkRank(match.getParticipants(), 0, 1, (int) Math.floor(100.0 * pop0 / totalPop), false, false);
		checkRank(match.getParticipants(), 1, 3, (int) Math.floor(100.0 * pop1 / totalPop), false, false);
		checkRank(match.getParticipants(), 2, 4, (int) Math.floor(100.0 * pop2 / totalPop), true, false);
		checkRank(match.getParticipants(), 3, 5, (int) Math.floor(100.0 * pop3 / totalPop), true, true);
		checkRank(match.getParticipants(), 4, 2, (int) Math.floor(100.0 * pop4 / totalPop), false, false);

		resetRankss(match.getParticipants(), destroyedParticipant);

		// domination
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).get(null);
				will(returnValue(match));
			}
		});
		match.setVictoryCondition(EnumVictoryCondition.domination);
		mockManager.updateRanking(match);
		mockContext.assertIsSatisfied();
		checkRank(match.getParticipants(), 0, 1, (int) Math.floor(100.0 * 10 / systems), false, false);
		checkRank(match.getParticipants(), 1, 2, (int) Math.floor(100.0 * 6 / systems), false, false);
		checkRank(match.getParticipants(), 2, 4, (int) Math.floor(100.0 * 0 / systems), true, false);
		checkRank(match.getParticipants(), 3, 5, (int) Math.floor(100.0 * 0 / systems), true, true);
		checkRank(match.getParticipants(), 4, 3, (int) Math.floor(100.0 * 5 / systems), false, false);

		resetRankss(match.getParticipants(), destroyedParticipant);

		// domination (not all alone)
		// putting 2 or more populations into the same system --> reduction in count
		// part 1 & 0 & 4 sharing system
		setSameSystem(match.getParticipants(), 1, 0, 0, 0);
		setSameSystem(match.getParticipants(), 1, 0, 4, 0);
		// part 1 & 0 sharing (another) system
		setSameSystem(match.getParticipants(), 1, 1, 0, 1);
		// part 1 & 0 sharing (another) system
		setSameSystem(match.getParticipants(), 1, 2, 0, 2);
		// --> part 0 => -3
		// --> part 1 => -3 (-1 Rank)
		// --> part 4 => -1 (+1 Rank)
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).get(null);
				will(returnValue(match));
			}
		});
		match.setVictoryCondition(EnumVictoryCondition.domination);
		mockManager.updateRanking(match);
		mockContext.assertIsSatisfied();
		checkRank(match.getParticipants(), 0, 1, (int) Math.floor(100.0 * 7 / systems), false, false);
		checkRank(match.getParticipants(), 1, 3, (int) Math.floor(100.0 * 3 / systems), false, false);
		checkRank(match.getParticipants(), 2, 4, (int) Math.floor(100.0 * 0 / systems), true, false);
		checkRank(match.getParticipants(), 3, 5, (int) Math.floor(100.0 * 0 / systems), true, true);
		checkRank(match.getParticipants(), 4, 2, (int) Math.floor(100.0 * 4 / systems), false, false);

		resetRankss(match.getParticipants(), destroyedParticipant);

		// vendetta
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).get(null);
				will(returnValue(match));
			}
		});
		match.setVictoryCondition(EnumVictoryCondition.vendetta);
		match.setVictoryParameter(40);
		int rivals = mockManager.getNumberOfRivals(match);
		assertEquals(2, rivals);
		mockManager.updateRanking(match);
		mockContext.assertIsSatisfied();
		checkRank(match.getParticipants(), 0, 2, (int) Math.floor(100.0 * 1 / rivals), false, false);
		checkRank(match.getParticipants(), 1, 1, (int) Math.floor(100.0 * 2 / rivals), false, false);
		checkRank(match.getParticipants(), 2, 4, (int) Math.floor(100.0 * 1 / rivals), true, false);
		checkRank(match.getParticipants(), 3, 5, (int) Math.floor(100.0 * 0 / rivals), true, true);
		checkRank(match.getParticipants(), 4, 3, (int) Math.floor(100.0 * 0 / rivals), false, false);
	}

	/**
	 * Sets the Infrastructure of pop1 as the Infrastructure of pop2 and adds pop2 to the
	 * infrastructure.
	 * 
	 * @param participants
	 * @param part1
	 * @param pop1
	 * @param part2
	 * @param pop2
	 */
	private void setSameSystem(List<Participant> participants, int part1, int pop1, int part2, int pop2)
	{
		SolarSystemInfrastructure inf = participants.get(part1).getPopulations().get(pop1).getInfrastructure();
		SolarSystemPopulation population2 = participants.get(part2).getPopulations().get(pop2);
		population2.setInfrastructure(inf);
		inf.getPopulations().add(population2);
	}

	private void checkRank(List<Participant> participants, int index, int rankExpected, int rankValueExpected, boolean rankFinalExpected,
			boolean wasFinalBefore)
	{
		assertEquals(rankExpected, participants.get(index).getRank().getRank());
		assertEquals(rankValueExpected, participants.get(index).getRank().getValue());
		assertEquals(rankFinalExpected, participants.get(index).getRank().isFinal());
		if(wasFinalBefore)
			assertNull(participants.get(index).getRank().getDate());
		else
			assertEquals(new Date(referenceTime), participants.get(index).getRank().getDate());
	}

	private void resetRankss(List<Participant> participants, Integer... ignoreIndexes)
	{
		List<Integer> ignore = Arrays.asList(ignoreIndexes);
		Participant p;
		for(int i = 0; i < participants.size(); i++)
		{
			if(ignore.contains(i))
				continue;
			p = participants.get(i);
			p.getRank().setFinal(false);
			p.getRank().setValue(0);
			p.getRank().setRank(0);
		}
	}

	private Participant createParticipant(int... pops)
	{
		Participant p = new Participant();
		p.setActivated(pops.length > 0);
		p.setRivals(new ArrayList<Participant>(5));
		p.setPopulations(new ArrayList<SolarSystemPopulation>(pops.length));

		int totalPop = 0;
		SolarSystemPopulation population;
		for(int pop : pops)
		{
			totalPop += pop;
			population = new SolarSystemPopulation();
			population.setPopulation(pop);
			if(pop == 0)
				population.setDestructionDate(new Date(referenceTime));
			population.setInfrastructure(new SolarSystemInfrastructure());
			population.getInfrastructure().setPopulations(new ArrayList<SolarSystemPopulation>());
			population.getInfrastructure().getPopulations().add(population);
			p.getPopulations().add(population);
		}

		if(totalPop == 0)
			p.setDestructionDate(new Date(referenceTime));

		return p;
	}

	public void testCreateChannels() throws Exception
	{
		final long matchId = 5678L;
		final Match match = new Match();
		match.setId(matchId);

		final List<Rank> rankList = new ArrayList<Rank>();
		final List<List<Long>> systemList = new ArrayList<List<Long>>();
		final List<List<Long>> movementList = new ArrayList<List<Long>>();

		final ConquestManager mockConquestManager = mockContext.mock(ConquestManager.class);
		MatchManagerImpl matchManager = new MatchManagerImpl(mockDao, galaxyManager, participantManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager, mockConquestManager) {
			public List<Rank> getRankList(Match match)
			{
				return rankList;
			}

			public List<List<Long>> getSystemList(Match match, Date time)
			{
				assertEquals(referenceTime, time.getTime());
				return systemList;
			}

			public List<List<Long>> getMovementList(Match match, Date time)
			{
				return movementList;
			}
		};

		BaseGameManager securityManager = new BaseGameManager(this.securityManager);
		securityManager.setTimeProvider(new MockTimeProvider(referenceTime));
		((MatchManagerImpl) matchManager).setSecurityManager(securityManager);

		final String chRanks = UniverseConquestConstants.CHANNEL_MATCH_RANKS.replace(UniverseConquestConstants.CHANNEL_ID_PLACEHOLDER, "" + matchId);
		final String chSystems = UniverseConquestConstants.CHANNEL_MATCH_SYSTEMS.replace(UniverseConquestConstants.CHANNEL_ID_PLACEHOLDER, ""
				+ matchId);
		final String chMovements = UniverseConquestConstants.CHANNEL_MATCH_MOVEMENTS.replace(UniverseConquestConstants.CHANNEL_ID_PLACEHOLDER, ""
				+ matchId);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).createChannel(with(equal(chRanks)), with((Object) null));
				will(returnValue(true));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).createChannel(with(equal(chSystems)), with((Object) null));
				will(returnValue(true));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).createChannel(with(equal(chMovements)), with((Object) null));
				will(returnValue(true));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).update(with(equal(chRanks)), with(same(rankList)));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).update(with(equal(chSystems)), with(same(systemList)));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).update(with(equal(chMovements)), with(same(movementList)));
			}
		});

		matchManager.createChannels(match);
		mockContext.assertIsSatisfied();
	}

	public void testUpdateChannels() throws Exception
	{
		final long matchId = 5678L;
		final Match match = new Match();
		match.setId(matchId);

		final List<Rank> rankList = new ArrayList<Rank>();
		final List<List<Long>> systemList = new ArrayList<List<Long>>();
		final List<List<Long>> movementList = new ArrayList<List<Long>>();

		final ConquestManager mockConquestManager = mockContext.mock(ConquestManager.class);
		MatchManagerImpl matchManager = new MatchManagerImpl(mockDao, galaxyManager, participantManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager, mockConquestManager) {
			public List<Rank> getRankList(Match match)
			{
				return rankList;
			}

			public List<List<Long>> getSystemList(Match match, Date time)
			{
				assertEquals(referenceTime, time.getTime());
				return systemList;
			}

			public List<List<Long>> getMovementList(Match match, Date time)
			{
				return movementList;
			}
		};

		BaseGameManager securityManager = new BaseGameManager(this.securityManager);
		securityManager.setTimeProvider(new MockTimeProvider(referenceTime));
		((MatchManagerImpl) matchManager).setSecurityManager(securityManager);

		final String chRanks = UniverseConquestConstants.CHANNEL_MATCH_RANKS.replace(UniverseConquestConstants.CHANNEL_ID_PLACEHOLDER, "" + matchId);
		final String chSystems = UniverseConquestConstants.CHANNEL_MATCH_SYSTEMS.replace(UniverseConquestConstants.CHANNEL_ID_PLACEHOLDER, ""
				+ matchId);
		final String chMovements = UniverseConquestConstants.CHANNEL_MATCH_MOVEMENTS.replace(UniverseConquestConstants.CHANNEL_ID_PLACEHOLDER, ""
				+ matchId);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).update(with(equal(chRanks)), with(same(rankList)));
			}
		});

		matchManager.updateChannels(match, true, false, false);
		mockContext.assertIsSatisfied();

		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).update(with(equal(chSystems)), with(same(systemList)));
			}
		});

		matchManager.updateChannels(match, false, true, false);
		mockContext.assertIsSatisfied();

		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).update(with(equal(chMovements)), with(same(movementList)));
			}
		});

		matchManager.updateChannels(match, false, false, true);
		mockContext.assertIsSatisfied();

		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).update(with(equal(chRanks)), with(same(rankList)));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).update(with(equal(chSystems)), with(same(systemList)));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockConquestManager).update(with(equal(chMovements)), with(same(movementList)));
			}
		});

		matchManager.updateChannels(match, true, true, true);
		mockContext.assertIsSatisfied();
	}

	public void testGetRankList() throws Exception
	{
		Match match = new Match();
		match.setParticipants(new ArrayList<Participant>());

		int participantCount = 5;

		Participant p;
		for(int i = 0; i < participantCount; i++)
		{
			p = new Participant();
			p.setRank(new Rank());
			p.setEmpire(new Empire());
			p.getEmpire().setPlayer(new Player());
			p.getEmpire().getPlayer().setUser(new User());
			p.getEmpire().getPlayer().getUser().setUsername("user" + i);

			// activate even entries only
			p.setActivated(i % 2 == 0);

			match.getParticipants().add(p);
		}

		List<Rank> ranks = mockManager.getRankList(match);

		assertNotNull(ranks);
		assertEquals((participantCount + 1) / 2, ranks.size());

		// check specific ranks
		// + check if the display name has been set

		for(int i = 0; i < ranks.size(); i++)
		{
			assertSame(ranks.get(i), match.getParticipants().get(i * 2).getRank());
			assertNotNull(ranks.get(i).getDisplayName());
			assertEquals("user" + (i * 2), ranks.get(i).getDisplayName());
		}
	}

	@TestCoversMethods({ "getSystemList", "getMovementList" })
	public void testGetLists() throws Exception
	{
		/*
		 * Create a match
		 * The match will contain 6 systems / infrastructures of which
		 * - 2 are unpopulated
		 * - 2 are populated by a single participant
		 * - 1 is populated by two participants
		 * - 1 non-modified -> will not appear in lists
		 * plus some movements from each single-participant-systems to
		 * - the shared system
		 * - 1 unpopulated system
		 */
		// TODO update
		Match match = new Match();

		// 6 systems / infs
		List<SolarSystemInfrastructure> infs = new ArrayList<SolarSystemInfrastructure>();
		for(int i = 0; i < 6; i++)
		{
			SolarSystemInfrastructure inf = new SolarSystemInfrastructure();
			inf.setId(1000L + i);
			inf.setInfrastructure(9999L * i);
			inf.setHabitability(99 * i);
			inf.setSolarSystem(new SolarSystem());
			inf.getSolarSystem().setId(2000L + i);
			inf.setPopulations(new ArrayList<SolarSystemPopulation>());
			inf.setModified(i < 5); // 6th inf is not modified (see above)
			infs.add(inf);
		}
		match.setInfrastructures(infs);

		// 2 parts
		List<Participant> parts = new ArrayList<Participant>();
		for(int i = 0; i < 2; i++)
		{
			Participant p = new Participant();
			p.setId(3000L + i);
			p.setPopulations(new ArrayList<SolarSystemPopulation>());
			p.setEmpire(new Empire());
			p.getEmpire().setId(4000L + i);
			parts.add(p);
		}

		// 3 systems are populated
		// sys 1 -> part 0
		SolarSystemPopulation p10 = createPopulation(parts.get(0), infs.get(1), null, new Date(referenceTime - 1000));
		// sys 2 -> part 1
		SolarSystemPopulation p21 = createPopulation(parts.get(1), infs.get(2), null, new Date(referenceTime - 1000));
		// sys 3 -> part 0 & part 1 (part 1 first)
		SolarSystemPopulation p30 = createPopulation(parts.get(0), infs.get(3), p10, new Date(referenceTime - 400));
		SolarSystemPopulation p31 = createPopulation(parts.get(1), infs.get(3), p21, new Date(referenceTime - 500));

		// some movements
		// part 0 : sys 1 -> sys 0
		SolarSystemPopulation m10 = createPopulation(parts.get(0), infs.get(0), p10, new Date(referenceTime + 100));
		// part 0 : sys 1 -> sys 3
		SolarSystemPopulation m13 = createPopulation(parts.get(0), infs.get(3), p10, new Date(referenceTime + 200));
		// part 1 : sys 2 -> sys 4
		SolarSystemPopulation m24 = createPopulation(parts.get(1), infs.get(4), p21, new Date(referenceTime + 300));
		// part 1 : sys 2 -> sys 3
		SolarSystemPopulation m23 = createPopulation(parts.get(1), infs.get(3), p21, new Date(referenceTime + 400));

		// now create both lists and check their content
		List<List<Long>> systemList = mockManager.getSystemList(match, new Date(referenceTime));
		List<List<Long>> movementList = mockManager.getMovementList(match, new Date(referenceTime));

		System.out.println("LIST RESULTS:");
		System.out.println(systemList);
		System.out.println(movementList);

		// @formatter:off

		// check system list
		assertNotNull(systemList);
		assertEquals(5, systemList.size());
		// check entries
		assertEquals(Arrays.asList(new Long[] {
													infs.get(0).getSolarSystem().getId(),
													(long) infs.get(0).getHabitability(),
													infs.get(0).getInfrastructure()
											}), systemList.get(0));
		assertEquals(Arrays.asList(new Long[] {
													infs.get(1).getSolarSystem().getId(),
													(long) infs.get(1).getHabitability(),
													infs.get(1).getInfrastructure(),
													p10.getParticipant().getEmpire().getId(),
													p10.getPopulation()
											}), systemList.get(1));
		assertEquals(Arrays.asList(new Long[] {
													infs.get(2).getSolarSystem().getId(),
													(long) infs.get(2).getHabitability(),
													infs.get(2).getInfrastructure(),
													p21.getParticipant().getEmpire().getId(),
													p21.getPopulation()
											}), systemList.get(2));
		assertEquals(Arrays.asList(new Long[] {
													infs.get(3).getSolarSystem().getId(),
													(long) infs.get(3).getHabitability(),
													infs.get(3).getInfrastructure(),
													p31.getParticipant().getEmpire().getId(),
													p31.getPopulation(),
													p30.getParticipant().getEmpire().getId(),
													p30.getPopulation()
											}), systemList.get(3));
		assertEquals(Arrays.asList(new Long[] {
													infs.get(4).getSolarSystem().getId(),
													(long) infs.get(4).getHabitability(),
													infs.get(4).getInfrastructure()
											}),systemList.get(4));

		// check movement list
		assertNotNull(movementList);
		assertEquals(4, movementList.size());
		// check entries
		assertEquals(Arrays.asList(new Long[] {
													infs.get(0).getSolarSystem().getId(),
													infs.get(1).getSolarSystem().getId(),
													m10.getParticipant().getEmpire().getId(),
													1000L,
													100L
											}), movementList.get(0));
		assertEquals(Arrays.asList(new Long[] {
													infs.get(3).getSolarSystem().getId(),
													infs.get(1).getSolarSystem().getId(),
													m13.getParticipant().getEmpire().getId(),
													1000L,
													200L
											}), movementList.get(1));
		assertEquals(Arrays.asList(new Long[] {
													infs.get(3).getSolarSystem().getId(),
													infs.get(2).getSolarSystem().getId(),
													m23.getParticipant().getEmpire().getId(),
													1000L,
													400L
											}), movementList.get(2));
		assertEquals(Arrays.asList(new Long[] {
													infs.get(4).getSolarSystem().getId(),
													infs.get(2).getSolarSystem().getId(),
													m24.getParticipant().getEmpire().getId(),
													1000L,
													300L
											}), movementList.get(3));
		
		// @formatter:on
	}

	private SolarSystemPopulation createPopulation(Participant part, SolarSystemInfrastructure inf, SolarSystemPopulation origin, Date colDate)
	{
		SolarSystemPopulation pop = new SolarSystemPopulation();
		pop.setId(part.getId() * 10 + inf.getId());
		pop.setActivated(true);
		pop.setPopulation(pop.getId());
		pop.setParticipant(part);
		pop.setInfrastructure(inf);
		pop.setColonizationDate(colDate);
		pop.setOriginationDate(new Date(colDate.getTime() - 1000));
		pop.setOrigin(origin);
		part.getPopulations().add(pop);
		inf.getPopulations().add(pop);
		return pop;
	}
}