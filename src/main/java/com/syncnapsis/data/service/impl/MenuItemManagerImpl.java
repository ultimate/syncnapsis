package com.syncnapsis.data.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.syncnapsis.data.dao.MenuItemDao;
import com.syncnapsis.data.model.MenuItem;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.MenuItemManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.enums.EnumMenuItemDynamicType;
import com.syncnapsis.enums.EnumMenuItemType;
import com.syncnapsis.providers.UserProvider;
import com.syncnapsis.ui.menu.MenuItemOptionHandler;
import com.syncnapsis.utils.ApplicationContextUtil;
import com.syncnapsis.utils.graphs.GenericTreeModel;

/**
 * Manager-Implementierung für den Zugriff auf MenuItem.
 * 
 * @author ultimate
 */
public class MenuItemManagerImpl extends GenericManagerImpl<MenuItem, String> implements MenuItemManager
{
	// TODO configure as property
	protected UserProvider				userProvider	= ApplicationContextUtil.getBean(UserProvider.class);

	/**
	 * MenuItemDao für den Datenbankzugriff
	 */
	private MenuItemDao					menuItemDao;
	/**
	 * UserManager für den Datenbankzugriff
	 */
	private UserManager					userManager;

	/**
	 * Die Liste der MenuItemOptionHandler
	 */
	private List<MenuItemOptionHandler>	optionHandlers;

	/**
	 * Standard Constructor, der die Beans speichert.
	 * 
	 * @param menuItemDao - MenuItemDao für den Datenbankzugriff
	 * @param userManager - UserManager für den Datenbankzugriff
	 */
	public MenuItemManagerImpl(MenuItemDao menuItemDao, UserManager userManager)
	{
		super(menuItemDao);
		this.menuItemDao = menuItemDao;
		this.userManager = userManager;

		this.optionHandlers = new LinkedList<MenuItemOptionHandler>();
	}

	/**
	 * Setzt eine Liste aus MenuItemOptionHandler-Klassen für die Behandlung der Erstellung von
	 * Submenüs
	 * 
	 * @param optionHandlerClasses - die Liste der Klassen
	 * @throws ClassNotFoundException - wenn die Klasse ungültig ist
	 */
	@SuppressWarnings("unchecked")
	public void setOptionHandlerClasses(List<String> optionHandlerClasses) throws ClassNotFoundException
	{
		Class<MenuItemOptionHandler> cls;
		for(String optionHandlerClass : optionHandlerClasses)
		{
			cls = (Class<MenuItemOptionHandler>) Class.forName(optionHandlerClass);
			try
			{
				addOptionHandler(cls.newInstance());
			}
			catch(InstantiationException e)
			{
				logger.error("InstantiationException: " + e.getMessage());
			}
			catch(IllegalAccessException e)
			{
				logger.error("IllegalAccessException: " + e.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.data.service.MenuItemManager#addOptionHandler(com.syncnapsis.ui.menu.MenuItemOptionHandler)
	 */
	@Override
	public void addOptionHandler(MenuItemOptionHandler optionHandler)
	{
		this.optionHandlers.add(optionHandler);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MenuItemManager#getOptionHandlers()
	 */
	@Override
	public List<MenuItemOptionHandler> getOptionHandlers()
	{
		return Collections.unmodifiableList(this.optionHandlers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MenuItemManager#attachChildren(com.syncnapsis.data.model.MenuItem,
	 * boolean)
	 */
	@Override
	public void attachChildren(MenuItem parent, boolean includeAdvanced)
	{
		List<MenuItem> children = getChildren(parent.getId(), includeAdvanced);
		List<MenuItem> processedChildren = new ArrayList<MenuItem>();
		for(MenuItem menuItem : children)
		{
			if(parent != menuItem.getParent())
				menuItem.setParent(parent);
			if(menuItem.getType().equals(EnumMenuItemType.separator))
			{
				processedChildren.add(menuItem);
			}
			else
			{
				if(menuItem.getDynamicType() == null)
				{
					processedChildren.add(menuItem);
				}
				else
				{
					if(menuItem.getDynamicType().equals(EnumMenuItemDynamicType.current))
					{
						for(MenuItemOptionHandler optionHandler : this.optionHandlers)
						{
							if(optionHandler.applies(menuItem.getDynamicSubType()))
								processedChildren.addAll(optionHandler.createCurrent(menuItem));
						}
					}
					else if(menuItem.getDynamicType().equals(EnumMenuItemDynamicType.list))
					{
						processedChildren.add(menuItem);
					}
					else if(menuItem.getDynamicType().equals(EnumMenuItemDynamicType.choice))
					{
						if(parent.getParameterValue() != null)
						{
							menuItem.setParameterValue(parent.getParameterValue());
							processedChildren.add(menuItem);
						}
					}
					else if(menuItem.getDynamicType().equals(EnumMenuItemDynamicType.option)
							|| menuItem.getDynamicType().equals(EnumMenuItemDynamicType.option_list))
					{
						for(MenuItemOptionHandler optionHandler : this.optionHandlers)
						{
							if(optionHandler.applies(menuItem.getParent().getDynamicSubType()))
								processedChildren.addAll(optionHandler.createOptions(menuItem));
						}
					}
				}
			}
		}

		parent.setChildren(processedChildren);
		for(MenuItem menuItem : parent.getChildren())
			attachChildren(menuItem, includeAdvanced);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MenuItemManager#get(java.lang.String, boolean)
	 */
	@Override
	public MenuItem get(String id, boolean evict)
	{
		return menuItemDao.get(id, evict);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MenuItemManager#getChildren(java.lang.String, boolean)
	 */
	@Override
	public List<MenuItem> getChildren(String parentId, boolean includeAdvanced)
	{
		return menuItemDao.getChildren(parentId, includeAdvanced);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MenuItemManager#getFullMenu(java.lang.String, boolean)
	 */
	@Override
	public MenuItem getFullMenu(String id, boolean includeAdvanced)
	{
		MenuItem root = menuItemDao.get(id, includeAdvanced);
		attachChildren(root, includeAdvanced);
		return root;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MenuItemManager#getMenuAsTree(java.lang.String)
	 */
	@Override
	public GenericTreeModel<MenuItem> getMenuAsTree(String key)
	{
//		User user = userManager.get(sessionProvider.getCurrentUserId());
		User user = userProvider.get();

		MenuItem rootItem = getFullMenu(key, user.isUsingAdvancedMenu());

		return new GenericTreeModel<MenuItem>(rootItem);
	}
}
