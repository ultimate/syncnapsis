package com.syncnapsis.test;

import java.io.File;

import org.dbunit.util.Base64;

import com.syncnapsis.utils.StringUtil;
import com.syncnapsis.tests.annotations.Untested;

@Untested
public class TestSomething
{
	public static void main(String[] args) throws Exception
	{
		System.out.println(StringUtil.encodePassword("admin", "SHA"));
		System.out.println(StringUtil.encodePassword("user1", "SHA"));
		System.out.println(StringUtil.encodePassword("user2", "SHA"));
		
		System.out.println(Base64.encodeObject(new Object[]{}));
		
		System.out.println(new File("").getAbsolutePath());
		System.out.println(new File(".").getAbsolutePath());
		System.out.println(new File("/").getAbsolutePath());
		System.out.println(new File("\\").getAbsolutePath());
		
		System.out.println(new File("a").getAbsolutePath());
		System.out.println(new File("/a").getAbsolutePath());
		System.out.println(new File("\\a").getAbsolutePath());
		
		System.out.println(new File("..").getAbsolutePath());
		System.out.println(new File("/..").getAbsolutePath());
		System.out.println(new File("\\..").getAbsolutePath());
		
		System.out.println(new File("../a").getAbsolutePath());
		System.out.println(new File("..\\a").getAbsolutePath());
		System.out.println(new File("/../a").getAbsolutePath());
		System.out.println(new File("\\..\\a").getAbsolutePath());
		
		System.out.println(new File("../syncnapsis-universe-evolution").getPath());
		System.out.println(new File("../syncnapsis-universe-evolution").getAbsolutePath());
		System.out.println(new File("../syncnapsis-universe-evolution").exists());
		
		System.out.println(new File("../../syncnapsis-modules").getPath());
		System.out.println(new File("../../syncnapsis-modules").getAbsolutePath());
		System.out.println(new File("../../syncnapsis-modules").exists());
	}
}
