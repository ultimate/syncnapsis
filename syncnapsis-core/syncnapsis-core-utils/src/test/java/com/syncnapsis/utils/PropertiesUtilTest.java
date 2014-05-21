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

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.syncnapsis.tests.LoggerTestCase;

public class PropertiesUtilTest extends LoggerTestCase
{
	public void testLoadProperties() throws IOException
	{
		File file = new File("src/test/resources/test.properties");
		Properties p = PropertiesUtil.loadProperties(file);
		assertNotNull(p.getProperty("testkey"));
		assertEquals("testvalue", p.getProperty("testkey"));
	}
	
	public void testLoadPropertiesMultiline() throws Exception
	{
		File file = new File("src/test/resources/mail.properties");
		Properties p = PropertiesUtil.loadProperties(file);
		assertNotNull(p.getProperty("template.register.text"));
		String expected = "Hello {user},\n" + 
				"thank you for your registration and welcome to syncnapsis.\n" + 
				"Please confirm your registration by clicking the following link:\n" + 
				"http://www.syncnapsis.com/test/activate/{code}\n" + 
				"Best regards"; 
		assertEquals(expected, p.getProperty("template.register.text"));
	}
}
