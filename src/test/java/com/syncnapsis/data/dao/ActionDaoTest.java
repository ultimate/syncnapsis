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

		Long existingId = actionDao.getAll().get(0).getId();
		String existingName = actionDao.getAll().get(0).getCode();

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
