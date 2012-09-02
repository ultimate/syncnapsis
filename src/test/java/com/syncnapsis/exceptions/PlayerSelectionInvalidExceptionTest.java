package com.syncnapsis.exceptions;

import com.syncnapsis.data.service.PlayerManager;
import com.syncnapsis.tests.BaseDaoTestCase;

public class PlayerSelectionInvalidExceptionTest extends BaseDaoTestCase
{
	private PlayerManager playerManager;

	public void testAddSelfSitter() throws Exception
	{
		Long playerId1 = playerManager.getByUsername("user1").getId();
		
		try
		{
			playerManager.addSitter(playerId1, playerId1);
			fail("Expected exception not occurred!");
		}
		catch(PlayerSelectionInvalidException e)
		{
			assertNotNull(e);
		}
	}
}
