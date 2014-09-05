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
		assertEquals(SolarSystemPopulation.class, new SolarSystemPopulationAccessController(new BaseAccessRule()).getTargetClass());
	}

	public void testIsAccessible() throws Exception
	{
		SolarSystemPopulationAccessController controller = new SolarSystemPopulationAccessController(new BaseAccessRule());

		Player owner = getPlayer(ApplicationBaseConstants.ROLE_NORMAL_USER);
		Player other = getPlayer(ApplicationBaseConstants.ROLE_NORMAL_USER);

		SolarSystemPopulation population = new SolarSystemPopulation();
		population.setParticipant(new Participant());
		population.getParticipant().setEmpire(new Empire());
		population.getParticipant().getEmpire().setPlayer(owner);

		Object context = null; 
		
		// owner
		assertTrue(controller.isAccessible(population, 0, context, getAuthorities(owner)));

		// other
		assertFalse(controller.isAccessible(population, 0, context, getAuthorities(other)));
	}

	private long	playerId	= 0;

	private Player getPlayer(int role)
	{
		Player player = new Player();
		player.setId(--playerId);
		player.setUser(new User());
		player.getUser().setId(playerId);
		player.getUser().setRole(new UserRole());
		player.getUser().getRole().setId(playerId);
		player.getUser().getRole().setMask(role);
		return player;
	}

	private Object[] getAuthorities(Player player)
	{
		return new Object[] { player, player.getRole(), player.getUser(), player.getUser().getRole() };
	}
}
