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
package com.syncnapsis.constants;

/**
 * Klasse für das Festhalten von Konstanten, die für die gesamte Applikation
 * gültig sind.<br/>
 * Durch die Verwendung von Konstanten, statt z.B. ausgeschriebener String
 * können Auswirkungen von Tippfehlern vermieden werden.
 * 
 * @author ultimate
 */
public class ApplicationBaseConstants
{
	/**
	 * Namen der Spielparameter: Maximale Anzahl angezeigter News
	 */
	public static final String	PARAM_NEWS_MAXITEMS		= "news.maxItems";
	/**
	 * Namen der Spielparameter: Maximales Alter der News mit Platzhalter für
	 * Abstufungen
	 */
	public static final String	PARAM_NEWS_MAXAGE		= "news.%L.maxAge";

	/**
	 * Rollennamen: Administrator
	 */
	public static final String	ROLE_ADMIN				= "ADMIN";
	/**
	 * Rollennamen: Moderator
	 */
	public static final String	ROLE_MODERATOR			= "MODERATOR";
	/**
	 * Rollennamen: Normaler User
	 */
	public static final String	ROLE_NORMAL_USER		= "NORMAL_USER";
	/**
	 * Rollennamen: Demo-User
	 */
	public static final String	ROLE_DEMO_USER			= "DEMO_USER";

	/**
	 * Schlüssel für die User-ID in der Session
	 */
	public static final String	SESSION_USER_KEY		= "j_user";
	/**
	 * Schlüssel für den Infinite-Status in der Session. Dieser gibt an, ob die
	 * Session des Benutzers niemals abläuft.
	 */
	public static final String	SESSION_INFINITE_KEY	= "j_infinite";
	/**
	 * Schlüssel für den Timeout der Session. Dieser gibt an, wann ein Relogin fällig ist, falls die
	 * Session nicht "infinite" ist.
	 */
	public static final String	SESSION_TIMEOUT_KEY		= "j_timeout";
	/**
	 * Schlüssel für die Queue der letzten Logins.
	 */
	public static final String	SESSION_LAST_LOGINS_KEY	= "j_last_logins";
	/**
	 * Schlüssel für die Locale in der Session
	 */
	public static final String	SESSION_LOCALE_KEY		= "j_locale";
	/**
	 * Schlüssel für die Refresh-Status in der Session. Der Refresh-Status gibt
	 * an, ob derzeit das Passwort neu eigegeben werden muss, weil die Session
	 * des Benutzers abgelaufen ist.
	 */
	public static final String	SESSION_REFRESH_KEY		= "j_refresh";

	/**
	 * Maximale Anzahl der Relogin-Versuche. Nach dieser Anzahl von Versuchen
	 * wird der Benutzer automatisch ausgeloggt.
	 */
	public static final int		LOGIN_MAXFAILS			= 3;

	/**
	 * Error-Key for "invalid username"
	 */
	public static final String	ERROR_USERNAME_INVALID	= "error.invalid_username";
	/**
	 * Error-Key for "no password"
	 */
	public static final String	ERROR_NO_PASSWORD		= "error.password_mismatch";
	/**
	 * Error-Key for "password mismatch"
	 */
	public static final String	ERROR_PASSWORD_MISMATCH	= "error.password_mismatch";
	/**
	 * Error-Key for "email exists"
	 */
	public static final String	ERROR_EMAIL_EXISTS		= "error.email_exists";
	/**
	 * Error-Key for "username exists"
	 */
	public static final String	ERROR_USERNAME_EXISTS	= "error.username_exists";
}
