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
	vendetta,
	// some more?
}
