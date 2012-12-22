/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.utils.serialization;

import java.util.Map;
import java.util.Map.Entry;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

/**
 * @author ultimate
 */
public class LimitedReferenceDepthMapperTest extends LoggerTestCase
{
	@SuppressWarnings("unchecked")
	@TestCoversMethods("*")
	public void testPrepare()
	{
		LimitedReferenceDepthMapper mapper = new LimitedReferenceDepthMapper();

		POJO p = new POJO(0);
		p.ref1 = new POJO(1);
		p.ref1.ref1 = new POJO(11);
		p.ref1.ref1.ref1 = new POJO(111);
		p.ref1.ref2 = new POJO(12);
		p.ref1.ref2.ref2 = new POJO(122);
		p.ref2 = new POJO(2);
		p.ref2.ref1 = new POJO(21);
		p.ref2.ref1.ref2 = new POJO(212);
		p.ref2.ref1.ref2.ref1 = new POJO(2121);
		
		Map<String, Object> m;
		
		mapper.setDepthLimit(0);
		m = (Map<String, Object>) mapper.prepare(p, (Object[]) null);
		System.out.println(m);
		assertTrue(containsObjectId(m, 0));
		assertTrue(containsObjectFull(m, 0));
		assertFalse(containsObjectId(m, 1));
		assertFalse(containsObjectFull(m, 1));
		assertFalse(containsObjectId(m, 2));
		assertFalse(containsObjectFull(m, 2));

		mapper.setDepthLimit(1);
		m = (Map<String, Object>) mapper.prepare(p, (Object[]) null);
		System.out.println(m);
		assertTrue(containsObjectId(m, 0));
		assertTrue(containsObjectFull(m, 0));
		assertTrue(containsObjectId(m, 1));
		assertTrue(containsObjectFull(m, 1));
		assertTrue(containsObjectId(m, 2));
		assertTrue(containsObjectFull(m, 2));
		assertFalse(containsObjectId(m, 11));
		assertFalse(containsObjectFull(m, 11));
		assertFalse(containsObjectId(m, 12));
		assertFalse(containsObjectFull(m, 12));
		assertFalse(containsObjectId(m, 21));
		assertFalse(containsObjectFull(m, 21));

		mapper.setDepthLimit(2);
		m = (Map<String, Object>) mapper.prepare(p, (Object[]) null);
		System.out.println(m);
		assertTrue(containsObjectId(m, 0));
		assertTrue(containsObjectFull(m, 0));
		assertTrue(containsObjectId(m, 1));
		assertTrue(containsObjectFull(m, 1));
		assertTrue(containsObjectId(m, 2));
		assertTrue(containsObjectFull(m, 2));
		assertTrue(containsObjectId(m, 11));
		assertTrue(containsObjectFull(m, 11));
		assertTrue(containsObjectId(m, 12));
		assertTrue(containsObjectFull(m, 12));
		assertTrue(containsObjectId(m, 21));
		assertTrue(containsObjectFull(m, 21));
		assertFalse(containsObjectId(m, 111));
		assertFalse(containsObjectFull(m, 111));
		assertFalse(containsObjectId(m, 122));
		assertFalse(containsObjectFull(m, 122));
		assertFalse(containsObjectId(m, 212));
		assertFalse(containsObjectFull(m, 212));

		mapper.setDepthLimit(3);
		m = (Map<String, Object>) mapper.prepare(p, (Object[]) null);
		System.out.println(m);
		assertTrue(containsObjectId(m, 0));
		assertTrue(containsObjectFull(m, 0));
		assertTrue(containsObjectId(m, 1));
		assertTrue(containsObjectFull(m, 1));
		assertTrue(containsObjectId(m, 2));
		assertTrue(containsObjectFull(m, 2));
		assertTrue(containsObjectId(m, 11));
		assertTrue(containsObjectFull(m, 11));
		assertTrue(containsObjectId(m, 12));
		assertTrue(containsObjectFull(m, 12));
		assertTrue(containsObjectId(m, 21));
		assertTrue(containsObjectFull(m, 21));
		assertTrue(containsObjectId(m, 111));
//		assertTrue(containsObjectFull(m, 111)); // object has no other fields than id
		assertTrue(containsObjectId(m, 122));
//		assertTrue(containsObjectFull(m, 122)); // object has no other fields than id
		assertTrue(containsObjectId(m, 212));
		assertTrue(containsObjectFull(m, 212));
		assertFalse(containsObjectId(m, 2121));
		assertFalse(containsObjectFull(m, 2121));

		mapper.setDepthLimit(4);
		m = (Map<String, Object>) mapper.prepare(p, (Object[]) null);
		System.out.println(m);
		assertTrue(containsObjectId(m, 0));
		assertTrue(containsObjectFull(m, 0));
		assertTrue(containsObjectId(m, 1));
		assertTrue(containsObjectFull(m, 1));
		assertTrue(containsObjectId(m, 2));
		assertTrue(containsObjectFull(m, 2));
		assertTrue(containsObjectId(m, 11));
		assertTrue(containsObjectFull(m, 11));
		assertTrue(containsObjectId(m, 12));
		assertTrue(containsObjectFull(m, 12));
		assertTrue(containsObjectId(m, 21));
		assertTrue(containsObjectFull(m, 21));
		assertTrue(containsObjectId(m, 111));
//		assertTrue(containsObjectFull(m, 111)); // object has no other fields than id
		assertTrue(containsObjectId(m, 122));
//		assertTrue(containsObjectFull(m, 122)); // object has no other fields than id
		assertTrue(containsObjectId(m, 212));
		assertTrue(containsObjectFull(m, 212));
		assertTrue(containsObjectId(m, 2121));
//		assertTrue(containsObjectFull(m, 2121)); // object has no other fields than id

		mapper.setDepthLimit(5);
		m = (Map<String, Object>) mapper.prepare(p, (Object[]) null);
		System.out.println(m);
		assertTrue(containsObjectId(m, 0));
		assertTrue(containsObjectFull(m, 0));
		assertTrue(containsObjectId(m, 1));
		assertTrue(containsObjectFull(m, 1));
		assertTrue(containsObjectId(m, 2));
		assertTrue(containsObjectFull(m, 2));
		assertTrue(containsObjectId(m, 11));
		assertTrue(containsObjectFull(m, 11));
		assertTrue(containsObjectId(m, 12));
		assertTrue(containsObjectFull(m, 12));
		assertTrue(containsObjectId(m, 21));
		assertTrue(containsObjectFull(m, 21));
		assertTrue(containsObjectId(m, 111));
//		assertTrue(containsObjectFull(m, 111)); // object has no other fields than id
		assertTrue(containsObjectId(m, 122));
//		assertTrue(containsObjectFull(m, 122)); // object has no other fields than id
		assertTrue(containsObjectId(m, 212));
		assertTrue(containsObjectFull(m, 212));
		assertTrue(containsObjectId(m, 2121));
//		assertTrue(containsObjectFull(m, 2121)); // object has no other fields than id
}
	
