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
import com.syncnapsis.utils.data.ExtendedRandom;

/**
 * @author ultimate
 *
 */
@TestExcludesMethods({ "*etRegExp" })
public class NameValidatorTest extends LoggerTestCase
{
	public void testIsValid() throws Exception
	{
		NameValidator v = new NameValidator();
		
		assertTrue(v.isValid("somename"));
		assertTrue(v.isValid("some_name"));
		assertTrue(v.isValid("some-name"));
		assertTrue(v.isValid("s0m3n4m3"));
		
		assertFalse(v.isValid("some name"));
		assertFalse(v.isValid("some,name"));
		assertFalse(v.isValid("some;name"));
		assertFalse(v.isValid("some@name"));
		
		String source = DefaultData.STRING_ASCII_LETTERS_LOWER + DefaultData.STRING_ASCII_LETTERS_UPPER + DefaultData.STRING_ASCII_NUMBERS + DefaultData.STRING_EMAIL_SYMBOLS;
		logger.debug("source: " + source);
		
		ExtendedRandom r = new ExtendedRandom();
		String name;
		for(int i = 0; i < 100; i++)
		{
			name = r.nextString(r.nextInt(1, 20), source);
			logger.debug(name);
			assertTrue(v.isValid(name));
		}
	}
}
