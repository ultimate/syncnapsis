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
