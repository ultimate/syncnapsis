package com.syncnapsis.utils.data;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.base.Contact;

public abstract class PoliticsDataGenerator extends DataGenerator implements InitializingBean
{
	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
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
