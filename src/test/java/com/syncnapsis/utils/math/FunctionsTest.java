package com.syncnapsis.utils.math;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class FunctionsTest extends LoggerTestCase
{
	@TestCoversMethods("*")
	public void testFunctions() throws Exception
	{
		assertEquals(1, Functions.linear(0), 0.000001);
		assertEquals(0, Functions.linear(1), 0.000001);

		assertEquals(1, Functions.linearSoft(0), 0.000001);
		assertEquals(0, Functions.linearSoft(1), 0.000001);
		
		assertEquals(Math.sqrt(0.5), Functions.linearSoft2(0, 0.5), 0.000001);
		assertEquals(0, Functions.linearSoft2(1, 0.5), 0.000001);
		
		assertEquals(1, Functions.quad(0), 0.000001);
		assertEquals(0, Functions.quad(1), 0.000001);
		
		assertEquals(1, Functions.cubic(0), 0.000001);
		assertEquals(0, Functions.cubic(1), 0.000001);
		
		assertEquals(1, Functions.circularUnit(0), 0.000001);
		assertEquals(0, Functions.circularUnit(1), 0.000001);
		
		assertEquals(0.5, Functions.circular(0, 0.5), 0.000001);
		assertEquals(0, Functions.circular(1, 0.5), 0.000001);
		
		assertEquals(0.5, Functions.ellipsis(0, 0.5, 0.5), 0.000001);
		assertEquals(0, Functions.ellipsis(1, 0.5, 0.5), 0.000001);
		
		assertEquals(1, Functions.gauss(0, 0.5, true), 0.000001);
		assertEquals(0, Functions.gauss(1, 0.5, true), 0.000001);
		
		assertEquals(1, Functions.gaussModified(0), 0.000001);
		assertEquals(0, Functions.gaussModified(1), 0.000001);
		
		assertEquals(0.5, Functions.disc(0, 0.5), 0.000001);
		assertEquals(0, Functions.disc(1, 0.5), 0.000001);
	}
}
