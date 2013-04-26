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
import java.util.Map;

import com.syncnapsis.data.model.Galaxy;

/**
 * Manager-Interface for access to Galaxy.
 * 
 * @author ultimate
 */
public interface GalaxyManager extends GenericNameManager<Galaxy, Long>
{
	/**
	 * Create a Galaxy from the list describing the underlying SolarSystems. All properties set
	 * within the the map-list will be used to create the SolarSystems, other properties not found
	 * will be randomly initialized.
	 * 
	 * @param seed - the seed used for the random generator. If null a random seed will be used
	 * @param systemList - the list describing the SolarSystems
	 * @return the newly created Galaxy
	 */
	public Galaxy createGalaxy(Long seed, List<Map<String, Object>> systemList);

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
	public Galaxy createGalaxy(Object configuration);
}
