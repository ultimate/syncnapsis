package com.syncnapsis.data.dao;

import com.syncnapsis.data.dao.hibernate.EmpireRankDaoHibernate;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.EmpireRank;
import com.syncnapsis.tests.BaseRankDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { EmpireRankDao.class, EmpireRankDaoHibernate.class })
public class EmpireRankDaoTest extends BaseRankDaoTestCase<EmpireRank, Empire, Long>
{
	@Override
	protected void setUp() throws Exception
	{
		setRankDaoClass(EmpireRankDao.class);
		super.setUp();
	}
}
