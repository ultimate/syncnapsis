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
package com.syncnapsis.data.dao;

import java.util.Date;
import java.util.List;

import com.syncnapsis.data.model.Match;
import com.syncnapsis.enums.EnumMatchState;

/**
 * Dao-Interface for access to Match
 * 
 * @author ultimate
 */
public interface MatchDao extends GenericNameDao<Match, Long>
{
	/**
	 * Get the list of matches for a creator for the given reference date:<br>
	 * This will mean the match state (planned, active, finished, canceled) will be determined for
	 * this timestamp.
	 * 
	 * @param creatorId - the id of the creator
	 * @param planned - include planned matches?
	 * @param active - include active matches?
	 * @param finished - include finished matches?
	 * @param canceled - include canceled matches?
	 * @param referenceDate - the date at which to evaluate the match states
	 * @return the list of matches
	 */
	public List<Match> getByCreator(long creatorId, boolean planned, boolean active, boolean finished, boolean canceled, Date referenceDate);

	/**
	 * Get the list of matches for a player for the given reference date:<br>
	 * This will mean the match state (planned, active, finished, canceled) will be determined for
	 * this timestamp.
	 * 
	 * @param playerId - the id of the player
	 * @param planned - include planned matches?
	 * @param active - include active matches?
	 * @param finished - include finished matches?
	 * @param canceled - include canceled matches?
	 * @param referenceDate - the date at which to evaluate the match states
	 * @return the list of matches
	 */
	public List<Match> getByPlayer(long playerId, boolean planned, boolean active, boolean finished, boolean canceled, Date referenceDate);

	/**
	 * Get the list of matches for a galaxy for the given reference date:<br>
	 * This will mean the match state (planned, active, finished, canceled) will be determined for
	 * this timestamp.
	 * 
	 * @param galaxyId - the id of the galaxy
	 * @param planned - include planned matches?
	 * @param active - include active matches?
	 * @param finished - include finished matches?
	 * @param canceled - include canceled matches?
	 * @param referenceDate - the date at which to evaluate the match states
	 * @return the list of matches
	 */
	public List<Match> getByGalaxy(long galaxyId, boolean planned, boolean active, boolean finished, boolean canceled, Date referenceDate);

	/**
	 * Get the list of matches that are in the given {@link EnumMatchState}.
	 * 
	 * @param state - the state to search for
	 * @return the list of matches
	 */
	public List<Match> getByState(EnumMatchState state);
}
