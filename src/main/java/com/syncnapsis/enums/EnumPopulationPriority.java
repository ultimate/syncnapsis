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
