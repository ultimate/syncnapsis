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
package com.syncnapsis.utils.data;

import java.util.ArrayList;
import java.util.List;

import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods({ "get*", "set*", "is*", "was*" })
public class VectorGeneratorTest extends LoggerTestCase
{
	@TestCoversMethods({ "generate", "init" })
	public void testGenerate()
	{
		VectorGenerator g = new VectorGenerator(-1, 1, -1, 1, -1, 1);
		
		List<Vector.Integer> results = new ArrayList<Vector.Integer>();
		
		Vector.Integer vec;
		for(int i = 0; i < g.getProbabilities().getVolume(); i++)
		{
			vec = g.generate();
			assertNotNull(vec);
			assertFalse(results.contains(vec));
			results.add(vec);
		}
		
		assertEquals(g.getProbabilities().getVolume(), results.size());
	}
}
