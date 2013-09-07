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
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.api.Invocation;
import org.jmock.lib.action.CustomAction;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.dao.ParticipantDao;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.EmpireManager;
import com.syncnapsis.data.service.ParticipantManager;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.enums.EnumJoinType;
import com.syncnapsis.enums.EnumMatchState;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.mock.util.ReturnArgAction;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.universe.Calculator;
import com.syncnapsis.utils.data.ExtendedRandom;

@TestCoversClasses({ ParticipantManager.class, ParticipantManagerImpl.class })
@TestExcludesMethods({ "isAccessible", "*etSecurityManager", "afterPropertiesSet", "*etCalculator" })
public class ParticipantManagerImplTest extends GenericManagerImplTestCase<Participant, Long, ParticipantManager, ParticipantDao>
{
	private BaseGameManager						securityManager;
	private EmpireManager						empireManager;
	private SolarSystemPopulationManager		solarSystemPopulationManager;
	private SolarSystemInfrastructureManager	solarSystemInfrastructureManager;
	private Calculator							calculator;

	private final long							referenceTime	= 1234;

	private final ExtendedRandom				random			= new ExtendedRandom(referenceTime);

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Participant());
		setDaoClass(ParticipantDao.class);
		setMockDao(mockContext.mock(ParticipantDao.class));
		setMockManager(new ParticipantManagerImpl(mockDao, empireManager, solarSystemPopulationManager, solarSystemInfrastructureManager));

		BaseGameManager securityManager = new BaseGameManager(this.securityManager);
		securityManager.setTimeProvider(new MockTimeProvider(referenceTime));
		((ParticipantManagerImpl) mockManager).setSecurityManager(securityManager);
	}

	public void testGetByMatch() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByMatch", new ArrayList<Participant>(), 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetByMatchAndEmpire() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByMatchAndEmpire", new Participant(), 1L, 2L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testJoinMatch() throws Exception
	{
		// Behaviour of addParticipant is not checked here. Test will only cover preconditions
		// (security check etc) within joinMatch and check forwarding to addParticipant.

		ParticipantManagerMockImpl mockManager = new ParticipantManagerMockImpl(mockDao, empireManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager);
		mockManager.setSecurityManager(securityManager);
		securityManager.getSessionProvider().set(new MockHttpSession());

		final Match match = new Match();
		match.setId(-1L);

		// no current empire
		try
		{
			mockManager.joinMatch(match);
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}

		final Empire empire = new Empire();
		empire.setId(-2L);
		securityManager.getEmpireProvider().set(empire);

		// match only allows invitations (planned)
		match.setPlannedJoinType(EnumJoinType.invitationsOnly);
		match.setState(EnumMatchState.planned);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(null));
			}
		});
		mockManager.joinMatch(match);
		mockContext.assertIsSatisfied();
		assertEquals(0, mockManager.addParticipantCalled);
		assertEquals(0, mockManager.removeParticipantCalled);

		// match not joinable (planned)
		match.setPlannedJoinType(EnumJoinType.none);
		match.setState(EnumMatchState.planned);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(null));
			}
		});
		mockManager.joinMatch(match);
		mockContext.assertIsSatisfied();
		assertEquals(0, mockManager.addParticipantCalled);
		assertEquals(0, mockManager.removeParticipantCalled);

		// match joinable (planned)
		match.setPlannedJoinType(EnumJoinType.joiningEnabled);
		match.setState(EnumMatchState.planned);
		mockManager.joinMatch(match);
		assertEquals(1, mockManager.addParticipantCalled);
		assertEquals(0, mockManager.removeParticipantCalled);

		mockManager.addParticipantCalled = 0;

		// match only allows invitations (started)
		match.setStartedJoinType(EnumJoinType.invitationsOnly);
		match.setState(EnumMatchState.active);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(null));
			}
		});
		mockManager.joinMatch(match);
		mockContext.assertIsSatisfied();
		assertEquals(0, mockManager.addParticipantCalled);
		assertEquals(0, mockManager.removeParticipantCalled);

		// match not joinable (started)
		match.setStartedJoinType(EnumJoinType.none);
		match.setState(EnumMatchState.active);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(null));
			}
		});
		mockManager.joinMatch(match);
		mockContext.assertIsSatisfied();
		assertEquals(0, mockManager.addParticipantCalled);
		assertEquals(0, mockManager.removeParticipantCalled);

		// match joinable (started)
		match.setStartedJoinType(EnumJoinType.joiningEnabled);
		match.setState(EnumMatchState.active);
		mockManager.joinMatch(match);
		assertEquals(1, mockManager.addParticipantCalled);
		assertEquals(0, mockManager.removeParticipantCalled);
	}

	public void testleaveMatch() throws Exception
	{
		// Behaviour of removeParticipant is not checked here. Test will only cover preconditions
		// (security check etc) within leaveMatch and check forwarding to removeParticipant.

		ParticipantManagerMockImpl mockManager = new ParticipantManagerMockImpl(mockDao, empireManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager);
		mockManager.setSecurityManager(securityManager);
		securityManager.getSessionProvider().set(new MockHttpSession());

		final Match match = new Match();
		match.setId(-1L);

		// no current empire
		try
		{
			mockManager.leaveMatch(match);
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}

		final Empire empire = new Empire();
		empire.setId(-2L);
		securityManager.getEmpireProvider().set(empire);

		// match leavable (started or planned)
		mockManager.leaveMatch(match);
		assertEquals(0, mockManager.addParticipantCalled);
		assertEquals(1, mockManager.removeParticipantCalled);
	}

	public void testAddParticipant() throws Exception
	{
		// Behaviour of addParticipant (protected) is not checked here. Test will only cover
		// preconditions (security check etc) within addParticipant (public) and check forwarding to
		// addParticipant.
		final EmpireManager mockEmpireManager = mockContext.mock(EmpireManager.class);

		ParticipantManagerMockImpl mockManager = new ParticipantManagerMockImpl(mockDao, mockEmpireManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager);
		mockManager.setSecurityManager(securityManager);
		securityManager.getSessionProvider().set(new MockHttpSession());

		Player creator = new Player();
		creator.setId(-1L);
		creator.setUser(new User());
		creator.getUser().setRole(new UserRole());
		creator.getUser().getRole().setRolename(ApplicationBaseConstants.ROLE_MODERATOR);
		securityManager.getPlayerProvider().set(creator);

		final Match match = new Match();
		match.setId(-1L);
		match.setCreator(creator);

		final long empireId = -2L;

		// match does not allow joins or invitations (planned)
		match.setPlannedJoinType(EnumJoinType.none);
		match.setState(EnumMatchState.planned);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empireId);
				will(returnValue(null));
			}
		});
		mockManager.addParticipant(match, empireId);
		mockContext.assertIsSatisfied();
		assertEquals(0, mockManager.addParticipantCalled);
		assertEquals(0, mockManager.removeParticipantCalled);

		// match allows joins and invitations (planned)
		match.setPlannedJoinType(EnumJoinType.joiningEnabled);
		match.setState(EnumMatchState.planned);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockEmpireManager).get(empireId);
				will(returnValue(new Empire()));
			}
		});
		mockManager.addParticipant(match, empireId);
		mockContext.assertIsSatisfied();
		assertEquals(1, mockManager.addParticipantCalled);
		assertEquals(0, mockManager.removeParticipantCalled);

		// match allows invitations (planned)
		match.setPlannedJoinType(EnumJoinType.invitationsOnly);
		match.setState(EnumMatchState.planned);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockEmpireManager).get(empireId);
				will(returnValue(new Empire()));
			}
		});
		mockManager.addParticipant(match, empireId);
		assertEquals(2, mockManager.addParticipantCalled);
		assertEquals(0, mockManager.removeParticipantCalled);

		mockManager.addParticipantCalled = 0;

		// match does not allow joins or invitations (active)
		match.setStartedJoinType(EnumJoinType.none);
		match.setState(EnumMatchState.active);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empireId);
				will(returnValue(null));
			}
		});
		mockManager.addParticipant(match, empireId);
		mockContext.assertIsSatisfied();
		assertEquals(0, mockManager.addParticipantCalled);
		assertEquals(0, mockManager.removeParticipantCalled);

		// match allows joins and invitations (active)
		match.setStartedJoinType(EnumJoinType.joiningEnabled);
		match.setState(EnumMatchState.active);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockEmpireManager).get(empireId);
				will(returnValue(new Empire()));
			}
		});
		mockManager.addParticipant(match, empireId);
		mockContext.assertIsSatisfied();
		assertEquals(1, mockManager.addParticipantCalled);
		assertEquals(0, mockManager.removeParticipantCalled);

		// match allows invitations (active)
		match.setStartedJoinType(EnumJoinType.invitationsOnly);
		match.setState(EnumMatchState.active);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockEmpireManager).get(empireId);
				will(returnValue(new Empire()));
			}
		});
		mockManager.addParticipant(match, empireId);
		assertEquals(2, mockManager.addParticipantCalled);
		assertEquals(0, mockManager.removeParticipantCalled);
	}

	public void testRemoveParticipant() throws Exception
	{
		// Behaviour of removeParticipant (protected) is not checked here. Test will only cover
		// preconditions (security check etc) within removeParticipant (public) and check forwarding
		// to removeParticipant.

		final EmpireManager mockEmpireManager = mockContext.mock(EmpireManager.class);

		ParticipantManagerMockImpl mockManager = new ParticipantManagerMockImpl(mockDao, mockEmpireManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager);
		mockManager.setSecurityManager(securityManager);
		securityManager.getSessionProvider().set(new MockHttpSession());

		Player creator = new Player();
		creator.setId(-1L);
		securityManager.getPlayerProvider().set(creator);

		final Match match = new Match();
		match.setId(-1L);
		match.setCreator(creator);
		match.setState(EnumMatchState.planned);

		final long empireId = -2L;

		// match leavable (started or planned)
		mockContext.checking(new Expectations() {
			{
				oneOf(mockEmpireManager).get(empireId);
				will(returnValue(new Empire()));
			}
		});
		mockManager.removeParticipant(match, empireId);
		assertEquals(0, mockManager.addParticipantCalled);
		assertEquals(1, mockManager.removeParticipantCalled);
	}

	/*
	 * Test for the internal/protected method
	 */
	public void testAddParticipant_protected() throws Exception
	{
		final ParticipantManagerImpl m = (ParticipantManagerImpl) mockManager;

		final Match match = new Match();
		match.setId(-1L);
		match.setParticipants(new LinkedList<Participant>());
		match.getParticipants().add(new Participant());
		match.getParticipants().add(new Participant());
		match.getParticipants().add(new Participant());

		final Empire empire = new Empire();
		empire.setId(-2L);

		Participant result;

		// can't join if match is canceled or finished
		match.setState(EnumMatchState.canceled);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(null));
			}
		});
		result = m.addParticipant(match, empire);
		mockContext.assertIsSatisfied();
		assertNull(result); // null will only be preserved if match is canceled or finished or full
		// finished
		match.setState(EnumMatchState.finished);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(null));
			}
		});
		result = m.addParticipant(match, empire);
		mockContext.assertIsSatisfied();
		assertNull(result); // null will only be preserved if match is canceled or finished or full
		// full
		match.setState(EnumMatchState.planned);
		match.setParticipantsMax(3);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(null));
			}
		});
		result = m.addParticipant(match, empire);
		mockContext.assertIsSatisfied();
		assertNull(result); // null will only be preserved if match is canceled or finished or full

		final Participant existing = new Participant();
		existing.setActivated(true);
		existing.setDestructionDate(null);
		existing.setDestructionType(null);
		existing.setEmpire(empire);
		existing.setJoinedDate(new Date(-referenceTime));
		existing.setMatch(match);
		existing.setRank(match.getParticipants().size());
		existing.setRankFinal(false);
		existing.setRankValue(0);
		existing.setStartSystemsSelected(0);

		final Participant expected = new Participant();
		expected.setActivated(true);
		expected.setDestructionDate(null);
		expected.setDestructionType(null);
		expected.setEmpire(empire);
		expected.setJoinedDate(new Date(referenceTime));
		expected.setMatch(match);
		expected.setRank(match.getParticipants().size());
		expected.setRankFinal(false);
		expected.setRankValue(0);
		expected.setStartSystemsSelected(0);

		// not full (unlimited)
		match.setParticipantsMax(0);

		// not yet participanting
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(null));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(expected);
				will(returnValue(expected));
			}
		});
		result = m.addParticipant(match, empire);
		mockContext.assertIsSatisfied();
		assertNotNull(result);
		assertEquals(expected, result);

		// already participanting
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(existing));
			}
		});
		result = m.addParticipant(match, empire);
		mockContext.assertIsSatisfied();
		assertNotNull(result);
		assertEquals(existing, result);
	}

	/*
	 * Test for the internal/protected method
	 */
	public void testRemoveParticipant_protected() throws Exception
	{
		// Behaviour of destroy is not checked here. Test will only cover
		// preconditions (security check etc) within removeParticipant (protected) and check
		// forwarding to destroy.

		final EmpireManager mockEmpireManager = mockContext.mock(EmpireManager.class);

		ParticipantManagerMockImpl2 mockManager = new ParticipantManagerMockImpl2(mockDao, mockEmpireManager, solarSystemPopulationManager,
				solarSystemInfrastructureManager);
		BaseGameManager securityManager = new BaseGameManager(this.securityManager);
		securityManager.setTimeProvider(new MockTimeProvider(referenceTime));
		mockManager.setSecurityManager(securityManager);
		securityManager.getSessionProvider().set(new MockHttpSession());

		final Player player = new Player();
		player.setId(-5L);

		final Player creator = new Player();
		creator.setId(-1L);
		securityManager.getPlayerProvider().set(creator);

		final Match match = new Match();
		match.setId(-1L);
		match.setCreator(creator);
		match.setState(EnumMatchState.planned);

		final Empire empire = new Empire();
		empire.setId(-2L);
		empire.setPlayer(player);

		final Participant participant = new Participant();
		participant.setId(-3L);
		participant.setActivated(true);
		participant.setDestructionDate(null);
		participant.setDestructionType(null);
		participant.setEmpire(empire);
		participant.setJoinedDate(new Date(-referenceTime));
		participant.setMatch(match);
		participant.setRankFinal(false);
		participant.setRankValue(0);
		participant.setStartSystemsSelected(0);

		// not yet participanting
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(null));
			}
		});
		assertFalse(mockManager.removeParticipant(match, empire));
		mockContext.assertIsSatisfied();
		assertEquals(0, mockManager.destroyCalled);

		// match already canceled
		match.setState(EnumMatchState.canceled);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(participant));
			}
		});
		assertFalse(mockManager.removeParticipant(match, empire));
		mockContext.assertIsSatisfied();
		assertEquals(0, mockManager.destroyCalled);

		// participating (planned)
		match.setState(EnumMatchState.planned);
		securityManager.getPlayerProvider().set(creator);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(participant));
			}
		});
		assertTrue(mockManager.removeParticipant(match, empire));
		mockContext.assertIsSatisfied();
		assertFalse(participant.isActivated());
		assertEquals(1, mockManager.destroyCalled);
		assertEquals(EnumDestructionType.removed, mockManager.destructionType);
		assertEquals(new Date(referenceTime), mockManager.destructionDate);

		// participating (planned) - self remove
		securityManager.getPlayerProvider().set(player);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(participant));
			}
		});
		assertTrue(mockManager.removeParticipant(match, empire));
		mockContext.assertIsSatisfied();
		assertFalse(participant.isActivated());
		assertEquals(2, mockManager.destroyCalled);
		assertEquals(EnumDestructionType.left, mockManager.destructionType);
		assertEquals(new Date(referenceTime), mockManager.destructionDate);

		// participating (active)
		match.setState(EnumMatchState.active);
		securityManager.getPlayerProvider().set(creator);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(participant));
			}
		});
		assertTrue(mockManager.removeParticipant(match, empire));
		mockContext.assertIsSatisfied();
		assertFalse(participant.isActivated());
		assertEquals(3, mockManager.destroyCalled);
		assertEquals(EnumDestructionType.removed, mockManager.destructionType);
		assertEquals(new Date(referenceTime), mockManager.destructionDate);

		// participating (active) - self remove
		securityManager.getPlayerProvider().set(player);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByMatchAndEmpire(match.getId(), empire.getId());
				will(returnValue(participant));
			}
		});
		assertTrue(mockManager.removeParticipant(match, empire));
		mockContext.assertIsSatisfied();
		assertFalse(participant.isActivated());
		assertEquals(4, mockManager.destroyCalled);
		assertEquals(EnumDestructionType.givenUp, mockManager.destructionType);
		assertEquals(new Date(referenceTime), mockManager.destructionDate);
	}

	public void testDestroy() throws Exception
	{
		final SolarSystemPopulationManager mockSolarSystemPopulationManager = mockContext.mock(SolarSystemPopulationManager.class);

		ParticipantManagerMockImpl mockManager = new ParticipantManagerMockImpl(mockDao, empireManager, mockSolarSystemPopulationManager,
				solarSystemInfrastructureManager);
		mockManager.setSecurityManager(securityManager);
		securityManager.getSessionProvider().set(new MockHttpSession());

		Player creator = new Player();
		creator.setId(-1L);
		securityManager.getPlayerProvider().set(creator);

		final Match match = new Match();
		match.setId(-1L);
		match.setCreator(creator);
		match.setState(EnumMatchState.planned);

		final Empire empire = new Empire();
		empire.setId(-2L);

		final Participant participant = new Participant();
		participant.setId(-3L);
		participant.setActivated(true);
		participant.setDestructionDate(null);
		participant.setDestructionType(null);
		participant.setEmpire(empire);
		participant.setJoinedDate(new Date(-referenceTime));
		participant.setMatch(match);
		participant.setRankFinal(false);
		participant.setRankValue(0);
		participant.setStartSystemsSelected(0);
		participant.setPopulations(new LinkedList<SolarSystemPopulation>());
		SolarSystemPopulation population;
		for(int i = 0; i < 5; i++)
		{
			population = new SolarSystemPopulation();
			population.setDestructionDate(null);
			population.setDestructionType(null);
			population.setActivated(i < 4);
			participant.getPopulations().add(population);
		}

		final EnumDestructionType destructionType = EnumDestructionType.givenUp;
		final Date destructionDate = new Date(-referenceTime);

		mockContext.checking(new Expectations() {
			{
				exactly(participant.getPopulations().size() - 1).of(mockSolarSystemPopulationManager).destroy(with(any(SolarSystemPopulation.class)),
						with(same(destructionType)), with(same(destructionDate)));
				will(returnValue(new SolarSystemPopulation()));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(participant);
				will(returnValue(participant));
			}
		});

		Participant result = mockManager.destroy(participant, destructionType, destructionDate);
		assertEquals(participant, result);
	}

	public void testStartParticipating() throws Exception
	{
		final SolarSystemPopulationManager mockSolarSystemPopulationManager = mockContext.mock(SolarSystemPopulationManager.class);
		ParticipantManagerImpl mockManager = new ParticipantManagerImpl(mockDao, empireManager, mockSolarSystemPopulationManager,
				solarSystemInfrastructureManager);

		final int populations = 5;
		final long startPopulation = populations * 100000;
		Participant participant = new Participant();
		participant.setMatch(new Match());
		participant.getMatch().setStartSystemCount(populations);
		participant.getMatch().setStartPopulation(startPopulation);
		participant.setPopulations(new ArrayList<SolarSystemPopulation>(populations));
		for(int i = 0; i < populations; i++)
		{
			participant.getPopulations().add(new SolarSystemPopulation());
			participant.getPopulations().get(i).setPopulation(startPopulation / populations);
		}

		Date participationDate = new Date(1234);

		final SolarSystemPopulation expected = new SolarSystemPopulation();
		expected.setColonizationDate(participationDate);
		expected.setPopulation(startPopulation / populations);
		expected.setLastUpdateDate(participationDate);

		mockContext.checking(new Expectations() {
			{
				exactly(populations).of(mockSolarSystemPopulationManager).save(expected);
				will(returnValue(expected));
			}
		});

		mockManager.startParticipating(participant, participationDate);
		mockContext.assertIsSatisfied();

		// not all population assigned
		participant.getPopulations().get(0).setPopulation(startPopulation / populations / 2);
		try
		{
			mockManager.startParticipating(participant, participationDate);
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}

		// not all systems selected
		participant.getPopulations().remove(0);
		try
		{
			mockManager.startParticipating(participant, participationDate);
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
	}

	public void testSelectStartSystem() throws Exception
	{
		final SolarSystemPopulationManager mockSolarSystemPopulationManager = mockContext.mock(SolarSystemPopulationManager.class);
		ParticipantManagerImpl mockManager = new ParticipantManagerImpl(mockDao, empireManager, mockSolarSystemPopulationManager,
				solarSystemInfrastructureManager);
		mockManager.setSecurityManager(securityManager);
		
		securityManager.getSessionProvider().set(new MockHttpSession());

		// prepare empires/participants
		int count = 5;
		List<Participant> participants = new ArrayList<Participant>(count);
		for(int i = 0; i < count; i++)
		{
			Empire empire = new Empire();
			empire.setId(111L * count);

			Participant participant = new Participant();
			participant.setEmpire(empire);

			participants.add(participant);
		}
		final Participant participant = participants.get(0);
		Collections.shuffle(participants);

		securityManager.getEmpireProvider().set(participant.getEmpire());

		// prepare match
		Match match = new Match();
		match.setParticipants(participants);

		final SolarSystemInfrastructure infrastructure = new SolarSystemInfrastructure();
		infrastructure.setMatch(match);

		final int population = 100000;

		mockContext.checking(new Expectations() {
			{
				oneOf(mockSolarSystemPopulationManager).selectStartSystem(participant, infrastructure, population);
				will(returnValue(new SolarSystemPopulation()));
			}
		});

		SolarSystemPopulation result = mockManager.selectStartSystem(infrastructure, population);
		mockContext.assertIsSatisfied();
		assertNotNull(result);

		Empire other = new Empire();
		other.setId(count * 2 * 111L);
		securityManager.getEmpireProvider().set(other);
		try
		{
			mockManager.selectStartSystem(infrastructure, population);
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
	}

	public void testRandomSelectStartSystems() throws Exception
	{
		final EmpireManager mockEmpireManager = mockContext.mock(EmpireManager.class);
		final SolarSystemPopulationManager mockSolarSystemPopulationManager = mockContext.mock(SolarSystemPopulationManager.class);
		final SolarSystemInfrastructureManager mockSolarSystemInfrastructureManager = mockContext.mock(SolarSystemInfrastructureManager.class);
		final ParticipantManagerImpl mockManager = new ParticipantManagerImpl(mockDao, mockEmpireManager, mockSolarSystemPopulationManager,
				mockSolarSystemInfrastructureManager);
		mockManager.setCalculator(calculator);

		// initialize infrastructures
		final int infs = 100;
		final List<SolarSystemInfrastructure> infrastructures = new ArrayList<SolarSystemInfrastructure>(infs);
		for(int i = 0; i < infs; i++)
		{
			infrastructures.add(getRandomInfrastructure());
		}

		final long matchId = 123;
		final long participantId = 456;
		final int startSystemCount = 5;
		final long startPopulation = 10000000;

		final Match match = new Match();
		match.setId(matchId);
		match.setStartSystemCount(startSystemCount);
		match.setStartPopulation(startPopulation);

		final Participant participant = new Participant();
		participant.setId(participantId);
		participant.setMatch(match);
		participant.setPopulations(new ArrayList<SolarSystemPopulation>(startSystemCount*2));

		List<SolarSystemPopulation> result;
		long populationAssigned;

		// no systems selected yet
		participant.setStartSystemsSelected(0);
		// participant.getPopulations().clear();
		mockContext.checking(new Expectations() {
			{
				oneOf(mockSolarSystemInfrastructureManager).getByMatch(matchId);
				will(returnValue(infrastructures));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(startSystemCount).of(mockSolarSystemPopulationManager).selectStartSystem(with(same(participant)),
						with(any(SolarSystemInfrastructure.class)), with(any(long.class)));
				will(new CustomAction("return startsystem") {
					@Override
					public Object invoke(Invocation invocation) throws Throwable
					{
						Participant participant = (Participant) invocation.getParameter(0);
						SolarSystemInfrastructure infrastructure = (SolarSystemInfrastructure) invocation.getParameter(1);
						long population = (Long) invocation.getParameter(2);
						SolarSystemPopulation pop = new SolarSystemPopulation();
						pop.setParticipant(participant);
						pop.setInfrastructure(infrastructure);
						pop.setPopulation(population);
						return pop;
					}
				});
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(startSystemCount).of(mockSolarSystemPopulationManager).save(with(any(SolarSystemPopulation.class)));
				will(new ReturnArgAction());
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(participant);
				will(returnValue(participant));
			}
		});

		result = mockManager.randomSelectStartSystems(participant, random);
		mockContext.assertIsSatisfied();

		assertNotNull(result);
		assertEquals(startSystemCount, result.size());
		assertEquals(startSystemCount, participant.getStartSystemsSelected());
		populationAssigned = 0;
		for(SolarSystemPopulation pop: result)
		{
			populationAssigned += pop.getPopulation();
		}
		assertEquals(startPopulation, populationAssigned);
		
		// check with preselected populations
		final int systemSelected = 3;
		participant.setStartSystemsSelected(systemSelected);
		SolarSystemPopulation population;
		for(int i = 0; i < systemSelected; i++)
		{
			population = new SolarSystemPopulation();
			population.setParticipant(participant);
			population.setInfrastructure(random.nextEntry(infrastructures));
			population.setPopulation((i+1)*1000000);
			
			participant.getPopulations().add(population);
		}
		
		mockContext.checking(new Expectations() {
			{
				oneOf(mockSolarSystemInfrastructureManager).getByMatch(matchId);
				will(returnValue(infrastructures));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(startSystemCount-systemSelected).of(mockSolarSystemPopulationManager).selectStartSystem(with(same(participant)),
						with(any(SolarSystemInfrastructure.class)), with(any(long.class)));
				will(new CustomAction("return startsystem") {
					@Override
					public Object invoke(Invocation invocation) throws Throwable
					{
						Participant participant = (Participant) invocation.getParameter(0);
						SolarSystemInfrastructure infrastructure = (SolarSystemInfrastructure) invocation.getParameter(1);
						long population = (Long) invocation.getParameter(2);
						SolarSystemPopulation pop = new SolarSystemPopulation();
						pop.setParticipant(participant);
						pop.setInfrastructure(infrastructure);
						pop.setPopulation(population);
						return pop;
					}
				});
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(startSystemCount).of(mockSolarSystemPopulationManager).save(with(any(SolarSystemPopulation.class)));
				will(new ReturnArgAction());
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(participant);
				will(returnValue(participant));
			}
		});

		result = mockManager.randomSelectStartSystems(participant, random);
		mockContext.assertIsSatisfied();

		assertNotNull(result);
		assertEquals(startSystemCount, result.size());
		assertEquals(startSystemCount, participant.getStartSystemsSelected());
		populationAssigned = 0;
		for(SolarSystemPopulation pop: result)
		{
			populationAssigned += pop.getPopulation();
		}
		assertEquals(startPopulation, populationAssigned);
	}

	public void testGetCenter() throws Exception
	{
		ParticipantManagerImpl m = new ParticipantManagerImpl(mockDao, empireManager, solarSystemPopulationManager, solarSystemInfrastructureManager);

		List<SolarSystemPopulation> populations = new ArrayList<SolarSystemPopulation>(10);

		populations.add(getPopulation(100, 100, 100));
		assertEquals(new Vector.Integer(100, 100, 100), m.getCenter(populations));

		populations.add(getPopulation(100, 100, -100));
		assertEquals(new Vector.Integer(100, 100, 0), m.getCenter(populations));

		populations.add(getPopulation(100, -100, 100));
		populations.add(getPopulation(100, -100, -100));
		assertEquals(new Vector.Integer(100, 0, 0), m.getCenter(populations));

		populations.add(getPopulation(-100, 100, 100));
		populations.add(getPopulation(-100, 100, -100));
		populations.add(getPopulation(-100, -100, 100));
		populations.add(getPopulation(-100, -100, -100));
		assertEquals(new Vector.Integer(0, 0, 0), m.getCenter(populations));
	}

	public void testSortByDistance() throws Exception
	{
		ParticipantManagerImpl m = new ParticipantManagerImpl(mockDao, empireManager, solarSystemPopulationManager, solarSystemInfrastructureManager);

		List<SolarSystemInfrastructure> infrastructures = new ArrayList<SolarSystemInfrastructure>(10);

		int center = 1234;
		int count = 0;
		infrastructures.add(getInfrastructure(center, center, center + count++));
		infrastructures.add(getInfrastructure(center, center + count++, center));
		infrastructures.add(getInfrastructure(center + count++, center, center));
		infrastructures.add(getInfrastructure(center, center, center + count++));
		infrastructures.add(getInfrastructure(center, center + count++, center));
		infrastructures.add(getInfrastructure(center + count++, center, center));
		infrastructures.add(getInfrastructure(center, center, center + count++));
		infrastructures.add(getInfrastructure(center, center + count++, center));
		infrastructures.add(getInfrastructure(center + count++, center, center));
		infrastructures.add(getInfrastructure(center, center, center + count++));
		infrastructures.add(getInfrastructure(center, center + count++, center));
		infrastructures.add(getInfrastructure(center + count++, center, center));

		List<SolarSystemInfrastructure> unshuffledCopy = new ArrayList<SolarSystemInfrastructure>(infrastructures);
		Collections.shuffle(infrastructures);

		Vector.Integer ref = new Vector.Integer(center, center, center);

		m.sortByDistance(infrastructures, ref);

		assertEquals(unshuffledCopy, infrastructures);
	}

	private SolarSystemPopulation getPopulation(int x, int y, int z)
	{
		SolarSystemPopulation pop = new SolarSystemPopulation();
		pop.setInfrastructure(getInfrastructure(x, y, z));
		return pop;
	}

	private SolarSystemInfrastructure getInfrastructure(int x, int y, int z)
	{
		SolarSystemInfrastructure inf = new SolarSystemInfrastructure();
		inf.setId((long) x * y * z);
		inf.setSolarSystem(new SolarSystem());
		inf.getSolarSystem().setId((long) x * y * z);
		inf.getSolarSystem().setCoords(new Vector.Integer(x, y, z));
		return inf;
	}

	private SolarSystemInfrastructure getRandomInfrastructure()
	{
		int x = random.nextGaussian(-1000, 1000);
		int y = random.nextGaussian(-1000, 1000);
		int z = random.nextGaussian(-1000, 1000);
		int s = random.nextGaussian(0, 1000);
		int h = random.nextGaussian(0, 1000);
		int i = Math.abs(random.nextGaussian(-s * h * 1000, s * h * 1000));

		SolarSystemInfrastructure inf = getInfrastructure(x, y, z);
		inf.setHabitability(h);
		inf.setInfrastructure(i);
		inf.setSize(s);

		return inf;
	}

	private class ParticipantManagerMockImpl extends ParticipantManagerImpl
	{
		private int	addParticipantCalled	= 0;
		private int	removeParticipantCalled	= 0;

		public ParticipantManagerMockImpl(ParticipantDao participantDao, EmpireManager empireManager,
				SolarSystemPopulationManager solarSystemPopulationManager, SolarSystemInfrastructureManager solarSystemInfrastructureManager)
		{
			super(participantDao, empireManager, solarSystemPopulationManager, solarSystemInfrastructureManager);
		}

		@Override
		protected Participant addParticipant(Match match, Empire empire)
		{
			addParticipantCalled++;
			return new Participant();
		}

		@Override
		protected boolean removeParticipant(Match match, Empire empire)
		{
			removeParticipantCalled++;
			return true;
		}
	}

	private class ParticipantManagerMockImpl2 extends ParticipantManagerImpl
	{
		private int					destroyCalled	= 0;
		private EnumDestructionType	destructionType;
		private Date				destructionDate;

		public ParticipantManagerMockImpl2(ParticipantDao participantDao, EmpireManager empireManager,
				SolarSystemPopulationManager solarSystemPopulationManager, SolarSystemInfrastructureManager solarSystemInfrastructureManager)
		{
			super(participantDao, empireManager, solarSystemPopulationManager, solarSystemInfrastructureManager);
		}

		@Override
		public Participant destroy(Participant participant, EnumDestructionType destructionType, Date destructionDate)
		{
			destroyCalled++;
			this.destructionType = destructionType;
			this.destructionDate = destructionDate;
			return null;
		}
	}
}
