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

import com.syncnapsis.data.dao.WindowDao;
import com.syncnapsis.data.model.Window;
import com.syncnapsis.data.service.WindowManager;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.WebSocketManager;
import com.syncnapsis.websockets.service.rpc.RPCService;

/**
 * A simple WindowManager-Impl
 * 
 * @author ultimate
 */
public class WindowManagerImpl extends GenericManagerImpl<Window, Long> implements WindowManager
{
	/**
	 * The WindowDao to user
	 */
	@SuppressWarnings("unused")
	private WindowDao			windowDao;

	private RPCService			rpcService;

	private WebSocketManager	webSocketManager;

	/**
	 * Standard Constructor
	 * 
	 * @param windowDao - the WindowDao to use
	 * @param rpcService
	 * @param webSocketManager
	 */
	public WindowManagerImpl(WindowDao windowDao, RPCService rpcService, WebSocketManager webSocketManager)
	{
		super(windowDao);
		this.windowDao = windowDao;
		this.rpcService = rpcService;
		this.webSocketManager = webSocketManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.WindowManager#updateWindow(java.lang.Long, int, int, int,
	 * int, double)
	 */
	@Override
	public void updateWindow(Long id, int positionX, int positionY, int width, int height, double rotation)
	{
		Window w = get(id);
		w.setPositionX(positionX);
		w.setPositionY(positionY);
		w.setWidth(width);
		w.setHeight(height);
		w.setRotation(rotation);
		w = save(w);
		
		WindowManager client;
		for(Connection connection: rpcService.getConnections())
		{
			client = getClientManager(connection);
			client.updateWindow(id, positionX, positionY, width, height, rotation);
		}
	}

	@Override
	public void initClientDesktop()
	{
		WindowManager client = getCurrentClientManager();

		List<Window> windowList = getAll();
		for(Window w : windowList)
		{
			client.createWindow(w.getId());
			client.updateWindow(w.getId(), w.getPositionX(), w.getPositionY(), w.getWidth(), w.getHeight(), w.getRotation());
		}
	}

	@Override
	public void createWindow(Long id)
	{
		Window w = new Window();
		w = save(w);
		
		WindowManager client;
		for(Connection connection: rpcService.getConnections())
		{
			client = getClientManager(connection);
			client.createWindow(w.getId());
		}
	}
	
	@Override
	public void removeWindow(Long id)
	{
		remove(get(id));

		WindowManager client;
		for(Connection connection: rpcService.getConnections())
		{
			client = getClientManager(connection);
			client.removeWindow(id);
		}
	}
	
	private WindowManager getCurrentClientManager()
	{
		return getClientManager(webSocketManager.getConnectionProvider().get());
	}

	private WindowManager getClientManager(Connection connection)
	{
		return (WindowManager) rpcService.getClientInstance("windowManager", connection);
	}
}
