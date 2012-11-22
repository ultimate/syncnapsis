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

import com.syncnapsis.constants.ApplicationBaseConstants;
import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.providers.LocaleProvider;
import com.syncnapsis.providers.SessionBasedProvider;

/**
 * Extension of SessionBasedProvider for the current Locale.
 * 
 * @author ultimate
 */
public class SessionBasedLocaleProvider extends SessionBasedProvider<EnumLocale> implements LocaleProvider
{
	/**
	 * Default-Constructor configuring SessionBasedProvider with
	 * {@link ApplicationBaseConstants#SESSION_LOCALE_KEY}.
	 */
	public SessionBasedLocaleProvider()
	{
		super(ApplicationBaseConstants.SESSION_LOCALE_KEY);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.SessionBasedProvider#get()
	 */
	@Override
	public EnumLocale get()
	{
		EnumLocale locale = super.get();
		if(locale != null)
			return locale;
		return EnumLocale.getDefault();
	}	
}
