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
 * Enum für die Spezifizierung der Sichtweise auf die Galaxie für die
 * Darstellung einer Galaxie im GalaxyViewFrame.
 * 
 * @author ultimate
 */
public enum EnumGalaxyViewMode
{
	/**
	 * Sicht in x-Richtung auf die y-z-Ebene
	 */
	yz, 
	/**
	 * Sicht in y-Richtung auf die x-z-Ebene
	 */
	xz,
	/**
	 * Sicht in z-Richtung auf die x-y-Ebene
	 */
	xy;
}
