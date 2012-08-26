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
}
