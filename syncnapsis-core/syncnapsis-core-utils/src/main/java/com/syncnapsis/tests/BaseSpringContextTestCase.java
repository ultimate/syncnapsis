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
package com.syncnapsis.tests;

import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.utils.ApplicationContextUtil;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class BaseSpringContextTestCase extends LoggerTestCase
{
	protected static ConfigurableApplicationContext	applicationContext;

	protected TimeProvider							timeProvider;

	protected boolean								autowired	= false;

	public static synchronized void initApplicationContext(String... locations)
	{
		if(applicationContext == null)
			applicationContext = ApplicationContextUtil.createApplicationContext(locations);
	}

	protected synchronized void initApplicationContext()
	{
		initApplicationContext(ApplicationContextUtil.CONTEXT_LOCATION_TEST, ApplicationContextUtil.CONTEXT_LOCATION_DEFAULT);
	}

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		if(!autowired)
		{
			initApplicationContext();
			ApplicationContextUtil.autowire(applicationContext, this);
			autowired = true;
		}
	}
}
