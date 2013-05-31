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

import com.syncnapsis.tests.LoggerTestCase;

public class GeneratorTest extends LoggerTestCase
{
	public void testGenerate()
	{
		long seed = 23453824523849390L;
		Generator<Integer> g1 = new TestGenerator(seed);
		Generator<Integer> g2 = new TestGenerator(new ExtendedRandom(seed));
		Generator<Integer> g3 = new TestGenerator();
		
		int i1, i2, i3;
		for(int i = 0; i < 100; i++)
		{
			i1 = g1.generate();
			i2 = g2.generate();
			i3 = g3.generate();
			
			assertTrue(i1 == i2);
			assertFalse(i1 == i3);
		}
	}

	private static class TestGenerator extends Generator<Integer>
	{
		public TestGenerator()
		{
			super();
		}

		public TestGenerator(ExtendedRandom random)
		{
			super(random);
		}

		public TestGenerator(long seed)
		{
			super(seed);
		}

		@Override
		public Integer generate(ExtendedRandom random, Object... args)
		{
			return random.nextInt();
		}
	}
}
