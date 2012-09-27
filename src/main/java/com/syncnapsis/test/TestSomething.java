package com.syncnapsis.test;

import java.io.File;

public class TestSomething
{
	public static void main(String[] args) throws Exception
	{
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
