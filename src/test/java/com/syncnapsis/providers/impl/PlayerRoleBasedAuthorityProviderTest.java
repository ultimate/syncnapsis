package com.syncnapsis.providers.impl;

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.PlayerRole;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.tests.LoggerTestCase;

public class PlayerRoleBasedAuthorityProviderTest extends LoggerTestCase
{
	public void testGet() throws Exception
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

		Object[] authorities = ap.get();

		assertEquals(3, authorities.length);
		assertEquals(player, authorities[0]);
		assertEquals(role, authorities[1]);
		assertEquals(role.getRolename(), authorities[2]);
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
