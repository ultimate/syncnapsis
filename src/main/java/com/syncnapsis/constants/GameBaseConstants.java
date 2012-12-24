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
public class GameBaseConstants
{
	/**
	 * Rollennamen: Premium-User
	 */
	public static final String	ROLE_PREMIUM_PLAYER	= "PREMIUM_PLAYER";
	/**
	 * Rollennamen: Normaler User
	 */
	public static final String	ROLE_NORMAL_PLAYER	= "NORMAL_PLAYER";
	/**
	 * Rollennamen: Demo-User
	 */
	public static final String	ROLE_DEMO_PLAYER	= "DEMO_PLAYER";
	/**
	 * Schlüssel für die Spieler-ID in der Session
	 */
	public static final String	SESSION_PLAYER_KEY	= "j_player";
	/**
	 * Schlüssel für die Imperiums-ID in der Session
	 */
	public static final String	SESSION_EMPIRE_KEY	= "j_empire";
}