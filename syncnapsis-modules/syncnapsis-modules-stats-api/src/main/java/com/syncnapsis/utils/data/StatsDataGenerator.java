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
package com.syncnapsis.utils.data;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Rank;

public abstract class StatsDataGenerator extends DataGenerator implements InitializingBean
{
	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.data.DataGenerator#generateDummyData(int)
	 */
	@Override
	public void generateDummyData(int amount)
	{
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.data.DataGenerator#generateTestData(java.util.Properties)
	 */
	@Override
	public void generateTestData(Properties testDataProperties)
	{
		throw new UnsupportedOperationException();
	}

	public abstract <T extends BaseObject<?>, R extends Rank<T>> R createRankSimple(Class<? extends R> rankClass, T entity, int totalPoints);

	public abstract <T extends BaseObject<?>, R extends Rank<T>> R createRank(Class<? extends R> rankClass, T entity, Map<String, Integer> points);

}
