package com.syncnapsis.client;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.model.PinboardMessage;
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

		sessionProvider.set(new MockHttpSession());

		final Long pinboardId = 1L;
		final String title = "title";
		final String content = "content";
		final PinboardMessage pinboardMessage = new PinboardMessage();

		final List<PinboardMessage> messages = new ArrayList<PinboardMessage>(1);
		messages.add(pinboardMessage);

		final Connection connection = new HttpConnection("test");

		final int connectionCount = 5;
		final List<Connection> connections = new ArrayList<Connection>(connectionCount);
		for(int i = 0; i < connectionCount; i++)
			connections.add(connection);

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
		mockContext.checking(new Expectations() {
			{
				exactly(connectionCount).of(mockRPCService).getClientInstance(messageManagerName, connection);
				will(returnValue(clientMessageManager));
			}
		});
		mockContext.checking(new Expectations() {
			{
				exactly(connectionCount).of(clientMessageManager).updatePinboard(pinboardId, messages);
			}
		});

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
