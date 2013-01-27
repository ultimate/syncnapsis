package com.syncnapsis.client;

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.providers.ConnectionProvider;
import com.syncnapsis.providers.LocaleProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.engine.http.HttpConnection;
import com.syncnapsis.websockets.service.rpc.RPCHandler;

@TestExcludesMethods({ "get*", "set*" })
public class UIManagerImplTest extends BaseSpringContextTestCase
{
	private SessionProvider sessionProvider;
	private LocaleProvider		localeProvider;
	private ConnectionProvider	connectionProvider;

	public void testReloadLocale()
	{
		// nothing to test
	}

	public void testSelectLocale()
	{
		UIManagerImpl uiManager = new UIManagerImpl();
		uiManager.setConnectionProvider(connectionProvider);
		uiManager.setLocaleProvider(localeProvider);
		
		sessionProvider.set(new MockHttpSession());
		localeProvider.set(EnumLocale.EN);

		final Connection connection = new HttpConnection("test");
		connectionProvider.set(connection);

		final RPCHandler mockRPCHandler = mockContext.mock(RPCHandler.class);
		uiManager.setRpcHandler(mockRPCHandler);

		final UIManager clientUIManager = mockContext.mock(UIManager.class);

		final String uiManagerName = "uiManager";

		mockContext.checking(new Expectations() {
			{
				oneOf(mockRPCHandler).getClientInstance(uiManagerName, connection);
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
		
		assertEquals(EnumLocale.DE, localeProvider.get());
	}
}
