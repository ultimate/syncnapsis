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
package com.syncnapsis.utils;

import java.awt.Shape;
import java.awt.geom.Arc2D;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class MathUtilTest extends LoggerTestCase
{
	public void testDistance() throws Exception
	{
		assertEquals(5, MathUtil.distance(0, 0, 3, 4), 0.000000001);
		assertEquals(Math.sqrt(3), MathUtil.distance(0, 0, 0, 1, 1, 1), 0.000000001);
	}

	public void testDistanceToLine() throws Exception
	{
		double[] nearest = new double[2];
		assertEquals(Math.sqrt(0.5), MathUtil.distanceToLine(0, 0, 0, 1, 1, 0, nearest));
		assertEquals(0.5, nearest[0]);
		assertEquals(0.5, nearest[1]);
	}

	@TestCoversMethods({ "distanceToShape", "distanceToBezier" })
	public void testDistanceToShapeOrBezier() throws Exception
	{
		Shape shape = new Arc2D.Double(-1, -1, 2, 2, 0, 360, Arc2D.OPEN);
		double expectedDistance;
		double expectedAngle;
		double expectedX;
		double expectedY;
		double[] nearest = new double[2];
		double[] distanceAndAngle = new double[2];
		for(double x = -2; x <= 2; x = x + 0.1)
		{
			for(double y = -2; y <= 2; y = y + 0.1)
			{
				expectedDistance = Math.abs(1 - MathUtil.distance(0, 0, x, y));
				expectedAngle = Math.atan2(y, x);
				expectedX = Math.cos(expectedAngle);
				expectedY = Math.sin(expectedAngle);

				MathUtil.distanceToShape(shape, x, y, nearest, distanceAndAngle);

				assertEquals(expectedDistance, distanceAndAngle[0], 0.01);
				assertEquals(expectedX, nearest[0], 0.01);
				assertEquals(expectedY, nearest[1], 0.01);
			}
		}
	}

	public void testCeil() throws Exception
	{
		// int
		assertEquals(1234, MathUtil.ceil(1234, -1));
		assertEquals(1234, MathUtil.ceil(1234, 0));
		assertEquals(1240, MathUtil.ceil(1234, 1));
		assertEquals(1300, MathUtil.ceil(1234, 2));
		assertEquals(2000, MathUtil.ceil(1234, 3));
		assertEquals(10000, MathUtil.ceil(1234, 4));
		assertEquals(5678, MathUtil.ceil(5678, -1));
		assertEquals(5678, MathUtil.ceil(5678, 0));
		assertEquals(5680, MathUtil.ceil(5678, 1));
		assertEquals(5700, MathUtil.ceil(5678, 2));
		assertEquals(6000, MathUtil.ceil(5678, 3));
		assertEquals(10000, MathUtil.ceil(5678, 4));
		// double
		assertEquals(1234.5678, MathUtil.ceil(1234.5678, -5), 0.00001);
		assertEquals(1234.5678, MathUtil.ceil(1234.5678, -4), 0.00001);
		assertEquals(1234.5680, MathUtil.ceil(1234.5678, -3), 0.00001);
		assertEquals(1234.5700, MathUtil.ceil(1234.5678, -2), 0.00001);
		assertEquals(1234.6000, MathUtil.ceil(1234.5678, -1), 0.00001);
		assertEquals(1235.0000, MathUtil.ceil(1234.5678, 0), 0.00001);
		assertEquals(1240.0000, MathUtil.ceil(1234.5678, 1), 0.00001);
		assertEquals(1300.0000, MathUtil.ceil(1234.5678, 2), 0.00001);
		assertEquals(2000.0000, MathUtil.ceil(1234.5678, 3), 0.00001);
		assertEquals(10000.0000, MathUtil.ceil(1234.5678, 4), 0.00001);
		assertEquals(5678.1234, MathUtil.ceil(5678.1234, -5), 0.00001);
		assertEquals(5678.1234, MathUtil.ceil(5678.1234, -4), 0.00001);
		assertEquals(5678.1240, MathUtil.ceil(5678.1234, -3), 0.00001);
		assertEquals(5678.1300, MathUtil.ceil(5678.1234, -2), 0.00001);
		assertEquals(5678.2000, MathUtil.ceil(5678.1234, -1), 0.00001);
		assertEquals(5679.0000, MathUtil.ceil(5678.1234, 0), 0.00001);
		assertEquals(5680.0000, MathUtil.ceil(5678.1234, 1), 0.00001);
		assertEquals(5700.0000, MathUtil.ceil(5678.1234, 2), 0.00001);
		assertEquals(6000.0000, MathUtil.ceil(5678.1234, 3), 0.00001);
		assertEquals(10000.0000, MathUtil.ceil(5678.1234, 4), 0.00001);
	}

	public void testFloor() throws Exception
	{
		// int
		assertEquals(1234, MathUtil.floor(1234, -1));
		assertEquals(1234, MathUtil.floor(1234, 0));
		assertEquals(1230, MathUtil.floor(1234, 1));
		assertEquals(1200, MathUtil.floor(1234, 2));
		assertEquals(1000, MathUtil.floor(1234, 3));
		assertEquals(0, MathUtil.floor(1234, 4));
		assertEquals(5678, MathUtil.floor(5678, -1));
		assertEquals(5678, MathUtil.floor(5678, 0));
		assertEquals(5670, MathUtil.floor(5678, 1));
		assertEquals(5600, MathUtil.floor(5678, 2));
		assertEquals(5000, MathUtil.floor(5678, 3));
		assertEquals(0, MathUtil.floor(5678, 4));
		// double
		assertEquals(1234.5678, MathUtil.floor(1234.5678, -5), 0.00001);
		assertEquals(1234.5678, MathUtil.floor(1234.5678, -4), 0.00001);
		assertEquals(1234.5670, MathUtil.floor(1234.5678, -3), 0.00001);
		assertEquals(1234.5600, MathUtil.floor(1234.5678, -2), 0.00001);
		assertEquals(1234.5000, MathUtil.floor(1234.5678, -1), 0.00001);
		assertEquals(1234.0000, MathUtil.floor(1234.5678, 0), 0.00001);
		assertEquals(1230.0000, MathUtil.floor(1234.5678, 1), 0.00001);
		assertEquals(1200.0000, MathUtil.floor(1234.5678, 2), 0.00001);
		assertEquals(1000.0000, MathUtil.floor(1234.5678, 3), 0.00001);
		assertEquals(0.0000, MathUtil.floor(1234.5678, 4), 0.00001);
		assertEquals(5678.1234, MathUtil.floor(5678.1234, -5), 0.00001);
		assertEquals(5678.1234, MathUtil.floor(5678.1234, -4), 0.00001);
		assertEquals(5678.1230, MathUtil.floor(5678.1234, -3), 0.00001);
		assertEquals(5678.1200, MathUtil.floor(5678.1234, -2), 0.00001);
		assertEquals(5678.1000, MathUtil.floor(5678.1234, -1), 0.00001);
		assertEquals(5678.0000, MathUtil.floor(5678.1234, 0), 0.00001);
		assertEquals(5670.0000, MathUtil.floor(5678.1234, 1), 0.00001);
		assertEquals(5600.0000, MathUtil.floor(5678.1234, 2), 0.00001);
		assertEquals(5000.0000, MathUtil.floor(5678.1234, 3), 0.00001);
		assertEquals(0.0000, MathUtil.floor(5678.1234, 4), 0.00001);
	}

	public void testRound() throws Exception
	{
		// int
		assertEquals(1234, MathUtil.round(1234, -1));
		assertEquals(1234, MathUtil.round(1234, 0));
		assertEquals(1230, MathUtil.round(1234, 1));
		assertEquals(1200, MathUtil.round(1234, 2));
		assertEquals(1000, MathUtil.round(1234, 3));
		assertEquals(0, MathUtil.round(1234, 4));
		assertEquals(5678, MathUtil.round(5678, -1));
		assertEquals(5678, MathUtil.round(5678, 0));
		assertEquals(5680, MathUtil.round(5678, 1));
		assertEquals(5700, MathUtil.round(5678, 2));
		assertEquals(6000, MathUtil.round(5678, 3));
		assertEquals(10000, MathUtil.round(5678, 4));
		// double
		assertEquals(1234.5678, MathUtil.round(1234.5678, -5), 0.00001);
		assertEquals(1234.5678, MathUtil.round(1234.5678, -4), 0.00001);
		assertEquals(1234.5680, MathUtil.round(1234.5678, -3), 0.00001);
		assertEquals(1234.5700, MathUtil.round(1234.5678, -2), 0.00001);
		assertEquals(1234.6000, MathUtil.round(1234.5678, -1), 0.00001);
		assertEquals(1235.0000, MathUtil.round(1234.5678, 0), 0.00001);
		assertEquals(1230.0000, MathUtil.round(1234.5678, 1), 0.00001);
		assertEquals(1200.0000, MathUtil.round(1234.5678, 2), 0.00001);
		assertEquals(1000.0000, MathUtil.round(1234.5678, 3), 0.00001);
		assertEquals(0.0000, MathUtil.round(1234.5678, 4), 0.00001);
		assertEquals(5678.1234, MathUtil.round(5678.1234, -5), 0.00001);
		assertEquals(5678.1234, MathUtil.round(5678.1234, -4), 0.00001);
		assertEquals(5678.1230, MathUtil.round(5678.1234, -3), 0.00001);
		assertEquals(5678.1200, MathUtil.round(5678.1234, -2), 0.00001);
		assertEquals(5678.1000, MathUtil.round(5678.1234, -1), 0.00001);
		assertEquals(5678.0000, MathUtil.round(5678.1234, 0), 0.00001);
		assertEquals(5680.0000, MathUtil.round(5678.1234, 1), 0.00001);
		assertEquals(5700.0000, MathUtil.round(5678.1234, 2), 0.00001);
		assertEquals(6000.0000, MathUtil.round(5678.1234, 3), 0.00001);
		assertEquals(10000.0000, MathUtil.round(5678.1234, 4), 0.00001);
	}

	public void testCeil2()
	{
		// int
		assertEquals(100000, MathUtil.ceil2(1234, -1));
		assertEquals(10000, MathUtil.ceil2(1234, 0));
		assertEquals(2000, MathUtil.ceil2(1234, 1));
		assertEquals(1300, MathUtil.ceil2(1234, 2));
		assertEquals(1240, MathUtil.ceil2(1234, 3));
		assertEquals(1234, MathUtil.ceil2(1234, 4));
		assertEquals(1234, MathUtil.ceil2(1234, 5));
		assertEquals(100000, MathUtil.ceil2(5678, -1));
		assertEquals(10000, MathUtil.ceil2(5678, 0));
		assertEquals(6000, MathUtil.ceil2(5678, 1));
		assertEquals(5700, MathUtil.ceil2(5678, 2));
		assertEquals(5680, MathUtil.ceil2(5678, 3));
		assertEquals(5678, MathUtil.ceil2(5678, 4));
		assertEquals(5678, MathUtil.ceil2(5678, 5));
	}

	public void testFloor2()
	{
		// int
		assertEquals(0, MathUtil.floor2(1234, -1));
		assertEquals(0, MathUtil.floor2(1234, 0));
		assertEquals(1000, MathUtil.floor2(1234, 1));
		assertEquals(1200, MathUtil.floor2(1234, 2));
		assertEquals(1230, MathUtil.floor2(1234, 3));
		assertEquals(1234, MathUtil.floor2(1234, 4));
		assertEquals(1234, MathUtil.floor2(1234, 5));
		assertEquals(0, MathUtil.floor2(5678, -1));
		assertEquals(0, MathUtil.floor2(5678, 0));
		assertEquals(5000, MathUtil.floor2(5678, 1));
		assertEquals(5600, MathUtil.floor2(5678, 2));
		assertEquals(5670, MathUtil.floor2(5678, 3));
		assertEquals(5678, MathUtil.floor2(5678, 4));
		assertEquals(5678, MathUtil.floor2(5678, 5));
	}

	public void testRound2()
	{
		// int
		assertEquals(0, MathUtil.round2(1234, -1));
		assertEquals(0, MathUtil.round2(1234, 0));
		assertEquals(1000, MathUtil.round2(1234, 1));
		assertEquals(1200, MathUtil.round2(1234, 2));
		assertEquals(1230, MathUtil.round2(1234, 3));
		assertEquals(1234, MathUtil.round2(1234, 4));
		assertEquals(1234, MathUtil.round2(1234, 5));
		assertEquals(0, MathUtil.round2(5678, -1));
		assertEquals(10000, MathUtil.round2(5678, 0));
		assertEquals(6000, MathUtil.round2(5678, 1));
		assertEquals(5700, MathUtil.round2(5678, 2));
		assertEquals(5680, MathUtil.round2(5678, 3));
		assertEquals(5678, MathUtil.round2(5678, 4));
		assertEquals(5678, MathUtil.round2(5678, 5));
	}

	public void testGetDigits()
	{
		assertEquals(0, MathUtil.getDigits(0));
		assertEquals(1, MathUtil.getDigits(1));
		assertEquals(2, MathUtil.getDigits(11));
		assertEquals(3, MathUtil.getDigits(111));
		assertEquals(4, MathUtil.getDigits(1111));
		assertEquals(0, MathUtil.getDigits(-0));
		assertEquals(1, MathUtil.getDigits(-1));
		assertEquals(2, MathUtil.getDigits(-11));
		assertEquals(3, MathUtil.getDigits(-111));
		assertEquals(4, MathUtil.getDigits(-1111));
	}

	public void testPermCheck() throws Exception
	{
		MathUtil.permCheck(3, new int[] { 0, 1, 2 });
		MathUtil.permCheck(3, new int[] { 2, 0, 1 });
		MathUtil.permCheck(3, new int[] { 1, 2, 0 });

		try
		{
			MathUtil.permCheck(3, new int[] { 0, 1, 2, 3 });
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		try
		{
			MathUtil.permCheck(3, new int[] { 0, 1 });
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		try
		{
			MathUtil.permCheck(3, new int[] { 0, 1, 3 });
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		try
		{
			MathUtil.permCheck(3, new int[] { 0, 1, 1 });
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
	}

	public void testPerm() throws Exception
	{
		int[] perm = new int[] { 2, 3, 1, 5, 4, 0 };

		Integer[] original = new Integer[] { 10, 22, 44, 32, 23, 57 };
		Integer[] permuted = new Integer[] { 44, 32, 22, 57, 23, 10 };

		assertEquals(permuted, MathUtil.perm(original, perm));
	}

	@TestCoversMethods({ "permCycleLength", "permPartialCycleLength" })
	public void testPermCycleLength() throws Exception
	{
		int[] perm = new int[] { 1, 0, 4, 2, 3 };

		assertEquals(6, MathUtil.permCycleLength(perm));
		assertEquals(2, MathUtil.permPartialCycleLength(perm));

		try
		{
			MathUtil.permCycleLength(new int[] { 0, 1, 3 });
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
	}
}
