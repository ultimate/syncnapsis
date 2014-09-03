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
package com.syncnapsis.data.dao.hibernate;

import org.hibernate.SessionFactory;

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
	@Deprecated
	public ActionDaoHibernate()
	{
		super(Action.class, "code");
	}

	/**
	 * Create a new DAO-Instance using the super-class GenericNameDaoHibernate
	 * with the model-Class Action and the given SessionFactory
	 *
	 * @param sessionFactory - the SessionFactory to use
	 */
	public ActionDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, Action.class, "code");
	}
}
