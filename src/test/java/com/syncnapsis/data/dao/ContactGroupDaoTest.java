/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
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
		Empire empire = empireDao.getByName("E1");
		List<ContactGroup> result = contactGroupDao.getByEmpire(empire.getId());
		
		assertNotNull(result);
		assertEquals(1, result.size());
		
		for(ContactGroup cg: result)
		{
			assertEquals(empire, cg.getOwnerEmpire());
		}
	}
	
	public void testGetByAlliance() throws Exception
	{
		Alliance alliance = allianceDao.getByName("A1");
		List<ContactGroup> result = contactGroupDao.getByAlliance(alliance.getId());
		
		assertNotNull(result);
		assertEquals(1, result.size());
		
		for(ContactGroup cg: result)
		{
			assertEquals(alliance, cg.getOwnerAlliance());
		}
	}
}
