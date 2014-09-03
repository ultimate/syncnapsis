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
package com.syncnapsis.enums;

import java.util.Locale;

/**
 * Enum f�r die Spezifizierung der Sprache f�r die Darstellung der Oberfl�che.
 * Jeder User kann seine Sprache aus dieser Liste ausw�hlen. Auf diese Weise ist
 * man nicht von der Locale-Einstellung des Browsers abh�ngig und es wird
 * zugleich garantiert, dass nur Sprachen ausgew�hlt werden k�nnen, f�r die auch
 * eine �bersetzung existiert.
 * F�r die Korrekte Benutzung von valueOfIgnoreCase(String name), sollten alle
 * Namen der Enum-Werte nur aus Gro�buchstaben bestehen.
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
	 * Werte nur aus Gro�buchstaben bestehen.
	 * 
	 * @param name - der nachzuschlagende Name der EnumLocale
	 * @return das zugeh�rige Enum zum gegebenen Namen
	 */
	public static EnumLocale valueOfIgnoreCase(String name)
	{
		return valueOf(name.toUpperCase(getDefault().getLocale()));
	}

	/**
	 * Gibt die default Locale zur�ck: EN
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
