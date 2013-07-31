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
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.utils.data.ExtendedRandom;

/**
 * Manager-Interface for access to Participant.
 * 
 * @author ultimate
 */
public interface ParticipantManager extends GenericManager<Participant, Long>
{

	/**
	 * Let the current Player join the given match as a Participant.<br>
	 * If special conditions meet this method not necessarily will create a new participant object
	 * but may use a previous (old) entity (e.g. if leaving and rejoining before match started). If
	 * the method is called when the current player already is a participant for the given match
	 * this method will return the Participant entity forming the association.
	 * 
	 * @param match - the match to join
	 * @return the (newly created) Participant associating the Player with the Match
	 */
	public Participant joinMatch(Match match);

	/**
	 * Let the current Player leave a match.<br>
	 * Normally those matches will be non-started matches, but if an already active match is left
	 * this will be interpreted as "giving up"
	 * 
	 * @param match - the match to leave
	 * @return true if the match was left, false otherwise
	 */
	public boolean leaveMatch(Match match);

	/**
	 * Add the given Empire as a participant for the given Match. This require the current user to
	 * be the creator of the Match.<br>
	 * If special conditions meet this method not necessarily will create a new participant object
	 * but may use a previous (old) entity (e.g. if leaving and rejoining before match started). If
	 * the method is called when the current player already is a participant for the given match
	 * this method will return the Participant entity forming the association.
	 * 
	 * @param match - the match to add the player to
	 * @param empireId - the empire to add
	 * @return the (newly created) Participant associating the empire with the Match
	 */
	public Participant addParticipant(Match match, long empireId);

	/**
	 * Remove the given Empire as a participant for the given Match. This require the current user
	 * to be the creator of the Match.
	 * 
	 * @param match - the match to remove the player to
	 * @param empireId - the empire to remove
	 * @return true if removing was successful, false otherwise
	 */
	public boolean removeParticipant(Match match, long empireId);

	/**
	 * Cleanup the given participant after all of his populations have been destroyed or the player
	 * gave up.
	 * 
	 * @param participant - the Participant to destroy
	 * @param destructionType - the destruction type
	 * @param destructionDate - the destruction date
	 * @return the updated Participant entity
	 */
	public Participant destroy(Participant participant, EnumDestructionType destructionType, Date destructionDate);

	/**
	 * Let the given Participant participate in the associated match starting from the given date.
	 * To participate all the Participants populations will be fed with a colonization date.
	 * 
	 * @param participant - the participant to update
	 * @param participationDate - the date to start participating
	 * @return
	 */
	public Participant startParticipating(Participant participant, Date participationDate);

	/**
	 * Get all Participants for a match
	 * 
	 * @param matchId - the match
	 * @return the List of Participants
	 */
	public List<Participant> getByMatch(long matchId);

	/**
	 * Get the Participant entity associating the given Empire with the given match (will only
	 * return activated participants)
	 * 
	 * @param matchId - the match
	 * @param empireId - the empire
	 * @return the Participant if existing, null otherwise
	 */
	public Participant getByMatchAndEmpire(long matchId, long empireId);

	/**
	 * Create a new SolarSystemPopulation for a start system selected by the current player with the
	 * current empire as a participant. If a population already exists for the given infrastructure
	 * it will be updated with the given population or even be deleted, if the update value is 0.<br>
	 * If a population exists for the given infrastructure for a different participant this method
	 * will throw an IllegalArgumentsException.<br>
	 * Furthermore this method will check wether the participant has enough start population left
	 * for creating the SolarSystemPopulation and will otherwise only assign the population left for
	 * this participant.
	 * 
	 * @param infrastructure - the SolarSystem represented by the it's SolarSystemInfrastructure
	 * @param population - the population for the SolarSystem
	 * @return the newly created SolarSystemPopulation entity
	 */
	public SolarSystemPopulation selectStartSystem(SolarSystemInfrastructure infrastructure, long population);

	/**
	 * Randomly create all required start systems as SolarSystemPopulations for the given
	 * participant. This requires either to be the creator of the match or the current player to be
	 * this participant.
	 * 
	 * @param participant - the participant to create the start systems for
	 * @param random - the ExtendedRandom-number-generator used to randomly select the systems
	 * @return the list of start system populations
	 */
	public List<SolarSystemPopulation> randomSelectStartSystems(Participant participant, ExtendedRandom random);
}
