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
package com.syncnapsis.websockets.engine;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * Test for {@link BaseWebEngine}
 * 
 * @author ultimate
 */
@TestExcludesMethods({ "start0", "stop0" })
public class BaseWebEngineTest extends LoggerTestCase
{
	@TestCoversMethods({ "afterPropertiesSet", "*etPath" })
	public void testAfterPropertiesSet() throws Exception
	{
		BaseWebEngine e = new BaseWebEngine() {};
		assertNull(e.getPath());
		e.afterPropertiesSet();
		assertNotNull(e.getPath());
		assertEquals("/*", e.getPath());

		String path = "/a_path";
		e.setPath(path);
		e.afterPropertiesSet();
		assertEquals(path, e.getPath());
	}
}