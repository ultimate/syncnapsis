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

import java.io.Serializable;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import com.syncnapsis.data.dao.UniversalDao;
import com.syncnapsis.utils.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dao-Implementierung für Hibernate für den universellen Zugriff auf die
 * Datenbank.<br/>
 * Die Klasse stellt grundlegende Funktionen zur Verfügung, welche durch die
 * GenericDaoHibernate oder andere Daos durch Verwendung und Angabe einer
 * Modell-Klasse genutzt werden können.
 * 
 * @author ultimate
 */
@SuppressWarnings("unchecked")
public class UniversalDaoHibernate implements UniversalDao
{
	/**
	 * Logger-Instanz zur Verwendung in allen Subklassen
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

	/**
	 * Holt die aktuelle Session aus dem ZK-HibernateUtil
	 * 
	 * @return die aktuelle Hibernate-Session
	 */
	protected Session currentSession()
	{
		return HibernateUtil.currentSession();
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
	 * Parametern zu erstellen. Die übergebenen Parameter ersetzen im Query
	 * verwendeten ?-Platzhalter.<br>
	 * Bsp: createQuery("from TABLE_1 where COL_1 = ?", value)
	 * 
	 * @param query - die Abfrage in HQL
	 * @param values - die einzusetzenden Parameter
	 * @return das Query-Objekt zum Ausführen der Abfrage
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
	 * Gibt aus einer Ergebnisliste den ersten Eintrag zurück, falls sie nicht leer ist, ansonsten
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
