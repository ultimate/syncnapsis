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

import java.io.Serializable;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.data.dao.UniversalDao;
import com.syncnapsis.utils.HibernateUtil;
import com.syncnapsis.utils.spring.Bean;

/**
 * Dao-Implementation for hibernate providing universal access to the database.<br/>
 * This class provides basic operations which may be used by {@link GenericDaoHibernate} or other
 * DAOs by passing the model class.
 * 
 * @author ultimate
 */
@SuppressWarnings("unchecked")
public class UniversalDaoHibernate extends Bean implements UniversalDao
{
	/**
	 * Logger-Instance for usage in all subclasses
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

	/**
	 * The underlying SessionFactory
	 */
	protected SessionFactory			sessionFactory;

	/**
	 * Default constructor getting SessionFactory from HibernateUteil
	 */
	@Deprecated
	public UniversalDaoHibernate()
	{
		this.sessionFactory = HibernateUtil.getInstance().getSessionFactory();
	}

	/**
	 * Recommended constructor defining a SessionFactory
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 */
	public UniversalDaoHibernate(SessionFactory sessionFactory)
	{
		super();
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Get the current session from the SessionFactory
	 * 
	 * @return the current session
	 */
	protected Session currentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.UniversalDao#save(java.lang.Object)
	 */
	@Override
	public <T> T save(T o)
	{
		return (T) currentSession().merge(o);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.UniversalDao#get(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <T> T get(Class<? extends T> clazz, Serializable id)
	{
		id = HibernateUtil.checkIdType(clazz, id);
		T o = (T) currentSession().get(clazz, id);
		if(o == null)
			throw new ObjectNotFoundException(id, clazz.getSimpleName());
		return o;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.UniversalDao#getAll(java.lang.Class)
	 */
	@Override
	public <T> List<T> getAll(Class<? extends T> clazz)
	{
		return createQuery("from " + clazz.getSimpleName()).list();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.UniversalDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(Object o)
	{
		currentSession().delete(o);
	}

	/**
	 * Universelle Methode um eine HQL-Abfrage unter der Verwendung von
	 * Parametern zu erstellen. Die �bergebenen Parameter ersetzen im Query
	 * verwendeten ?-Platzhalter.<br>
	 * Bsp: createQuery("from TABLE_1 where COL_1 = ?", value)
	 * 
	 * @param query - die Abfrage in HQL
	 * @param values - die einzusetzenden Parameter
	 * @return das Query-Objekt zum Ausf�hren der Abfrage
	 */
	protected Query createQuery(String query, Object... values)
	{
		Query queryObject = currentSession().createQuery(query);
		if(values != null)
		{
			for(int i = 0; i < values.length; i++)
			{
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}

	/**
	 * Gibt aus einer Ergebnisliste den ersten Eintrag zur�ck, falls sie nicht leer ist, ansonsten
	 * null.
	 * 
	 * @param <T> - der Typ des Ergebnisses
	 * @param results - die Liste
	 * @return der erste Eintrag der Liste
	 */
	protected <T> T singleResult(List<T> results)
	{
		if(results == null || results.isEmpty())
		{
			return null;
		}
		else
		{
			return results.get(0);
		}
	}
}
