/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
package com.syncnapsis.data.dao;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import com.syncnapsis.data.dao.hibernate.MatchDaoHibernate;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.enums.EnumJoinType;
import com.syncnapsis.enums.EnumStartCondition;
import com.syncnapsis.enums.EnumVictoryCondition;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({ MatchDao.class, MatchDaoHibernate.class })
public class MatchDaoTest extends GenericNameDaoTestCase<Match, Long>
{
	private MatchDao	matchDao;
	private GalaxyDao	galaxyDao;
	private PlayerDao	playerDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		String existingName = matchDao.getAll().get(0).getTitle();
		Long existingId = matchDao.getByName(existingName).getId();

		Match match = new Match();
		match.setCreationDate(new Date(timeProvider.get()));
		match.setCreator(playerDao.getByUsername("user1"));
		match.setFinishedDate(null);
		match.setGalaxy(galaxyDao.getByName("galaxy1"));
		match.setParticipantsMax(10);
		match.setParticipantsMin(2);
		match.setPlannedJoinType(EnumJoinType.invitationsOnly);
		match.setSpeed(10);
		match.setStartDate(new Date(timeProvider.get()));
		match.setStartCondition(EnumStartCondition.immediately);
		match.setStartSystemCount(1);
		match.setStartSystemSelectionEnabled(false);
		match.setStartedJoinType(EnumJoinType.none);
		match.setTitle("any title");
		match.setVictoryCondition(EnumVictoryCondition.domination);
		// set individual properties here

		setEntity(match);

		setEntityProperty("title");
		setEntityPropertyValue("another title");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);

		setGenericNameDao(matchDao);
	}

	// insert individual Tests here

	@TestCoversMethods({ "getByCreator", "getBy" })
	public void testGetByCreator() throws Exception
	{
		Long creator = 20L;
		Date refDate1 = dateFormat.get().parse("2012-01-15 00:00:00");
		Date refDate2 = dateFormat.get().parse("2012-02-15 00:00:00");

		getByTest("getByCreator", refDate1, creator, creator, null, null, 2, 2, 0, 0);
		getByTest("getByCreator", refDate2, creator, creator, null, null, 1, 1, 1, 1);
	}

	@TestCoversMethods({ "getByGalaxy", "getBy" })
	public void testGetByGalaxy() throws Exception
	{
		Long galaxy = 1L;
		Date refDate1 = dateFormat.get().parse("2012-01-15 00:00:00");
		Date refDate2 = dateFormat.get().parse("2012-02-15 00:00:00");

		getByTest("getByGalaxy", refDate1, galaxy, null, galaxy, null, 2, 4, 0, 0);
		getByTest("getByGalaxy", refDate2, galaxy, null, galaxy, null, 1, 2, 2, 1);
	}

	@TestCoversMethods({ "getByPlayer", "getBy" })
	public void testGetByPlayer() throws Exception
	{
		Long player = 10L;
		Date refDate1 = dateFormat.get().parse("2012-01-15 00:00:00");
		Date refDate2 = dateFormat.get().parse("2012-02-15 00:00:00");

		getByTest("getByPlayer", refDate1, player, null, null, player, 0, 2, 0, 0);
		getByTest("getByPlayer", refDate2, player, null, null, player, 0, 1, 1, 0);
	}

	@SuppressWarnings("unchecked")
	private void getByTest(String methodName, Date refDate, long arg, Long creator, Long galaxy, Long player, int planned, int active, int finished,
			int canceled) throws Exception
	{
		List<Match> result;

		Method method = matchDao.getClass().getMethod(methodName,
				new Class<?>[] { long.class, boolean.class, boolean.class, boolean.class, boolean.class, Date.class });

		result = (List<Match>) method.invoke(matchDao, new Object[] { arg, false, false, false, false, refDate });
		checkMatches(result, 0, creator, galaxy, player);

		result = (List<Match>) method.invoke(matchDao, new Object[] { arg, true, false, false, false, refDate });
		checkMatches(result, planned, creator, galaxy, player);
		for(Match m : result)
		{
			assertTrue(m.getStartDate() == null || m.getStartDate().after(refDate));
			assertTrue(m.getCanceledDate() == null || m.getCanceledDate().after(refDate));
		}

		result = (List<Match>) method.invoke(matchDao, new Object[] { arg, false, true, false, false, refDate });
		checkMatches(result, active, creator, galaxy, player);
		for(Match m : result)
		{
			assertTrue(m.getStartDate() != null && m.getStartDate().before(refDate));
			assertTrue(m.getCanceledDate() == null || m.getCanceledDate().after(refDate));
		}

		result = (List<Match>) method.invoke(matchDao, new Object[] { arg, false, false, true, false, refDate });
		checkMatches(result, finished, creator, galaxy, player);
		for(Match m : result)
		{
			assertTrue(m.getStartDate() != null && m.getStartDate().before(refDate));
			assertTrue(m.getFinishedDate() != null && m.getFinishedDate().before(refDate));
		}

		result = (List<Match>) method.invoke(matchDao, new Object[] { arg, false, false, false, true, refDate });
		checkMatches(result, canceled, creator, galaxy, player);
		for(Match m : result)
		{
			assertTrue(m.getCanceledDate() != null && m.getCanceledDate().before(refDate));
		}

		// combination

		result = (List<Match>) method.invoke(matchDao, new Object[] { arg, true, true, false, false, refDate });
		checkMatches(result, planned + active, creator, galaxy, player);

		result = (List<Match>) method.invoke(matchDao, new Object[] { arg, true, false, true, false, refDate });
		checkMatches(result, planned + finished, creator, galaxy, player);

		result = (List<Match>) method.invoke(matchDao, new Object[] { arg, true, false, false, true, refDate });
		checkMatches(result, planned + canceled, creator, galaxy, player);

		result = (List<Match>) method.invoke(matchDao, new Object[] { arg, false, true, true, false, refDate });
		checkMatches(result, active + finished, creator, galaxy, player);

		result = (List<Match>) method.invoke(matchDao, new Object[] { arg, false, true, false, true, refDate });
		checkMatches(result, active + canceled, creator, galaxy, player);

		result = (List<Match>) method.invoke(matchDao, new Object[] { arg, false, false, true, true, refDate });
		checkMatches(result, finished + canceled, creator, galaxy, player);

		result = (List<Match>) method.invoke(matchDao, new Object[] { arg, true, true, true, true, refDate });
		checkMatches(result, planned + active + finished + canceled, creator, galaxy, player);
	}

	private void checkMatches(List<Match> matches, int size, Long creator, Long galaxy, Long player)
	{
		assertNotNull(matches);
		assertEquals(size, matches.size());
		for(Match m : matches)
		{
			if(creator != null)
				assertEquals(creator, m.getCreator().getId());
			if(galaxy != null)
				assertEquals(galaxy, m.getGalaxy().getId());
			if(player != null)
			{
				boolean found = false;
				for(Participant p : m.getParticipants())
				{
					if(p.getEmpire().getPlayer().getId().equals(player))
						found = true;
				}
				assertTrue(found);
			}
		}
	}
}
