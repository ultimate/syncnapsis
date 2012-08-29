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

		assertEquals(3, authorities.length);
		assertEquals(user, authorities[0]);
		assertEquals(role, authorities[1]);
		assertEquals(role.getRolename(), authorities[2]);
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
