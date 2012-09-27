package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.MatchDao;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.service.MatchManager;

/**
 * Manager-Implementation for access to Match.
 * 
 * @author ultimate
 */
public class MatchManagerImpl extends GenericNameManagerImpl<Match, Long> implements MatchManager
{
	/**
	 * MatchDao for database access
	 */
	protected MatchDao			matchDao;

	/**
	 * Standard Constructor
	 * 
	 * @param matchDao - MatchDao for database access
	 */
	public MatchManagerImpl(MatchDao matchDao)
	{
		super(matchDao);
		this.matchDao = matchDao;
	}
}
