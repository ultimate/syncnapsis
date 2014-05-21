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
package com.syncnapsis.exceptions;

import com.syncnapsis.data.service.PlayerManager;
import com.syncnapsis.tests.BaseDaoTestCase;

public class PlayerSelectionInvalidExceptionTest extends BaseDaoTestCase
{
	private PlayerManager playerManager;

	public void testAddSelfSitter() throws Exception
	{
		Long playerId1 = playerManager.getByUsername("user1").getId();
		
		try
		{
			playerManager.addSitter(playerId1, playerId1);
			fail("Expected exception not occurred!");
		}
		catch(PlayerSelectionInvalidException e)
		{
			assertNotNull(e);
		}
	}
}
