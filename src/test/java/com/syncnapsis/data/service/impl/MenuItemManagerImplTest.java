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
package com.syncnapsis.data.service.impl;

import java.util.ArrayList;

import com.syncnapsis.data.dao.MenuItemDao;
import com.syncnapsis.data.model.MenuItem;
import com.syncnapsis.data.model.User;
import com.syncnapsis.data.service.MenuItemManager;
import com.syncnapsis.data.service.UserManager;
import com.syncnapsis.providers.UserProvider;
import com.syncnapsis.tests.GenericManagerImplTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;
import com.syncnapsis.utils.graphs.GenericTreeModel;

@TestCoversClasses({ MenuItemManager.class, MenuItemManagerImpl.class })
@TestExcludesMethods({ "setOptionHandlerClasses", "addOptionHandler", "getOptionHandlers" })
public class MenuItemManagerImplTest extends GenericManagerImplTestCase<MenuItem, String, MenuItemManager, MenuItemDao>
{
	private UserProvider	userProvider;
	private UserManager		userManager;
	private MenuItemManager	menuItemManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setEntity(new MenuItem());
		setDaoClass(MenuItemDao.class);
		setMockDao(mockContext.mock(MenuItemDao.class));
		setMockManager(new MenuItemManagerImpl(mockDao, userManager));
	}

	@TestCoversMethods("get")
	public void testGetAndEvict() throws Exception
	{
		MethodCall managerCall = new MethodCall("get", entity, "id", true);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetChildren() throws Exception
	{
		MethodCall managerCall = new MethodCall("getChildren", new ArrayList<MenuItem>(), "id", true);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	@TestCoversMethods({ "getFullMenu", "getMenuAsTree", "attachChildren" })
	public void testGetMenu() throws Exception
	{
		User user = userManager.getByName("user1");
		userProvider.set(user);

		GenericTreeModel<MenuItem> tree = menuItemManager.getMenuAsTree("MR");
		assertNotNull(tree);
		assertNotNull(tree.getRoot());
		assertNotNull(tree.getRoot().getChildren());
		assertTrue(tree.getRoot().getChildren().size() > 0);
	}
}
