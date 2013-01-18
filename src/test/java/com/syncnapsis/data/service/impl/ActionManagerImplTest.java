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
package com.syncnapsis.data.service.impl;

import org.jmock.Expectations;

import com.syncnapsis.data.dao.ActionDao;
import com.syncnapsis.data.model.Action;
import com.syncnapsis.data.service.ActionManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.serialization.JacksonStringSerializer;
import com.syncnapsis.utils.serialization.Serializer;
import com.syncnapsis.websockets.service.rpc.RPCCall;

@TestCoversClasses({ ActionManager.class, ActionManagerImpl.class })
@TestExcludesMethods({ "*etSerializer", "afterPropertiesSet" })
public class ActionManagerImplTest extends GenericNameManagerImplTestCase<Action, Long, ActionManager, ActionDao>
{
	private Serializer<String>	serializer	= new JacksonStringSerializer();

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Action());
		setDaoClass(ActionDao.class);
		setMockDao(mockContext.mock(ActionDao.class));
		setMockManager(new ActionManagerImpl(mockDao));

		((ActionManagerImpl) mockManager).setSerializer(serializer);
	}

	public void testGetByCode() throws Exception
	{
		// getByCode invokes getByName!
		MethodCall managerCall = new MethodCall("getByName", entity, "code");
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetRPCCall() throws Exception
	{
		final Action action = new Action();
		final String code = "a1b2c3";
		final Object[] args = new Object[] { "0", 1 };

		action.setCode(code);
		action.getRPCCall().setObject("mybean");
		action.getRPCCall().setMethod("mymethod");
		action.getRPCCall().setArgs(serializer.serialize(args, (Object[]) null));
		action.setMaxUses(10);

		final RPCCall rpcCall = new RPCCall(action.getRPCCall().getObject(), action.getRPCCall().getMethod(), args);

		// test with uses < maxUses-1
		action.setUses(action.getMaxUses() - 2);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByName(code);
				will(returnValue(action));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(action);
				will(returnValue(action));
			}
		});
		assertEquals(rpcCall, mockManager.getRPCCall(code));
		mockContext.assertIsSatisfied();

		// test with uses = maxUses-1
		action.setUses(action.getMaxUses() - 1);
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByName(code);
				will(returnValue(action));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).remove(action);
				will(returnValue("deleted"));
			}
		});
		assertEquals(rpcCall, mockManager.getRPCCall(code));
		mockContext.assertIsSatisfied();

		// test with uses = maxUses
		action.setUses(action.getMaxUses());
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByName(code);
				will(returnValue(action));
			}
		});
		assertNull(mockManager.getRPCCall(code));
		mockContext.assertIsSatisfied();
	}
}
