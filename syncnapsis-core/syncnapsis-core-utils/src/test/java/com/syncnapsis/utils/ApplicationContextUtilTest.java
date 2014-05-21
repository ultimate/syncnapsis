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
package com.syncnapsis.utils;

import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods({"*etApplicationContext", "getInstance"})
public class ApplicationContextUtilTest extends BaseSpringContextTestCase
{
	public ApplicationContextUtilTest()
	{
		// so wird verhindert, dass für dieses Object automatisch autowire angewandt wird
		this.autowired = true;
		initApplicationContext();
	}
	
	public void testAutowire() throws Exception
	{
		assertNull(timeProvider);  // defined in BaseSpringContextTestCase
		
		String[] a = applicationContext.getBeanNamesForType(TimeProvider.class);
		for(String s: a)
			logger.debug(s);
		ApplicationContextUtil.autowire(applicationContext, this);
		
		assertNotNull(timeProvider);		
	}
	
	public void testCreateApplicationContext() throws Exception
	{
		assertNotNull(applicationContext);
	}
	
	public void testGetBean() throws Exception
	{
		TimeProvider um = ApplicationContextUtil.getBean(applicationContext, TimeProvider.class);
		assertNotNull(um);
	}
	
	public void testGetBeanName() throws Exception
	{
		assertNotNull(ApplicationContextUtil.getBean("duplicate"));
		assertNotNull(ApplicationContextUtil.getBean("dup1"));
		assertNotNull(ApplicationContextUtil.getBean("dup2"));
		
		Object dup = ApplicationContextUtil.getBean("duplicate");
		assertEquals(dup, ApplicationContextUtil.getBean("dup1"));
		assertEquals(dup, ApplicationContextUtil.getBean("dup2"));
		
		logger.debug("name of 'duplicate' is '" + ApplicationContextUtil.getBeanName(dup) + "'");
		assertEquals("duplicate", ApplicationContextUtil.getBeanName(dup));
	}
}
