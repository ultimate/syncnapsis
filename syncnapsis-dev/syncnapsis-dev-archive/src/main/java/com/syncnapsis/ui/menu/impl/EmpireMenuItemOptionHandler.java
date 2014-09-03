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
package com.syncnapsis.ui.menu.impl;

import java.util.ArrayList;
import java.util.List;

import com.syncnapsis.constants.GameBaseConstants;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.MenuItem;
import com.syncnapsis.data.service.EmpireManager;
import com.syncnapsis.enums.EnumMenuItemDynamicType;
import com.syncnapsis.providers.EmpireProvider;
import com.syncnapsis.providers.UserProvider;
import com.syncnapsis.ui.menu.MenuItemOptionHandler;
import com.syncnapsis.utils.ApplicationContextUtil;
import com.syncnapsis.utils.SortUtil;

/**
 * MenuItemOptionHandler f�r Imperien
 * 
 * @author ultimate
 */
//public class EmpireMenuItemOptionHandler implements MenuItemOptionHandler
//{
//	// TODO fix
//	protected EmpireProvider empireProvider = ApplicationContextUtil.getBean(EmpireProvider.class);
//	protected UserProvider userProvider = ApplicationContextUtil.getBean(UserProvider.class);
//
//	/**
//	 * Konstante f�r MenuItem.dynamicSubType
//	 */
//	public static final String			DYNAMIC_SUB_TYPE	= "empire";
//
//	/**
//	 * Konstante f�r den Title-Key "empires.current"
//	 */
//	public static final String			CURRENT_TITLE_KEY	= "empire.current";
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
//			Empire empire = ApplicationContextUtil.getBean(EmpireManager.class).get(menuItem.getParameterValueLong());
//
//			if(menuItem.getDynamicType().equals(EnumMenuItemDynamicType.option)
//					|| menuItem.getDynamicType().equals(EnumMenuItemDynamicType.option_list))
//			{
//				// TODO selected
//				// if(empire.getId().equals(SessionProvider.getCurrentEmpireId()))
//				// return empire.getShortName() + " " + LabelProvider.getText("menu.selection");
//				return empire.getShortName();
//			}
//			else if(menuItem.getDynamicType().equals(EnumMenuItemDynamicType.current) && menuItem.getTitleKey().equals(CURRENT_TITLE_KEY))
//			{
//				return empire.getShortName();
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
//
//		// es z�hlt hier nicht immer der echte aktuelle User, da um den Besitzer der Imperien geht,
//		// und das kann auch ein gesitteter sein!
//		Long currentUserId = sessionProvider.getCurrentUserId();
//		if(menuItem.getParent().getParameterName() != null && menuItem.getParent().getParameterName().equals(BaseGameConstants.REQUEST_USER_ID_KEY))
//			currentUserId = menuItem.getParent().getParameterValueLong();
//		Long currentEmpireId = sessionProvider.getCurrentEmpireId();
//
//		if(currentUserId != null)
//		{
//			List<Empire> empireList = ApplicationContextUtil.getBean(EmpireManager.class).getByUser(currentUserId);
//			SortUtil.sortListAscending(empireList, "getShortName");
//			if(empireList != null && empireList.size() != 0)
//			{
//				for(Empire empire : empireList)
//				{
//					tmpItem = menuItem.clone();
//					if(tmpItem.getParameterName() == null || tmpItem.equals(""))
//						tmpItem.setParameterName(BaseGameConstants.REQUEST_EMPIRE_ID_KEY);
//					tmpItem.setParameterValue(empire.getId());
//					tmpItem.setDisabled(empire.getId().equals(currentEmpireId));
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
//		Long currentEmpireId = sessionProvider.getCurrentEmpireId();
//		if(currentEmpireId != null)
//		{
//			if(menuItem.getParameterName() == null)
//				menuItem.setParameterName(BaseGameConstants.REQUEST_EMPIRE_ID_KEY);
//			menuItem.setParameterValue(currentEmpireId);
//			processedChildren.add(menuItem);
//		}
//		else if(!EnumMenuItemDynamicType.current.equals(menuItem.getParent().getDynamicType()))
//		{
//			processedChildren.add(menuItem);
//		}
//		return processedChildren;
//	}
//}
