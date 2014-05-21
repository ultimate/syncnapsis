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
package com.syncnapsis.client;

import org.springframework.util.Assert;

import com.syncnapsis.data.service.MatchManager;

/**
 * {@link ConquestManager} implementation
 * 
 * @author ultimate
 */
public class ConquestManagerImpl extends BaseClientManager implements ConquestManager
{
	/**
	 * The MatchManager
	 */
	protected MatchManager	matchManager;

	/**
	 * Default constructor
	 */
	public ConquestManagerImpl()
	{
		super();
	}

	/**
	 * The MatchManager
	 * 
	 * @return matchManager
	 */
	public MatchManager getMatchManager()
	{
		return matchManager;
	}

	/**
	 * The MatchManager
	 * 
	 * @param matchManager - the MatchManager
	 */
	public void setMatchManager(MatchManager matchManager)
	{
		this.matchManager = matchManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.BaseClientManager#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(matchManager, "matchManager must not be null!");
	}
}
