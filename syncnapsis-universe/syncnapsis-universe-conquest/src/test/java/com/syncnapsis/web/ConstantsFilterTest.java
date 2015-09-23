package com.syncnapsis.web;

import java.io.File;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.FileUtil;
import com.syncnapsis.utils.serialization.JSONGenerator;

@TestExcludesMethods({ "prepare", "requiresPreparation", "doFilter", "afterPropertiesSet"})
public class ConstantsFilterTest extends LoggerTestCase
{
	public void testPrepare() throws Exception
	{
		ConstantsFilter filter = new ConstantsFilter();

		File constantsFile = new File("constants.js");
		String expected = JSONGenerator.createConstantsJS();

		try
		{
			assertFalse(constantsFile.exists());
			
			filter.prepare(constantsFile, null);
			
			assertTrue(constantsFile.exists());
			String result = FileUtil.readFile(constantsFile);
			
			assertEquals(expected, result);
		}
		finally
		{
			if(constantsFile.exists())
				constantsFile.delete();
		}
	}
}
