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
package com.syncnapsis.websockets.engine;

import com.syncnapsis.enums.EnumEngineSupport;
import com.syncnapsis.exceptions.WebSocketEngineException;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods({ "start0", "stop0" })
public class BaseTCPEngineTest extends LoggerTestCase
{
	@TestCoversMethods({ "*etPort", "*etSslPort" })
	public void testGetAndSet() throws Exception
	{
		BaseTCPEngine e = new BaseTCPEngine(EnumEngineSupport.NOT_SUPPORTED) {
			//@formatter:off
			protected void start0() throws WebSocketEngineException {}
			protected void stop0() throws WebSocketEngineException {}
			//@formatter:on
		};

		// simple properties
		getAndSetTest(e, "port", Integer.class, 12345);
		getAndSetTest(e, "sslPort", Integer.class, 12345);
	}

	public void testAfterPropertiesSet() throws Exception
	{
		BaseTCPEngine e = new BaseTCPEngine(EnumEngineSupport.NOT_SUPPORTED) {
			//@formatter:off
			protected void start0() throws WebSocketEngineException {}
			protected void stop0() throws WebSocketEngineException {}
			//@formatter:on
		};

		try
		{
			e.afterPropertiesSet();
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException ex)
		{
			assertEquals("either port or sslPort must not be null", ex.getMessage());
		}

		e.setPort(111); // OK
		e.afterPropertiesSet();
		e.setPort(0); // OK
		e.afterPropertiesSet();
		e.setPort(65535); // OK
		e.afterPropertiesSet();
		e.setPort(-1); // NOT OK
		try
		{
			e.afterPropertiesSet();
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException ex)
		{
			assertEquals("port must be between 0 (inclusive) and 65536 (exclusive)", ex.getMessage());
		}
		e.setPort(65536); // NOT OK
		try
		{
			e.afterPropertiesSet();
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException ex)
		{
			assertEquals("port must be between 0 (inclusive) and 65536 (exclusive)", ex.getMessage());
		}
		
		e.setPort(null);

		e.setSslPort(111); // OK
		e.afterPropertiesSet();
		e.setSslPort(0); // OK
		e.afterPropertiesSet();
		e.setSslPort(65535); // OK
		e.afterPropertiesSet();
		e.setSslPort(-1); // NOT OK
		try
		{
			e.afterPropertiesSet();
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException ex)
		{
			assertEquals("sslPort must be between 0 (inclusive) and 65536 (exclusive)", ex.getMessage());
		}
		e.setSslPort(65536); // NOT OK
		try
		{
			e.afterPropertiesSet();
			fail("expected Exception not occurred!");
		}
		catch(IllegalArgumentException ex)
		{
			assertEquals("sslPort must be between 0 (inclusive) and 65536 (exclusive)", ex.getMessage());
		}
	}
}