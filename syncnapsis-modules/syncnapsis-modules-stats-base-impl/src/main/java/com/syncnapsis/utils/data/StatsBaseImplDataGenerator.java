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
package com.syncnapsis.utils.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.util.Assert;

import com.syncnapsis.data.model.annotations.RankCriterion;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Rank;
import com.syncnapsis.providers.TimeProvider;

public class StatsBaseImplDataGenerator extends StatsDataGenerator
{
	protected TimeProvider timeProvider;
	
	public TimeProvider getTimeProvider()
	{
		return timeProvider;
	}

	public void setTimeProvider(TimeProvider timeProvider)
	{
		this.timeProvider = timeProvider;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		// @formatter:off
		this.setExcludeTableList(new String[] {
				});
		// @formatter:on
		
		super.afterPropertiesSet();
		
		Assert.notNull(timeProvider, "timeProvider must not be null!");
	}

	public <T extends BaseObject<?>, R extends Rank<T>> R createRankSimple(Class<? extends R> rankClass, T entity, int totalPoints)
	{
		Map<String, Integer> points = new HashMap<String, Integer>();

		points.put(Rank.getPrimaryCriterion(rankClass, RankCriterion.defaultCategory), totalPoints);

		int pointsRemaining = totalPoints;
		int subpointsRemaining;
		int currentPoints;
		int currentSubpoints;
		Set<String> categories = Rank.getCategories(rankClass);
		List<String> criterions;
		try
		{
			int categoryCount = 0;
			int criterionCount = 0;
			
			if(categories.size() == 1)
			{
				// only default/main category
				String category = categories.toArray(new String[1])[0];

				criterions = Rank.getAvailableCriterions(rankClass, category);
				subpointsRemaining = totalPoints;

				criterionCount = 0;
				for(String criterion : criterions)
				{
					if(criterion.equals(Rank.getPrimaryCriterion(rankClass, category)))
						continue;
					criterionCount++;
					if(criterionCount == criterions.size() - 1)
						currentSubpoints = subpointsRemaining;
					else
						currentSubpoints = totalPoints / (criterions.size() - 1);
					subpointsRemaining -= currentSubpoints;

					points.put(criterion, currentSubpoints);
				}
				
				points.put(Rank.getPrimaryCriterion(rankClass, category), totalPoints);
			}
			else
			{
			
				for(String category : categories)
				{
					if(category.equals(RankCriterion.defaultCategory))
						continue;
					categoryCount++;
					if(categoryCount == categories.size() - 1)
						currentPoints = pointsRemaining;
					else
						currentPoints = totalPoints / (categories.size() - 1);
					pointsRemaining -= currentPoints;
	
					points.put(category, currentPoints);
	
					criterions = Rank.getAvailableCriterions(rankClass, category);
					subpointsRemaining = currentPoints;
	
					criterionCount = 0;
					for(String criterion : criterions)
					{
						if(criterion.equals(Rank.getPrimaryCriterion(rankClass, category)))
							continue;
						criterionCount++;
						if(criterionCount == criterions.size())
							currentSubpoints = subpointsRemaining;
						else
							currentSubpoints = currentPoints / criterions.size();
						subpointsRemaining -= currentSubpoints;
	
						points.put(criterion, currentSubpoints);
					}
				}
				points.put(Rank.getPrimaryCriterion(rankClass, RankCriterion.defaultCategory), totalPoints);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return createRank(rankClass, entity, points);
	}

	public <T extends BaseObject<?>, R extends Rank<T>> R createRank(Class<? extends R> rankClass, T entity, Map<String, Integer> points)
	{
		try
		{
			R rank = rankClass.newInstance();
			rank.setEntity(entity);
			rank.setTimeOfCalculation(new Date(timeProvider.get()));
			rank.setActual(true);
			for(Entry<String, Integer> entry : points.entrySet())
			{
				rank.setPoints(entry.getKey(), entry.getValue());
			}
			return rank;
		}
		catch(Exception e)
		{
			logger.error("could not create Rank '" + e.getMessage() + "': " + rankClass.getName());
			return null;
		}
	}
}
