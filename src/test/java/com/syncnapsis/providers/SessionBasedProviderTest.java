package com.syncnapsis.providers;

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.providers.SessionBasedProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.impl.ThreadLocalSessionProvider;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class SessionBasedProviderTest extends LoggerTestCase
{
	@TestCoversMethods({"*"})
	public void testProvider() throws Exception
	{
		SessionProvider sp = new ThreadLocalSessionProvider();
		SessionBasedProvider<String> p = new SessionBasedProvider<String>("attr");
		p.setSessionProvider(sp);		
		sp.set(new MockHttpSession());
		
		String value = "my value";
		p.set(value);
		
		assertEquals(value, p.get());
		assertNotNull(sp.get().getAttribute(p.getAttributeName()));
		assertEquals(value, sp.get().getAttribute(p.getAttributeName()));
	}
}
