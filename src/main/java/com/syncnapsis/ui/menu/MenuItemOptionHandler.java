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
package com.syncnapsis.ui.menu;

import java.util.List;

import com.syncnapsis.data.model.MenuItem;

/**
 * Klasse, die die Erstellung von Submenüs für bestimmte Typen behandelt.
 * 
 * @author ultimate
 */
public interface MenuItemOptionHandler
{
	/**
	 * Soll dieser Handler angewendet werden?
	 * 
	 * @param dynamicSubType - der dynamicSubType
	 * @return true oder false
	 */
	public boolean applies(String dynamicSubType);

	/**
	 * Behandelt die Erstellung des Submenüs
	 * 
	 * @param menuItem - der Menüeintrag für das Submenü
	 * @return die Liste der erstellen Menüpunkte
	 */
	public List<MenuItem> createOptions(MenuItem menuItem);

	/**
	 * Behandelt die Erstellung eines ausgewählten Eintrags
	 * 
	 * @param menuItem - der Menüeintrag für das Submenü
	 * @return die Liste der erstellen Menüpunkte
	 */
	public List<MenuItem> createCurrent(MenuItem menuItem);

	/**
	 * Gibt die Bezeichnung für einen Menüeintrag zurück
	 * 
	 * @param menuItem - der Menueintrag
	 * @return die Bezeichnung
	 */
	public String getLabel(MenuItem menuItem);
}
