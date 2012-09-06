package com.syncnapsis.data.dao;

import com.syncnapsis.data.dao.hibernate.AllianceRankDaoHibernate;
import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.AllianceRank;
import com.syncnapsis.tests.BaseRankDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { AllianceRankDao.class, AllianceRankDaoHibernate.class })
public class AllianceRankDaoTest extends BaseRankDaoTestCase<AllianceRank, Alliance, Long>
{
	@Override
	protected void setUp() throws Exception
	{
		setRankDaoClass(AllianceRankDao.class);
		super.setUp();
	}
}
