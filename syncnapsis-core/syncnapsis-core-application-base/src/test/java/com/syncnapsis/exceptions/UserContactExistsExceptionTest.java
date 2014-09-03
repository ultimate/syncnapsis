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
		Long userId1 = userManager.getByName("user1").getId();
		Long userId2 = userManager.getByName("user2").getId();
		
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
