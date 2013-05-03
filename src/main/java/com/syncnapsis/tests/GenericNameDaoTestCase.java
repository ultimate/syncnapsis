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
package com.syncnapsis.tests;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.dao.GenericNameDao;
import com.syncnapsis.data.model.base.Identifiable;

public abstract class GenericNameDaoTestCase<T extends Identifiable<PK>, PK extends Serializable> extends GenericDaoTestCase<T, PK>
{
	protected GenericNameDao<T, PK> genericNameDao;
	protected String existingEntityName;
	
	public void setGenericNameDao(GenericNameDao<T, PK> genericNameDao)
	{
		this.genericNameDao = genericNameDao;
		this.setGenericDao(genericNameDao);
	}

	public void setExistingEntityName(String existingEntityName)
	{
		this.existingEntityName = existingEntityName;
	}

	public void testGetOrderedByName() throws Exception
	{
		List<T> result = genericNameDao.getOrderedByName(true);
		assertNotNull(result);
		assertTrue(result.size() > 0);
	}
	
	public void testGetByPrefix() throws Exception
	{
		List<T> result = genericNameDao.getByPrefix(existingEntityName.substring(0,1), -1, true);
		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

	public void testGetByName() throws Exception
	{
		assertNotNull(genericNameDao.getByName(existingEntityName));
	}
}