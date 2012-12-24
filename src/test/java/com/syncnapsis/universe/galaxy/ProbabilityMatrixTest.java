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
package com.syncnapsis.universe.galaxy;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods({"get*", "set*", "hashCode"})
public class ProbabilityMatrixTest extends LoggerTestCase
{
	@TestCoversMethods({"checkRange"})
	public void testProbabilityMatrixIndexes() throws Exception
	{
		logger.debug("testing probabilityMatrixIndexes...");

		ProbabilityMatrix m = new ProbabilityMatrix(3, 4, 5);
		double p = 0;
		for(int x = m.getXMin() - 1; x <= m.getXMax() + 1; x++)
		{
			for(int y = m.getYMin() - 1; y <= m.getYMax() + 1; y++)
			{
				for(int z = m.getZMin() - 1; z <= m.getZMax() + 1; z++)
				{
					try
					{
						m.setProbability(x, y, z, p++);
						assertTrue(x >= m.getXMin());
						assertTrue(x <= m.getXMax());
						assertTrue(y >= m.getYMin());
						assertTrue(y <= m.getYMax());
						assertTrue(z >= m.getZMin());
						assertTrue(z <= m.getZMax());
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

		ProbabilityMatrix m1 = new ProbabilityMatrix(3, 2, 1);
		m1.setProbability(-1, 0, 0, 0.1);
		m1.setProbability(-1, 1, 0, 0.2);
		m1.setProbability(0, 0, 0, 0.3);
		m1.setProbability(0, 1, 0, 0.4);
		m1.setProbability(1, 0, 0, 0.5);
		m1.setProbability(1, 1, 0, 0.6);
		ProbabilityMatrix m2 = new ProbabilityMatrix(3, 2, 1);
		m2.setProbability(-1, 0, 0, 0.9);
		m2.setProbability(-1, 1, 0, 0.8);
		m2.setProbability(0, 0, 0, 0.7);
		m2.setProbability(0, 1, 0, 0.6);
		m2.setProbability(1, 0, 0, 0.5);
		m2.setProbability(1, 1, 0, 0.4);

		m1.addMatrix(m2);

		for(int x = -1; x < 1; x++)
			for(int y = 0; y < 1; y++)
				assertEquals(1.0, m1.getProbability(x, y, 0), 0.000001);

		ProbabilityMatrix m3 = new ProbabilityMatrix(4, 5, 6);
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

	@TestCoversMethods({"norm", "*Probabilities", "*Probability"})
	public void testNorm() throws Exception
	{
		logger.debug("testing norm...");

		ProbabilityMatrix m1 = new ProbabilityMatrix(3, 2, 1);
		m1.setProbability(-1, 0, 0, 0.1);
		m1.setProbability(-1, 1, 0, 0.2);
		m1.setProbability(0, 0, 0, 0.3);
		m1.setProbability(0, 1, 0, 0.4);
		m1.setProbability(1, 0, 0, 0.5);
		m1.setProbability(1, 1, 0, 0.6);

		long expected = 4;
		m1.norm(expected);

		assertEquals((double) expected, m1.sumOfProbabilities(), 0.000001);
		assertEquals(1.0, m1.normedSumOfProbabilities(expected), 0.000001);
		assertEquals(expected, m1.sumOfProbabilities(), 0.000001);
	}
	
	public void testEquals() throws Exception
	{
		ProbabilityMatrix m1 = new ProbabilityMatrix(3, 2, 1);
		int count1 = 0;
		for(int x = m1.getXMin(); x <= m1.getXMax(); x++)
		{
			for(int y = m1.getYMin(); y <= m1.getYMax(); y++)
			{
				for(int z = m1.getZMin(); z <= m1.getZMax(); z++)
				{
					m1.setProbability(x, y, z, count1++);					
				}
			}
		}

		ProbabilityMatrix m2 = new ProbabilityMatrix(3, 2, 1);
		int count2 = 0;
		for(int x = m2.getXMin(); x <= m2.getXMax(); x++)
		{
			for(int y = m2.getYMin(); y <= m2.getYMax(); y++)
			{
				for(int z = m2.getZMin(); z <= m2.getZMax(); z++)
				{
					m2.setProbability(x, y, z, count2++);					
				}
			}
		}
		
		ProbabilityMatrix m3 = new ProbabilityMatrix(3, 2, 1);
		ProbabilityMatrix m4 = new ProbabilityMatrix(1, 2, 3);
		ProbabilityMatrix m5 = new ProbabilityMatrix(1, 2, 3);
		
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
		ProbabilityMatrix m1 = new ProbabilityMatrix(3, 2, 1);
		int count = 0;
		for(int x = m1.getXMin(); x <= m1.getXMax(); x++)
		{
			for(int y = m1.getYMin(); y <= m1.getYMax(); y++)
			{
				for(int z = m1.getZMin(); z <= m1.getZMax(); z++)
				{
					m1.setProbability(x, y, z, count++);					
				}
			}
		}
		
		ProbabilityMatrix m2 = m1.clone();
		
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
					assertEquals(count2++, (int) m1.getProbability(x, y, z));
				}
			}
		}
	}
}
