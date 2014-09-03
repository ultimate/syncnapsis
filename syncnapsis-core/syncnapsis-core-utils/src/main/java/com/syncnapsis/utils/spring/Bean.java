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
package com.syncnapsis.utils.spring;

import org.springframework.beans.factory.BeanNameAware;

/**
 * Simple base for all beans that should be aware of their own name
 * 
 * @author ultimate
 */
public class Bean implements BeanNameAware
{
	/**
	 * A name for this bean
	 */
	private String	beanName;

	/**
	 * Get a name for this bean in the application context set via {@link Bean#setBeanName(String)}
	 * 
	 * @return a name for this bean in the application context
	 */
	public String getBeanName()
	{
		return beanName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
	 */
	@Override
	public void setBeanName(String beanName)
	{
		this.beanName = beanName;
	}
}
