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
package com.syncnapsis.utils.data;

import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;

/**
 * @author ultimate
 */
public class SolarSystemInfrastructureGenerator extends Generator<SolarSystemInfrastructure>
{

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.data.Generator#generate(com.syncnapsis.utils.data.ExtendedRandom,
	 * java.lang.Object[])
	 */
	@Override
	public SolarSystemInfrastructure generate(ExtendedRandom random, Object... args)
	{
		Match match = (Match) args[0];
		SolarSystem system = (SolarSystem) args[1];
		
		SolarSystemInfrastructure infrastructure = new SolarSystemInfrastructure();
		infrastructure.setActivated(true);
		infrastructure.setFirstColonizationDate(null);
		infrastructure.setHabitability(random.nextInt(min, max));
		infrastructure.setInfrastructure(random.nextInt(min, max));
		infrastructure.setMatch(match);
		infrastructure.setSize(random.nextInt(min, max));
		infrastructure.setSolarSystem(system);

		return infrastructure;
	}
}
