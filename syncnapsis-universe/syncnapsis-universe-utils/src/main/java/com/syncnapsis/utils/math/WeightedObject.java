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
package com.syncnapsis.utils.math;

/**
 * General generic implementation for linking arbitrary objects with an associated weight.
 * 
 * @author ultimate
 * 
 * @param <O> - the type of object
 * @param <W> - the type of weight
 */
public class WeightedObject<O, W extends Number>
{
	/**
	 * The object
	 */
	private O	object;
	/**
	 * The objects weight
	 */
	private W	weight;

	/**
	 * Empty default constructor (initializing object and weight with <code>null</code>)
	 */
	public WeightedObject()
	{
		this(null, null);
	}

	/**
	 * Standard constructor
	 * 
	 * @param object - the object
	 * @param weight - the weight
	 */
	public WeightedObject(O object, W weight)
	{
		super();
		this.object = object;
		this.weight = weight;
	}

	/**
	 * The object
	 * 
	 * @return object
	 */
	public O getObject()
	{
		return object;
	}

	/**
	 * The object
	 * 
	 * @param object - the object
	 */
	public void setObject(O object)
	{
		this.object = object;
	}

	/**
	 * The objects weight
	 * 
	 * @return weight
	 */
	public W getWeight()
	{
		return weight;
	}

	/**
	 * The objects weight
	 * 
	 * @param weight - the weight
	 */
	public void setWeight(W weight)
	{
		this.weight = weight;
	}
}
