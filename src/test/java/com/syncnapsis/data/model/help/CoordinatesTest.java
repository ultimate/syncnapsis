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
package com.syncnapsis.data.model.help;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.utils.ReflectionsUtil;

/**
 * @author ultimate
 */
public class CoordinatesTest extends LoggerTestCase
{
	public void testGenericTypes() throws Exception
	{
		assertEquals(Integer.class, ReflectionsUtil.getActualTypeArguments(new Coordinates.Integer(), Coordinates.class)[0]);
		assertEquals(Long.class, ReflectionsUtil.getActualTypeArguments(new Coordinates.Long(), Coordinates.class)[0]);
		assertEquals(Float.class, ReflectionsUtil.getActualTypeArguments(new Coordinates.Float(), Coordinates.class)[0]);
		assertEquals(Double.class, ReflectionsUtil.getActualTypeArguments(new Coordinates.Double(), Coordinates.class)[0]);
	}
}
