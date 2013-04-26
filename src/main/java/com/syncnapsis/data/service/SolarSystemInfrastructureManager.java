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

import com.syncnapsis.data.model.SolarSystemInfrastructure;

/**
 * Manager-Interface for access to SolarSystemInfrastructure.
 * 
 * @author ultimate
 */
public interface SolarSystemInfrastructureManager extends GenericManager<SolarSystemInfrastructure, Long>
{
	/**
	 * Get all infrastructures for a given match
	 * 
	 * @param matchId - the match
	 * @return the List of SolarSystemInfrastrutures
	 */
	public List<SolarSystemInfrastructure> getByMatch(long matchId);

//	/**
//	 * Create a new SolarSystemInfrastructure for the given Match and SolarSystem
//	 * 
//	 * @param matchId - the Match for the infrastructure
//	 * @param systemId - the SolarSystem for the infrastructure
//	 * @param infrastructure - the infrastructure value
//	 * @return the newly created SolarSystemInfrastructure entity
//	 */
//	public SolarSystemInfrastructure initialize(long matchId, long systemId, int infrastructure);
}
