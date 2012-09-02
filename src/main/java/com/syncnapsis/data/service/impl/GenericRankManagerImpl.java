package com.syncnapsis.data.service.impl;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.dao.RankDao;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Rank;
import com.syncnapsis.data.service.RankManager;

/**
 * Manager-Implementierung für den generischen Zugriff auf die Methoden der RankDao durch einfache
 * Weiterleitung der RankManager-Aufrufe.
 * 
 * @author ultimate
 * @param <R> - die Rank-Klasse
 * @param <T> - die Klasse auf die sich die Bewertung bezieht
 * @param <PK> - der Primary-Key-Typ des vom Rank aus referenzierten Objekts
 */
public abstract class GenericRankManagerImpl<R extends Rank<T>, T extends BaseObject<PK>, PK extends Serializable> extends GenericManagerImpl<R, Long> implements RankManager<R, T, PK>
{
	/**
	 * RankDao für den Datenbankzugriff.
	 */
	protected RankDao<R, T, PK>	rankDao;

	/**
	 * Standard Constructor, der die DAOs speichert.
	 * 
	 * @param rankDao - RankDao für den Datenbankzugriff
	 */
	public GenericRankManagerImpl(final RankDao<R, T, PK> rankDao)
	{
		super(rankDao);
		this.rankDao = rankDao;
	}

	/*
	 * 	@Override(non-Javadoc)
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
