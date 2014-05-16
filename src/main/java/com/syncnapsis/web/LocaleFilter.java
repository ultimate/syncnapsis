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
package com.syncnapsis.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;

import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.providers.LocaleProvider;
import com.syncnapsis.providers.SessionProvider;
import com.syncnapsis.utils.ServletUtil;

/**
 * Extension of {@link URLRewriteFilter} rewriting urls for the current locale the user selected.<br/>
 * By using {@link URLRewriteFilter#rewriteURL(String, HttpServletRequest, HttpServletResponse)} the
 * current locale is inserted into the requested path at a specified directory level.
 * 
 * @author ultimate
 */
public class LocaleFilter extends URLRewriteFilter
{
	/**
	 * The SessionProvider used to store the current session on request
	 */
	protected SessionProvider	sessionProvider;

	/**
	 * The LocaleProvider used to access the current locale
	 */
	protected LocaleProvider	localeProvider;

	/**
	 * The directory level to insert the locale into, e.g.<br>
	 * <ul>
	 * <li>0 - at top level (e.g. /lang/x/y -> /en/lang/x/y</li>
	 * <li>1 - at first level (e.g. /lang/x/y -> /lang/en/x/y</li>
	 * <li>2 - at second level (e.g. /lang/x/y -> /lang/x/en/y</li>
	 * <li>and so on...</li>
	 * </ul>
	 */
	protected int				localeDirectoryLevel	= -1;

	/**
	 * Include the locale in lower case? (Default = true)
	 */
	protected boolean			localeLowerCase			= true;

	/**
	 * Empty Default Constructor
	 */
	public LocaleFilter()
	{
		super();
	}

	/**
	 * Construct a new LocalFilter with a given path.
	 * 
	 * @param path - an optional path for the Filter to listen at
	 */
	public LocaleFilter(String path)
	{
		super(path);
	}

	/**
	 * The SessionProvider used to store the current session on request
	 * 
	 * @return sessionProvider
	 */
	public SessionProvider getSessionProvider()
	{
		return sessionProvider;
	}

	/**
	 * The SessionProvider used to store the current session on request
	 * 
	 * @param sessionProvider - the SessionProvider
	 */
	public void setSessionProvider(SessionProvider sessionProvider)
	{
		this.sessionProvider = sessionProvider;
	}

	/**
	 * The LocaleProvider used to access the current locale
	 * 
	 * @return localeProvider
	 */
	public LocaleProvider getLocaleProvider()
	{
		return localeProvider;
	}

	/**
	 * The LocaleProvider used to access the current locale
	 * 
	 * @param localeProvider - the LocaleProvider
	 */
	public void setLocaleProvider(LocaleProvider localeProvider)
	{
		this.localeProvider = localeProvider;
	}

	/**
	 * The directory level to insert the locale into, e.g.<br>
	 * <ul>
	 * <li>0 - at top level (e.g. /lang/x/y -> /en/lang/x/y</li>
	 * <li>1 - at first level (e.g. /lang/x/y -> /lang/en/x/y</li>
	 * <li>2 - at second level (e.g. /lang/x/y -> /lang/x/en/y</li>
	 * <li>and so on...</li>
	 * </ul>
	 * 
	 * @return localeDirectoryLevel
	 */
	public int getLocaleDirectoryLevel()
	{
		return localeDirectoryLevel;
	}

	/**
	 * The directory level to insert the locale into, e.g.<br>
	 * <ul>
	 * <li>0 - at top level (e.g. /lang/x/y -> /en/lang/x/y</li>
	 * <li>1 - at first level (e.g. /lang/x/y -> /lang/en/x/y</li>
	 * <li>2 - at second level (e.g. /lang/x/y -> /lang/x/en/y</li>
	 * <li>and so on...</li>
	 * </ul>
	 * 
	 * @param localeDirectoryLevel - the level index
	 */
	public void setLocaleDirectoryLevel(int localeDirectoryLevel)
	{
		this.localeDirectoryLevel = localeDirectoryLevel;
	}

	/**
	 * Include the locale in lower case? (Default = true)
	 * 
	 * @return localeLowerCase
	 */
	public boolean isLocaleLowerCase()
	{
		return localeLowerCase;
	}

	/**
	 * Include the locale in lower case? (Default = true)
	 * 
	 * @param localeLowerCase - true or false
	 */
	public void setLocaleLowerCase(boolean localeLowerCase)
	{
		this.localeLowerCase = localeLowerCase;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.engine.BaseWebEngine#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(sessionProvider, "sessionProvider must not be null!");
		Assert.notNull(localeProvider, "localeProvider must not be null!");
		Assert.isTrue(localeDirectoryLevel >= 0, "localeDirectoryLevel must be >= 0!");
		super.afterPropertiesSet();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.web.URLRewriteFilter#rewriteURL(java.lang.String,
	 * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String rewriteURL(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		ServletUtil.copyRequestClientInfo(request, request.getSession());
		sessionProvider.set(request.getSession());

		EnumLocale locale = localeProvider.get();

		String target;
		if(localeLowerCase)
			target = ServletUtil.insertDirectory(url, locale.toString().toLowerCase(), localeDirectoryLevel);
		else
			target = ServletUtil.insertDirectory(url, locale.toString(), localeDirectoryLevel);

		logger.debug("forwarding to: " + target);
		return target;
	}
}
