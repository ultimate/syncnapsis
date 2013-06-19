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
import java.util.Date;

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.dao.MatchDao;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.data.service.GalaxyManager;
import com.syncnapsis.data.service.MatchManager;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.data.service.ParticipantManager;
import com.syncnapsis.data.service.SolarSystemInfrastructureManager;
import com.syncnapsis.data.service.SolarSystemPopulationManager;
import com.syncnapsis.enums.EnumStartCondition;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestCoversClasses({ MatchManager.class, MatchManagerImpl.class })
@TestExcludesMethods({"isAccessible"})
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
		
		MatchManagerImpl matchManager = new MatchManagerImpl(mockDao, galaxyManager, participantManager, solarSystemPopulationManager, solarSystemInfrastructureManager, parameterManager) {
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
		
		MatchManagerImpl matchManager = new MatchManagerImpl(mockDao, galaxyManager, participantManager, solarSystemPopulationManager, solarSystemInfrastructureManager, parameterManager) {
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
		fail("unimplemented");
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
		fail("unimplemented");
	}
	
	public void testUpdateRanking() throws Exception
	{
		fail("unimplemented");
	}
}
