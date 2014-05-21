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
		
		String existingName = "auth102";
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
