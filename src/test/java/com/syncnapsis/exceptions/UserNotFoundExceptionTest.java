package com.syncnapsis.exceptions;

import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.tests.BaseDaoTestCase;

public class UserNotFoundExceptionTest extends BaseDaoTestCase
{
	private UserManager userManager;

	public void testUserNotFound() throws Exception
	{
		logger.debug("testing user not found...");
		assertNotNull(userManager);

		// try getting a non-existing user
		try
		{
			logger.debug("Expecting Exception...");
			userManager.getByName("asdkgljashdgf");
			fail("Unknown user didn't throw UserNotFoundException");
		}
		catch(UserNotFoundException uee)
		{
			assertNotNull(uee);
		}
	}
}
