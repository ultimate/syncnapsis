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
public class VectorTest extends LoggerTestCase
{
	public void testGenericTypes() throws Exception
	{
		assertEquals(Integer.class, ReflectionsUtil.getActualTypeArguments(new Vector.Integer(), Vector.class)[0]);
		assertEquals(Long.class, ReflectionsUtil.getActualTypeArguments(new Vector.Long(), Vector.class)[0]);
		assertEquals(Float.class, ReflectionsUtil.getActualTypeArguments(new Vector.Float(), Vector.class)[0]);
		assertEquals(Double.class, ReflectionsUtil.getActualTypeArguments(new Vector.Double(), Vector.class)[0]);
	}
}
