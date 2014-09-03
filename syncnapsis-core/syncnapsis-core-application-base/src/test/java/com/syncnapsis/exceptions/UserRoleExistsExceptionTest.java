/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
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

		UserRole userRole = userRoleManager.getByMask(ApplicationBaseConstants.ROLE_DEMO_USER);

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
