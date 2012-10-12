package com.syncnapsis.data.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import com.syncnapsis.data.dao.GenericNameDao;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.exceptions.ObjectNotFoundException;

/**
 * Dao-Implementierung für Hibernate für den generischen Zugriff auf beliebige
 * Modell-Klassen
 * 
 * @author ultimate
 */
public class GenericNameDaoHibernate<T extends BaseObject<PK>, PK extends Serializable> extends GenericDaoHibernate<T, PK> implements
		GenericNameDao<T, PK>
{
	/**
	 * Das Feld nach dem gesucht/sortiert werden soll
	 */
	protected String	nameField;

	/**
	 * Erzeugt eine neue DAO-Instanz der gegeben Modell-Klasse und idOverwrite =
	 * false
	 * 
	 * @param persistentClass - Die Modell-Klasse
	 * @param nameField - Das Feld nach dem gesucht/sortiert werden soll
	 */
	public GenericNameDaoHibernate(final Class<T> persistentClass, String nameField)
	{
		super(persistentClass);
		this.nameField = nameField;
	}

	/**
	 * Erzeugt eine neue DAO-Instanz der gegeben Modell-Klasse und gegebenen
	 * idOverwrite
	 * 
	 * @param persistentClass - Die Modell-Klasse
	 * @param nameField - Das Feld nach dem gesucht/sortiert werden soll
	 * @param idOverwrite - Soll beim initialen Speichern eines Objektes die
	 *            autmatisch vergebene ID durch eine Vorgabe überschrieben
	 *            werden?
	 */
	public GenericNameDaoHibernate(final Class<T> persistentClass, String nameField, boolean idOverwrite)
	{
		super(persistentClass, idOverwrite);
		this.nameField = nameField;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericNameDao#getByName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getByName(String name)
	{
		List<T> results;
		if(this.activatable)
		{
			results = createQuery("from " + this.persistentClass.getName() + " where lower(" + nameField + ")=lower(?) and activated=true", name)
					.list();
		}
		else
		{
			results = createQuery("from " + this.persistentClass.getName() + " where lower(" + nameField + ")=lower(?)", name).list();
		}
		return singleResult(results);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericNameDao#getOrderedByName(boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getOrderedByName(boolean returnOnlyActivated)
	{
		List<T> results;
		if(!this.activatable)
		{
			returnOnlyActivated = false;
		}
		results = createQuery(
				"from " + this.persistentClass.getName() + (returnOnlyActivated ? " where activated=true" : "") + " order by lower(" + nameField
						+ ")").list();
		return results;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericNameDao#getByPrefix(java.lang.String, int, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByPrefix(String prefix, int nRows, boolean returnOnlyActivated)
	{
		List<T> results;
		if(!this.activatable)
		{
			returnOnlyActivated = false;
		}
		results = createQuery(
				"from " + this.persistentClass.getName() + " where lower(" + nameField + ") like lower('" + prefix + "%')"
						+ (returnOnlyActivated ? " and activated=true" : "") + " order by lower(" + nameField + ")").list();
		// TODO rows in query einarbeiten!
		if(nRows >= 0)
		{
			results = results.subList(0, nRows);
		}
		return results; 
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.dao.GenericNameDao#isNameAvailable(java.lang.String)
	 */
	@Override
	public boolean isNameAvailable(String name)
	{
		try
		{
			T t = this.getByName(name);
			return (t == null);
		}
		catch(ObjectNotFoundException e)
		{
			return true;
		}
	}
}
