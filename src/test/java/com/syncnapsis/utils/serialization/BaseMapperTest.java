package com.syncnapsis.utils.serialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.syncnapsis.security.SecurityManager;
import com.syncnapsis.security.accesscontrol.FieldAccessController;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.security.annotations.Authority;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.ReflectionsUtil;

@TestCoversClasses({ Mapper.class, BaseMapper.class })
@TestExcludesMethods({ "get*", "set*", "afterPropertiesSet" })
public class BaseMapperTest extends LoggerTestCase
{
	private final BaseMapper	mapper	= new BaseMapper();

	public void testMerge() throws Exception
	{
		assertEquals(1, (int) mapper.merge(int.class, 0, 1, (Object[]) null));
		assertEquals(1, (int) mapper.merge(Integer.class, 0, 1, (Object[]) null));

		assertEquals(1, (int) mapper.merge(int.class, new Integer(0), 1, (Object[]) null));
		assertEquals(1, (int) mapper.merge(Integer.class, new Integer(0), 1, (Object[]) null));

		assertEquals(1, (int) mapper.merge(int.class, new Integer(0), new Integer(1), (Object[]) null));
		assertEquals(1, (int) mapper.merge(Integer.class, new Integer(0), new Integer(1), (Object[]) null));

		assertEquals(1, (int) mapper.merge(int.class, (Integer) null, 1, (Object[]) null));
		assertEquals(1, (int) mapper.merge(Integer.class, (Integer) null, 1, (Object[]) null));

		assertEquals(1, (int) mapper.merge(int.class, 1, (Object[]) null));
		assertEquals(1, (int) mapper.merge(Integer.class, 1, (Object[]) null));

		assertEquals(1, (int) mapper.merge((Integer) null, 1, (Object[]) null));
	}

	public void testMergeToArray() throws Exception
	{
		POJO1 p1 = new POJO1(1, "one");
		POJO1 p2 = new POJO1(2, "two");
		POJO1 p3 = new POJO1(3, "three");

		Map<String, Object> m1 = mapper.toMap(p1);
		Map<String, Object> m2 = mapper.toMap(p2);
		Map<String, Object> m3 = mapper.toMap(p3);

		Object[] array = new Object[] { m1, m2, m3 };
		List<Object> list = Arrays.asList(array);

		POJO1[] expected = new POJO1[] { p1, p2, p3 };
		POJO1[] result;

		result = mapper.mergeToArray(array, POJO1.class);
		assertEquals(expected, result);
		assertEquals(POJO1.class, result.getClass().getComponentType());

		result = mapper.mergeToArray(list, POJO1.class);
		assertEquals(expected, result);
		assertEquals(POJO1.class, result.getClass().getComponentType());
	}

	@SuppressWarnings("unchecked")
	public void testMergeToCollection() throws Exception
	{
		POJO1 p1 = new POJO1(1, "one");
		POJO1 p2 = new POJO1(2, "two");
		POJO1 p3 = new POJO1(3, "three");

		Map<String, Object> m1 = mapper.toMap(p1);
		Map<String, Object> m2 = mapper.toMap(p2);
		Map<String, Object> m3 = mapper.toMap(p3);

		Object[] array = new Object[] { m1, m2, m3 };
		List<Object> list = Arrays.asList(array);

		POJO1[] expectedArray = new POJO1[] { p1, p2, p3 };
		List<POJO1> expected = Arrays.asList(expectedArray);
		List<POJO1> result;

		result = (List<POJO1>) mapper.mergeToCollection(array, POJO1.class, (Class<? extends Collection<?>>) List.class);
		assertEquals(expected, result);

		result = (List<POJO1>) mapper.mergeToCollection(list, POJO1.class);
		assertEquals(expected, result);
	}

