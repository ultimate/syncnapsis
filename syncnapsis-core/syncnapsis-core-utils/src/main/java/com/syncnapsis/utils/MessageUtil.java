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
package com.syncnapsis.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class offering template based message formatting.
 * 
 * @author ultimate
 */
public abstract class MessageUtil
{
	/**
	 * Logger-Instance
	 */
	protected static transient final Logger	logger		= LoggerFactory.getLogger(MessageUtil.class);
	/**
	 * The start char for the keys used in templates
	 */
	public static final char				KEY_START	= '{';
	/**
	 * The end char for the keys used in templates
	 */
	public static final char				KEY_END		= '}';

	/**
	 * Fill a template with the given key-bound values.<br>
	 * Therefore each key of the map found in the String in the format "{<i>key</i>}" will be
	 * replaced with the associated value of the map. Keys not found in the String will be ignored.
	 * 
	 * @param template - the message template
	 * @param values - the values to insert
	 * @return the resulting message
	 */
	public static String fromTemplate(String template, Map<String, Object> values)
	{
		String result = new String(template);
		String key;
		String value;
		boolean replaced;
		do
		{
			replaced = false;
			for(Entry<String, Object> entry : values.entrySet())
			{
				value = entry.getValue() != null ? entry.getValue().toString() : "null";
				key = KEY_START + entry.getKey() + KEY_END;
				if(result.contains(key))
				{
					result = result.replace(key, value);
					replaced = true;
				}
			}
		} while(replaced);
		return result;
	}

	/**
	 * Get all used keys within the template.<br>
	 * This algorithm does NOT perform any plausibility checks for the keys and may therefore return
	 * nested keys. (I know this is not the best solution but it will work for now, if correct keys
	 * are used.)
	 * 
	 * @param template - the template to scan
	 * @return the list of keys
	 */
	public static List<String> getUsedTemplateKeys(String template)
	{
		List<String> keys = new LinkedList<String>();

		int start = -1;
		int end = -1;
		while(true)
		{
			start = template.indexOf(KEY_START, start + 1);
			if(start == -1)
				break;
			end = template.indexOf(KEY_END, start);
			if(end == -1)
				break;
			keys.add(template.substring(start + 1, end));
		}

		return keys;
	}

	public static Map<String, Object> extractValues(List<String> keys, Object... args)
	{
		Map<String, Object> values = new HashMap<String, Object>();

		String keyClass;
		String field;
		int dotIndex;
		for(String key : keys)
		{
			if(key == null)
				continue;

			dotIndex = key.indexOf('.');

			if(dotIndex == -1)
			{
				keyClass = key;
				field = null;
			}
			else
			{
				keyClass = key.substring(0, dotIndex);
				field = key.substring(dotIndex + 1);
			}

			for(Object arg : args)
			{
				if(arg == null)
					continue;
				if(arg.getClass().getSimpleName().equalsIgnoreCase(keyClass))
				{
					if(field != null)
					{
						try
						{
							values.put(key, ReflectionsUtil.getFieldByKey(arg, field));
						}
						catch(NoSuchFieldException e)
						{
							logger.warn("field '" + field + "' not found in " + arg + " - value will be null");
						}
						catch(IllegalAccessException e)
						{
							logger.warn("field '" + field + "' not acessible in " + arg + " - value will be null");
						}
					}
					else
					{
						values.put(key, arg);
					}
				}
			}
		}
		return values;
	}
}
