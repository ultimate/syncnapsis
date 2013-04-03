/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
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
package com.syncnapsis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syncnapsis.tests.LoggerTestCase;

/**
 * @author ultimate
 */
public class MessageUtilTest extends LoggerTestCase
{
	public void testFromTemplate() throws Exception
	{
		String user = "new guy";
		String link = "www.syncnapsis.com/for/bar";
		String provider = "syncnapsis";

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("user", user);
		values.put("link", link);
		values.put("provider", provider);
		values.put("unused", "something addtional");

		String template = "Hello {user},\n" + "Thank you for your registration.\n" + "For verification please click the following link:\n"
				+ "<a href=\"{link}\">{link}</a>\n" + "Have fun!\n" + "You {provider}-team";
		String expected = "Hello " + user + ",\n" + "Thank you for your registration.\n" + "For verification please click the following link:\n"
				+ "<a href=\"" + link + "\">" + link + "</a>\n" + "Have fun!\n" + "You " + provider + "-team";

		assertEquals(expected, MessageUtil.fromTemplate(template, values));
		
		// check for recursice replacement of keys in values, too 
		values.put("link", "link/for/{user}/to/click");
		expected = "Hello " + user + ",\n" + "Thank you for your registration.\n" + "For verification please click the following link:\n"
				+ "<a href=\"link/for/" + user + "/to/click\">link/for/" + user + "/to/click</a>\n" + "Have fun!\n" + "You " + provider + "-team";
		
		assertEquals(expected, MessageUtil.fromTemplate(template, values));
	}

	public void testGetUsedTemplateKeys() throws Exception
	{
		String key1 = "foo";
		String key2 = "bar";
		String t1 = "abc {" + key1 + "}\n123 {" + key2 + "}";
		String t2 = "abc {" + key1 + "{" + key2 + "}";
		String t3 = "abc {" + key1 + "{" + key2 + "}" + key1 + "}";

		List<String> keys;

		// regular keys
		keys = MessageUtil.getUsedTemplateKeys(t1);
		assertNotNull(keys);
		assertEquals(2, keys.size());
		assertTrue(keys.contains(key1));
		assertTrue(keys.contains(key2));

		// nested keys
		keys = MessageUtil.getUsedTemplateKeys(t2);
		assertNotNull(keys);
		assertEquals(2, keys.size());
		assertFalse(keys.contains(key1));
		assertTrue(keys.contains(key2));
		assertTrue(keys.contains(key1 + "{" + key2));

		// double nested keys
		keys = MessageUtil.getUsedTemplateKeys(t3);
		assertNotNull(keys);
		assertEquals(2, keys.size());
		assertFalse(keys.contains(key1));
		assertTrue(keys.contains(key2));
		assertTrue(keys.contains(key1 + "{" + key2));
	}

	public void testExtractValues() throws Exception
	{
		POJO1 p1 = new POJO1();
		p1.name = "p1";
		p1.value = 1;

		POJO2 p21 = new POJO2();
		p21.values = new ArrayList<String>();
		p21.key = "p21";

		POJO2 p22 = new POJO2();
		p22.values = null;
		p22.key = "p22";

		p1.ref = p21;

		// @formatter:off
		String[] keys = new String[] {
			"pojo1",
			"pojo1.name",
			"pojo1.value",
			"pojo1.ref",
			"pojo1.ref.values",
			"pojo1.ref.key",
			"pojo2",
			"pojo2.values",
			"pojo2.key"
		};
		// @formatter:on
		Map<String, Object> values = MessageUtil.extractValues(Arrays.asList(keys), p1, p22);

		int i = 0;
		assertSame(p1, values.get(keys[i++]));
		assertSame(p1.name, values.get(keys[i++]));
		assertEquals(p1.value, values.get(keys[i++])); // assertSame does not work for int
		assertSame(p1.ref, values.get(keys[i++]));
		assertSame(p1.ref.values, values.get(keys[i++]));
		assertSame(p1.ref.key, values.get(keys[i++]));
		assertSame(p22, values.get(keys[i++]));
		assertSame(p22.values, values.get(keys[i++]));
		assertSame(p22.key, values.get(keys[i++]));
	}

	public static class POJO1
	{
		public String	name;
		public int		value;
		public POJO2	ref;
	}

	public static class POJO2
	{
		public List<String>	values;
		public String		key;
	}
}
