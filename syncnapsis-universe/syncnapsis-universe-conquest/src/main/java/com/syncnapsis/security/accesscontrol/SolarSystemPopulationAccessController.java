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

import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.security.AccessController;
import com.syncnapsis.security.AccessRule;

/**
 * AccessController for {@link SolarSystemPopulation}
 * 
 * @author ultimate
 */
public class SolarSystemPopulationAccessController extends AccessController<SolarSystemPopulation>
{
	/**
	 * The operation for "resettle"
	 */
	public static final int	OPERATION_RESETTLE	= 1;
	/**
	 * The operation for "spinoff"
	 */
	public static final int	OPERATION_SPINOFF	= 2;

	/**
	 * Create a new AccessController with the given rule
	 * 
	 * @param rule - the AccessRule to use
	 */
	public SolarSystemPopulationAccessController(AccessRule rule)
	{
		super(SolarSystemPopulation.class, rule);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.AccessController#isAccessible(java.lang.Object, int, java.lang.Object, java.lang.Object[])
	 */
	@Override
	public boolean isAccessible(SolarSystemPopulation target, int operation, Object context, Object... authorities)
	{
		// context will be ignored (should be same as target)
		// operation does not have any influence
		return rule.is(AccessRule.OWNER, target, authorities);
	}
}
