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
package com.syncnapsis;

import com.syncnapsis.exceptions.ObjectNotFoundException;
import com.syncnapsis.tests.CoverageTestCase;

public class DataCoverageTest extends CoverageTestCase
{
	@Override
	public void testCoverage() throws Exception
	{
		logger.debug("checking test-coverage...");
		super.testCoverage();
	}

	@Override
	protected String ignoreSpecialClass(String packageName, Class<?> c)
	{
		String dataPackage = "com.syncnapsis.data";
		if(c.getName().startsWith(dataPackage) && !c.getName().startsWith(dataPackage + ".dao.mock"))
			return "Data-Class --> Test in specific implementation!";
		if(c.equals(ObjectNotFoundException.class))
			return "Exception-Super-Type --> Test in specific implementation!";
		return super.ignoreSpecialClass(packageName, c);
	}
}
