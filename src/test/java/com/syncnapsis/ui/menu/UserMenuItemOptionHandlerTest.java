package com.syncnapsis.ui.menu;

import com.syncnapsis.tests.BaseDaoTestCase;

//@TestCoversClasses({ MenuItemOptionHandler.class, UserMenuItemOptionHandler.class })
public class UserMenuItemOptionHandlerTest extends BaseDaoTestCase
{
//	private PlayerManager		userManager;
//	private MenuItemManager	menuItemManager;
//
//	public void testGetLabel() throws Exception
//	{
//		Player user = userManager.getByName("user1");
//		MenuItem menuItem = menuItemManager.get("CHASIT");
//		menuItem.setParameterValueLong(user.getId());
//
//		MenuItemOptionHandler h = new UserMenuItemOptionHandler();
//		assertEquals(user.getUsername(), h.getLabel(menuItem));
//	}
//
//	public void testApplies() throws Exception
//	{
//		MenuItemOptionHandler h = new EmpireMenuItemOptionHandler();
//		assertTrue(h.applies(EmpireMenuItemOptionHandler.DYNAMIC_SUB_TYPE));
//		assertFalse(h.applies("other"));
//	}
//
//	public void testCreateOptions() throws Exception
//	{
//		Player user = userManager.getByName("user2");
//		List<Player> sitted = user.getSitted();
//		sessionProvider.get().setAttribute(BaseGameConstants.SESSION_USER_KEY, user.getId());
//
//		MenuItem menuItem = menuItemManager.get("CHASIT");
//
//		MenuItemOptionHandler h = new UserMenuItemOptionHandler();
//
//		List<MenuItem> entries = h.createOptions(menuItem);
//		assertNotNull(entries);
//		assertEquals(sitted.size(), entries.size());
//
//		boolean found;
//		for(Player s : sitted)
//		{
//			found = false;
//			for(MenuItem mi : entries)
//			{
//				if(mi.getParameterValueLong().equals(s.getId()))
//				{
//					found = true;
//					break;
//				}
//			}
//			assertTrue(found);
//		}
//	}
//
//	public void testCreateCurrent() throws Exception
//	{
//		Player user = userManager.getByName("user1");
//		sessionProvider.get().setAttribute(BaseGameConstants.SESSION_USER_KEY, user.getId());
//
//		MenuItem menuItem = menuItemManager.get("CHA_OPT");
//
//		MenuItemOptionHandler h = new UserMenuItemOptionHandler();
//
//		List<MenuItem> entries = h.createCurrent(menuItem);
//		assertNotNull(entries);
//		assertEquals(1, entries.size());
//
//		assertEquals(user.getId(), entries.get(0).getParameterValue());
//	}
}
