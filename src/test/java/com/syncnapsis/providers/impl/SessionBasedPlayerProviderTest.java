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

import com.syncnapsis.constants.GameBaseConstants;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.providers.PlayerProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({SessionBasedPlayerProvider.class, PlayerProvider.class})
public class SessionBasedPlayerProviderTest extends LoggerTestCase
{
	public void testProvider() throws Exception
	{
		SessionProvider sp = new ThreadLocalSessionProvider();
		SessionBasedPlayerProvider p = new SessionBasedPlayerProvider();
		p.setSessionProvider(sp);		
		sp.set(new MockHttpSession());
		
		Player value = new Player();
		
		p.set(value);
		
		assertEquals(GameBaseConstants.SESSION_PLAYER_KEY, p.getAttributeName());
		assertEquals(value, p.get());
		assertNotNull(sp.get().getAttribute(p.getAttributeName()));
		assertEquals(value, sp.get().getAttribute(p.getAttributeName()));
	}
}
