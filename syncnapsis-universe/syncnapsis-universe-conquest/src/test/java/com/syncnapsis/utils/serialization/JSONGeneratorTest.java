package com.syncnapsis.utils.serialization;

import java.util.Arrays;
import java.util.Collections;

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.utils.constants.Constant;
import com.syncnapsis.utils.constants.ConstantLoader;

public class JSONGeneratorTest extends LoggerTestCase
{	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		ConstantLoader<String> constantLoader = new ConstantLoader<String>(String.class) {
			@Override
			public void load(Constant<String> constant)
			{
				if(constant == UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX)
					constant.define("1000");
				else if(constant == UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX)
					constant.define("1000");
				else if(constant == UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR)
					constant.define("1000000");
				else if(constant == UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR)
					constant.define("10.0");
				else if(constant == UniverseConquestConstants.PARAM_TRAVEL_EXODUS_FACTOR)
					constant.define("20.0");
				else if(constant == UniverseConquestConstants.PARAM_TRAVEL_TIME_FACTOR)
					constant.define("0.7");
				else if(constant == UniverseConquestConstants.PARAM_FACTOR_BUILD)
					constant.define("0.08");
			}
		};

		constantLoader.setConstantClasses(Arrays.asList(new Class<?>[] { UniverseConquestConstants.class }));
		constantLoader.afterPropertiesSet();
	}

	@TestCoversMethods("toJSON")
	public void testToJSON_Galaxy() throws Exception
	{
		SolarSystem sys1 = new SolarSystem();
		sys1.setId(1L);
		sys1.setCoords(new Vector.Integer(101, 102, 103));

		SolarSystem sys2 = new SolarSystem();
		sys2.setId(2L);
		sys2.setCoords(new Vector.Integer(201, 202, 203));

		SolarSystem sys3 = new SolarSystem();
		sys3.setId(3L);
		sys3.setCoords(new Vector.Integer(301, 302, 303));

		Galaxy galaxy = new Galaxy();
		galaxy.setSolarSystems(Arrays.asList(sys1, sys2, sys3));

		String expected1 = "[[1,101,102,103],[2,201,202,203],[3,301,302,303]]";
		String expected2 = "[\r\n\t[1,101,102,103],\r\n\t[2,201,202,203],\r\n\t[3,301,302,303]\r\n]";
		String expected3 = "[]";

		Collections.shuffle(galaxy.getSolarSystems());
		assertEquals(expected1, JSONGenerator.toJSON(galaxy, false));
		Collections.shuffle(galaxy.getSolarSystems());
		assertEquals(expected2, JSONGenerator.toJSON(galaxy, true));
		// test with empty galaxy
		Collections.shuffle(galaxy.getSolarSystems());
		assertEquals(expected3, JSONGenerator.toJSON(new Galaxy(), false));
		Collections.shuffle(galaxy.getSolarSystems());
		assertEquals(expected3, JSONGenerator.toJSON(new Galaxy(), true));
	}

	@TestCoversMethods("toJSON")
	public void testToJSON_Match() throws Exception
	{
		SolarSystem sys1 = new SolarSystem();
		sys1.setId(1L);
		sys1.setCoords(new Vector.Integer(101, 102, 103));
		SolarSystemInfrastructure inf1 = new SolarSystemInfrastructure();
		inf1.setId(11L);
		inf1.setSolarSystem(sys1);
		inf1.setSize(10);
		inf1.setHeat(100);

		SolarSystem sys2 = new SolarSystem();
		sys2.setId(2L);
		sys2.setCoords(new Vector.Integer(201, 202, 203));
		SolarSystemInfrastructure inf2 = new SolarSystemInfrastructure();
		inf2.setId(22L);
		inf2.setSolarSystem(sys2);
		inf2.setSize(20);
		inf2.setHeat(200);

		SolarSystem sys3 = new SolarSystem();
		sys3.setId(3L);
		sys3.setCoords(new Vector.Integer(301, 302, 303));
		SolarSystemInfrastructure inf3 = new SolarSystemInfrastructure();
		inf3.setId(33L);
		inf3.setSolarSystem(sys3);
		inf3.setSize(30);
		inf3.setHeat(300);

		Match match = new Match();
		match.setInfrastructures(Arrays.asList(inf1, inf2, inf3));

		String expected1 = "[[11,101,102,103,10,100],[22,201,202,203,20,200],[33,301,302,303,30,300]]";
		String expected2 = "[\r\n\t[11,101,102,103,10,100],\r\n\t[22,201,202,203,20,200],\r\n\t[33,301,302,303,30,300]\r\n]";
		String expected3 = "[]";

		Collections.shuffle(match.getInfrastructures());
		assertEquals(expected1, JSONGenerator.toJSON(match, false));
		Collections.shuffle(match.getInfrastructures());
		assertEquals(expected2, JSONGenerator.toJSON(match, true));
		// test with empty match
		Collections.shuffle(match.getInfrastructures());
		assertEquals(expected3, JSONGenerator.toJSON(new Match(), false));
		Collections.shuffle(match.getInfrastructures());
		assertEquals(expected3, JSONGenerator.toJSON(new Match(), true));
	}
	
	@TestCoversMethods({"createConstantsJS", "writeConstant"})
	public void testCreateConstantsJS() throws Exception
	{
		String constants = JSONGenerator.createConstantJS();
		
		logger.debug(constants);
		
		// @formatter: off
		assertTrue(constants.contains("UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR = " + UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR.asInt() + ";"));
		assertTrue(constants.contains("UniverseConquestConstants.PARAM_TRAVEL_EXODUS_FACTOR = " + UniverseConquestConstants.PARAM_TRAVEL_EXODUS_FACTOR.asInt() + ";"));
		assertTrue(constants.contains("UniverseConquestConstants.PARAM_TRAVEL_TIME_FACTOR = " + UniverseConquestConstants.PARAM_TRAVEL_TIME_FACTOR.asDouble() + ";"));
		assertTrue(constants.contains("UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX = " + UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX.asInt() + ";"));
		assertTrue(constants.contains("UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX = " + UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX.asInt() + ";"));
		assertTrue(constants.contains("UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR = " + UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR.asInt() + ";"));
		assertTrue(constants.contains("UniverseConquestConstants.PARAM_FACTOR_BUILD = " + UniverseConquestConstants.PARAM_FACTOR_BUILD.asDouble() + ";"));
		// @formatter: on
	}
}
