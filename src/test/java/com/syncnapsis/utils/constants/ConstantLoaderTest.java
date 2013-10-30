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
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.data.ConstantLoader;
import com.syncnapsis.utils.data.constants.Constant;
import com.syncnapsis.utils.data.constants.NumberConstant;
import com.syncnapsis.utils.data.constants.StringConstant;

/**
 * @author ultimate
 * 
 */
@TestCoversClasses({ ConstantLoader.class, Constant.class, StringConstant.class, NumberConstant.class })
@TestExcludesMethods({ "load", "set*", "get*" })
public class ConstantLoaderTest extends LoggerTestCase
{
	public void testScanClasses() throws Exception
	{
		List<Class<?>> classes = Arrays.asList(new Class<?>[] {A.class, B.class});
		
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
	
	public static class A
	{
		public static final StringConstant A_string_public = new StringConstant("A_string_public");
		private static final StringConstant A_string_private = new StringConstant("A_string_private");
		public static final NumberConstant A_number_public = new NumberConstant("A_number_public");
		private static final NumberConstant A_number_private = new NumberConstant("A_number_private");
	}
	
	public static class B
	{
		public static final Constant<String> B_string_public = new StringConstant("B_string_public");
		private static final Constant<String> B_string_private = new StringConstant("B_string_private");
		public static final Constant<Number> B_number_public = new NumberConstant("B_number_public");
		private static final Constant<Number> B_number_private = new NumberConstant("B_number_private");
	}
}
