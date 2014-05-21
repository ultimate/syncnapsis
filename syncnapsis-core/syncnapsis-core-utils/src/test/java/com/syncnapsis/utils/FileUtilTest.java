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
import java.util.List;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class FileUtilTest extends LoggerTestCase
{
	@TestCoversMethods({ "list*" })
	public void testListFilesIncludingSubfolders() throws Exception
	{
		File c = new File("a/b/c");
		c.mkdirs();
		File b = c.getParentFile();
		File a = b.getParentFile();
		List<File> files = FileUtil.listFilesIncludingSubfoldersAsList(a);
		assertFalse(files.contains(a));
		assertTrue(files.contains(b));
		assertTrue(files.contains(c));
		assertTrue(c.delete());
		assertTrue(b.delete());
		assertTrue(a.delete());
	}

	@TestCoversMethods({"copyFile", "readFile" })
	public void testCopyAndReadFile() throws Exception
	{
		File source = new File("pom.xml");
		assertTrue(source.exists());

		File target = new File("tmp.xml");
		assertFalse(target.exists());

		FileUtil.copyFile(source, target);

		assertEquals(FileUtil.readFile(source), FileUtil.readFile(target));

		assertTrue(target.delete());
	}

	@TestCoversMethods({ "readFile", "writeFile" })
	public void testWriteAndReadFile() throws Exception
	{
		String content = "a very senseful text:\ntxet lufesnes yrev a";
		
		File tmp = new File("tmp.txt");
		
		FileUtil.writeFile(tmp, content);		
		String content2 = FileUtil.readFile(tmp);
		
		assertEquals(content, content2);
		
		assertTrue(tmp.delete());
	}

	public void testGetExtension() throws Exception
	{
		assertEquals(".xml", FileUtil.getExtension(new File("pom.xml")));
		assertEquals(".md", FileUtil.getExtension(new File("README.md")));
		assertEquals(".gitignore", FileUtil.getExtension(new File(".gitignore")));
		assertNull(FileUtil.getExtension(new File("src")));
	}
}
