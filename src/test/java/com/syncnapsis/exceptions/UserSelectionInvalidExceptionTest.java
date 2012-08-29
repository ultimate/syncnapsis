package com.syncnapsis.exceptions;

import com.syncnapsis.data.service.UserContactManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.tests.BaseDaoTestCase;

public class UserSelectionInvalidExceptionTest extends BaseDaoTestCase
{
	private UserManager userManager;
	private UserContactManager userContactManager;

	public void testAddSelfContact() throws Exception
	{
		Long userId1 = userManager.getByName("user1").getId();
		
		try
		{
			userContactManager.addUserContact(userId1, userId1);
			fail("expected exception not occurred");
		}
		catch(UserSelectionInvalidException e)
		{
			assertNotNull(e);
		}		
	}
}
