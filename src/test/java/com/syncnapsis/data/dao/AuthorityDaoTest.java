package com.syncnapsis.data.dao;

import com.syncnapsis.data.dao.hibernate.AuthorityDaoHibernate;
import com.syncnapsis.data.model.Authority;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { AuthorityDao.class, AuthorityDaoHibernate.class })
public class AuthorityDaoTest extends GenericNameDaoTestCase<Authority, Long>
{
	private AuthorityDao		authorityDao;
	private AuthorityGroupDao	authorityGroupDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		String existingName = "allowedToSeeTroops";
		Long existingId = authorityDao.getByName(existingName).getId();
		
		Authority authority = new Authority();
		authority.setName("any name");
		authority.setGroup(authorityGroupDao.getAll().get(0));
		
		setEntity(authority);
		
		setEntityProperty("name");
		setEntityPropertyValue("any name2");
		
		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);
		
		setGenericNameDao(authorityDao);
	}
}
