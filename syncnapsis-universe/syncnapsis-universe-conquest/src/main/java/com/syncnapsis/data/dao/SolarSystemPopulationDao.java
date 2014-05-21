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

import com.syncnapsis.data.model.SolarSystemPopulation;

/**
 * Dao-Interface for access to SolarSystemPopulation
 * 
 * @author ultimate
 */
public interface SolarSystemPopulationDao extends GenericDao<SolarSystemPopulation, Long>
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
}
