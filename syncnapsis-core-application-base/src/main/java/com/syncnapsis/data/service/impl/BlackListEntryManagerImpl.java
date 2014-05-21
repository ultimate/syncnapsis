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

import java.util.List;

import com.syncnapsis.data.dao.BlackListEntryDao;
import com.syncnapsis.data.model.BlackList;
import com.syncnapsis.data.model.BlackListEntry;
import com.syncnapsis.data.service.BlackListEntryManager;
import com.syncnapsis.data.service.BlackListManager;
import com.syncnapsis.security.validation.BlackListStringValidator;

/**
 * Manager-Implementation for access to BlackListEntry.
 * 
 * @author ultimate
 */
public class BlackListEntryManagerImpl extends GenericManagerImpl<BlackListEntry, Long> implements BlackListEntryManager
{
	/**
	 * BlackListEntryDao for database access
	 */
	protected BlackListEntryDao	blackListEntryDao;
	
	/**
	 * The BlackListManager
	 */
	protected BlackListManager blackListManager;

	/**
	 * Standard Constructor
	 * 
	 * @param blackListEntryDao - BlackListEntryDao for database access
	 * @param blackListManager - the BlackListManager
	 */
	public BlackListEntryManagerImpl(BlackListEntryDao blackListEntryDao, BlackListManager blackListManager)
	{
		super(blackListEntryDao);
		this.blackListEntryDao = blackListEntryDao;
		this.blackListManager = blackListManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.BlackListEntryManager#getByBlackList(java.lang.Long)
	 */
	@Override
	public List<BlackListEntry> getByBlackList(Long blackListId)
	{
		return blackListEntryDao.getByBlackList(blackListId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.BlackListEntryManager#getValuesByBlackListName(java.lang.String)
	 */
	@Override
	public List<String> getValuesByBlackListName(String blackListName)
	{
		return blackListEntryDao.getValuesByBlackListName(blackListName);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.BlackListEntryManager#addValuesToBlackList(java.lang.String,
	 * java.util.List)
	 */
	@Override
	public int addValuesToBlackList(String blackListName, List<String> values)
	{
		BlackList blackList = blackListManager.getByName(blackListName);
		List<String> existingValues = getValuesByBlackListName(blackListName);
		
		// create a temporary validator for being able to check 
		//   wether the given values are already excluded
		// therefore we are using the fact, that the black-list performs 
		//   strict or non-strict checks for us so we know if it is
		//   necessary to add the value to the black-list
		// of course we could check it here directly, but why should we
		//   reimplement that...
		BlackListStringValidator validator = new BlackListStringValidator();
		validator.setBlackList(existingValues);
		validator.setStrict(blackList.isStrict());
		
		BlackListEntry entry;
		int entriesCreated = 0;
		for(String value: values)
		{
			// if the value is already invalid (by other entries),
			// we do not need to add it to the black-list
			if(!validator.isValid(value))
				continue;
			
			entry = new BlackListEntry();
			entry.setBlackList(blackList);
			entry.setValue(value);
			
			entry = save(entry);
			
			entriesCreated++;
		}
		return entriesCreated;
	}
}
