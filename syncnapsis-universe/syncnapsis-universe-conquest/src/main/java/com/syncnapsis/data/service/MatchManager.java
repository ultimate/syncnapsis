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
package com.syncnapsis.data.service;

import java.util.Date;
import java.util.List;

import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.help.Rank;
import com.syncnapsis.enums.EnumJoinType;
import com.syncnapsis.enums.EnumMatchState;
import com.syncnapsis.enums.EnumStartCondition;
import com.syncnapsis.enums.EnumVictoryCondition;
import com.syncnapsis.utils.data.ExtendedRandom;

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
	 * Get the list of matches that are in the given {@link EnumMatchState}.
	 * 
	 * @param state - the state to search for
	 * @return the list of matches
	 */
	public List<Match> getByState(EnumMatchState state);

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
	 * @param empireIds - a list of empire to add as participants
	 * @param plannedJoinType - the join type before the match is started
	 * @param startedJoinType - the join type after the match is started
	 * @return the match created
	 */
	public Match createMatch(String title, long galaxyId, int speed, Long seed, EnumStartCondition startCondition, Date startDate,
			boolean startSystemSelectionEnabled, int startSystemCount, long startPopulation, EnumVictoryCondition victoryCondition,
			int victoryParameter, int participantsMax, int participantsMin, Long[] empireIds, EnumJoinType plannedJoinType,
			EnumJoinType startedJoinType);

	/**
	 * Create a new match and start it if the startCondition demands it.<br>
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
	 * @param empireIds - a list of empire to add as participants
	 * @param plannedJoinType - the join type before the match is started
	 * @param startedJoinType - the join type after the match is started
	 * @return the match created
	 */
	public Match createMatch(String title, long galaxyId, int speed, String seed, EnumStartCondition startCondition, Date startDate,
			boolean startSystemSelectionEnabled, int startSystemCount, long startPopulation, EnumVictoryCondition victoryCondition,
			int victoryParameter, int participantsMax, int participantsMin, Long[] empireIds, EnumJoinType plannedJoinType,
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
	 * Cancel a planned game if it has not yet been started. This may include cleaning up
	 * remaining data if necessary.<br>
	 * <br>
	 * Canceling the match will perform a security check wether the calling user is equal to the
	 * creator or is an admin/moderator. Additionally admins/moderators even have the right to
	 * cancel active matches.
	 * 
	 * @param match - the match to cancel
	 * @return the match entity
	 */
	public Match cancelMatch(Match match);

	/**
	 * Clean up the given match by setting all remaining required properties for the match and all
	 * associated entities. This will include setting finish and or destruction dates etc.
	 * 
	 * @param match - the match to finish
	 * @return the match entity
	 */
	public Match finishMatch(Match match);

	/**
	 * Check wether a match should start by its start condition. This method will only check the
	 * start condition field but not any other prerequisites that must be met. This additional check
	 * is performed by {@link MatchManager#isStartPossible(Match)}. If the start is necessary and
	 * possible the match may be started using {@link MatchManager#startMatch(Match)}.
	 * 
	 * @param match - the match to check
	 * @return true or false
	 */
	public boolean isStartNecessary(Match match);

	/**
	 * Check wether it is possible to start a match. This will check against the limits of min and
	 * max participants and possibly other properties in the future. Only if this method returns
	 * true it is possible to start the match using {@link MatchManager#startMatch(Match)}.
	 * 
	 * @param match - the match to check
	 * @return true or false
	 */
	public boolean isStartPossible(Match match);

	/**
	 * Get reason why a match is not startable. If the returned list is empty or null, the match is
	 * startable.
	 * 
	 * @see MatchManager#isStartPossible(Match)
	 * @param match - the match to check
	 * @return the list of reasons
	 */
	public List<String> getMatchStartNotPossibleReasons(Match match);

	/**
	 * Check wether the victory condition for the given match is met. If the condition is met the
	 * match is ready to be finished by using {@link MatchManager#finishMatch(Match)}. This requires
	 * the match's ranking to be up-to-date since this method won't update the ranking before
	 * checking the condition.
	 * 
	 * @param match - the match to check
	 * @return true or false
	 */
	public boolean isVictoryConditionMet(Match match);

	/**
	 * Update the rankings for the given match.
	 * 
	 * @param match - the match to update
	 * @return the match entity
	 */
	public Match updateRanking(Match match);

	/**
	 * Assign the specified number of rivals to each participant from within the choice of all other
	 * participants within the list. This method guarantees every participant is exactly rival for
	 * the n participants and receives n rivals itself where n is the number of rivals specified.
	 * Furthermore it will guarantee not to assign a rival multiple times to the same participant.<br>
	 * <br>
	 * The algorithm is design in such a way that calling this method twice with the same list of
	 * participants, number of rivals and random number generator the assigned rivals will be
	 * identical. This will also include the case where the order of the participants within the
	 * list has been changed, since this order does not have any influence on the result of the
	 * algorithm.<br>
	 * <br>
	 * <b>Note:</b> The participant entities won't be saved to database - this method only assigns
	 * the rivals as references.
	 * 
	 * @param participants - the number of participants
	 * @param rivals - the number of rivals to assign for each participant
	 * @param random - the ExtendedRandom-number generator
	 * @return the matrix of rival associations
	 */
	public void assignRivals(List<Participant> participants, int rivals, ExtendedRandom random);

	/**
	 * Return the number of rivals to assign for the given number of total participants primarily
	 * used in vedetta mode. This method will currently return zero for all game mode other than
	 * {@link EnumVictoryCondition#vendetta}<br>
	 * 
	 * @param match - the match to get the number of rivals for
	 * @return the number of rivals
	 */
	public int getNumberOfRivals(Match match);

	/**
	 * Create all necessary channels for the given match.<br>
	 * Channels might only be created if the match is in a state that requires those channels.
	 * Otherwise channels not needed may be omitted.
	 * 
	 * @param match - the match to create the channels for
	 */
	public void createChannels(Match match);

	/**
	 * Update all channels for the given match.
	 * 
	 * @param match - the match to update the channels for
	 * @param updateRanks - send update for the participant's ranks?
	 * @param updateSystems - send update for systems (including populations and infrastructures)?
	 * @param updateMovements - send update for movements?
	 */
	public void updateChannels(Match match, boolean updateRanks, boolean updateSystems, boolean updateMovements);

	/**
	 * Get a list of the ranks of all (active) {@link Participant}s.
	 * 
	 * @param match - the match to get the list for
	 * @return the rank list
	 */
	public List<Rank> getRankList(Match match);

	/**
	 * Create a list representation of the given {@link Match} that is reduced to the following
	 * simple format:<br>
	 * <code><pre>
	 * [
	 *   [inf_id, inf_val, part1_id, part1_pop, ... , partn_id, partn_pop ],
	 *   ... // for each infrastructure/system
	 * ]
	 * </pre></code>
	 * 
	 * @param match - the match to get the list for
	 * @param time - the time for which to create the list
	 * @return the list of systems (including infrastructure and populations)
	 */
	public List<List<Long>> getSystemList(Match match, Date time);

	/**
	 * 
	 * @param match - the match to get the list for
	 * @param time - the time for which to create the list
	 * @return the list of movements
	 */
	public List<List<Long>> getMovementList(Match match, Date time);
}