	@SuppressWarnings("unchecked")
	private boolean containsObjectId(Map<String, Object> m, int id)
	{
		boolean foundInChild = false;
		for(Entry<String, Object> e: m.entrySet())
		{
			if(e.getKey().equals("id") && ((Integer) e.getValue() == id))
				return true;
			else if(e.getValue() instanceof Map)
			{
				foundInChild = containsObjectId((Map<String, Object>) e.getValue(), id);
				if(foundInChild)
					return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private boolean containsObjectFull(Map<String, Object> m, int id)
	{
		boolean idFound = false;
		boolean refFound = false;
		boolean foundInChild = false;
		for(Entry<String, Object> e: m.entrySet())
		{
			if(e.getKey().equals("id") && ((Integer) e.getValue() == id))
				idFound = true;
			else if(e.getValue() instanceof Map)
			{
				foundInChild = containsObjectFull((Map<String, Object>) e.getValue(), id);
				if(foundInChild)
					return true;
				refFound = true;
			}
		}
		return idFound && refFound;
	}

	public static class POJO
	{
		private int		id;
		private POJO	ref1;
		private POJO	ref2;

		public POJO(int id)
		{
			this.id = id;
		}

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		public POJO getRef1()
		{
			return ref1;
		}

		public POJO getRef2()
		{
			return ref2;
		}

		public void setRef1(POJO ref1)
		{
			this.ref1 = ref1;
		}

		public void setRef2(POJO ref2)
		{
			this.ref2 = ref2;
		}
	}
}
