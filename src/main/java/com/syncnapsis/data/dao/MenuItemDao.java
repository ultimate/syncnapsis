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
