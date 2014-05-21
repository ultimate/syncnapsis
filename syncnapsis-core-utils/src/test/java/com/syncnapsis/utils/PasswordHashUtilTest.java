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
