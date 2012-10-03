package com.syncnapsis.test;

import java.io.File;
import java.io.ObjectOutputStream;

import org.dbunit.util.Base64;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import com.syncnapsis.utils.StringUtil;

public class TestSomething
{
	public static void main(String[] args) throws Exception
	{
		System.out.println(StringUtil.encodePassword("admin", "SHA"));
		System.out.println(StringUtil.encodePassword("user1", "SHA"));
		System.out.println(StringUtil.encodePassword("user2", "SHA"));
		
		ByteOutputStream bos = new ByteOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		os.writeObject(new Object[]{});
		os.flush();
		os.close();
		System.out.println(StringUtil.toHexString(bos.getBytes(), 0, bos.size()));
		System.out.println(StringUtil.toHexString(bos.getBytes(), 0, bos.getCount()));
		
		System.out.println(Base64.encodeBytes(bos.getBytes(), 0, bos.getCount()));
		
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
