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
package com.syncnapsis.providers.impl;

import com.syncnapsis.providers.TimeProvider;

/**
 * Provider-Class for accessing the current System time.<br>
 * Creating a Bean-Instance of this class offeres access to the system time via Springs
 * ApplicationContext with the additional opportunity to replace the TimeProvider for test purposes.
 * 
 * @see System#currentTimeMillis()
 * @author ultimate
 */
// TODO own impl-Modul?
public class SystemTimeProvider implements TimeProvider
{
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.TimeProvider#get()
	 */
	@Override
	public Long get()
	{
		return System.currentTimeMillis();
	}

	/**
	 * Operation not supported by this Provider
	 * @throws UnsupportedOperationException 
	 */
	@Override
	public void set(Long time) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("Setting System time is not supported!");
	}
}
