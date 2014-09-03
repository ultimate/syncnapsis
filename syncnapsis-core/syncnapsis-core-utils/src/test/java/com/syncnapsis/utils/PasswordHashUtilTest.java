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
package com.syncnapsis.utils;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class PasswordHashUtilTest extends LoggerTestCase
{
	@TestCoversMethods("*")
	public void testHashing() throws Exception
	{
		// Print out 10 hashes
		for(int i = 0; i < 10; i++)
			logger.debug(PasswordHashUtil.createHash("p\r\nassw0Rd!"));
		
		logger.debug("admin => " + PasswordHashUtil.createHash("admin"));
		logger.debug("user1 => " + PasswordHashUtil.createHash("user1"));
		logger.debug("user2 => " + PasswordHashUtil.createHash("user2"));
		logger.debug("hash length = " + PasswordHashUtil.createHash("admin").length());

		// Test password validation
		logger.debug("Running tests...");
		for(int i = 0; i < 100; i++)
		{
			String password = "" + i;
			String hash = PasswordHashUtil.createHash(password);
			String secondHash = PasswordHashUtil.createHash(password);
			assertFalse("two hashes are equal", hash.equals(secondHash));
			String wrongPassword = "" + (i + 1);
			assertFalse("wrong password was validated", PasswordHashUtil.validatePassword(wrongPassword, hash));
			assertTrue("correct password was not validated", PasswordHashUtil.validatePassword(password, hash));
		}
	}
}
