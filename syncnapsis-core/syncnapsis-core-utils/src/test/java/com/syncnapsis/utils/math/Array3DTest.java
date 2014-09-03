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
package com.syncnapsis.utils.math;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.math.Array3D;

/**
 * Test for {@link Array3D}
 * 
 * @author ultimate
 */
@TestExcludesMethods({ "hashCode" })
public class Array3DTest extends LoggerTestCase
{
	@TestCoversMethods({ "init", "get*", "set*", "checkRange" })
	public void testIndexes() throws Exception
	{
		logger.debug("testing probabilityMatrixIndexes...");

		int xSize = 3;
		int ySize = 4;
		int zSize = 5;

		Array3D m;

		logger.debug("matrix by size");
		m = new Array3D(xSize, ySize, zSize);
		assertEquals(xSize, m.getXSize());
		assertEquals(ySize, m.getYSize());
		assertEquals(zSize, m.getZSize());

		logger.debug("index test...");
		indexTest(m);

		int xMin = -2;
		int xMax = 5;
		int yMin = -4;
		int yMax = 10;
		int zMin = -6;
		int zMax = 15;

		logger.debug("matrix by min/max");
		m = new Array3D(xMin, xMax, yMin, yMax, zMin, zMax);
		assertEquals(xMin, m.getXMin());
		assertEquals(xMax, m.getXMax());
		assertEquals(yMin, m.getYMin());
		assertEquals(yMax, m.getYMax());
		assertEquals(zMin, m.getZMin());
		assertEquals(zMax, m.getZMax());

		logger.debug("index test...");
		indexTest(m);
	}

	private void indexTest(Array3D m) throws Exception
	{
		double p = 0;
		for(int x = m.getXMin() - 1; x <= m.getXMax() + 1; x++)
		{
			for(int y = m.getYMin() - 1; y <= m.getYMax() + 1; y++)
			{
				for(int z = m.getZMin() - 1; z <= m.getZMax() + 1; z++)
				{
					try
					{
						m.set(x, y, z, p++);
						assertTrue(x >= m.getXMin());
						assertTrue(x <= m.getXMax());
						assertTrue(y >= m.getYMin());
						assertTrue(y <= m.getYMax());
						assertTrue(z >= m.getZMin());
						assertTrue(z <= m.getZMax());
						assertEquals(p - 1, m.get(x, y, z));
					}
					catch(IllegalArgumentException e)
					{
						char dim = e.getMessage().charAt(0);
						int num = Integer.parseInt(e.getMessage().substring(e.getMessage().indexOf("range: ") + "range: ".length(),
								e.getMessage().indexOf(" Range:")));
						switch(dim)
						{
							case 'x':
								assertTrue(e.getMessage(), x < m.getXMin() || x > m.getXMax());
								assertTrue(e.getMessage(), x == num);
								break;
							case 'y':
								assertTrue(e.getMessage(), y < m.getYMin() || y > m.getYMax());
								assertTrue(e.getMessage(), y == num);
								break;
							case 'z':
								assertTrue(e.getMessage(), z < m.getZMin() || z > m.getZMax());
								assertTrue(e.getMessage(), z == num);
								break;
						}
					}
				}
			}
		}
	}

