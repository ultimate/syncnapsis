package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.PlayerRankDao;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.PlayerRank;
import com.syncnapsis.data.service.PlayerRankManager;
import com.syncnapsis.tests.BaseRankManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { PlayerRankManager.class, PlayerRankManagerImpl.class })
public class PlayerRankManagerImplTest extends BaseRankManagerImplTestCase<PlayerRank, Player, Long, PlayerRankManager, PlayerRankDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new PlayerRank());
		setDaoClass(PlayerRankDao.class);
		setMockDao(mockContext.mock(PlayerRankDao.class));
		setMockManager(new PlayerRankManagerImpl(mockDao));
	}
}
