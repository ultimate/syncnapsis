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

import com.syncnapsis.data.dao.hibernate.GenericRankDaoHibernate;
import com.syncnapsis.data.service.impl.GenericRankManagerImpl;
import com.syncnapsis.tests.CoverageTestCase;

public class StatsApiCoverageTest extends CoverageTestCase
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
		if(c.isInterface())
			return "Interface";
		if(c.isAnnotation())
			return "Annotation";
		if(c.equals(GenericRankDaoHibernate.class))
			return "Generic-Data-Class --> Test in specific implementation!";
		if(c.equals(GenericRankManagerImpl.class))
			return "Generic-Data-Class --> Test in specific implementation!";
		return super.ignoreSpecialClass(packageName, c);
	}
}
