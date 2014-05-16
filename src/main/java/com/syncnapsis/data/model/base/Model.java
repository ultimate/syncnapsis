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
package com.syncnapsis.data.model.base;

/**
 * Interface zur Indentifizierung von Model-Klassen. Auf diese Weise sind in
 * allen Model-Klassen die unten definierten Konstanten für die Spaltenlängen in
 * der Datenbank bekannt.
 * Desweiteren können so in den Tests Model-Klassen von anderen Klassen
 * unterschieden werden. Bei der Vollständigkeitsprüfung der Tests werden die
 * Model-Klassen ausgeschlossen.
 * 
 * @author ultimate
 */
public interface Model
{
	/**
	 * Standard-Länge für kurze Namen
	 */
	public static final int	LENGTH_NAME_SHORT		= 10;
	/**
	 * Standard-Länge für normale Namen
	 */
	public static final int	LENGTH_NAME_NORMAL		= 30;
	/**
	 * Standard-Länge für lange Namen
	 */
	public static final int	LENGTH_NAME_LONG		= 100;
	/**
	 * Standard-Länge für lange Namen
	 */
	public static final int	LENGTH_NAME_VERYLONG	= 200;

	/**
	 * Standard-Länge für Passwörter
	 */
	public static final int	LENGTH_PASSWORD			= 200;
	/**
	 * Standard-Länge für e-Mail-Adressen
	 */
	public static final int	LENGTH_EMAIL			= 200;
	/**
	 * Standard-Länge für URLs
	 */
	public static final int	LENGTH_URL				= 200;
	/**
	 * Standard-Länge für Beschreibungen
	 */
	public static final int	LENGTH_DESCRIPTION		= 2000;
	/**
	 * Standard-Länge für freie Titel
	 */
	public static final int	LENGTH_TITLE			= 200;
	/**
	 * Standard-Länge für freie Texte
	 */
	public static final int	LENGTH_TEXT				= 2000;

	/**
	 * Standard-Länge für ID
	 */
	public static final int	LENGTH_ID				= 50;
	/**
	 * Standard-Länge für Action-Namen
	 */
	public static final int	LENGTH_ACTION			= 50;
	/**
	 * Standard-Länge für Parameter-Namen
	 */
	public static final int	LENGTH_PARAMETER		= 50;
	/**
	 * Standard-Länge für Parameter-Werte
	 */
	public static final int	LENGTH_PARAMETERVALUE	= 50;

	/**
	 * Standard-Länge für Enum-Werte
	 */
	public static final int	LENGTH_ENUM				= 50;

	/**
	 * Standard-Länge für Sprach-Schlüssel
	 */
	public static final int	LENGTH_LANGUAGE_KEY		= 100;
}
