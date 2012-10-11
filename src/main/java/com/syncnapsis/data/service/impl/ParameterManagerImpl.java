package com.syncnapsis.data.service.impl;

import java.util.Date;

import com.syncnapsis.data.dao.ParameterDao;
import com.syncnapsis.data.model.Parameter;
import com.syncnapsis.data.service.ParameterManager;

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
	private ParameterDao	parameterDao;

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
	 * @see com.syncnapsis.service.ParameterManager#getString(java.lang.String)
	 */
	@Override
	public String getString(String name)
	{
		return parameterDao.getString(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getBoolean(java.lang.String)
	 */
	@Override
	public Boolean getBoolean(String name)
	{
		return parameterDao.getBoolean(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getByte(java.lang.String)
	 */
	@Override
	public Byte getByte(String name)
	{
		return parameterDao.getByte(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getDate(java.lang.String)
	 */
	@Override
	public Date getDate(String name)
	{
		return parameterDao.getDate(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getDouble(java.lang.String)
	 */
	@Override
	public Double getDouble(String name)
	{
		return parameterDao.getDouble(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getFloat(java.lang.String)
	 */
	@Override
	public Float getFloat(String name)
	{
		return parameterDao.getFloat(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getInteger(java.lang.String)
	 */
	@Override
	public Integer getInteger(String name)
	{
		return parameterDao.getInteger(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getLong(java.lang.String)
	 */
	@Override
	public Long getLong(String name)
	{
		return parameterDao.getLong(name);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getShort(java.lang.String)
	 */
	@Override
	public Short getShort(String name)
	{
		return parameterDao.getShort(name);
	}
}
