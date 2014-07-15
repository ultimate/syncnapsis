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

import com.syncnapsis.utils.constants.Constant;
import com.syncnapsis.utils.constants.StringConstant;

/**
 * Klasse f�r das Festhalten von Konstanten, die f�r die gesamte Applikation
 * g�ltig sind.<br/>
 * Durch die Verwendung von Konstanten, statt z.B. ausgeschriebener String
 * k�nnen Auswirkungen von Tippfehlern vermieden werden.
 * 
 * @author ultimate
 */
public class ApplicationBaseConstants
{
	/**
	 * Application configuration via DB: key for "length for generated action codes"
	 */
	public static final Constant<String>	PARAM_ACTION_CODE_LENGTH			= new StringConstant("action.code.length", "50");
	/**
	 * Application configuration via DB: key for "default session timeout"
	 */
	public static final Constant<String>	PARAM_SESSION_TIMEOUT_DEFAULT		= new StringConstant("session.timeout.default");
	/**
	 * Application configuration via DB: key for "default status on registration"
	 */
	public static final Constant<String>	PARAM_REGISTRATION_STATUS_DEFAULT	= new StringConstant("registration.status.default");
	/**
	 * Application configuration via DB: key for "time to verfiy e-mail after registration"
	 */
	public static final Constant<String>	PARAM_REGISTRATION_TIME_TO_VERIFY	= new StringConstant("registration.timeToVerify");
	/**
	 * Application configuration via DB: key for "time to verfiy e-mail after change"
	 */
	public static final Constant<String>	PARAM_EMAIL_CHANGE_TIME_TO_VERIFY	= new StringConstant("email.change.timeToVerify");
	/**
	 * Application configuration via DB: key for "time to verfiy a password reset after request"
	 */
	public static final Constant<String>	PARAM_PASSWORD_RESET_TIME_TO_VERIFY	= new StringConstant("password.reset.timeToVerify", "8");
	/**
	 * Application configuration via DB: key for "the length for generated passwords"
	 */
	public static final Constant<String>	PARAM_PASSWORD_GENERATED_LENGTH		= new StringConstant("password.generated.length");
	/**
	 * Namen der Spielparameter: Maximale Anzahl angezeigter News
	 */
	@Deprecated
	public static final Constant<String>	PARAM_NEWS_MAXITEMS					= new StringConstant("news.maxItems");
	/**
	 * Namen der Spielparameter: Maximales Alter der News mit Platzhalter f�r
	 * Abstufungen
	 */
	@Deprecated
	public static final Constant<String>	PARAM_NEWS_MAXAGE					= new StringConstant("news.%L.maxAge");

	/**
	 * bitmask: Administrator
	 */
	public static final int					ROLE_ADMIN							= 1;
	/**
	 * bitmask: Moderator
	 */
	public static final int					ROLE_MODERATOR						= 2;
	/**
	 * bitmask: Normaler User
	 */
	public static final int					ROLE_NORMAL_USER					= 4;
	/**
	 * bitmask: Demo-User
	 */
	public static final int					ROLE_DEMO_USER						= 8;

	/**
	 * Schl�ssel f�r die User-ID in der Session
	 */
	public static final String				SESSION_USER_KEY					= "j_user";
	/**
	 * Schl�ssel f�r den Infinite-Status in der Session. Dieser gibt an, ob die
	 * Session des Benutzers niemals abl�uft.
	 */
	public static final String				SESSION_INFINITE_KEY				= "j_infinite";
	/**
	 * Schl�ssel f�r den Timeout der Session. Dieser gibt an, wann ein Relogin f�llig ist, falls die
	 * Session nicht "infinite" ist.
	 */
	public static final String				SESSION_TIMEOUT_KEY					= "j_timeout";
	/**
	 * Schl�ssel f�r die Queue der letzten Logins.
	 */
	public static final String				SESSION_LAST_LOGINS_KEY				= "j_last_logins";
	/**
	 * Schl�ssel f�r die Locale in der Session
	 */
	public static final String				SESSION_LOCALE_KEY					= "j_locale";
	/**
	 * Schl�ssel f�r die Refresh-Status in der Session. Der Refresh-Status gibt
	 * an, ob derzeit das Passwort neu eigegeben werden muss, weil die Session
	 * des Benutzers abgelaufen ist.
	 */
	public static final String				SESSION_REFRESH_KEY					= "j_refresh";

	/**
	 * Maximale Anzahl der Relogin-Versuche. Nach dieser Anzahl von Versuchen
	 * wird der Benutzer automatisch ausgeloggt.
	 */
	public static final int					LOGIN_MAXFAILS						= 3;

	/**
	 * Error-Key for "invalid username"
	 */
	public static final String				ERROR_USERNAME_INVALID				= "error.invalid_username";
	/**
	 * Error-Key for "invalid username"
	 */
	public static final String				ERROR_EMAIL_INVALID					= "error.invalid_email";
	/**
	 * Error-Key for "no password"
	 */
	public static final String				ERROR_NO_PASSWORD					= "error.password_mismatch";
	/**
	 * Error-Key for "password mismatch"
	 */
	public static final String				ERROR_PASSWORD_MISMATCH				= "error.password_mismatch";
	/**
	 * Error-Key for "email exists"
	 */
	public static final String				ERROR_EMAIL_EXISTS					= "error.email_exists";
	/**
	 * Error-Key for "username exists"
	 */
	public static final String				ERROR_USERNAME_EXISTS				= "error.username_exists";
}
