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
package com.syncnapsis.providers.impl;

import com.syncnapsis.providers.ConnectionProvider;
import com.syncnapsis.providers.ThreadLocalProvider;
import com.syncnapsis.websockets.Connection;

/**
 * HttpConnections must be provided by the endpoint via set(..) in order to be accessible for other
 * Threads via get().
 * Creating a Bean of this type offers the opportunity to change the way of accessing the time via
 * SpringContext-Configuration.<br>
 * 
 * @author ultimate
 */
public class ThreadLocalConnectionProvider extends ThreadLocalProvider<Connection> implements ConnectionProvider
{
	// nothing specific here
}
