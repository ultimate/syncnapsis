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
package com.syncnapsis.data.service.impl;

import java.util.Date;

import org.jmock.Expectations;

import com.syncnapsis.data.dao.ParameterDao;
import com.syncnapsis.data.model.Parameter;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.enums.EnumDateFormat;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses({ParameterManager.class, ParameterManagerImpl.class})
public class ParameterManagerImplTest extends GenericManagerImplTestCase<Parameter, Long, ParameterManager, ParameterDao>
{
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new Parameter());
		setDaoClass(ParameterDao.class);
		setMockDao(mockContext.mock(ParameterDao.class));
		setMockManager(new ParameterManagerImpl(mockDao));
	}
	
	public void testGetString() throws Exception
	{
		Parameter dummy = new Parameter();
		dummy.setName("name");
		dummy.setValue("1");
		
		MethodCall managerCall = new MethodCall("getString", new String("1"), "name");
		MethodCall daoCall = new MethodCall("getByName", dummy, "name");
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testGetLong() throws Exception
	{
		Parameter dummy = new Parameter();
		dummy.setName("name");
		dummy.setValue("1");
		
		MethodCall managerCall = new MethodCall("getLong", new Long(1), "name");
		MethodCall daoCall = new MethodCall("getByName", dummy, "name");
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testGetInteger() throws Exception
	{
		Parameter dummy = new Parameter();
		dummy.setName("name");
		dummy.setValue("1");
		
		MethodCall managerCall = new MethodCall("getInteger", new Integer(1), "name");
		MethodCall daoCall = new MethodCall("getByName", dummy, "name");
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testGetShort() throws Exception
	{
		Parameter dummy = new Parameter();
		dummy.setName("name");
		dummy.setValue("1");
		
		MethodCall managerCall = new MethodCall("getShort", new Short("1"), "name");
		MethodCall daoCall = new MethodCall("getByName", dummy, "name");
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testGetByte() throws Exception
	{
		Parameter dummy = new Parameter();
		dummy.setName("name");
		dummy.setValue("1");
		
		MethodCall managerCall = new MethodCall("getByte", new Byte("1"), "name");
		MethodCall daoCall = new MethodCall("getByName", dummy, "name");
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testGetDouble() throws Exception
	{
		Parameter dummy = new Parameter();
		dummy.setName("name");
		dummy.setValue("1");
		
		MethodCall managerCall = new MethodCall("getDouble", new Double("1"), "name");
		MethodCall daoCall = new MethodCall("getByName", dummy, "name");
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testGetFloat() throws Exception
	{
		Parameter dummy = new Parameter();
		dummy.setName("name");
		dummy.setValue("1");
		
		MethodCall managerCall = new MethodCall("getFloat", new Float("1"), "name");
		MethodCall daoCall = new MethodCall("getByName", dummy, "name");
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testGetBoolean() throws Exception
	{
		Parameter dummy = new Parameter();
		dummy.setName("name");
		dummy.setValue("true");
		
		MethodCall managerCall = new MethodCall("getBoolean", new Boolean(true), "name");
		MethodCall daoCall = new MethodCall("getByName", dummy, "name");
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testGetDate() throws Exception
	{
		Date date = new Date(1000); // must match a full seconds, cause ms are not formatted...
		
		Parameter dummy = new Parameter();
		dummy.setName("name");
		dummy.setValue(EnumDateFormat.getDefault().getDateFormat().format(date));
		
		MethodCall managerCall = new MethodCall("getDate", date, "name");
		MethodCall daoCall = new MethodCall("getByName", dummy, "name");
		simpleGenericTest(managerCall, daoCall);
	}
	
	public void testSetString() throws Exception
	{
		final String name = "aParam";
		final String value ="val";
		final long id = 123L;
		
		final Parameter oldParam = new Parameter();
		oldParam.setId(id);
		oldParam.setName(name);
		oldParam.setValue("old");
		
		final Parameter newParam = new Parameter();
		//no id yet
		newParam.setName(name);
		newParam.setValue(value);
		
		final Parameter newParamSaved = new Parameter();
		newParamSaved.setId(id);
		newParamSaved.setName(name);
		newParamSaved.setValue(value);
		
		// parameter existing
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByName(name);
				will(returnValue(oldParam));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(newParamSaved);
				will(returnValue(newParamSaved));
			}
		});
		assertEquals(id, mockManager.setString(name, value));
		
		// parameter not existing
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).getByName(name);
				will(returnValue(null));
			}
		});
		mockContext.checking(new Expectations() {
			{
				oneOf(mockDao).save(newParam);
				will(returnValue(newParamSaved));
			}
		});
		assertEquals(id, mockManager.setString(name, value));
	}
}
