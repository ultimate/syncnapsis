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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestCoversClasses({ BaseSerializer.class, JacksonStringSerializer.class })
@TestExcludesMethods({ "get*", "set*", "afterPropertiesSet" })
public class JacksonStringSerializerTest extends LoggerTestCase
{
	public void testSerialize() throws Exception
	{
		JacksonStringSerializer ser = new JacksonStringSerializer();

		POJO p1, p2;
		String s1, s2, sExpected;

		// serialize

		p1 = new POJO(1, "one", Arrays.asList(new Object[] { 1.0 }));
		s1 = ser.serialize(p1, (Object[]) null);
		logger.debug(p1 + " vs. " + s1);
		assertEquals(p1.toString(), s1);

		s2 = ser.serialize(p1, new Object[] { "id" });
		sExpected = "{\"id\":1}";
		logger.debug(sExpected + " vs. " + s2);
		assertEquals(sExpected, s2);

		s2 = ser.serialize(p1, new Object[] { "name" });
		sExpected = "{\"name\":\"one\"}";
		logger.debug(sExpected + " vs. " + s2);
		assertEquals(sExpected, s2);

		s2 = ser.serialize(p1, new Object[] { "value" });
		sExpected = "{\"value\":[1.0]}";
		logger.debug(sExpected + " vs. " + s2);
		assertEquals(sExpected, s2);

		s2 = ser.serialize(p1, new Object[] { "id", "name", "value" });
		sExpected = p1.toString();
		logger.debug(sExpected + " vs. " + s2);
		assertEquals(sExpected, s2);

		// serialize "trees"

		p2 = new POJO(2, "zwei");

		POJOParent pp1 = new POJOParent();
		pp1.setChildren(Arrays.asList(new POJO[] { p1 }));

		POJOParent pp2 = new POJOParent();
		pp2.setChildren(Arrays.asList(new POJO[] { p2 }));

		pp1.setSibling(pp2);

		s2 = ser.serialize(pp1, (Object[]) null);
		sExpected = pp1.toString();
		logger.debug(sExpected + " vs. " + s2);
		assertEquals(sExpected, s2);

		// serialize arrays

		Object[] arr1;

		arr1 = new Object[] { 1, "2", null };

		s1 = ser.serialize(arr1, (Object[]) null);
		sExpected = "[1,\"2\",null]";
		logger.debug(sExpected + " vs. " + s1);
		assertEquals(sExpected, s1);

		arr1 = new Object[] { new POJO(1, "one"), new POJO(2, "two") };

		s1 = ser.serialize(arr1, (Object[]) null);
		sExpected = "[" + arr1[0] + "," + arr1[1] + "]";
		logger.debug(sExpected + " vs. " + s1);
		assertEquals(sExpected, s1);
	}

