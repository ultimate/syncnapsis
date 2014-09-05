/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
