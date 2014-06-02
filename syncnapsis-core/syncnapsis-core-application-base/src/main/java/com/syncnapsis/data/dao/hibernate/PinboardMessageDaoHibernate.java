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

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.syncnapsis.data.dao.PinboardMessageDao;
import com.syncnapsis.data.model.PinboardMessage;

/**
 * Dao-Implementation for Hibernate for access to PinboardMessage
 * 
 * @author ultimate
 */
public class PinboardMessageDaoHibernate extends GenericDaoHibernate<PinboardMessage, Long> implements PinboardMessageDao
{
	/**
	 * Create a new DAO-Instance using the super-class GenericDaoHibernate
	 * with the model-Class PinboardMessage
	 */
	@Deprecated
	public PinboardMessageDaoHibernate()
	{
		super(PinboardMessage.class);
	}

	/**
	 * Create a new DAO-Instance using the super-class GenericDaoHibernate
	 * with the model-Class PinboardMessage and the given SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public PinboardMessageDaoHibernate(SessionFactory sessionFactory)
	{
		super(sessionFactory, PinboardMessage.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.PinboardMessageDao#getByPinboard(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PinboardMessage> getByPinboard(Long pinboardId)
	{
		return createQuery("from PinboardMessage where pinboard.id = ? and activated=true order by messageId desc", pinboardId).list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.PinboardMessageDao#getByPinboard(java.lang.Long, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PinboardMessage> getByPinboard(Long pinboardId, int count)
	{
		Query q = createQuery("from PinboardMessage where pinboard.id = ? and activated=true order by messageId desc", pinboardId);
		q.setMaxResults(count);
		return q.list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.PinboardMessageDao#getByPinboard(java.lang.Long, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PinboardMessage> getByPinboard(Long pinboardId, int fromMessageId, int toMessageId)
	{
		return createQuery("from PinboardMessage where pinboard.id = ? and activated=true and messageId >= ? and messageId <= ? order by messageId desc", pinboardId, fromMessageId, toMessageId).list();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.PinboardMessageDao#getLatestMessageId(java.lang.Long)
	 */
	@Override
	public int getLatestMessageId(Long pinboardId)
	{
		Integer result = (Integer) createQuery("select max(messageId) from PinboardMessage where pinboard.id = ?", pinboardId).uniqueResult();
		if(result == null) 
			return 0;
		return result;
	}
}
