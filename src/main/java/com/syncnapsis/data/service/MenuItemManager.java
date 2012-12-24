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
package com.syncnapsis.data.service;

import java.util.List;

import com.syncnapsis.data.model.MenuItem;
import com.syncnapsis.ui.menu.MenuItemOptionHandler;
import com.syncnapsis.utils.graphs.GenericTreeModel;

/**
 * Manager-Interface für den Zugriff auf MenuItem.
 * 
 * @author ultimate
 */
public interface MenuItemManager extends GenericManager<MenuItem, String>
{
	/**
	 * Fügt einen MenuItemOptionHandler zur Liste hinzu
	 * 
	 * @param optionHandler - der MenuItemOptionHandler
	 */
	public void addOptionHandler(MenuItemOptionHandler optionHandler);
	
	/**
	 * Die Liste der MenuItemOptionHandler
	 * 
	 * @return die Liste
	 */
	public List<MenuItemOptionHandler> getOptionHandlers();
	
	/**
	 * Lädt eine Liste aller Untermenüeinträge zu einem Menüeintrag
	 * 
	 * @param parentId - der Primärschlüssel des Elternmenüeintrags
	 * @param includeAdvanced - sollen auch erweiterte Einträge geladen werden
	 * @return - die Liste
	 */
	public List<MenuItem> getChildren(String parentId, boolean includeAdvanced);
	
	/**
	 * Lädt einen vollständigen Menü-Baum, ausgehend von einem Menüeintrag
	 * 
	 * @param id - der Primärschlüssel des obersten Menüeintrags
	 * @param includeAdvanced - sollen auch erweiterte Einträge geladen werden
	 * @return der oberste Menüeintrag mit allen Kindern
	 */
	public MenuItem getFullMenu(String id, boolean includeAdvanced);
	
	/**
	 * Lädt eine Liste aller Untermenüeinträge zu einem Menüeintrag und hängt
	 * diese an den Menüeintrag an.
	 * Es werden hierbei dynamische Menüeinträge behandelt und ggf. mehrfach
	 * eingefügt
	 * 
	 * @param parent - der Elternmenüeintrags
	 * @param includeAdvanced - sollen auch erweiterte Einträge geladen werden
	 */
	public void attachChildren(MenuItem parent, boolean includeAdvanced);
	
	/**
	 * Lädt einen vollständigen Menü-Baum, ausgehend von einem Menüeintrag und
	 * gibt diesen als TreeModel zurück. inculdeAdvanced wird dabei aus den
	 * Benutzereinstellungen des aktuellen Benutzers geladen
	 * 
	 * @see com.syncnapsis.data.service.MenuManager#getFullMenu(String, boolean)
	 * @param id - der Primärschlüssel des obersten Menüeintrags
	 * @param userId - der Benutzer
	 * @return der oberste Menüeintrag mit allen Kindern
	 */
	public GenericTreeModel<MenuItem> getMenuAsTree(String key);
	
	/**
	 * Lade einen Menüeintrag anhand des Primärschlüssels und löst diesen ggf.
	 * aus dem Hibernate-Cache
	 * 
	 * @see com.syncnapsis.service.GenericManager#get(java.io.Serializable)
	 * @param id - der Primärschlüssels
	 * @param evict - soll der Menüeintrag aus dem Hibernate-Chache gelöst
	 *            werden
	 * @return der Menüeintrag
	 */
	public MenuItem get(String id, boolean evict);
}
