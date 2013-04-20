/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
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
package com.syncnapsis.websockets.engine.http;

import java.util.Enumeration;
import java.util.StringTokenizer;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.mock.MockService;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.utils.ServletUtil;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.Protocol;
import com.syncnapsis.websockets.Service;
import com.syncnapsis.websockets.WebSocketManager;

public class HttpFallbackEngineTest extends LoggerTestCase
{
	public void testIsWebSocketHandshake()
	{
		HttpFallbackEngine e = new HttpFallbackEngine();

		MockHttpServletRequest req;

		// wrong method

		req = new MockHttpServletRequest();
		req.setMethod("POST");
		assertFalse(e.isWebSocketHandshake(req, null));

		// right method, but no headers

		req = new MockHttpServletRequest();
		req.setMethod("GET");
		assertFalse(e.isWebSocketHandshake(req, null));

		// right method, but not all headers

		req = new MockHttpServletRequest();
		req.setMethod("GET");
		req.addHeader(Protocol.HEADER_CONNECTION, Protocol.HEADER_CONNECTION_UPGRADE);
		assertFalse(e.isWebSocketHandshake(req, null));

		req = new MockHttpServletRequest();
		req.setMethod("GET");
		req.addHeader(Protocol.HEADER_UPGRADE, Protocol.HEADER_UPGRADE_WEBSOCKET);
		assertFalse(e.isWebSocketHandshake(req, null));

		// right method, but (partially) wrong headers

		req = new MockHttpServletRequest();
		req.setMethod("GET");
		req.addHeader(Protocol.HEADER_CONNECTION, "foo");
		req.addHeader(Protocol.HEADER_UPGRADE, Protocol.HEADER_UPGRADE_WEBSOCKET);
		assertFalse(e.isWebSocketHandshake(req, null));

		req = new MockHttpServletRequest();
		req.setMethod("GET");
		req.addHeader(Protocol.HEADER_CONNECTION, Protocol.HEADER_CONNECTION_UPGRADE);
		req.addHeader(Protocol.HEADER_UPGRADE, "bar");
		assertFalse(e.isWebSocketHandshake(req, null));

		req = new MockHttpServletRequest();
		req.setMethod("GET");
		req.addHeader(Protocol.HEADER_CONNECTION, "foo");
		req.addHeader(Protocol.HEADER_UPGRADE, "bar");
		assertFalse(e.isWebSocketHandshake(req, null));

		// correct

		req = new MockHttpServletRequest();
		req.setMethod("GET");
		req.addHeader(Protocol.HEADER_CONNECTION, Protocol.HEADER_CONNECTION_UPGRADE);
		req.addHeader(Protocol.HEADER_UPGRADE, Protocol.HEADER_UPGRADE_WEBSOCKET);
		assertTrue(e.isWebSocketHandshake(req, null));

		req.addHeader(Protocol.HEADER_CONNECTION, "keep-alive");
		assertTrue(e.isWebSocketHandshake(req, null));

		req.addHeader(Protocol.HEADER_UPGRADE, "some second value");
		assertTrue(e.isWebSocketHandshake(req, null));

		// correct 2

		req = new MockHttpServletRequest();
		req.setMethod("GET");
		req.addHeader(Protocol.HEADER_CONNECTION, Protocol.HEADER_CONNECTION_UPGRADE + " keep-alive");
		req.addHeader(Protocol.HEADER_UPGRADE, Protocol.HEADER_UPGRADE_WEBSOCKET + " foo");
		assertTrue(e.isWebSocketHandshake(req, null));
	}

