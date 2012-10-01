package com.syncnapsis.providers.impl;

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.model.User;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.UserProvider;
import com.syncnapsis.providers.impl.SessionBasedUserProvider;
import com.syncnapsis.providers.impl.ThreadLocalSessionProvider;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({SessionBasedUserProvider.class, UserProvider.class})
public class SessionBasedUserProviderTest extends LoggerTestCase
{
	public void testProvider() throws Exception
	{
		SessionProvider sp = new ThreadLocalSessionProvider();
		SessionBasedUserProvider p = new SessionBasedUserProvider();
		p.setSessionProvider(sp);		
		sp.set(new MockHttpSession());
		
		User value = new User();
		value.setUsername("a name");
		
		p.set(value);
		
		assertEquals(ApplicationBaseConstants.SESSION_USER_KEY, p.getAttributeName());
		assertEquals(value, p.get());
		assertNotNull(sp.get().getAttribute(p.getAttributeName()));
		assertEquals(value, sp.get().getAttribute(p.getAttributeName()));
	}
}
