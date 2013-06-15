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
package com.syncnapsis.data.service;

import java.util.Date;
import java.util.List;

import com.syncnapsis.data.model.Match;
import com.syncnapsis.enums.EnumJoinType;
import com.syncnapsis.enums.EnumStartCondition;
import com.syncnapsis.enums.EnumVictoryCondition;

/**
 * Manager-Interface for access to Match.
 * 
 * @author ultimate
 */
public interface MatchManager extends GenericNameManager<Match, Long>
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
	 * @return the list of matches
	 */
	public List<Match> getByCreator(long creatorId, boolean planned, boolean active, boolean finished, boolean canceled);

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
	 * @return the list of matches
	 */
	public List<Match> getByPlayer(long playerId, boolean planned, boolean active, boolean finished, boolean canceled);

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
	 * @return the list of matches
	 */
	public List<Match> getByGalaxy(long galaxyId, boolean planned, boolean active, boolean finished, boolean canceled);

	/**
	 * Create a new match and start it if the startCondition demands it.
	 * 
	 * @see EnumStartCondition
	 * @param title - the title of the match
	 * @param galaxyId - the galaxy for the match
	 * @param speed - the speed of the match
	 * @param seed - an optional seed for the match
	 * @param startCondition - the start condition
	 * @param startDate - the start date (if required for the start condition)
	 * @param startSystemSelectionEnabled - is start system selection enabled?
	 * @param startSystemCount - the number of start systems for each participant
	 * @param startPopulation - the total population for all start systems
	 * @param victoryCondition - the victory condition
	 * @param victoryParameter - the victory parameter
	 * @param participantsMax - the max number of participants
	 * @param participantsMin - the min number of participants
	 * @param participantIds - a list of participants invited (as player ids)
	 * @param plannedJoinType - the join type before the match is started
	 * @param startedJoinType - the join type after the match is started
	 * @return the match created
	 */
	public Match createMatch(String title, long galaxyId, int speed, Long seed, EnumStartCondition startCondition, Date startDate,
			boolean startSystemSelectionEnabled, int startSystemCount, int startPopulation, EnumVictoryCondition victoryCondition,
			int victoryParamter, int participantsMax, int participantsMin, List<Long> participantIds, EnumJoinType plannedJoinType,
			EnumJoinType startedJoinType);

	/**
	 * Force the start of the given match no matter if the start condition is met or not.<br>
	 * <br>
	 * All required settings and calculations required prior to starting the match will be made.
	 * This will include randomly associating the rivals in vendetta-mode as well as marking all
	 * populations chosen as start populations with the first colonization date and select ramdom
	 * start systems for all participants without start systems chosen. If necessary the match will
	 * furthermore be registered for calculation services etc.<br>
	 * <br>
	 * Starting the match will perform a security check wether the calling user is equal to the
	 * creator or if the match is started by the system itself.
	 * 
	 * @param match - the match to start
	 * @return the match entity
	 */
	public Match startMatch(Match match);

	/**
	 * Start the given match if the start condition is met.<br>
	 * 
	 * @see MatchManager#startMatch(Match)
	 * @param match - the match to start
	 * @return the match entity
	 */
	public Match startMatchIfNecessary(Match match);

	/**
	 * Cancel a planned game if it has not yet been started. This will include cleaning up all
	 * remaining data like start system selections, associated
	 * participants, initialized galaxies and more...<br>
	 * <br>
	 * Canceling the match will perform a security check wether the calling user is equal to the
	 * creator.
	 * 
	 * @param match - the match to cancel
	 * @return true if the match has been canceled, false otherwise
	 */
	public boolean cancelMatch(Match match);

	/**
	 * Clean up the given match by setting all remaining required properties for the match and all
	 * associated entities. This will include setting finish and or destruction dates etc.
	 * 
	 * @param match - the match to finish
	 * @return the match entity
	 */
	public Match finishMatch(Match match);
}
