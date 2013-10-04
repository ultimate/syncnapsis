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

import java.text.ParseException;
import java.util.Date;

import com.syncnapsis.data.dao.ParameterDao;
import com.syncnapsis.data.model.Parameter;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.enums.EnumDateFormat;

/**
 * Manager-Implementierung für den Zugriff auf Parameter.
 * 
 * @author ultimate
 */
public class ParameterManagerImpl extends GenericNameManagerImpl<Parameter, Long> implements ParameterManager
{
	/**
	 * ParameterDao für den Datenbankzugriff
	 */
	protected ParameterDao	parameterDao;

	/**
	 * Standard Constructor, der die DAOs speichert.
	 * 
	 * @param parameterDao - ParameterDao für den Datenbankzugriff
	 */
	public ParameterManagerImpl(ParameterDao parameterDao)
	{
		super(parameterDao);
		this.parameterDao = parameterDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getString(java.lang.String)
	 */
	@Override
	public String getString(String name)
	{
		Parameter parameter = getByName(name);
		if(parameter != null)
			return parameter.getValue();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getBoolean(java.lang.String)
	 */
	@Override
	public Boolean getBoolean(String name)
	{
		String parameterString = getString(name);
		if(parameterString != null)
			return Boolean.valueOf(parameterString);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getByte(java.lang.String)
	 */
	@Override
	public Byte getByte(String name)
	{
		String parameterString = getString(name);
		if(parameterString != null)
			return Byte.parseByte(parameterString);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getDate(java.lang.String)
	 */
	@Override
	public Date getDate(String name)
	{
		String parameterString = getString(name);
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
	public Double getDouble(String name)
	{
		String parameterString = getString(name);
		if(parameterString != null)
			return Double.parseDouble(parameterString);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getFloat(java.lang.String)
	 */
	@Override
	public Float getFloat(String name)
	{
		String parameterString = getString(name);
		if(parameterString != null)
			return Float.parseFloat(parameterString);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getInteger(java.lang.String)
	 */
	@Override
	public Integer getInteger(String name)
	{
		String parameterString = getString(name);
		if(parameterString != null)
			return Integer.parseInt(parameterString);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getLong(java.lang.String)
	 */
	@Override
	public Long getLong(String name)
	{
		String parameterString = getString(name);
		if(parameterString != null)
			return Long.parseLong(parameterString);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.dao.ParameterDao#getShort(java.lang.String)
	 */
	@Override
	public Short getShort(String name)
	{
		String parameterString = getString(name);
		if(parameterString != null)
			return Short.parseShort(parameterString);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.ParameterManager#setString(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public long setString(String name, String value)
	{
		Parameter p = getByName(name);
		if(p == null)
		{
			p = new Parameter();
			p.setName(name);
		}
		p.setValue(value);
		p = save(p);
		return p.getId();
	}
}
