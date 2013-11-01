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
package com.syncnapsis.utils.constants;

import java.util.Arrays;
import java.util.List;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

/**
 * @author ultimate
 * 
 */
@TestCoversClasses({ ConstantLoader.class })
public class ConstantLoaderTest extends LoggerTestCase
{
	public void testScanClasses() throws Exception
	{
		List<Class<?>> classes = Arrays.asList(new Class<?>[] { A.class, B.class });

		List<Constant<String>> stringConstants;
		List<Constant<Number>> numberConstants;

		stringConstants = ConstantLoader.scanClasses(classes, String.class);
		assertEquals(4, stringConstants.size());
		assertTrue(stringConstants.contains(A.A_string_public));
		assertTrue(stringConstants.contains(A.A_string_private));
		assertTrue(stringConstants.contains(B.B_string_public));
		assertTrue(stringConstants.contains(B.B_string_private));

		numberConstants = ConstantLoader.scanClasses(classes, Number.class);
		assertEquals(4, numberConstants.size());
		assertTrue(numberConstants.contains(A.A_number_public));
		assertTrue(numberConstants.contains(A.A_number_private));
		assertTrue(numberConstants.contains(B.B_number_public));
		assertTrue(numberConstants.contains(B.B_number_private));
	}

	@TestCoversMethods({ "load", "*etConstantClasses", "getConstantRawType", "getConstants", "afterPropertiesSet" })
	public void testInit() throws Exception
	{
		ConstantLoader<String> cl = new ConstantLoader<String>(String.class) {
			@Override
			public void load(Constant<String> constant)
			{
				constant.define("123");
			}
		};

		assertEquals(String.class, cl.getConstantRawType());
		assertNull(cl.getConstantClasses());
		assertNull(cl.getConstants());

		cl.setConstantClasses(new String[] { A.class.getName(), B.class.getName() });
		cl.afterPropertiesSet();
		assertNotNull(cl.getConstantClasses());
		assertEquals(2, cl.getConstantClasses().size());
		assertEquals(A.class, cl.getConstantClasses().get(0));
		assertEquals(B.class, cl.getConstantClasses().get(1));
		assertNotNull(cl.getConstants());
		assertEquals(4, cl.getConstants().size());

		cl.setConstantClasses(Arrays.asList(new Class<?>[] { A.class, B.class }));
		cl.afterPropertiesSet();
		assertNotNull(cl.getConstantClasses());
		assertEquals(2, cl.getConstantClasses().size());
		assertEquals(A.class, cl.getConstantClasses().get(0));
		assertEquals(B.class, cl.getConstantClasses().get(1));
		assertNotNull(cl.getConstants());
		assertEquals(4, cl.getConstants().size());
	}

	public static class A
	{
		public static final StringConstant	A_string_public		= new StringConstant("A_string_public");
		private static final StringConstant	A_string_private	= new StringConstant("A_string_private");
		public static final NumberConstant	A_number_public		= new NumberConstant("A_number_public");
		private static final NumberConstant	A_number_private	= new NumberConstant("A_number_private");
	}

	public static class B
	{
		public static final Constant<String>	B_string_public		= new StringConstant("B_string_public");
		private static final Constant<String>	B_string_private	= new StringConstant("B_string_private");
		public static final Constant<Number>	B_number_public		= new NumberConstant("B_number_public");
		private static final Constant<Number>	B_number_private	= new NumberConstant("B_number_private");
	}
}
