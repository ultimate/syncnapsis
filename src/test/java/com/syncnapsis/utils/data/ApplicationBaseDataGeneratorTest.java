package com.syncnapsis.utils.data;

import com.syncnapsis.tests.BaseSpringContextTestCase;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ApplicationContextUtil;

@TestExcludesMethods({"get*", "set*", "generate*"})
public class ApplicationBaseDataGeneratorTest extends BaseSpringContextTestCase
{
	protected ApplicationBaseDataGenerator gen = new ApplicationBaseDataGenerator();
	
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		ApplicationContextUtil.autowire(applicationContext, gen);
	}
	
	public void testCreateUser()
	{
//		gen.createUser(name, rolename)
	}
	
	public void testCreateUserContact()
	{
//		gen.createUser(name, rolename)
	}
}
