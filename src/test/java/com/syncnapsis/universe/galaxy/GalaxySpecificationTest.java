package com.syncnapsis.universe.galaxy;

import java.util.List;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods({"get*", "set*"})
public class GalaxySpecificationTest extends LoggerTestCase
{
	@TestCoversMethods({"add*", "generate*", "processGridSize"})
	public void testGalaxySpecification() throws Exception
	{
		logger.debug("testing GalaxySpecification...");
		GalaxySpecification gs = new GalaxySpecification(20, 20, 10, 100, 30);
		gs.addTypeEx();
		List<int[]> coords = gs.generateCoordinates();

		assertNotNull(coords);
		for(int[] c : coords)
			assertNotNull(c);
	}
}
