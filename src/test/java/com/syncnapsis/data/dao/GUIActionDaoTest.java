package com.syncnapsis.data.dao;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import com.syncnapsis.data.dao.hibernate.GUIActionDaoHibernate;
import com.syncnapsis.data.model.GUIAction;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { GUIActionDao.class, GUIActionDaoHibernate.class })
public class GUIActionDaoTest extends BaseDaoTestCase
{
	private GUIActionDao	guiActionDao;

	public void testGetGUIActionInvalid() throws Exception
	{
		logger.debug("testing getGUIAction invalid...");
		try
		{
			guiActionDao.get(-1000L);
			fail("'badGUIActionID' found in database, failing test...");
		}
		catch(ObjectNotFoundException e)
		{
			assertNotNull(e);
		}
	}

	public void testGetGUIAction() throws Exception
	{
		logger.debug("testing getGUIAction valid...");
		GUIAction guiAction = guiActionDao.get(1001L);

		assertNotNull(guiAction);
		assertEquals("security/login", guiAction.getAction());
	}

	public void testGetByActionName() throws Exception
	{
		logger.debug("testing getByActionName valid...");

		String actionName = "security/login";

		GUIAction guiAction;

		guiAction = guiActionDao.getByName(actionName);
		assertNotNull(guiAction);
		assertEquals(actionName, guiAction.getAction());
		assertEquals(new Long(1001), guiAction.getId());
	}

	public void testGetByWindowId() throws Exception
	{
		logger.debug("testing getByWindowId valid...");

		String windowId = "loginWin";

		GUIAction guiAction;

		guiAction = guiActionDao.getByWindowId(windowId);
		assertNotNull(guiAction);
		assertEquals(windowId, guiAction.getWindowId());
		assertEquals(new Long(1001), guiAction.getId());
	}

	public void testUpdateGUIAction() throws Exception
	{
		logger.debug("testing update guiAction...");
		GUIAction guiAction = guiActionDao.get(1001L);

		guiAction.setTitleKey("menu.root2");
		guiAction = guiActionDao.save(guiAction);

		guiAction = guiActionDao.get(1001L);
		assertEquals("menu.root2", guiAction.getTitleKey());
	}

	public void testAddAndRemoveGUIAction() throws Exception
	{
		logger.debug("testing add and remove on guiAction...");
		GUIAction guiAction = new GUIAction();
		guiAction.setId(10000L);
		guiAction.setTitleKey("title");
		guiAction.setAction("action");
		guiAction.setWindowId("winId");
		guiAction.setWindowAction(true);

		guiAction = guiActionDao.save(guiAction);

		assertNotNull(guiAction.getId());
		guiAction = guiActionDao.get(guiAction.getId());
		assertEquals("title", guiAction.getTitleKey());
		assertEquals("action", guiAction.getAction());
		assertEquals("winId", guiAction.getWindowId());
		assertEquals(true, guiAction.isWindowAction());

		assertEquals("deleted", guiActionDao.remove(guiAction));
	}

	public void testGUIActionExists() throws Exception
	{
		logger.debug("testing guiAction exists...");
		boolean b = guiActionDao.exists(1001L);
		super.assertTrue(b);
	}

	public void testGUIActionNotExists() throws Exception
	{
		logger.debug("testing guiAction not exists...");
		boolean b = guiActionDao.exists(-1000L);
		super.assertFalse(b);
	}

	public void testGetAll() throws Exception
	{
		List<GUIAction> guiActions;

		logger.debug("testing getAll()...");
		guiActions = guiActionDao.getAll();
		assertNotNull(guiActions);
		logger.debug(guiActions.size() + " guiActions found");
		assertTrue(guiActions.size() >= 5);
	}
}
