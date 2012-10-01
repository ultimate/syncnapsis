package com.syncnapsis.exceptions;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.data.service.UserRoleManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import org.springframework.beans.BeanUtils;

public class UserRoleExistsExceptionTest extends BaseDaoTestCase
{
	private UserRoleManager userRoleManager;

	public void testAddExistingUserRole() throws Exception
	{
		logger.debug("testing add existing userRole...");
		assertNotNull(userRoleManager);

		UserRole userRole = userRoleManager.getByName(ApplicationBaseConstants.ROLE_DEMO_USER);

		// create new object with null id - Hibernate doesn't like setId(null)
		UserRole userRole2 = new UserRole();
		BeanUtils.copyProperties(userRole, userRole2);
		userRole2.setId(10000L);
		userRole2.setVersion(null);

		// try saving as new userRole, this should fail b/c of unique keys
		try
		{
			logger.debug("Expecting Exception...");
			userRoleManager.save(userRole2);
			fail("Duplicate userRole didn't throw UserRoleExistsException");
		}
		catch(UserRoleExistsException uee)
		{
			assertNotNull(uee);
		}
	}
}
