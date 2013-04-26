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
package com.syncnapsis.data.dao;

import java.util.Date;

import com.syncnapsis.data.dao.hibernate.MatchDaoHibernate;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.enums.EnumJoinType;
import com.syncnapsis.enums.EnumStartCondition;
import com.syncnapsis.enums.EnumVictoryCondition;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

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
}
