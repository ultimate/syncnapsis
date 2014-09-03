/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
import com.syncnapsis.data.model.help.RPCCall;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.utils.serialization.JacksonStringSerializer;
import com.syncnapsis.utils.serialization.Serializer;

@TestCoversClasses({ ActionDao.class, ActionDaoHibernate.class })
public class ActionDaoTest extends GenericNameDaoTestCase<Action, Long>
{
	private ActionDao	actionDao;
	
	private Serializer<String> serializer = new JacksonStringSerializer();

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Long existingId = 1L;
		String existingName = actionDao.get(existingId).getCode();

		Action action = new Action();
		action.setCode("code");
		action.setRPCCall(new RPCCall());
		action.getRPCCall().setObject("object");
		action.getRPCCall().setMethod("method");
		action.getRPCCall().setArgs("[]");
		// set individual properties here

		setEntity(action);

		setEntityProperty("code");
		setEntityPropertyValue("1A2B3C");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);

		setGenericNameDao(actionDao);
	}
	
	public void testDeserializeArgs() throws Exception
	{
		Action a = actionDao.get(existingEntityId);
		
		Object[] args = serializer.deserialize(a.getRPCCall().getArgs(), Object[].class, (Object[]) null);
		assertNotNull(args);
	}

	// insert individual Tests here
}
