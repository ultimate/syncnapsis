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

import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.enums.EnumPopulationPriority;
import com.syncnapsis.utils.data.ExtendedRandom;

/**
 * Manager-Interface for access to SolarSystemPopulation.
 * 
 * @author ultimate
 */
public interface SolarSystemPopulationManager extends GenericManager<SolarSystemPopulation, Long>
{
	/**
	 * Get all SolarSystemPopulations belonging to the given participant (and the corresponding
	 * Match).
	 * 
	 * @param participantId - the participant to get the populations for
	 * @return the list of SolarSystemPopulations
	 */
	public List<SolarSystemPopulation> getByParticipant(long participantId);

	/**
	 * Get all SolarSystemPopulations for a given match.
	 * 
	 * @param matchId - the match to get the populations for
	 * @return the list of SolarSystemPopulations
	 */
	public List<SolarSystemPopulation> getByMatch(long matchId);

	/**
	 * Create a new SolarSystemPopulation for a start system selected the current player as a
	 * participant
	 * 
	 * @param infrastructure - the SolarSystem represented by the it's SolarSystemInfrastructure
	 * @param population - the population for the SolarSystem
	 * @return the newly created SolarSystemPopulation entity
	 */
	public SolarSystemPopulation selectStartSystem(SolarSystemInfrastructure infrastructure, int population);

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

	/**
	 * Create a new SolarSystemPopulation as a spin of from the given origin population.<br>
	 * If priorities are not set values from the origin will be used as default. The time of travel
	 * will autmatically be calculated if the arrival time is not set or is not far enough in future
	 * (travelling takes longer than time is remaining).
	 * 
	 * @param origin - the origin population
	 * @param targetInfrastructure - the infrastruture to travel to
	 * @param targetArrivalDate - the requested time of arrival
	 * @param population - the population for the spinoff
	 * @param attackPriority - the attack priority (if null the origins priority will be used)
	 * @param buildPriority - the build priority (if null the origins priority will be used)
	 * @return the newly created SolarSystemPopulation entity
	 */
	public SolarSystemPopulation spinoff(SolarSystemPopulation origin, SolarSystemInfrastructure targetInfrastructure, Date targetArrivalDate,
			int population, EnumPopulationPriority attackPriority, EnumPopulationPriority buildPriority);

	/**
	 * Create a new SolarSystemPopulation as resettlement from the given origin population. The
	 * original population will be destroyed (as given up) and a new population entity will be
	 * created to maintain tracability-<br>
	 * The time of travel will autmatically be calculated if the arrival time is not set or is not
	 * far enough in future (travelling takes longer than time is remaining).<br>
	 * 
	 * @param origin - the origin population
	 * @param targetInfrastructure - the infrastruture to travel to
	 * @param targetArrivalDate - the requested time of arrival
	 * @return the newly created SolarSystemPopulation entity
	 */
	public SolarSystemPopulation resettle(SolarSystemPopulation origin, SolarSystemInfrastructure targetInfrastructure, Date targetArrivalDate);

	/**
	 * Destroy the given population and clean up by setting several properties.<br>
	 * This method may be used for give ups (and resettles) as well as for attack results.
	 * 
	 * @param population - the population to destroy
	 * @param destructionType - the destruction type
	 * @param destructionDate - the destruction date
	 * @return the updated SolarSystemPopulation entity
	 */
	public SolarSystemPopulation destroy(SolarSystemPopulation population, EnumDestructionType destructionType, Date destructionDate);
}
