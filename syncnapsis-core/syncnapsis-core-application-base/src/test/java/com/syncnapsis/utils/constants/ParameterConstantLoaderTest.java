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
package com.syncnapsis.utils.constants;

import java.util.Arrays;

import org.jmock.Expectations;

import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.tests.LoggerTestCase;

/**
 * @author ultimate
 * 
 */
public class ParameterConstantLoaderTest extends LoggerTestCase
{
	private static final Constant<String>	PARAM_X	= new StringConstant("param.x");

	public void testLoad() throws Exception
	{
		final ParameterManager mockParameterManager = mockContext.mock(ParameterManager.class);
		final String value = "12345";

		ParameterConstantLoader cl = new ParameterConstantLoader(mockParameterManager);

		mockContext.checking(new Expectations() {
			{
				oneOf(mockParameterManager).getString(PARAM_X.getName());
				will(returnValue(value));
			}
		});

		cl.setConstantClasses(Arrays.asList(new Class<?>[] { getClass() }));
		cl.afterPropertiesSet();
		
		mockContext.assertIsSatisfied();
		
		assertEquals(value, PARAM_X.asString());
	}
}
