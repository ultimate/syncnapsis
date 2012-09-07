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
