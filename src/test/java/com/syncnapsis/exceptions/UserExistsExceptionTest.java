package com.syncnapsis.exceptions;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.data.service.UserRoleManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import org.springframework.beans.BeanUtils;

public class UserExistsExceptionTest extends BaseDaoTestCase
{
	private UserManager userManager;
	private UserRoleManager userRoleManager;

	public void testAddExistingUser() throws Exception
	{
		logger.debug("testing add existing user...");
		assertNotNull(userManager);

		User user = userManager.getByName("user1");

		// create new object with null id - Hibernate doesn't like setId(null)
		User user2 = new User();
		BeanUtils.copyProperties(user, user2);
		user2.setId(null);
		user2.setVersion(null);
		user2.setRole(userRoleManager.getByName(ApplicationBaseConstants.ROLE_NORMAL_USER));
		user2.setMessengerContacts(null);
		user2.setUserContacts1(null);
		user2.setUserContacts2(null);

		// try saving as new user, this should fail b/c of unique keys
		try
		{
			logger.debug("Expecting Exception...");
			userManager.save(user2);
			fail("Duplicate user didn't throw UserExistsException");
		}
		catch(UserExistsException uee)
		{
			assertNotNull(uee);
		}
	}
}
