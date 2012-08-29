package com.syncnapsis.data.dao.hibernate;

import java.text.ParseException;
import java.util.Date;

import com.syncnapsis.data.dao.ParameterDao;
import com.syncnapsis.data.model.Parameter;
import com.syncnapsis.enums.EnumDateFormat;

/**
 * Dao-Implementierung für Hibernate für den Zugriff auf AllianceAllianceContactGroup
 * 
 * @author ultimate
 */
public class ParameterDaoHibernate extends GenericDaoHibernate<Parameter, String> implements ParameterDao
{
	/**
	 * Erzeugt eine neue DAO-Instanz durch die Super-Klasse GenericDaoHibernate
	 * mit der Modell-Klasse Parameter und der Option idOverwrite = true
	 */
	public ParameterDaoHibernate()
	{
		super(Parameter.class, true);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getString(java.lang.String)
	 */
	@Override
	public String getString(String id)
	{
		Parameter parameter = get(id);
		if(parameter != null)
			return parameter.getValue();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getBoolean(java.lang.String)
	 */
	@Override
	public Boolean getBoolean(String id)
	{
		String parameterString = getString(id);
		if(parameterString != null)
			return Boolean.valueOf(parameterString);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getByte(java.lang.String)
	 */
	@Override
	public Byte getByte(String id)
	{
		String parameterString = getString(id);
		if(parameterString != null)
			return Byte.parseByte(parameterString);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getDate(java.lang.String)
	 */
	@Override
	public Date getDate(String id)
	{
		String parameterString = getString(id);
		if(parameterString != null)
		{
			try
			{
				return EnumDateFormat.getDefault().getDateFormat().parse(parameterString);
			}
			catch(ParseException e)
			{
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getDouble(java.lang.String)
	 */
	@Override
	public Double getDouble(String id)
	{
		String parameterString = getString(id);
		if(parameterString != null)
			return Double.parseDouble(parameterString);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getFloat(java.lang.String)
	 */
	@Override
	public Float getFloat(String id)
	{
		String parameterString = getString(id);
		if(parameterString != null)
			return Float.parseFloat(parameterString);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getInteger(java.lang.String)
	 */
	@Override
	public Integer getInteger(String id)
	{
		String parameterString = getString(id);
		if(parameterString != null)
			return Integer.parseInt(parameterString);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getLong(java.lang.String)
	 */
	@Override
	public Long getLong(String id)
	{
		String parameterString = getString(id);
		if(parameterString != null)
			return Long.parseLong(parameterString);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getShort(java.lang.String)
	 */
	@Override
	public Short getShort(String id)
	{
		String parameterString = getString(id);
		if(parameterString != null)
			return Short.parseShort(parameterString);
		return null;
	}
}
