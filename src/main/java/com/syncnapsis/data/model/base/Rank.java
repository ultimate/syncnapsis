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
 * Abstrakte Oberklasse für die Punktebewertung in der Rangliste.
 * Bei Ableitung dieser Klasse können verschiedene Bewertungskriterien implementiert werden.
 * Die Punktebewertung muss in Punkten vom Typ int vorgenommen werden.
 * Die Klasse stellt dabei eine generische Erweiterung basierend auf Reflections zur Verfügung, über
 * die automatisch die definierten Felder der Subklasse ausgelesen werden und so die verfügbaren
 * Bewertungskriterien ermittelt werden. Des Weiteren wird das setzen und abfragen der Punkte über
 * Reflections realisiert.
 * 
 * @author ultimate
 * @param <E> - die Klasse auf die sich die Bewertung bezieht
 */
@MappedSuperclass
public abstract class Rank<E extends BaseObject<?>> extends BaseObject<Long>
{
	/**
	 * Das Objekt, zu dem dieser Rang gehört.
	 */
	protected E			entity;

	/**
	 * Der Zeitpunkt zu dem dieser Rang berechnet oder zuletzt aktualisiert wurde.
	 */
	protected Date		timeOfCalculation;

	/**
	 * Ist dies der aktuelle/neueste Rang zum Objekt?
	 */
	protected boolean	actual;

	/**
	 * Das Objekt, zu dem dieser Rang gehört.
	 * 
	 * @return das Objekt
	 */
	@ManyToOne
	@JoinColumn(name = "fkEntity", nullable = false)
	public E getEntity()
	{
		return entity;
	}

	/**
	 * Der Zeitpunkt zu dem dieser Rang berechnet oder zuletzt aktualisiert wurde.
	 * 
	 * @return der Zeitpunkt
	 */
	@Column(nullable = false)
	public Date getTimeOfCalculation()
	{
		return timeOfCalculation;
	}

	/**
	 * Ist dies der aktuelle/neueste Rang zum Objekt?
	 * 
	 * @return true oder false
	 */
	@Column(nullable = false)
	public boolean isActual()
	{
		return actual;
	}

	/**
	 * Das Objekt, zu dem dieser Rang gehört.
	 * 
	 * @param entity - das Objekt
	 */
	public void setEntity(E entity)
	{
		this.entity = entity;
	}

	/**
	 * Der Zeitpunkt zu dem dieser Rang berechnet oder zuletzt aktualisiert wurde.
	 * 
	 * @param timeOfCalculation - der Zeitpunkt
	 */
	public void setTimeOfCalculation(Date timeOfCalculation)
	{
		this.timeOfCalculation = timeOfCalculation;
	}

	/**
	 * Ist dies der aktuelle/neueste Rang zum Objekt?
	 * 
	 * @param actual - true oder false
	 */
	public void setActual(boolean actual)
	{
		this.actual = actual;
	}

	/**
	 * Setzt den Punktewert für ein bestimmtes Bewertungskriterium.
	 * Das Bewertungskriterium kann sowohl ein Hauptbewertungskriterium, als auch ein
	 * Subbewertungskriteriun sein.
	 * 
	 * @param criterion - das zu setzende Bewertungskriterium
	 * @return die Differenz zum vorherigen Punktewert
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
	 * Setzt den Punktewert für ein bestimmtes Bewertungskriterium.
	 * Das Bewertungskriterium kann sowohl ein Hauptbewertungskriterium, als auch ein
	 * Subbewertungskriteriun sein.
	 * 
	 * @param criterion - das zu setzende Bewertungskriterium
	 * @return der Punktewert
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
	 * Nicht-Statischer Zugriff auf getCategories
	 * 
	 * @see Rank#getCategories(Class)
	 * @return die Kategorien
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public final Set<String> getCategories()
	{
		return getCategories((Class<? extends Rank<?>>) getClass());
	}

	/**
	 * Nicht-Statischer Zugriff auf getPrimaryCriterion
	 * 
	 * @see Rank#getPrimaryCriterion(Class)
	 * @param category - die Bewertungs-Kategorie
	 * @return das Bewertungskriterium
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public final String getPrimaryCriterion(String category)
	{
		return getPrimaryCriterion((Class<? extends Rank<?>>) getClass(), category);
	}

	/**
	 * Nicht-Statischer Zugriff auf getAvailableCriterions
	 * 
	 * @see Rank#getAvailableCriterions(Class)
	 * @param category - die Bewertungs-Kategorie
	 * @return die Liste der Bewertungskriterien
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public final List<String> getAvailableCriterions(String category)
	{
		return getAvailableCriterions((Class<? extends Rank<?>>) getClass(), category);
	}

	/**
	 * Nicht-Statischer Zugriff auf getAverageCriterion
	 * 
	 * @see Rank#getAverageCriterion(Class)
	 * @param criterion - das eine Bewertungskriterium
	 * @return das andere Bewertungskriterium
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public final String getAverageCriterion(String criterion)
	{
		return getAverageCriterion((Class<? extends Rank<?>>) getClass(), criterion);
	}

	/**
	 * Nicht-Statischer Zugriff auf getAverageAmount
	 * 
	 * @see Rank#getAverageAmount(Class)
	 * @return das Bewertungskriterium
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public final String getAverageAmount()
	{
		return getAverageAmount((Class<? extends Rank<?>>) getClass());
	}

	/**
	 * Nicht-Statischer Zugriff auf getAllCriterions
	 * 
	 * @see Rank#getAllCriterions(Class)
	 * @return die Kriterien
	 */
	@SuppressWarnings("unchecked")
	@Transient
	public final List<String> getAllCriterions()
	{
		return getAllCriterions((Class<? extends Rank<?>>) getClass());
	}

