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
