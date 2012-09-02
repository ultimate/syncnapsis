package com.syncnapsis.exceptions;

import com.syncnapsis.data.model.Player;

/**
 * Exception, die geworfen wird, wenn eine Sittingbeziehung erstellt werden soll, die bereits
 * vorhanden ist.
 * 
 * @author ultimate
 */
public class PlayerSittingExistsException extends RuntimeException
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Der bereits vorhandene Player
	 */
	private Player				player;

	/**
	 * Erzeugt eine neue RuntimeException mit gegebener Nachricht
	 * 
	 * @param message - die Nachricht
	 * @param Player - der bereits vorhandene Player
	 */
	public PlayerSittingExistsException(String message, Player player)
	{
		super(message);
		this.player = player;
	}

	/**
	 * Der bereits vorhandene Player
	 * 
	 * @return player
	 */
	public Player getPlayer()
	{
		return player;
	}
}
