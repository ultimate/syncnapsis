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
package com.syncnapsis.utils.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Klasse, die Konstanten als Defaultwerte f�r die Generierung zuf�lliger Werte
 * durch RandomData bereitstellt.
 * Die Bezeichner sollten selbsterkl�rend sein, wenn nicht, einen kurzen Blick
 * auf den Inhalt werfen...
 * 
 * @author ultimate
 */
public abstract class DefaultData
{
	public static final String			STRING_ASCII_CONTROLCHARS					= "\n\r\t\f";
	public static final String			STRING_ASCII_LETTERS_LOWER					= "abcdefghijklmnopqrstuvwxyz";
	public static final String			STRING_ASCII_LETTERS_UPPER					= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String			STRING_ASCII_NUMBERS						= "0123456789";
	public static final String			STRING_ASCII_SYMBOLS_1						= " !\"#$%&'()*+,-./";
	public static final String			STRING_ASCII_SYMBOLS_2						= ":;<=>?@";
	public static final String			STRING_ASCII_SYMBOLS_3						= "[\\]^_`";
	public static final String			STRING_ASCII_SYMBOLS_4						= "{|}~";

	public static final String			STRING_ASCII_COMPLETE_NO_CONTROLCHARS		= STRING_ASCII_SYMBOLS_1 + STRING_ASCII_NUMBERS
																							+ STRING_ASCII_SYMBOLS_2 + STRING_ASCII_LETTERS_UPPER
																							+ STRING_ASCII_SYMBOLS_3 + STRING_ASCII_LETTERS_LOWER
																							+ STRING_ASCII_SYMBOLS_4;
	public static final String			STRING_ASCII_COMPLETE_WITH_CONTROLCHARS		= STRING_ASCII_CONTROLCHARS
																							+ STRING_ASCII_COMPLETE_NO_CONTROLCHARS;

	public static final String			STRING_EMAIL_SYMBOLS						= "!#$%&'*+-/=?^_`{|}~";

	public static final String			STRING_EMAIL_COMPLETE_NO_DOT_NO_AT			= STRING_EMAIL_SYMBOLS + STRING_ASCII_LETTERS_LOWER
																							+ STRING_ASCII_LETTERS_UPPER + STRING_ASCII_NUMBERS;
	@Deprecated
	private static final String[]		STRING_TOP_LEVEL_DOMAIN_EXTENSIONS_ARRAY	= {
																					// nicht
																					// gesponsorte
																					// TLDs
			"arpa", "biz", "com", "info", "name", "net", "org", "pro",
			// gesponsorte TLDs
			"aero", "asia", "cat", "coop", "edu", "gov", "int", "jobs", "mil", "mobi", "museum", "tel", "travel" };

	@Deprecated
	public static final List<String>	STRING_TOP_LEVEL_DOMAIN_EXTENSIONS_LIST		= Collections.unmodifiableList(Arrays
																							.asList(STRING_TOP_LEVEL_DOMAIN_EXTENSIONS_ARRAY));
	@Deprecated
	public static final String			STRING_TOP_LEVEL_DOMAIN_EXTENSIONS			= toRegExpString(STRING_TOP_LEVEL_DOMAIN_EXTENSIONS_ARRAY);

	@Deprecated
	public static final String			REGEXP_DOMAIN_OLD							= "(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+(?:[a-zA-Z]{2}|"
																							+ STRING_TOP_LEVEL_DOMAIN_EXTENSIONS + ")";
	public static final String			REGEXP_DOMAIN								= "(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?";
	public static final String			REGEXP_EMAIL								= "[a-zA-Z0-9" + STRING_EMAIL_SYMBOLS + "]+(?:\\.[a-zA-Z0-9" + STRING_EMAIL_SYMBOLS + "]+)*@"
																							+ REGEXP_DOMAIN;

	public static final int				INT											= 1234567890;
	public static final long			LONG										= 1234567890123456789L;

	/**
	 * Konvertiert ein Array (z.B. mit den TLD) in einen String zur Benutzung in
	 * einer RegExp.
	 * 
	 * @param array - das Array zum konvertieren
	 * @return der RexExp-String
	 */
	private static String toRegExpString(String[] array)
	{
		if(array == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for(String s : array)
		{
			if(sb.length() != 0)
				sb.append("|");
			sb.append(s);
		}
		return sb.toString();
	}
}
