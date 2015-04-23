package com.syncnapsis.utils.serialization;

import java.util.Arrays;

import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class JSONGeneratorTest extends LoggerTestCase
{
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
		
		String expected = "[[1,101,102,103],[2,201,202,203],[3,301,302,303]]";
		
		String json = JSONGenerator.toJSON(galaxy);
		
		assertEquals(expected, json);
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
		inf1.setHabitability(100);

		SolarSystem sys2 = new SolarSystem();
		sys2.setId(2L);
		sys2.setCoords(new Vector.Integer(201, 202, 203));
		SolarSystemInfrastructure inf2 = new SolarSystemInfrastructure();
		inf2.setId(22L);
		inf2.setSolarSystem(sys2);
		inf2.setSize(20);
		inf2.setHabitability(200);
		
		SolarSystem sys3 = new SolarSystem();
		sys3.setId(3L);
		sys3.setCoords(new Vector.Integer(301, 302, 303));
		SolarSystemInfrastructure inf3 = new SolarSystemInfrastructure();
		inf3.setId(33L);
		inf3.setSolarSystem(sys3);
		inf3.setSize(30);
		inf3.setHabitability(300);
		
		Match match = new Match();
		match.setInfrastructures(Arrays.asList(inf1, inf2, inf3));
		
		String expected = "[[11,101,102,103,10,100],[22,201,202,203,20,200],[33,301,302,303,30,300]]";
		
		String json = JSONGenerator.toJSON(match);
		
		assertEquals(expected, json);
	}
}
