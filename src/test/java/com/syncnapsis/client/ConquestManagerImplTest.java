package com.syncnapsis.client;

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.service.MatchManager;
import com.syncnapsis.providers.ConnectionProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods({ "afterPropertiesSet" })
public class ConquestManagerImplTest extends BaseSpringContextTestCase
{
	private SessionProvider		sessionProvider;
	private BaseGameManager		securityManager;
	private ConnectionProvider	connectionProvider;

	private MatchManager		matchManager;

	private static final String	beanName	= "conquestManager";

	@TestCoversMethods({ "get*", "set*" })
	public void testGetAndSet() throws Exception
	{
		ConquestManagerImpl manager = new ConquestManagerImpl();

		getAndSetTest(manager, "matchManager", MatchManager.class, matchManager);
	}

	public void testX()
	{
		ConquestManagerImpl conquestManager = new ConquestManagerImpl();
		conquestManager.setConnectionProvider(connectionProvider);
		conquestManager.setSecurityManager(securityManager);
		conquestManager.setMatchManager(matchManager);
		conquestManager.setBeanName(beanName);

		sessionProvider.set(new MockHttpSession());
		
		// TODO
	}
}
