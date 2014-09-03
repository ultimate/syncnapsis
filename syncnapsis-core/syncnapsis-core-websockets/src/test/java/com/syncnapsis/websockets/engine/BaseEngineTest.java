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
package com.syncnapsis.websockets.engine;

import com.syncnapsis.enums.EnumEngineSupport;
import com.syncnapsis.exceptions.WebSocketEngineException;
import com.syncnapsis.mock.MockConnection;
import com.syncnapsis.mock.MockProtocol;
import com.syncnapsis.providers.impl.ThreadLocalConnectionProvider;
import com.syncnapsis.security.SecurityManager;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.websockets.ConnectionProperties;
import com.syncnapsis.websockets.Engine;
import com.syncnapsis.websockets.Service;
import com.syncnapsis.websockets.ServiceMapping;
import com.syncnapsis.websockets.WebSocketManager;
import com.syncnapsis.websockets.service.EchoService;

@TestExcludesMethods({ "init" })
public class BaseEngineTest extends BaseSpringContextTestCase
{
	private SecurityManager	securityManager;

	@TestCoversMethods({ "*etManager", "*etConnectionProperties", "*etServiceMapping", "*etParent", "*Disabled", "*etEngineSupport", "*Children",
			"*Child" })
	public void testGetAndSet() throws Exception
	{
		BaseEngine e = new BaseEngine(EnumEngineSupport.CHILDREN_REQUIRED) {
			//@formatter:off
			protected void start0() throws WebSocketEngineException {}
			protected void stop0() throws WebSocketEngineException {}
			//@formatter:on
		};
		BaseEngine e2 = new BaseEngine(EnumEngineSupport.CHILDREN_REQUIRED) {
			//@formatter:off
			protected void start0() throws WebSocketEngineException {}
			protected void stop0() throws WebSocketEngineException {}
			//@formatter:on
		};

		// simple properties
		getAndSetTest(e, "manager", WebSocketManager.class, new WebSocketManager());
		getAndSetTest(e, "connectionProperties", ConnectionProperties.class, new ConnectionProperties());
		getAndSetTest(e, "serviceMapping", ServiceMapping.class, new ServiceMapping());
		getAndSetTest(e, "parent", Engine.class, e2);
		getAndSetTest(e, "disabled", boolean.class, true);

		assertEquals(EnumEngineSupport.CHILDREN_REQUIRED, e.getEngineSupport());

		e.addChild(e2);
		assertTrue(e.getChildren().contains(e2));
		e.removeChild(e2);
		assertFalse(e.getChildren().contains(e2));
	}

	@TestCoversMethods({ "start*", "stop*", "isRunning", "afterPropertiesSet", "disconnectAll", "getService" })
	public void testLifecycle() throws Exception
	{
		WebSocketManager e = new WebSocketManager();
		BaseEngine e2 = new BaseEngine(EnumEngineSupport.NOT_SUPPORTED) {
			//@formatter:off
			protected void start0() throws WebSocketEngineException {}
			protected void stop0() throws WebSocketEngineException {}
			//@formatter:on
		};
		
		String subprotocol = "mysub";
		Service service = new EchoService();
		
		ServiceMapping serviceMapping = new ServiceMapping();
		serviceMapping.addProtocol(subprotocol, service);
		
		e.setSecurityManager(securityManager);
		e.setConnectionProvider(new ThreadLocalConnectionProvider());
		e.setServiceMapping(serviceMapping);
		e.addChild(e2);
		e2.setParent(e);
		e2.setManager(e);

		e.afterPropertiesSet();
		e2.afterPropertiesSet();
		
		e.start();
		assertTrue(e.isRunning());
		assertTrue(e2.isRunning());
		
		Service result = e.getService(subprotocol, null);
		assertNotNull(result);
		assertSame(service, result);
		
		MockConnection connection = new MockConnection() {
			@Override
			public void close(int closeCode, String message)
			{
				super.close(closeCode, message);
				service.removeConnection(this);
			}
		};
		connection.setService(service);
		connection.setManager(e);
		connection.setProtocol(new MockProtocol());
		
		assertEquals(0, service.getNumberOfConnections());
		service.addConnection(connection);
		assertEquals(1, service.getNumberOfConnections());
		
		e.disconnectAll(1, "a");
		assertEquals(0, service.getNumberOfConnections());
		
		e.stop();
		assertFalse(e.isRunning());
		assertFalse(e2.isRunning());
	}
}