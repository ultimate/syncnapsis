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
package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.PlayerRankDao;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.PlayerRank;
import com.syncnapsis.data.service.PlayerRankManager;

/**
 * Manager-Implementierung für den Zugriff auf PlayerRank.
 * 
 * @author ultimate
 */
public class PlayerRankManagerImpl extends GenericRankManagerImpl<PlayerRank, Player, Long> implements PlayerRankManager
{
	/**
	 * PlayerRankDao für den Datenbankzugriff
	 */
	@SuppressWarnings("unused")
	private PlayerRankDao playerRankDao;

	/**
	 * Standard-Constructor, der die DAO speichert
	 * 
	 * @param playerRankDao - die Dao
	 */
	public PlayerRankManagerImpl(PlayerRankDao playerRankDao)
	{
		super(playerRankDao);
		this.playerRankDao = playerRankDao;
	}

}
