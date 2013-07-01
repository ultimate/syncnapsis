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
 * @author ultimate
 * 
 */
public class MatchAccessController implements AccessController<Match>
{
	public static final int	OPERATION_CREATE			= 1;
	public static final int	OPERATION_START				= 2;
	public static final int	OPERATION_CANCEL			= 3;
	public static final int	OPERATION_ADD_PARTICIPANT	= 4;
	public static final int	OPERATION_JOIN				= 5;
	public static final int	OPERATION_LEAVE				= 6;

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
		if(operation == OPERATION_CANCEL && target.getState() == EnumMatchState.active)
			return isModerator(player) || isAdmin(player);
		
		return target.getCreator().equals(player) || isModerator(player) || isAdmin(player);
	}

	protected boolean isModerator(Player player)
	{
		return player.getUser().getRole().getRolename().equals(ApplicationBaseConstants.ROLE_MODERATOR);
	}

	protected boolean isAdmin(Player player)
	{
		return player.getUser().getRole().getRolename().equals(ApplicationBaseConstants.ROLE_ADMIN);
	}
}
