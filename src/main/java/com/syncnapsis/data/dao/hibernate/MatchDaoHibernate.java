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
package com.syncnapsis.data.dao.hibernate;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.syncnapsis.data.dao.MatchDao;
import com.syncnapsis.data.model.Match;

/**
 * Dao-Implementation for Hibernate for access to Match
 * 
 * @author ultimate
 */
public class MatchDaoHibernate extends GenericNameDaoHibernate<Match, Long> implements MatchDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericNameDaoHibernate
	 * with the model-Class Match and the specified name-property
	 */
	public MatchDaoHibernate()
	{
		super(Match.class, "title");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.MatchDao#getByCreator(long, boolean, boolean, boolean,
	 * java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Match> getByCreator(long creatorId, boolean planned, boolean active, boolean finished, Date referenceDate)
	{
		List<Match> matches = new LinkedList<Match>();
		if(planned)
			matches.addAll(createQuery("from Match m where m.creator.id=? AND (m.startDate is null OR m.startDate > ?)", creatorId, referenceDate)
					.list());
		if(active)
			matches.addAll(createQuery(
					"from Match m where m.creator.id=? AND m.startDate <= ? AND (m.finishedDate is null OR m.finishedDate > ?)", creatorId,
					referenceDate, referenceDate).list());
		if(finished)
			matches.addAll(createQuery("from Match m where m.creator.id=? AND m.finishedDate <= ?", creatorId, referenceDate).list());
		return matches;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.MatchDao#getByPlayer(long, boolean, boolean, boolean,
	 * java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Match> getByPlayer(long playerId, boolean planned, boolean active, boolean finished, Date referenceDate)
	{
		List<Match> matches = new LinkedList<Match>();
		if(planned)
			matches.addAll(createQuery("select m from Match m inner join m.participants p where p.empire.player.id=? AND (m.startDate is null OR m.startDate > ?)", playerId, referenceDate)
					.list());
		if(active)
			matches.addAll(createQuery(
					"select m from Match m inner join m.participants p where p.empire.player.id=? AND m.startDate <= ? AND (m.finishedDate is null OR m.finishedDate > ?)", playerId,
					referenceDate, referenceDate).list());
		if(finished)
			matches.addAll(createQuery("select m from Match m inner join m.participants p where p.empire.player.id=? AND m.finishedDate <= ?", playerId, referenceDate).list());
		return matches;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.MatchDao#getByGalaxy(long, boolean, boolean, boolean,
	 * java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Match> getByGalaxy(long galaxyId, boolean planned, boolean active, boolean finished, Date referenceDate)
	{
		List<Match> matches = new LinkedList<Match>();
		if(planned)
			matches.addAll(createQuery("from Match m where m.galaxy.id=? AND (m.startDate is null OR m.startDate > ?)", galaxyId, referenceDate)
					.list());
		if(active)
			matches.addAll(createQuery(
					"from Match m where m.galaxy.id=? AND m.startDate <= ? AND (m.finishedDate is null OR m.finishedDate > ?)", galaxyId,
					referenceDate, referenceDate).list());
		if(finished)
			matches.addAll(createQuery("from Match m where m.galaxy.id=? AND m.finishedDate <= ?", galaxyId, referenceDate).list());
		return matches;
	}
}
