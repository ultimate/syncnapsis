/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import com.syncnapsis.data.dao.AllianceMemberRankDao;
import com.syncnapsis.data.model.AllianceMemberRank;
import com.syncnapsis.data.service.AllianceMemberRankManager;

/**
 * Manager-Implementierung f�r den Zugriff auf AllianceMemberRank.
 * 
 * @author ultimate
 */
public class AllianceMemberRankManagerImpl extends GenericManagerImpl<AllianceMemberRank, Long> implements AllianceMemberRankManager
{
	/**
	 * AllianceMemberRankDao f�r den Datenbankzugriff
	 */
	private AllianceMemberRankDao	allianceMemberRankDao;

	/**
	 * Standard-Constructor
	 * @param allianceMemberRankDao - AllianceMemberRankDao f�r den Datenbankzugriff
	 */
	public AllianceMemberRankManagerImpl(AllianceMemberRankDao allianceMemberRankDao)
	{
		super(allianceMemberRankDao);
		this.allianceMemberRankDao = allianceMemberRankDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.AllianceMemberRankManager#getByEmpire(java.lang.Long)
	 */
	@Override
	public List<AllianceMemberRank> getByEmpire(Long empireId)
	{
		return allianceMemberRankDao.getByEmpire(empireId);
	}
}
