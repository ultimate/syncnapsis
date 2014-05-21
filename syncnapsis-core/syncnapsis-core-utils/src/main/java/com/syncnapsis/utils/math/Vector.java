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
package com.syncnapsis.utils.math;


/**
 * Basic interface representing 3-dimensional vectors.
 * 
 * @author ultimate
 */
public interface Vector<N extends Number>
{
	/**
	 * The x-coordinate
	 * 
	 * @return x
	 */
	public N getX();

	/**
	 * The y-coordinate
	 * 
	 * @return y
	 */
	public N getY();

	/**
	 * The z-coordinate
	 * 
	 * @return z
	 */
	public N getZ();

	/**
	 * The x-coordinate
	 * 
	 * @param x - the coordinate value (null is permitted)
	 */
	public void setX(N x);

	/**
	 * The y-coordinate
	 * 
	 * @param y - the coordinate value (null is permitted)
	 */
	public void setY(N y);

	/**
	 * The z-coordinate
	 * 
	 * @param z - the coordinate value (null is permitted)
	 */
	public void setZ(N z);
}
