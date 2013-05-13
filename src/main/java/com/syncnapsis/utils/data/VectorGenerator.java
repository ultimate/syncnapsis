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
package com.syncnapsis.utils.data;

import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.utils.math.Array3D;

/**
 * @author ultimate
 */
public abstract class VectorGenerator<V extends Vector<N>, N extends Number> extends Generator<V>
{
	/**
	 * The min value for x (inclusive)
	 */
	protected N	xMin;
	/**
	 * The max value for x (inclusive)
	 */
	protected N	xMax;
	/**
	 * The min value for y (inclusive)
	 */
	protected N	yMin;
	/**
	 * The max value for y (inclusive)
	 */
	protected N	yMax;
	/**
	 * The min value for z (inclusive)
	 */
	protected N	zMin;
	/**
	 * The max value for z (inclusive)
	 */
	protected N	zMax;

	/**
	 * Construct a new VectorGenerator with the given bounds
	 * 
	 * @param xMin - The min value for x (inclusive)
	 * @param xMax - The max value for x (inclusive)
	 * @param yMin - The min value for y (inclusive)
	 * @param yMax - The max value for y (inclusive)
	 * @param zMin - The min value for z (inclusive)
	 * @param zMax - The max value for z (inclusive)
	 */
	public VectorGenerator(N xMin, N xMax, N yMin, N yMax, N zMin, N zMax)
	{
		super();
		setBounds(xMin, xMax, yMin, yMax, zMin, zMax);
	}

	/**
	 * Set the bounds for this vector generator
	 * 
	 * @param xMin - The min value for x (inclusive)
	 * @param xMax - The max value for x (inclusive)
	 * @param yMin - The min value for y (inclusive)
	 * @param yMax - The max value for y (inclusive)
	 * @param zMin - The min value for z (inclusive)
	 * @param zMax - The max value for z (inclusive)
	 */
	public void setBounds(N xMin, N xMax, N yMin, N yMax, N zMin, N zMax)
	{
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.zMin = zMin;
		this.zMax = zMax;
	}

	public N getXMin()
	{
		return xMin;
	}

	public void setXMin(N xMin)
	{
		this.xMin = xMin;
	}

	public N getXMax()
	{
		return xMax;
	}

	public void setXMax(N xMax)
	{
		this.xMax = xMax;
	}

	public N getYMin()
	{
		return yMin;
	}

	public void setYMin(N yMin)
	{
		this.yMin = yMin;
	}

	public N getYMax()
	{
		return yMax;
	}

	public void setYMax(N yMax)
	{
		this.yMax = yMax;
	}

	public N getZMin()
	{
		return zMin;
	}

	public void setZMin(N zMin)
	{
		this.zMin = zMin;
	}

	public N getZMax()
	{
		return zMax;
	}

	public void setZMax(N zMax)
	{
		this.zMax = zMax;
	}

	public static class Integer extends VectorGenerator<Vector.Integer, java.lang.Integer>
	{
		/**
		 * Construct a new VectorGenerator with the given bounds
		 * 
		 * @param xMin - The min value for x (inclusive)
		 * @param xMax - The max value for x (inclusive)
		 * @param yMin - The min value for y (inclusive)
		 * @param yMax - The max value for y (inclusive)
		 * @param zMin - The min value for z (inclusive)
		 * @param zMax - The max value for z (inclusive)
		 */
		public Integer(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax)
		{
			super(xMin, xMax, yMin, yMax, zMin, zMax);
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.utils.data.Generator#generate(java.lang.Object[])
		 */
		@Override
		public Vector.Integer generate(Object... args)
		{
			return new Vector.Integer(random.nextInt(xMin, xMax), random.nextInt(yMin, yMax), random.nextInt(zMin, zMax));
		}
	}
}
