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
package com.syncnapsis.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.servlet.ServletContext;

import org.jmock.Expectations;

import com.syncnapsis.exceptions.ConversionException;
import com.syncnapsis.mock.MockSetAndGetEntity;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.reflections.FieldCriterion;
import com.syncnapsis.utils.serialization.BaseMapper;
import com.syncnapsis.utils.serialization.Mapper;
import com.syncnapsis.utils.spring.BeanProxy;

@TestCoversClasses({ ReflectionsUtil.class, FieldCriterion.class, ConversionException.class })
public class ReflectionsUtilTest extends LoggerTestCase
{
	public void testSetAccessible() throws Exception
	{
		Method m = BeanProxy.class.getDeclaredMethod("getContext", ServletContext.class);
		assertFalse(m.isAccessible());
		ReflectionsUtil.setAccessible(m, true);
		assertTrue(m.isAccessible());
		ReflectionsUtil.setAccessible(m, false);
		assertFalse(m.isAccessible());

		Field f = BeanProxy.class.getDeclaredField("delegate");
		assertFalse(f.isAccessible());
		ReflectionsUtil.setAccessible(f, true);
		assertTrue(f.isAccessible());
		ReflectionsUtil.setAccessible(f, false);
		assertFalse(f.isAccessible());
	}

	@TestCoversMethods("*etField")
	public void testSetAndGetField() throws Exception
	{
		MockSetAndGetEntity<String> entity = new MockSetAndGetEntity<String>();

		String value = "any value";

		assertEquals(value, ReflectionsUtil.setField(entity, "value", value));
		assertEquals(value, entity.getValue());
		assertEquals(value, ReflectionsUtil.getField(entity, "value"));
	}

	public void testGetFieldByKey() throws Exception
	{
		POJO3 p1 = new POJO3();
		p1.name = "p1";
		POJO3 p2 = new POJO3();
		p2.name = "p2";
		POJO3 p3 = new POJO3();
		p3.name = "p3";

		p1.child = p2;
		p2.child = p3;

		assertEquals(p1.name, ReflectionsUtil.getFieldByKey(p1, "name"));
		assertEquals(p1.child, ReflectionsUtil.getFieldByKey(p1, "child"));

		assertEquals(p2.name, ReflectionsUtil.getFieldByKey(p1, "child.name"));
		assertEquals(p2.child, ReflectionsUtil.getFieldByKey(p1, "child.child"));

		assertEquals(p3.name, ReflectionsUtil.getFieldByKey(p1, "child.child.name"));

		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("name", "m1");
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("id", "m2");
		Map<String, Object> m3 = new HashMap<String, Object>();
		m3.put("desc", "m1");

		m1.put("sub", m2);
		m2.put("child", m3);

		assertEquals(m1.get("name"), ReflectionsUtil.getFieldByKey(m1, "name"));
		assertEquals(m1.get("sub"), ReflectionsUtil.getFieldByKey(m1, "sub"));

		assertEquals(m2.get("id"), ReflectionsUtil.getFieldByKey(m1, "sub.id"));
		assertEquals(m2.get("child"), ReflectionsUtil.getFieldByKey(m1, "sub.child"));

		assertEquals(m3.get("desc"), ReflectionsUtil.getFieldByKey(m1, "sub.child.desc"));
	}

