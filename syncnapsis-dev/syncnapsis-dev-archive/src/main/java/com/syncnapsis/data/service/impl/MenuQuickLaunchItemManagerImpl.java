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

import com.syncnapsis.data.dao.MenuQuickLaunchItemDao;
import com.syncnapsis.data.model.MenuItem;
import com.syncnapsis.data.model.MenuQuickLaunchItem;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.MenuItemManager;
import com.syncnapsis.data.service.MenuQuickLaunchItemManager;
import com.syncnapsis.data.service.UserManager;
import org.springframework.util.Assert;

/**
 * Manager-Implementierung f�r den Zugriff auf MenuQuickLaunchItem.
 * 
 * @author ultimate
 */
public class MenuQuickLaunchItemManagerImpl extends GenericManagerImpl<MenuQuickLaunchItem, Long> implements MenuQuickLaunchItemManager
{
	/**
	 * MenuQuickLaunchItemDao f�r den Datenbankzugriff
	 */
	private MenuQuickLaunchItemDao			menuQuickLaunchItemDao;
	
	/**
	 * MenuItemManager f�r den Datenbankzugriff
	 */
	private MenuItemManager			menuItemManager;
	/**
	 * UserManager f�r den Datenbankzugriff
	 */
	private UserManager					userManager;

	/**
	 * Standard Constructor, der die DAOs speichert.
	 * 
	 * @param menuQuickLaunchItemDao - MenuQuickLaunchItemDao f�r den Datenbankzugriff
	 * @param menuItemManager - MenuItemManager f�r den Datenbankzugriff
	 * @param userManager - UserManager f�r den Datenbankzugriff
	 */
	public MenuQuickLaunchItemManagerImpl(MenuQuickLaunchItemDao menuQuickLaunchItemDao, MenuItemManager menuItemManager, UserManager userManager)
	{
		super(menuQuickLaunchItemDao);
		this.menuQuickLaunchItemDao = menuQuickLaunchItemDao;
		this.menuItemManager = menuItemManager;
		this.userManager = userManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MenuQuickLaunchItemManager#getByUser(java.lang.Long)
	 */
	@Override
	public List<MenuQuickLaunchItem> getByUser(Long userId)
	{
		User user = userManager.get(userId);
		List<MenuQuickLaunchItem> result = menuQuickLaunchItemDao.getByUser(userId);
		if(result != null)
		{
			for(MenuQuickLaunchItem ql : result)
			{
				menuItemManager.attachChildren(ql.getMenuItem(), user.isUsingAdvancedMenu());
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MenuQuickLaunchItemManager#addQuickLaunchItem(java.lang.Long, java.lang.String, int)
	 */
	@Override
	public List<MenuQuickLaunchItem> addQuickLaunchItem(Long userId, String miId, int qlPosTo)
	{
		Assert.notNull(userId, "userId argument is null!");
		Assert.notNull(miId, "miId argument is null!");

		MenuQuickLaunchItem quickLaunchItem;		
		List<MenuQuickLaunchItem> quickLaunchMenu = this.getByUser(userId);

		if(qlPosTo < 0)
			qlPosTo = 0;
		if(qlPosTo > quickLaunchMenu.size())
			qlPosTo = quickLaunchMenu.size();

		MenuItem mi = menuItemManager.get(miId);
		Assert.notNull(mi, "MenuItem is null!");

		for(MenuQuickLaunchItem ql : quickLaunchMenu)
		{
			if(ql.getPosition() < qlPosTo)
				continue;
			else if(ql.getPosition() >= qlPosTo)
			{
				ql.setPosition(ql.getPosition() + 1);
				this.save(ql);
			}
		}

		quickLaunchItem = new MenuQuickLaunchItem();
		quickLaunchItem.setMenuItem(mi);
		quickLaunchItem.setUser(userManager.get(userId));
		quickLaunchItem.setPosition(qlPosTo);
		this.save(quickLaunchItem);

		quickLaunchMenu = this.getByUser(userId);

		return quickLaunchMenu;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MenuQuickLaunchItemManager#moveQuickLaunchItem(java.lang.Long, int, int)
	 */
	@Override
	public List<MenuQuickLaunchItem> moveQuickLaunchItem(Long userId, int qlPosFrom, int qlPosTo)
	{
		Assert.notNull(userId, "userId argument is null!");

		List<MenuQuickLaunchItem> quickLaunchMenu = this.getByUser(userId);

		Assert.isTrue(qlPosFrom < quickLaunchMenu.size(), "Invalid qlPosFrom");
		Assert.isTrue(qlPosFrom >= 0, "Invalid qlPosFrom");

		if(qlPosTo < 0)
			qlPosTo = 0;
		if(qlPosTo > quickLaunchMenu.size())
			qlPosTo = quickLaunchMenu.size();

		if(qlPosTo < qlPosFrom)
		{
			// element an neuer Stelle einf�gen
			quickLaunchMenu.add(qlPosTo, quickLaunchMenu.get(qlPosFrom));
			// altes element l�schen
			quickLaunchMenu.remove(qlPosFrom + 1);
		}
		else if(qlPosTo == qlPosFrom)
			return quickLaunchMenu;
		else
		{
			// element an neuer Stelle einf�gen
			quickLaunchMenu.add(qlPosTo + 1, quickLaunchMenu.get(qlPosFrom));
			// altes element l�schen
			quickLaunchMenu.remove(qlPosFrom);
		}

		int pos = 0;
		for(MenuQuickLaunchItem ql : quickLaunchMenu)
		{
			if(ql.getPosition() != pos)
			{
				ql.setPosition(pos);
				this.save(ql);
			}
			pos++;
		}

		quickLaunchMenu = this.getByUser(userId);

		return quickLaunchMenu;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.MenuQuickLaunchItemManager#removeQuickLaunchItem(java.lang.Long, int)
	 */
	@Override
	public List<MenuQuickLaunchItem> removeQuickLaunchItem(Long userId, int qlPosFrom)
	{
		Assert.notNull(userId, "userId argument is null!");

		List<MenuQuickLaunchItem> quickLaunchMenu = this.getByUser(userId);

		Assert.isTrue(qlPosFrom < quickLaunchMenu.size(), "Invalid qlPosFrom");
		Assert.isTrue(qlPosFrom >= 0, "Invalid qlPosFrom");

		for(MenuQuickLaunchItem ql : quickLaunchMenu)
		{
			if(ql.getPosition() < qlPosFrom)
				continue;
			else if(ql.getPosition() == qlPosFrom)
				this.remove(ql);
			else
			{
				ql.setPosition(ql.getPosition() - 1);
				this.save(ql);
			}
		}

		quickLaunchMenu = this.getByUser(userId);

		return quickLaunchMenu;
	}
}
