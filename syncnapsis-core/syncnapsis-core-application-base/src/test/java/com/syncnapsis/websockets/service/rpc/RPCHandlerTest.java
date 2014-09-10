/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.websockets.service.rpc;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * Test for {@link RPCHandler} within the context of the application
 * 
 * @author ultimate
 */
@TestExcludesMethods({ "set*", "get*", "afterPropertiesSet" })
public class RPCHandlerTest extends BaseDaoTestCase
{
	private BeanRPCHandler		beanRPCHandler;
	private UserManager			userManager;

	private static final String	targetObject	= "userManager";
	private static final String	targetMethod	= "save";

	public void testGetTarget() throws Exception
	{
		// this test especially checks functionality on proxies!
		assertTrue(Proxy.isProxyClass(userManager.getClass()));
		
		Object target = beanRPCHandler.getTarget(targetObject);

		assertSame(userManager, target);
	}

	public void testGetMethod() throws Exception
	{
		// this test especially checks functionality on proxies!
		assertTrue(Proxy.isProxyClass(userManager.getClass()));
		
		long userId = 1L;
		
		User originalUser = userManager.get(userId);
		
		Map<String, Object> userArg = new HashMap<String, Object>();
		userArg.put("id", userId);
		userArg.put("username", "some user");

		Object[] authorities = new Object[] {};

		Object[] args = new Object[] { userArg };

		Method m = beanRPCHandler.getMethod(userManager, targetMethod, args, authorities);
		
		assertNotNull(m);
		assertEquals(targetMethod, m.getName());
		
		// check the conversion of the user
		assertNotNull(args[0]);
		assertTrue(args[0] instanceof User);
		User convertedUser = (User) args[0];
		// chevk if properties have been merged
		assertEquals(userArg.get("id"), convertedUser.getId());
		assertEquals(userArg.get("username"), convertedUser.getUsername());
		// check if the rest of the content has been loaded from the DataMapper
		assertNotNull(convertedUser.getEmail());
		assertEquals(originalUser.getEmail(), convertedUser.getEmail());
	}
}
