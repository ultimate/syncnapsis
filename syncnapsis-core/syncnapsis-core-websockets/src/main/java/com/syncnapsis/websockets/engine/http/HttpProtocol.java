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
package com.syncnapsis.websockets.engine.http;

import com.syncnapsis.websockets.engine.BaseProtocol;

/**
 * Implementation of the WebSocketProtocol Interface for the HTTP fallback via HttpWebSocketServlet
 * 
 * @author ultimate
 */
public class HttpProtocol extends BaseProtocol
{
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#isMessageComplete(byte)
	 */
	@Override
	public boolean isMessageComplete(byte flags)
	{
		// no message fragmenting
		return true;
	}
}
