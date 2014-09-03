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

import com.syncnapsis.data.dao.GUIActionDao;
import com.syncnapsis.data.model.GUIAction;
import com.syncnapsis.data.service.GUIActionManager;

/**
 * Manager-Implementierung f�r den Zugriff auf GUIAction.
 * 
 * @author ultimate
 */
public class GUIActionManagerImpl extends GenericNameManagerImpl<GUIAction, Long> implements GUIActionManager
{
	/**
	 * GUIActionDao f�r den Datenbankzugriff
	 */
	private GUIActionDao guiActionDao;
	
	/**
	 * Standard-Constructor
	 * @param guiActionDao - GUIActionDao f�r den Datenbankzugriff
	 */
	public GUIActionManagerImpl(GUIActionDao guiActionDao)
	{
		super(guiActionDao);
		this.guiActionDao = guiActionDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.GUIActionManager#getByWindowId(java.lang.String)
	 */
	@Override
	public GUIAction getByWindowId(String windowId)
	{
		return guiActionDao.getByWindowId(windowId);
	}
}
