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
