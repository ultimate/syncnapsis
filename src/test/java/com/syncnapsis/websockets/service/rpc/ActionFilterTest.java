package com.syncnapsis.websockets.service.rpc;

import org.jmock.Expectations;

import com.syncnapsis.data.service.ActionManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.utils.serialization.Serializer;

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
