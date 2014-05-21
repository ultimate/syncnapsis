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
package com.syncnapsis.security.validation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.data.model.BlackList;
import com.syncnapsis.data.model.BlackListEntry;
import com.syncnapsis.data.service.BlackListEntryManager;
import com.syncnapsis.data.service.BlackListManager;

/**
 * A database based black-list validator for Strings.<br>
 * The Validator will use the black-list provided by a {@link BlackListManager} with a given
 * name. Furthermore black-list parameters (e.g. strict) will be determined from the
 * {@link BlackList} and can therefore not be set.
 * 
 * @see BlackList
 * @see BlackListManager
 * @see BlackListEntry
 * @see BlackListEntryManager
 * 
 * @author ultimate
 */
public class DBBlackListStringValidator extends BlackListStringValidator implements InitializingBean
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());
	/**
	 * The BlackListManager
	 */
	protected BlackListManager			blackListManager;
	/**
	 * The BlackListEntryManager
	 */
	protected BlackListEntryManager		blackListEntryManager;

	/**
	 * The name of the black-list to use
	 */
	protected String					blackListName;

	/**
	 * Default Constructor
	 */
	public DBBlackListStringValidator()
	{
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(blackListManager, "blackListManager must not be null!");
		Assert.notNull(blackListEntryManager, "blackListEntryManager must not be null!");
		Assert.notNull(blackListName, "blackListName must not be null!");
		if(blackListManager.getByName(blackListName) == null)
			logger.warn("no black-list with the given name found!");
	}

	/**
	 * The BlackListManager
	 * 
	 * @return blackListManager
	 */
	public BlackListManager getBlackListManager()
	{
		return blackListManager;
	}

	/**
	 * The BlackListManager
	 * 
	 * @param blackListManager - the BlackListManager
	 */
	public void setBlackListManager(BlackListManager blackListManager)
	{
		this.blackListManager = blackListManager;
	}

	/**
	 * The BlackListEntryManager
	 * 
	 * @return blackListEntryManager
	 */
	public BlackListEntryManager getBlackListEntryManager()
	{
		return blackListEntryManager;
	}

	/**
	 * The BlackListEntryManager
	 * 
	 * @param blackListEntryManager - the BlackListEntryManager
	 */
	public void setBlackListEntryManager(BlackListEntryManager blackListEntryManager)
	{
		this.blackListEntryManager = blackListEntryManager;
	}

	/**
	 * The name of the black-list to use
	 * 
	 * @return blackListName
	 */
	public String getBlackListName()
	{
		return blackListName;
	}

	/**
	 * The name of the black-list to use
	 * 
	 * @param blackListName - the name
	 */
	public void setBlackListName(String blackListName)
	{
		this.blackListName = blackListName;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.validation.BlackListValidator#getBlackList()
	 */
	@Override
	public List<String> getBlackList()
	{
		return blackListEntryManager.getValuesByBlackListName(blackListName);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.validation.BlackListValidator#setBlackList(java.util.List)
	 */
	@Override
	public void setBlackList(List<String> blackList)
	{
		throw new IllegalArgumentException("The black-list cannot be set in this validator!");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.validation.BlackListStringValidator#isStrict()
	 */
	@Override
	public boolean isStrict()
	{
		BlackList blackList = blackListManager.getByName(blackListName);
		if(blackList != null)
			return blackList.isStrict();
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.validation.BlackListStringValidator#setStrict(boolean)
	 */
	@Override
	public void setStrict(boolean strict)
	{
		throw new IllegalArgumentException("The strict flag cannot be set in this validator!");
	}
}
