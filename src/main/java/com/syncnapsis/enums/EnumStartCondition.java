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

/**
 * Enum representing differing start conditions for matches.<br>
 * The condition is used to decide when a match will be started.
 * 
 * @author ultimate
 */
public enum EnumStartCondition
{
	/**
	 * The match must be started manually by the creator
	 */
	manually,
	/**
	 * The match will be started automatically when the maximum number of participants is reached
	 * unless it has not been started manually before that.
	 */
	maxParticipantsReached,
	/**
	 * The match will start at a planned date and time.
	 */
	planned,
	/**
	 * The match will start at a planned date and time if the minimum number of participants is
	 * reached. If there are less participants, the match will wait for a manual start or the
	 * minimum number of participants to be reached.
	 */
	plannedAndMinParticipantsReached,
	/**
	 * The match will start immediately
	 */
	immediately
}
