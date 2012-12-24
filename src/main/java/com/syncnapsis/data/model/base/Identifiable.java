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
package com.syncnapsis.data.model.base;

import java.io.Serializable;

import com.syncnapsis.security.annotations.Accessible;

/**
 * Interface for identifiable Objects.<br>
 * Those Objects must provide an ID via getId() and setId(PK id).<br>
 * <br>
 * Usage examples:<br>
 * <ul>
 * <li>matching authority-Objects to values in {@link Accessible}-Annotations</li>
 * <li>super Interface for com.syncnapsis.BaseObject (in com.syncnapsis.data)</li>
 * </ul>
 * 
 * @author ultimate
 * @param <PK> - the type of the id
 */
public interface Identifiable<PK extends Serializable>
{
	public PK getId();

	public void setId(PK id);
}
