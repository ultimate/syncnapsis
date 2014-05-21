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
package com.syncnapsis.enums;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Enum für die Spezifizierung des Datumsformats eines Users.
 * 
 * @author ultimate
 */
public enum EnumDateFormat
{
	/**
	 * Datumsformat: "dd.MM.yyyy HH:mm:ss" (default)
	 */
	dMy24_dot,
	/**
	 * Datumsformat: "dd.MM.yyyy hh:mm:ss a"
	 */
	dMy12_dot,
	/**
	 * Datumsformat: "dd/MM/yyyy HH:mm:ss"
	 */
	dMy24_slash,
	/**
	 * Datumsformat: "dd/MM/yyyy hh:mm:ss a"
	 */
	dMy12_slash,
	/**
	 * Datumsformat: "MM.dd.yyyy HH:mm:ss"
	 */
	Mdy24_dot,
	/**
	 * Datumsformat: "MM.dd.yyyy hh:mm:ss a"
	 */
	Mdy12_dot,
	/**
	 * Datumsformat: "MM/dd/yyyy HH:mm:ss"
	 */
	Mdy24_slash,
	/**
	 * Datumsformat: "MM/dd/yyyy hh:mm:ss a"
	 */
	Mdy12_slash,
	/**
	 * Datumsformat: "yyyy.MM.dd HH:mm:ss"
	 */
	yMd24_dot,
	/**
	 * Datumsformat: "yyyy.MM.dd hh:mm:ss a"
	 */
	yMd12_dot,
	/**
	 * Datumsformat: "yyyy/MM/dd HH:mm:ss"
	 */
	yMd24_slash,
	/**
	 * Datumsformat: "yyyy/MM/dd hh:mm:ss a"
	 */
	yMd12_slash,
	/**
	 * Datumsformat: "yyyy-MM-dd HH:mm:ss"
	 */
	yMd24_minus,
	/**
	 * Datumsformat: "yyyy-MM-dd hh:mm:ss a"
	 */
	yMd12_minus;

	/**
	 * Gibt das default Datumsformat zurück: yMd24_minus
	 * 
	 * @return das default Datumsformat
	 */
	public static EnumDateFormat getDefault()
	{
		return yMd24_minus;
	}

	/**
	 * Wandelt dieses Enum in ein Datumsformat um. Es wird ein SimpleDateFormat
	 * mit Hilfe von toString() erzeugt.
	 * 
	 * @return das DateFormat zu diesem Enum
	 */
	public DateFormat getDateFormat()
	{
		return new SimpleDateFormat(this.toString());
	}

	@Override
	public String toString()
	{
		if(this.equals(dMy24_dot))
			return "dd.MM.yyyy HH:mm:ss";
		if(this.equals(dMy12_dot))
			return "dd.MM.yyyy hh:mm:ss a";
		if(this.equals(dMy24_slash))
			return "dd/MM/yyyy HH:mm:ss";
		if(this.equals(dMy12_slash))
			return "dd/MM/yyyy hh:mm:ss a";
		if(this.equals(Mdy24_dot))
			return "MM.dd.yyyy HH:mm:ss";
		if(this.equals(Mdy12_dot))
			return "MM.dd.yyyy hh:mm:ss a";
		if(this.equals(Mdy24_slash))
			return "MM/dd/yyyy HH:mm:ss";
		if(this.equals(Mdy12_slash))
			return "MM/dd/yyyy hh:mm:ss a";
		if(this.equals(yMd24_dot))
			return "yyyy.MM.dd HH:mm:ss";
		if(this.equals(yMd12_dot))
			return "yyyy.MM.dd hh:mm:ss a";
		if(this.equals(yMd24_slash))
			return "yyyy/MM/dd HH:mm:ss";
		if(this.equals(yMd12_slash))
			return "yyyy/MM/dd hh:mm:ss a";
		if(this.equals(yMd24_minus))
			return "yyyy-MM-dd HH:mm:ss";
		if(this.equals(yMd12_minus))
			return "yyyy-MM-dd hh:mm:ss a";
		return "";
	}
}
