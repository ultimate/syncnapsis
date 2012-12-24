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

import com.syncnapsis.data.dao.hibernate.AllianceAllianceContactDaoHibernate;
import com.syncnapsis.data.model.contacts.AllianceAllianceContact;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { AllianceAllianceContactDao.class, AllianceAllianceContactDaoHibernate.class })
public class AllianceAllianceContactDaoTest extends GenericDaoTestCase<AllianceAllianceContact, Long>
{
	private AllianceAllianceContactDao	allianceAllianceContactDao;
	private AllianceDao		allianceDao;
	private AuthoritiesGenericImplDao		authoritiesDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = allianceAllianceContactDao.getAll().get(0).getId();

		AllianceAllianceContact allianceAllianceContact = new AllianceAllianceContact();
		allianceAllianceContact.setContact1(allianceDao.get(0L));
		allianceAllianceContact.setContact2(allianceDao.get(1L));
		allianceAllianceContact.setContactAuthorities1(authoritiesDao.get(1L));
		allianceAllianceContact.setContactAuthorities2(authoritiesDao.get(1L));
		allianceAllianceContact.setApprovedByContact1(true);
		allianceAllianceContact.setApprovedByContact2(true);

		setEntity(allianceAllianceContact);

		setEntityProperty("approvedByContact1");
		setEntityPropertyValue(false);

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(allianceAllianceContactDao);
	}
}
