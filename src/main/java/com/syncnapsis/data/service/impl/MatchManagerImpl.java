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

import com.syncnapsis.data.dao.MatchDao;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.service.MatchManager;

/**
 * Manager-Implementation for access to Match.
 * 
 * @author ultimate
 */
public class MatchManagerImpl extends GenericNameManagerImpl<Match, Long> implements MatchManager
{
	/**
	 * MatchDao for database access
	 */
	protected MatchDao			matchDao;

	/**
	 * Standard Constructor
	 * 
	 * @param matchDao - MatchDao for database access
	 */
	public MatchManagerImpl(MatchDao matchDao)
	{
		super(matchDao);
		this.matchDao = matchDao;
	}
}
