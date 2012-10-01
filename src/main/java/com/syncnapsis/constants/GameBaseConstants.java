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