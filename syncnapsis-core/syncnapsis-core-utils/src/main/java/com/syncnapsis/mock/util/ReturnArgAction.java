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

import org.jmock.api.Invocation;
import org.jmock.lib.action.CustomAction;

/**
 * JMock-Action that will return the argument at the specified index as a result for the
 * invocation.
 * 
 * @author ultimate
 */
public class ReturnArgAction extends CustomAction
{
	/**
	 * The index of the argument to return
	 */
	private int	argumentIndex;

	/**
	 * Default constructor (argumentIndex = 0)
	 */
	public ReturnArgAction()
	{
		this(0);
	}

	/**
	 * Constructor with custom argumentIndex
	 * 
	 * @param argumentIndex - the index
	 */
	public ReturnArgAction(int argumentIndex)
	{
		super("returns argument @ index " + argumentIndex);
		this.argumentIndex = argumentIndex;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jmock.api.Invokable#invoke(org.jmock.api.Invocation)
	 */
	@Override
	public Object invoke(Invocation invocation) throws Throwable
	{
		return invocation.getParameter(argumentIndex);
	}
}
