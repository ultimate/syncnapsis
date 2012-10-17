package com.syncnapsis.utils.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ApplicationContextUtil;

@TestExcludesMethods({ "afterPropertiesSet", "generate*", "set*", "get*" })
public class AuthorityGenericImplDataGeneratorTest extends BaseDaoTestCase
{
	private AuthorityGenericImplDataGenerator	gen	= new AuthorityGenericImplDataGenerator();

	@Override
	public void setUp() throws Exception
	{
		super.setUp();

		ApplicationContextUtil.autowire(applicationContext, gen);
		gen.setProjectDirectory(new File(".").getAbsolutePath());
		gen.afterPropertiesSet();
	}

	public void testCreateAuthorities() throws Exception
	{
		Map<String, Boolean> authoritiesMap = new HashMap<String, Boolean>();
		authoritiesMap.put("allowedToFlyIntoTerritory", true);
		authoritiesMap.put("allowedToSeeEconomy", false);
		authoritiesMap.put("allowedToSeeOnlineStatus", true);
		authoritiesMap.put("allowedToSeeReports", false);
		authoritiesMap.put("allowedToSeeSpecialOffersInMarketPlace", true);
		authoritiesMap.put("allowedToSeeTroops", false);
		authoritiesMap.put("allowedToUseScanners", true);
		authoritiesMap.put("allowedToUseStargates", false);
		authoritiesMap.put("notAllowedToAttack", true);

		AuthoritiesGenericImpl auth = gen.createAuthorities(authoritiesMap);

		for(Entry<String, Boolean> e: authoritiesMap.entrySet())
		{
			assertEquals((boolean) e.getValue(), auth.isAuthorityGranted(e.getKey()));
		}
	}
}
