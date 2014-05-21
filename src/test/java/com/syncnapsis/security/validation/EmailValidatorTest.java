/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
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
import com.syncnapsis.utils.data.ExtendedRandom;

/**
 * @author ultimate
 *
 */
@TestExcludesMethods({ "*etRegExp" })
public class EmailValidatorTest extends LoggerTestCase
{
	public void testIsValid() throws Exception
	{
		EmailValidator v = new EmailValidator();
		
		assertTrue(v.isValid("example@syncnapsis.com"));
		assertTrue(v.isValid("info@example.com"));
		
		assertFalse(v.isValid("example@syncnapsis"));
		
		ExtendedRandom r = new ExtendedRandom();
		String email;
		for(int i = 0; i < 100; i++)
		{
			email = r.nextEmail(30+i);
			logger.debug(email);
			assertTrue(v.isValid(email));
		}
	}
}
