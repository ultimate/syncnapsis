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

import com.syncnapsis.data.dao.hibernate.EmpireAllianceContactDaoHibernate;
import com.syncnapsis.data.model.contacts.EmpireAllianceContact;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { EmpireAllianceContactDao.class, EmpireAllianceContactDaoHibernate.class })
public class EmpireAllianceContactDaoTest extends GenericDaoTestCase<EmpireAllianceContact, Long>
{
	private EmpireAllianceContactDao	empireAllianceContactDao;
	private EmpireDao					empireDao;
	private AllianceDao					allianceDao;
	private AuthoritiesGenericImplDao	authoritiesDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = empireAllianceContactDao.getAll().get(0).getId();

		EmpireAllianceContact empireAllianceContact = new EmpireAllianceContact();
		empireAllianceContact.setContact1(empireDao.get(0L));
		empireAllianceContact.setContact2(allianceDao.get(0L));
		empireAllianceContact.setContactAuthorities1(authoritiesDao.get(1L));
		empireAllianceContact.setContactAuthorities2(authoritiesDao.get(1L));
		empireAllianceContact.setApprovedByContact1(true);
		empireAllianceContact.setApprovedByContact2(true);

		setEntity(empireAllianceContact);

		setEntityProperty("approvedByContact1");
		setEntityPropertyValue(false);

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(empireAllianceContactDao);
	}
}
