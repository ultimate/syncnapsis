package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.dao.hibernate.ContactGroupDaoHibernate;
import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.model.ContactGroup;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { ContactGroupDao.class, ContactGroupDaoHibernate.class })
public class ContactGroupDaoTest extends GenericDaoTestCase<ContactGroup, Long>
{
	private ContactGroupDao	contactGroupDao;
	private EmpireDao	empireDao;
	private AllianceDao allianceDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = contactGroupDao.getAll().get(0).getId();

		ContactGroup contactGroup = new ContactGroup();
		contactGroup.setName("any name");

		setEntity(contactGroup);

		setEntityProperty("name");
		setEntityPropertyValue("any name2");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(contactGroupDao);
	}
	
	public void testGetByEmpire() throws Exception
	{
		Empire empire = empireDao.getByName("emp1");
		List<ContactGroup> result = contactGroupDao.getByEmpire(empire.getId());
		
		assertNotNull(result);
		assertEquals(6, result.size());
		
		for(ContactGroup cg: result)
		{
			assertEquals(empire, cg.getOwnerEmpire());
		}
	}
	
	public void testGetByAlliance() throws Exception
	{
		Alliance alliance = allianceDao.getByName("alliance1");
		List<ContactGroup> result = contactGroupDao.getByAlliance(alliance.getId());
		
		assertNotNull(result);
		assertEquals(6, result.size());
		
		for(ContactGroup cg: result)
		{
			assertEquals(alliance, cg.getOwnerAlliance());
		}
	}
}
