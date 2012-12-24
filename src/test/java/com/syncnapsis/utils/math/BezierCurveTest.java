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

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.math.BezierCurve;

@TestExcludesMethods({"toString", "getType", "getCoords", "getFactors*"})
public class BezierCurveTest extends LoggerTestCase
{
	public void testGetCoordsAt() throws Exception
	{
		BezierCurve bc;
		
		try
		{
			bc = new BezierCurve(99, null);
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
			assertEquals("coords must not be null", e.getMessage());
		}
		
		try
		{
			bc = new BezierCurve(BezierCurve.LINEAR, new double[1]);
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
			assertEquals("coords must have length 4 with LINEAR", e.getMessage());
		}
		
		try
		{
			bc = new BezierCurve(BezierCurve.QUADRIC, new double[1]);
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
			assertEquals("coords must have length 6 with QUADRIC", e.getMessage());
		}
		
		try
		{
			bc = new BezierCurve(BezierCurve.CUBIC, new double[1]);
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
			assertEquals("coords must have length 8 with CUBIC", e.getMessage());
		}
		
		try
		{
			bc = new BezierCurve(99, new double[1]);
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
			assertEquals("type must be LINEAR, QUADRIC or CUBIC", e.getMessage());
		}
		
		bc = new BezierCurve(BezierCurve.LINEAR, new double[]{0.0,0.0,1.0,1.0});
		for(double t = 0; t <= 1; t = t+0.001)
		{
			assertEquals(t, bc.getCoordsAt(t)[0], 0.0001);
			assertEquals(t, bc.getCoordsAt(t)[1], 0.0001);
		}
		
		bc = new BezierCurve(BezierCurve.QUADRIC, new double[]{0.0,0.0,1.0,1.0,2.0,2.0});
		for(double t = 0; t <= 1; t = t+0.001)
		{
			assertEquals(2*t, bc.getCoordsAt(t)[0], 0.0001);
			assertEquals(2*t, bc.getCoordsAt(t)[1], 0.0001);
		}
		
		bc = new BezierCurve(BezierCurve.CUBIC, new double[]{0.0,0.0,1.0,1.0,2.0,2.0,3.0,3.0});
		for(double t = 0; t <= 1; t = t+0.001)
		{
			assertEquals(3*t, bc.getCoordsAt(t)[0], 0.0001);
			assertEquals(3*t, bc.getCoordsAt(t)[1], 0.0001);
		}
	}
}
