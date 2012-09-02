package com.syncnapsis.data.dao;

import com.syncnapsis.data.dao.hibernate.AuthorityGroupDaoHibernate;
import com.syncnapsis.data.model.AuthorityGroup;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { AuthorityGroupDao.class, AuthorityGroupDaoHibernate.class })
public class AuthorityGroupDaoTest extends GenericNameDaoTestCase<AuthorityGroup, Long>
{
	private AuthorityGroupDao	authorityGroupDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		String existingName = "contactauthorities";
		Long existingId = authorityGroupDao.getByName(existingName).getId();

		AuthorityGroup authorityGroup = new AuthorityGroup();
		authorityGroup.setName("any name");

		setEntity(authorityGroup);

		setEntityProperty("name");
		setEntityPropertyValue("any name2");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);

		setGenericNameDao(authorityGroupDao);
	}
}
