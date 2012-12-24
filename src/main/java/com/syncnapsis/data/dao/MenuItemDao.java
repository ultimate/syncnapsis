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
package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.model.MenuItem;

/**
 * Dao-Interface für den Zugriff auf MenuItem
 * 
 * @author ultimate
 */
public interface MenuItemDao extends GenericDao<MenuItem, String>
{
	/**
	 * Lädt einen Menüeintrag und löst diesen ggf. von der Session, damit er
	 * manipulierbar ist, ohne gespeichert werden zu müssen.
	 * 
	 * @param id - die ID des Menüeintrags
	 * @param evict - soll das MenuItem-Objekt von der Session gelöst werden?
	 * @return das MenuItem-Objekt
	 */
	public MenuItem get(String id, boolean evict);

	/**
	 * Lädt die Submenüeinträge zu einem übergeordneten Menüeintrag.
	 * 
	 * @param parentId - die ID des übergeordneten Menüeintrags
	 * @param includeAdvanced - sollen erweiterte Menueinträge mit geladen
	 *            werden?
	 * @return die Liste der Submenüeinträge
	 */
	public List<MenuItem> getChildren(String parentId, boolean includeAdvanced);
}
