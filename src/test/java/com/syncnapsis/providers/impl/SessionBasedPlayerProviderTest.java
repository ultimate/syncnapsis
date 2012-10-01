package com.syncnapsis.providers.impl;

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.constants.ApplicationBaseConstants;
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
		
		assertEquals(ApplicationBaseConstants.SESSION_USER_KEY, p.getAttributeName());
		assertEquals(value, p.get());
		assertNotNull(sp.get().getAttribute(p.getAttributeName()));
		assertEquals(value, sp.get().getAttribute(p.getAttributeName()));
	}
}
