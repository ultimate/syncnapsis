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

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.universe.Calculator;

/**
 * Enum representing differing victory conditions for matches.<br>
 * The condition is used to decide when a match is finished and a winner can be determined.
 * 
 * @author ultimate
 */
public enum EnumVictoryCondition
{	
	/**
	 * Rule over xx% of the galaxy for a specified duration
	 */
	domination,
	/**
	 * Destroy all populations/colonies of all other participants
	 */
	extermination,
	/**
	 * Destroy the populations/colonies of your specified randomly chosen rivals
	 */
	vendetta;
	// some more?
	
	/**
	 * Is this victory condition bound to a timeout?<br>
	 * If so, participant wins if condition is met and the specific timeout has passed. Otherwise the participant wins immediately
	 * 
	 * @return true or false
	 */
	public boolean hasTimeout()
	{
		switch(this)
		{
			case domination:
			case extermination:
				return true;
			case vendetta:         
			default:
				return false;
		}
	}
	
	/**
	 * Get the timeout factor for this victory condition.
	 * 
	 * @see Calculator#calculateVictoryTimeout(com.syncnapsis.data.model.Match)
	 * @return the timeout factor
	 */
	public double getTimeoutFactor()
	{
		switch(this)
		{
			case domination:
				return UniverseConquestConstants.PARAM_VICTORY_DOMINATION_TIMEOUT.asDouble();
			case extermination:
				return UniverseConquestConstants.PARAM_VICTORY_EXTERMINATION_TIMEOUT.asDouble();
			case vendetta:         
				return UniverseConquestConstants.PARAM_VICTORY_VENDETTA_TIMEOUT.asDouble();
			default:
				return 0.0;
		}
	}
}
