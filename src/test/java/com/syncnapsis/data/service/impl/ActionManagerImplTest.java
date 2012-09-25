package com.syncnapsis.data.service.impl;

import org.jmock.Expectations;

import com.syncnapsis.data.dao.ActionDao;
import com.syncnapsis.data.model.Action;
import com.syncnapsis.data.service.ActionManager;
import com.syncnapsis.tests.GenericNameManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ ActionManager.class, ActionManagerImpl.class })
public class ActionManagerImplTest extends GenericNameManagerImplTestCase<Action, Long, ActionManager, ActionDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Action());
		setDaoClass(ActionDao.class);
		setMockDao(mockContext.mock(ActionDao.class));
		setMockManager(new ActionManagerImpl(mockDao));
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
		action.setCode(code);
		action.setObject("mybean");
		action.setMethod("mymethod");
		action.setArgs(new Object[] { "0", 1 });
		action.setMaxUses(10);

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
		assertEquals(action.getRPCCall(), mockManager.getRPCCall(code));
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
		assertEquals(action.getRPCCall(), mockManager.getRPCCall(code));
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
