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
		allianceAllianceContact.setContact1(allianceDao.getAll().get(0));
		allianceAllianceContact.setContact2(allianceDao.getAll().get(1));
		allianceAllianceContact.setContactAuthorities1(authoritiesDao.getAll().get(0));
		allianceAllianceContact.setContactAuthorities2(authoritiesDao.getAll().get(1));
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
