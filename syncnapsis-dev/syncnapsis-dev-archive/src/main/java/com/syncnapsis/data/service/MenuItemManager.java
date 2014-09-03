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
package com.syncnapsis.data.service;

import java.util.List;

import com.syncnapsis.data.model.MenuItem;
import com.syncnapsis.ui.menu.MenuItemOptionHandler;
import com.syncnapsis.utils.graphs.GenericTreeModel;

/**
 * Manager-Interface f�r den Zugriff auf MenuItem.
 * 
 * @author ultimate
 */
public interface MenuItemManager extends GenericManager<MenuItem, String>
{
	/**
	 * F�gt einen MenuItemOptionHandler zur Liste hinzu
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
	 * L�dt eine Liste aller Untermen�eintr�ge zu einem Men�eintrag
	 * 
	 * @param parentId - der Prim�rschl�ssel des Elternmen�eintrags
	 * @param includeAdvanced - sollen auch erweiterte Eintr�ge geladen werden
	 * @return - die Liste
	 */
	public List<MenuItem> getChildren(String parentId, boolean includeAdvanced);
	
	/**
	 * L�dt einen vollst�ndigen Men�-Baum, ausgehend von einem Men�eintrag
	 * 
	 * @param id - der Prim�rschl�ssel des obersten Men�eintrags
	 * @param includeAdvanced - sollen auch erweiterte Eintr�ge geladen werden
	 * @return der oberste Men�eintrag mit allen Kindern
	 */
	public MenuItem getFullMenu(String id, boolean includeAdvanced);
	
	/**
	 * L�dt eine Liste aller Untermen�eintr�ge zu einem Men�eintrag und h�ngt
	 * diese an den Men�eintrag an.
	 * Es werden hierbei dynamische Men�eintr�ge behandelt und ggf. mehrfach
	 * eingef�gt
	 * 
	 * @param parent - der Elternmen�eintrags
	 * @param includeAdvanced - sollen auch erweiterte Eintr�ge geladen werden
	 */
	public void attachChildren(MenuItem parent, boolean includeAdvanced);
	
	/**
	 * L�dt einen vollst�ndigen Men�-Baum, ausgehend von einem Men�eintrag und
	 * gibt diesen als TreeModel zur�ck. inculdeAdvanced wird dabei aus den
	 * Benutzereinstellungen des aktuellen Benutzers geladen
	 * 
	 * @see com.syncnapsis.data.service.MenuManager#getFullMenu(String, boolean)
	 * @param id - der Prim�rschl�ssel des obersten Men�eintrags
	 * @param userId - der Benutzer
	 * @return der oberste Men�eintrag mit allen Kindern
	 */
	public GenericTreeModel<MenuItem> getMenuAsTree(String key);
	
	/**
	 * Lade einen Men�eintrag anhand des Prim�rschl�ssels und l�st diesen ggf.
	 * aus dem Hibernate-Cache
	 * 
	 * @see com.syncnapsis.service.GenericManager#get(java.io.Serializable)
	 * @param id - der Prim�rschl�ssels
	 * @param evict - soll der Men�eintrag aus dem Hibernate-Chache gel�st
	 *            werden
	 * @return der Men�eintrag
	 */
	public MenuItem get(String id, boolean evict);
}
