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
package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.model.Participant;

/**
 * Dao-Interface for access to Participant
 * 
 * @author ultimate
 */
public interface ParticipantDao extends GenericDao<Participant, Long>
{
	/**
	 * Get all Participants for a match
	 * 
	 * @param matchId - the match
	 * @return the List of Participants
	 */
	public List<Participant> getByMatch(long matchId);

	/**
	 * Get the Participant entity associating the given Empire with the given match
	 * 
	 * @param matchId - the match
	 * @param empireId - the empire
	 * @return the Participant if existing, null otherwise
	 */
	public Participant getByMatchAndEmpire(long matchId, long empireId);
}
