package com.syncnapsis.utils.data;

import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Contact;

public abstract class PoliticsDataGenerator implements DataGenerator, InitializingBean
{
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet()
	{
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

	public abstract <C1 extends BaseObject<?>, C2 extends BaseObject<?>> Contact<C1, C2, ?> createContact(C1 contact1, C2 contact2,
			Map<String, Boolean> authoritiesMap1, Map<String, Boolean> authoritiesMap2);
}
