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

import com.syncnapsis.constants.GameBaseConstants;
import com.syncnapsis.data.model.PlayerRole;
import com.syncnapsis.data.service.PlayerRoleManager;
import com.syncnapsis.tests.BaseDaoTestCase;
import org.springframework.beans.BeanUtils;

public class PlayerRoleExistsExceptionTest extends BaseDaoTestCase
{
	private PlayerRoleManager playerRoleManager;

	public void testAddExistingPlayerRole() throws Exception
	{
		logger.debug("testing add existing playerRole...");
		assertNotNull(playerRoleManager);

		PlayerRole playerRole = playerRoleManager.getByName(GameBaseConstants.ROLE_DEMO_PLAYER);

		// create new object with null id - Hibernate doesn't like setId(null)
		PlayerRole playerRole2 = new PlayerRole();
		BeanUtils.copyProperties(playerRole, playerRole2);
		playerRole2.setId(10000L);
		playerRole2.setVersion(null);

		// try saving as new playerRole, this should fail b/c of unique keys
		try
		{
			logger.debug("Expecting Exception...");
			playerRoleManager.save(playerRole2);
			fail("Duplicate playerRole didn't throw PlayerRoleExistsException");
		}
		catch(PlayerRoleExistsException uee)
		{
			assertNotNull(uee);
		}
	}
}
