package com.syncnapsis.enums;

/**
 * Enum für die Spezifizierung der Haupttypen der Knoten eines Menu-Baums. Die
 * Knoten werden beim Rendern des Baumes gesondert behandelt und auf
 * verschiedene Weise dargestellt.
 * 
 * @author ultimate
 */
public enum EnumMenuItemType
{
	/**
	 * Wurzelknoten. Dient als Ausgangspunkt für das Laden eines Baumes
	 */
	root,
	/**
	 * Knoten. Gewöhnliches Element innerhalb des Baumes
	 */
	node,
	/**
	 * Trennelement. Dient der Formatierung des Baumes und der optischen
	 * Trennung mehrere Menübereiche der gleichen Ebene von einander.
	 */
	separator;
}
