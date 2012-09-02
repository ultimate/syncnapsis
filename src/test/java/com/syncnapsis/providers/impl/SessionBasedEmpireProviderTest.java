package com.syncnapsis.providers.impl;

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.constants.BaseApplicationConstants;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.providers.EmpireProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({SessionBasedEmpireProvider.class, EmpireProvider.class})
public class SessionBasedEmpireProviderTest extends LoggerTestCase
{
	public void testProvider() throws Exception
	{
		SessionProvider sp = new ThreadLocalSessionProvider();
		SessionBasedEmpireProvider p = new SessionBasedEmpireProvider();
		p.setSessionProvider(sp);		
		sp.set(new MockHttpSession());
		
		Empire value = new Empire();
		value.setShortName("a name");
		
		p.set(value);
		
		assertEquals(BaseApplicationConstants.SESSION_USER_KEY, p.getAttributeName());
		assertEquals(value, p.get());
		assertNotNull(sp.get().getAttribute(p.getAttributeName()));
		assertEquals(value, sp.get().getAttribute(p.getAttributeName()));
	}
}
