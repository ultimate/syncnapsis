package com.syncnapsis.exceptions;

import com.syncnapsis.constants.BaseGameConstants;
import com.syncnapsis.data.model.PlayerRole;
import com.syncnapsis.data.service.PlayerRoleManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import org.springframework.beans.BeanUtils;

public class PlayerRoleExistsExceptionTest extends BaseDaoTestCase
{
	private PlayerRoleManager playerRoleManager;

	public void testAddExistingPlayerRole() throws Exception
	{
		logger.debug("testing add existing playerRole...");
		assertNotNull(playerRoleManager);

		PlayerRole playerRole = playerRoleManager.getByName(BaseGameConstants.ROLE_DEMO_PLAYER);

		// create new object with null id - Hibernate doesn't like setId(null)
		PlayerRole playerRole2 = new PlayerRole();
		BeanUtils.copyProperties(playerRole, playerRole2);
		playerRole2.setId(10000L);
		playerRole2.setVersion(null);

		// try saving as new playerRole, this should fail b/c of unique keys
		try
		{
			logger.debug("Expecting Exception...");
			playerRoleManager.save(playerRole2);
			fail("Duplicate playerRole didn't throw PlayerRoleExistsException");
		}
		catch(PlayerRoleExistsException uee)
		{
			assertNotNull(uee);
		}
	}
}
