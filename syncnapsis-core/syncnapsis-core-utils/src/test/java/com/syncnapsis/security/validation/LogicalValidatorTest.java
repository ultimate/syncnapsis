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

import java.util.ArrayList;
import java.util.List;

import com.syncnapsis.enums.EnumLogicalOperator;
import com.syncnapsis.security.Validator;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 * 
 */
@TestExcludesMethods({ "*etOperator", "*etDefaultState", "*etValidators" })
public class LogicalValidatorTest extends LoggerTestCase
{
	public void testIsValid() throws Exception
	{
		Validator<String> v1 = new Validator<String>() {
			public boolean isValid(String value)
			{
				return true;
			}
		};
		Validator<String> v2 = new Validator<String>() {
			public boolean isValid(String value)
			{
				return false;
			}
		};
		List<Validator<String>> validators = new ArrayList<Validator<String>>();
		validators.add(v1);
		validators.add(v2);
		
		LogicalValidator<String> validator = new LogicalValidator<String>(EnumLogicalOperator.AND);
		validator.setValidators(validators);

		validator.setOperator(EnumLogicalOperator.AND);
		assertEquals(false, validator.isValid(""));		

		validator.setOperator(EnumLogicalOperator.OR);
		assertEquals(true, validator.isValid(""));		
		
		validator.setOperator(EnumLogicalOperator.XOR);
		assertEquals(true, validator.isValid(""));		
		
		validator.setOperator(EnumLogicalOperator.AND);
		assertEquals(false, validator.isValid(""));		
	}
}
