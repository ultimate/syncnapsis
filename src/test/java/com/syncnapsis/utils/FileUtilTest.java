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