	public void testDeserialize() throws Exception
	{
		JacksonStringSerializer ser = new JacksonStringSerializer();

		POJO p1, p2, pExpected;
		String s1;

		p1 = new POJO(1, "one", Arrays.asList(new Object[] { 1.0 }));
		s1 = ser.serialize(p1, (Object[]) null);

		// deserialize

		p2 = ser.deserialize(s1, POJO.class, (Object[]) null);
		logger.debug(p1 + " vs. " + p2);
		assertEquals(p1, p2);

		p2 = ser.deserialize(s1, POJO.class, new Object[] { "id" });
		pExpected = new POJO(p1.id, null);
		logger.debug(pExpected + " vs. " + p2);
		assertEquals(pExpected, p2);

		p2 = ser.deserialize(s1, POJO.class, new Object[] { "name" });
		pExpected = new POJO(0, p1.name);
		logger.debug(pExpected + " vs. " + p2);
		assertEquals(pExpected, p2);

		p2 = ser.deserialize(s1, POJO.class, new Object[] { "id", "name" });
		pExpected = new POJO(p1.id, p1.name);
		logger.debug(pExpected + " vs. " + p2);
		assertEquals(pExpected, p2);

		// deserialize "trees"

		p2 = new POJO(2, "zwei");

		POJOParent pp1 = new POJOParent();
		pp1.setChildren(Arrays.asList(new POJO[] { p1 }));

		POJOParent pp2 = new POJOParent();
		pp2.setChildren(Arrays.asList(new POJO[] { p2 }));

		pp1.setSibling(pp2);

		s1 = ser.serialize(pp1, (Object[]) null);

		POJOParent pp3 = ser.deserialize(s1, POJOParent.class, (Object[]) null);
		logger.debug(pp1 + " vs. " + pp3);
		assertEquals(pp1, pp3);

		// deserialize Arrays

		Object[] arr1, arr1a, arr2;

		arr1 = new Object[] { 1, "2", null };

		s1 = ser.serialize(arr1, (Object[]) null);

		arr2 = ser.deserialize(s1, Object[].class, (Object[]) null);
		logger.debug(Arrays.asList(arr1) + " vs. " + Arrays.asList(arr2));
		assertEquals(arr1, arr2);

		arr1 = new POJO[] { new POJO(1, "one"), new POJO(2, "two") };

		s1 = ser.serialize(arr1, (Object[]) null);

		arr2 = ser.deserialize(s1, POJO[].class, (Object[]) null);
		logger.debug(Arrays.asList(arr1) + " vs. " + Arrays.asList(arr2));
		assertEquals(arr1, arr2);

		arr1a = new Object[] { ((POJO) arr1[0]).toMap(), ((POJO) arr1[1]).toMap() };

		arr2 = ser.deserialize(s1, Object[].class, (Object[]) null);
		logger.debug(Arrays.asList(arr1a) + " vs. " + Arrays.asList(arr2));
		assertEquals(arr1a, arr2);
	}

	/*
	 * public void testSerialize_withCycle() throws Exception
	 * {
	 * JacksonStringSerializer ser = new JacksonStringSerializer();
	 * POJOParent pp1, pp2, pExpected;
	 * String s1;
	 * pp1 = new POJOParent();
	 * pp2 = new POJOParent();
	 * pp1.sibling = pp2;
	 * pp2.sibling = pp1;
	 * s1 = ser.serialize(pp1);
	 * logger.debug(s1);
	 * Map<String, Object> m1 = new HashMap<String, Object>();
	 * Map<String, Object> m2 = new HashMap<String, Object>();
	 * m1.put("sibling", m2);
	 * m2.put("sibling", m1);
	 * s1 = ser.serialize(m1);
	 * logger.debug(s1);
	 * }
	 */

	public static class POJO implements Mapable<POJO>
	{
		private int				id;
		private String			name;
		private List<Object>	value;

		public POJO()
		{
			this(0, null, null);
		}

		public POJO(int id, String name)
		{
			this(id, name, null);
		}

		public POJO(int id, String name, List<Object> value)
		{
			super();
			this.id = id;
			this.name = name;
			this.value = value;
		}

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public List<Object> getValue()
		{
			return value;
		}

		public void setValue(List<Object> value)
		{
			this.value = value;
		}

		@Override
		public String toString()
		{
			String valS;
			if(value == null)
				valS = "null";
			else
			{
				valS = "[";
				for(int i = 0; i < value.size(); i++)
				{
					if(i > 0)
						valS = valS + ",";
					valS = valS + value.get(i);
				}
				valS = valS + "]";
			}
			return "{\"id\":" + id + ",\"name\":\"" + name + "\",\"value\":" + valS + "}";
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			POJO other = (POJO) obj;
			if(id != other.id)
				return false;
			if(name == null)
			{
				if(other.name != null)
					return false;
			}
			else if(!name.equals(other.name))
				return false;
			if(value == null)
			{
				if(other.value != null)
					return false;
			}
			else if(!value.equals(other.value))
				return false;
			return true;
		}

