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
package com.syncnapsis.constants;

/**
 * Klasse f�r das Festhalten von Konstanten, die f�r die gesamte Applikation
 * g�ltig sind.<br/>
 * Durch die Verwendung von Konstanten, statt z.B. ausgeschriebener String
 * k�nnen Auswirkungen von Tippfehlern vermieden werden.
 * 
 * @author ultimate
 */
public class GameBaseConstants
{
	/**
	 * Unique bit mask: Premium-User
	 */
	public static final int	ROLE_PREMIUM_PLAYER			= 512;
	/**
	 * Unique bit mask: Normaler User
	 */
	public static final int ROLE_NORMAL_PLAYER			= 1024;
	/**
	 * Unique bit mask: Demo-User
	 */
	public static final int ROLE_DEMO_PLAYER			= 2048;
	/**
	 * Schl�ssel f�r die Spieler-ID in der Session
	 */
	public static final String	SESSION_PLAYER_KEY			= "j_player";
	/**
	 * Schl�ssel f�r die Imperiums-ID in der Session
	 */
	public static final String	SESSION_EMPIRE_KEY			= "j_empire";
	/**
	 * Error-Key for "invalid empirename"
	 */
	public static final String	ERROR_EMPIRENAME_INVALID	= "error.invalid_empirename";
	/**
	 * Error-Key for "max empires reached"
	 */
	public static final String	ERROR_MAXEMPIRES			= "error.max_empires";
}