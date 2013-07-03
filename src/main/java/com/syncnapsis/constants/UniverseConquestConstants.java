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
package com.syncnapsis.constants;

import com.syncnapsis.data.model.SolarSystemInfrastructure;

/**
 * Class defining constants within universe-conquest
 * 
 * @author ultimate
 */
public class UniverseConquestConstants
{
	/**
	 * The name for the parameter containing the default percentage for rivals in vendetta mode
	 */
	public static final String	PARAM_MATCH_VENDETTA_PARAM_DEFAULT		= "match.vendetta.param.default";

	/**
	 * The maximum value for the habitability of a SolarSystem
	 * 
	 * @see SolarSystemInfrastructure#getHabitability()
	 */
	public static final String	PARAM_SOLARSYSTEM_HABITABILITY_MAX		= "solarsystem.habitability.max";
	/**
	 * The maximum value for the size of a SolarSystem
	 * 
	 * @see SolarSystemInfrastructure#getSize()
	 */
	public static final String	PARAM_SOLARSYSTEM_SIZE_MAX				= "solarsystem.size.max";
	/**
	 * The factor for calculating the maximum population of a SolarSystem from the habitability and
	 * the size in the way <code>habitability*size*factor</code>
	 */
	public static final String	PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR	= "solarsystem.population.factor";
}
