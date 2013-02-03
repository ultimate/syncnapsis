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
package com.syncnapsis.exceptions;

import java.util.ArrayList;

import com.syncnapsis.enums.EnumEngineSupport;
import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.websockets.Engine;
import com.syncnapsis.websockets.engine.BaseEngine;

/**
 * Test for WebSocketEngineException
 * 
 * @author ultimate
 */
public class WebSocketEngineExceptionTest extends LoggerTestCase
{
	private Engine	engine	= new BaseEngine(EnumEngineSupport.NOT_SUPPORTED) {
								/*
								 * (non-Javadoc)
								 * @see com.syncnapsis.websockets.engine.BaseEngine#stop0()
								 */
								@Override
								protected void stop0() throws WebSocketEngineException
								{
									// nothing
								}

								/*
								 * (non-Javadoc)
								 * @see com.syncnapsis.websockets.engine.BaseEngine#start0()
								 */
								@Override
								protected void start0() throws WebSocketEngineException
								{
									// nothing
								}
							};

	public void testInvalidGetChildren()
	{
		try
		{
			engine.getChildren();
			fail("expected Exception not occurred");
		}
		catch(WebSocketEngineException ex)
		{
			assertNotNull(ex);
		}
	}

	public void testInvalidSetChildren()
	{
		try
		{
			engine.setChildren(new ArrayList<Engine>());
			fail("expected Exception not occurred");
		}
		catch(WebSocketEngineException ex)
		{
			assertNotNull(ex);
		}
	}

	public void testInvalidAddChild()
	{
		try
		{
			engine.addChild(null);
			fail("expected Exception not occurred");
		}
		catch(WebSocketEngineException ex)
		{
			assertNotNull(ex);
		}
	}

	public void testInvalidRemoveChild()
	{
		try
		{
			engine.removeChild(null);
			fail("expected Exception not occurred");
		}
		catch(WebSocketEngineException ex)
		{
			assertNotNull(ex);
		}
	}
}
