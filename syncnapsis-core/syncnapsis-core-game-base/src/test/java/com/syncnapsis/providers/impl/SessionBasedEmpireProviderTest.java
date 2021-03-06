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
package com.syncnapsis.providers.impl;

import org.springframework.mock.web.MockHttpSession;

import com.syncnapsis.constants.GameBaseConstants;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.providers.EmpireProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({SessionBasedEmpireProvider.class, EmpireProvider.class})
public class SessionBasedEmpireProviderTest extends LoggerTestCase
{
	public void testProvider() throws Exception
	{
		SessionProvider sp = new ThreadLocalSessionProvider();
		SessionBasedEmpireProvider p = new SessionBasedEmpireProvider();
		p.setSessionProvider(sp);		
		sp.set(new MockHttpSession());
		
		Empire value = new Empire();
		value.setShortName("a name");
		
		p.set(value);
		
		assertEquals(GameBaseConstants.SESSION_EMPIRE_KEY, p.getAttributeName());
		assertEquals(value, p.get());
		assertNotNull(sp.get().getAttribute(p.getAttributeName()));
		assertEquals(value, sp.get().getAttribute(p.getAttributeName()));
	}
}
