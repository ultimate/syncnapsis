package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.AllianceRankDao;
import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.AllianceRank;

public class AllianceRankDaoHibernate extends GenericRankDaoHibernate<AllianceRank, Alliance, Long> implements AllianceRankDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse AllianceRank
	 */
	public AllianceRankDaoHibernate()
	{
		super(AllianceRank.class, ", entity.shortName asc");
	}
}
