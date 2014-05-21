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
package com.syncnapsis.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the Provider Interface using a ThreadLocal to store the provided value.<br>
 * The provided value can be set from somewhere within a Thread in order to be usable somewhere
 * else.<br>
 * 
 * @see ThreadLocal
 * @author ultimate
 * @param <T> - the Type this Provider provides
 */
public class ThreadLocalProvider<T> implements Provider<T>
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger		= LoggerFactory.getLogger(getClass());
	/**
	 * The ThreadLocal holding the provided Objects.
	 */
	protected final ThreadLocal<T>		threadLocal	= new ThreadLocal<T>();

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.Provider#set(java.lang.Object)
	 */
	@Override
	public void set(T t)
	{
		threadLocal.set(t);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.Provider#get()
	 */
	@Override
	public T get()
	{
		return threadLocal.get();
	}
}