	public void testAddMatrix() throws Exception
	{
		logger.debug("testing addMatrix...");

		Array3D m1 = new Array3D(3, 2, 1);
		m1.set(-1, 0, 0, 0.1);
		m1.set(-1, 1, 0, 0.2);
		m1.set(0, 0, 0, 0.3);
		m1.set(0, 1, 0, 0.4);
		m1.set(1, 0, 0, 0.5);
		m1.set(1, 1, 0, 0.6);
		Array3D m2 = new Array3D(3, 2, 1);
		m2.set(-1, 0, 0, 0.9);
		m2.set(-1, 1, 0, 0.8);
		m2.set(0, 0, 0, 0.7);
		m2.set(0, 1, 0, 0.6);
		m2.set(1, 0, 0, 0.5);
		m2.set(1, 1, 0, 0.4);

		m1.addMatrix(m2);

		for(int x = -1; x < 1; x++)
			for(int y = 0; y < 1; y++)
				assertEquals(1.0, m1.get(x, y, 0), 0.000001);

		Array3D m3 = new Array3D(4, 5, 6);
		try
		{
			m2.addMatrix(m3);
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
	}

	@TestCoversMethods({ "norm", "*Probabilities", "*Probability" })
	public void testNorm() throws Exception
	{
		logger.debug("testing norm...");

		Array3D m1 = new Array3D(3, 2, 1);
		m1.set(-1, 0, 0, 0.1);
		m1.set(-1, 1, 0, 0.2);
		m1.set(0, 0, 0, 0.3);
		m1.set(0, 1, 0, 0.4);
		m1.set(1, 0, 0, 0.5);
		m1.set(1, 1, 0, 0.6);

		long expected = 4;
		m1.norm(expected);

		assertEquals((double) expected, m1.getSum(), 0.000001);
		assertEquals(1.0, m1.getNormedSum(expected), 0.000001);
		assertEquals(expected, m1.getSum(), 0.000001);
	}

	public void testEquals() throws Exception
	{
		Array3D m1 = new Array3D(3, 2, 1);
		int count1 = 0;
		for(int x = m1.getXMin(); x <= m1.getXMax(); x++)
		{
			for(int y = m1.getYMin(); y <= m1.getYMax(); y++)
			{
				for(int z = m1.getZMin(); z <= m1.getZMax(); z++)
				{
					m1.set(x, y, z, count1++);
				}
			}
		}

		Array3D m2 = new Array3D(3, 2, 1);
		int count2 = 0;
		for(int x = m2.getXMin(); x <= m2.getXMax(); x++)
		{
			for(int y = m2.getYMin(); y <= m2.getYMax(); y++)
			{
				for(int z = m2.getZMin(); z <= m2.getZMax(); z++)
				{
					m2.set(x, y, z, count2++);
				}
			}
		}

		Array3D m3 = new Array3D(3, 2, 1);
		Array3D m4 = new Array3D(1, 2, 3);
		Array3D m5 = new Array3D(1, 2, 3);

		assertEquals(m1, m2);

		assertEquals(m4, m5);

		assertNotSame(m1, m3);
		assertNotSame(m2, m3);
		assertNotSame(m1, m4);
		assertNotSame(m1, m5);
		assertNotSame(m2, m4);
		assertNotSame(m2, m5);
		assertNotSame(m3, m4);
		assertNotSame(m3, m5);
	}

	public void testClone() throws Exception
	{
		Array3D m1 = new Array3D(3, 2, 1);
		int count = 0;
		for(int x = m1.getXMin(); x <= m1.getXMax(); x++)
		{
			for(int y = m1.getYMin(); y <= m1.getYMax(); y++)
			{
				for(int z = m1.getZMin(); z <= m1.getZMax(); z++)
				{
					m1.set(x, y, z, count++);
				}
			}
		}

		Array3D m2 = m1.clone();

		assertFalse(m1 == m2);
		assertEquals(m1, m2);
		assertEquals(m1.getXSize(), m2.getXSize());
		assertEquals(m1.getYSize(), m2.getYSize());
		assertEquals(m1.getZSize(), m2.getZSize());
		int count2 = 0;
		for(int x = m1.getXMin(); x <= m1.getXMax(); x++)
		{
			for(int y = m1.getYMin(); y <= m1.getYMax(); y++)
			{
				for(int z = m1.getZMin(); z <= m1.getZMax(); z++)
				{
					assertEquals(count2++, (int) m1.get(x, y, z));
				}
			}
		}
	}

	@TestCoversMethods({ "getIndexes", "getIndex" })
	public void testIndexToIndexes() throws Exception
	{
		int xMin = -2;
		int xMax = 5;
		int yMin = -4;
		int yMax = 10;
		int zMin = -6;
		int zMax = 15;

		Array3D m = new Array3D(xMin, xMax, yMin, yMax, zMin, zMax);

		int index;
		int count = 0;
		int[] coords;
		for(int x = xMin; x <= xMax; x++)
		{
			for(int y = yMin; y <= yMax; y++)
			{
				for(int z = zMin; z <= zMax; z++)
				{
					index = m.getIndex(x, y, z);
					assertEquals(count, index);

					coords = m.getIndexes(index);
					assertEquals(x, coords[0]);
					assertEquals(y, coords[1]);
					assertEquals(z, coords[2]);

					count++;
				}
			}
		}
	}

	public void testAdd() throws Exception
	{
		Array3D m = new Array3D(3, 2, 1);
		m.set(-1, 0, 0, 0.1);
		m.set(-1, 1, 0, 0.2);
		m.set(0, 0, 0, 0.3);
		m.set(0, 1, 0, 0.4);
		m.set(1, 0, 0, 0.5);
		m.set(1, 1, 0, 0.6);

		double oldValue;
		double rand;
		for(int i = 0; i < m.getVolume(); i++)
		{
			oldValue = m.get(i);
			rand = Math.random();

			m.add(i, rand);

			assertTrue(oldValue + rand == m.get(i));
		}
	}

	public void testMultiply() throws Exception
	{
		Array3D m = new Array3D(3, 2, 1);
		m.set(-1, 0, 0, 0.1);
		m.set(-1, 1, 0, 0.2);
		m.set(0, 0, 0, 0.3);
		m.set(0, 1, 0, 0.4);
		m.set(1, 0, 0, 0.5);
		m.set(1, 1, 0, 0.6);

		double oldValue;
		double rand;
		for(int i = 0; i < m.getVolume(); i++)
		{
			oldValue = m.get(i);
			rand = Math.random();

			m.multiply(i, rand);

			assertTrue(oldValue * rand == m.get(i));
		}
	}
}
