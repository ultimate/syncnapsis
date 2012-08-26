package com.syncnapsis.providers.impl;

import javax.servlet.http.HttpSession;

import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.providers.ThreadLocalProvider;

/**
 * HttpSessions must be provided by the endpoint via set(..) in order to be accessible for other
 * Threads via get().
 * Creating a Bean of this type offers the opportunity to change the way of accessing the time via
 * SpringContext-Configuration.<br>
 * 
 * @author ultimate
 */
public class ThreadLocalSessionProvider extends ThreadLocalProvider<HttpSession> implements SessionProvider
{
	// nothing specific here
}
