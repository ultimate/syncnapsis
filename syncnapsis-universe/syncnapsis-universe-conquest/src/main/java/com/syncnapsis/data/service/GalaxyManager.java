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
import com.syncnapsis.data.model.help.Vector;

/**
 * Manager-Interface for access to Galaxy.
 * 
 * @author ultimate
 */
public interface GalaxyManager extends GenericNameManager<Galaxy, Long>
{
	/**
	 * Get all galaxies created by the given player.
	 * 
	 * @param playerId - the creator
	 * @return the list of Galaxy entities
	 */
	public List<Galaxy> getByCreator(long playerId);

	/**
	 * Create a Galaxy from the list describing the underlying SolarSystems. The coordinates will be
	 * used to initialize the SolarSystems which will either be named randomly or from the given
	 * name list (see {@link SolarSystemManager#create(Galaxy, List, List, int)})
	 * The size of the galaxy will be calculated automatically from the given coordinates.
	 * 
	 * @param name - the name for the Galaxy
	 * @param systemCoords - the list containing all the systemCoords for the SolarSystems
	 * @param systemNames - the list containing possible systemNames for the SolarSystems (optional)
	 * @param seed - the seed used for the random generator. If null a random seed will be used
	 * @return the newly created Galaxy
	 */
	public Galaxy create(String name, List<Vector.Integer> systemCoords, List<String> systemNames, Long seed);

	/**
	 * Create a Galaxy from the given configuration. The underlying SolarSystems will be created
	 * genericly from the parameters specified in the configuration.<br>
	 * <br>
	 * <b>THIS METHOD IS DESIGNED AS A PLACEHOLDER FOR FUTURE USE!<br>
	 * IT'S METHOD SIGNATURE MAY CHANGE IN UPCOMING VERSIONS!</b>
	 * 
	 * @param configuration - the galaxy configuration
	 * @return the newly created Galaxy
	 */
	public Galaxy create(Object configuration);
}
