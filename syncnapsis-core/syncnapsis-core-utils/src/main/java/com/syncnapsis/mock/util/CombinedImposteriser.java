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
package com.syncnapsis.mock.util;

import org.jmock.api.Imposteriser;
import org.jmock.api.Invokable;
import org.jmock.lib.JavaReflectionImposteriser;

/**
 * An {@link org.jmock.api.Imposteriser} that can swith between
 * <ul>
 * <li>{@link JavaReflectionImposteriser} (used for interfaces)</li>
 * <li>{@link JavassistImposteriser} (used for abstract classes)</li>
 * </ul>
 * 
 * @author ultimate
 */
public class CombinedImposteriser implements Imposteriser
{
	/**
	 * A global instance of this Imposteriser
	 */
	public static final Imposteriser	INSTANCE				= new CombinedImposteriser();

	/**
	 * Shortcut to {@link JavaReflectionImposteriser#INSTANCE}
	 */
	private static final Imposteriser	REFLECTIONS_INSTANCE	= JavaReflectionImposteriser.INSTANCE;

	/**
	 * Shortcut to {@link JavassistImposteriser#INSTANCE}
	 */
	private static final Imposteriser	JAVASSIST_INSTANCE		= JavassistImposteriser.INSTANCE;

	/*
	 * (non-Javadoc)
	 * @see org.jmock.api.Imposteriser#canImposterise(java.lang.Class)
	 */
	@Override
	public boolean canImposterise(Class<?> type)
	{
		return REFLECTIONS_INSTANCE.canImposterise(type) || JAVASSIST_INSTANCE.canImposterise(type);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jmock.api.Imposteriser#imposterise(org.jmock.api.Invokable, java.lang.Class,
	 * java.lang.Class<?>[])
	 */
	@Override
	public <T> T imposterise(final Invokable mockObject, Class<T> mockedType, Class<?>... ancilliaryTypes)
	{
		if(mockedType.isInterface())
			return REFLECTIONS_INSTANCE.imposterise(mockObject, mockedType, ancilliaryTypes);
		else
			return JAVASSIST_INSTANCE.imposterise(mockObject, mockedType, ancilliaryTypes);
	}
}
