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
package com.syncnapsis.data.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

/**
 * Universal DAO-Interface for access to any arbitrary Class offering standard CRUD-Operations:
 * <ul>
 * <li>CREATE --&gt; use {@link UniversalDao#save(Object)} with an Object without ID set</li>
 * <li>RETRIEVE --&gt; use {@link UniversalDao#get(Class, Serializable)} or
 * {@link UniversalDao#getAll(Class)}</li>
 * <li>UPDATE --&gt; use {@link UniversalDao#save(Object)} with a manipulated (but already persited)
 * Object</li>
 * <li>DELETE --&gt; use {@link UniversalDao#delete(Object)} with the Object to delete</li>
 * </ul>
 * 
 * @author ultimate
 */
public interface UniversalDao
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
