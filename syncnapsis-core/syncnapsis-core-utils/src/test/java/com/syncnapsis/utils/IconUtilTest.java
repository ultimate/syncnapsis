/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 */
@TestExcludesMethods({ "round" })
public class IconUtilTest extends LoggerTestCase
{
	private Random random = new Random();
	
	public void testCreateIcon() throws Exception
	{
		StringBuilder sb = new StringBuilder();
		String icon;
		for(int i = 0; i < 10; i++)
		{
			sb.append((char) random.nextInt(256));
			icon = IconUtil.createIcon(sb.toString());
			assertNotNull(icon);
			assertTrue(icon.contains("<circle id=\"0\""));
			assertTrue(icon.contains("<polygon id=\"3\""));
			assertTrue(icon.contains("<polygon id=\"4\""));
			assertTrue(icon.contains("<use xlink:href=\"#"));
		}
	}
	
	public void testCreateIconElements() throws Exception
	{
		StringBuilder sb = new StringBuilder();
		String elements;
		int count;
		List<Map<String, Number>> parameters;
		for(int i = 0; i < 10; i++)
		{
			sb.append((char) random.nextInt(256));
			parameters = IconUtil.parameterize(sb.toString());
			
			assertTrue(parameters.size() > 0);
			
			count = 0;
			for(Map<String, Number> p: parameters)
			{
				count += (Integer) p.get(IconUtil.KEY_COUNT);
			}

			elements = IconUtil.createIconElements(sb.toString());
			assertEquals(elements, IconUtil.createIconElements(parameters));
			
			assertNotNull(elements);
			assertTrue(count > 0);
			assertEquals(count, StringUtil.countOccurrences(elements, "<use xlink:href=\"#", false));
		}
	}
	
	public void testParameterize() throws Exception
	{
		StringBuilder sb = new StringBuilder();
		List<Map<String, Number>> parameters;
		for(int i = 0; i < 10; i++)
		{
			sb.append((char) random.nextInt(256));
			parameters = IconUtil.parameterize(sb.toString());
			
			assertTrue(parameters.size() > 0);
			// don't check the numbers but assert all keys are present
			for(Map<String, Number> p: parameters)
			{
				assertTrue(p.containsKey(IconUtil.KEY_BORDER));
				assertTrue(p.containsKey(IconUtil.KEY_COUNT));
				assertTrue(p.containsKey(IconUtil.KEY_HREF));
				assertTrue(p.containsKey(IconUtil.KEY_OFFSET));
				assertTrue(p.containsKey(IconUtil.KEY_ROTATION));
				assertTrue(p.containsKey(IconUtil.KEY_SCALE));
				assertTrue(p.containsKey(IconUtil.KEY_STYLE));
			}
		}
	}
	
	public void testGetByte() throws Exception
	{
		for(byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++)
		{
			assertTrue(IconUtil.getByte(new byte[]{b}, random.nextInt(100), random.nextInt(100)) >= 0);
		}
	}
}
