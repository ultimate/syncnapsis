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
package com.syncnapsis.security;

import java.util.ArrayList;
import java.util.List;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 */
@TestExcludesMethods({ "getTargetClass", "isAccessible" })
public class AccessControllerTest extends LoggerTestCase
{
	public void testIsOwner() throws Exception
	{
		Object owner = new Object();
		Object other = new Object();

		Target t = new Target(owner);

		assertTrue(AccessController.isOwner(t, new Object[] { owner }));
		assertTrue(AccessController.isOwner(t, new Object[] { owner, other }));
		assertFalse(AccessController.isOwner(t, new Object[] { other }));
		assertFalse(AccessController.isOwner(t, new Object[] {}));
		assertFalse(AccessController.isOwner(t));
	}

	public void testIsFriend() throws Exception
	{
		assertFalse(AccessController.isFriend(null));
	}

	public void testIsEnemy() throws Exception
	{
		assertFalse(AccessController.isEnemy(null));
	}
	
	public void testIsAlly() throws Exception
	{
		assertFalse(AccessController.isAlly(null));
	}

	public static class Target implements Ownable<Object>
	{
		private List<Object>	owners;

		public Target(Object owner)
		{
			this(new ArrayList<Object>(1));
			this.owners.add(owner);
		}

		public Target(List<Object> owners)
		{
			super();
			this.owners = owners;
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.security.Ownable#getOwners()
		 */
		@Override
		public List<Object> getOwners()
		{
			return owners;
		}

	}
}
