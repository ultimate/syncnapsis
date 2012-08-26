package com.syncnapsis.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class FileUtilTest extends LoggerTestCase
{
	@TestCoversMethods({"list*"})
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
	
	public void testCopyFile() throws Exception
	{
		File source = new File("pom.xml");
		assertTrue(source.exists());
		
		File target = new File("tmp.xml");
		assertFalse(target.exists());
		
		FileUtil.copyFile(source, target);
		
		int c;

		InputStream is_source = new BufferedInputStream(new FileInputStream(source));
		StringBuilder sb_source = new StringBuilder();
		while((c = is_source.read()) != -1)
		{
			sb_source.append((char) c);
		}
		is_source.close();

		InputStream is_target = new BufferedInputStream(new FileInputStream(target));
		StringBuilder sb_target = new StringBuilder();
		while((c = is_target.read()) != -1)
		{
			sb_target.append((char) c);
		}
		is_target.close();
		
		assertEquals(sb_source.toString(), sb_target.toString());
		
		assertTrue(target.delete());
	}
	
	public void testGetExtension() throws Exception
	{
		assertEquals(".xml", FileUtil.getExtension(new File("pom.xml")));
		assertEquals(".md", FileUtil.getExtension(new File("README.md")));
		assertEquals(".gitignore", FileUtil.getExtension(new File(".gitignore")));
		assertNull(FileUtil.getExtension(new File("src")));
	}
}
