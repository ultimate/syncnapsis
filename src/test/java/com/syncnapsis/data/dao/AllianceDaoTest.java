package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.dao.hibernate.AllianceDaoHibernate;
import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.AllianceMemberRank;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { AllianceDao.class, AllianceDaoHibernate.class })
public class AllianceDaoTest extends GenericNameDaoTestCase<Alliance, Long>
{
	private AllianceDao	allianceDao;
	private EmpireDao empireDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		String existingName = "alliance1";
		Long existingId = allianceDao.getByName(existingName).getId();

		Alliance alliance = new Alliance();
		alliance.setShortName("any name");

		setEntity(alliance);

		setEntityProperty("shortName");
		setEntityPropertyValue("any name2");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);

		setGenericNameDao(allianceDao);
	}
	
	public void testGetByEmpire() throws Exception
	{
		Empire empire = empireDao.getByName("emp10");
		List<Alliance> result = allianceDao.getByEmpire(empire.getId());
		
		assertNotNull(result);
		assertEquals(2, result.size());
		
		boolean found;
		for(Alliance alliance: result)
		{
			found = false;
			for(AllianceMemberRank allianceMemberRank: alliance.getAllianceMemberRanks())
			{
				for(Empire empire2: allianceMemberRank.getEmpires())
				{
					if(empire2.equals(empire))
					{
						found = true;
						break;
					}
				}
				if(found)
				{
					break;
				}
			}
			assertTrue(found);
		}
	}
}
