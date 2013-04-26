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

import java.util.List;

import com.syncnapsis.data.model.Participant;

/**
 * Manager-Interface for access to Participant.
 * 
 * @author ultimate
 */
public interface ParticipantManager extends GenericManager<Participant, Long>
{
	/**
	 * Let the current Player join the given match as a Participant.
	 * 
	 * @param matchId - the match to join
	 * @return the newly created Participant associating the Player with the Match
	 */
	public Participant joinMatch(long matchId);

	/**
	 * Let the current Player leave a (non-started) match.
	 * 
	 * @param matchId - the match to leave
	 * @return true if the match was left, false otherwise
	 */
	public boolean leaveMatch(long matchId);

	/**
	 * Add the given Player as a participant for the given Match. This require the current user to
	 * be the creator of the Match.
	 * 
	 * @param matchId - the match to add the player to
	 * @param playerId - the player to add
	 * @return true if adding was successful, false otherwise
	 */
	public boolean addParticipant(long matchId, long playerId);

	/**
	 * Remove the given Player as a participant for the given Match. This require the current user
	 * to be the creator of the Match.
	 * 
	 * @param matchId - the match to remove the player to
	 * @param playerId - the player to remove
	 * @return true if removing was successful, false otherwise
	 */
	public boolean removeParticipant(long matchId, long playerId);

	/**
	 * Cleanup the given participant after all of his populations have been destroyed or the player
	 * gave up.
	 * 
	 * @param participantId - the Participant to destroy
	 * @return the updated Participant entity
	 */
	public Participant destroy(long participantId);

	/**
	 * Get all Participants for a match
	 * 
	 * @param matchId - the match
	 * @return the List of Participants
	 */
	public List<Participant> getByMatch(long matchId);
}
