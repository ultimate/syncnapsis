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

import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.enums.EnumDestructionType;
import com.syncnapsis.enums.EnumPopulationPriority;
import com.syncnapsis.universe.Calculator;
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
	 * Create a new SolarSystemPopulation as a spin of from the given origin population.<br>
	 * If priorities are not set values from the origin will be used as default. The time of travel
	 * will autmatically be calculated from the desired (initial) travel speed.
	 * 
	 * @param origin - the origin population
	 * @param targetInfrastructure - the infrastruture to travel to
	 * @param travelSpeed - the speed to travel with (in percent)
	 * @param population - the population for the spinoff
	 * @param attackPriority - the attack priority (if null the origins priority will be used)
	 * @param buildPriority - the build priority (if null the origins priority will be used)
	 * @return the newly created SolarSystemPopulation entity
	 */
	public SolarSystemPopulation spinoff(SolarSystemPopulation origin, SolarSystemInfrastructure targetInfrastructure, int travelSpeed,
			long population, EnumPopulationPriority attackPriority, EnumPopulationPriority buildPriority);

	/**
	 * Create a new SolarSystemPopulation as resettlement from the given origin population. The
	 * original population will be destroyed (as given up) and a new population entity will be
	 * created to maintain tracability.<br>
	 * The time of travel will autmatically be calculated from the desired (initial) travel speed.<br>
	 * Exodus mode will mean all infrastructure of the origin system will be disassembled to
	 * increase the maximum travel distance and those infrastructure may be used to increase the
	 * infrastructure of the target system additionally.
	 * 
	 * @param origin - the origin population
	 * @param targetInfrastructure - the infrastruture to travel to
	 * @param travelSpeed - the speed to travel with (in percent)
	 * @param exodus - perform exodus on the origin system
	 * @param attackPriority - the attack priority (if null the origins priority will be used)
	 * @param buildPriority - the build priority (if null the origins priority will be used)
	 * @return the newly created SolarSystemPopulation entity
	 */
	public SolarSystemPopulation resettle(SolarSystemPopulation origin, SolarSystemInfrastructure targetInfrastructure, int travelSpeed,
			boolean exodus, EnumPopulationPriority attackPriority, EnumPopulationPriority buildPriority);

	/**
	 * Destroy the given population and clean up by setting several properties.<br>
	 * This method may be used for give ups (and resettles) as well as for attack results.<br>
	 * <b>Note: population <u>won't be saved</u> during this process!</b>
	 * 
	 * @param population - the population to destroy
	 * @param destructionType - the destruction type
	 * @param destructionDate - the destruction date
	 * @return the updated SolarSystemPopulation entity
	 */
	public SolarSystemPopulation destroy(SolarSystemPopulation population, EnumDestructionType destructionType, Date destructionDate);

	/**
	 * Merge the populations for this infrastructe if possible.<br>
	 * Populations of the same participant (already arrived at the infrastructure) will be merged to
	 * single entities by adding population values and preserving the oldest colonization date.<br>
	 * Single popuations newly arrived may be updated during this process as well.<br>
	 * <b>Note: populations and infrastructure <u>won't be saved</u> during this process!</b>
	 * 
	 * @param infrastructure - the infrastructure which's populations to merge
	 * @param time - the current time
	 * @return the list of merged populations
	 */
	public List<SolarSystemPopulation> merge(SolarSystemInfrastructure infrastructure, Date time);

	/**
	 * Update the travel speed of a population
	 * 
	 * @param population - the population to update
	 * @param travelSpeed - the new speed to set
	 * @return the updated population entity
	 */
	public SolarSystemPopulation updateTravelSpeed(SolarSystemPopulation population, int travelSpeed);

	/**
	 * Get the population with the earliest arrival/colonization date that is still present at the
	 * given {@link SolarSystemInfrastructure} and therefore has the "home-bonus" for the give point
	 * in time.
	 * 
	 * @param infrastructure - the infrastructure to scan
	 * @param time - the time to calculate the home population for
	 * @return the "oldest" population for this system or null if there are currently no populations
	 *         present
	 */
	public SolarSystemPopulation getHomePopulation(SolarSystemInfrastructure infrastructure, Date time);

	/**
	 * Create a new SolarSystemPopulation for a start system selected for the given participant (and
	 * empire). If a population already exists for the given infrastructure it will be updated with
	 * the given population or even be deleted, if the update value is 0.<br>
	 * If a population exists for the given infrastructure for a different participant this method
	 * will throw an IllegalArgumentsException.<br>
	 * Furthermore this method will check wether the participant has enough start population left
	 * for creating the SolarSystemPopulation and will otherwise only assign the population left for
	 * this participant.
	 * 
	 * @param participant - the participant to create the population for
	 * @param infrastructure - the SolarSystem represented by the it's SolarSystemInfrastructure
	 * @param population - the population for the SolarSystem
	 * @return the newly created SolarSystemPopulation entity
	 */
	public SolarSystemPopulation selectStartSystem(Participant participant, SolarSystemInfrastructure infrastructure, long population);

	/**
	 * Simulate population changes due to building, population growths and attacks.<br>
	 * This method includes all operations necessary to be performed for a tick, in special:
	 * <ul>
	 * <li>{@link SolarSystemPopulationManager#merge(SolarSystemInfrastructure)}</li>
	 * <li>{@link Calculator#simulate(SolarSystemInfrastructure, ExtendedRandom)}</li>
	 * <li>saving all updated entities</li>
	 * </ul>
	 * 
	 * @param infrastructure - the infrastructure which's populations to merge and simulate
	 * @param random - the random number generator used for randomization
	 * @return the list of simulated populations
	 */
	public List<SolarSystemPopulation> simulate(SolarSystemInfrastructure infrastructure, ExtendedRandom random);
}
