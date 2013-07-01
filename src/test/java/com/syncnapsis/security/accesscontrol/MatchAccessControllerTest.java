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
package com.syncnapsis.security.accesscontrol;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.enums.EnumMatchState;
import com.syncnapsis.tests.LoggerTestCase;

/**
 * @author ultimate
 */
public class MatchAccessControllerTest extends LoggerTestCase
{
	public void testGetTargetClass() throws Exception
	{
		assertEquals(Match.class, new MatchAccessController().getTargetClass());
	}

	public void testIsModerator() throws Exception
	{
		MatchAccessController controller = new MatchAccessController();

		assertTrue(controller.isModerator(getPlayer(ApplicationBaseConstants.ROLE_MODERATOR)));
		assertFalse(controller.isModerator(getPlayer(ApplicationBaseConstants.ROLE_ADMIN)));
		assertFalse(controller.isModerator(getPlayer(ApplicationBaseConstants.ROLE_DEMO_USER)));
		assertFalse(controller.isModerator(getPlayer(ApplicationBaseConstants.ROLE_NORMAL_USER)));
	}

	public void testIsAdmin() throws Exception
	{
		MatchAccessController controller = new MatchAccessController();
		
		assertTrue(controller.isAdmin(getPlayer(ApplicationBaseConstants.ROLE_ADMIN)));
		assertFalse(controller.isAdmin(getPlayer(ApplicationBaseConstants.ROLE_MODERATOR)));
		assertFalse(controller.isAdmin(getPlayer(ApplicationBaseConstants.ROLE_DEMO_USER)));
		assertFalse(controller.isAdmin(getPlayer(ApplicationBaseConstants.ROLE_NORMAL_USER)));
	}

	public void testIsAccessible() throws Exception
	{
		MatchAccessController controller = new MatchAccessController();

		Player creator = getPlayer(ApplicationBaseConstants.ROLE_NORMAL_USER);
		Player other = getPlayer(ApplicationBaseConstants.ROLE_NORMAL_USER);
		Player admin = getPlayer(ApplicationBaseConstants.ROLE_ADMIN);
		Player moderator = getPlayer(ApplicationBaseConstants.ROLE_MODERATOR);
		
		Match match = new Match();
		match.setCreator(creator);
		
		// OPERATION_ADD_PARTICIPANT
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT, creator));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT, admin));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT, moderator));
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT, other));
		
		// OPERATION_CANCEL
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, creator));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, admin));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, moderator));
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, other));
		match.setState(EnumMatchState.active);
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, creator));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, admin));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, moderator));
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, other));
		
		// OPERATION_CREATE
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CREATE, creator));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CREATE, admin));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CREATE, moderator));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CREATE, other));
		
		// OPERATION_JOIN
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_JOIN, creator));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_JOIN, admin));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_JOIN, moderator));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_JOIN, other));
		
		// OPERATION_LEAVE
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_LEAVE, creator));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_LEAVE, admin));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_LEAVE, moderator));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_LEAVE, other));
		
		// OPERATION_START
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_START, creator));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_START, admin));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_START, moderator));
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_START, other));
	}
	
	private long playerId = 0;

	private Player getPlayer(String role)
	{
		Player player = new Player();
		player.setId(--playerId);
		player.setUser(new User());
		player.getUser().setId(playerId);
		player.getUser().setRole(new UserRole());
		player.getUser().getRole().setId(playerId);
		player.getUser().getRole().setRolename(role);
		return player;
	}
}
