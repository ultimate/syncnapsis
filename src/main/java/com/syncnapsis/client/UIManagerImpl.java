/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.client;

import com.syncnapsis.enums.EnumLocale;

/**
 * Interface representing the client and server UIManager functions
 * 
 * @author ultimate
 */
public class UIManagerImpl extends BaseClientManager implements UIManager
{
	/**
	 * Default Constructor
	 */
	public UIManagerImpl()
	{
		super("uiManager");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.UIManager#reloadLocale()
	 */
	@Override
	public void reloadLocale()
	{
		// nothing to do on the server
		// this is the stub for client method invocation
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.UIManager#selectLocale(com.syncnapsis.enums.EnumLocale)
	 */
	@Override
	public void selectLocale(EnumLocale locale)
	{
		logger.debug("setting locale: " + locale);
		securityManager.getLocaleProvider().set(locale);
		logger.debug("reloading locale on client now!");
		((UIManager) getClientInstance("uiManager", connectionProvider.get())).reloadLocale();
	}
}
