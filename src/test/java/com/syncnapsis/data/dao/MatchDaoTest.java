package com.syncnapsis.data.dao;

import java.util.Date;

import com.syncnapsis.data.dao.hibernate.MatchDaoHibernate;
import com.syncnapsis.data.model.Match;
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
		match.setSpeed(10);
		match.setStartDate(new Date(timeProvider.get()));
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
