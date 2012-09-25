package com.syncnapsis.data.dao.hibernate;

import com.syncnapsis.data.dao.ActionDao;
import com.syncnapsis.data.model.Action;

/**
 * Dao-Implementation for Hibernate for access to Action
 * 
 * @author ultimate
 */
public class ActionDaoHibernate extends GenericNameDaoHibernate<Action, Long> implements ActionDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericNameDaoHibernate
	 * with the model-Class Action
	 */
	public ActionDaoHibernate()
	{
		super(Action.class, "code");
	}
}
