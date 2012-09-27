package com.syncnapsis.data.dao.hibernate;

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
}
