package com.syncnapsis.tests;

import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.utils.ApplicationContextUtil;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class BaseSpringContextTestCase extends LoggerTestCase
{	
	protected static final ConfigurableApplicationContext applicationContext;	

	protected TimeProvider timeProvider;
	
	protected boolean autowired = false;
	
	static
	{
		applicationContext = ApplicationContextUtil.createApplicationContext(ApplicationContextUtil.getDefaultConfigLocations());
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		if(!autowired)
		{
			ApplicationContextUtil.autowire(applicationContext, this);
			autowired = true;
		}
	}
}
