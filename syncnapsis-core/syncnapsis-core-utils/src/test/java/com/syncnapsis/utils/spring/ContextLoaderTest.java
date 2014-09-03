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

import java.io.File;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import com.syncnapsis.mock.MockBean;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ApplicationContextUtil;
import com.syncnapsis.utils.PropertiesUtil;

@TestExcludesMethods({ "*etContext", "*etLocations" })
public class ContextLoaderTest extends LoggerTestCase
{
	private static final String	properties_test		= "target/test-classes/test.properties";

	private static final String	injectedBeanName	= "myBean";
	private static final String	otherBeanName		= "placeholderConfigurerTarget";

	@TestCoversMethods({ "loadContext", "loadBeans", "*LoadBeans", "closeContext", "getBean" })
	public void testLoadAndCloseContext() throws Exception
	{
		final AtomicBoolean beforeLoadBeansCalled = new AtomicBoolean(false);
		final AtomicBoolean afterLoadBeansCalled = new AtomicBoolean(false);

		ContextLoader loader = new ContextLoader(ApplicationContextUtil.CONTEXT_LOCATION_TEST) {
			@Override
			public void beforeLoadBeans()
			{
				beforeLoadBeansCalled.set(true);
			}

			@Override
			public void afterLoadBeans()
			{
				afterLoadBeansCalled.set(true);
			}
		};

		loader.loadContext();

		assertNotNull(loader.getContext());
		assertNotNull(loader.getBean(otherBeanName, MockBean.class));

		assertTrue(beforeLoadBeansCalled.get());
		assertTrue(afterLoadBeansCalled.get());
	}

	public void testInjectBean() throws Exception
	{
		final MockBean bean = new MockBean();

		ContextLoader loader = new ContextLoader(new String[0]) // no locations
		{
			@Override
			public void beforeLoadBeans()
			{
				injectBean(injectedBeanName, bean);
			}

			@Override
			public void afterLoadBeans()
			{
				MockBean resultBean = getBean(injectedBeanName, MockBean.class);

				assertNotNull(resultBean);
				assertSame(bean, resultBean);

				assertEquals(injectedBeanName, bean.getBeanName());
			}
		};

		loader.loadContext();
	}

	public void testInjectProperties() throws Exception
	{
		final Properties properties = new Properties();
		properties.setProperty("a_key", "a_value");
		properties.setProperty("b_key", "b_value");
		properties.setProperty("c_key.nested", "c_value");

		ContextLoader loader = new ContextLoader(new String[0]) // no locations
		{
			@Override
			public void beforeLoadBeans()
			{
				injectProperties(injectedBeanName, properties);
			}

			@Override
			public void afterLoadBeans()
			{
				Properties propertiesBean = getBean(injectedBeanName, Properties.class);

				assertNotNull(propertiesBean);
				assertEquals(propertiesBean.getProperty("a_key"), properties.getProperty("a_key"));
				assertEquals(propertiesBean.getProperty("b_key"), properties.getProperty("b_key"));
				assertEquals(propertiesBean.getProperty("c_key.nested"), properties.getProperty("c_key.nested"));
			}
		};

		loader.loadContext();
	}

	@TestCoversMethods("injectPropertyPlaceholderConfigurer")
	public void testInjectPropertyPlaceholderConfigurer_withProperties() throws Exception
	{
		final Properties properties = new Properties();
		properties.setProperty("hibernate.connection.url", "myUrl");
		properties.setProperty("hibernate.connection.username", "myUsername");
		properties.setProperty("hibernate.connection.password", "myPassword");
		properties.setProperty("testkey", "testvalue");

		ContextLoader loader = new ContextLoader(ApplicationContextUtil.CONTEXT_LOCATION_TEST) {
			@Override
			public void beforeLoadBeans()
			{
				injectProperties(injectedBeanName, properties);
				injectPropertyPlaceholderConfigurer(injectedBeanName);
			}

			@Override
			public void afterLoadBeans()
			{
				MockBean otherBean = getBean(otherBeanName, MockBean.class);

				assertNotNull(otherBean);
				assertEquals(properties.getProperty("hibernate.connection.url"), otherBean.getUrl());
				assertEquals(properties.getProperty("hibernate.connection.username"), otherBean.getUsername());
				assertEquals(properties.getProperty("hibernate.connection.password"), otherBean.getPassword());
				assertEquals(properties.getProperty("testkey"), otherBean.getValue());
			}
		};

		loader.loadContext();
	}

	@TestCoversMethods("injectPropertyPlaceholderConfigurer")
	public void testInjectPropertyPlaceholderConfigurer_withFile() throws Exception
	{
		final Properties properties = PropertiesUtil.loadProperties(new File(properties_test));

		ContextLoader loader = new ContextLoader(ApplicationContextUtil.CONTEXT_LOCATION_TEST) {
			@Override
			public void beforeLoadBeans()
			{
				injectPropertyPlaceholderConfigurer(new File(properties_test), true);
			}

			@Override
			public void afterLoadBeans()
			{
				MockBean otherBean = getBean(otherBeanName, MockBean.class);

				assertNotNull(otherBean);
				assertEquals(properties.getProperty("testkey"), otherBean.getValue());
			}
		};

		loader.loadContext();
	}

	public void testLoadContext_withMissingProperties() throws Exception
	{
		ContextLoader loader = new ContextLoader(ApplicationContextUtil.CONTEXT_LOCATION_TEST) {
			@Override
			public void beforeLoadBeans()
			{
				// do NOT inject Properties or PropertyPlaceholderConfigurer
			}

			@Override
			public void afterLoadBeans()
			{
				MockBean otherBean = getBean(otherBeanName, MockBean.class);

				assertNotNull(otherBean);
				assertEquals("${testkey}", otherBean.getValue());
			}
		};

		loader.loadContext();
	}

	public void testLoadContext_withMissingPropertyPlaceholderConfigurer() throws Exception
	{
		final Properties properties = new Properties();
		properties.setProperty("hibernate.connection.url", "myUrl");
		properties.setProperty("hibernate.connection.username", "myUsername");
		properties.setProperty("hibernate.connection.password", "myPassword");
		properties.setProperty("testkey", "testvalue");

		ContextLoader loader = new ContextLoader(ApplicationContextUtil.CONTEXT_LOCATION_TEST) {
			@Override
			public void beforeLoadBeans()
			{
				// inject Properties
				injectProperties(injectedBeanName, properties);
				// but do NOT inject PropertyPlaceholderConfigurer
			}

			@Override
			public void afterLoadBeans()
			{
				MockBean otherBean = getBean(otherBeanName, MockBean.class);

				assertNotNull(otherBean);
				assertEquals("${testkey}", otherBean.getValue());
			}
		};

		loader.loadContext();
	}
}