		@Override
		public Map<String, ?> toMap(Object... authorities)
		{
			List<Object> vList = new ArrayList<Object>();
			if(authorities != null)
				vList = Arrays.asList(authorities);
			Map<String, Object> m = new HashMap<String, Object>();
			if(authorities == null || authorities.length == 0 || vList.contains("id"))
				m.put("id", id);
			if(authorities == null || authorities.length == 0 || vList.contains("name"))
				m.put("name", name);
			if(authorities == null || authorities.length == 0 || vList.contains("value"))
				m.put("value", value);
			return m;
		}

		@SuppressWarnings("unchecked")
		@Override
		public POJO fromMap(Map<String, ?> map, Object... authorities)
		{
			List<Object> vList = new ArrayList<Object>();
			if(authorities != null)
				vList = Arrays.asList(authorities);
			if(authorities == null || authorities.length == 0 || vList.contains("id"))
				if(map.containsKey("id"))
					this.id = (Integer) map.get("id");
			if(authorities == null || authorities.length == 0 || vList.contains("name"))
				if(map.containsKey("name"))
					this.name = (String) map.get("name");
			if(authorities == null || authorities.length == 0 || vList.contains("value"))
				if(map.containsKey("value"))
					this.value = (List<Object>) map.get("value");
			return this;
		}
	}

	public static class POJOParent implements Mapable<POJOParent>
	{
		private POJOParent	sibling;
		private List<POJO>	children;

		public POJOParent getSibling()
		{
			return sibling;
		}

		public void setSibling(POJOParent sibling)
		{
			this.sibling = sibling;
		}

		public List<POJO> getChildren()
		{
			return children;
		}

		public void setChildren(List<POJO> children)
		{
			this.children = children;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((children == null) ? 0 : children.hashCode());
			result = prime * result + ((sibling == null) ? 0 : sibling.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			POJOParent other = (POJOParent) obj;
			if(children == null)
			{
				if(other.children != null)
					return false;
			}
			else if(!children.equals(other.children))
				return false;
			if(sibling == null)
			{
				if(other.sibling != null)
					return false;
			}
			else if(!sibling.equals(other.sibling))
				return false;
			return true;
		}

		@Override
		public String toString()
		{
			String childS;
			if(children == null)
				childS = "null";
			else
			{
				childS = "[";
				for(int i = 0; i < children.size(); i++)
				{
					if(i > 0)
						childS = childS + ",";
					childS = childS + children.get(i);
				}
				childS = childS + "]";
			}
			return "{\"sibling\":" + sibling + ",\"children\":" + childS + "}";
		}

		@Override
		public Map<String, ?> toMap(Object... authorities)
		{
			List<Object> vList = new ArrayList<Object>();
			if(authorities != null)
				vList = Arrays.asList(authorities);
			Map<String, Object> m = new HashMap<String, Object>();
			if(authorities == null || authorities.length == 0 || vList.contains("sibling"))
				m.put("sibling", sibling);
			if(authorities == null || authorities.length == 0 || vList.contains("children"))
			{
				List<Map<String, ?>> childList = new ArrayList<Map<String, ?>>();
				for(POJO p : children)
					childList.add(p.toMap(authorities));
				m.put("children", childList);
			}
			return m;
		}

		@SuppressWarnings("unchecked")
		@Override
		public POJOParent fromMap(Map<String, ?> map, Object... authorities)
		{
			List<Object> vList = new ArrayList<Object>();
			if(authorities != null)
				vList = Arrays.asList(authorities);
			if(authorities == null || authorities.length == 0 || vList.contains("sibling"))
				if(map.containsKey("sibling"))
					if(map.get("sibling") != null)
						this.sibling = new POJOParent().fromMap((Map<String, ?>) map.get("sibling"), authorities);
					else
						this.sibling = null;
			if(authorities == null || authorities.length == 0 || vList.contains("children"))
				if(map.containsKey("children"))
				{
					this.children = new ArrayList<POJO>();
					for(Map<String, ?> m : (List<Map<String, ?>>) map.get("children"))
						children.add(new POJO().fromMap(m, authorities));
				}
			return this;
		}

	}
}
