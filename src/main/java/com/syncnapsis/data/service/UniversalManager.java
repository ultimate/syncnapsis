package com.syncnapsis.data.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import com.syncnapsis.data.dao.GenericDao;

/**
 * Universal Manager-Interface for access to any arbitrary Class offering standard CRUD-Operations:
 * <ul>
 * <li>CREATE --&gt; use {@link UniversalManager#save(Object)} with an Object without ID set</li>
 * <li>RETRIEVE --&gt; use {@link UniversalManager#get(Class, Serializable)} or
 * {@link UniversalManager#getAll(Class)}</li>
 * <li>UPDATE --&gt; use {@link UniversalManager#save(Object)} with a manipulated (but already persited)
 * Object</li>
 * <li>DELETE --&gt; use {@link UniversalManager#delete(Object)} with the Object to delete</li>
 * </ul>
 * 
 * @author ultimate
 */
public interface UniversalManager
{
	/**
	 * Universal Method for loading an Object by it's primary key.
	 * 
	 * @see Session#get(Class, Serializable)
	 * @param <T> - the type of the Object, defined by clazz
	 * @param clazz - the model-Class of the Object
	 * @param id - the primary key of the Object
	 * @return the Object
	 */
	public <T> T get(Class<? extends T> clazz, Serializable id);

	/**
	 * Universal Method for loading all Objects of a given Type (both activated and deactivated).
	 * 
	 * @param <T> - the type of the Objects, defined by clazz
	 * @param clazz - the model-Class
	 * @return the List of all Objects of the given Type
	 */
	public <T> List<T> getAll(Class<? extends T> clazz);

	/**
	 * Universal Method for saving any arbitrary persistant Object
	 * 
	 * @see Session#merge(Object)
	 * @param o - the Object to save
	 * @return the saved (and merged) Object
	 */
	public <T> T save(T o);

	/**
	 * Universal Method for deleting an arbitrary persistant Object.<br>
	 * This Method will truly delete the Object irreversibly! Using a
	 * {@link GenericDao#remove(com.syncnapsis.data.model.base.BaseObject)} should be preferred.
	 * 
	 * @param o - the Object to delete
	 */
	public void delete(Object o);
}
