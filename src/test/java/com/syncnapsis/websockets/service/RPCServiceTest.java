package com.syncnapsis.websockets.service;

import java.util.HashMap;
import java.util.Map;

import com.syncnapsis.exceptions.SerializationException;
import com.syncnapsis.mock.MockConnection;
import com.syncnapsis.mock.MockProtocol;
import com.syncnapsis.mock.MockRPCHandler;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.utils.serialization.JacksonStringSerializer;
import com.syncnapsis.utils.serialization.Mapable;
import com.syncnapsis.websockets.service.RPCService;
import com.syncnapsis.websockets.service.rpc.RPCCall;
import com.syncnapsis.websockets.service.rpc.RPCMessage;

public class RPCServiceTest extends LoggerTestCase
{
	public void testCall() throws Exception
	{
		final RPCService rpcService = new RPCService();

		final JacksonStringSerializer serializer = new JacksonStringSerializer();
		rpcService.setSerializer(serializer);

		final MockConnection connection = new MockConnection(true, true);
		connection.setProtocol(new MockProtocol());

		RPCCall call1 = new RPCCall("dummy", "doX", null);
		RPCCall call2 = new RPCCall("dummy", "doX", new Object[] { "lala", 5 });

		RPCMessage message1 = new RPCMessage(connection, null, 1L, call1);
		RPCMessage message2 = new RPCMessage(connection, null, 2L, call2);

		// without result
		assertNull(rpcService.call(connection, "dummy", "doX", null, void.class));
		assertEquals(1, connection.getMessageBuffer().size());
		assertEquals(serializer.serialize(message1, new Object[0]), connection.getMessageBuffer().get(0).getDataString());
		connection.clearMessageBuffer(1);

		// with result
		ResultPOJO expectedResult = new ResultPOJO();
		expectedResult.setValue("some value");
		final RPCMessage resultMessage = new RPCMessage(connection, 1L, message2.getScid(), expectedResult);
		// we need a thread to inject a result asynchronously
		Thread t = new Thread() {
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				try
				{
					rpcService.onMessage(connection, serializer.serialize(resultMessage, new Object[0]));
				}
				catch(SerializationException e)
				{
					e.printStackTrace();
				}
			}
		};

		t.start();

		ResultPOJO result = (ResultPOJO) rpcService.call(connection, "dummy", "doX", new Object[] { "lala", 5 }, ResultPOJO.class);
		assertEquals(expectedResult, result);
		assertEquals(expectedResult.getValue(), result.getValue());
		assertEquals(1, connection.getMessageBuffer().size());
		assertEquals(serializer.serialize(message2, new Object[0]), connection.getMessageBuffer().get(0).getDataString());
		connection.clearMessageBuffer(1);

		t.join();
	}

	public void testOnRPC() throws Exception
	{
		RPCService rpcService = new RPCService();

		JacksonStringSerializer serializer = new JacksonStringSerializer();
		rpcService.setSerializer(serializer);

		MockRPCHandler rpcHandler = new MockRPCHandler();
		rpcService.setRpcHandler(rpcHandler);

		MockConnection connection = new MockConnection(true, true);
		connection.setProtocol(new MockProtocol());

		RPCCall call1 = new RPCCall("dummy", "doX", null);
		RPCCall call2 = new RPCCall("dummy", "doX", new Object[] { "lala", 5 });

		RPCMessage message1 = new RPCMessage(connection, 1L, null, call1);
		RPCMessage message2 = new RPCMessage(connection, 2L, null, call2);

		rpcService.onMessage(connection, serializer.serialize(message1, new Object[0]));
		assertEquals(1, rpcHandler.getCalls().size());
		assertEquals(call1, rpcHandler.getCalls().get(0));
		rpcHandler.getCalls().clear();

		rpcService.onMessage(connection, serializer.serialize(message2, new Object[0]));
		assertEquals(1, rpcHandler.getCalls().size());
		assertEquals(call2, rpcHandler.getCalls().get(0));
		rpcHandler.getCalls().clear();
	}

	public static class ResultPOJO implements Mapable<ResultPOJO>
	{
		private Object	value;

		public ResultPOJO()
		{
			super();
		}

		public Object getValue()
		{
			return value;
		}

		public void setValue(Object value)
		{
			this.value = value;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			ResultPOJO other = (ResultPOJO) obj;
			if(value == null)
			{
				if(other.value != null)
					return false;
			}
			else if(!value.equals(other.value))
				return false;
			return true;
		}

		@Override
		public Map<String, ?> toMap(Object... authorities)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", value);
			return map;
		}

		@Override
		public ResultPOJO fromMap(Map<String, ?> map, Object... authorities)
		{
			this.value = map.get("value");
			return this;
		}

	}
}
