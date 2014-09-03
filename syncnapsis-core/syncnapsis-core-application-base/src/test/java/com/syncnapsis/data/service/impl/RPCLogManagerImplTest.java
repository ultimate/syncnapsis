/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.data.service.impl;

import java.util.Date;

import org.jmock.Expectations;
import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.data.dao.RPCLogDao;
import com.syncnapsis.data.model.RPCLog;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.RPCLogManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ServletUtil;
import com.syncnapsis.utils.serialization.JacksonStringSerializer;
import com.syncnapsis.websockets.service.rpc.RPCCall;

@TestCoversClasses({ RPCLogManager.class, RPCLogManagerImpl.class })
@TestExcludesMethods({ "*etSerializer", "afterPropertiesSet" })
public class RPCLogManagerImplTest extends GenericManagerImplTestCase<RPCLog, Long, RPCLogManager, RPCLogDao>
{
	private UserManager		userManager;

	private TimeProvider	timeProvider;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new RPCLog());
		setDaoClass(RPCLogDao.class);
		setMockDao(mockContext.mock(RPCLogDao.class));
		setMockManager(new RPCLogManagerImpl(mockDao));
	}

	public void testLog() throws Exception
	{		
		((RPCLogManagerImpl) mockManager).setSerializer(new JacksonStringSerializer());
		
		RPCCall rpcCall = new RPCCall("object", "method", new Object[] { "1", 2 });
		User user = userManager.get(0L);
		String result = "a text";
		Exception ex = new IllegalAccessException("not allowed");
		Object[] authorities = new Object[0];
		Date executionDate = new Date(timeProvider.get());
		String addr = "1.2.3.4";
		String agent = "browser";
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_ADDR, addr);
		session.setAttribute(ServletUtil.ATTRIBUTE_REMOTE_HOST, addr);
		session.setAttribute(ServletUtil.ATTRIBUTE_USER_AGENT, agent);
		
		final RPCLog expected;
		RPCLog log;
		
		expected = new RPCLog();
		expected.setExecutionDate(executionDate);
		expected.setRemoteAddr(addr);
		expected.setResult("\"" + result + "\"");
		expected.setRPCCall(new com.syncnapsis.data.model.help.RPCCall());
		expected.getRPCCall().setObject(rpcCall.getObject());
		expected.getRPCCall().setMethod(rpcCall.getMethod());
		expected.getRPCCall().setArgs("[\"1\",2]");
		expected.setUser(user);
		expected.setUserAgent(agent);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(expected);
				will(returnValue(expected));
			}
		});
		
		log = mockManager.log(rpcCall, result, executionDate, user, session, authorities);
		
		assertNotNull(log);
		assertEquals(expected, log);
		mockContext.assertIsSatisfied();

		expected.setResult(ex.getClass().getName() + ": " + ex.getMessage());
		
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(expected);
				will(returnValue(expected));
			}
		});
		
		log = mockManager.log(rpcCall, ex, executionDate, user, session, authorities);
		
		assertNotNull(log);
		assertEquals(expected, log);
		mockContext.assertIsSatisfied();
	}
}
