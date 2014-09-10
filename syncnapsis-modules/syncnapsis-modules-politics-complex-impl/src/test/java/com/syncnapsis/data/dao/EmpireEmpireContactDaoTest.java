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

import com.syncnapsis.data.dao.hibernate.EmpireEmpireContactDaoHibernate;
import com.syncnapsis.data.model.contacts.EmpireEmpireContact;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ EmpireEmpireContactDao.class, EmpireEmpireContactDaoHibernate.class })
public class EmpireEmpireContactDaoTest extends GenericDaoTestCase<EmpireEmpireContact, Long>
{
	private EmpireEmpireContactDao		empireEmpireContactDao;
	private EmpireDao					empireDao;
	private AuthoritiesGenericImplDao	authoritiesDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = empireEmpireContactDao.getAll().get(0).getId();

		EmpireEmpireContact empireEmpireContact = new EmpireEmpireContact();
		empireEmpireContact.setContact1(empireDao.get(10L));
		empireEmpireContact.setContact2(empireDao.get(20L));
		empireEmpireContact.setContactAuthorities1(authoritiesDao.get(1L));
		empireEmpireContact.setContactAuthorities2(authoritiesDao.get(1L));
		empireEmpireContact.setApprovedByContact1(true);
		empireEmpireContact.setApprovedByContact2(true);

		setEntity(empireEmpireContact);

		setEntityProperty("approvedByContact1");
		setEntityPropertyValue(false);

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(empireEmpireContactDao);
	}
}
