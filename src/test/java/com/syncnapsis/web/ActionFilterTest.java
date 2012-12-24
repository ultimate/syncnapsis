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
package com.syncnapsis.web;

import org.jmock.Expectations;

import com.syncnapsis.data.service.ActionManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.utils.serialization.Serializer;
import com.syncnapsis.web.ActionFilter;
import com.syncnapsis.websockets.service.rpc.RPCCall;
import com.syncnapsis.websockets.service.rpc.RPCHandler;

public class ActionFilterTest extends BaseDaoTestCase
{
	private ActionManager	mockActionManager;
	private ActionFilter	actionFilter;

	@SuppressWarnings("unchecked")
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		mockActionManager = mockContext.mock(ActionManager.class);
		actionFilter = new ActionFilter();
		actionFilter.setActionManager(mockActionManager);
		actionFilter.setSerializer(mockContext.mock(Serializer.class));
		actionFilter.setRpcHandler(mockContext.mock(RPCHandler.class));
		actionFilter.afterPropertiesSet();
	}

	@Override
	protected void tearDown() throws Exception
	{
		mockActionManager = null;
		actionFilter = null;
		super.tearDown();
	}

	@TestCoversMethods({ "getRPCCall", "afterPropertiesSet", "*etActionManager" })
	public void testGetRPCCall() throws Exception
	{
		final String code = "arbitrary code...";
		final RPCCall rpcCall = new RPCCall("obj", "meth", new Object[] {});

		mockContext.checking(new Expectations() {
			{
				oneOf(mockActionManager).getRPCCall(code);
				will(returnValue(rpcCall));
			}
		});

		assertEquals(rpcCall, actionFilter.getRPCCall(code));
		mockContext.assertIsSatisfied();
	}
}
