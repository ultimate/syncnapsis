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
import com.syncnapsis.enums.EnumMatchState;
import com.syncnapsis.security.AccessController;

/**
 * AccessController for {@link Match}
 * 
 * @author ultimate
 */
public class MatchAccessController implements AccessController<Match>
{
	/**
	 * The operation "create"
	 */
	public static final int	OPERATION_CREATE				= 1;
	/**
	 * The operation "start"
	 */
	public static final int	OPERATION_START					= 2;
	/**
	 * The operation "cancel"
	 */
	public static final int	OPERATION_CANCEL				= 3;
	/**
	 * The operation "add participant"
	 */
	public static final int	OPERATION_ADD_PARTICIPANT		= 4;
	/**
	 * The operation "remove participant"
	 */
	public static final int	OPERATION_REMOVE_PARTICIPANT	= 5;
	/**
	 * The operation "join as a participant"
	 */
	public static final int	OPERATION_JOIN					= 6;
	/**
	 * The operation "leave" or "giveup"
	 */
	public static final int	OPERATION_LEAVE					= 7;

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.AccessController#getTargetClass()
	 */
	@Override
	public Class<Match> getTargetClass()
	{
		return Match.class;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.AccessController#isAccessible(java.lang.Object, int,
	 * java.lang.Object[])
	 */
	@Override
	public boolean isAccessible(Match target, int operation, Object... authorities)
	{
		Player player = (Player) authorities[0];

		if(operation == OPERATION_CREATE)
			return true; // everybody
		if(operation == OPERATION_JOIN)
			return true; // everybody
		if(operation == OPERATION_LEAVE)
			return true; // everybody
		if(target.getState() != EnumMatchState.planned)
			return isModerator(player) || isAdmin(player);

		return target.getCreator().equals(player) || isModerator(player) || isAdmin(player);
	}

	/**
	 * Is the given player a moderator?
	 * 
	 * @param player - the player to check
	 * @return true or false
	 */
	protected boolean isModerator(Player player)
	{
		return player.getUser().getRole().getRolename().equals(ApplicationBaseConstants.ROLE_MODERATOR);
	}

	/**
	 * Is the given player an admin?
	 * 
	 * @param player - the player to check
	 * @return true or false
	 */
	protected boolean isAdmin(Player player)
	{
		return player.getUser().getRole().getRolename().equals(ApplicationBaseConstants.ROLE_ADMIN);
	}
}
