package com.syncnapsis.enums;

import java.util.Locale;

/**
 * Enum für die Spezifizierung der Sprache für die Darstellung der Oberfläche.
 * Jeder User kann seine Sprache aus dieser Liste auswählen. Auf diese Weise ist
 * man nicht von der Locale-Einstellung des Browsers abhängig und es wird
 * zugleich garantiert, dass nur Sprachen ausgewählt werden können, für die auch
 * eine Übersetzung existiert.
 * Für die Korrekte Benutzung von valueOfIgnoreCase(String name), sollten alle
 * Namen der Enum-Werte nur aus Großbuchstaben bestehen.
 * 
 * @author ultimate
 */
public enum EnumLocale
{
	/**
	 * Englisch (default)
	 */
	EN,
	/**
	 * Deutsch
	 */
	DE;

	/**
	 * Wandelt dieses Enum in eine Locale um. Es wird ein Locale-Object mit
	 * Hilfe von toString() erzeugt.
	 * 
	 * @return die Locale zu diesem Enum
	 */
	public Locale getLocale()
	{
		return new Locale(this.toString());
	}

	/**
	 * Eine Variante von Enum.valueOf(String name), nur case-insensitiv. Dazu
	 * wird der Name nach Upper-Case transformiert, da in dieser Klasse alle
	 * Werte nur aus Großbuchstaben bestehen.
	 * 
	 * @param name - der nachzuschlagende Name der EnumLocale
	 * @return das zugehörige Enum zum gegebenen Namen
	 */
	public static EnumLocale valueOfIgnoreCase(String name)
	{
		return valueOf(name.toUpperCase(getDefault().getLocale()));
	}

	/**
	 * Gibt die default Locale zurück: EN
	 * 
	 * @return die default Locale
	 */
	public static EnumLocale getDefault()
	{
		return EN;
	}
	
	/**
	 * Returns the available Locales represented in EnumLocale as Locale Objects.
	 * 
	 * @see EnumLocale#values()
	 * @return the Array of Locales
	 */
	public static Locale[] localeValues()
	{
		EnumLocale[] enumLocales = values();
		Locale[] locales = new Locale[enumLocales.length];
		for(int i = 0; i < enumLocales.length; i++)
			locales[i] = enumLocales[i].getLocale();
		return locales;
	}
}
