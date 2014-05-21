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
package com.syncnapsis.data.dao.hibernate;

import org.hibernate.SessionFactory;

import com.syncnapsis.data.dao.RPCLogDao;
import com.syncnapsis.data.model.RPCLog;

/**
 * Dao-Implementation for Hibernate for access to RPCLog
 * 
 * @author ultimate
 */
public class RPCLogDaoHibernate extends GenericDaoHibernate<RPCLog, Long> implements RPCLogDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericDaoHibernate
	 * with the model-Class RPCLog
	 */
	@Deprecated
	public RPCLogDaoHibernate()
	{
		super(RPCLog.class);
	}

	/**
	 * Create a new DAO-Instance using the super-class GenericDaoHibernate
	 * with the model-Class RPCLog and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public RPCLogDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, RPCLog.class);
	}
}
