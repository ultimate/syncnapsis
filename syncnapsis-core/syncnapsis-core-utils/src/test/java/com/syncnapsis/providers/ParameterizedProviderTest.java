/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
