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
package com.syncnapsis.security.validation;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.data.DefaultData;

/**
 * @author ultimate
 * 
 */
@TestExcludesMethods({ "*etRegExp", "getPattern" })
public class RegExpValidatorTest extends LoggerTestCase
{
	public void testIsValid() throws Exception
	{
		String regExp = DefaultData.REGEXP_EMAIL;
		RegExpValidator v = new RegExpValidator(regExp);

		assertTrue(v.isValid("user-name@test.com"));
		assertTrue(v.isValid("user_name@test.com"));
		assertTrue(v.isValid("username@test.com"));
		assertTrue(v.isValid("user.name@test.com"));

		assertFalse(v.isValid("user name@test.com"));
		assertFalse(v.isValid("user name@test.com"));
		assertFalse(v.isValid("user@,ame@test.com"));
		assertFalse(v.isValid("www.test.com"));
	}
}
