package com.syncnapsis.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jmock.Expectations;

import com.syncnapsis.providers.ConnectionProvider;
import com.syncnapsis.providers.impl.ThreadLocalConnectionProvider;
import com.syncnapsis.security.BaseGameManager;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.engine.http.HttpConnection;
import com.syncnapsis.websockets.service.rpc.RPCService;

public class BaseClientManagerTest extends BaseSpringContextTestCase
{
	@TestCoversMethods({"get*", "set*"})
	public void testGetAndSet() throws Exception
	{
		BaseClientManager manager = new BaseClientManager();
		
		getAndSetTest(manager, "connectionProvider", ConnectionProvider.class, new ThreadLocalConnectionProvider());
		getAndSetTest(manager, "rpcService", RPCService.class, new RPCService());
		getAndSetTest(manager, "securityManager", BaseGameManager.class, new BaseGameManager());
	}
	
	public void testGetClientInstance()
	{
		BaseClientManager manager = new BaseClientManager();

		final RPCService mockRPCService = mockContext.mock(RPCService.class);
		manager.setRpcService(mockRPCService);

		final String objectName = "mybean";
		final Object bean = new Object();
		final Connection connection = new HttpConnection("subprotocol");

		mockContext.checking(new Expectations() {
			{
				oneOf(mockRPCService).getClientInstance(objectName, connection);
				will(returnValue(bean));
			}
		});

		Object o = manager.getClientInstance(objectName, connection);

		mockContext.assertIsSatisfied();
		assertSame(bean, o);
	}

	public void testGetConnections() throws Exception
	{
		BaseClientManager manager = new BaseClientManager();

		final RPCService mockRPCService = mockContext.mock(RPCService.class);
		manager.setRpcService(mockRPCService);

		final List<Connection> connections = new ArrayList<Connection>();

		mockContext.checking(new Expectations() {
			{
				oneOf(mockRPCService).getConnections();
				will(returnValue(connections));
			}
		});

		Collection<Connection> l = manager.getConnections();

		mockContext.assertIsSatisfied();
		assertSame(connections, l);
	}
}
