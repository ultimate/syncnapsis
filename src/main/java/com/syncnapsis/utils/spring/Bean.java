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

import com.syncnapsis.utils.ApplicationContextUtil;

/**
 * Simple base for all beans that should be aware of their own name
 * 
 * @author ultimate
 */
public class Bean
{
	/**
	 * A name for this bean
	 */
	private String	beanName;

	/**
	 * Get a name for this bean in the application context
	 * 
	 * @see ApplicationContextUtil#getBeanName(Object)
	 * @return a name for this bean in the application context, or null if none found
	 */
	public String getBeanName()
	{
		if(beanName == null)
		{
			beanName = ApplicationContextUtil.getBeanName(this);
		}
		return beanName;
	}
}
