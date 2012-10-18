package com.syncnapsis.mock;

import com.syncnapsis.data.model.annotations.RankCriterion;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Rank;

public class MockRank<T extends BaseObject<?>> extends Rank<T>
{
	private static Long	count	= 1L;
	@RankCriterion
	private int			criterion;

	public int getCriterion()
	{
		return criterion;
	}

	public void setCriterion(int criterion)
	{
		this.criterion = criterion;
	}

	public MockRank()
	{

	}

	public MockRank(T entity)
	{
		super.setEntity(entity);
		super.setId(count);
	}
}