	@TestCoversMethods({ "addConnection", "getConnection", "removeConnection" })
	public void testAddGetRemoveConnection() throws Exception
	{
		HttpFallbackEngine e = new HttpFallbackEngine();

		String subprotocol = "myprotocol";
		String sessionId = "foobar";
		HttpConnection connection = new HttpConnection(subprotocol);

		assertNull(e.getConnection(subprotocol, sessionId));

		e.addConnection(subprotocol, sessionId, connection);
		assertNotNull(e.getConnection(subprotocol, sessionId));
		assertSame(connection, e.getConnection(subprotocol, sessionId));

		assertNull(e.getConnection(subprotocol, "some other id"));
		assertNull(e.getConnection("some other protocol", sessionId));

		assertSame(connection, e.removeConnection(subprotocol, sessionId));
		assertNull(e.getConnection(subprotocol, sessionId));
	}

	@SuppressWarnings("unchecked")
	@TestCoversMethods({ "doConnect", "doDisconnect" })
	public void testConnectAndDisconnect() throws Exception
	{
		final MockService service = new MockService();
		
		final String sessionId = "foo";
		final String subprotocol = "bar";
		final Enumeration<?> extensions = new StringTokenizer("a b c");
		final MockHttpSession session = new MockHttpSession();
		final String id = "123456789";
		
		final int closeCode = 1234;
		
		
		final MockHttpServletRequest openRequest = new MockHttpServletRequest();
		openRequest.setSession(session);
		openRequest.setRemoteAddr("remoteAddr");
		openRequest.setRemoteHost("remoteHost");
		openRequest.setRemotePort(1234);
		openRequest.addHeader(ServletUtil.ATTRIBUTE_USER_AGENT, "agent");
		
		final MockHttpServletRequest closeRequest = new MockHttpServletRequest();
		closeRequest.setSession(session);
		closeRequest.addHeader(HttpFallbackEngine.HEADER_CLOSE_CODE, closeCode);

		HttpFallbackEngine e = new HttpFallbackEngine() {
			/*
			 * (non-Javadoc)
			 * @see com.syncnapsis.websockets.engine.BaseEngine#getService(java.lang.String,
			 * java.util.Enumeration)
			 */
			@Override
			public Service getService(String s, Enumeration<String> e)
			{
				assertSame(subprotocol, s);
				assertSame(extensions, e);
				return service;
			}
		};
		e.setManager(new WebSocketManager() {

			@Override
			public String generateId(String s)
			{
				assertSame(subprotocol, s);
				return id;
			}
		});
		
		e.doConnect(sessionId, subprotocol, (Enumeration<String>) extensions, openRequest);
		
		assertEquals(2, service.getEvents().size());
		
		HttpConnection connection = (HttpConnection) service.getEvents().get(0).getConnection();

		assertEquals("onOpen", service.getEvents().get(0).getName());
		assertEquals("onHandshake", service.getEvents().get(1).getName());
		
		assertNotNull(connection);
		assertSame(connection, service.getEvents().get(1).getConnection());
		assertSame(connection, e.getConnection(subprotocol, sessionId));
		assertEquals(id, connection.getId());
		
		assertEquals(openRequest.getRemoteAddr(), session.getAttribute(ServletUtil.ATTRIBUTE_REMOTE_ADDR));
		assertEquals(openRequest.getRemoteHost(), session.getAttribute(ServletUtil.ATTRIBUTE_REMOTE_HOST));
		assertEquals(openRequest.getRemotePort(), session.getAttribute(ServletUtil.ATTRIBUTE_REMOTE_PORT));
		assertEquals(openRequest.getHeader(ServletUtil.ATTRIBUTE_USER_AGENT), session.getAttribute(ServletUtil.ATTRIBUTE_USER_AGENT));
		
		// TODO check session attributes
		
		e.doDisconnect(connection, closeRequest);
		
		assertEquals(3, service.getEvents().size());
		assertEquals("onClose", service.getEvents().get(2).getName());
		assertEquals(closeCode, service.getEvents().get(2).getArgs()[0]);
		
		assertFalse(connection.isOpen());
		
		// TODO message
		// TODO removed connection
	}
	// getMessage() -> no Test found
	// stop0() -> no Test found
	// doFilter() -> no Test found
	// doFallback() -> no Test found
	// fetchMessage() -> no Test found
	// handleMessage() -> no Test found
}