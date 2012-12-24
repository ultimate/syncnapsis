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

import com.syncnapsis.data.dao.AllianceDao;
import com.syncnapsis.data.model.Alliance;
import com.syncnapsis.data.service.AllianceManager;

/**
 * Manager-Implementierung für den Zugriff auf Alliance.
 * 
 * @author ultimate
 */
public class AllianceManagerImpl extends GenericNameManagerImpl<Alliance, Long> implements AllianceManager
{
	/**
	 * AllianceDao für den Datenbankzugriff
	 */
	private AllianceDao	allianceDao;

	/**
	 * Standard-Constructor
	 * @param allianceDao - AllianceDao für den Datenbankzugriff
	 */
	public AllianceManagerImpl(AllianceDao allianceDao)
	{
		super(allianceDao);
		this.allianceDao = allianceDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.AllianceManager#getByEmpire(java.lang.Long)
	 */
	@Override
	public List<Alliance> getByEmpire(Long empireId)
	{
		return allianceDao.getByEmpire(empireId);
	}
}
