package com.syncnapsis.ui.menu;

import com.syncnapsis.tests.BaseDaoTestCase;

//@TestCoversClasses({ MenuItemOptionHandler.class, EmpireMenuItemOptionHandler.class })
public class EmpireMenuItemOptionHandlerTest extends BaseDaoTestCase
{
//	private PlayerManager					userManager;
//	private EmpireManager				empireManager;
//	private MenuItemManager				menuItemManager;
//
//	public void testGetLabel() throws Exception
//	{
//		Empire empire = empireManager.getByName("emp1");
//		MenuItem menuItem = menuItemManager.get("CHA_OPT");
//		menuItem.setParameterValueLong(empire.getId());
//
//		MenuItemOptionHandler h = new EmpireMenuItemOptionHandler();
//		assertEquals(empire.getShortName(), h.getLabel(menuItem));
//
//		sessionProvider.get().setAttribute(BaseGameConstants.SESSION_EMPIRE_KEY, empire.getId());
//
//		// TODO fix
//		// assertEquals(empire.getShortName() + " " + LabelProvider.getText("menu.selection"),
//		// h.getLabel(menuItem));
//
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
//		Player user = userManager.getByName("user1");
//		List<Empire> empires = empireManager.getByUser(user.getId());
//		sessionProvider.get().setAttribute(BaseGameConstants.SESSION_USER_KEY, user.getId());
//		sessionProvider.get().setAttribute(BaseGameConstants.SESSION_EMPIRE_KEY, empires.get(0).getId());
//
//		MenuItem menuItem = menuItemManager.get("CHA_OPT");
//
//		MenuItemOptionHandler h = new EmpireMenuItemOptionHandler();
//
//		List<MenuItem> entries = h.createOptions(menuItem);
//		assertNotNull(entries);
//		assertEquals(empires.size(), entries.size());
//
//		boolean found;
//		for(Empire e : empires)
//		{
//			found = false;
//			for(MenuItem mi : entries)
//			{
//				if(mi.getParameterValueLong().equals(e.getId()))
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
//		List<Empire> empires = empireManager.getByUser(user.getId());
//		sessionProvider.get().setAttribute(BaseGameConstants.SESSION_USER_KEY, user.getId());
//		sessionProvider.get().setAttribute(BaseGameConstants.SESSION_EMPIRE_KEY, empires.get(0).getId());
//
//		MenuItem menuItem = menuItemManager.get("CHA_OPT");
//
//		MenuItemOptionHandler h = new EmpireMenuItemOptionHandler();
//
//		List<MenuItem> entries = h.createCurrent(menuItem);
//		assertNotNull(entries);
//		assertEquals(1, entries.size());
//
//		assertEquals(empires.get(0).getId(), entries.get(0).getParameterValue());
//	}
}
