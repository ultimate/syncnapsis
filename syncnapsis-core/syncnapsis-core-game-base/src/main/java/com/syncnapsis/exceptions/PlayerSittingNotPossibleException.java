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
