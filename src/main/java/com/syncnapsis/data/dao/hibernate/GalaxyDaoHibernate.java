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

import java.util.List;

import com.syncnapsis.data.dao.GalaxyDao;
import com.syncnapsis.data.model.Galaxy;

/**
 * Dao-Implementation for Hibernate for access to Galaxy
 * 
 * @author ultimate
 */
public class GalaxyDaoHibernate extends GenericNameDaoHibernate<Galaxy, Long> implements GalaxyDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericNameDaoHibernate
	 * with the model-Class Galaxy and the specified name-property
	 */
	public GalaxyDaoHibernate()
	{
		super(Galaxy.class, "name");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GalaxyDao#getByCreator(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Galaxy> getByCreator(long playerId)
	{
		return createQuery("from Galaxy g where g.creator.id=?", playerId).list();
	}
}