	@TestCoversMethods({ "initSuitabilityMatrix", "isMethodSuitableFor" })
	public void testSuitabilityMatrix() throws Exception
	{
		@SuppressWarnings("unused")
		Object tmp = new Object() {
			// @formatter:off
			public void doX(Integer a)	{}
			public void doX(Long a)		{}
			public void doX(Short a)	{}
			public void doX(Byte a)		{}
			public void doX(Double a)	{}
			public void doX(Float a)	{}
			public void doX(Boolean a)	{}
			public void doX(Character a){}
			public void doX(String a)	{}
			public void doX(Object a) 	{}
			public void doX(POJO a) 	{}
			// @formatter:on
		};

		Class<?> cls = tmp.getClass();

		Method methodFound;
		for(Method methodToSearch : cls.getMethods())
		{
			if(methodToSearch.getName().equals("doX"))
			{
				methodFound = ReflectionsUtil.findMethod(cls, methodToSearch.getName(), methodToSearch.getParameterTypes());
				assertEquals(methodToSearch, methodFound);
			}
		}

		Method expected, found;

		expected = cls.getMethod("doX", Object.class);
		found = ReflectionsUtil.findMethod(cls, "doX", new Object() {}.getClass());
		assertEquals(expected, found);

		expected = cls.getMethod("doX", POJO.class);
		found = ReflectionsUtil.findMethod(cls, "doX", new POJO() {}.getClass());
		assertEquals(expected, found);

		// test some Object cases...

		Method m;

		m = cls.getMethod("doX", String.class);
		assertTrue(ReflectionsUtil.isMethodSuitableFor(m, "111"));
		assertFalse(ReflectionsUtil.isMethodSuitableFor(m, new Object()));
		assertFalse(ReflectionsUtil.isMethodSuitableFor(m, new POJO()));

		m = cls.getMethod("doX", Object.class);
		assertTrue(ReflectionsUtil.isMethodSuitableFor(m, "111"));
		assertTrue(ReflectionsUtil.isMethodSuitableFor(m, new Object()));
		assertTrue(ReflectionsUtil.isMethodSuitableFor(m, new POJO()));

		m = cls.getMethod("doX", POJO.class);
		assertFalse(ReflectionsUtil.isMethodSuitableFor(m, "111"));
		assertFalse(ReflectionsUtil.isMethodSuitableFor(m, new Object()));
		assertTrue(ReflectionsUtil.isMethodSuitableFor(m, new POJO()));
	}

