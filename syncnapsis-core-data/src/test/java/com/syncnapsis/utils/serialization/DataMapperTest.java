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
package com.syncnapsis.utils.serialization;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.UniversalManager;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods({ "get*", "set*", "afterPropertiesSet" })
public class DataMapperTest extends LoggerTestCase
{
	@SuppressWarnings("unchecked")
	public void testToMap() throws Exception
	{
		DataMapper mapper = new DataMapper();
		
		Container c = new Container();
		POJO p1 = new POJO();
		POJO p2 = new POJO();
		
		c.setPojo(p1);
		p1.setChild(p2);
		p1.setId(1L);
		p2.setId(2L);
		
		Map<String, Object> map = mapper.toMap(c, (Object[]) null);
		
		assertNotNull(map);
		// first level
		assertEquals(1, map.size());
		assertFalse(map.containsKey(DataMapper.KEY_TYPE));
		assertTrue(map.containsKey("pojo"));
		assertTrue(map.get("pojo") instanceof Map);
		// second level
		Map<String, Object> map2 = (Map<String, Object>) map.get("pojo");
		assertEquals(5, map2.size());
		assertEquals(POJO.class.getName(), map2.get(DataMapper.KEY_TYPE));
		assertEquals(1L, map2.get(DataMapper.KEY_ID));
		assertTrue(map2.containsKey("value"));
		assertTrue(map2.containsKey("version"));
		assertTrue(map2.containsKey("child"));
		assertTrue(map2.get("child") instanceof Map);
		// third level	
		Map<String, Object> map3 = (Map<String, Object>) map2.get("child");
		assertEquals(2, map3.size());
		assertEquals(POJO.class.getName(), map3.get(DataMapper.KEY_TYPE));
		assertEquals(2L, map3.get(DataMapper.KEY_ID));
		assertFalse(map3.containsKey("value"));
		assertFalse(map3.containsKey("version"));
		assertFalse(map3.containsKey("child"));
	}
	
	public void testFromMap() throws Exception
	{
		final UniversalManager universalManager = mockContext.mock(UniversalManager.class);
		DataMapper mapper = new DataMapper();
		mapper.setUniversalManager(universalManager);
		
		final long id = 1;
		final String value = "abc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DataMapper.KEY_ID, id);
		map.put("value", value);
		
		final POJO p = new POJO();
		p.setId(id);
		p.setValue("def");
		
		mockContext.checking(new Expectations(){
			{
				oneOf(universalManager).get(POJO.class, id);
				will(returnValue(p));
			}
		});
		
		POJO result = mapper.fromMap(new POJO(), map);
		mockContext.assertIsSatisfied();
		
		assertNotNull(result);
		assertEquals(value, result.getValue());
		assertEquals((Long) id, result.getId());
	}
	
	public static class POJO extends BaseObject<Long>
	{
		private String value;
		
		private POJO child;

		public String getValue()
		{
			return value;
		}

		public void setValue(String value)
		{
			this.value = value;
		}

		public POJO getChild()
		{
			return child;
		}

		public void setChild(POJO child)
		{
			this.child = child;
		}
	}
	
	public static class Container
	{
		private POJO pojo;

		public POJO getPojo()
		{
			return pojo;
		}

		public void setPojo(POJO pojo)
		{
			this.pojo = pojo;
		}		
	}
}
