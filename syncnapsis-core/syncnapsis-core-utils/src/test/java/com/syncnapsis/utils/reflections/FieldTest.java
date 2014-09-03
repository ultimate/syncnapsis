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
package com.syncnapsis.utils.reflections;

import java.util.List;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ReflectionsUtil;

@SuppressWarnings("unused")
@TestExcludesMethods({"toString", "get*" })
public class FieldTest extends LoggerTestCase
{
	@TestCoversMethods({"set", "get"})
	public void testField_valid() throws Exception
	{
		Object valid = new Object() {
			private int	x;

			public int getX()
			{
				return x;

			}

			public void setX(int x)
			{
				this.x = x;
			}
		};

		List<Field> fields = ReflectionsUtil.findDefaultFields(valid.getClass());
		assertEquals(1, fields.size());

		Field f = fields.get(0);
		
		int value = (int) (Math.random()*1000);
		f.set(valid, value);
		assertEquals(value, f.get(valid));
	}

	public void testField_invalid() throws Exception
	{
		Object valid = new Object() {
			private int	x;

			public String getX()
			{
				return "" + x;

			}

			public void setX(int x)
			{
				this.x = x;
			}
		};

		List<Field> fields = ReflectionsUtil.findDefaultFields(valid.getClass());
		assertEquals(0, fields.size());
	}
}
