package com.syncnapsis.utils.data;

import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.data.model.base.Authorities;
import com.syncnapsis.data.service.AuthoritiesManager;

public abstract class AuthorityDataGenerator<A extends Authorities> implements DataGenerator, InitializingBean
{
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

	protected AuthoritiesManager<A>		authoritiesManager;

	public AuthoritiesManager<A> getAuthoritiesManager()
	{
		return authoritiesManager;
	}

	public void setAuthoritiesManager(AuthoritiesManager<A> authoritiesManager)
	{
		this.authoritiesManager = authoritiesManager;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet()
	{
		Assert.notNull(authoritiesManager, "authoritiesManager must not be null!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.data.DataGenerator#generateDummyData(int)
	 */
	@Override
	public void generateDummyData(int amount)
	{
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.data.DataGenerator#generateTestData(java.util.Properties)
	 */
	@Override
	public void generateTestData(Properties testDataProperties)
	{
		throw new UnsupportedOperationException();
	}

	public abstract A createAuthorities(Map<String, Boolean> authoritiesMap);
}