	@TestCoversMethods({ "toMap", "prepare" })
	public void testToMap_Mapable() throws Exception
	{
		// to map
		POJO2 p1 = new POJO2(1, "one", Arrays.asList(new Object[] { 1.0 }));

		checkMap(mapper.toMap(p1, (Object[]) null), "id", p1.getId(), "name", p1.getName(), "value", p1.getValue());
		checkMap(mapper.toMap(p1, new Object[] { "id" }), "id", p1.getId());
		checkMap(mapper.toMap(p1, new Object[] { "name" }), "name", p1.getName());
		checkMap(mapper.toMap(p1, new Object[] { "value" }), "value", p1.getValue());
		checkMap(mapper.toMap(p1, new Object[] { "id", "name", "value" }), "id", p1.getId(), "name", p1.getName(), "value", p1.getValue());

		// to map for "trees"
		POJO2 p2 = new POJO2(2, "two");
		POJO2Parent pp1 = new POJO2Parent();
		pp1.setChildren(Arrays.asList(new POJO2[] { p1 }));
		POJO2Parent pp2 = new POJO2Parent();
		pp2.setChildren(Arrays.asList(new POJO2[] { p2 }));
		pp1.setSibling(pp2);

		Map<String, Object> mp1 = mapper.toMap(pp1, (Object[]) null);
		Map<String, Object> mp2 = mapper.toMap(pp2, (Object[]) null);
		Map<String, Object> m1 = mapper.toMap(p1, (Object[]) null);
		Map<String, Object> m2 = mapper.toMap(p2, (Object[]) null);

		List<Object> c1 = Arrays.asList(new Object[] { m1 });
		List<Object> c2 = Arrays.asList(new Object[] { m2 });

		checkMap(m1, "id", p1.getId(), "name", p1.getName(), "value", p1.getValue());
		checkMap(m2, "id", p2.getId(), "name", p2.getName(), "value", p2.getValue());
		checkMap(mp2, "sibling", null, "children", c2);
		checkMap(mp1, "sibling", mp2, "children", c1);
	}

	@TestCoversMethods("fromMap")
	public void testFromMap_Mapable() throws Exception
	{
		// from map
		POJO2 p1 = new POJO2(1, "one", Arrays.asList(new Object[] { 1.0 }));
		Map<String, Object> m1 = mapper.toMap(p1, (Object[]) null);

		POJO2 expected;

		expected = new POJO2(p1.getId(), p1.getName(), p1.getValue());
		assertEquals(expected, mapper.fromMap(new POJO2(), m1, (Object[]) null));
		expected = new POJO2(p1.getId(), null, null);
		assertEquals(expected, mapper.fromMap(new POJO2(), m1, new Object[] { "id" }));
		expected = new POJO2(0, p1.getName(), null);
		assertEquals(expected, mapper.fromMap(new POJO2(), m1, new Object[] { "name" }));
		expected = new POJO2(0, null, p1.getValue());
		assertEquals(expected, mapper.fromMap(new POJO2(), m1, new Object[] { "value" }));
		expected = new POJO2(p1.getId(), p1.getName(), p1.getValue());
		assertEquals(expected, mapper.fromMap(new POJO2(), m1, new Object[] { "id", "name", "value" }));

		// from map for "trees"
		POJO2 p2 = new POJO2(2, "two");
		POJO2Parent pp1 = new POJO2Parent();
		pp1.setChildren(Arrays.asList(new POJO2[] { p1 }));
		POJO2Parent pp2 = new POJO2Parent();
		pp2.setChildren(Arrays.asList(new POJO2[] { p2 }));
		pp1.setSibling(pp2);

		Map<String, Object> mp1 = mapper.toMap(pp1, (Object[]) null);

		assertEquals(pp1, mapper.fromMap(new POJO2Parent(), mp1, (Object[]) null));
	}

	@TestCoversMethods({ "toMap", "prepare" })
	public void testToMap_Map() throws Exception
	{
		Map<Object, Object> m = new HashMap<Object, Object>();
		m.put("x", 1);
		m.put("y", "one");

		checkMap(mapper.toMap(m, "any"), "x", m.get("x"), "y", m.get("y"));
		checkMap(mapper.toMap(m, "random"), "x", m.get("x"), "y", m.get("y"));
		checkMap(mapper.toMap(m, "role"), "x", m.get("x"), "y", m.get("y"));
	}

	@TestCoversMethods("fromMap")
	public void testFromMap_Map() throws Exception
	{
		Map<Object, Object> m = new HashMap<Object, Object>();
		m.put("x", 1);
		m.put("y", "one");

		assertEquals(m, mapper.fromMap(new HashMap<Object, Object>(), mapper.toMap(m, "any"), (Object[]) null));
		assertEquals(m, mapper.fromMap(new HashMap<Object, Object>(), mapper.toMap(m, "random"), (Object[]) null));
		assertEquals(m, mapper.fromMap(new HashMap<Object, Object>(), mapper.toMap(m, "role"), (Object[]) null));
	}

