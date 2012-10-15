package com.syncnapsis.exceptions;

import com.syncnapsis.data.service.PlayerManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class PlayerSittingNotPossibleExceptionTest extends BaseDaoTestCase
{
	private PlayerManager playerManager;

	@TestCoversMethods("get*")
	public void testAddFullSitter() throws Exception
	{
		Long playerId1 = playerManager.getByUsername("user1").getId();
		Long playerId2 = playerManager.getByUsername("user2").getId();
		
		try
		{
			playerManager.addSitter(playerId1, playerId2);
			fail("Expected exception not occurred!");
		}
		catch(PlayerSittingNotPossibleException e)
		{
			assertNotNull(e);
			assertEquals(playerId2, e.getPlayer().getId());
		}
	}

	@TestCoversMethods("get*")
	public void testAddFullSitted() throws Exception
	{
		Long playerId1 = playerManager.getByUsername("user1").getId();
		Long playerId2 = playerManager.getByUsername("user2").getId();
		
		try
		{
			playerManager.addSitter(playerId2, playerId1);
			fail("Expected exception not occurred!");
		}
		catch(PlayerSittingNotPossibleException e)
		{
			assertNotNull(e);
			assertEquals(playerId2, e.getPlayer().getId());
		}
	}
}
