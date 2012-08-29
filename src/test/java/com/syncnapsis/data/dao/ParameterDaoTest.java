package com.syncnapsis.data.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import com.syncnapsis.data.dao.hibernate.ParameterDaoHibernate;
import com.syncnapsis.data.model.Parameter;
import com.syncnapsis.enums.EnumDateFormat;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses( { ParameterDao.class, ParameterDaoHibernate.class })
public class ParameterDaoTest extends BaseDaoTestCase
{
	private ParameterDao	parameterDao;

	public void testGetParameterInvalid() throws Exception
	{
		logger.debug("testing getParameter invalid...");
		try
		{
			parameterDao.get("universe.year2");
			fail("'badParameterName' found in database, failing test...");
		}
		catch(ObjectNotFoundException e)
		{
			assertNotNull(e);
		}
	}

	public void testGetParameter() throws Exception
	{
		logger.debug("testing getParameter valid...");
		Parameter parameter = parameterDao.get("universe.year");

		assertNotNull(parameter);
		assertEquals("universe.year", parameter.getId());
		assertEquals("3600", parameter.getValue());

		String parameterString = parameterDao.getString("universe.year");
		assertNotNull(parameterString);
		assertEquals("3600", parameterString);
	}

	public void testGetParameterByName() throws Exception
	{
		logger.debug("testing getParameterByName valid...");

		Parameter parameter;

		parameter = parameterDao.get("universe.year");
		assertNotNull(parameter);
		assertEquals("universe.year", parameter.getId());
		assertEquals("3600", parameter.getValue());

		parameter = parameterDao.get("news.maxItems");
		assertNotNull(parameter);
		assertEquals("news.maxItems", parameter.getId());
		assertEquals("10", parameter.getValue());
	}

	public void testUpdateParameter() throws Exception
	{
		logger.debug("testing update parameter...");
		Parameter parameter = parameterDao.get("universe.year");

		parameter.setValue("xyz");
		parameter = parameterDao.save(parameter);

		parameter = parameterDao.get("universe.year");
		assertEquals("xyz", parameter.getValue());
	}

	public void testAddAndRemoveParameter() throws Exception
	{
		String id = "param";
		String val = "value";
		logger.debug("testing add and remove on parameter...");
		Parameter parameter = new Parameter();
		parameter.setId(id);
		parameter.setValue(val);

		parameter = parameterDao.save(parameter);
		assertNotNull(parameter.getId());
		assertEquals(id, parameter.getId());

		parameter = parameterDao.get(parameter.getId());
		assertEquals(id, parameter.getId());
		assertEquals(val, parameter.getValue());

		assertEquals("deleted", parameterDao.remove(parameter));
	}

	public void testParameterExists() throws Exception
	{
		logger.debug("testing parameter exists...");
		boolean b = parameterDao.exists("universe.year");
		super.assertTrue(b);
	}

	public void testParameterNotExists() throws Exception
	{
		logger.debug("testing parameter not exists...");
		boolean b = parameterDao.exists("universe.year2");
		super.assertFalse(b);
	}

	public void testGetAll() throws Exception
	{
		List<Parameter> parameters;

		logger.debug("testing getAll()...");
		parameters = parameterDao.getAll();
		assertNotNull(parameters);
		logger.debug(parameters.size() + " parameters found");
		assertTrue(parameters.size() >= 5);
	}

	@TestCoversMethods( { "getString", "getInteger", "getLong", "getDouble", "getFloat", "getShort", "getByte", "getBoolean", "getDate" })
	public void testGetByType() throws Exception
	{
		logger.debug("testing string...");

		String strValue = "lalala";
		Parameter string = new Parameter();
		string.setId("string");
		string.setValue(strValue.toString());

		parameterDao.save(string);

		assertEquals(strValue, parameterDao.getString(string.getId()));

		logger.debug("testing numbers...");

		Byte numValue = 3;
		Parameter number = new Parameter();
		number.setId("number");
		number.setValue(numValue.toString());

		parameterDao.save(number);

		assertEquals((Integer) numValue.intValue(), parameterDao.getInteger(number.getId()));
		assertEquals((Long) numValue.longValue(), parameterDao.getLong(number.getId()));
		assertEquals((Double) numValue.doubleValue(), parameterDao.getDouble(number.getId()));
		assertEquals((Float) numValue.floatValue(), parameterDao.getFloat(number.getId()));
		assertEquals((Short) numValue.shortValue(), parameterDao.getShort(number.getId()));
		assertEquals((Byte) numValue.byteValue(), parameterDao.getByte(number.getId()));

		logger.debug("testing boolean...");

		Parameter bool = new Parameter();
		bool.setId("boolean");

		bool.setValue("true");
		bool = parameterDao.save(bool);
		assertEquals((Boolean) true, parameterDao.getBoolean(bool.getId()));

		bool.setValue("false");
		bool = parameterDao.save(bool);
		assertEquals((Boolean) false, parameterDao.getBoolean(bool.getId()));

		bool.setValue("xyz");
		bool = parameterDao.save(bool);
		assertEquals((Boolean) false, parameterDao.getBoolean(bool.getId()));

		logger.debug("testing date...");

		Date dateValue = EnumDateFormat.getDefault().getDateFormat().parse("2009-06-05 04:03:02");
		Parameter date = new Parameter();
		date.setId("date");
		date.setValue(EnumDateFormat.getDefault().getDateFormat().format(dateValue));

		parameterDao.save(date);

		assertEquals(dateValue.getTime(), parameterDao.getDate(date.getId()).getTime());
		assertEquals(dateValue, parameterDao.getDate(date.getId()));
	}
}