	@TestCoversMethods({ "toMap", "prepare" })
	public void testToMap_POJO() throws Exception
	{
		SecurityManager securityManager = new SecurityManager();
		securityManager.addAccessController(new FieldAccessController());
		mapper.setSecurityManager(securityManager);

		POJO1 p = new POJO1(1, "one");

		// roles with permission
		checkMap(mapper.toMap(p, "v1"), "x", p.getX(), "y", p.getY());
		checkMap(mapper.toMap(p, 2), "x", p.getX(), "y", p.getY());
		checkMap(mapper.toMap(p, "v1", 2), "x", p.getX(), "y", p.getY());

		// roles with permission mixed with others roles without
		checkMap(mapper.toMap(p, "v1", 1), "x", p.getX(), "y", p.getY());
		checkMap(mapper.toMap(p, 2, "v2"), "x", p.getX(), "y", p.getY());

		// roles without permission
		checkMap(mapper.toMap(p, 1), "x", p.getX());
		checkMap(mapper.toMap(p, "v2"), "x", p.getX());
		checkMap(mapper.toMap(p, 1, "v2"), "x", p.getX());

		// no role
		checkMap(mapper.toMap(p, (Object[]) null), "x", p.getX());
		checkMap(mapper.toMap(p, new Object[] {}), "x", p.getX());

		mapper.setSecurityManager(null);
	}

	@TestCoversMethods("fromMap")
	public void testFromMap_POJO() throws Exception
	{
		SecurityManager securityManager = new SecurityManager();
		securityManager.addAccessController(new FieldAccessController());
		mapper.setSecurityManager(securityManager);

		POJO1 p = new POJO1(1, "one");
		Map<String, Object> m = mapper.toMap(p, "v1");
		assertTrue(m.containsKey("x"));
		assertTrue(m.containsKey("y"));

		POJO1 expected;

		// roles with permission
		expected = new POJO1(p.getX(), p.getY());
		assertEquals(expected, mapper.fromMap(new POJO1(), m, "v1"));
		assertEquals(expected, mapper.fromMap(new POJO1(), m, 2));
		assertEquals(expected, mapper.fromMap(new POJO1(), m, "v1", 2));

		// roles with permission mixed with others roles without
		// same expected
		assertEquals(expected, mapper.fromMap(new POJO1(), m, "v1", 1));
		assertEquals(expected, mapper.fromMap(new POJO1(), m, 2, "v2"));

		// roles without permission
		expected = new POJO1(p.getX(), null);
		assertEquals(expected, mapper.fromMap(new POJO1(), m, 1));
		assertEquals(expected, mapper.fromMap(new POJO1(), m, "v2"));
		assertEquals(expected, mapper.fromMap(new POJO1(), m, 1, "v2"));

		// no role
		// same expected
		assertEquals(expected, mapper.fromMap(new POJO1(), m, (Object[]) null));
		assertEquals(expected, mapper.fromMap(new POJO1(), m, new Object[] {}));

		mapper.setSecurityManager(null);
	}

	@TestCoversMethods({ "toMap", "prepare" })
	public void testToMap_POJO3() throws Exception
	{
		// to map
		POJO3 p1 = new POJO3(1, "one", Arrays.asList(new Double[] { 1.0 }));

		checkMap(mapper.toMap(p1, (Object[]) null), "id", p1.getId(), "name", p1.getName(), "value", p1.getValue());

		// to map for "trees"
		POJO3 p2 = new POJO3(2, "two");
		POJO3Parent pp1 = new POJO3Parent();
		pp1.setChildren(Arrays.asList(new POJO3[] { p1 }));
		POJO3Parent pp2 = new POJO3Parent();
		pp2.setChildren(Arrays.asList(new POJO3[] { p2 }));
		pp1.setSibling(pp2);

		Map<String, Object> mp1 = mapper.toMap(pp1, (Object[]) null);
		Map<String, Object> mp2 = mapper.toMap(pp2, (Object[]) null);
		Map<String, Object> m1 = mapper.toMap(p1, (Object[]) null);
		Map<String, Object> m2 = mapper.toMap(p2, (Object[]) null);

		List<Object> c1 = Arrays.asList(new Object[] { m1 });
		List<Object> c2 = Arrays.asList(new Object[] { m2 });

		checkMap(m1, "id", p1.getId(), "name", p1.getName(), "value", p1.getValue());
		checkMap(m2, "id", p2.getId(), "name", p2.getName(), "value", p2.getValue());
		checkMap(mp2, "sibling", null, "children", c2);
		checkMap(mp1, "sibling", mp2, "children", c1);
	}

