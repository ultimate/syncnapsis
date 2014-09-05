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
		assertEquals(Match.class, new MatchAccessController(new BaseAccessRule()).getTargetClass());
	}

	public void testIsAccessible() throws Exception
	{
		MatchAccessController controller = new MatchAccessController(new BaseAccessRule());

		Player creator = getPlayer(ApplicationBaseConstants.ROLE_NORMAL_USER);
		Player other = getPlayer(ApplicationBaseConstants.ROLE_NORMAL_USER);
		Player admin = getPlayer(ApplicationBaseConstants.ROLE_ADMIN);
		Player moderator = getPlayer(ApplicationBaseConstants.ROLE_MODERATOR);

		Match match = new Match();
		match.setCreator(creator);

		Object context = null;

		// OPERATION_ADD_PARTICIPANT
		match.setState(EnumMatchState.planned);
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT, context, getAuthorities(moderator)));
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT, context, getAuthorities(other)));
		match.setState(EnumMatchState.active);
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT, context, getAuthorities(moderator)));
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_ADD_PARTICIPANT, context, getAuthorities(other)));

		// OPERATION_REMOVE_PARTICIPANT
		match.setState(EnumMatchState.planned);
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_REMOVE_PARTICIPANT, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_REMOVE_PARTICIPANT, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_REMOVE_PARTICIPANT, context, getAuthorities(moderator)));
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_REMOVE_PARTICIPANT, context, getAuthorities(other)));
		match.setState(EnumMatchState.active);
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_REMOVE_PARTICIPANT, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_REMOVE_PARTICIPANT, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_REMOVE_PARTICIPANT, context, getAuthorities(moderator)));
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_REMOVE_PARTICIPANT, context, getAuthorities(other)));

		// OPERATION_CANCEL
		match.setState(EnumMatchState.planned);
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, context, getAuthorities(moderator)));
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, context, getAuthorities(other)));
		match.setState(EnumMatchState.active);
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, context, getAuthorities(moderator)));
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_CANCEL, context, getAuthorities(other)));

		// OPERATION_CREATE
		match.setState(EnumMatchState.planned);
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CREATE, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CREATE, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CREATE, context, getAuthorities(moderator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CREATE, context, getAuthorities(other)));
		match.setState(EnumMatchState.active);
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CREATE, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CREATE, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CREATE, context, getAuthorities(moderator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_CREATE, context, getAuthorities(other)));

		// OPERATION_JOIN
		match.setState(EnumMatchState.planned);
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_JOIN, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_JOIN, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_JOIN, context, getAuthorities(moderator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_JOIN, context, getAuthorities(other)));
		match.setState(EnumMatchState.active);
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_JOIN, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_JOIN, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_JOIN, context, getAuthorities(moderator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_JOIN, context, getAuthorities(other)));

		// OPERATION_LEAVE
		match.setState(EnumMatchState.planned);
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_LEAVE, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_LEAVE, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_LEAVE, context, getAuthorities(moderator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_LEAVE, context, getAuthorities(other)));
		match.setState(EnumMatchState.active);
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_LEAVE, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_LEAVE, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_LEAVE, context, getAuthorities(moderator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_LEAVE, context, getAuthorities(other)));

		// OPERATION_START
		match.setState(EnumMatchState.planned);
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_START, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_START, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_START, context, getAuthorities(moderator)));
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_START, context, getAuthorities(other)));
		match.setState(EnumMatchState.active);
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_START, context, getAuthorities(creator)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_START, context, getAuthorities(admin)));
		assertTrue(controller.isAccessible(match, MatchAccessController.OPERATION_START, context, getAuthorities(moderator)));
		assertFalse(controller.isAccessible(match, MatchAccessController.OPERATION_START, context, getAuthorities(other)));
	}

	private long	playerId	= 0;

	private Player getPlayer(int role)
	{
		Player player = new Player();
		player.setId(--playerId);
		player.setUser(new User());
		player.getUser().setId(playerId);
		player.getUser().setRole(new UserRole());
		player.getUser().getRole().setId(playerId);
		player.getUser().getRole().setMask(role);
		return player;
	}

	private Object[] getAuthorities(Player player)
	{
		return new Object[] { player, player.getRole(), player.getUser(), player.getUser().getRole() };
	}
}
