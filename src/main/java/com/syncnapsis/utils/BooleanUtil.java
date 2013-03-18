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

import com.syncnapsis.enums.EnumLogicalOperator;

/**
 * Class implementing useful operations on Booleans.<br>
 * <br>
 * 
 * @author ultimate
 */
public abstract class BooleanUtil
{
	/**
	 * Concatenate two booleans with the operation "AND"<br>
	 * <br>
	 * 
	 * @param b1 - the first boolean
	 * @param b2 - the second boolean
	 * @return the result (b1 && b2)
	 */
	public static Boolean and(Boolean b1, Boolean b2)
	{
		if(b1 == null)
			throw new IllegalArgumentException("The given Boolean b1 is null.");
		if(b2 == null)
			throw new IllegalArgumentException("The given Boolean b2 is null.");
		return b1 && b2;
	}

	/**
	 * Concatenate more than two booleans with the operation "AND"<br>
	 * <br>
	 * 
	 * @param b - the array of booleans
	 * @return the result (b1 && b2 && b3 && ... && bn)
	 */
	public static Boolean and(Boolean[] b)
	{
		if(b == null)
			throw new IllegalArgumentException("The given Array of Boolean b is null.");
		if(b.length == 0)
			throw new IllegalArgumentException("The given Array of Boolean contains no elements.");
		for(int i = 0; i < b.length; i++)
		{
			if(b[i] == null)
				throw new IllegalArgumentException("The given Array of Boolean contains a null element: index " + i + ".");
		}
		Boolean ret = true;
		for(Boolean bi : b)
		{
			ret = ret && bi;
			if(!ret)
				return false;
		}
		return ret;
	}

	/**
	 * Concatenate two booleans with the operation "OR"<br>
	 * <br>
	 * 
	 * @param b1 - the first boolean
	 * @param b2 - the second boolean
	 * @return the result (b1 || b2)
	 */
	public static Boolean or(Boolean b1, Boolean b2)
	{
		if(b1 == null)
			throw new IllegalArgumentException("The given Boolean b1 is null.");
		if(b2 == null)
			throw new IllegalArgumentException("The given Boolean b2 is null.");
		return b1 || b2;
	}

	/**
	 * Concatenate more than two booleans with the operation "OR"<br>
	 * <br>
	 * 
	 * @param b - the array of booleans
	 * @return the result (b1 || b2 || b3 || ... || bn)
	 */
	public static Boolean or(Boolean[] b)
	{
		if(b == null)
			throw new IllegalArgumentException("The given Array of Boolean b is null.");
		if(b.length == 0)
			throw new IllegalArgumentException("The given Array of Boolean contains no elements.");
		for(int i = 0; i < b.length; i++)
		{
			if(b[i] == null)
				throw new IllegalArgumentException("The given Array of Boolean contains a null element: index " + i + ".");
		}
		for(Boolean bi : b)
		{
			if(bi)
				return true;
		}
		return false;
	}

	/**
	 * Concatenate two booleans with the operation "XOR"<br>
	 * <br>
	 * 
	 * @param b1 - the first boolean
	 * @param b2 - the second boolean
	 * @return the result (b1 != b2)
	 */
	public static Boolean xor(Boolean b1, Boolean b2)
	{
		if(b1 == null)
			throw new IllegalArgumentException("The given Boolean b1 is null.");
		if(b2 == null)
			throw new IllegalArgumentException("The given Boolean b2 is null.");
		return (b1 != b2);
	}

	/**
	 * Concatenate more than two booleans with the operation "XOR"<br>
	 * <br>
	 * 
	 * @param b - the array of booleans
	 * @return the result (exactly one boolean is true)
	 */
	public static Boolean xor(Boolean[] b)
	{
		if(b == null)
			throw new IllegalArgumentException("The given Array of Boolean b is null.");
		if(b.length == 0)
			throw new IllegalArgumentException("The given Array of Boolean contains no elements.");
		for(int i = 0; i < b.length; i++)
		{
			if(b[i] == null)
				throw new IllegalArgumentException("The given Array of Boolean contains a null element: index " + i + ".");
		}
		int numberOfTrues = 0;
		for(Boolean bi : b)
		{
			if(bi)
				numberOfTrues++;
		}
		return (numberOfTrues == 1);
	}

	/**
	 * Concatenate two booleans with the operation "NOR"<br>
	 * <br>
	 * 
	 * @param b1 - the first boolean
	 * @param b2 - the second boolean
	 * @return the result (!b1 && !b2)
	 */
	public static Boolean nor(Boolean b1, Boolean b2)
	{
		if(b1 == null)
			throw new IllegalArgumentException("The given Boolean b1 is null.");
		if(b2 == null)
			throw new IllegalArgumentException("The given Boolean b2 is null.");
		return (!b1 && !b2);
	}

	/**
	 * Concatenate more than two booleans with the operation "NOR"<br>
	 * <br>
	 * 
	 * @param b - the array of booleans
	 * @return the result (no boolean is true)
	 */
	public static Boolean nor(Boolean[] b)
	{
		if(b == null)
			throw new IllegalArgumentException("The given Array of Boolean b is null.");
		if(b.length == 0)
			throw new IllegalArgumentException("The given Array of Boolean contains no elements.");
		for(int i = 0; i < b.length; i++)
		{
			if(b[i] == null)
				throw new IllegalArgumentException("The given Array of Boolean contains a null element: index " + i + ".");
		}
		int numberOfTrues = 0;
		for(Boolean bi : b)
		{
			if(bi)
				numberOfTrues++;
		}
		return (numberOfTrues == 0);
	}

	/**
	 * Concatenate two booleans with the given operation.<br>
	 * 
	 * @see EnumLogicalOperator
	 * 
	 * @param b1 - the first boolean
	 * @param b2 - the second boolean
	 * @return the result (b1 o b2)
	 */
	public static Boolean logical(EnumLogicalOperator logic, Boolean b1, Boolean b2)
	{
		if(b1 == null)
			throw new IllegalArgumentException("The given Boolean b1 is null.");
		if(b2 == null)
			throw new IllegalArgumentException("The given Boolean b2 is null.");

		if(logic == EnumLogicalOperator.AND)
			return and(b1, b2);
		else if(logic == EnumLogicalOperator.OR)
			return or(b1, b2);
		else if(logic == EnumLogicalOperator.XOR)
			return xor(b1, b2);
		else if(logic == EnumLogicalOperator.NOR)
			return nor(b1, b2);
		else
			throw new IllegalArgumentException("The given EnumLogicalOperator is not valid.");
	}

	/**
	 * Concatenate more than two booleans with the given operation.<br>
	 * 
	 * @see EnumLogicalOperator
	 * 
	 * @param b - the array of booleans
	 * @return the result
	 */
	public static Boolean logical(EnumLogicalOperator logic, Boolean[] b)
	{
		if(b == null)
			throw new IllegalArgumentException("The given Array of Boolean b is null.");
		if(b.length == 0)
			throw new IllegalArgumentException("The given Array of Boolean contains no elements.");
		for(int i = 0; i < b.length; i++)
		{
			if(b[i] == null)
				throw new IllegalArgumentException("The given Array of Boolean contains a null element: index " + i + ".");
		}
		if(logic == EnumLogicalOperator.AND)
			return and(b);
		else if(logic == EnumLogicalOperator.OR)
			return or(b);
		else if(logic == EnumLogicalOperator.XOR)
			return xor(b);
		else if(logic == EnumLogicalOperator.NOR)
			return nor(b);
		else
			throw new IllegalArgumentException("The given EnumLogicalOperator is not valid.");
	}
}