	@TestCoversMethods("fromMap")
	public void testFromMap_POJO3() throws Exception
	{
		// from map
		POJO3 p1 = new POJO3(1, "one", Arrays.asList(new Double[] { 1.0 }));
		Map<String, Object> m1 = mapper.toMap(p1, (Object[]) null);

		POJO3 expected;

		expected = new POJO3(p1.getId(), p1.getName(), p1.getValue());
		System.out.println("----");
		assertEquals(expected, mapper.fromMap(new POJO3(), m1, (Object[]) null));

		// from map for "trees"
		POJO3 p2 = new POJO3(2, "two");
		POJO3Parent pp1 = new POJO3Parent();
		pp1.setChildren(Arrays.asList(new POJO3[] { p1 }));
		POJO3Parent pp2 = new POJO3Parent();
		pp2.setChildren(Arrays.asList(new POJO3[] { p2 }));
		pp1.setSibling(pp2);

		Map<String, Object> mp1 = mapper.toMap(pp1, (Object[]) null);

		assertEquals(pp1, mapper.fromMap(new POJO3Parent(), mp1, (Object[]) null));
	}

	private void checkMap(Map<String, Object> map, Object... entries)
	{
		assertTrue(entries.length % 2 == 0);
		assertEquals(entries.length / 2, map.size());

		for(int i = 0; i < entries.length; i = i + 2)
		{
			assertTrue(map.containsKey(entries[i]));
			assertEquals(entries[i + 1], map.get(entries[i]));
		}
	}

	@TestCoversMethods({ "isReadable", "isWritable" })
	public void testIsAccessible() throws Exception
	{
		SecurityManager securityManager = new SecurityManager();
		FieldAccessController controller = new FieldAccessController();
		securityManager.addAccessController(controller);
		mapper.setSecurityManager(securityManager);

		List<com.syncnapsis.utils.reflections.Field> fields = ReflectionsUtil.findFields(POJO1.class);
		assertEquals(2, fields.size());

		com.syncnapsis.utils.reflections.Field xField = fields.get(0);
		assertEquals("x", xField.getName());
		com.syncnapsis.utils.reflections.Field yField = fields.get(1);
		assertEquals("y", yField.getName());

		// READABLE
		// @formatter:off
		Object[][] authorities = new Object[][] { 
				new Object[] { "v1" },
				new Object[] { 2 },
				new Object[] { "v1", 2 },
				new Object[] { "v1", 1 },
				new Object[] { 2, "v2" },
				new Object[] { 1 },
				new Object[] { "v2" },
				new Object[] { 1, "v2" },
				null,
				new Object[] { },
		};
		// @formatter:on

		for(int i = 0; i < authorities.length; i++)
		{
			assertEquals(controller.isReadable(xField, authorities[i]), mapper.isReadable(xField, authorities[i]));			
			assertEquals(controller.isWritable(xField, authorities[i]), mapper.isWritable(xField, authorities[i]));			
			assertEquals(controller.isReadable(yField, authorities[i]), mapper.isReadable(yField, authorities[i]));			
			assertEquals(controller.isWritable(yField, authorities[i]), mapper.isWritable(yField, authorities[i]));			
		}
	}

	@SuppressWarnings("unchecked")
	public void testCreateCollection() throws Exception
	{
		Collection<POJO1> collection;

		collection = mapper.createCollection(POJO1.class, (Class<? extends Collection<?>>) List.class, 5);
		assertEquals(ArrayList.class, collection.getClass());

		collection = mapper.createCollection(POJO1.class, (Class<? extends Collection<?>>) Set.class, 5);
		assertEquals(HashSet.class, collection.getClass());

		collection = mapper.createCollection(POJO1.class, (Class<? extends Collection<?>>) Queue.class, 5);
		assertEquals(PriorityQueue.class, collection.getClass());
	}

