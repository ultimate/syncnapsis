package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.model.MenuQuickLaunchItem;

/**
 * Dao-Interface für den Zugriff auf MenuQuickLaunchItem
 * 
 * @author ultimate
 */
public interface MenuQuickLaunchItemDao extends GenericDao<MenuQuickLaunchItem, Long>
{
	/**
	 * Lade eine Liste aller Schnellstart-Menüeinträge für einen Benutzer.
	 * 
	 * @param userId - die ID des Benutzers
	 * @return die Liste der Schnellstart-Menüeinträge
	 */
	public List<MenuQuickLaunchItem> getByUser(Long userId);
}
