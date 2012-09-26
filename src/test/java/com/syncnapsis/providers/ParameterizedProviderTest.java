package com.syncnapsis.providers;

import java.util.HashMap;
import java.util.Map;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class ParameterizedProviderTest extends LoggerTestCase
{
	@TestCoversMethods({ "get", "set" })
	public void testGetAndSet()
	{
		final Map<String, String> map = new HashMap<String, String>();
		ParameterizedProvider<String, String> provider = new ParameterizedProvider<String, String>() {

			@Override
			public void set(String key, String value) throws UnsupportedOperationException
			{
				map.put(key, value);
			}

			@Override
			public String get(String key)
			{
				return map.get(key);
			}
		};

		String key = "key";
		String value = "value";

		provider.set(key, value);
		assertEquals(map.get(key), value);
		assertEquals(map.get(key), provider.get(key));
	}
}
