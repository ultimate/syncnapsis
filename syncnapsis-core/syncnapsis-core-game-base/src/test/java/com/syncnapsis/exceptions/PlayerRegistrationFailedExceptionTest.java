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
package com.syncnapsis.exceptions;

import com.syncnapsis.data.service.PlayerManager;
import com.syncnapsis.tests.BaseDaoTestCase;

public class PlayerRegistrationFailedExceptionTest extends BaseDaoTestCase
{
	private PlayerManager playerManager;

	public void testRegisterPlayerInvalid() throws Exception
	{
		try
		{
			// username not available
			playerManager.register("admin", "a@example.com", "a", "a");
			fail("expected Exception not occurred!");
		}
		catch(PlayerRegistrationFailedException e)
		{
			assertNotNull(e);
		}
		try
		{
			// username blacklisted
			playerManager.register("badguy", "a@example.com", "a", "a");
			fail("expected Exception not occurred!");
		}
		catch(PlayerRegistrationFailedException e)
		{
			assertNotNull(e);
		}
		try
		{
			// email blacklisted
			playerManager.register("goodguy", "spammer@example.com", "a", "a");
			fail("expected Exception not occurred!");
		}
		catch(PlayerRegistrationFailedException e)
		{
			assertNotNull(e);
		}
		try
		{
			// no password
			playerManager.register("goodguy", "a@example.com", null, null);
			fail("expected Exception not occurred!");
		}
		catch(PlayerRegistrationFailedException e)
		{
			assertNotNull(e);
		}
		try
		{
			// password not confirmed
			playerManager.register("goodguy", "a@example.com", "a", "b");
			fail("expected Exception not occurred!");
		}
		catch(PlayerRegistrationFailedException e)
		{
			assertNotNull(e);
		}
	}
}
