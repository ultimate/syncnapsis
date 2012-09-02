package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.EmpireRankDao;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.EmpireRank;

public class EmpireRankDaoHibernate extends GenericRankDaoHibernate<EmpireRank, Empire, Long> implements EmpireRankDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse EmpireRank
	 */
	public EmpireRankDaoHibernate()
	{
		super(EmpireRank.class, ", entity.shortName asc");
	}
}
