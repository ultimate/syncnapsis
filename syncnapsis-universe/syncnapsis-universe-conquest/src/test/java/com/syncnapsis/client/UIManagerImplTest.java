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

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.providers.ConnectionProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.engine.http.HttpConnection;
import com.syncnapsis.websockets.service.rpc.RPCService;

@TestExcludesMethods({ "get*", "set*", "afterPropertiesSet" })
public class UIManagerImplTest extends BaseSpringContextTestCase
{
	private SessionProvider		sessionProvider;
	private BaseGameManager		securityManager;
	private ConnectionProvider	connectionProvider;

	private UserManager			userManager;

	@TestCoversMethods({ "get*", "set*" })
	public void testGetAndSet() throws Exception
	{
		UIManagerImpl manager = new UIManagerImpl();

		getAndSetTest(manager, "userManager", UserManager.class, userManager);
	}

	public void testReloadLocale()
	{
		// nothing to test
	}

	public void testSelectLocale()
	{
		UIManagerImpl uiManager = new UIManagerImpl();
		uiManager.setConnectionProvider(connectionProvider);
		uiManager.setSecurityManager(securityManager);

		sessionProvider.set(new MockHttpSession());
		securityManager.getLocaleProvider().set(EnumLocale.EN);

		final Connection connection = new HttpConnection("test");
		connectionProvider.set(connection);

		final RPCService mockRPCService = mockContext.mock(RPCService.class);
		uiManager.setRpcService(mockRPCService);

		final UIManager clientUIManager = mockContext.mock(UIManager.class);

		final String uiManagerName = "uiManager";

		mockContext.checking(new Expectations() {
			{
				oneOf(mockRPCService).getClientInstance(uiManagerName, connection);
				will(returnValue(clientUIManager));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(clientUIManager).reloadLocale();
			}
		});

		uiManager.selectLocale(EnumLocale.DE);

		mockContext.assertIsSatisfied();

		assertEquals(EnumLocale.DE, securityManager.getLocaleProvider().get());
	}
}
