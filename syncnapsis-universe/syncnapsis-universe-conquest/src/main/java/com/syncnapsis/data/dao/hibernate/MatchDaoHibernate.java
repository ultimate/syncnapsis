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
package com.syncnapsis.data.dao.hibernate;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.syncnapsis.data.dao.MatchDao;
import com.syncnapsis.data.model.Match;

/**
 * Dao-Implementation for Hibernate for access to Match
 * 
 * @author ultimate
 */
public class MatchDaoHibernate extends GenericNameDaoHibernate<Match, Long> implements MatchDao
{
	private static final String	GET_ONLY_PLANNED	= "(m.startDate is null OR m.startDate > ?) AND (m.canceledDate is null OR m.canceledDate > ?)";
	private static final String	GET_ONLY_ACTIVE		= "m.startDate <= ? AND (m.finishedDate is null OR m.finishedDate > ?) AND (m.canceledDate is null OR m.canceledDate > ?)";
	private static final String	GET_ONLY_FINISHED	= "m.finishedDate <= ?";
	private static final String	GET_ONLY_CANCELED	= "m.canceledDate <= ?";

	/**
	 * Create a new DAO-Instance using the super-class GenericNameDaoHibernate
	 * with the model-Class Match and the specified name-property
	 */
	@Deprecated
	public MatchDaoHibernate()
	{
		super(Match.class, "title");
	}

	/**
	 * Create a new DAO-Instance using the super-class GenericNameDaoHibernate
	 * with the model-Class Match and the specified name-property and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public MatchDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, Match.class, "title");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.MatchDao#getByCreator(long, boolean, boolean, boolean, boolean,
	 * java.util.Date)
	 */
	@Override
	public List<Match> getByCreator(long creatorId, boolean planned, boolean active, boolean finished, boolean canceled, Date referenceDate)
	{
		return getBy("where m.creator.id=?", creatorId, planned, active, finished, canceled, referenceDate);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.MatchDao#getByPlayer(long, boolean, boolean, boolean, boolean,
	 * java.util.Date)
	 */
	@Override
	public List<Match> getByPlayer(long playerId, boolean planned, boolean active, boolean finished, boolean canceled, Date referenceDate)
	{
		return getBy("inner join m.participants p where p.empire.player.id=?", playerId, planned, active, finished, canceled, referenceDate);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.MatchDao#getByGalaxy(long, boolean, boolean, boolean, boolean,
	 * java.util.Date)
	 */
	@Override
	public List<Match> getByGalaxy(long galaxyId, boolean planned, boolean active, boolean finished, boolean canceled, Date referenceDate)
	{
		return getBy("where m.galaxy.id=?", galaxyId, planned, active, finished, canceled, referenceDate);
	}

	/**
	 * Generic getBy... implementation for matches implementing all required filters for the given
	 * match states.
	 * 
	 * @param where - the initial WHERE-clause to use (including "WHERE" and necessary joins)
	 * @param id - the id to filter for in the WHERE-clause
	 * @param planned - include planned matches?
	 * @param active - include active matches?
	 * @param finished - include finished matches?
	 * @param canceled - include canceled matches?
	 * @param referenceDate - the date at which to evaluate the match states
	 * @return the list of matches
	 */
	@SuppressWarnings("unchecked")
	protected List<Match> getBy(String where, long id, boolean planned, boolean active, boolean finished, boolean canceled, Date referenceDate)
	{
		List<Match> matches = new LinkedList<Match>();
		if(planned)
			matches.addAll(createQuery("select m from Match m " + where + " AND " + GET_ONLY_PLANNED, id, referenceDate, referenceDate).list());
		if(active)
			matches.addAll(createQuery("select m from Match m " + where + " AND " + GET_ONLY_ACTIVE, id, referenceDate, referenceDate, referenceDate)
					.list());
		if(finished)
			matches.addAll(createQuery("select m from Match m " + where + " AND " + GET_ONLY_FINISHED, id, referenceDate).list());
		if(canceled)
			matches.addAll(createQuery("select m from Match m " + where + " AND " + GET_ONLY_CANCELED, id, referenceDate).list());
		return matches;
	}
}
