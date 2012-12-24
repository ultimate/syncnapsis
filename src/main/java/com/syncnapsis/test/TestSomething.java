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
