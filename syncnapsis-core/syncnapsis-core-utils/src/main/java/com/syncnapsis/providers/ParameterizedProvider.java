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
package com.syncnapsis.providers;

import javax.naming.OperationNotSupportedException;

/**
 * Abstract subtype of provider allowing parameterized access to variables (e.g. like a Map)
 * 
 * @author ultimate
 * @param <K> - the Type of the Key
 * @param <V> - the Type of the Value
 */
public abstract class ParameterizedProvider<K, V> implements Provider<V>
{
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.Provider#get()
	 */
	@Override
	public V get()
	{
		return get(null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.Provider#set(java.lang.Object)
	 */
	@Override
	public void set(V value) throws UnsupportedOperationException
	{
		set(null, value);
	}

	/**
	 * Get the provided value for the given key
	 * 
	 * @param key - the key
	 * @return the value
	 */
	public abstract V get(K key);

	/**
	 * Set a provided value for the given key
	 * 
	 * @param key - the key
	 * @param value - the value
	 * @throws OperationNotSupportedException - if this Provider does not support the set-Operation
	 */
	public abstract void set(K key, V value) throws UnsupportedOperationException;
}
