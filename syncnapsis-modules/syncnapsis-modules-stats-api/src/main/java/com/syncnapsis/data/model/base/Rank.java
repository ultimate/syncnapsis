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
package com.syncnapsis.data.model.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.syncnapsis.data.model.annotations.RankCriterion;
import com.syncnapsis.utils.ReflectionsUtil;

/**
 * Abstract parent Class for Pointrankings within a Ranklist.<br>
 * When subclassing this Class different rating criterions can be implemented.<br>
 * The point rating has to be made by Fields of Type int.<br>
 * This Class offers generic extensibility via Reflections, for being able to automatically scan the
 * subclass for rating criterions marked with the {@link RankCriterion}-Annotation.<br>
 * Furthermore setting and getting of points is implemented via Reflections.<br>
 * 
 * @author ultimate
 * @param <E> - the Class the rating refers to
 */
@MappedSuperclass
public abstract class Rank<E extends BaseObject<?>> extends BaseObject<Long>
{
	/**
	 * The entity this Rank belongs to (e.g. User, Player, Empire, ...)
	 */
	protected E			entity;

	/**
	 * The time this Rank hast last been calculated or updated.
	 */
	protected Date		timeOfCalculation;

	/**
	 * Is this the current/newest Rank for the entity?
	 */
	protected boolean	actual;

	/**
	 * The entity this Rank belongs to (e.g. User, Player, Empire, ...)
	 * 
	 * @return the entity
	 */
	@ManyToOne
	@JoinColumn(name = "fkEntity", nullable = false)
	public E getEntity()
	{
		return entity;
	}

	/**
	 * The time this Rank hast last been calculated or updated.
	 * 
	 * @return the time of calculation
	 */
	@Column(nullable = false)
	public Date getTimeOfCalculation()
	{
		return timeOfCalculation;
	}

	/**
	 * Is this the current/newest Rank for the entity?
	 * 
	 * @return true or false
	 */
	@Column(nullable = false)
	public boolean isActual()
	{
		return actual;
	}

	/**
	 * The entity this Rank belongs to (e.g. User, Player, Empire, ...)
	 * 
	 * @param entity - the entity
	 */
	public void setEntity(E entity)
	{
		this.entity = entity;
	}

	/**
	 * The time this Rank hast last been calculated or updated.
	 * 
	 * @param timeOfCalculation - the time of calculation
	 */
	public void setTimeOfCalculation(Date timeOfCalculation)
	{
		this.timeOfCalculation = timeOfCalculation;
	}

	/**
	 * Is this the current/newest Rank for the entity?
	 * 
	 * @param actual - true or false
	 */
	public void setActual(boolean actual)
	{
		this.actual = actual;
	}

	/**
	 * Set the point value for a rating criterion via Reflections (so exactly knowledge of the
	 * subclass is not necessary). The criterion either be a main rating criterion or a
	 * subcriterion.
	 * 
	 * @param criterion - the rating criterion to set
	 * @param points - the new point value to set
	 * @return the difference to the last point value
	 */
	@Transient
	public final int setPoints(String criterion, int points)
	{
		try
		{
			Integer oldPoints = ReflectionsUtil.getField(this, criterion, Integer.class);
			ReflectionsUtil.setField(this, criterion, points);
			return points - oldPoints;
		}
		catch(IllegalAccessException e)
		{
			throw new IllegalArgumentException("Field '" + criterion + "' is not accessible!");
		}
		catch(NoSuchFieldException e)
		{
			throw new IllegalArgumentException("Field '" + criterion + "' does not exist!");
		}
	}

