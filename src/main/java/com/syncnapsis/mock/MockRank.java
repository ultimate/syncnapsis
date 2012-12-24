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
