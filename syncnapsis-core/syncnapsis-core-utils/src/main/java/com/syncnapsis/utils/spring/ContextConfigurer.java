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
package com.syncnapsis.utils.spring;

/**
 * @author ultimate
 */
public interface ContextConfigurer
{
	/**
	 * Method executed by {@link ContextLoader#loadContext()} <b>before</b>
	 * {@link ContextLoader#loadBeans()} is called.<br>
	 * Override this method for example if you want to inject beans prior to loading the beans from
	 * the locations.
	 * 
	 * @param loader - the {@link ContextConfigurer} calling the method
	 */
	public void beforeLoadBeans(ContextLoader loader);

	/**
	 * Method executed by {@link ContextLoader#loadContext()} <b>after</b>
	 * {@link ContextLoader#loadBeans()} is called.<br>
	 * Override this method for example if you want to inject beans after to loading the beans from
	 * the locations or if you want to check the application context for validity / completeness.
	 * 
	 * @param loader
	 */
	public void afterLoadBeans(ContextLoader loader);
}
