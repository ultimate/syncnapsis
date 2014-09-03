/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import java.util.List;

/**
 * {@link BlackListValidator}-extension especially for Strings which can be configured in two ways:<br>
 * <ul>
 * <li>non-strict - value must not MATCH a list entry</li>
 * <li>strict - value must not CONTAIN a list entry</li>
 * </ul>
 * 
 * @author ultimate
 */
public class BlackListStringValidator extends BlackListValidator<String>
{
	/**
	 * Is this Validator configured strict?<br>
	 * <ul>
	 * <li>non-strict - value must not MATCH a list entry</li>
	 * <li>strict - value must not CONTAIN a list entry</li>
	 * </ul>
	 */
	protected boolean	strict	= false;

	/**
	 * Construct a new BlackListStringValidator
	 */
	public BlackListStringValidator()
	{
		super();
	}

	/**
	 * Is this Validator configured strict?<br>
	 * <ul>
	 * <li>non-strict - value must not MATCH a list entry</li>
	 * <li>strict - value must not CONTAIN a list entry</li>
	 * </ul>
	 * 
	 * @return true or false
	 */
	public boolean isStrict()
	{
		return strict;
	}

	/**
	 * Is this Validator configured strict?<br>
	 * <ul>
	 * <li>non-strict - value must not MATCH a list entry</li>
	 * <li>strict - value must not CONTAIN a list entry</li>
	 * </ul>
	 * 
	 * @param strict - true or false
	 */
	public void setStrict(boolean strict)
	{
		this.strict = strict;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.security.Validator#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(String value)
	{
		if(!isStrict())
		{
			return super.isValid(value);
		}
		else
		{
			List<String> blackList = getBlackList();
			for(String blackListEntry : blackList)
			{
				if(value.contains(blackListEntry))
					return false;
			}
			return true;
		}
	}
}
