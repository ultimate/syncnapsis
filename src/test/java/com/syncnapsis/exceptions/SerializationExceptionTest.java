package com.syncnapsis.exceptions;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.utils.serialization.JacksonStringSerializer;

public class SerializationExceptionTest extends LoggerTestCase
{
	public void testSerializationException() throws Exception
	{
		Entity e = new Entity();
		e.entity = e;
		String s = null;
		try
		{
			s = new JacksonStringSerializer().serialize(e);
			fail("expected SerializationException");
		}
		catch(SerializationException ex)
		{
			assertNotNull(ex);
			assertNull(s);
		}
	}
	
	public static class Entity 
	{
		public Entity entity;
	}
}
