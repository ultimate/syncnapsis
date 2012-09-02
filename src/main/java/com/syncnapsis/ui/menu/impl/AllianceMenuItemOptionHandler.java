package com.syncnapsis.ui.menu.impl;


/**
 * MenuItemOptionHandler für Allianzen
 * 
 * @author ultimate
 */
//public class AllianceMenuItemOptionHandler implements MenuItemOptionHandler
//{
//	protected SessionProviderExtension	sessionProvider		= (SessionProviderExtension) ApplicationContextUtil.getBean(SessionProvider.class);
//	/**
//	 * Konstante für MenuItem.dynamicSubType
//	 */
//	public static final String			DYNAMIC_SUB_TYPE	= "alliance";
//
//	/*
//	 * (non-Javadoc)
//	 * @see com.syncnapsis.ui.menu.MenuItemOptionHandler#getLabel(com.syncnapsis.data.model.MenuItem)
//	 */
//	@Override
//	public String getLabel(MenuItem menuItem)
//	{
//		if(menuItem.getParameterValueLong() != null)
//		{
//			Alliance alliance = ApplicationContextUtil.getBean(AllianceManager.class).get(menuItem.getParameterValueLong());
//			return alliance.getShortName();
//		}
//		return null;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see com.syncnapsis.ui.menu.MenuItemOptionHandler#applies(java.lang.String)
//	 */
//	@Override
//	public boolean applies(String dynamicSubType)
//	{
//		return DYNAMIC_SUB_TYPE.equals(dynamicSubType);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see com.syncnapsis.menu.MenuItemOptionHandler#createOptions(com.syncnapsis.data.model.MenuItem)
//	 */
//	@Override
//	public List<MenuItem> createOptions(MenuItem menuItem)
//	{
//		List<MenuItem> processedChildren = new ArrayList<MenuItem>();
//		MenuItem tmpItem;
//		Long currentEmpireId = sessionProvider.getCurrentEmpireId();
//		if(currentEmpireId != null)
//		{
//			List<Alliance> allianceList = ApplicationContextUtil.getBean(AllianceManager.class).getByEmpire(currentEmpireId);
//			if(allianceList != null && allianceList.size() != 0)
//			{
//				for(Alliance alliance : allianceList)
//				{
//					tmpItem = menuItem.clone();
//					tmpItem.setParameterValue(alliance.getId());
//					processedChildren.add(tmpItem);
//				}
//			}
//			else
//			{
//				menuItem.setDisabled(true);
//				processedChildren.add(menuItem);
//			}
//		}
//		else
//		{
//			menuItem.setDisabled(true);
//			processedChildren.add(menuItem);
//		}
//		return processedChildren;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see com.syncnapsis.ui.menu.MenuItemOptionHandler#createCurrent(com.syncnapsis.data.model.MenuItem)
//	 */
//	@Override
//	public List<MenuItem> createCurrent(MenuItem menuItem)
//	{
//		// does nothing...
//		List<MenuItem> processedChildren = new ArrayList<MenuItem>();
//		processedChildren.add(menuItem);
//		return processedChildren;
//	}
//}
