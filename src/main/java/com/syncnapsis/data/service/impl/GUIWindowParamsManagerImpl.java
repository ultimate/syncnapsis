package com.syncnapsis.data.service.impl;

import com.syncnapsis.data.dao.GUIWindowParamsDao;
import com.syncnapsis.data.model.GUIWindowParams;
import com.syncnapsis.data.service.GUIWindowParamsManager;

/**
 * Manager-Implementierung für den Zugriff auf GUIWindowParams.
 * 
 * @author ultimate
 */
public class GUIWindowParamsManagerImpl extends GenericManagerImpl<GUIWindowParams, Long> implements GUIWindowParamsManager
{
	/**
	 * GUIWindowParamsDao für den Datenbankzugriff
	 */
	@SuppressWarnings("unused")
	private GUIWindowParamsDao guiWindowParamsDao;
	
	/**
	 * Standard-Constructor
	 * @param guiWindowParamsDao - GUIWindowParamsDao für den Datenbankzugriff
	 */
	public GUIWindowParamsManagerImpl(GUIWindowParamsDao guiWindowParamsDao)
	{
		super(guiWindowParamsDao);
		this.guiWindowParamsDao = guiWindowParamsDao;
	}
}