	@TestCoversMethods({ "findMethodAndConvertArgs", "checkAndConvertArg*" })
	public void testFindMethodAndConvertArgs() throws Exception
	{
		Mapper mapper = new BaseMapper();

		@SuppressWarnings("unused")
		Object tmp = new Object() {
			// @formatter:off
			public void doSomething1(Long id, String name)	{}
			public void doSomething2(String x, String... args)	{}
			public void doSomething3(int i, POJO p)	{}
			// @formatter:on
		};
		Class<?> cls = tmp.getClass();

		Method expected;

		expected = cls.getMethod("doSomething1", Long.class, String.class);
		// valid
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1L, "a" }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1L, null }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, "a" }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, null }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, "a" }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1.0, "a" }, mapper));
		// invalid
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, 1 }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", "a" }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, "a", null }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1 }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a" }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null }, mapper));

		expected = cls.getMethod("doSomething2", String.class, String[].class);
		// valid
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a" }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", "b" }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", "b", "c" }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", null, "c" }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", "b", null }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", null, null }, mapper));
		assertEquals(expected,
				ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", new String[] { "b", "c" } }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", new String[] { "b" } }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", (String[]) null }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, "b" }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, "b", "c" }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, null, "c" }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, "b", null }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, null, null }, mapper));
		assertEquals(expected,
				ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, new String[] { "b", "c" } }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, new String[] { "b" } }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, (String[]) null }, mapper));
		// invalid
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, 1 }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1 }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, "a" }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", 1 }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, "a", null }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { "a", 1, null }, mapper));

		POJO p = new POJO(1, "eins");
		Map<String, Object> pm = mapper.toMap(p);
		assertEquals(p.getId(), pm.get("id"));
		assertEquals(p.getName(), pm.get("name"));
		Map<String, Object> im = new HashMap<String, Object>();

		expected = cls.getMethod("doSomething3", int.class, POJO.class);
		// valid
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, p }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, null }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1, pm }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1L, null }, mapper));
		assertEquals(expected, ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1.0, null }, mapper));
		// invalid
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { 1 }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, p }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, null }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { null, pm }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { im, p }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { im, null }, mapper));
		assertNull(ReflectionsUtil.findMethodAndConvertArgs(cls, expected.getName(), new Object[] { im, pm }, mapper));
	}

	public void testConvert() throws Exception
	{
		assertEquals(new Long(1), ReflectionsUtil.convert(Long.class, new Integer(1)));
		assertEquals(new Long(1), ReflectionsUtil.convert(long.class, new Integer(1)));
		assertEquals(new Long(1), ReflectionsUtil.convert(Long.class, 1));
		assertEquals(new Long(1), ReflectionsUtil.convert(long.class, 1));

		assertEquals(new Integer(1), ReflectionsUtil.convert(Integer.class, new Long(1)));
		assertEquals(new Integer(1), ReflectionsUtil.convert(int.class, new Long(1)));
		assertEquals(new Integer(1), ReflectionsUtil.convert(Integer.class, 1L));
		assertEquals(new Integer(1), ReflectionsUtil.convert(int.class, 1L));

		final Dummy<Integer> d = new Dummy<Integer>();
		assertSame(d, ReflectionsUtil.convert(Dummy.class, d));
		assertSame(d, ReflectionsUtil.convert(DummySuper.class, d));

		try
		{
			ReflectionsUtil.convert(Dummy.class, new DummySuper());
			fail("expected Exception not occurred!");
		}
		catch(ConversionException e)
		{
			assertNotNull(e);
		}

		final Mapper m = mockContext.mock(Mapper.class);
		final DummySuper ds = new DummySuper();

		mockContext.checking(new Expectations() {
			{
				oneOf(m).merge(Dummy.class, ds);
				will(returnValue(d));
			}
		});

		assertSame(d, ReflectionsUtil.convert(Dummy.class, ds, m));
	}

	public void testFindMethod() throws Exception
	{
		Dummy<String> dummy = new Dummy<String>();
		String methodName = "anyMethod";
		Method m = ReflectionsUtil.findMethod(dummy.getClass(), methodName, String.class, int.class, Boolean.class);
		assertNotNull(m);
		assertEquals(methodName, m.getName());
	}

	public void testFindField() throws Exception
	{
		Field f;

		f = ReflectionsUtil.findField(Dummy.class, "field0");
		assertEquals("field0", f.getName());

		f = ReflectionsUtil.findField(Dummy.class, "field1");
		assertEquals("field1", f.getName());

		f = ReflectionsUtil.findField(Dummy.class, "field2");
		assertEquals("field2", f.getName());

		f = ReflectionsUtil.findField(DummySuper.class, "field0");
		assertEquals("field0", f.getName());

		try
		{
			f = ReflectionsUtil.findField(DummySuper.class, "field1");
			fail("expected Exception not occurred!");
		}
		catch(NoSuchFieldException e)
		{
			// expected Exception
		}
	}

	public void testFindFields() throws Exception
	{
		List<com.syncnapsis.utils.reflections.Field> fields;

		fields = ReflectionsUtil.findFields(Dummy.class);
		assertEquals(4, fields.size());
		assertTrue(containsField(fields, "field0"));
		assertTrue(containsField(fields, "field1"));
		assertTrue(containsField(fields, "field2"));
		assertTrue(containsField(fields, "field3"));

		fields = ReflectionsUtil.findFields(Dummy.class, FieldCriterion.ENTITY);
		assertEquals(1, fields.size());
		assertTrue(containsField(fields, "field1"));

		fields = ReflectionsUtil.findFields(DummySuper.class);
		assertEquals(1, fields.size());
		assertTrue(containsField(fields, "field0"));
	}

	@TestCoversMethods({ "findDefaultFields", "*etDefaultFieldCriterions", "isValidField" })
	public void testFindDefaultFields() throws Exception
	{
		List<com.syncnapsis.utils.reflections.Field> fields;

		fields = ReflectionsUtil.findDefaultFields(Dummy.class);
		assertEquals(3, fields.size());
		assertTrue(containsField(fields, "field0"));
		assertTrue(containsField(fields, "field1"));
		assertTrue(containsField(fields, "field2"));

		fields = ReflectionsUtil.findDefaultFields(DummySuper.class);
		assertEquals(1, fields.size());
		assertTrue(containsField(fields, "field0"));
	}

	public void testGetGetter() throws Exception
	{
		Method m;

		m = ReflectionsUtil.getGetter(Dummy.class, ReflectionsUtil.findField(Dummy.class, "field0"));
		assertEquals("getField0", m.getName());

		m = ReflectionsUtil.getGetter(Dummy.class, ReflectionsUtil.findField(Dummy.class, "field1"));
		assertEquals("getField1", m.getName());

		m = ReflectionsUtil.getGetter(Dummy.class, ReflectionsUtil.findField(Dummy.class, "field2"));
		assertEquals("isField2", m.getName());

		m = ReflectionsUtil.getGetter(DummySuper.class, ReflectionsUtil.findField(Dummy.class, "field0"));
		assertEquals("getField0", m.getName());

		m = ReflectionsUtil.getGetter(DummySuper.class, ReflectionsUtil.findField(Dummy.class, "field1"));
		assertNull(m);
	}

	public void testGetSetter() throws Exception
	{
		Method m;

		m = ReflectionsUtil.getSetter(Dummy.class, ReflectionsUtil.findField(Dummy.class, "field0"));
		assertEquals("setField0", m.getName());

		m = ReflectionsUtil.getSetter(Dummy.class, ReflectionsUtil.findField(Dummy.class, "field1"));
		assertEquals("setField1", m.getName());

		m = ReflectionsUtil.getSetter(Dummy.class, ReflectionsUtil.findField(Dummy.class, "field2"));
		assertEquals("setField2", m.getName());

		m = ReflectionsUtil.getSetter(DummySuper.class, ReflectionsUtil.findField(Dummy.class, "field0"));
		assertEquals("setField0", m.getName());

		m = ReflectionsUtil.getSetter(DummySuper.class, ReflectionsUtil.findField(Dummy.class, "field1"));
		assertNull(m);
	}

	@SuppressWarnings("serial")
	public void testGetActualTypeArguments() throws Exception
	{
		List<String> l = new ArrayList<String>();
		List<String> l_ = new ArrayList<String>() {};
		Generic1<String> g1 = new Generic1<String>();
		Generic1<String> g1_ = new Generic1<String>() {};
		Generic2<String> g2 = new Generic2<String>();
		Generic2<String> g2_ = new Generic2<String>() {};
		Generic3<String, Integer> g3 = new Generic3<String, Integer>();
		Generic3<String, Integer> g3_ = new Generic3<String, Integer>() {};
		Generic4<String> g4 = new Generic4<String>();
		Generic4<String> g4_ = new Generic4<String>() {};

		assertEquals(new Type[] { Object.class }, ReflectionsUtil.getActualTypeArguments(l));
		assertEquals(new Type[] { Object.class }, ReflectionsUtil.getActualTypeArguments(l, List.class));

		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(l_));
		assertEquals(new Type[] { String.class }, ReflectionsUtil.getActualTypeArguments(l_, List.class));

		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(g1, Object.class));

		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(g1_));
		assertEquals(new Type[] { String.class }, ReflectionsUtil.getActualTypeArguments(g1_, Generic1.class));
		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(g1_, Object.class));

		assertEquals(new Type[] { Object.class }, ReflectionsUtil.getActualTypeArguments(g2));
		assertEquals(new Type[] { Object.class }, ReflectionsUtil.getActualTypeArguments(g2, Generic2.class));
		assertEquals(new Type[] { Object.class }, ReflectionsUtil.getActualTypeArguments(g2, Generic1.class));
		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(g2, Object.class));

		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(g2_));
		assertEquals(new Type[] { String.class }, ReflectionsUtil.getActualTypeArguments(g2_, Generic2.class));
		assertEquals(new Type[] { String.class }, ReflectionsUtil.getActualTypeArguments(g2_, Generic1.class));
		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(g2_, Object.class));

		assertEquals(new Type[] { Object.class, Object.class }, ReflectionsUtil.getActualTypeArguments(g3));
		assertEquals(new Type[] { Object.class, Object.class }, ReflectionsUtil.getActualTypeArguments(g3, Generic3.class));
		assertEquals(new Type[] { Object.class }, ReflectionsUtil.getActualTypeArguments(g3, Generic2.class));
		assertEquals(new Type[] { Object.class }, ReflectionsUtil.getActualTypeArguments(g3, Generic1.class));
		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(g3, Object.class));

		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(g3_));
		assertEquals(new Type[] { String.class, Integer.class }, ReflectionsUtil.getActualTypeArguments(g3_, Generic3.class));
		assertEquals(new Type[] { String.class }, ReflectionsUtil.getActualTypeArguments(g3_, Generic2.class));
		assertEquals(new Type[] { String.class }, ReflectionsUtil.getActualTypeArguments(g3_, Generic1.class));
		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(g3_, Object.class));

		assertEquals(new Type[] { Object.class }, ReflectionsUtil.getActualTypeArguments(g4));
		assertEquals(new Type[] { Object.class }, ReflectionsUtil.getActualTypeArguments(g4, Generic4.class));
		assertEquals(new Type[] { Object.class, Integer.class }, ReflectionsUtil.getActualTypeArguments(g4, Generic3.class));
		assertEquals(new Type[] { Object.class }, ReflectionsUtil.getActualTypeArguments(g4, Generic2.class));
		assertEquals(new Type[] { Object.class }, ReflectionsUtil.getActualTypeArguments(g4, Generic1.class));
		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(g4, Object.class));

		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(g4_));
		assertEquals(new Type[] { String.class }, ReflectionsUtil.getActualTypeArguments(g4_, Generic4.class));
		assertEquals(new Type[] { String.class, Integer.class }, ReflectionsUtil.getActualTypeArguments(g4_, Generic3.class));
		assertEquals(new Type[] { String.class }, ReflectionsUtil.getActualTypeArguments(g4_, Generic2.class));
		assertEquals(new Type[] { String.class }, ReflectionsUtil.getActualTypeArguments(g4_, Generic1.class));
		assertEquals(new Type[] {}, ReflectionsUtil.getActualTypeArguments(g4_, Object.class));

		try
		{
			ReflectionsUtil.getActualTypeArguments(g1, Generic2.class);
			fail("Expected Exception not occurred");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		try
		{
			ReflectionsUtil.getActualTypeArguments(g2, Generic3.class);
			fail("Expected Exception not occurred");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		try
		{
			ReflectionsUtil.getActualTypeArguments(g3, Generic4.class);
			fail("Expected Exception not occurred");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		try
		{
			ReflectionsUtil.getActualTypeArguments(g4, String.class);
			fail("Expected Exception not occurred");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
	}

	public static void testGetActualTypeArgumentsOnField() throws Exception
	{
		Field fGen = POJO2.class.getField("list");
		Field fMy = POJO2.class.getField("myList");

		assertEquals(new Type[] { String.class }, ReflectionsUtil.getActualTypeArguments(fGen.getGenericType(), List.class));
		assertEquals(new Type[] { String.class }, ReflectionsUtil.getActualTypeArguments(fMy.getGenericType(), List.class));
	}

	public void testResolveTypeArguments() throws Exception
	{
		Map<Type, Map<TypeVariable<?>, Type>> typeArgs;

		Generic4<String> g4 = new Generic4<String>();
		Generic4<String> g4_ = new Generic4<String>() {};
		Field fGen = POJO2.class.getField("list");
		Field fMy = POJO2.class.getField("myList");

		typeArgs = ReflectionsUtil.resolveTypeArguments(g4);
		assertEquals(6, typeArgs.size());
		assertTrue(typeArgs.containsKey(Object.class));
		assertTrue(typeArgs.containsKey(GenericI.class));
		assertTrue(typeArgs.containsKey(Generic1.class));
		assertTrue(typeArgs.containsKey(Generic2.class));
		assertTrue(typeArgs.containsKey(Generic3.class));
		assertTrue(typeArgs.containsKey(Generic4.class));
		assertFalse(typeArgs.containsKey(g4_.getClass()));

		typeArgs = ReflectionsUtil.resolveTypeArguments(g4_);
		assertEquals(7, typeArgs.size());
		assertTrue(typeArgs.containsKey(Object.class));
		assertTrue(typeArgs.containsKey(GenericI.class));
		assertTrue(typeArgs.containsKey(Generic1.class));
		assertTrue(typeArgs.containsKey(Generic2.class));
		assertTrue(typeArgs.containsKey(Generic3.class));
		assertTrue(typeArgs.containsKey(Generic4.class));
		assertTrue(typeArgs.containsKey(g4_.getClass()));

		typeArgs = ReflectionsUtil.resolveTypeArguments(fGen.getGenericType());
		assertEquals(3, typeArgs.size());
		assertTrue(typeArgs.containsKey(List.class));
		assertTrue(typeArgs.containsKey(Collection.class));
		assertTrue(typeArgs.containsKey(Iterable.class));
		assertFalse(typeArgs.containsKey(fMy.getType()));
		assertFalse(typeArgs.containsKey(Object.class));

		typeArgs = ReflectionsUtil.resolveTypeArguments(fMy.getGenericType());
		assertEquals(4, typeArgs.size());
		assertTrue(typeArgs.containsKey(List.class));
		assertTrue(typeArgs.containsKey(Collection.class));
		assertTrue(typeArgs.containsKey(Iterable.class));
		assertTrue(typeArgs.containsKey(fMy.getType()));
		assertFalse(typeArgs.containsKey(Object.class));
	}

	private static boolean containsField(List<com.syncnapsis.utils.reflections.Field> fields, String name)
	{
		for(com.syncnapsis.utils.reflections.Field f : fields)
		{
			if(f.getName().equals(name))
				return true;
		}
		return false;
	}

	@TestCoversMethods({ "getAnnotation", "isAnnotationPresent" })
	public void testGetAnnotation() throws Exception
	{
		AnnotatedElement annotated;
		Accessible accessible;
		TestExcludesMethods testExcludesMethods;

		// method

		annotated = Annotated1.class.getMethod("method");
		assertTrue(ReflectionsUtil.isAnnotationPresent(annotated, Accessible.class));
		accessible = ReflectionsUtil.getAnnotation(annotated, Accessible.class);
		assertNotNull(accessible);
		assertEquals(annotated1, accessible.by());

		annotated = Annotated2.class.getMethod("method");
		assertTrue(ReflectionsUtil.isAnnotationPresent(annotated, Accessible.class));
		accessible = ReflectionsUtil.getAnnotation(annotated, Accessible.class);
		assertNotNull(accessible);
		assertEquals(annotated2, accessible.by());

		annotated = Annotated3.class.getMethod("method");
		assertTrue(ReflectionsUtil.isAnnotationPresent(annotated, Accessible.class));
		accessible = ReflectionsUtil.getAnnotation(annotated, Accessible.class);
		assertNotNull(accessible);
		assertEquals(annotated2, accessible.by());

		// class

		annotated = Annotated1.class;
		assertTrue(ReflectionsUtil.isAnnotationPresent(annotated, TestExcludesMethods.class));
		testExcludesMethods = ReflectionsUtil.getAnnotation(annotated, TestExcludesMethods.class);
		assertNotNull(testExcludesMethods);
		assertEquals("annotated1", testExcludesMethods.value()[0]);

		annotated = Annotated2.class;
		assertTrue(ReflectionsUtil.isAnnotationPresent(annotated, TestExcludesMethods.class));
		testExcludesMethods = ReflectionsUtil.getAnnotation(annotated, TestExcludesMethods.class);
		assertNotNull(testExcludesMethods);
		assertEquals("annotated2", testExcludesMethods.value()[0]);

		annotated = Annotated3.class;
		assertTrue(ReflectionsUtil.isAnnotationPresent(annotated, TestExcludesMethods.class));
		testExcludesMethods = ReflectionsUtil.getAnnotation(annotated, TestExcludesMethods.class);
		assertNotNull(testExcludesMethods);
		assertEquals("annotated2", testExcludesMethods.value()[0]);
	}

	public void testGetAnnotations() throws Exception
	{
		AnnotatedElement annotated;
		Annotation[] annotations;

		// method

		annotated = Annotated1.class.getMethod("method");
		annotations = ReflectionsUtil.getAnnotations(annotated);
		assertNotNull(annotations);
		assertEquals(1, annotations.length);
		assertEquals(annotated1, ((Accessible) annotations[0]).by());

		annotated = Annotated2.class.getMethod("method");
		annotations = ReflectionsUtil.getAnnotations(annotated);
		assertNotNull(annotations);
		assertEquals(2, annotations.length);
		assertEquals(annotated2, ((Accessible) annotations[0]).by());
		assertEquals(annotated1, ((Accessible) annotations[1]).by());

		annotated = Annotated3.class.getMethod("method");
		annotations = ReflectionsUtil.getAnnotations(annotated);
		assertNotNull(annotations);
		assertEquals(2, annotations.length);
		assertEquals(annotated2, ((Accessible) annotations[0]).by());
		assertEquals(annotated1, ((Accessible) annotations[1]).by());

		// method

		annotated = Annotated1.class;
		annotations = ReflectionsUtil.getAnnotations(annotated);
		assertNotNull(annotations);
		assertEquals(1, annotations.length);
		assertEquals("annotated1", ((TestExcludesMethods) annotations[0]).value()[0]);

		annotated = Annotated2.class;
		annotations = ReflectionsUtil.getAnnotations(annotated);
		assertNotNull(annotations);
		assertEquals(2, annotations.length);
		assertEquals("annotated2", ((TestExcludesMethods) annotations[0]).value()[0]);
		assertEquals("annotated1", ((TestExcludesMethods) annotations[1]).value()[0]);

		annotated = Annotated3.class;
		annotations = ReflectionsUtil.getAnnotations(annotated);
		assertNotNull(annotations);
		assertEquals(2, annotations.length);
		assertEquals("annotated2", ((TestExcludesMethods) annotations[0]).value()[0]);
		assertEquals("annotated1", ((TestExcludesMethods) annotations[1]).value()[0]);
	}

	public void testGetClassForType() throws Exception
	{
		// Class
		Class<?> cls = getClass();
		assertEquals(cls, ReflectionsUtil.getClassForType(cls));

		// ParameterizedType
		ParameterizedType pt = (ParameterizedType) Generic4.class.getGenericSuperclass();
		assertEquals(Generic3.class, ReflectionsUtil.getClassForType(pt));

		// GenericArrayType
		GenericArrayType at = (GenericArrayType) new Generic4<String>().getClass().getField("array").getGenericType();
		assertEquals(Object[].class, ReflectionsUtil.getClassForType(at));
	}

	public class DummySuper
	{
		private Integer	field0;

		public Integer getField0()
		{
			return field0;
		}

		public void setField0(Integer field0)
		{
			this.field0 = field0;
		}
	}

	public class Dummy<T> extends DummySuper
	{
		private T		field1;
		private boolean	field2;
		@SuppressWarnings("unused")
		private double	field3;

		public void anyMethod(T s, int i, Boolean b)
		{

		}

		@Column
		public T getField1()
		{
			return field1;
		}

		public void setField1(T field1)
		{
			this.field1 = field1;
		}

		public boolean isField2()
		{
			return field2;
		}

		public void setField2(boolean field2)
		{
			this.field2 = field2;
		}
	}

	public static class POJO
	{
		private int		id;
		private String	name;

		public POJO()
		{
			super();
		}

		public POJO(int id, String name)
		{
			super();
			this.id = id;
			this.name = name;
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

	};

	public static class POJO2
	{
		public List<String>	list;
		public MyList		myList;
	}

	public static interface MyList extends List<String>
	{

	}

	public static class POJO3
	{
		public String	name;
		public POJO3	child;
	}

	// fomatter:off
	public static interface GenericI<TI>
	{
	}

	public static class Generic1<T1>
	{
	}

	public static class Generic2<T2> extends Generic1<T2>
	{
	}

	public static class Generic3<T31, T32> extends Generic2<T31> implements GenericI<T32>
	{
	}

	public static class Generic4<T4> extends Generic3<T4, Integer>
	{
		public T4[]	array;
	}

	// fomatter:on

	private static final int	annotated1	= 123;
	private static final int	annotated2	= 456;

	@TestExcludesMethods("annotated1")
	public static class Annotated1
	{
		@Accessible(by = annotated1)
		public void method()
		{

		}
	}

	@TestExcludesMethods("annotated2")
	public static class Annotated2 extends Annotated1
	{
		@Accessible(by = annotated2)
		@Override
		public void method()
		{

		}
	}

	public static class Annotated3 extends Annotated2
	{
		@Override
		public void method()
		{

		}
	}
}
