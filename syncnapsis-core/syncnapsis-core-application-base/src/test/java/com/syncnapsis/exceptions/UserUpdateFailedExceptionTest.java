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

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.UserProvider;
import com.syncnapsis.tests.BaseDaoTestCase;

public class UserUpdateFailedExceptionTest extends BaseDaoTestCase
{
	private UserManager		userManager;
	private SessionProvider	sessionProvider;
	private UserProvider	userProvider;

	public void testChangePasswordInvalid() throws Exception
	{
		sessionProvider.set(new MockHttpSession());
		try
		{
			// no password
			userManager.changePassword("a", null, null);
			fail("expected Exception not occurred!");
		}
		catch(UserUpdateFailedException e)
		{
			assertNotNull(e);
		}
		try
		{
			// passwords not matching
			userManager.changePassword("a", "b", "c");
			fail("expected Exception not occurred!");
		}
		catch(UserUpdateFailedException e)
		{
			assertNotNull(e);
		}
		try
		{
			// no current user
			userManager.changePassword("a", "b", "b");
			fail("expected Exception not occurred!");
		}
		catch(UserUpdateFailedException e)
		{
			assertNotNull(e);
		}
		User user = userManager.getByName("admin");
		userProvider.set(user);
		try
		{
			// old password not correct
			userManager.changePassword("falsepw", "b", "b");
			fail("expected Exception not occurred!");
		}
		catch(UserUpdateFailedException e)
		{
			assertNotNull(e);
		}
	}
}
