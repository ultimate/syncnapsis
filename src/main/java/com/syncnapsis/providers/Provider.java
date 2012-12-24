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


/**
 * Simple Interface for Providers. All Providers have to implement <code>public T get()</code>.
 * 
 * @author ultimate
 * @param <T> - the Type this Provider provides
 */
public interface Provider<T>
{
	/**
	 * Get the provided value
	 * 
	 * @return the value
	 */
	public T get();

	/**
	 * Sets the provided value (e.g. for ThreadLocalProviders).
	 * 
	 * @param value - the new value
	 * @return - the old value
	 * @throws UnsupportedOperationException - if the set Operation is not supported by the
	 *             Provider
	 */
	public void set(T t) throws UnsupportedOperationException;
}
