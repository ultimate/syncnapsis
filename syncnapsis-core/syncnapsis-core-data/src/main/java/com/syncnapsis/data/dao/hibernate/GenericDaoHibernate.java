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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.syncnapsis.data.dao.GenericDao;
import com.syncnapsis.data.model.base.ActivatableInstance;
import com.syncnapsis.data.model.base.Identifiable;
import com.syncnapsis.exceptions.ObjectNotFoundException;
import com.syncnapsis.utils.HibernateUtil;

/**
 * Dao-Implementierung f�r Hibernate f�r den generischen Zugriff auf beliebige
 * Modell-Klassen
 * 
 * @author ultimate
 */
public class GenericDaoHibernate<T extends Identifiable<PK>, PK extends Serializable> extends UniversalDaoHibernate implements GenericDao<T, PK>
{
	/**
	 * Die Modell-Klasse
	 */
	protected Class<? extends T>	persistentClass;
	/**
	 * Soll beim initialen Speichern eines Objektes die autmatisch vergebene ID
	 * durch eine Vorgabe �berschrieben werden?
	 */
	protected boolean				idOverwrite;
	/**
	 * Enable true DELETE for {@link ActivatableInstance}s via
	 * {@link GenericDaoHibernate#delete(Object)}
	 */
	protected boolean				deleteEnabled;
	/**
	 * Ist diese Klasse ActivatableInstance?
	 */
	protected boolean				activatable;

	/**
	 * Erzeugt eine neue DAO-Instanz der gegeben Modell-Klasse und idOverwrite =
	 * false
	 * 
	 * @param persistentClass - Die Modell-Klasse
	 */
	@Deprecated
	public GenericDaoHibernate(final Class<? extends T> persistentClass)
	{
		this(HibernateUtil.getInstance().getSessionFactory(), persistentClass);
	}

	/**
	 * Erzeugt eine neue DAO-Instanz der gegeben Modell-Klasse und idOverwrite =
	 * false
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 * @param persistentClass - Die Modell-Klasse
	 */
	public GenericDaoHibernate(SessionFactory sessionFactory, final Class<? extends T> persistentClass)
	{
		this(sessionFactory, persistentClass, false, false);
	}

	/**
	 * Erzeugt eine neue DAO-Instanz der gegeben Modell-Klasse und gegebenen
	 * idOverwrite
	 * 
	 * @param persistentClass - Die Modell-Klasse
	 * @param deleteEnabled - enable true DELETE for {@link ActivatableInstance}s via
	 *            {@link GenericDaoHibernate#delete(Object)}
	 * @param idOverwrite - Soll beim initialen Speichern eines Objektes die
	 *            autmatisch vergebene ID durch eine Vorgabe �berschrieben
	 *            werden?
	 */
	@Deprecated
	public GenericDaoHibernate(final Class<? extends T> persistentClass, boolean deleteEnabled, boolean idOverwrite)
	{
		this(HibernateUtil.getInstance().getSessionFactory(), persistentClass, deleteEnabled, idOverwrite);
	}

	/**
	 * Erzeugt eine neue DAO-Instanz der gegeben Modell-Klasse und gegebenen
	 * idOverwrite
	 * 
	 * @param sessionFactory - the SessionFactory to use
	 * @param persistentClass - Die Modell-Klasse
	 * @param deleteEnabled - enable true DELETE for {@link ActivatableInstance}s via
	 *            {@link GenericDaoHibernate#delete(Object)}
	 * @param idOverwrite - Soll beim initialen Speichern eines Objektes die
	 *            autmatisch vergebene ID durch eine Vorgabe �berschrieben
	 *            werden?
	 */
	public GenericDaoHibernate(SessionFactory sessionFactory, final Class<? extends T> persistentClass, boolean deleteEnabled, boolean idOverwrite)
	{
		super(sessionFactory);
		
		this.persistentClass = persistentClass;
		this.deleteEnabled = deleteEnabled;
		this.idOverwrite = idOverwrite;

		T inst = null;
		try
		{
			inst = this.persistentClass.newInstance();
		}
		catch(Exception e)
		{
			logger.error("oh oh...");
			e.printStackTrace();
		}
		this.activatable = (inst instanceof ActivatableInstance);
		if(!activatable)
			this.deleteEnabled = true;
	}

