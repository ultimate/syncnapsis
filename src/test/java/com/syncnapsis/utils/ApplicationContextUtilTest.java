package com.syncnapsis.utils;

import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods({"*etApplicationContext", "getInstance"})
public class ApplicationContextUtilTest extends BaseSpringContextTestCase
{
	public ApplicationContextUtilTest()
	{
		// so wird verhindert, dass für dieses Object automatisch autowire angewandt wird
		this.autowired = true;
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
	
	@TestCoversMethods({"createApplicationContext", "getDefaultConfigLocations"})
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
