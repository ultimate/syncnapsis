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
package com.syncnapsis.tests;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.syncnapsis.data.dao.GenericDao;
import com.syncnapsis.data.dao.RankDao;
import com.syncnapsis.data.model.annotations.RankCriterion;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Rank;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.utils.ApplicationContextUtil;

public class BaseRankDaoTestCase<R extends Rank<T>, T extends BaseObject<PK>, PK extends Serializable> extends GenericDaoTestCase<Rank<T>, Long>
{
	private RankDao<R, T, PK>	rankDao;
	private Class<? extends RankDao<R, T, PK>>	rankDaoClass;
	private Class<R> rankClass;

	@SuppressWarnings("unchecked")
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		rankDao = ApplicationContextUtil.getBean(applicationContext, rankDaoClass);
		R existingRank = rankDao.getAll().get(0);
		rankClass = (Class<R>) existingRank.getClass();

		Long existingId = existingRank.getId();

		R rank = rankClass.newInstance();
		rank.setEntity(existingRank.getEntity());
		rank.setTimeOfCalculation(new Date(timeProvider.get()));
		rank.setActual(true);

		setEntity(rank);

		setEntityProperty("actual");
		setEntityPropertyValue(false);

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao((GenericDao<Rank<T>, Long>) rankDao);
	}

	public void setRankDaoClass(Class<? extends RankDao<R, T, PK>> rankDaoClass)
	{
		this.rankDaoClass = rankDaoClass;
	}

	public void testGetByEntity() throws Exception
	{
		logger.debug("testing getByEntity...");
		
		R existingRank = rankDao.get(existingEntityId);
		
		assertTrue(existingRank.isActual());
		
		R result = rankDao.getByEntity(existingRank.getEntity().getId());
		
		assertEquals(existingRank.getId(), result.getId());
	}

	public void testGetHistoryByEntity() throws Exception
	{
		logger.debug("testing getHistoryByEntity...");
		
		R existingRank = rankDao.get(existingEntityId);
		
		assertTrue(existingRank.isActual());
		
		List<R> result = rankDao.getHistoryByEntity(existingRank.getEntity().getId());
		
		assertTrue(result.size() >= 1);
		
		assertEquals(existingRank.getId(), result.get(result.size()-1).getId());
		for(Rank<T> r: result)
		{
			assertEquals(r.getEntity().getId(), r.getEntity().getId());
		}
	}
	
	@TestCoversMethods("*Criterion")
	public void testGetByCriterion() throws Exception
	{
		logger.debug("testing getByCriterion, getByDefaultPrimaryCriterion...");

		List<R> result = rankDao.getByDefaultPrimaryCriterion();
		
		String primaryCriterion = Rank.getPrimaryCriterion(rankClass, RankCriterion.defaultCategory);
		
		int value = Integer.MAX_VALUE;
		for(R r: result)
		{
			assertTrue(r.getPoints(primaryCriterion) <= value);
			value = r.getPoints(primaryCriterion);
		}		
	}
}
