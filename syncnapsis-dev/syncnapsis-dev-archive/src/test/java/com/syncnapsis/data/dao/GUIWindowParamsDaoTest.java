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
import com.syncnapsis.data.dao.hibernate.GUIWindowParamsDaoHibernate;
import com.syncnapsis.data.model.GUIWindowParams;
import com.syncnapsis.tests.BaseDaoTestCase;
import com.syncnapsis.tests.annotations.TestCoversClasses;

@TestCoversClasses( { GUIWindowParamsDao.class, GUIWindowParamsDaoHibernate.class })
public class GUIWindowParamsDaoTest extends BaseDaoTestCase
{
	private GUIWindowParamsDao	guiWindowParamsDao;

	public void testGetGUIWindowParamsInvalid() throws Exception
	{
		logger.debug("testing getGUIWindowParams invalid...");
		try
		{
			guiWindowParamsDao.get(-1000L);
			fail("'badGUIWindowParamsID' found in database, failing test...");
		}
		catch(ObjectNotFoundException e)
		{
			assertNotNull(e);
		}
	}

	public void testGetGUIWindowParams() throws Exception
	{
		logger.debug("testing getGUIWindowParams valid...");
		GUIWindowParams guiWindowParams = guiWindowParamsDao.get(1001L);

		assertNotNull(guiWindowParams);
		assertTrue(guiWindowParams.isClosable());
		assertNull(guiWindowParams.getDraggable());
		assertNull(guiWindowParams.getDroppable());
		assertFalse(guiWindowParams.isMaximizable());
		assertFalse(guiWindowParams.isMaximized());
		assertFalse(guiWindowParams.isMinimizable());
		assertFalse(guiWindowParams.isMinimized());
		assertFalse(guiWindowParams.isSizable());
		assertTrue(guiWindowParams.isVisible());
	}

	public void testUpdateGUIWindowParams() throws Exception
	{
		logger.debug("testing update guiWindowParams...");
		GUIWindowParams guiWindowParams = guiWindowParamsDao.get(1001L);

		guiWindowParams.setClosable(true);
		guiWindowParams.setDraggable("abc");
		guiWindowParams.setDroppable("def");
		guiWindowParams.setMaximizable(true);
		guiWindowParams.setMaximized(false);
		guiWindowParams.setMinimizable(true);
		guiWindowParams.setMinimized(false);
		guiWindowParams.setSizable(true);
		guiWindowParams.setVisible(true);
		guiWindowParams = guiWindowParamsDao.save(guiWindowParams);

		guiWindowParams = guiWindowParamsDao.get(1001L);
		assertTrue(guiWindowParams.isClosable());
		assertEquals("abc", guiWindowParams.getDraggable());
		assertEquals("def", guiWindowParams.getDroppable());
		assertTrue(guiWindowParams.isMaximizable());
		assertFalse(guiWindowParams.isMaximized());
		assertTrue(guiWindowParams.isMinimizable());
		assertFalse(guiWindowParams.isMinimized());
		assertTrue(guiWindowParams.isSizable());
		assertTrue(guiWindowParams.isVisible());
	}

	public void testAddAndRemoveGUIWindowParams() throws Exception
	{
		logger.debug("testing add and remove on guiWindowParams...");
		GUIWindowParams guiWindowParams = new GUIWindowParams();
		guiWindowParams.setId(10000L);
		guiWindowParams.setHeight("100px");
		guiWindowParams.setMinheight(99);
		guiWindowParams.setWidth("100px");
		guiWindowParams.setMinwidth(99);
		guiWindowParams.setLeft("100px");
		guiWindowParams.setTop("100px");
		guiWindowParams.setZindex(0);
		guiWindowParams.setPosition("center");

		guiWindowParams.setClosable(true);
		guiWindowParams.setDraggable("abc");
		guiWindowParams.setDroppable("def");
		guiWindowParams.setMaximizable(true);
		guiWindowParams.setMaximized(false);
		guiWindowParams.setMinimizable(true);
		guiWindowParams.setMinimized(false);
		guiWindowParams.setSizable(true);
		guiWindowParams.setVisible(true);

		guiWindowParams = guiWindowParamsDao.save(guiWindowParams);

		assertNotNull(guiWindowParams.getId());
		guiWindowParams = guiWindowParamsDao.get(guiWindowParams.getId());
		assertTrue(guiWindowParams.isClosable());
		assertEquals("abc", guiWindowParams.getDraggable());
		assertEquals("def", guiWindowParams.getDroppable());
		assertTrue(guiWindowParams.isMaximizable());
		assertFalse(guiWindowParams.isMaximized());
		assertTrue(guiWindowParams.isMinimizable());
		assertFalse(guiWindowParams.isMinimized());
		assertTrue(guiWindowParams.isSizable());
		assertTrue(guiWindowParams.isVisible());

		assertEquals("deleted", guiWindowParamsDao.remove(guiWindowParams));
	}

	public void testGUIWindowParamsExists() throws Exception
	{
		logger.debug("testing guiWindowParams exists...");
		boolean b = guiWindowParamsDao.exists(1001L);
		super.assertTrue(b);
	}

	public void testGUIWindowParamsNotExists() throws Exception
	{
		logger.debug("testing guiWindowParams not exists...");
		boolean b = guiWindowParamsDao.exists(-1000L);
		super.assertFalse(b);
	}

	public void testGetAll() throws Exception
	{
		List<GUIWindowParams> guiWindowParamss;

		logger.debug("testing getAll()...");
		guiWindowParamss = guiWindowParamsDao.getAll();
		assertNotNull(guiWindowParamss);
		logger.debug(guiWindowParamss.size() + " guiWindowParamss found");
		assertTrue(guiWindowParamss.size() >= 5);
	}
}
