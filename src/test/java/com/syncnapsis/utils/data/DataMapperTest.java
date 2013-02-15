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

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.UniversalManager;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.serialization.DataMapper;

@TestExcludesMethods({ "get*", "set*", "afterPropertiesSet" })
public class DataMapperTest extends LoggerTestCase
{
	public void testMerge() throws Exception
	{
		final UniversalManager universalManager = mockContext.mock(UniversalManager.class);
		DataMapper mapper = new DataMapper();
		mapper.setUniversalManager(universalManager);
		
		final long id = 1;
		final String value = "abc";
		Map<String, Object> prepared = new HashMap<String, Object>();
		prepared.put(DataMapper.KEY_ID, id);
		prepared.put("value", value);
		
		final POJO p = new POJO();
		p.setId(id);
		p.setValue("def");
		
		mockContext.checking(new Expectations(){
			{
				oneOf(universalManager).get(POJO.class, id);
				will(returnValue(p));
			}
		});
		
		POJO result = mapper.merge(POJO.class, prepared);
		mockContext.assertIsSatisfied();
		
		assertNotNull(result);
		assertEquals(value, result.getValue());
		assertEquals((Long) id, result.getId());
	}
	
	public static class POJO extends BaseObject<Long>
	{
		private String value;

		public String getValue()
		{
			return value;
		}

		public void setValue(String value)
		{
			this.value = value;
		}
	}
}
