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
	public void afterPropertiesSet()
	{
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
					criterionCount++;
					if(criterionCount == criterions.size())
						currentSubpoints = subpointsRemaining;
					else
						currentSubpoints = currentPoints / criterions.size();
					subpointsRemaining -= currentSubpoints;

					points.put(criterion, currentSubpoints);
				}
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
