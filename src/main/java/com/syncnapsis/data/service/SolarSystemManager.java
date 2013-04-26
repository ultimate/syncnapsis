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

import com.syncnapsis.data.model.SolarSystem;

/**
 * Manager-Interface for access to SolarSystem.
 * 
 * @author ultimate
 */
public interface SolarSystemManager extends GenericManager<SolarSystem, Long>
{
	/**
	 * Get all SolarSystems for a Galaxy
	 * 
	 * @param galaxyId - the id of the Galaxy
	 * @return the list of SolarSystems
	 */
	public List<SolarSystem> getByGalaxy(long galaxyId);

//	/**
//	 * Create a new SolarSystem with the given parameters
//	 * 
//	 * @param galaxyId - the Galaxy the SolarSystem is in
//	 * @param coords - the coordinates for the SolarSystem
//	 * @param name - the name for the SolarSystem
//	 * @param size - the size of the SolarSystem
//	 * @param habitability - the habitability of the SolarSystem
//	 * @return the newly created SolarSystem entity
//	 */
//	public SolarSystem create(long galaxyId, Vector.Integer coords, String name, int size, int habitability);
}
