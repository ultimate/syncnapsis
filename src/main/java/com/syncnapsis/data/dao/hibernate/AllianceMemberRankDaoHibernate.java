package com.syncnapsis.data.dao.hibernate;

import java.util.List;

import com.syncnapsis.data.dao.AllianceMemberRankDao;
import com.syncnapsis.data.model.AllianceMemberRank;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContactGroup
 * 
 * @author ultimate
 */
public class AllianceMemberRankDaoHibernate extends GenericDaoHibernate<AllianceMemberRank, Long> implements AllianceMemberRankDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse AllianceMemberRank
	 */
	public AllianceMemberRankDaoHibernate()
	{
		super(AllianceMemberRank.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.AllianceMemberRankDao#getByEmpire(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AllianceMemberRank> getByEmpire(Long empireId)
	{
		return createQuery("select amr from AllianceMemberRank amr inner join amr.empires emp where emp.id = ?", empireId).list();
	}
}
