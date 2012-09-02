package com.syncnapsis.data.dao.hibernate;

import java.util.List;

import com.syncnapsis.data.dao.EmpireDao;
import com.syncnapsis.data.model.Empire;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContactGroup
 * 
 * @author ultimate
 */
public class EmpireDaoHibernate extends GenericNameDaoHibernate<Empire, Long> implements EmpireDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse Empire
	 */
	public EmpireDaoHibernate()
	{
		super(Empire.class, "shortName");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.EmpireDao#getByUser(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Empire> getByPlayer(Long playerId)
	{
		return createQuery("from Empire e where e.player.id=?", playerId).list();
	}
}
