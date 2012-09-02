package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.PlayerRoleDao;
import com.syncnapsis.data.model.PlayerRole;
import com.syncnapsis.data.service.PlayerRoleManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { PlayerRoleManager.class, PlayerRoleManagerImpl.class })
public class PlayerRoleManagerImplTest extends GenericNameManagerImplTestCase<PlayerRole, Long, PlayerRoleManager, PlayerRoleDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new PlayerRole());
		setDaoClass(PlayerRoleDao.class);
		setMockDao(mockContext.mock(PlayerRoleDao.class));
		setMockManager(new PlayerRoleManagerImpl(mockDao));
	}
}
