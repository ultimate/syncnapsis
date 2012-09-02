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
		empireAllianceContact.setContact1(empireDao.getAll().get(0));
		empireAllianceContact.setContact2(allianceDao.getAll().get(0));
		empireAllianceContact.setContactAuthorities1(authoritiesDao.getAll().get(0));
		empireAllianceContact.setContactAuthorities2(authoritiesDao.getAll().get(1));
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