	public void testCreateArray() throws Exception
	{
		POJO1[] array = mapper.createArray(POJO1.class, 5);
		assertEquals(POJO1[].class, array.getClass());
		assertEquals(5, array.length);
	}

	public static class POJO1
	{
		private int		x;

		private String	y;

		public POJO1()
		{

		}

		public POJO1(int x, String y)
		{
			super();
			this.x = x;
			this.y = y;
		}

		@Accessible(accessible = { @Authority(name = "v1"), @Authority(id = 2L) })
		public int getX()
		{
			return x;
		}

		@Accessible(accessible = { @Authority(name = "v1"), @Authority(id = 2L) }, defaultAccessible = false)
		public String getY()
		{
			return y;
		}

		@Accessible(accessible = { @Authority(name = "v1"), @Authority(id = 2L) })
		public void setX(int x)
		{
			this.x = x;
		}

		@Accessible(accessible = { @Authority(name = "v1"), @Authority(id = 2L) }, defaultAccessible = false)
		public void setY(String y)
		{
			this.y = y;
		}

		@Override
		public String toString()
		{
			return "POJO1 [x=" + x + ", y=" + y + "]";
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + ((y == null) ? 0 : y.hashCode());
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
			POJO1 other = (POJO1) obj;
			if(x != other.x)
				return false;
			if(y == null)
			{
				if(other.y != null)
					return false;
			}
			else if(!y.equals(other.y))
				return false;
			return true;
		}
	}

	public static class POJO2 implements Mapable<POJO2>
	{
		private int				id;
		private String			name;
		private List<Object>	value;

		public POJO2()
		{
			this(0, null, null);
		}

		public POJO2(int id, String name)
		{
			this(id, name, null);
		}

		public POJO2(int id, String name, List<Object> value)
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
			POJO2 other = (POJO2) obj;
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
		public POJO2 fromMap(Map<String, ?> map, Object... authorities)
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

	public static class POJO2Parent implements Mapable<POJO2Parent>
	{
		private POJO2Parent	sibling;
		private List<POJO2>	children;

		public POJO2Parent getSibling()
		{
			return sibling;
		}

		public void setSibling(POJO2Parent sibling)
		{
			this.sibling = sibling;
		}

		public List<POJO2> getChildren()
		{
			return children;
		}

		public void setChildren(List<POJO2> children)
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
			POJO2Parent other = (POJO2Parent) obj;
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
				for(POJO2 p : children)
					childList.add(p.toMap(authorities));
				m.put("children", childList);
			}
			return m;
		}

		@SuppressWarnings("unchecked")
		@Override
		public POJO2Parent fromMap(Map<String, ?> map, Object... authorities)
		{
			List<Object> vList = new ArrayList<Object>();
			if(authorities != null)
				vList = Arrays.asList(authorities);
			if(authorities == null || authorities.length == 0 || vList.contains("sibling"))
				if(map.containsKey("sibling"))
					if(map.get("sibling") != null)
						this.sibling = new POJO2Parent().fromMap((Map<String, ?>) map.get("sibling"), authorities);
					else
						this.sibling = null;
			if(authorities == null || authorities.length == 0 || vList.contains("children"))
				if(map.containsKey("children"))
				{
					this.children = new ArrayList<POJO2>();
					for(Map<String, ?> m : (List<Map<String, ?>>) map.get("children"))
						children.add(new POJO2().fromMap(m, authorities));
				}
			return this;
		}

	}

	public static class POJO3
	{
		private int				id;
		private String			name;
		private List<Double>	value;

		public POJO3()
		{
			this(0, null, null);
		}

		public POJO3(int id, String name)
		{
			this(id, name, null);
		}

		public POJO3(int id, String name, List<Double> value)
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

		public List<Double> getValue()
		{
			return value;
		}

		public void setValue(List<Double> value)
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
			POJO3 other = (POJO3) obj;
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
	}

	public static class POJO3Parent
	{
		private POJO3Parent	sibling;
		private List<POJO3>	children;

		public POJO3Parent getSibling()
		{
			return sibling;
		}

		public void setSibling(POJO3Parent sibling)
		{
			this.sibling = sibling;
		}

		public List<POJO3> getChildren()
		{
			return children;
		}

		public void setChildren(List<POJO3> children)
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
			POJO3Parent other = (POJO3Parent) obj;
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
	}
}
