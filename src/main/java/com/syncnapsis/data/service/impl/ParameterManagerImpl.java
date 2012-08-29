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
public class ParameterManagerImpl extends GenericManagerImpl<Parameter, String> implements ParameterManager
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
	public String getString(String id)
	{
		return parameterDao.getString(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getBoolean(java.lang.String)
	 */
	@Override
	public Boolean getBoolean(String id)
	{
		return parameterDao.getBoolean(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getByte(java.lang.String)
	 */
	@Override
	public Byte getByte(String id)
	{
		return parameterDao.getByte(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getDate(java.lang.String)
	 */
	@Override
	public Date getDate(String id)
	{
		return parameterDao.getDate(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getDouble(java.lang.String)
	 */
	@Override
	public Double getDouble(String id)
	{
		return parameterDao.getDouble(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getFloat(java.lang.String)
	 */
	@Override
	public Float getFloat(String id)
	{
		return parameterDao.getFloat(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getInteger(java.lang.String)
	 */
	@Override
	public Integer getInteger(String id)
	{
		return parameterDao.getInteger(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getLong(java.lang.String)
	 */
	@Override
	public Long getLong(String id)
	{
		return parameterDao.getLong(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.service.ParameterManager#getShort(java.lang.String)
	 */
	@Override
	public Short getShort(String id)
	{
		return parameterDao.getShort(id);
	}
}
