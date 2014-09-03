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

import java.util.List;

import org.hibernate.SessionFactory;

import com.syncnapsis.data.dao.BlackListEntryDao;
import com.syncnapsis.data.model.BlackListEntry;

/**
 * Dao-Implementation for Hibernate for access to BlackListEntry
 * 
 * @author ultimate
 */
public class BlackListEntryDaoHibernate extends GenericDaoHibernate<BlackListEntry, Long> implements BlackListEntryDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericDaoHibernate
	 * with the model-Class BlackListEntry
	 */
	@Deprecated
	public BlackListEntryDaoHibernate()
	{
		super(BlackListEntry.class);
	}

	/**
	 * Create a new DAO-Instance using the super-class GenericDaoHibernate
	 * with the model-Class BlackListEntry and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public BlackListEntryDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, BlackListEntry.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.BlackListEntryDao#getByBlackList(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BlackListEntry> getByBlackList(Long blackListId)
	{
		return createQuery("from BlackListEntry where blackList.id = ?", blackListId).list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.BlackListEntryDao#getValuesByBlackListName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getValuesByBlackListName(String blackListName)
	{
		return createQuery("select value from BlackListEntry where blackList.name = ?", blackListName).list();
	}
}
