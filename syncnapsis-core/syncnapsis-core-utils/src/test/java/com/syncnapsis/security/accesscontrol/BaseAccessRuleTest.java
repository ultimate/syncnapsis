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

import java.util.ArrayList;
import java.util.List;

import com.syncnapsis.data.model.base.BitMask;
import com.syncnapsis.security.AccessRule;
import com.syncnapsis.security.Ownable;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 */
@TestExcludesMethods({ "getTargetClass", "isAccessible" })
public class BaseAccessRuleTest extends LoggerTestCase
{
	public void testIs() throws Exception
	{
		BaseAccessRule rule = new BaseAccessRule();

		Object owner = new Object();
		Object other = new Object();

		Target t = new Target(owner);

		assertTrue(rule.is(AccessRule.ANYBODY, t, owner));
		assertTrue(rule.is(AccessRule.OWNER, t, owner));
		assertFalse(rule.is(AccessRule.FRIEND, t, owner));
		assertFalse(rule.is(AccessRule.ENEMY, t, owner));
		assertFalse(rule.is(AccessRule.ALLY, t, owner));
		assertFalse(rule.is(AccessRule.NOBODY, t, owner));

		assertTrue(rule.is(AccessRule.ANYBODY, t, other));
		assertFalse(rule.is(AccessRule.OWNER, t, other));
		assertFalse(rule.is(AccessRule.FRIEND, t, other));
		assertFalse(rule.is(AccessRule.ENEMY, t, other));
		assertFalse(rule.is(AccessRule.ALLY, t, other));
		assertFalse(rule.is(AccessRule.NOBODY, t, other));

		assertTrue(rule.is(AccessRule.ANYBODY, t, owner, other));
		assertTrue(rule.is(AccessRule.OWNER, t, owner, other));
		assertFalse(rule.is(AccessRule.FRIEND, t, owner, other));
		assertFalse(rule.is(AccessRule.ENEMY, t, owner, other));
		assertFalse(rule.is(AccessRule.ALLY, t, owner, other));
		assertFalse(rule.is(AccessRule.NOBODY, t, owner, other));
	}

	public void testIsOf() throws Exception
	{
		BaseAccessRule rule = new BaseAccessRule();

		Role roleA = new Role(1 << 0);
		Role roleB = new Role(1 << 2);
		Role roleC = new Role(1 << 3);

		assertTrue(rule.isOf(AccessRule.ANYROLE, roleA));
		assertTrue(rule.isOf(AccessRule.ANYROLE, roleB));
		assertTrue(rule.isOf(AccessRule.ANYROLE, roleC));
		assertTrue(rule.isOf(AccessRule.ANYROLE));

		assertFalse(rule.isOf(AccessRule.NOROLE, roleA));
		assertFalse(rule.isOf(AccessRule.NOROLE, roleB));
		assertFalse(rule.isOf(AccessRule.NOROLE, roleC));
		assertFalse(rule.isOf(AccessRule.NOROLE));

		assertTrue(rule.isOf(roleA.getMask(), roleA));
		assertFalse(rule.isOf(roleB.getMask(), roleA));
		assertFalse(rule.isOf(roleB.getMask(), roleA));

		assertTrue(rule.isOf(roleA.getMask() | roleB.getMask(), roleA));
		assertTrue(rule.isOf(roleA.getMask() | roleB.getMask(), roleB));
		assertFalse(rule.isOf(roleA.getMask() | roleB.getMask(), roleC));
	}

	public void testIsOwner() throws Exception
	{
		BaseAccessRule rule = new BaseAccessRule();

		Object owner = new Object();
		Object other = new Object();

		Target t = new Target(owner);

		assertTrue(rule.isOwner(t, new Object[] { owner }));
		assertTrue(rule.isOwner(t, new Object[] { owner, other }));
		assertFalse(rule.isOwner(t, new Object[] { other }));
		assertFalse(rule.isOwner(t, new Object[] {}));
		assertFalse(rule.isOwner(t));
		
		// test recursive
		
		Target t2 = new Target(t);

		assertTrue(rule.isOwner(t2, new Object[] { owner }));
		assertTrue(rule.isOwner(t2, new Object[] { owner, other }));
		assertFalse(rule.isOwner(t2, new Object[] { other }));
		assertFalse(rule.isOwner(t2, new Object[] {}));
		assertFalse(rule.isOwner(t2));
	}

	public void testIsFriend() throws Exception
	{
		BaseAccessRule rule = new BaseAccessRule();

		assertFalse(rule.isFriend(null));
	}

	public void testIsEnemy() throws Exception
	{
		BaseAccessRule rule = new BaseAccessRule();

		assertFalse(rule.isEnemy(null));
	}

	public void testIsAlly() throws Exception
	{
		BaseAccessRule rule = new BaseAccessRule();

		assertFalse(rule.isAlly(null));
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

	public static class Role implements BitMask
	{
		private int	mask;

		public Role(int mask)
		{
			super();
			this.mask = mask;
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.data.model.base.BitMask#getMask()
		 */
		@Override
		public int getMask()
		{
			return mask;
		}

	}
}
