package com.syncnapsis.data.dao;

import com.syncnapsis.data.dao.hibernate.PlayerRankDaoHibernate;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.PlayerRank;
import com.syncnapsis.tests.BaseRankDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { PlayerRankDao.class, PlayerRankDaoHibernate.class })
public class PlayerRankDaoTest extends BaseRankDaoTestCase<PlayerRank, Player, Long>
{
	@Override
	protected void setUp() throws Exception
	{
		setRankDaoClass(PlayerRankDao.class);
		super.setUp();
	}
}
