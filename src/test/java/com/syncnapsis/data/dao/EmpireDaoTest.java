package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.dao.hibernate.EmpireDaoHibernate;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({EmpireDao.class, EmpireDaoHibernate.class})
public class EmpireDaoTest extends GenericNameDaoTestCase<Empire, Long>
{
	private PlayerDao playerDao;
	private EmpireDao empireDao;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		String existingName = "emp1";
		Long existingId = empireDao.getByName(existingName).getId();
		
		Empire empire = new Empire();
		empire.setShortName("any name");
		empire.setPlayer(playerDao.getByUsername("user1"));
		
		setEntity(empire);
		
		setEntityProperty("shortName");
		setEntityPropertyValue("any name2");
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);
		
		setGenericNameDao(empireDao);
	}
	
	public void testGetByPlayer() throws Exception
	{
		Player player = playerDao.getByUsername("user1");
		List<Empire> result = empireDao.getByPlayer(player.getId());
		
		assertNotNull(result);
		assertEquals(5, result.size());
		
		for(Empire e: result)
		{
			assertEquals(player, e.getPlayer());
		}
	}
}
