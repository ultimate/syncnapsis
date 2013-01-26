/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.websockets.service.rpc;

import java.util.Date;

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.model.RPCLog;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.RPCLogManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.mock.MockTimeProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.providers.UserProvider;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ServletUtil;

/**
 * Test for {@link LogRPCHandler}
 * 
 * @author ultimate
 */
@TestExcludesMethods({ "set*", "get*", "afterPropertiesSet" })
public class LogRPCHandlerTest extends BaseDaoTestCase
{
	private SessionProvider	sessionProvider;
	private UserProvider	userProvider;
	private UserManager		userManager;

	public void testDoRPC() throws Exception
	{
		final RPCHandler mockHandler = mockContext.mock(RPCHandler.class);
		final RPCLogManager mockRPCLogManager = mockContext.mock(RPCLogManager.class);
		final TimeProvider timeProvider = new MockTimeProvider(1111);
		
		LogRPCHandler logRPCHandler = new LogRPCHandler();
		logRPCHandler.setDelegate(mockHandler);
		logRPCHandler.setSessionProvider(sessionProvider);
		logRPCHandler.setTimeProvider(timeProvider);
		logRPCHandler.setUserProvider(userProvider);
		logRPCHandler.setRpcLogManager(mockRPCLogManager);
		
		final RPCCall rpcCall = new RPCCall("object", "method", new Object[] { "1", 2 });
		final User user = userManager.get(0L);
		final String result = "a text";
		final Object[] authorities = new Object[0];
		final Date executionDate = new Date(timeProvider.get());
		String addr = "1.2.3.4";
		String agent = "browser";
		final MockHttpSession session = new MockHttpSession();
		session.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_ADDR, addr);
		session.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_HOST, addr);
		session.setAttribute(ServletUtil.ATTRIBUTE_USER_AGENT, agent);
		
		final RPCLog log;
		
		log = new RPCLog();
		log.setExecutionDate(executionDate);
		log.setRemoteAddr(addr);
		log.setResult("\"" + result + "\"");
		log.setRPCCall(new com.syncnapsis.data.model.help.RPCCall());
		log.getRPCCall().setObject(rpcCall.getObject());
		log.getRPCCall().setMethod(rpcCall.getMethod());
		log.getRPCCall().setArgs("[\"1\",2]");
		log.setUser(user);
		log.setUserAgent(agent);
		
		sessionProvider.set(session);
		userProvider.set(user);
		
		mockContext.checking(new Expectations() {
			{
				oneOf(mockHandler).doRPC(rpcCall, authorities);
				will(returnValue(result));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockRPCLogManager).log(rpcCall, result, executionDate, user, session, authorities);
				will(returnValue(log));
			}
		});
		
		Object returnValue = logRPCHandler.doRPC(rpcCall, authorities);
		
		assertEquals(result, returnValue);
		
		mockContext.assertIsSatisfied();
	}
}
