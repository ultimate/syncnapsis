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

import java.util.List;

import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.utils.data.ExtendedRandom;

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

	/**
	 * Create a new SolarSystem with the given parameters
	 * 
	 * @param galaxy - the Galaxy the SolarSystem is in
	 * @param coords - the coordinates for the SolarSystem
	 * @param name - the name for the SolarSystem
	 * @param random - the random number generator used to generate more parameters if required
	 * @return the newly created SolarSystem entity
	 */
	public SolarSystem create(Galaxy galaxy, Vector.Integer coords, String name);
	
	/**
	 * Create a new SolarSystem from the given List of parameters.<br>
	 * Therefore systemNames will taken from the list in the following way:
	 * <ul>
	 * <li><code>systemNames == null || systemNames.size() == 0</code> : random systemNames will be
	 * generated</li>
	 * <li><code>systemNames.size() >= systemCoords.size()</code> : name for systemCoords[i] =
	 * systemNames[i]</li>
	 * <li><code>systemNames.size() < systemCoords.size()</code> : name for systemCoords[i] =
	 * systemNames[i%systemNames.size()] + counter if necessary (e.g. "example I", "example II",
	 * ...)</li>
	 * </ul>
	 * 
	 * @param galaxy - the Galaxy the SolarSystem is in
	 * @param systemCoords - the list containing all the systemCoords for the SolarSystems
	 * @param systemNames - the list containing possible systemNames for the SolarSystems (optional)
	 * @param index - the index of the SolarSystem to generate within the systemCoords
	 * @param random - the random number generator used to generate more parameters if required
	 * @return the newly created SolarSystem entity
	 */
	public SolarSystem create(Galaxy galaxy, List<Vector.Integer> systemCoords, List<String> systemNames, int index, ExtendedRandom random);
}
