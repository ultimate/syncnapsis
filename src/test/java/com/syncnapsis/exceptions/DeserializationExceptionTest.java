package com.syncnapsis.exceptions;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.utils.serialization.JacksonStringSerializer;

public class DeserializationExceptionTest extends LoggerTestCase
{
	public void testDeserializationException() throws Exception
	{
		Object o = null;
		try
		{
			o = new JacksonStringSerializer().deserialize("{]");
			fail("expected DeserializationException");
		}
		catch(DeserializationException e)
		{
			assertNotNull(e);
			assertNull(o);
		}
	}
}
