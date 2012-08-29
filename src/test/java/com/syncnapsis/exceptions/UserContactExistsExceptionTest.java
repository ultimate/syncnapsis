package com.syncnapsis.exceptions;

import com.syncnapsis.data.service.UserContactManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class UserContactExistsExceptionTest extends BaseDaoTestCase
{
	private UserManager userManager;
	private UserContactManager userContactManager;

	@TestCoversMethods("get*")
	public void testAddExistingUserContact() throws Exception
	{
		Long userId1 = userManager.getByName("user2").getId();
		Long userId2 = userManager.getByName("user12").getId();
		
		try
		{
			userContactManager.addUserContact(userId1, userId2);
			fail("expected exception not occurred");
		}
		catch(UserContactExistsException e)
		{
			assertNotNull(e);
			assertNotNull(e.getUserContact());
			assertEquals(userId1, e.getUserContact().getUser1().getId());
			assertEquals(userId2, e.getUserContact().getUser2().getId());
		}
	}
}