	/**
	 * Eine Map mit allen primären Bewertungskriterien zu allen Kategorien der Rank-Klassen
	 * 
	 * @see Rank#getPrimaryCriterion()
	 */
	protected static final Map<Class<? extends Rank<?>>, Map<String, String>>		primaryCriterions	= new HashMap<Class<? extends Rank<?>>, Map<String, String>>();

	/**
	 * Eine Map mit allen Bewertungskriterien zu allen Kategorien der Rank-Klassen
	 * 
	 * @see Rank#getCategories(Class)
	 * @see Rank#getAvailableCriterions(Class)
	 */
	protected static final Map<Class<? extends Rank<?>>, Map<String, List<String>>>	categoryCriterions	= new HashMap<Class<? extends Rank<?>>, Map<String, List<String>>>();

	/**
	 * Eine Map mit allen Bewertungskriterien, die Durchschnitte enthalten
	 * 
	 * @see Rank#getAverageCriterion(Class, String)
	 */
	protected static final Map<Class<? extends Rank<?>>, Map<String, String>>		averageCriterions	= new HashMap<Class<? extends Rank<?>>, Map<String, String>>();

	/**
	 * Eine Map mit allen Durchschnittsmengen für die Durchschnittsberechnung
	 * 
	 * @see Rank#getAverageAmount(Class)
	 */
	protected static final Map<Class<? extends Rank<?>>, String>					averageAmounts		= new HashMap<Class<? extends Rank<?>>, String>();

	/**
	 * Eine Map mit allen Kriterien für eine Klasse
	 */
	protected static final Map<Class<? extends Rank<?>>, List<String>>				allCriterions		= new HashMap<Class<? extends Rank<?>>, List<String>>();

	/**
	 * Gibt alle verfügbaren Bewertungskategorien einer Rank-Klasse zurück.
	 * 
	 * @param rankClass - die Rank-Klasse
	 * @return die Kategorien
	 */
	public static final Set<String> getCategories(Class<? extends Rank<?>> rankClass)
	{
		if(!categoryCriterions.containsKey(rankClass))
			readCriterions(rankClass);
		return categoryCriterions.get(rankClass).keySet();
	}

	/**
	 * Gibt das primäre Bewertungskriterium zu einer Kategorie einer Rank-Klasse zurück.
	 * 
	 * @param rankClass - die Rank-Klasse
	 * @param category - die Bewertungs-Kategorie
	 * @return das Bewertungskriterium
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
	 * Gibt eine Liste der enthaltenen Bewertungskriterien zurück zu einer Kategorie einer
	 * Rank-Klasse. Die Reihenfolge dieser Kriterien in der Liste entspricht der Reihenfolge der
	 * Felddefinitionen in der Klasse und repräsentiert die Reihenfolge der Spalten in der späteren
	 * Rangliste.
	 * 
	 * @param rankClass - die Rank-Klasse
	 * @param category - die Bewertungs-Kategorie
	 * @return die Liste der Bewertungskriterien
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
	 * Gibt den Namen des Bewertungskriteriums zurück, dass für das gegebene Kriterium den
	 * Durchschnitt repräsentiert, sofern, dies über averageFor() definiert wurde.
	 * 
	 * @see RankCriterion#averageFor()
	 * @param rankClass - die Rank-Klasse
	 * @param criterion - das eine Bewertungskriterium
	 * @return das andere Bewertungskriterium
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
	 * Gibt das Kriterium zurück, dass für die Anzahl bei der Durchschnittsberechnung verantwortlich
	 * ist.
	 * 
	 * @param rankClass - die Rank-Klasse
	 * @return das Bewertungskriterium
	 */
	public static final String getAverageAmount(Class<? extends Rank<?>> rankClass)
	{
		if(!averageAmounts.containsKey(rankClass))
			readCriterions(rankClass);
		return averageAmounts.get(rankClass);
	}

	/**
	 * Gibt alle verfügbaren Bewertungskriterien einer Rank-Klasse zurück.
	 * 
	 * @param rankClass - die Rank-Klasse
	 * @return die Kriterien
	 */
	public static final List<String> getAllCriterions(Class<? extends Rank<?>> rankClass)
	{
		if(!allCriterions.containsKey(rankClass))
			readCriterions(rankClass);
		return allCriterions.get(rankClass);
	}

	/**
	 * Liest über Reflections die Bewertungskriterien für eine Subklasse aus.
	 * 
	 * @param rankClass - die Rang-Subklasse
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
