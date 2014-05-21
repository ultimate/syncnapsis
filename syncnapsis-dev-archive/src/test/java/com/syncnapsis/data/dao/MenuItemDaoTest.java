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
package com.syncnapsis.data.dao;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import com.syncnapsis.data.dao.hibernate.MenuItemDaoHibernate;
import com.syncnapsis.data.model.MenuItem;
import com.syncnapsis.enums.EnumMenuItemType;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses( { MenuItemDao.class, MenuItemDaoHibernate.class })
public class MenuItemDaoTest extends BaseDaoTestCase
{
	private MenuItemDao	menuItemDao;

	public void testGetMenuItemInvalid() throws Exception
	{
		logger.debug("testing getMenuItem invalid...");
		try
		{
			menuItemDao.get("algkrabzvsloic");
			fail("'badMenuItemID' found in database, failing test...");
		}
		catch(ObjectNotFoundException e)
		{
			assertNotNull(e);
		}
	}

	@TestCoversMethods( { "get" })
	public void testGetMenuItem() throws Exception
	{
		logger.debug("testing getMenuItem...");
		MenuItem menuItem = menuItemDao.get("ACC");

		assertNotNull(menuItem);
		assertEquals("account", menuItem.getTitleKey());
		assertEquals(EnumMenuItemType.node, menuItem.getType());
	}

	public void testGetChildren() throws Exception
	{
		logger.debug("testing getChildren...");
		MenuItem menuItem;
		String menuId = "MR";

		menuItem = menuItemDao.get(menuId);
		assertNotNull(menuItem);

		List<MenuItem> childrenAll = menuItemDao.getChildren(menuItem.getId(), true);
		assertNotNull(childrenAll);
		logger.debug(childrenAll.size() + " children found");
		assertTrue(childrenAll.size() >= 8);
		for(MenuItem child : childrenAll)
		{
			assertNotNull(child);
			assertEquals(menuItem, child.getParent());
		}

		List<MenuItem> childrenNormal = menuItemDao.getChildren(menuItem.getId(), false);
		assertNotNull(childrenNormal);
		logger.debug(childrenNormal.size() + " children found");
		assertTrue(childrenNormal.size() >= 5);
		for(MenuItem child : childrenNormal)
		{
			assertNotNull(child);
			assertEquals(menuItem, child.getParent());
			assertFalse(child.isAdvancedItem());
		}
	}

	public void testUpdateMenuItem() throws Exception
	{
		logger.debug("testing update menuItem...");
		MenuItem menuItem = menuItemDao.get("MR");

		menuItem.setTitleKey("menu.root2");
		menuItem = menuItemDao.save(menuItem);

		menuItem = menuItemDao.get("MR");
		assertEquals("menu.root2", menuItem.getTitleKey());
	}

	public void testAddAndRemoveMenuItem() throws Exception
	{
		String id = "newMI";
		logger.debug("testing add and remove on menuItem...");
		MenuItem menuItem = new MenuItem();
		menuItem.setId(id);
		menuItem.setTitleKey("title");
		menuItem.setType(EnumMenuItemType.node);

		menuItem = menuItemDao.save(menuItem);
		assertNotNull(menuItem.getId());
		assertEquals(id, menuItem.getId());

		menuItem = menuItemDao.get(menuItem.getId());
		assertEquals(id, menuItem.getId());
		assertEquals("title", menuItem.getTitleKey());
		assertEquals(EnumMenuItemType.node, menuItem.getType());

		assertEquals("deleted", menuItemDao.remove(menuItem));
	}

	public void testMenuItemExists() throws Exception
	{
		logger.debug("testing menuItem exists...");
		boolean b = menuItemDao.exists("MR");
		super.assertTrue(b);
	}

	public void testMenuItemNotExists() throws Exception
	{
		logger.debug("testing menuItem not exists...");
		boolean b = menuItemDao.exists("aaksdjzghfakwe");
		super.assertFalse(b);
	}

	public void testGetAll() throws Exception
	{
		List<MenuItem> menuItems;

		logger.debug("testing getAll()...");
		menuItems = menuItemDao.getAll();
		assertNotNull(menuItems);
		logger.debug(menuItems.size() + " menuItems found");
		assertTrue(menuItems.size() >= 15);
	}
}
