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
package com.syncnapsis.utils;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Utility class offering template based message formatting.
 * 
 * @author ultimate
 */
public abstract class MessageUtil
{
	/**
	 * Fill a template with the given key-bound values.<br>
	 * Therefore each key of the map found in the String in the format "{<i>key</i>}" will be
	 * replaced with the associated value of the map. Keys not found in the String will be ignored.
	 * 
	 * @param template - the message template
	 * @param values - the values to insert
	 * @return the resulting message
	 */
	public static String fromTemplate(String template, Map<String, String> values)
	{
		String result = new String(template);
		for(Entry<String, String> entry : values.entrySet())
		{
			result = result.replace("{" + entry.getKey() + "}", entry.getValue());
		}
		return result;
	}
}
