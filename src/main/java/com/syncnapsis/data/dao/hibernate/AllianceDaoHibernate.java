package com.syncnapsis.data.dao.hibernate;

import java.util.List;

import com.syncnapsis.data.dao.AllianceDao;
import com.syncnapsis.data.model.Alliance;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf Alliance
 * 
 * @author ultimate
 */
public class AllianceDaoHibernate extends GenericNameDaoHibernate<Alliance, Long> implements AllianceDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse Alliance
	 */
	public AllianceDaoHibernate()
	{
		super(Alliance.class, "shortName");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.AllianceDao#getByEmpire(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Alliance> getByEmpire(Long empireId)
	{
		return createQuery(
				"select distinct a from Alliance a" + " left outer join a.allianceMemberRanks amr" + " left outer join amr.empires e"
						+ " where e.id = ? order by a.shortName", empireId).list();
	}
}
