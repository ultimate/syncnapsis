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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.providers.ConnectionProvider;
import com.syncnapsis.providers.LocaleProvider;
import com.syncnapsis.websockets.service.rpc.RPCService;

/**
 * Interface representing the client and server UIManager functions
 * 
 * @author ultimate
 */
public class UIManagerImpl implements UIManager
{
	/**
	 * The Logger-Instance
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(UIManagerImpl.class);
	/**
	 * The LocaleProvider for accessing the session locale
	 */
	protected LocaleProvider			localeProvider;

	/**
	 * The ConnectionProvider for accessing the current connection
	 */
	protected ConnectionProvider		connectionProvider;

	/**
	 * The RPCService used to obtain the client instance
	 */
	protected RPCService				rpcService;

	/**
	 * The LocaleProvider for accessing the session locale
	 * 
	 * @return localeProvider
	 */
	public LocaleProvider getLocaleProvider()
	{
		return localeProvider;
	}

	/**
	 * The LocaleProvider for accessing the session locale
	 * 
	 * @param localeProvider - the LocaleProvider
	 */
	public void setLocaleProvider(LocaleProvider localeProvider)
	{
		this.localeProvider = localeProvider;
	}

	/**
	 * The ConnectionProvider for accessing the current connection
	 * 
	 * @return connectionProvider
	 */
	public ConnectionProvider getConnectionProvider()
	{
		return connectionProvider;
	}

	/**
	 * The ConnectionProvider for accessing the current connection
	 * 
	 * @param connectionProvider - the ConnectionProvider
	 */
	public void setConnectionProvider(ConnectionProvider connectionProvider)
	{
		this.connectionProvider = connectionProvider;
	}

	/**
	 * The RPCService used to obtain the client instance
	 * 
	 * @return rpcService
	 */
	public RPCService getRpcService()
	{
		return rpcService;
	}

	/**
	 * The RPCService used to obtain the client instance
	 * 
	 * @param rpcService - the RPCService
	 */
	public void setRpcService(RPCService rpcService)
	{
		this.rpcService = rpcService;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.UIManager#reloadLocale()
	 */
	@Override
	public void reloadLocale()
	{
		// nothing to do on the server
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.client.UIManager#selectLocale(com.syncnapsis.enums.EnumLocale)
	 */
	@Override
	public void selectLocale(EnumLocale locale)
	{
		logger.debug("setting locale: " + locale);
		localeProvider.set(locale);
		logger.debug("reloading locale on client now!");
		((UIManager) rpcService.getClientInstance("uiManager", connectionProvider.get())).reloadLocale();
	}
}
