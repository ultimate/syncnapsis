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
		assertEquals(5, MathUtil.distance(0,0, 3,4), 0.000000001);
		assertEquals(Math.sqrt(3), MathUtil.distance(0,0,0, 1,1,1), 0.000000001);
	}

	public void testDistanceToLine() throws Exception
	{
		double[] nearest = new double[2];
		assertEquals(Math.sqrt(0.5), MathUtil.distanceToLine(0, 0, 0, 1, 1, 0, nearest));
		assertEquals(0.5, nearest[0]);
		assertEquals(0.5, nearest[1]);
	}

	@TestCoversMethods({"distanceToShape", "distanceToBezier"})
	public void testDistanceToShapeOrBezier() throws Exception
	{
		Shape shape = new Arc2D.Double(-1, -1, 2, 2, 0, 360, Arc2D.OPEN);
		double expectedDistance;
		double expectedAngle;
		double expectedX;
		double expectedY;
		double[] nearest = new double[2];
		double[] distanceAndAngle = new double[2];
		for(double x = -2; x <= 2; x = x+0.1)
		{
			for(double y = -2; y <= 2; y = y+0.1)
			{
				expectedDistance = Math.abs(1-MathUtil.distance(0,0,x,y));
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
}
