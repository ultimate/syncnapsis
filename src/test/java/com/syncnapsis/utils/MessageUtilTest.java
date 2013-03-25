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

import java.util.HashMap;
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
				
		Map<String, String> values = new HashMap<String, String>();
		values.put("user", user);
		values.put("link", link);
		values.put("provider", provider);
		values.put("unused", "something addtional");
		
		String template = "Hello {user},\n" +
				"Thank you for your registration.\n" +
				"For verification please click the following link:\n" +
				"<a href=\"{link}\">{link}</a>\n" +
				"Have fun!\n" +
				"You {provider}-team";
		String expected = "Hello " + user + ",\n" +
				"Thank you for your registration.\n" +
				"For verification please click the following link:\n" +
				"<a href=\"" + link + "\">" + link + "</a>\n" +
				"Have fun!\n" +
				"You " + provider + "-team";
		
		assertEquals(expected, MessageUtil.fromTemplate(template, values));
	}
}
