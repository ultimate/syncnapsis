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
package com.syncnapsis.utils.math;

import javax.vecmath.Vector2d;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class StatisticsTest extends LoggerTestCase
{
	public void testBinomial() throws Exception
	{
		int[][] result = new int[10][];
		result[0] = new int[] { 1 };
		result[1] = new int[] { 1, 1 };
		result[2] = new int[] { 1, 2, 1 };
		result[3] = new int[] { 1, 3, 3, 1 };
		result[4] = new int[] { 1, 4, 6, 4, 1 };
		result[5] = new int[] { 1, 5, 10, 10, 5, 1 };
		result[6] = new int[] { 1, 6, 15, 20, 15, 6, 1 };
		result[7] = new int[] { 1, 7, 21, 35, 35, 21, 7, 1 };
		result[8] = new int[] { 1, 8, 28, 56, 70, 56, 28, 8, 1 };
		result[9] = new int[] { 1, 9, 36, 84, 126, 126, 84, 36, 9, 1 };

		for(int[] res : result)
		{
			int n = res.length - 1;
			System.out.print("n=" + n + "\t-\t");
			for(int k = 0; k <= n; k++)
			{
				System.out.print("\t" + Statistics.binomial(n, k));
				assertEquals(res[k], Statistics.binomial(n, k));
			}
			System.out.println("");
		}
	}

	@TestCoversMethods("gaussian")
	public void testGaussian_1D() throws Exception
	{
		int range = 200;
		int step = 10;
		int drawScale = 100;
		double dx;
		double g;
		for(int x = -range; x <= range; x += step)
		{
			dx = x / (double) 100;
			g = Statistics.gaussian(dx, 0, 1);
			for(int j = 0; j < drawScale; j++)
			{
				if(j / (double) drawScale < g)
					System.out.print("x");
			}
			System.out.println("");
		}

		assertEquals(0.4, Statistics.gaussian(0, 0, 1), 0.01);
		assertEquals(0.2, Statistics.gaussian(0, 0, 2), 0.01);

		assertEquals(0.241, Statistics.gaussian(1, 0, 1), 0.01);
		assertEquals(0.176, Statistics.gaussian(1, 0, 2), 0.01);

		assertEquals(0.4, Statistics.gaussian(1, 1, 1), 0.01);
		assertEquals(0.2, Statistics.gaussian(1, 1, 2), 0.01);

		assertEquals(Statistics.gaussian(-1, 0, 1), Statistics.gaussian(1, 0, 1), 0.01);
		assertEquals(Statistics.gaussian(-0.5, 0, 1), Statistics.gaussian(0.5, 0, 1), 0.01);
		assertEquals(Statistics.gaussian(-0.2, 0, 1), Statistics.gaussian(0.2, 0, 1), 0.01);
	}

	@TestCoversMethods("gaussian")
	public void testGaussian_2D() throws Exception
	{
		int range = 200;
		int step = 10;
		double dx, dy;
		double g;
		for(int x = -range; x <= range; x += step)
		{
			dx = x / (double) 100;
			for(int y = -range; y <= range; y += step)
			{
				dy = y / (double) 100;
				g = Statistics.gaussian(new Vector2d(dx, dy), new Vector2d(0, 0), new Vector2d(1, 1), 0);
				System.out.print(g + "\t");
			}
			System.out.println("");
		}

		assertEquals(0.159, Statistics.gaussian(new Vector2d(0, 0), new Vector2d(0, 0), new Vector2d(1, 1), 0), 0.001);
		assertEquals(0.096, Statistics.gaussian(new Vector2d(1, 0), new Vector2d(0, 0), new Vector2d(1, 1), 0), 0.001);
		assertEquals(0.096, Statistics.gaussian(new Vector2d(0, 1), new Vector2d(0, 0), new Vector2d(1, 1), 0), 0.001);
	}

	@TestCoversMethods("gaussian")
	public void testGaussian_3D() throws Exception
	{
		// TODO
	}
}
