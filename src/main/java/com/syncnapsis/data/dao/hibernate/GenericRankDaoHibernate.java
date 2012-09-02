package com.syncnapsis.data.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.dao.RankDao;
import com.syncnapsis.data.model.annotations.RankCriterion;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Rank;

/**
 * Dao-Implementierung für Hibernate für den generischen Zugriff auf Rank-Klassen
 * 
 * @author ultimate
 * @param <R> - die Rank-Klasse
 * @param <T> - die Klasse auf die sich die Bewertung bezieht
 * @param <PK> - der Primary-Key-Typ des vom Rank aus referenzierten Objekts
 */
public abstract class GenericRankDaoHibernate<R extends Rank<T>, T extends BaseObject<PK>, PK extends Serializable> extends GenericDaoHibernate<R, Long> implements RankDao<R, T, PK>
{
	/**
	 * Die Spalte nach der bei Punktgleichheit sortiert wird.
	 */
	private String secondarySortString;
	
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der übergebenen Rank-Modell-Klasse
	 */
	public GenericRankDaoHibernate(final Class<R> persistentClass, String secondarySortString)
	{
		super(persistentClass);
		this.secondarySortString = secondarySortString;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.RankDao#getByCriterion(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<R> getByCriterion(String criterion)
	{
		if(criterion == null)
			criterion = Rank.getPrimaryCriterion(persistentClass, RankCriterion.defaultCategory);
		return createQuery("from " + this.persistentClass.getName() + " where actual=true order by " + criterion + " desc" + secondarySortString).list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.RankDao#getByEntity(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public R getByEntity(PK entityId)
	{
		return (R) createQuery("from " + this.persistentClass.getName() + " where actual=true and entity.id=?", entityId).uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.RankDao#getHistoryByEntity(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<R> getHistoryByEntity(PK entityId)
	{
		return createQuery("from " + this.persistentClass.getName() + " where entity.id=? order by timeOfCalculation", entityId).list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.RankDao#getByDefaultPrimaryCriterion()
	 */
	@Override
	public List<R> getByDefaultPrimaryCriterion()
	{
		return getByCriterion(Rank.getPrimaryCriterion(persistentClass, RankCriterion.defaultCategory));
	}
}
