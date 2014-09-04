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
		authoritiesMap.put("auth101", true);
		authoritiesMap.put("auth102", false);
		authoritiesMap.put("auth103", true);
		authoritiesMap.put("auth201", false);
		authoritiesMap.put("auth202", true);

		AuthoritiesGenericImpl auth = gen.createAuthorities(authoritiesMap);

		for(Entry<String, Boolean> e: authoritiesMap.entrySet())
		{
			assertEquals((boolean) e.getValue(), auth.isAuthorityGranted(e.getKey()));
		}
	}
}
