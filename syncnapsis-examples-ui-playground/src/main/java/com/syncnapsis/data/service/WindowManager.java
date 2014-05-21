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
package com.syncnapsis.data.service;

import com.syncnapsis.data.model.Window;
import com.syncnapsis.data.service.GenericManager;

/**
 * A generic manager for Windows
 * 
 * @author ultimate
 */
public interface WindowManager extends GenericManager<Window, Long>
{
	/**
	 * Update a Window
	 * 
	 * @param id
	 * @param positionX
	 * @param positionY
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public void updateWindow(Long id, int positionX, int positionY, int width, int height, double rotation);

	/**
	 * 
	 * @param id
	 */
	public void createWindow(Long id);
	
	/**
	 * 
	 * @param id
	 */
	public void removeWindow(Long id);
	
	/**
	 * Initiate a Client Desktop by creating and updating all available windows
	 */
	public void initClientDesktop();
	
	// TODO functions
}
