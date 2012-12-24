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
 * Enum für die Spezifizierung der Haupttypen der Knoten eines Menu-Baums. Die
 * Knoten werden beim Rendern des Baumes gesondert behandelt und auf
 * verschiedene Weise dargestellt.
 * 
 * @author ultimate
 */
public enum EnumMenuItemType
{
	/**
	 * Wurzelknoten. Dient als Ausgangspunkt für das Laden eines Baumes
	 */
	root,
	/**
	 * Knoten. Gewöhnliches Element innerhalb des Baumes
	 */
	node,
	/**
	 * Trennelement. Dient der Formatierung des Baumes und der optischen
	 * Trennung mehrere Menübereiche der gleichen Ebene von einander.
	 */
	separator;
}
