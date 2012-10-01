package com.syncnapsis.ui.menu.impl;

import java.util.ArrayList;
import java.util.List;

import com.syncnapsis.constants.GameBaseConstants;
import com.syncnapsis.data.model.MenuItem;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.service.PlayerManager;
import com.syncnapsis.enums.EnumMenuItemDynamicType;
import com.syncnapsis.providers.UserProvider;
import com.syncnapsis.ui.menu.MenuItemOptionHandler;
import com.syncnapsis.utils.ApplicationContextUtil;
import com.syncnapsis.utils.SortUtil;

/**
 * MenuItemOptionHandler für Benutzer
 * 
 * @author ultimate
 */
//public class UserMenuItemOptionHandler implements MenuItemOptionHandler
//{
//	protected UserProvider userProvider = ApplicationContextUtil.getBean(UserProvider.class);
//
//	/**
//	 * Konstante für MenuItem.dynamicSubType
//	 */
//	public static final String			DYNAMIC_SUB_TYPE	= "user";
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
//			if(!menuItem.getDynamicType().equals(EnumMenuItemDynamicType.current))
//			{
//				Player user = ApplicationContextUtil.getBean(PlayerManager.class).get(menuItem.getParameterValueLong());
//				return user.getUsername();
//			}
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
//	 * @see com.syncnapsis.ui.menu.MenuItemOptionHandler#createOptions(com.syncnapsis.data.model.MenuItem)
//	 */
//	@Override
//	public List<MenuItem> createOptions(MenuItem menuItem)
//	{
//		List<MenuItem> processedChildren = new ArrayList<MenuItem>();
//		MenuItem tmpItem;
//		Long currentUserId = sessionProvider.getCurrentUserId();
//		if(currentUserId != null)
//		{
//			Player user = ApplicationContextUtil.getBean(PlayerManager.class).get(currentUserId);
//			List<Player> userList = user.getSitted();
//			SortUtil.sortListAscending(userList, "getUsername");
//			if(userList != null && userList.size() != 0)
//			{
//				for(Player sitted : userList)
//				{
//					tmpItem = menuItem.clone();
//					if(tmpItem.getParameterName() == null || tmpItem.getParameterName().equals(""))
//						tmpItem.setParameterName(BaseGameConstants.REQUEST_USER_ID_KEY);
//					tmpItem.setParameterValue(sitted.getId());
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
//		List<MenuItem> processedChildren = new ArrayList<MenuItem>();
//		Long currentUserId = sessionProvider.getCurrentUserId();
//		if(currentUserId != null)
//		{
//			if(menuItem.getParameterName() == null)
//				menuItem.setParameterName(BaseGameConstants.REQUEST_USER_ID_KEY);
//			menuItem.setParameterValue(currentUserId);
//			processedChildren.add(menuItem);
//		}
//		else if(!EnumMenuItemDynamicType.current.equals(menuItem.getParent().getDynamicType()))
//		{
//			processedChildren.add(menuItem);
//		}
//		return processedChildren;
//	}
//}
