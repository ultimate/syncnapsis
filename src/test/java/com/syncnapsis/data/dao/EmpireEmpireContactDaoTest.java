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
		empireEmpireContact.setContact1(empireDao.get(0L));
		empireEmpireContact.setContact2(empireDao.get(1L));
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
