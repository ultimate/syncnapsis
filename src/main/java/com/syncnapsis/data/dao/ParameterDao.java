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
package com.syncnapsis.data.dao;

import java.util.Date;

import com.syncnapsis.data.model.Parameter;

/**
 * Dao-Interface für den Zugriff auf Parameter
 * 
 * @author ultimate
 */
public interface ParameterDao extends GenericNameDao<Parameter, Long>
{
	/**
	 * Lädt den Wert eines Parameters als String
	 * 
	 * @param name - der Name des Parameters
	 * @return der Parameter als String
	 */
	public String getString(String name);

	/**
	 * Lädt den Wert eines Parameters als Long
	 * 
	 * @param name - der Name des Parameters
	 * @return der Parameter als Long
	 */
	public Long getLong(String name);

	/**
	 * Lädt den Wert eines Parameters als Integer
	 * 
	 * @param name - der Name des Parameters
	 * @return der Parameter als Integer
	 */
	public Integer getInteger(String name);

	/**
	 * Lädt den Wert eines Parameters als Short
	 * 
	 * @param name - der Name des Parameters
	 * @return der Parameter als Short
	 */
	public Short getShort(String name);

	/**
	 * Lädt den Wert eines Parameters als Byte
	 * 
	 * @param name - der Name des Parameters
	 * @return der Parameter als Byte
	 */
	public Byte getByte(String name);

	/**
	 * Lädt den Wert eines Parameters als Double
	 * 
	 * @param name - der Name des Parameters
	 * @return der Parameter als Double
	 */
	public Double getDouble(String name);

	/**
	 * Lädt den Wert eines Parameters als Float
	 * 
	 * @param name - der Name des Parameters
	 * @return der Parameter als Float
	 */
	public Float getFloat(String name);

	/**
	 * Lädt den Wert eines Parameters als Boolean
	 * 
	 * @param name - der Name des Parameters
	 * @return der Parameter als Boolean
	 */
	public Boolean getBoolean(String name);

	/**
	 * Lädt den Wert eines Parameters als Date
	 * 
	 * @param name - der Name des Parameters
	 * @return der Parameter als Date
	 */
	public Date getDate(String name);
}
