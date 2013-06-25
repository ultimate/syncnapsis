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
import java.util.List;

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.dao.MatchDao;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.data.service.MatchManager;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.data.service.ParticipantManager;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumStartCondition;
import com.syncnapsis.enums.EnumVictoryCondition;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestCoversClasses({ MatchManager.class, MatchManagerImpl.class })
@TestExcludesMethods({ "isAccessible" })
public class MatchManagerImplTest extends GenericNameManagerImplTestCase<Match, Long, MatchManager, MatchDao>
{
	private GalaxyManager						galaxyManager;
	private ParticipantManager					participantManager;
	private SolarSystemPopulationManager		solarSystemPopulationManager;
	private SolarSystemInfrastructureManager	solarSystemInfrastructureManager;
	private ParameterManager					parameterManager;

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
		fail("unimplemented");
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
		fail("unimplemented");
	}

	public void testAssignRivals() throws Exception
	{
		fail("unimplemented");
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
		fail("unimplemented");
	}

	public void testFinishMatch() throws Exception
	{
		fail("unimplemented");
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
