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

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class ClassPathUtilTest extends LoggerTestCase
{
	private final String	packageName	= "com.syncnapsis";

	@TestCoversMethods("add*")
	public void testAddURLToClassPath() throws Exception
	{
		String className = packageName + ".utils.dev.CodeScanner";
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
		File f = new File(workingDir.getAbsoluteFile().getParentFile().getParentFile().getParentFile().getAbsolutePath() + "/syncnapsis-dev/syncnapsis-dev-utils/target/classes/");
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
