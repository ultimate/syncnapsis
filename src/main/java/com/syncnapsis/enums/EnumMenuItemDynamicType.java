package com.syncnapsis.enums;

/**
 * Enum für die Spezifizierung der dynamischen Knoten eines Menu-Baums. Die
 * dynamischen Knoten werden beim Rendern des Baumes gesondert behandelt und mit
 * Parametern versehen und ggf. mehrfach mit verschiedenen Werten eingefügt.
 * 
 * @author ultimate
 */
public enum EnumMenuItemDynamicType
{
	/**
	 * Elternknoten für eine Liste aus Spezialeinträgen (Imperien/Allianzen etc.)
	 */
	list,
	/**
	 * Knoten für ein Eintrag innerhalb einer Liste
	 */
	option,
	/**
	 * Knoten für ein Eintrag innerhalb einer Liste, der gleichzeitig wieder eine Liste ist
	 * @see EnumMenuItemDynamicType#list
	 */
	option_list,
	/**
	 * Knoten unterhalb eines option-Knotens, der den Parameter erbt
	 */
	choice,
	/**
	 * Knoten mit einem aktuell ausgewählten Element als Parameter
	 */
	current;
}
