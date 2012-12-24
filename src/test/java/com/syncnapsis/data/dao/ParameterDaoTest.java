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
package com.syncnapsis.data.dao;

import java.util.Date;

import com.syncnapsis.data.dao.hibernate.ParameterDaoHibernate;
import com.syncnapsis.data.model.Parameter;
import com.syncnapsis.enums.EnumDateFormat;
import com.syncnapsis.tests.GenericNameDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses({ ParameterDao.class, ParameterDaoHibernate.class })
public class ParameterDaoTest extends GenericNameDaoTestCase<Parameter, Long>
{
	private ParameterDao	parameterDao;
	protected void setUp() throws Exception
	{
		super.setUp();

		String existingName = "news.maxItems";
		Long existingId = parameterDao.getByName(existingName).getId();

		Parameter parameter = new Parameter();
		parameter.setName("new param");
		parameter.setValue("new value");

		setEntity(parameter);

		setEntityProperty("name");
		setEntityPropertyValue("any other value");

		setExistingEntityId(existingId);
		setBadEntityId(-1L);
		setExistingEntityName(existingName);

		setGenericNameDao(parameterDao);
	}

	@TestCoversMethods({ "getString", "getInteger", "getLong", "getDouble", "getFloat", "getShort", "getByte", "getBoolean", "getDate" })
	public void testGetByType() throws Exception
	{
		logger.debug("testing string...");

		String strValue = "lalala";
		Parameter string = new Parameter();
		string.setName("string");
		string.setValue(strValue.toString());

		parameterDao.save(string);

		assertEquals(strValue, parameterDao.getString(string.getName()));

		logger.debug("testing numbers...");

		Byte numValue = 3;
		Parameter number = new Parameter();
		number.setName("number");
		number.setValue(numValue.toString());

		parameterDao.save(number);

		assertEquals((Integer) numValue.intValue(), parameterDao.getInteger(number.getName()));
		assertEquals((Long) numValue.longValue(), parameterDao.getLong(number.getName()));
		assertEquals((Double) numValue.doubleValue(), parameterDao.getDouble(number.getName()));
		assertEquals((Float) numValue.floatValue(), parameterDao.getFloat(number.getName()));
		assertEquals((Short) numValue.shortValue(), parameterDao.getShort(number.getName()));
		assertEquals((Byte) numValue.byteValue(), parameterDao.getByte(number.getName()));

		logger.debug("testing boolean...");

		Parameter bool = new Parameter();
		bool.setName("boolean");

		bool.setValue("true");
		bool = parameterDao.save(bool);
		assertEquals((Boolean) true, parameterDao.getBoolean(bool.getName()));

		bool.setValue("false");
		bool = parameterDao.save(bool);
		assertEquals((Boolean) false, parameterDao.getBoolean(bool.getName()));

		bool.setValue("xyz");
		bool = parameterDao.save(bool);
		assertEquals((Boolean) false, parameterDao.getBoolean(bool.getName()));

		logger.debug("testing date...");

		Date dateValue = EnumDateFormat.getDefault().getDateFormat().parse("2009-06-05 04:03:02");
		Parameter date = new Parameter();
		date.setName("date");
		date.setValue(EnumDateFormat.getDefault().getDateFormat().format(dateValue));

		parameterDao.save(date);

		assertEquals(dateValue.getTime(), parameterDao.getDate(date.getName()).getTime());
		assertEquals(dateValue, parameterDao.getDate(date.getName()));
	}
}
