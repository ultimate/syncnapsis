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
package com.syncnapsis.security.validation;

import java.util.LinkedList;
import java.util.List;

import org.springframework.util.Assert;

import com.syncnapsis.security.Validator;

/**
 * A Validator checking values based on a white-list (values must match a list-entry).<br>
 * When checking the value against the white-list {@link WhiteListValidator#getWhiteList()} is used
 * to access the white-list. This way this validator can be extended by simply overwriting
 * getWhiteList().
 * 
 * @author ultimate
 * 
 * @param <T> - the type to validate
 */
public class WhiteListValidator<T> implements Validator<T>
{
	/**
	 * The List to validate values against.
	 */
	private List<T>	whiteList;

	/**
	 * Construct a new WhiteListValidator
	 */
	public WhiteListValidator()
	{
		this.whiteList = new LinkedList<T>();
	}

	/**
	 * The white-list to validate values against.
	 * 
	 * @return whiteList
	 */
	public List<T> getWhiteList()
	{
		return whiteList;
	}

	/**
	 * The white-list to validate values against.
	 * 
	 * @param list - the List
	 */
	public void setWhiteList(List<T> whiteList)
	{
		Assert.notNull(whiteList, "whiteList must not be null!");
		this.whiteList = whiteList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.Validator#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(T value)
	{
		return getWhiteList().contains(value);
	}
}
