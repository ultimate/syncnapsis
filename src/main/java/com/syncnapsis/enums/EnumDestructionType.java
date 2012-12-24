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
package com.syncnapsis.enums;

import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.SolarSystemPopulation;

/**
 * Enum representing different destruction types for {@link SolarSystemPopulation}s or
 * {@link Participant}s within a {@link Match}.<br>
 * <ul>
 * <li>A population can either be destroyed,
 * <ul>
 * <li>by another player (all population is lost)</li>
 * <li>when a colony is given up (all population is moved away)</li>
 * <li>when population decreased to 0 due to negative growth rate (no population left)</li>
 * </ul>
 * </li>
 * <li>A participant can either be destroyed,
 * <ul>
 * <li>by another player (all populations/systems are lost)</li>
 * <li>when he/she gives up (player leaves the game)</li>
 * <li>when the last existing population decreased to 0 due to negative growth rate (no population
 * left)</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author ultimate
 */
public enum EnumDestructionType
{
	/**
	 * totally destroyed by opponent(all population is lost)
	 */
	destroyed,
	/**
	 * a colony is given up by the player itself (all population is moved away)
	 */
	givenUp,
	/**
	 * population decreased to 0 due to negative growth rate (no population left)
	 */
	starved,
}
