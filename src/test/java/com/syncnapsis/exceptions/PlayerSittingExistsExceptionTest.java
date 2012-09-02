package com.syncnapsis.exceptions;

import com.syncnapsis.data.service.PlayerManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class PlayerSittingExistsExceptionTest extends BaseDaoTestCase
{
	private PlayerManager playerManager;

	@TestCoversMethods("get*")
	public void testAddExistingSitter() throws Exception
	{
		Long playerId1 = playerManager.getByUsername("user2").getId();
		Long playerId2 = playerManager.getByUsername("user25").getId();
		
		try
		{
			playerManager.addSitter(playerId1, playerId2);
			fail("Expected exception not occurred!");
		}
		catch(PlayerSittingExistsException e)
		{
			assertNotNull(e);
			assertEquals(playerId2, e.getPlayer().getId());
		}
	}
}
