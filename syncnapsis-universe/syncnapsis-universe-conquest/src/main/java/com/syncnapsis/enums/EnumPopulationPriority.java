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
package com.syncnapsis.enums;

import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;

/**
 * Enum representing possible priorities for changing {@link SolarSystemPopulation}s and
 * {@link SolarSystemInfrastructure}s.<br>
 * Changes can result from population growth and/or attacks
 * 
 * @author ultimate
 */
public enum EnumPopulationPriority
{
	/**
	 * Set the priority to population growth (if settling) or destruction (if attacking).
	 */
	population,
	/**
	 * Set the priority to infrastructure growth (if settling) or destruction (if attacking).
	 */
	infrastructure,
	/**
	 * Use a balanced setting for growth (if settling) or destruction (if attacking).
	 */
	balanced,
}
