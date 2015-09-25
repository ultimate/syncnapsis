package com.syncnapsis.web;

import java.io.File;
import java.util.ArrayList;

import org.jmock.Expectations;

import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.MatchManager;
import com.syncnapsis.exceptions.ObjectNotFoundException;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.FileUtil;
import com.syncnapsis.utils.serialization.JSONGenerator;

@TestExcludesMethods({ "prepare", "requiresPreparation", "doFilter", "afterPropertiesSet"})
public class GalaxyFilterTest extends BaseDaoTestCase
{
	public void testPrepare() throws Exception
	{
		final MatchManager matchManager = mockContext.mock(MatchManager.class);
		
		GalaxyFilter filter = new GalaxyFilter();
		filter.setMatchManager(matchManager);
		
		final long matchId = 123L;
		final int systems = 1000;
		
		final Match match = new Match();
		match.setId(matchId);
		match.setInfrastructures(new ArrayList<SolarSystemInfrastructure>(systems));
		
		SolarSystemInfrastructure inf;
		SolarSystem sys;
		for(int i = 0; i < systems; i ++)
		{
			sys = new SolarSystem();
			sys.setCoords(new Vector.Integer(i, -i, i*i));

			inf = new SolarSystemInfrastructure();
			inf.setId((long) i);
			inf.setSize(i);
			inf.setHeat(i);
			inf.setSolarSystem(sys);
			
			match.getInfrastructures().add(inf);
		}

		File galaxyFile = new File(matchId + GalaxyFilter.EXTENSION);
		String expected = JSONGenerator.toJSON(match, false);

		try
		{
			assertFalse(galaxyFile.exists());
			
			mockContext.checking(new Expectations() {
				{
					oneOf(matchManager).get(matchId);
					will(returnValue(match));
				}
			});
			
			assertTrue(filter.prepare(galaxyFile, null));
			
			mockContext.assertIsSatisfied();
			
			assertTrue(galaxyFile.exists());
			String result = FileUtil.readFile(galaxyFile);
			
			assertEquals(expected, result);
			
			// test invalid
			
			galaxyFile.delete();
			
			mockContext.checking(new Expectations() {
				{
					oneOf(matchManager).get(matchId);
					will(throwException(new ObjectNotFoundException("Match", "" + matchId)));
				}
			});
			
			assertFalse(filter.prepare(galaxyFile, null));
			
			mockContext.assertIsSatisfied();
			
			assertFalse(galaxyFile.exists());
		}
		finally
		{
			if(galaxyFile.exists())
				galaxyFile.delete();
		}
	}
	
	public void testFileFilter() throws Exception
	{
		String ext = GalaxyFilter.EXTENSION;
		String path = GalaxyFilter.PATH;
		
		// valid
		assertTrue(GalaxyFilter.FILTER.accept(new File(path + "/" + 123 + ext)));
		assertTrue(GalaxyFilter.FILTER.accept(new File(path + "/" + 456 + ext)));
		assertTrue(GalaxyFilter.FILTER.accept(new File(path + "/" + 789 + ext)));

		// invalid
		assertFalse(GalaxyFilter.FILTER.accept(new File(path + "x/" + 123 + ext)));
		assertFalse(GalaxyFilter.FILTER.accept(new File(path + "/" + 123 + ext + "x")));
		assertFalse(GalaxyFilter.FILTER.accept(new File("abc/" + path + "/" + 123 + ext)));
		assertFalse(GalaxyFilter.FILTER.accept(new File(123 + ext)));
	}
}
