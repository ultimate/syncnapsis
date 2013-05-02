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
	 * Create a new match and start it if the startCondition demands it.
	 * 
	 * @see EnumStartCondition
	 * @param title - the title of the match
	 * @param galaxyId - the galaxy for the match
	 * @param speed - the speed of the match
	 * @param startCondition - the start condition
	 * @param startDate - the start date (if required for the start condition)
	 * @param startSystemSelectionEnabled - is start system selection enabled?
	 * @param startSystemCount - the number of start systems for each participant
	 * @param victoryCondition - the victory condition
	 * @param participantsMax - the max number of participants
	 * @param participantsMin - the min number of participants
	 * @param participantIds - a list of participants invited
	 * @return the match created
	 */
	public Match createMatch(String title, long galaxyId, int speed, EnumStartCondition startCondition, Date startDate,
			boolean startSystemSelectionEnabled, int startSystemCount, EnumVictoryCondition victoryCondition, int participantsMax,
			int participantsMin, List<Long> participantIds);

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
	 * creator.
	 * 
	 * @param matchId - the id of the match to start
	 * @return the match entity
	 */
	public Match startMatch(long matchId);

	/**
	 * Cancel a planned game if it has not yet been started. This will include cleaning up all
	 * remaining data like start system selections, associated
	 * participants, initialized galaxies and more...<br>
	 * <br>
	 * Canceling the match will perform a security check wether the calling user is equal to the
	 * creator.
	 * 
	 * @param matchId - the id of the match to cancel
	 * @return true if the match has been canceled, false otherwise
	 */
	public boolean cancelMatch(long matchId);

	/**
	 * Clean up the given match by setting all remaining required properties for the match and all
	 * associated entities. This will include setting finish and or destruction dates etc.
	 * 
	 * @param matchId - the id of the match to finish
	 * @return the match entity
	 */
	public Match finishMatch(long matchId);

	/**
	 * Get the list of matches for a creator
	 * 
	 * @param creatorId - the id of the creator
	 * @param planned - include planned matches?
	 * @param active - include active matches?
	 * @param finished - include finished matches?
	 * @return the list of matches
	 */
	public List<Match> getByCreator(long creatorId, boolean planned, boolean active, boolean finished);
	
	/**
	 * Get the list of matches for a player
	 * 
	 * @param playerId - the id of the player
	 * @param planned - include planned matches?
	 * @param active - include active matches?
	 * @param finished - include finished matches?
	 * @return the list of matches
	 */
	public List<Match> getByPlayer(long playerId, boolean planned, boolean active, boolean finished);

	/**
	 * Get the list of matches for a galaxy
	 * 
	 * @param galaxyId - the id of the galaxy
	 * @param planned - include planned matches?
	 * @param active - include active matches?
	 * @param finished - include finished matches?
	 * @return the list of matches
	 */
	public List<Match> getByGalaxy(long galaxyId, boolean planned, boolean active, boolean finished);
}
