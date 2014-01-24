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

import com.syncnapsis.data.model.Empire;
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
		SessionBasedEmpireProvider ep = new SessionBasedEmpireProvider();
		PlayerRoleBasedAuthorityProvider ap = new PlayerRoleBasedAuthorityProvider(pp, ep);
		ep.setSessionProvider(sp);
		pp.setSessionProvider(sp);
		sp.set(new MockHttpSession());

		Empire e1 = getEmpire(1);
		Empire e2 = getEmpire(2);

		pp.set(e1.getPlayer());
		ep.set(e2); // set different empire

		Object[] authorities = ap.get();

		assertEquals(5, authorities.length);
		assertSame(e2, authorities[0]);
		assertSame(e1.getPlayer(), authorities[1]);
		assertSame(e1.getPlayer().getRole(), authorities[2]);
		assertSame(e1.getPlayer().getUser(), authorities[3]);
		assertSame(e1.getPlayer().getUser().getRole(), authorities[4]);
	}

	public Empire getEmpire(long id)
	{
		UserRole userRole = new UserRole();
		userRole.setId(id);
		userRole.setRolename("userrole" + id);

		User user = new User();
		user.setId(id);
		user.setRole(userRole);

		PlayerRole role = new PlayerRole();
		role.setId(id);
		role.setRolename("role" + id);

		Player player = new Player();
		player.setId(id);
		player.setRole(role);
		player.setUser(user);

		Empire empire = new Empire();
		empire.setId(id);
		empire.setPlayer(player);

		return empire;
	}

	public void testSet() throws Exception
	{

		SessionProvider sp = new ThreadLocalSessionProvider();
		SessionBasedPlayerProvider pp = new SessionBasedPlayerProvider();
		SessionBasedEmpireProvider ep = new SessionBasedEmpireProvider();
		PlayerRoleBasedAuthorityProvider ap = new PlayerRoleBasedAuthorityProvider(pp, ep);
		pp.setSessionProvider(sp);
		sp.set(new MockHttpSession());

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
