package com.syncnapsis.providers.impl;

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.PlayerRole;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.tests.BaseSpringContextTestCase;

public class PlayerRoleBasedAuthorityProviderTest extends BaseSpringContextTestCase
{
	public void testGet() throws Exception
	{
		SessionProvider sp = new ThreadLocalSessionProvider();
		SessionBasedPlayerProvider pp = new SessionBasedPlayerProvider();
		PlayerRoleBasedAuthorityProvider ap = new PlayerRoleBasedAuthorityProvider(pp);
		pp.setSessionProvider(sp);
		sp.set(new MockHttpSession());
		
		UserRole userRole = new UserRole();
		userRole.setRolename("userrole1");
		
		User user = new User();
		user.setRole(userRole);

		PlayerRole role = new PlayerRole();
		role.setRolename("role1");

		Player player = new Player();
		player.setRole(role);
		player.setUser(user);

		pp.set(player);

		Object[] authorities = ap.get();

		assertEquals(6, authorities.length);
		assertEquals(player, authorities[0]);
		assertEquals(role, authorities[1]);
		assertEquals(role.getRolename(), authorities[2]);
		assertEquals(user, authorities[3]);
		assertEquals(userRole, authorities[4]);
		assertEquals(userRole.getRolename(), authorities[5]);
	}
	
	public void testSet() throws Exception
	{

		SessionProvider sp = new ThreadLocalSessionProvider();
		SessionBasedPlayerProvider up = new SessionBasedPlayerProvider();
		PlayerRoleBasedAuthorityProvider ap = new PlayerRoleBasedAuthorityProvider(up);
		up.setSessionProvider(sp);
		sp.set(new MockHttpSession());

		PlayerRole role = new PlayerRole();
		role.setRolename("role1");

		Player player = new Player();
		player.setRole(role);

		up.set(player);
		
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
