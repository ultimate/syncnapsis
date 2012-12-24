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
package com.syncnapsis.providers.impl;

import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.providers.impl.SystemTimeProvider;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({TimeProvider.class, SystemTimeProvider.class})
public class SystemTimeProviderTest extends LoggerTestCase
{
	@TestCoversMethods("*")
	public void testProvider()
	{
		logger.debug("testing getTime...");
	
		TimeProvider tp = new SystemTimeProvider();
		
		assertNotNull(tp.get());
		
		assertTrue(tp.get() - System.currentTimeMillis() < 1000);
	}
}
