/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
