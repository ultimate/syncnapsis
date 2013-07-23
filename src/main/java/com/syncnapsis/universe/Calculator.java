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
package com.syncnapsis.universe;

import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;

/**
 * Interface for the calculator service containing the mathematical game logic.
 * 
 * @author ultimate
 */
public interface Calculator
{
	/**
	 * Get the standard travel distance for the given galaxy
	 * 
	 * @param galaxy - the galaxy to get the travel distance for
	 * @return the distance
	 */
	public int getStandardTravelDistance(Galaxy galaxy);

	/**
	 * Calculate the maximum travel distance for the amount population to move away from the origin
	 * population.<br>
	 * The distance calculated may be dependent of
	 * <ul>
	 * <li>the origin population value</li>
	 * <li>the moved population value</li>
	 * <li>the origin infrastructure value</li>
	 * <li>colonization information (e.g. exodus-mode or not)</li>
	 * <li>and other information (e.g. required information from the associated match)</li>
	 * </ul>
	 * 
	 * @param origin - the origin population
	 * @param movedPopulation - the amount of population to move
	 * @param exodus - wether the origin population is completely given up and infrastructure will
	 *            be disassembled
	 * @return the maximum travel distance
	 */
	public double calculateMaxTravelDistance(SolarSystemPopulation origin, long movedPopulation, boolean exodus);

	/**
	 * This calculation is the inversion of
	 * {@link Calculator#calculateMaxTravelDistance(SolarSystemPopulation, long)} and will determine
	 * the maximum population required to be able to travel the desired distance.
	 * 
	 * @param origin - the origin population
	 * @param travelDistance - the distance to travel
	 * @return the maximum amount of population to move
	 */
	public long calculateMaxMovablePopulation(SolarSystemPopulation origin, double travelDistance);

	/**
	 * Calculate the minimum time needed to travel from one SolarSystem to another. SolarSystems are
	 * therefore represented by their infrastructure to be able to determine
	 * additional information if necessary.
	 * 
	 * @param origin - the origin infrastructure
	 * @param target - the target infrastructure
	 * @param travelSpeed - the speed for travelling
	 * @return the time needed for traveling the whole distance with the given speed in ms
	 */
	public long calculateTravelTime(SolarSystemInfrastructure origin, SolarSystemInfrastructure target, int travelSpeed);
}
