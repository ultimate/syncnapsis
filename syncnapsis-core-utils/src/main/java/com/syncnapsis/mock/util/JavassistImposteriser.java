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

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import org.jmock.api.Imposteriser;
import org.jmock.api.Invocation;
import org.jmock.api.Invokable;
import org.jmock.internal.CaptureControl;
import org.jmock.lib.JavaReflectionImposteriser;

/**
 * An {@link org.jmock.api.Imposteriser} that uses Javassist.<br>
 * This class is uses same structure and layout as {@link JavaReflectionImposteriser} only with
 * Javassist instead of Java Reflections.
 * 
 * @author ultimate
 */
public class JavassistImposteriser implements Imposteriser
{
	/**
	 * A global instance of this Imposteriser
	 */
	public static final Imposteriser	INSTANCE	= new JavassistImposteriser();
	
	/**
	 * Additional Interfaces needed for jmock
	 */
	private static final Class<?>[] MOCK_INTERFACES = {
		CaptureControl.class
	};

	/*
	 * (non-Javadoc)
	 * @see org.jmock.api.Imposteriser#canImposterise(java.lang.Class)
	 */
	@Override
	public boolean canImposterise(Class<?> type)
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jmock.api.Imposteriser#imposterise(org.jmock.api.Invokable, java.lang.Class,
	 * java.lang.Class<?>[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T imposterise(final Invokable mockObject, Class<T> mockedType, Class<?>... ancilliaryTypes)
	{
		ProxyFactory factory = new ProxyFactory();
		factory.setSuperclass(mockedType);
		factory.setInterfaces(MOCK_INTERFACES);

		try
		{
			return (T) factory.create(new Class<?>[0], new Object[0], new MethodHandler() {
				public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable
				{
					return mockObject.invoke(new Invocation(self, thisMethod, args));
				}
			});
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("could not create javassis-proxy for " + mockedType.getName(), e);
		}
	}
}
