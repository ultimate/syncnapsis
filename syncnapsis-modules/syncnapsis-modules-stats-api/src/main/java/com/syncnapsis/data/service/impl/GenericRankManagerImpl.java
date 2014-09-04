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
package com.syncnapsis.data.service.impl;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.dao.RankDao;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Rank;
import com.syncnapsis.data.service.RankManager;

/**
 * Manager-Implementation for generic access to methods of RankDao using simple forwarding of the
 * method calls.
 * 
 * @author ultimate
 * @param <R> - the Rank-Class
 * @param <T> - the Class the rating refers to
 * @param <PK> - der Primary-Key-Type of the Object referenced by the Rank
 */
public abstract class GenericRankManagerImpl<R extends Rank<T>, T extends BaseObject<PK>, PK extends Serializable> extends
		GenericManagerImpl<R, Long> implements RankManager<R, T, PK>
{
	/**
	 * RankDao for database access
	 */
	protected RankDao<R, T, PK>	rankDao;

	/**
	 * Standard Constructor
	 * 
	 * @param rankDao - RankDao for database access
	 */
	public GenericRankManagerImpl(final RankDao<R, T, PK> rankDao)
	{
		super(rankDao);
		this.rankDao = rankDao;
	}

	/*
	 * @Override(non-Javadoc)
	 * @see com.syncnapsis.data.service.RankManager#getByCriterion(java.lang.String)
	 */
	public List<R> getByCriterion(String criterion)
	{
		return rankDao.getByCriterion(criterion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.RankManager#getByEntity(java.io.Serializable)
	 */
	@Override
	public R getByEntity(PK entityId)
	{
		return rankDao.getByEntity(entityId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.RankManager#getHistoryByEntity(java.io.Serializable)
	 */
	@Override
	public List<R> getHistoryByEntity(PK entityId)
	{
		return rankDao.getHistoryByEntity(entityId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.RankManager#getByDefaultPrimaryCriterion()
	 */
	@Override
	public List<R> getByDefaultPrimaryCriterion()
	{
		return rankDao.getByDefaultPrimaryCriterion();
	}
}
