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
package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.model.MenuItem;

/**
 * Dao-Interface f�r den Zugriff auf MenuItem
 * 
 * @author ultimate
 */
public interface MenuItemDao extends GenericDao<MenuItem, String>
{
	/**
	 * L�dt einen Men�eintrag und l�st diesen ggf. von der Session, damit er
	 * manipulierbar ist, ohne gespeichert werden zu m�ssen.
	 * 
	 * @param id - die ID des Men�eintrags
	 * @param evict - soll das MenuItem-Objekt von der Session gel�st werden?
	 * @return das MenuItem-Objekt
	 */
	public MenuItem get(String id, boolean evict);

	/**
	 * L�dt die Submen�eintr�ge zu einem �bergeordneten Men�eintrag.
	 * 
	 * @param parentId - die ID des �bergeordneten Men�eintrags
	 * @param includeAdvanced - sollen erweiterte Menueintr�ge mit geladen
	 *            werden?
	 * @return die Liste der Submen�eintr�ge
	 */
	public List<MenuItem> getChildren(String parentId, boolean includeAdvanced);
}
