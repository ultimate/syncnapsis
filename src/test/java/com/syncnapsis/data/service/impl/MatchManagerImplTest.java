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
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.data.service.MatchManager;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.data.service.ParticipantManager;
import com.syncnapsis.data.service.PlayerManager;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumJoinType;
import com.syncnapsis.enums.EnumMatchState;
import com.syncnapsis.enums.EnumStartCondition;
import com.syncnapsis.enums.EnumVictoryCondition;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.security.accesscontrol.MatchAccessController;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.data.ExtendedRandom;

@TestCoversClasses({ MatchManager.class, MatchManagerImpl.class })
@TestExcludesMethods({ "isAccessible", "*etSecurityManager", "afterPropertiesSet" })
public class MatchManagerImplTest extends GenericNameManagerImplTestCase<Match, Long, MatchManager, MatchDao>
{
	private GalaxyManager						galaxyManager;
	private ParticipantManager					participantManager;
	private SolarSystemPopulationManager		solarSystemPopulationManager;
	private SolarSystemInfrastructureManager	solarSystemInfrastructureManager;
	private ParameterManager					parameterManager;
	private PlayerManager						playerManager;

	private BaseGameManager						securityManager;

	private long								referenceTime	= 1234;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Match());
		setDaoClass(MatchDao.class);
		setMockDao(mockContext.mock(MatchDao.class));
		setMockManager(new MatchManagerImpl(mockDao, galaxyManager, participantManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager, parameterManager));

		BaseGameManager securityManager = new BaseGameManager(this.securityManager);
		securityManager.setTimeProvider(new MockTimeProvider(referenceTime));

		((MatchManagerImpl) mockManager).setSecurityManager(securityManager);
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

	public void testCreateMatch() throws Exception
	{
		Long galaxyId = 1L;
		Player creator = playerManager.getByUsername("user1");
		int participantsMax = 5;
		int participantsMin = 3;
		EnumJoinType plannedJoinType = EnumJoinType.invitationsOnly;
		long seed = 123456789;
		int speed = 100;
		EnumStartCondition startCondition = EnumStartCondition.manually;
		Date startDate = new Date(2345);
		EnumJoinType startedJoinType = EnumJoinType.none;
		int startSystemCount = 2;
		int startPopulation = 1000;
		boolean startSystemSelectionEnabled = true;
		String title = "test-battle";
		EnumVictoryCondition victoryCondition = EnumVictoryCondition.domination;
		int victoryParameter = 100;

		final Galaxy galaxy = galaxyManager.get(galaxyId);

		final Match match = new Match();
		match.setId(123L);
		match.setActivated(true);
		match.setCreationDate(new Date(referenceTime));
		match.setCreator(creator);
		match.setFinishedDate(null);
		match.setGalaxy(galaxy);
		match.setParticipantsMax(participantsMax);
		match.setParticipantsMin(participantsMin);
		match.setPlannedJoinType(plannedJoinType);
		match.setSeed(seed);
		match.setSpeed(speed);
		match.setStartCondition(startCondition);
		match.setStartDate(startDate);
		match.setStartedJoinType(startedJoinType);
		match.setStartPopulation(startPopulation);
		match.setStartSystemCount(startSystemCount);
		match.setStartSystemSelectionEnabled(startSystemSelectionEnabled);
		match.setTitle(title);
		match.setVictoryCondition(victoryCondition);
		match.setVictoryParameter(victoryParameter);

		final List<Long> playerIds = new ArrayList<Long>();
		playerIds.add(1L);
		playerIds.add(2L);

		final ExtendedRandom random = new ExtendedRandom(seed);

		securityManager.getSessionProvider().set(new MockHttpSession());
		securityManager.getPlayerProvider().set(creator);

		Match result;

		final SolarSystemInfrastructureManager mockInfrastructureManager = mockContext.mock(SolarSystemInfrastructureManager.class);
		final ParticipantManager mockParticipantManager = mockContext.mock(ParticipantManager.class);
		MatchManagerImpl mockManager = new MatchManagerImpl(mockDao, galaxyManager, mockParticipantManager, solarSystemPopulationManager,
				mockInfrastructureManager, parameterManager);
		mockManager.setSecurityManager(((MatchManagerImpl) this.mockManager).getSecurityManager());

		// expectatins are a bit more complicated here...
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(with(new BaseMatcher<Match>() {
					@Override
					public boolean matches(Object arg0)
					{
						if(((Match) arg0).getId() != null)
							return false;
						((Match) arg0).setId(match.getId());
						return match.equals(arg0);
					}

					@Override
					public void describeTo(Description arg0)
					{
					}
				}));
				will(returnValue(match));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).get(match.getId());
				will(returnValue(match));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(galaxy.getSolarSystems().size()).of(mockInfrastructureManager).initialize(with(equal(match)),
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
				exactly(playerIds.size()).of(mockParticipantManager).addParticipant(with(equal(match)), with(new BaseMatcher<Long>() {
					private List<Long>	used	= new LinkedList<Long>();

					@Override
					public boolean matches(Object arg0)
					{
						boolean matches = playerIds.contains(arg0) && !used.contains(arg0);
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
		result = mockManager.createMatch(title, galaxyId, speed, seed, startCondition, startDate, startSystemSelectionEnabled, startSystemCount,
				startPopulation, victoryCondition, victoryParameter, participantsMax, participantsMin, playerIds, plannedJoinType, startedJoinType);
		mockContext.assertIsSatisfied();
		assertNotNull(result);
		assertEquals(match, result);
	}

	public void testStartMatch() throws Exception
	{
		final ThreadLocal<Boolean> performed = new ThreadLocal<Boolean>();

		MatchManagerImpl matchManager = new MatchManagerImpl(mockDao, galaxyManager, participantManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager, parameterManager) {
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
		admin.getUser().getRole().setRolename(ApplicationBaseConstants.ROLE_ADMIN);

		Player creator = new Player();
		creator.setUser(new User());
		creator.getUser().setId(2L);
		creator.getUser().setRole(new UserRole());
		creator.getUser().getRole().setRolename(ApplicationBaseConstants.ROLE_NORMAL_USER);

		Player other = new Player();
		other.setUser(new User());
		other.getUser().setId(3L);
		other.getUser().setRole(new UserRole());
		other.getUser().getRole().setRolename(ApplicationBaseConstants.ROLE_NORMAL_USER);

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
				solarSystemInfrastructureManager, parameterManager) {
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

		// immediately
		match.setStartCondition(EnumStartCondition.immediately);
		performed.set(false);
		matchManager.startMatchIfNecessary(match);
		assertTrue(performed.get());

		// manually
		match.setStartCondition(EnumStartCondition.manually);
		performed.set(false);
		matchManager.startMatchIfNecessary(match);
		assertFalse(performed.get());

		// planned
		match.setStartCondition(EnumStartCondition.planned);
		match.setStartDate(new Date(referenceTime + 10));
		performed.set(false);
		matchManager.startMatchIfNecessary(match);
		assertFalse(performed.get());
		match.setStartDate(new Date(referenceTime - 10));
		matchManager.startMatchIfNecessary(match);
		assertTrue(performed.get());

		// plannedAndMinParticipantsReached
		match.setStartCondition(EnumStartCondition.plannedAndMinParticipantsReached);
		match.setParticipantsMin(2);
		match.setParticipants(new ArrayList<Participant>());
		performed.set(false);
		matchManager.startMatchIfNecessary(match);
		assertFalse(performed.get());
		match.getParticipants().add(new Participant());
		match.getParticipants().add(new Participant());
		matchManager.startMatchIfNecessary(match);
		assertTrue(performed.get());

		// maxParticipantsReached
		match.setStartCondition(EnumStartCondition.maxParticipantsReached);
		match.setParticipantsMax(3);
		performed.set(false);
		matchManager.startMatchIfNecessary(match);
		assertFalse(performed.get());
		match.getParticipants().add(new Participant());
		matchManager.startMatchIfNecessary(match);
		assertTrue(performed.get());
	}

	public void testPerformStartMatch() throws Exception
	{
		final SolarSystemPopulationManager mockSolarSystemPopulationManager = mockContext.mock(SolarSystemPopulationManager.class);
		MatchManagerImpl mockManager = new MatchManagerImpl(mockDao, galaxyManager, participantManager, mockSolarSystemPopulationManager,
				solarSystemInfrastructureManager, parameterManager);
		mockManager.setSecurityManager(((MatchManagerImpl) this.mockManager).getSecurityManager());

		int participants = 5;
		final Match match = new Match();
		match.setParticipants(new ArrayList<Participant>(participants));
		Participant p;
		for(int i = 0; i < participants; i++)
		{
			p = new Participant();
			p.setId((long) i + 1);
			p.setEmpire(new Empire());
			p.getEmpire().setId((long) i + 1);
			p.setActivated(i < participants - 1);
		}
		Collections.shuffle(match.getParticipants());
		match.setStartSystemCount(3);
		match.setSeed(123L);

		final List<SolarSystemPopulation> populations = new ArrayList<SolarSystemPopulation>(match.getStartSystemCount());
		for(int i = 0; i < match.getStartSystemCount(); i++)
			populations.add(new SolarSystemPopulation());

		final ExtendedRandom random = new ExtendedRandom(match.getSeed());

		// test without start date // domination
		int victoryParameter = 70;
		match.setVictoryParameter(victoryParameter);
		match.setVictoryCondition(EnumVictoryCondition.domination);
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
				exactly(match.getParticipants().size() - 1).of(mockSolarSystemPopulationManager).randomSelectStartSystems(
						with(any(Participant.class)), with(equal(random)));
				will(returnValue(populations));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly((match.getParticipants().size() - 1) * match.getStartSystemCount()).of(mockSolarSystemPopulationManager).save(
						with(any(SolarSystemPopulation.class)));
				will(returnValue(new SolarSystemPopulation()));
			}
		});
		Match result1 = ((MatchManagerImpl) mockManager).performStartMatch(match);
		assertSame(match, result1);
		mockContext.assertIsSatisfied();
		assertNotNull(match);
		assertNotNull(match.getStartDate());
		assertEquals(new Date(referenceTime), match.getStartDate());
		assertEquals(victoryParameter, match.getVictoryParameter());
		for(Participant p1 : match.getParticipants())
		{
			assertNull(p1.getRivals());
		}

		// test with start date // extermination
		match.setVictoryCondition(EnumVictoryCondition.extermination);
		Date aStartDate = new Date(4321);
		match.setStartDate(aStartDate);
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
				exactly(match.getParticipants().size() - 1).of(mockSolarSystemPopulationManager).randomSelectStartSystems(
						with(any(Participant.class)), with(equal(random)));
				will(returnValue(populations));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly((match.getParticipants().size() - 1) * match.getStartSystemCount()).of(mockSolarSystemPopulationManager).save(
						with(any(SolarSystemPopulation.class)));
				will(returnValue(new SolarSystemPopulation()));
			}
		});
		Match result2 = ((MatchManagerImpl) mockManager).performStartMatch(match);
		assertSame(match, result2);
		mockContext.assertIsSatisfied();
		assertNotNull(match);
		assertNotNull(match.getStartDate());
		assertEquals(aStartDate, match.getStartDate());
		assertEquals(100, match.getVictoryParameter());
		for(Participant p1 : match.getParticipants())
		{
			assertNull(p1.getRivals());
		}

		// test the third victory condition // vendetta
		match.setVictoryCondition(EnumVictoryCondition.vendetta);
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
				exactly(match.getParticipants().size() - 1).of(mockSolarSystemPopulationManager).randomSelectStartSystems(
						with(any(Participant.class)), with(equal(random)));
				will(returnValue(populations));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly((match.getParticipants().size() - 1) * match.getStartSystemCount()).of(mockSolarSystemPopulationManager).save(
						with(any(SolarSystemPopulation.class)));
				will(returnValue(new SolarSystemPopulation()));
			}
		});
		Match result3 = ((MatchManagerImpl) mockManager).performStartMatch(match);
		assertSame(match, result3);
		mockContext.assertIsSatisfied();
		assertNotNull(match);
		assertNotNull(match.getStartDate());
		assertEquals(aStartDate, match.getStartDate());
		assertEquals((int) parameterManager.getInteger(UniverseConquestConstants.PARAM_MATCH_VENDETTA_PARAM_DEFAULT), match.getVictoryParameter());
		int rivals = mockManager.getNumberOfRivals(match);
		for(Participant p1 : match.getParticipants())
		{
			assertNotNull(p1.getRivals());
			assertEquals(rivals, p1.getRivals());
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

		for(int i = 1; i < expectedRivals_40.length; i++)
		{
			match.getParticipants().add(new Participant());

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
			p.setRank(i + 1);
			p.setRankValue((participants - 1 - i) * 10);
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
			assertTrue(p.isRankFinal());
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
				p.setRankFinal(false);
				p.setActivated(true);
			}
		}
	}

	public void testIsVictoryConditionMet() throws Exception
	{
		int participants = 5;

		Match match = new Match();
		match.setVictoryParameter(participants * 10);
		match.setParticipants(new ArrayList<Participant>(participants));

		Participant p;
		for(int i = 0; i < participants; i++)
		{
			p = new Participant();
			p.setRank(i + 1);
			p.setRankValue((participants - 1 - i) * 10);
			match.getParticipants().add(p);
		}
		Collections.shuffle(match.getParticipants());

		assertFalse(mockManager.isVictoryConditionMet(match));

		match.setVictoryParameter((participants - 1) * 10);
		assertTrue(mockManager.isVictoryConditionMet(match));
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
		match.getParticipants().get(destroyedParticipant).setRank(5);
		match.getParticipants().get(destroyedParticipant).setRankFinal(true);

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
		checkRank(match.getParticipants(), 0, 1, (int) Math.floor(100.0 * pop0 / totalPop), false);
		checkRank(match.getParticipants(), 1, 3, (int) Math.floor(100.0 * pop1 / totalPop), false);
		checkRank(match.getParticipants(), 2, 4, (int) Math.floor(100.0 * pop2 / totalPop), true);
		checkRank(match.getParticipants(), 3, 5, (int) Math.floor(100.0 * pop3 / totalPop), true);
		checkRank(match.getParticipants(), 4, 2, (int) Math.floor(100.0 * pop4 / totalPop), false);

		resetRanks(match.getParticipants(), destroyedParticipant);

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
		checkRank(match.getParticipants(), 0, 1, (int) Math.floor(100.0 * 10 / systems), false);
		checkRank(match.getParticipants(), 1, 2, (int) Math.floor(100.0 * 6 / systems), false);
		checkRank(match.getParticipants(), 2, 4, (int) Math.floor(100.0 * 0 / systems), true);
		checkRank(match.getParticipants(), 3, 5, (int) Math.floor(100.0 * 0 / systems), true);
		checkRank(match.getParticipants(), 4, 3, (int) Math.floor(100.0 * 5 / systems), false);

		resetRanks(match.getParticipants(), destroyedParticipant);

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
		checkRank(match.getParticipants(), 0, 2, (int) Math.floor(100.0 * 1 / rivals), false);
		checkRank(match.getParticipants(), 1, 1, (int) Math.floor(100.0 * 2 / rivals), false);
		checkRank(match.getParticipants(), 2, 4, (int) Math.floor(100.0 * 1 / rivals), true);
		checkRank(match.getParticipants(), 3, 5, (int) Math.floor(100.0 * 0 / rivals), true);
		checkRank(match.getParticipants(), 4, 3, (int) Math.floor(100.0 * 0 / rivals), false);
	}

	private void checkRank(List<Participant> participants, int index, int rankExpected, int rankValueExpected, boolean rankFinalExpected)
	{
		assertEquals(rankExpected, participants.get(index).getRank());
		assertEquals(rankValueExpected, participants.get(index).getRankValue());
		assertEquals(rankFinalExpected, participants.get(index).isRankFinal());
	}

	private void resetRanks(List<Participant> participants, Integer... ignoreIndexes)
	{
		List<Integer> ignore = Arrays.asList(ignoreIndexes);
		Participant p;
		for(int i = 0; i < participants.size(); i++)
		{
			if(ignore.contains(i))
				continue;
			p = participants.get(i);
			p.setRankFinal(false);
			p.setRankValue(0);
			p.setRank(0);
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
			p.getPopulations().add(population);
		}

		if(totalPop == 0)
			p.setDestructionDate(new Date(referenceTime));

		return p;
	}
}