	/**
	 * Is true DELETE enabled (especially for {@link ActivatableInstance}s)
	 * 
	 * @return deleteEnabled
	 */
	public boolean isDeleteEnabled()
	{
		return deleteEnabled;
	}

	/*
	 * (non-Javadoc)
	 * @see org.com.syncnapsisericDao#getAll()
	 */
	@Override
	public List<T> getAll()
	{
		return getAll(true);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.com.syncnapsisDao#getAll(boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(boolean returnOnlyActivated)
	{
		List<T> results;
		if(this.activatable)
			results = createQuery("from " + this.persistentClass.getName() + (returnOnlyActivated ? " where activated=true" : "")).list();
		else
			results = getAll(this.persistentClass);
		if(results == null)
			return new LinkedList<T>();
		return results;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.com.syncnapsisget(java.io.Serializable)
	 */
	@Override
	public T get(PK id)
	{
		return get(this.persistentClass, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.Genecom.syncnapsisyIdList(java.util.List)
	 */
	@Override
	public List<T> getByIdList(List<PK> idList)
	{
		ArrayList<T> objectList = new ArrayList<T>();
		if(idList == null)
			return objectList;

		for(PK id : idList)
		{
			if(id != null)
			{
				try
				{
					T entity = (T) get(this.persistentClass, id);
					objectList.add(entity);
				}
				catch(ObjectNotFoundException e)
				{
					objectList.add(null);
				}
			}
			else
				objectList.add(null);
		}

		return objectList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.GenericDcom.syncnapsisava.io.Serializable)
	 */
	@Override
	public boolean exists(PK id)
	{
		try
		{
			return get(id) != null;
		}
		catch(ObjectNotFoundException e)
		{
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.GenericDao#save(com.syncnapsis.model.base.BaseObject)
	 */
	@Override
	public T save(T object)
	{
		if(idOverwrite)
		{
			PK oldId = object.getId();
			T afterSave = (T) super.save(object);
			if(oldId != null && !oldId.equals(afterSave.getId()))
			{
				logger.debug("id overwrite required: " + afterSave.getId() + " -> " + oldId);
				afterSave = overwriteId(afterSave, oldId);
			}
			return afterSave;
		}
		else
		{
			return (T) super.save(object);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.GenericDao#remove(orcom.syncnapsis.base.BaseObject)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String remove(T object)
	{
		if(this.activatable)
		{
			((ActivatableInstance) object).setActivated(false);
			super.save(object);
			return "deactivated";
		}
		else
		{
			super.delete(object);
			return "deleted";
		}
	}

	/**
	 * This method only supports DELETE non-{@link ActivatableInstance}s or for
	 * {@link ActivatableInstance}s if {@link GenericDaoHibernate#isDeleteEnabled()} is true.
	 */
	@Override
	public void delete(T o)
	{
		if(!this.deleteEnabled)
			throw new UnsupportedOperationException("delete(...) is not supported for GenericDaoHibernate. Use remove(...) instead!");
		else
			super.delete(o);
	}

	/**
	 * �berschreibt den Prim�rschl�ssel eines Objektes. Diese Operation ist nur
	 * bei nicht referenzierten Objekten m�glich und sollte nur beim Speichern
	 * von neuen Objekten ausgef�hrt werden.
	 * 
	 * @param object - das Objekt, dessen Prim�rschl�ssel ge�ndert werden soll.
	 * @param newId - der neue Prim�rschl�ssel
	 * @return das ge�nderte Objekt mit neuem Prim�rschl�ssel
	 */
	protected T overwriteId(T object, PK newId)
	{
		Query q = createQuery("update " + object.getClass().getSimpleName() + " set id = ? where id = ?");
		q.setParameter(0, newId);
		q.setParameter(1, object.getId());
		int i = q.executeUpdate();
		logger.debug(i + " entities updated");
		return get(newId);
	}
}
