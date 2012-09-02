package com.syncnapsis.exceptions;

import com.syncnapsis.data.model.Player;

/**
 * Exception, die geworfen wird, wenn ein Player erstellt werden soll, der bereits vorhanden
 * ist.
 * 
 * @author ultimate
 */
public class PlayerSittingNotPossibleException extends RuntimeException
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Der betroffene Player, bei dem die Plätze nicht mehr ausreichen
	 */
	private Player			player;

	/**
	 * Erzeugt eine neue RuntimeException mit gegebener Nachricht
	 * 
	 * @param message - die Nachricht
	 * @param player - Der betroffene Player, bei dem die Plätze nicht mehr ausreichen
	 */
	public PlayerSittingNotPossibleException(String message, Player player)
	{
		super(message);
		this.player = player;
	}

	/**
	 * Der betroffene Player, bei dem die Plätze nicht mehr ausreichen
	 * 
	 * @return player
	 */
	public Player getPlayer()
	{
		return player;
	}
}
