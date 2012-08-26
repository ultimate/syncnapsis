package com.syncnapsis.utils;

import java.io.File;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class ClassPathUtilTest extends LoggerTestCase
{
	private final String	packageName	= "com.syncnapsis";

	@TestCoversMethods("add*")
	public void testAddURLToClassPath() throws Exception
	{
		String className = packageName + ".data.model.base.Model";
		Class<?> cls;
		
		try
		{
			logger.debug("looking for non-existing class '" + className + "'");
			cls = Class.forName(className);
			fail("expected ClassNotFoundException");
		}
		catch(ClassNotFoundException e)
		{
			logger.debug("non-existing class not found!");
			assertNotNull(e);
		}
		
		logger.debug("adding path to classpath");
		
		File workingDir = new File(".");
		File f = new File(workingDir.getAbsoluteFile().getParentFile().getParentFile().getAbsolutePath() + "/syncnapsis-core-data/target/classes/");
		logger.debug(f.getAbsolutePath());
		assertTrue(f.exists());
		
		ClassPathUtil.addFileToClassPath(f);

		try
		{
			logger.debug("looking for added class '" + className + "'");
			cls = Class.forName(className);
			assertNotNull(cls);
			logger.debug("class successfully found!");
		}
		catch(ClassNotFoundException e)
		{
			fail("ClassNotFoundException");
		}
	}
}
