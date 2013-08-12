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
package com.syncnapsis.security.accesscontrol;

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.Participant;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.model.UserRole;
import com.syncnapsis.tests.LoggerTestCase;

/**
 * @author ultimate
 */
public class SolarSystemPopulationAccessControllerTest extends LoggerTestCase
{
	public void testGetTargetClass() throws Exception
	{
		assertEquals(SolarSystemPopulation.class, new SolarSystemPopulationAccessController().getTargetClass());
	}

	public void testIsAccessible() throws Exception
	{
		SolarSystemPopulationAccessController controller = new SolarSystemPopulationAccessController();

		Player owner = getPlayer(ApplicationBaseConstants.ROLE_NORMAL_USER);
		Player other = getPlayer(ApplicationBaseConstants.ROLE_NORMAL_USER);

		SolarSystemPopulation population = new SolarSystemPopulation();
		population.setParticipant(new Participant());
		population.getParticipant().setEmpire(new Empire());
		population.getParticipant().getEmpire().setPlayer(owner);

		// owner
		assertTrue(controller.isAccessible(population, 0, owner));

		// other
		assertFalse(controller.isAccessible(population, 0, other));
	}

	private long	playerId	= 0;

	private Player getPlayer(String role)
	{
		Player player = new Player();
		player.setId(--playerId);
		player.setUser(new User());
		player.getUser().setId(playerId);
		player.getUser().setRole(new UserRole());
		player.getUser().getRole().setId(playerId);
		player.getUser().getRole().setRolename(role);
		return player;
	}
}
