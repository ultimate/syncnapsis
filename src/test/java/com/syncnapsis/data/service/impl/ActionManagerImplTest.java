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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jmock.Expectations;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.dao.ActionDao;
import com.syncnapsis.data.model.Action;
import com.syncnapsis.data.service.ActionManager;
import com.syncnapsis.data.service.ParameterManager;
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
	private ParameterManager	parameterManager;

	private Serializer<String>	serializer	= new JacksonStringSerializer();

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Action());
		setDaoClass(ActionDao.class);
		setMockDao(mockContext.mock(ActionDao.class));
		setMockManager(new ActionManagerImpl(mockDao, parameterManager));

		((ActionManagerImpl) mockManager).setSerializer(serializer);
	}

	public void testGetByCode() throws Exception
	{
		// getByCode invokes getByName!
		MethodCall managerCall = new MethodCall("getByCode", entity, "code");
		MethodCall daoCall = new MethodCall("getByName", entity, "code");
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetRPCCall() throws Exception
	{
		final Action action = new Action();
		final String code = "a1b2c3";
		final Object[] args = new Object[] { "0", 1 };

		action.setCode(code);
		action.setRPCCall(new com.syncnapsis.data.model.help.RPCCall());
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

	public void testGenerateCode() throws Exception
	{
		String code;

		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByName(with(any(String.class)));
				will(returnValue(null));
			}
		});

		code = mockManager.generateCode();
		mockContext.assertIsSatisfied();

		assertNotNull(code);
		assertEquals(ApplicationBaseConstants.PARAM_ACTION_CODE_LENGTH.asInt(), code.length());

		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByName(with(any(String.class)));
				will(returnValue(new Action()));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByName(with(any(String.class)));
				will(returnValue(null));
			}
		});

		code = mockManager.generateCode();
		mockContext.assertIsSatisfied();

		assertNotNull(code);
		assertEquals(ApplicationBaseConstants.PARAM_ACTION_CODE_LENGTH.asInt(), code.length());
	}

	public void testCreateAction() throws Exception
	{
		Object[] args = new Object[] { 1L, "blub", true };
		final RPCCall rpcCall = new RPCCall("aBean", "aMethod", args);
		
		int maxUses = 1;
		
		final Action action = new Action();
		action.setMaxUses(maxUses);
		action.setRPCCall(new com.syncnapsis.data.model.help.RPCCall());
		action.getRPCCall().setObject(rpcCall.getObject());
		action.getRPCCall().setMethod(rpcCall.getMethod());
		action.getRPCCall().setArgs("[1,\"blub\",true]");
		
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByName(with(any(String.class)));
				will(returnValue(null));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(with(new BaseMatcher<Action>() {
					@Override
					public boolean matches(Object arg0)
					{
						if(!(arg0 instanceof Action))
							return false;
								
						Action a = (Action) arg0;
						boolean matches = true;
						matches = matches && (a.getMaxUses() == action.getMaxUses());
						matches = matches && a.getRPCCall().equals(action.getRPCCall());
						action.setCode(a.getCode());
						return matches;
					}

					@Override
					public void describeTo(Description arg0)
					{
					}
				}));
				will(returnValue(action));
			}
		});

		Action result = mockManager.createAction(rpcCall, 1);
		mockContext.assertIsSatisfied();
		
		assertNotNull(result.getCode());
	}
}
