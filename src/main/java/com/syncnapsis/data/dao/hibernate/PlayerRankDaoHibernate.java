package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.PlayerRankDao;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.PlayerRank;

public class PlayerRankDaoHibernate extends GenericRankDaoHibernate<PlayerRank, Player, Long> implements PlayerRankDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse PlayerRank
	 */
	public PlayerRankDaoHibernate()
	{
		super(PlayerRank.class, ", entity.user.username asc");
	}
}
