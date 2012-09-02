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
