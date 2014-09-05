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

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.model.Pinboard;
import com.syncnapsis.data.model.PinboardMessage;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.PinboardManager;
import com.syncnapsis.data.service.PinboardMessageManager;
import com.syncnapsis.providers.ConnectionProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.engine.http.HttpConnection;
import com.syncnapsis.websockets.service.rpc.RPCService;

@TestExcludesMethods({ "afterPropertiesSet" })
public class MessageManagerImplTest extends BaseSpringContextTestCase
{
	private SessionProvider			sessionProvider;
	private BaseGameManager			securityManager;
	private ConnectionProvider		connectionProvider;

	private PinboardManager			pinboardManager;
	private PinboardMessageManager	pinboardMessageManager;

	private static final String		beanName	= "messageManager";

	@TestCoversMethods({ "get*", "set*" })
	public void testGetAndSet() throws Exception
	{
		MessageManagerImpl manager = new MessageManagerImpl();

		getAndSetTest(manager, "pinboardManager", PinboardManager.class, pinboardManager);
		getAndSetTest(manager, "pinboardMessageManager", PinboardMessageManager.class, pinboardMessageManager);
	}

	public void testPostPinboardMessage()
	{
		MessageManagerImpl messageManager = new MessageManagerImpl();
		messageManager.setConnectionProvider(connectionProvider);
		messageManager.setSecurityManager(securityManager);
		messageManager.setBeanName(beanName);

		final int connectionCount = 5;
		final User[] users = new User[connectionCount];
		final List<Connection> connections = new ArrayList<Connection>(connectionCount);

		for(int i = 0; i < connectionCount; i++)
		{
			users[i] = new User();
			users[i].setId((long) i);

			final Connection connection = new HttpConnection("test");
			connection.setSession(new MockHttpSession());
			connection.getSession().setAttribute(ApplicationBaseConstants.SESSION_USER_KEY, users[i]);

			connections.add(connection);
		}

		sessionProvider.set(connections.get(0).getSession());
		final User creator = users[0];

		final Long pinboardId = 1L;
		final Pinboard pinboard = new Pinboard();
		pinboard.setId(pinboardId);
		pinboard.setCreator(creator);

		final String title = "title";
		final String content = "content";

		final PinboardMessage pinboardMessage = new PinboardMessage();
		pinboardMessage.setPinboard(pinboard);
		pinboardMessage.setContent(content);
		pinboardMessage.setTitle(title);
		pinboardMessage.setCreator(creator);

		final List<PinboardMessage> messages = new ArrayList<PinboardMessage>(1);
		messages.add(pinboardMessage);

		final RPCService mockRPCService = mockContext.mock(RPCService.class);
		messageManager.setRpcService(mockRPCService);

		final PinboardManager mockPinboardManager = mockContext.mock(PinboardManager.class);
		messageManager.setPinboardManager(mockPinboardManager);

		final MessageManager clientMessageManager = mockContext.mock(MessageManager.class);

		final String messageManagerName = "messageManager";

		mockContext.checking(new Expectations() {
			{
				oneOf(mockPinboardManager).postMessage(pinboardId, title, content);
				will(returnValue(pinboardMessage));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockRPCService).getConnections();
				will(returnValue(connections));
			}
		});
		for(int i = 0; i < connectionCount; i++)
		{
			final int fi = i;
			mockContext.checking(new Expectations() {
				{
					oneOf(mockPinboardManager).checkReadPermission(pinboard, users[fi]);
					will(returnValue(fi == 0));
				}
			});
			if(fi == 0)
			{
				mockContext.checking(new Expectations() {
					{
						oneOf(mockRPCService).getClientInstance(messageManagerName, connections.get(fi));
						will(returnValue(clientMessageManager));
					}
				});
				mockContext.checking(new Expectations() {
					{
						oneOf(clientMessageManager).updatePinboard(pinboardId, messages);
					}
				});
			}
		}

		messageManager.postPinboardMessage(pinboardId, title, content);

		mockContext.assertIsSatisfied();
	}

	public void testUpdatePinboard()
	{
		// nothing to test (client stub only)
	}

	public void testRequestPinboardUpdate()
	{
		MessageManagerImpl messageManager = new MessageManagerImpl();
		messageManager.setConnectionProvider(connectionProvider);
		messageManager.setSecurityManager(securityManager);
		messageManager.setBeanName(beanName);

		sessionProvider.set(new MockHttpSession());

		final Long pinboardId = 1L;
		final List<PinboardMessage> messages = new ArrayList<PinboardMessage>();
		final int messageCount = 10;

		final Connection connection = new HttpConnection("test");
		connectionProvider.set(connection);

		final RPCService mockRPCService = mockContext.mock(RPCService.class);
		messageManager.setRpcService(mockRPCService);

		final PinboardMessageManager mockPinboardMessageManager = mockContext.mock(PinboardMessageManager.class);
		messageManager.setPinboardMessageManager(mockPinboardMessageManager);

		final MessageManager clientMessageManager = mockContext.mock(MessageManager.class);

		final String messageManagerName = "messageManager";

		mockContext.checking(new Expectations() {
			{
				oneOf(mockPinboardMessageManager).getByPinboard(pinboardId, messageCount);
				will(returnValue(messages));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockRPCService).getClientInstance(messageManagerName, connection);
				will(returnValue(clientMessageManager));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(clientMessageManager).updatePinboard(pinboardId, messages);
			}
		});

		messageManager.requestPinboardUpdate(pinboardId, messageCount);

		mockContext.assertIsSatisfied();
	}
}
