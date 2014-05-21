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

import com.syncnapsis.data.model.MenuQuickLaunchItem;

/**
 * Manager-Interface für den Zugriff auf MenuQuickLaunchItem.
 * 
 * @author ultimate
 */
public interface MenuQuickLaunchItemManager extends GenericManager<MenuQuickLaunchItem, Long>
{

	/**
	 * Lade eine Liste aller Schnellstart-Menüeinträge für einen Benutzer.
	 * 
	 * @param userId - die ID des Benutzers
	 * @return die Liste der Schnellstart-Menüeinträge
	 */
	public List<MenuQuickLaunchItem> getByUser(Long userId);

	/**
	 * Fügt einen Menüeintrag in das Schnellstartmenü eines Benutzers an einer
	 * bestimmten Position ein.
	 * 
	 * @param userId - der Primärschlüssel des Benutzers
	 * @param miId - der Primärschlüssel des Menüeintrags
	 * @param qlPosTo - die Position für den Menüeintrag
	 * @return das aktualisierte Schnellstartmenü des Benutzers
	 */
	public List<MenuQuickLaunchItem> addQuickLaunchItem(Long userId, String miId, int qlPosTo);

	/**
	 * Löscht einen Menüeintrag aus dem Schnellstartmenü eines Benutzers von
	 * einer bestimmten Position
	 * 
	 * @param userId - der Primärschlüssel des Benutzers
	 * @param qlPosFrom - die Position des zu löschenden Menüeintrags
	 * @return das aktualisierte Schnellstartmenü des Benutzers
	 */
	public List<MenuQuickLaunchItem> removeQuickLaunchItem(Long userId, int qlPosFrom);

	/**
	 * Verschiebt einen Menüeintrag im Schnellstartmenü eines Benutzers von
	 * einer Position an eine andere
	 * 
	 * @param userId - der Primärschlüssel des Benutzers
	 * @param qlPosFrom - die Position von der der Menüeintrag entfernt werden
	 *            soll
	 * @param qlPosTo - die neue Position an der der Menüeintrag eingefügt
	 *            werden soll
	 * @return das aktualisierte Schnellstartmenü des Benutzers
	 */
	public List<MenuQuickLaunchItem> moveQuickLaunchItem(Long userId, int qlPosFrom, int qlPosTo);
}
