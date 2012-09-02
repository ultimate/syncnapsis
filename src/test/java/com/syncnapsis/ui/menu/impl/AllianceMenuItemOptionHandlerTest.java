package com.syncnapsis.ui.menu.impl;

import com.syncnapsis.tests.BaseDaoTestCase;

public class AllianceMenuItemOptionHandlerTest extends BaseDaoTestCase
{
//	private SessionProviderExtension	sessionProvider;
//	private EmpireManager				empireManager;
//	private AllianceManager				allianceManager;
//	private MenuItemManager				menuItemManager;
//
//	public void testGetLabel() throws Exception
//	{
//		Alliance alliance = allianceManager.getByName("alliance1");
//		MenuItem menuItem = menuItemManager.get("ALL_OPT");
//		menuItem.setParameterValueLong(alliance.getId());
//
//		MenuItemOptionHandler h = new AllianceMenuItemOptionHandler();
//		assertEquals(alliance.getShortName(), h.getLabel(menuItem));
//
//		sessionProvider.get().setAttribute(SecurityConstants.SESSION_EMPIRE_KEY, alliance.getId());
//
//		assertEquals(alliance.getShortName(), h.getLabel(menuItem));
//	}
//
//	public void testApplies() throws Exception
//	{
//		MenuItemOptionHandler h = new AllianceMenuItemOptionHandler();
//		assertTrue(h.applies(AllianceMenuItemOptionHandler.DYNAMIC_SUB_TYPE));
//		assertFalse(h.applies("other"));
//	}
//
//	public void testCreateOptions() throws Exception
//	{
//		Empire empire = empireManager.getByName("emp10");
//		List<Alliance> alliances = allianceManager.getByEmpire(empire.getId());
//		sessionProvider.get().setAttribute(SecurityConstants.SESSION_USER_KEY, empire.getUser().getId());
//		sessionProvider.get().setAttribute(SecurityConstants.SESSION_EMPIRE_KEY, empire.getId());
//
//		MenuItem menuItem = menuItemManager.get("ALL_OPT");
//
//		MenuItemOptionHandler h = new AllianceMenuItemOptionHandler();
//
//		List<MenuItem> entries = h.createOptions(menuItem);
//		assertNotNull(entries);
//		assertEquals(alliances.size() == 0 ? 1 : alliances.size(), entries.size());
//
//		boolean found;
//		for(Alliance a : alliances)
//		{
//			found = false;
//			for(MenuItem mi : entries)
//			{
//				if(mi.getParameterValueLong().equals(a.getId()))
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
//		MenuItem menuItem = menuItemManager.get("CHA_OPT");
//
//		MenuItemOptionHandler h = new AllianceMenuItemOptionHandler();
//
//		List<MenuItem> entries = h.createCurrent(menuItem);
//		assertNotNull(entries);
//		assertEquals(1, entries.size());
//
//		assertNull(entries.get(0).getParameterValue());
//	}
}
