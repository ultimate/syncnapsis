/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
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
package com.syncnapsis.providers.impl;

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.tests.LoggerTestCase;

public class UserRoleBasedAuthorityProviderTest extends LoggerTestCase
{
	public void testGet() throws Exception
	{
		SessionProvider sp = new ThreadLocalSessionProvider();
		SessionBasedUserProvider up = new SessionBasedUserProvider();
		UserRoleBasedAuthorityProvider ap = new UserRoleBasedAuthorityProvider(up);
		up.setSessionProvider(sp);
		sp.set(new MockHttpSession());

		UserRole role = new UserRole();
		role.setRolename("role1");

		User user = new User();
		user.setUsername("user1");
		user.setRole(role);

		up.set(user);

		Object[] authorities = ap.get();

		assertEquals(2, authorities.length);
		assertEquals(user, authorities[0]);
		assertEquals(role, authorities[1]);
	}
	
	public void testSet() throws Exception
	{

		SessionProvider sp = new ThreadLocalSessionProvider();
		SessionBasedUserProvider up = new SessionBasedUserProvider();
		UserRoleBasedAuthorityProvider ap = new UserRoleBasedAuthorityProvider(up);
		up.setSessionProvider(sp);
		sp.set(new MockHttpSession());

		UserRole role = new UserRole();
		role.setRolename("role1");

		User user = new User();
		user.setUsername("user1");
		user.setRole(role);

		up.set(user);
		
		try
		{
			ap.set(new Object[0]);
			fail("expected Exception not occurred!");
		}
		catch(UnsupportedOperationException e)
		{
			assertNotNull(e);
		}
	}
}
