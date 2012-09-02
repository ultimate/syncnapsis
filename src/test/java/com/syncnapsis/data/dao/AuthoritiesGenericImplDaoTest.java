package com.syncnapsis.data.dao;

import java.util.LinkedList;

import com.syncnapsis.data.dao.hibernate.AuthoritiesGenericImplDaoHibernate;
import com.syncnapsis.data.model.Authority;
import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.tests.GenericDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { AuthoritiesGenericImplDao.class, AuthoritiesGenericImplDaoHibernate.class })
public class AuthoritiesGenericImplDaoTest extends GenericDaoTestCase<AuthoritiesGenericImpl, Long>
{
	private AuthoritiesGenericImplDao	authoritiesDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = authoritiesDao.getAll().get(0).getId();

		AuthoritiesGenericImpl authoritiesGenericImpl = new AuthoritiesGenericImpl();

		setEntity(authoritiesGenericImpl);

		setEntityProperty("authoritiesGranted");
		setEntityPropertyValue(new LinkedList<Authority>());

		setExistingEntityId(existingId);
		setBadEntityId(-1L);

		setGenericDao(authoritiesDao);
	}
}
