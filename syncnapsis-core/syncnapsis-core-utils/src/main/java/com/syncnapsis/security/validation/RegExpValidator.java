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

import org.springframework.util.Assert;

import com.syncnapsis.security.Validator;

/**
 * A Validator checking wether a String matches a given regular expression
 * 
 * @author ultimate
 */
public class RegExpValidator implements Validator<String>
{
	/**
	 * The regular expression used to validate the Strings
	 */
	protected String	regExp;

	/**
	 * Construct a new RegExpValidator
	 * 
	 * @param regExp - The regular expression used to validate the Strings
	 */
	public RegExpValidator(String regExp)
	{
		Assert.notNull(regExp, "regExp must not be null!");
		this.regExp = regExp;
	}

	/**
	 * The regular expression used to validate the Strings
	 * 
	 * @return
	 */
	public String getRegExp()
	{
		return regExp;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.Validator#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(String value)
	{
		return value.matches(regExp);
	}

}