	/**
	 * Get the point value for a rating criterion via Reflections (so exactly knowledge of the
	 * subclass is not necessary). The criterion either be a main rating criterion or a
	 * subcriterion.
	 * 
	 * @param criterion - the rating criterion to get
	 * @return the point value
	 */
	@Transient
	public final int getPoints(String criterion)
	{
		try
		{
			return ReflectionsUtil.getField(this, criterion, Integer.class);
		}
		catch(IllegalAccessException e)
		{
			throw new IllegalArgumentException("Field '" + criterion + "' is not accessible!");
		}
		catch(NoSuchFieldException e)
		{
			throw new IllegalArgumentException("Field '" + criterion + "' does not exist!");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (actual ? 1231 : 1237);
		result = prime * result + ((entity == null) ? 0 : entity.getId().hashCode());
		result = prime * result + ((timeOfCalculation == null) ? 0 : timeOfCalculation.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		Rank<?> other = (Rank<?>) obj;
		if(actual != other.actual)
			return false;
		if(entity == null)
		{
			if(other.entity != null)
				return false;
		}
		else if(!entity.getId().equals(other.entity.getId()))
			return false;
		if(timeOfCalculation == null)
		{
			if(other.timeOfCalculation != null)
				return false;
		}
		else if(!timeOfCalculation.equals(other.timeOfCalculation))
			return false;
		return true;
	}

	/**
	 * Not-static access to {@link Rank#getCategories(Class)}
	 * 
	 * @see Rank#getCategories(Class)
	 * @return the categories
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public final Set<String> getCategories()
	{
		return getCategories((Class<? extends Rank<?>>) getClass());
	}

	/**
	 * Not-static access to {@link Rank#getPrimaryCriterion(Class, String)}
	 * 
	 * @see Rank#getPrimaryCriterion(Class, String)
	 * @param category - the category
	 * @return the criterion
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public final String getPrimaryCriterion(String category)
	{
		return getPrimaryCriterion((Class<? extends Rank<?>>) getClass(), category);
	}

	/**
	 * Not-static access to {@link Rank#getAvailableCriterions(Class, String)}
	 * 
	 * @see Rank#getAvailableCriterions(Class, String)
	 * @param category - the category
	 * @return the List of criterions
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public final List<String> getAvailableCriterions(String category)
	{
		return getAvailableCriterions((Class<? extends Rank<?>>) getClass(), category);
	}

	/**
	 * Not-static access to {@link Rank#getAverageCriterion(Class, String)}
	 * 
	 * @see Rank#getAverageCriterion(Class, String)
	 * @param criterion - the criterion to get the average for
	 * @return the average criterion
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public final String getAverageCriterion(String criterion)
	{
		return getAverageCriterion((Class<? extends Rank<?>>) getClass(), criterion);
	}

	/**
	 * Not-static access to {@link Rank#getAverageAmount(Class)}
	 * 
	 * @see Rank#getAverageAmount(Class)
	 * @return the criterion
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public final String getAverageAmount()
	{
		return getAverageAmount((Class<? extends Rank<?>>) getClass());
	}

	/**
	 * Not-static access to {@link Rank#getAllCriterions(Class)}
	 * 
	 * @see Rank#getAllCriterions(Class)
	 * @return the List of criterions
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public final List<String> getAllCriterions()
	{
		return getAllCriterions((Class<? extends Rank<?>>) getClass());
	}

	// STATIC PART //

	/**
	 * A Map of all primary criterions for all categories of all Rank-Classes
	 * 
	 * @see Rank#getPrimaryCriterion()
	 */
	protected static final Map<Class<? extends Rank<?>>, Map<String, String>>		primaryCriterions	= new HashMap<Class<? extends Rank<?>>, Map<String, String>>();

	/**
	 * A Map of all criterions for all categerories of all Rank-Classes
	 * 
	 * @see Rank#getCategories(Class)
	 * @see Rank#getAvailableCriterions(Class)
	 */
	protected static final Map<Class<? extends Rank<?>>, Map<String, List<String>>>	categoryCriterions	= new HashMap<Class<? extends Rank<?>>, Map<String, List<String>>>();

	/**
	 * A Map of all criterions contaning average values for all Rank-Classes
	 * 
	 * @see Rank#getAverageCriterion(Class, String)
	 */
	protected static final Map<Class<? extends Rank<?>>, Map<String, String>>		averageCriterions	= new HashMap<Class<? extends Rank<?>>, Map<String, String>>();

	/**
	 * A Map of all average amounts for the average-calculation for all Rank-Classes
	 * 
	 * @see Rank#getAverageAmount(Class)
	 */
	protected static final Map<Class<? extends Rank<?>>, String>					averageAmounts		= new HashMap<Class<? extends Rank<?>>, String>();

	/**
	 * A Map of all criterions for all Rank-Classes
	 */
	protected static final Map<Class<? extends Rank<?>>, List<String>>				allCriterions		= new HashMap<Class<? extends Rank<?>>, List<String>>();

	/**
	 * Get all available categories for a Rank-Class
	 * 
	 * @param rankClass - the Rank-Class
	 * @return the Set of categories
	 */
	public static final Set<String> getCategories(Class<? extends Rank<?>> rankClass)
	{
		if(!categoryCriterions.containsKey(rankClass))
			readCriterions(rankClass);
		return categoryCriterions.get(rankClass).keySet();
	}

	/**
	 * Get the primary criterion for a category for a Rank-Class
	 * 
	 * @param rankClass - the Rank-Class
	 * @param category - the category
	 * @return the primary criterion
	 */
	public static final String getPrimaryCriterion(Class<? extends Rank<?>> rankClass, String category)
	{
		if(!primaryCriterions.containsKey(rankClass))
			readCriterions(rankClass);
		if(!primaryCriterions.get(rankClass).containsKey(category))
			return null;
		return primaryCriterions.get(rankClass).get(category);
	}

	/**
	 * Get the List of criterions for a category of a Rank-Class. The order of the criterions will
	 * match the order of the Fields within th Class and should represent the order of the columns
	 * in the ranking.
	 * 
	 * @param rankClass - the Rank-Class
	 * @param category - the category
	 * @return the List of criterions
	 */
	public static final List<String> getAvailableCriterions(Class<? extends Rank<?>> rankClass, String category)
	{
		if(!categoryCriterions.containsKey(rankClass))
			readCriterions(rankClass);
		if(!categoryCriterions.get(rankClass).containsKey(category))
			throw new IllegalArgumentException("Invalid category for Class '" + rankClass.getName() + "': " + category);
		return categoryCriterions.get(rankClass).get(category);
	}

	/**
	 * Get the name of the criterions that represents the average for the given criterion of a
	 * Rank-Class as far as the average defines {@link RankCriterion#averageFor()}
	 * 
	 * @see RankCriterion#averageFor()
	 * @param rankClass - the Rank-Class
	 * @param criterion - the criterion to get the average for
	 * @return the average criterion
	 */
	public static final String getAverageCriterion(Class<? extends Rank<?>> rankClass, String criterion)
	{
		if(!averageCriterions.containsKey(rankClass))
			readCriterions(rankClass);
		if(!averageCriterions.get(rankClass).containsKey(criterion))
			throw new IllegalArgumentException("Invalid criterion for Class '" + rankClass.getName() + "': " + criterion);
		return averageCriterions.get(rankClass).get(criterion);
	}

	/**
	 * Get the criterion that represent the amount for average calculation for a Rank-Class.
	 * 
	 * @param rankClass - the Rank-Class
	 * @return the criterion
	 */
	public static final String getAverageAmount(Class<? extends Rank<?>> rankClass)
	{
		if(!averageAmounts.containsKey(rankClass))
			readCriterions(rankClass);
		return averageAmounts.get(rankClass);
	}

	/**
	 * Get all available criterions for a Rank-Class
	 * 
	 * @param rankClass - the Rank-Class
	 * @return the List of criterions
	 */
	public static final List<String> getAllCriterions(Class<? extends Rank<?>> rankClass)
	{
		if(!allCriterions.containsKey(rankClass))
			readCriterions(rankClass);
		return allCriterions.get(rankClass);
	}

	/**
	 * Read all criterions marked with {@link RankCriterion} from the subclass via Reflections.
	 * 
	 * @param rankClass - the Rank-subclass
	 */
	@SuppressWarnings("unchecked")
	private static synchronized void readCriterions(Class<? extends Rank<?>> rankClass)
	{
		List<String> allCriterionsTmp = new LinkedList<String>();
		Map<String, String> primaryCriterionsTmp = new HashMap<String, String>();
		Map<String, List<String>> categoryCriterionsTmp = new HashMap<String, List<String>>();
		Map<String, String> averageCriterionsTmp = new HashMap<String, String>();
		String averageAmountTmp = null;

		String criterion, category;
		Class<? extends Rank<?>> currentClass = rankClass;
		do
		{
			for(Field field : currentClass.getDeclaredFields())
			{
				if(field.isAnnotationPresent(RankCriterion.class))
				{
					criterion = field.getName();
					category = field.getAnnotation(RankCriterion.class).category();

					if(!categoryCriterionsTmp.containsKey(category))
						categoryCriterionsTmp.put(category, new ArrayList<String>());

					allCriterionsTmp.add(criterion);
					categoryCriterionsTmp.get(category).add(criterion);

					if(field.getAnnotation(RankCriterion.class).primary())
						primaryCriterionsTmp.put(category, criterion);

					if(field.getAnnotation(RankCriterion.class).average())
						averageCriterionsTmp.put(field.getAnnotation(RankCriterion.class).averageFor(), criterion);

					if(field.getAnnotation(RankCriterion.class).averageAmount())
						averageAmountTmp = criterion;
				}
			}
			currentClass = (Class<? extends Rank<?>>) currentClass.getSuperclass();
		} while(!currentClass.equals(Rank.class));

		allCriterions.put(rankClass, allCriterionsTmp);
		primaryCriterions.put(rankClass, primaryCriterionsTmp);
		categoryCriterions.put(rankClass, categoryCriterionsTmp);
		averageCriterions.put(rankClass, averageCriterionsTmp);
		averageAmounts.put(rankClass, averageAmountTmp);
	}
}
