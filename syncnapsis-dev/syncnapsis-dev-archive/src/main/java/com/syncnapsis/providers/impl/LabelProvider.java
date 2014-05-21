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

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.providers.ParameterizedProvider;
import com.syncnapsis.utils.PropertiesUtil;
import com.syncnapsis.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Provider-Klasse für das statische Abrufen von Texten anhand von Schlüsseln
 * aus den i3-label*.properties
 * 
 * @author ultimate
 */
@Deprecated
public class LabelProvider extends ParameterizedProvider<String, String>
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());
	
	protected Locale locale;
	
	protected Properties properties;
	
	protected LabelProvider(Locale locale)
	{
		this.locale = locale;
		try
		{
			this.properties = PropertiesUtil.loadProperties(getPropertiesFile(locale));
		}
		catch(IOException e)
		{
			logger.error("Could not load requried properties file", e);
			this.properties = new Properties();
		}
	}

	/**
	 * Gibt den Text zum gegebenen Schlüssel zurück.
	 * Falls der Schlüssel nicht existiert, wird ???key??? zurückgegeben.
	 * 
	 * @see org.zkoss.util.resource.impl.LabelLoader#getLabel(String)
	 * @param key - der Schlüssel
	 * @return der Text
	 */
	public String get(String key)
	{
		return get(key, "???" + key + "???");
	}

	/**
	 * Operation not supported by LabelProvider
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void set(String key, String value) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("set is not supported for LabelProvider");
	}

	/**
	 * Gibt den Text zum gegebenen Schlüssel zurück.
	 * Falls der Schlüssel nicht existiert, wird ???key??? zurückgegeben.
	 * In der Nachricht werden zusätzlich Platzhalter durch die Argumente in
	 * args ersetzt.
	 * 
	 * @see org.zkoss.util.resource.impl.LabelLoader#getLabel(String)
	 * @param key - der Schlüssel
	 * @param args - die Argumente
	 * @return der Text
	 */
	public String get(String key, Object... args)
	{
		final String s = get(key);
		return s != null ? StringUtil.format(s, locale, args) : null;
	}

	/**
	 * Gibt den Text zum gegebenen Schlüssel zurück.
	 * Falls der Schlüssel nicht existiert, wird defValue zurückgegeben.
	 * 
	 * @see org.zkoss.util.resource.impl.LabelLoader#getLabel(String)
	 * @param key - der Schlüssel
	 * @param defValue - der Default-Wert
	 * @return der Text
	 */
	public String get(String key, String defValue)
	{
		String label = this.properties.getProperty(key);
		return label == null ? defValue : label;
	}

	/**
	 * Gibt den Text zum gegebenen Schlüssel zurück.
	 * Falls der Schlüssel nicht existiert, wird defValue zurückgegeben.
	 * In der Nachricht werden zusätzlich Platzhalter durch die Argumente in
	 * args ersetzt.
	 * 
	 * @see org.zkoss.util.resource.impl.LabelLoader#getLabel(String)
	 * @param key - der Schlüssel
	 * @param defValue - der Default-Wert
	 * @param args - die Argumente
	 * @return der Text
	 */
	public String get(String key, String defValue, Object... args)
	{
		final String s = get(key, defValue);
		return s != null ? StringUtil.format(s, locale, args) : null;
	}
	
	private static final Map<Locale, LabelProvider> providers = new TreeMap<Locale, LabelProvider>();

	public static LabelProvider getInstance(Locale locale)
	{
		if(locale == null)
			return getInstance(EnumLocale.getDefault().getLocale());
		LabelProvider provider = providers.get(locale);
		if(provider == null)
		{
			provider = new LabelProvider(locale);
			providers.put(locale, provider);
		}
		return provider;
	}
	
	public static File getPropertiesFile(Locale locale)
	{
		return new File("i3-label_" + locale + ".properties");
	}
}
