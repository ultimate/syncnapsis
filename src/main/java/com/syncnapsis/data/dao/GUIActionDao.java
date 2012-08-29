package com.syncnapsis.data.dao;

import com.syncnapsis.data.model.GUIAction;

/**
 * Dao-Interface für den Zugriff auf GUIAction
 * 
 * @author ultimate
 */
public interface GUIActionDao extends GenericNameDao<GUIAction, Long>
{
	/**
	 * Lädt eine GUIAction anhand ihrer WindowId.
	 * 
	 * @param windowId - der WindowId
	 * @return das GUIAction-Objekt
	 */
	public GUIAction getByWindowId(String windowId);
}
