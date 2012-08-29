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
