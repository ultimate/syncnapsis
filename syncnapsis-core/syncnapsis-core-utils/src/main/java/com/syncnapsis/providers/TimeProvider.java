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
 * Provider-Interface allowing access to the current time.<br>
 * Creating a Bean of this type offers the opportunity to change the way of accessing the time via
 * SpringContext-Configuration.<br>
 * This was time can be manipulated during test cases or for modified timelines.
 * 
 * @author ultimate
 */
// TODO own api-Module?
// toReal(long time)
// fromReal(long time)
public interface TimeProvider extends Provider<Long>
{
	/**
	 * Returns the current time in ms
	 * 
	 * @return the time
	 */
	@Override
	public Long get();

	/**
	 * Sets the current time in ms.<br>
	 * May not be supported by some implementations.
	 * 
	 * @param time - the new time
	 * @return the old time
	 */
	@Override
	public void set(Long time) throws UnsupportedOperationException;
}
