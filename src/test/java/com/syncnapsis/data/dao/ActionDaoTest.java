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

import com.syncnapsis.data.dao.hibernate.ActionDaoHibernate;
import com.syncnapsis.data.model.Action;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ ActionDao.class, ActionDaoHibernate.class })
public class ActionDaoTest extends GenericNameDaoTestCase<Action, Long>
{
	private ActionDao	actionDao;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = 1L;
		String existingName = actionDao.get(existingId).getCode();

		Action action = new Action();
		// set individual properties here

		setEntity(action);

		setEntityProperty("code");
		setEntityPropertyValue("1A2B3C");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);

		setGenericNameDao(actionDao);
	}

	// insert individual Tests here
}
