package com.syncnapsis.data.dao;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import com.syncnapsis.data.dao.hibernate.MenuQuickLaunchItemDaoHibernate;
import com.syncnapsis.data.model.MenuQuickLaunchItem;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { MenuQuickLaunchItemDao.class, MenuQuickLaunchItemDaoHibernate.class })
public class MenuQuickLaunchItemDaoTest extends BaseDaoTestCase
{
	private MenuQuickLaunchItemDao	menuQuickLaunchItemDao;
	private UserDao					userDao;
	private MenuItemDao				menuItemDao;

	public void testGetMenuQuickLaunchItemInvalid() throws Exception
	{
		logger.debug("testing getMenuQuickLaunchItem invalid...");
		try
		{
			menuQuickLaunchItemDao.get(-1L);
			fail("'badMenuQuickLaunchItemID' found in database, failing test...");
		}
		catch(ObjectNotFoundException e)
		{
			assertNotNull(e);
		}
	}

	public void testAddAndRemoveMenuQuickLaunchItem() throws Exception
	{
		logger.debug("testing add and remove on menuQuickLaunchItem...");
		MenuQuickLaunchItem menuQuickLaunchItem = new MenuQuickLaunchItem();
		menuQuickLaunchItem.setUser(userDao.get(0L));
		menuQuickLaunchItem.setMenuItem(menuItemDao.get("PROF"));
		menuQuickLaunchItem.setPosition(4);
		menuQuickLaunchItem.setParameterValue(99L);

		menuQuickLaunchItem = menuQuickLaunchItemDao.save(menuQuickLaunchItem);

		long id = menuQuickLaunchItem.getId();

		assertTrue(menuQuickLaunchItemDao.exists(id));
		assertFalse(menuQuickLaunchItemDao.exists(id + 1));

		assertNotNull(id);
		menuQuickLaunchItem = menuQuickLaunchItemDao.get(menuQuickLaunchItem.getId());
		assertEquals(userDao.get(0L), menuQuickLaunchItem.getUser());
		assertEquals(menuItemDao.get("PROF"), menuQuickLaunchItem.getMenuItem());
		assertEquals(4, menuQuickLaunchItem.getPosition());
		assertEquals(99L, menuQuickLaunchItem.getParameterValue());

		menuQuickLaunchItem.setParameterValue("aaa");
		
		menuQuickLaunchItem = menuQuickLaunchItemDao.save(menuQuickLaunchItem);
		
		menuQuickLaunchItem = menuQuickLaunchItemDao.get(menuQuickLaunchItem.getId());
		
		assertEquals("aaa", menuQuickLaunchItem.getParameterValue());

		assertEquals("deleted", menuQuickLaunchItemDao.remove(menuQuickLaunchItem));
	}

	public void testMenuQuickLaunchItemNotExists() throws Exception
	{
		logger.debug("testing menuQuickLaunchItem not exists...");
		boolean b = menuQuickLaunchItemDao.exists(-1L);
		super.assertFalse(b);
	}

	public void testGetAll() throws Exception
	{
		List<MenuQuickLaunchItem> menuQuickLaunchItems;

		logger.debug("testing getAll()...");

		MenuQuickLaunchItem menuQuickLaunchItem1 = new MenuQuickLaunchItem();
		menuQuickLaunchItem1.setUser(userDao.get(0L));
		menuQuickLaunchItem1.setMenuItem(menuItemDao.get("PROF"));
		menuQuickLaunchItem1.setPosition(0);
		menuQuickLaunchItem1.setParameterValue(99L);
		menuQuickLaunchItem1 = menuQuickLaunchItemDao.save(menuQuickLaunchItem1);

		MenuQuickLaunchItem menuQuickLaunchItem2 = new MenuQuickLaunchItem();
		menuQuickLaunchItem2.setUser(userDao.get(0L));
		menuQuickLaunchItem2.setMenuItem(menuItemDao.get("ACC"));
		menuQuickLaunchItem2.setPosition(1);
		menuQuickLaunchItem2.setParameterValue(99L);
		menuQuickLaunchItem2 = menuQuickLaunchItemDao.save(menuQuickLaunchItem2);

		menuQuickLaunchItems = menuQuickLaunchItemDao.getAll();
		assertNotNull(menuQuickLaunchItems);
		logger.debug(menuQuickLaunchItems.size() + " menuQuickLaunchItems found");
		assertTrue(menuQuickLaunchItems.size() >= 1);

		assertEquals("deleted", menuQuickLaunchItemDao.remove(menuQuickLaunchItem1));
		assertEquals("deleted", menuQuickLaunchItemDao.remove(menuQuickLaunchItem2));
	}

	public void testGetByUser() throws Exception
	{
		logger.debug("testing getByUser valid...");

		MenuQuickLaunchItem menuQuickLaunchItem1 = new MenuQuickLaunchItem();
		menuQuickLaunchItem1.setUser(userDao.get(0L));
		menuQuickLaunchItem1.setMenuItem(menuItemDao.get("PROF"));
		menuQuickLaunchItem1.setPosition(0);
		menuQuickLaunchItem1.setParameterValue(99L);
		menuQuickLaunchItem1 = menuQuickLaunchItemDao.save(menuQuickLaunchItem1);

		MenuQuickLaunchItem menuQuickLaunchItem2 = new MenuQuickLaunchItem();
		menuQuickLaunchItem2.setUser(userDao.get(0L));
		menuQuickLaunchItem2.setMenuItem(menuItemDao.get("ACC"));
		menuQuickLaunchItem2.setPosition(1);
		menuQuickLaunchItem2.setParameterValue(99L);
		menuQuickLaunchItem2 = menuQuickLaunchItemDao.save(menuQuickLaunchItem2);

		List<MenuQuickLaunchItem> menuQuickLaunchItems;

		menuQuickLaunchItems = menuQuickLaunchItemDao.getByUser(0L);
		assertNotNull(menuQuickLaunchItems);
		assertEquals(2, menuQuickLaunchItems.size());

		assertEquals("deleted", menuQuickLaunchItemDao.remove(menuQuickLaunchItem1));
		assertEquals("deleted", menuQuickLaunchItemDao.remove(menuQuickLaunchItem2));
	}
}
