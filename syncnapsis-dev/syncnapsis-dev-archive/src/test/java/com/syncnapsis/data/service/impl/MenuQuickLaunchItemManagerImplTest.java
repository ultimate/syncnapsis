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

import java.util.ArrayList;
import java.util.List;

import com.syncnapsis.data.dao.MenuQuickLaunchItemDao;
import com.syncnapsis.data.model.MenuQuickLaunchItem;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.MenuItemManager;
import com.syncnapsis.data.service.MenuQuickLaunchItemManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;

@TestCoversClasses( { MenuQuickLaunchItemManager.class, MenuQuickLaunchItemManagerImpl.class })
public class MenuQuickLaunchItemManagerImplTest extends GenericManagerImplTestCase<MenuQuickLaunchItem, Long, MenuQuickLaunchItemManager, MenuQuickLaunchItemDao>
{
	private MenuItemManager				menuItemManager;
	private MenuQuickLaunchItemManager	menuQuickLaunchItemManager;
	private UserManager					userManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new MenuQuickLaunchItem());
		setDaoClass(MenuQuickLaunchItemDao.class);
		setMockDao(mockContext.mock(MenuQuickLaunchItemDao.class));
		setMockManager(new MenuQuickLaunchItemManagerImpl(mockDao, menuItemManager, userManager));
	}

	public void testGetByUser() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByUser", new ArrayList<MenuQuickLaunchItem>(), userManager.getByName("user1").getId());
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	@TestCoversMethods({"*QuickLaunchItem"})
	public void testAddMoveRemove() throws Exception
	{
		List<MenuQuickLaunchItem> menu;
		
		String id1 = "ACC";
		String id2 = "PROF";
		String id3 = "SET";
		String id4 = "SEC";
		
		User user = userManager.getByName("admin");
		
		menu = menuQuickLaunchItemManager.addQuickLaunchItem(user.getId(), id1, 0);
		assertNotNull(menu);
		assertEquals(1, menu.size());
		assertEquals(id1, menu.get(0).getMenuItem().getId());
		
		menu = menuQuickLaunchItemManager.addQuickLaunchItem(user.getId(), id2, 0);
		assertNotNull(menu);
		assertEquals(2, menu.size());
		assertEquals(id2, menu.get(0).getMenuItem().getId());
		assertEquals(id1, menu.get(1).getMenuItem().getId());
		
		menu = menuQuickLaunchItemManager.addQuickLaunchItem(user.getId(), id3, 1);
		assertNotNull(menu);
		assertEquals(3, menu.size());
		assertEquals(id2, menu.get(0).getMenuItem().getId());
		assertEquals(id3, menu.get(1).getMenuItem().getId());
		assertEquals(id1, menu.get(2).getMenuItem().getId());
		
		menu = menuQuickLaunchItemManager.addQuickLaunchItem(user.getId(), id4, 3);
		assertNotNull(menu);
		assertEquals(4, menu.size());
		assertEquals(id2, menu.get(0).getMenuItem().getId());
		assertEquals(id3, menu.get(1).getMenuItem().getId());
		assertEquals(id1, menu.get(2).getMenuItem().getId());
		assertEquals(id4, menu.get(3).getMenuItem().getId());
		
		menu = menuQuickLaunchItemManager.moveQuickLaunchItem(user.getId(), 1, 3);
		assertNotNull(menu);
		assertEquals(4, menu.size());
		assertEquals(id2, menu.get(0).getMenuItem().getId());
		assertEquals(id1, menu.get(1).getMenuItem().getId());
		assertEquals(id4, menu.get(2).getMenuItem().getId());
		assertEquals(id3, menu.get(3).getMenuItem().getId());
		
		menu = menuQuickLaunchItemManager.moveQuickLaunchItem(user.getId(), 2, 0);
		assertNotNull(menu);
		assertEquals(4, menu.size());
		assertEquals(id4, menu.get(0).getMenuItem().getId());
		assertEquals(id2, menu.get(1).getMenuItem().getId());
		assertEquals(id1, menu.get(2).getMenuItem().getId());
		assertEquals(id3, menu.get(3).getMenuItem().getId());
		
		// test some invalid cases...
		try
		{
			menu = menuQuickLaunchItemManager.moveQuickLaunchItem(user.getId(), -1, 0);
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		try
		{
			menu = menuQuickLaunchItemManager.moveQuickLaunchItem(user.getId(), 4, 0);
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		try
		{
			menu = menuQuickLaunchItemManager.removeQuickLaunchItem(user.getId(), -1);
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		try
		{
			menu = menuQuickLaunchItemManager.removeQuickLaunchItem(user.getId(), 4);
			fail("Expected Exception not occurred!");
		}
		catch(IllegalArgumentException e)
		{
			assertNotNull(e);
		}
		
		menu = menuQuickLaunchItemManager.removeQuickLaunchItem(user.getId(), 1);
		assertNotNull(menu);
		assertEquals(3, menu.size());
		assertEquals(id4, menu.get(0).getMenuItem().getId());
		assertEquals(id1, menu.get(1).getMenuItem().getId());
		assertEquals(id3, menu.get(2).getMenuItem().getId());
		
		menu = menuQuickLaunchItemManager.removeQuickLaunchItem(user.getId(), 0);
		assertNotNull(menu);
		assertEquals(2, menu.size());
		assertEquals(id1, menu.get(0).getMenuItem().getId());
		assertEquals(id3, menu.get(1).getMenuItem().getId());
		
		menu = menuQuickLaunchItemManager.removeQuickLaunchItem(user.getId(), 1);
		assertNotNull(menu);
		assertEquals(1, menu.size());
		assertEquals(id1, menu.get(0).getMenuItem().getId());
		
		menu = menuQuickLaunchItemManager.removeQuickLaunchItem(user.getId(), 0);
		assertNotNull(menu);
		assertEquals(0, menu.size());
	}
}
