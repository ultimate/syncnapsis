package com.syncnapsis.websockets.service.rpc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.utils.serialization.BaseSerializer;

public class GenericRPCHandlerTest extends LoggerTestCase
{
	private final BaseSerializer<String> serializer = new BaseSerializer<String>() {
		// formatter:off
		@Override
		public String serialize(Object prepared) {return null;}
		@Override
		public Map<String, Object> deserialize(String serialization) {return null;}
		// formatter:on
	};

	public void testFindMethodAndConvertArgs() throws Exception
	{
		GenericRPCHandler rpcHandler = new GenericRPCHandler() {

			@Override
			public Object getTarget(String objectName)
			{
				return null;
			}
		};

		@SuppressWarnings("unused")
		Object tmp = new Object() {
			// @formatter:off
			public void doSomething1(Long id, String name)	{}
			public void doSomething2(String x, String... args)	{}
			public void doSomething3(int i, POJO p)	{}
			// @formatter:on
		};
		Class<?> cls = tmp.getClass();

		Method expected;

		expected = cls.getMethod("doSomething1", Long.class, String.class);
		// valid
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1L, "a" }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1L, null }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, "a" }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, null }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, "a" }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1.0, "a" }));
		// invalid
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, 1 }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", "a" }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, "a", null }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1 }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a" }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null }));

		expected = cls.getMethod("doSomething2", String.class, String[].class);
		// valid
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a" }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", "b" }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", "b", "c" }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", null, "c" }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", "b", null }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", null, null }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", new String[] { "b", "c" } }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", new String[] { "b" } }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", (String[]) null }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, "b" }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, "b", "c" }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, null, "c" }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, "b", null }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, null, null }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, new String[] { "b", "c" } }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, new String[] { "b" } }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, (String[]) null }));
		// invalid
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, 1 }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1 }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, "a" }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", 1 }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, "a", null }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", 1, null }));

		POJO p = new POJO(1, "eins");
		Map<String, Object> pm = serializer.getMapper().toMap(p);
		assertEquals(p.getId(), pm.get("id"));
		assertEquals(p.getName(), pm.get("name"));
		Map<String, Object> im = new HashMap<String, Object>();

		expected = cls.getMethod("doSomething3", int.class, POJO.class);
		// valid
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, p }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, null }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, pm }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1L, null }));
		assertEquals(expected, rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1.0, null }));
		// invalid
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1 }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, p }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, null }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, pm }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { im, p }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { im, null }));
		assertNull(rpcHandler.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { im, pm }));
	}
	public static class POJO
	{
		private int		id;
		private String	name;

		public POJO()
		{
			super();
		}

		public POJO(int id, String name)
		{
			super();
			this.id = id;
			this.name = name;
		}

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

	};

}
