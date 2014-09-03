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
 * Enum f�r die Spezifizierung der dynamischen Knoten eines Menu-Baums. Die
 * dynamischen Knoten werden beim Rendern des Baumes gesondert behandelt und mit
 * Parametern versehen und ggf. mehrfach mit verschiedenen Werten eingef�gt.
 * 
 * @author ultimate
 */
public enum EnumMenuItemDynamicType
{
	/**
	 * Elternknoten f�r eine Liste aus Spezialeintr�gen (Imperien/Allianzen etc.)
	 */
	list,
	/**
	 * Knoten f�r ein Eintrag innerhalb einer Liste
	 */
	option,
	/**
	 * Knoten f�r ein Eintrag innerhalb einer Liste, der gleichzeitig wieder eine Liste ist
	 * @see EnumMenuItemDynamicType#list
	 */
	option_list,
	/**
	 * Knoten unterhalb eines option-Knotens, der den Parameter erbt
	 */
	choice,
	/**
	 * Knoten mit einem aktuell ausgew�hlten Element als Parameter
	 */
	current;
}
