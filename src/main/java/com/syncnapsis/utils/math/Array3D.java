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

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.util.Assert;

/**
 * Class representing a three-dimensional array/matrix for storing doubles. In comparison to
 * standard arrays a range for each index can be specified so indexes can be negative as well.<br>
 * Additionally standard math operations are offered as accessors to be able to manipulated the
 * matrix easily.<br>
 * Indexes within this matrix will go from iMin to iMax (both inclusive) and the corresponding size
 * is offered as iSize. The values are accessible via two different indexes:
 * <ul>
 * <li>x,y,z - as the three-dimensional index</li>
 * <li>i&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- as an counter counting in the manner<br>
 * <code>i = ((x - xMin) * (ySize) + (y - yMin)) * (zSize) + (z - zMin)</code></li>
 * </ul>
 * 
 * @author ultimate
 */
public class Array3D implements Cloneable, Serializable
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * The matrix holding the valus
	 */
	private double[][][]		matrix;
	/**
	 * The size in x-direction (first index)
	 */
	private int					xSize;
	/**
	 * The size in y-direction (second index)
	 */
	private int					ySize;
	/**
	 * The size in z-direction (third index)
	 */
	private int					zSize;

	/**
	 * The smallest index in x-direction (first index)
	 */
	private int					xMin;
	/**
	 * The largest index in x-direction (first index)
	 */
	private int					xMax;
	/**
	 * The smallest index in y-direction (second index)
	 */
	private int					yMin;
	/**
	 * The largest index in y-direction (second index)
	 */
	private int					yMax;
	/**
	 * The smallest index in z-direction (third index)
	 */
	private int					zMin;
	/**
	 * The largest index in z-direction (third index)
	 */
	private int					zMax;

	/**
	 * The total volume (xSize * ySize * zSize)
	 */
	private int					volume;
	/**
	 * The weight of this matrix (a factor to multiply it with when norming)
	 */
	private double				weight;

	/**
	 * Create a new three-dimensional Array and init it with the given size. The bounds will
	 * automatically be calculated from the size with<br>
	 * <code>iMin = (-iSize + 1) / 2</code> and <code>iMax = iSize / 2</code>
	 * 
	 * @param xSize - The size in x-direction (first index)
	 * @param ySize - The size in y-direction (second index)
	 * @param zSize - The size in z-direction (third index)
	 */
	public Array3D(int xSize, int ySize, int zSize)
	{
		Assert.isTrue(xSize > 0, "xSize <= 0");
		Assert.isTrue(ySize > 0, "ySize <= 0");
		Assert.isTrue(zSize > 0, "zSize <= 0");

		init((-xSize + 1) / 2, xSize / 2, (-ySize + 1) / 2, ySize / 2, (-zSize + 1) / 2, zSize / 2);
	}

	/**
	 * Create a new three-dimensional Array and init it from the given bounds.
	 * 
	 * @param xMin - The smallest index in x-direction (first index)
	 * @param xMax - The largest index in x-direction (first index)
	 * @param yMin - The smallest index in y-direction (second index)
	 * @param yMax - The largest index in y-direction (second index)
	 * @param zMin - The smallest index in z-direction (third index)
	 * @param zMax - The largest index in z-direction (third index)
	 */
	public Array3D(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax)
	{
		Assert.isTrue(xMin <= xMax, "xMin > xMax");
		Assert.isTrue(yMin <= yMax, "yMin > yMax");
		Assert.isTrue(zMin <= zMax, "zMin > zMax");

		init(xMin, xMax, yMin, yMax, zMin, zMax);
	}

	/**
	 * Init the matrix from the given bounds.
	 * 
	 * @param xMin - The smallest index in x-direction (first index)
	 * @param xMax - The largest index in x-direction (first index)
	 * @param yMin - The smallest index in y-direction (second index)
	 * @param yMax - The largest index in y-direction (second index)
	 * @param zMin - The smallest index in z-direction (third index)
	 * @param zMax - The largest index in z-direction (third index)
	 */
	private void init(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax)
	{
		// set size
		this.xSize = xMax - xMin + 1;
		this.ySize = yMax - yMin + 1;
		this.zSize = zMax - zMin + 1;
		// init matrix
		this.matrix = new double[xSize][ySize][zSize];
		this.weight = 1.0;
		// calc volume
		this.volume = xSize * ySize * zSize;
		// calc min max
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.zMin = zMin;
		this.zMax = zMax;
	}

	/**
	 * The size in x-direction (first index)
	 * 
	 * @return xSize
	 */
	public int getXSize()
	{
		return xSize;
	}

	/**
	 * The size in y-direction (second index)
	 * 
	 * @return ySize
	 */
	public int getYSize()
	{
		return ySize;
	}

	/**
	 * The size in z-direction (third index)
	 * 
	 * @return ySize
	 */
	public int getZSize()
	{
		return zSize;
	}

	/**
	 * The smallest index in x-direction (first index)
	 * 
	 * @return xMin
	 */
	public int getXMin()
	{
		return xMin;
	}

	/**
	 * The largest index in x-direction (first index)
	 * 
	 * @return xMin
	 */
	public int getXMax()
	{
		return xMax;
	}

	/**
	 * The smallest index in y-direction (second index)
	 * 
	 * @return yMin
	 */
	public int getYMin()
	{
		return yMin;
	}

	/**
	 * The largest index in y-direction (second index)
	 * 
	 * @return yMin
	 */
	public int getYMax()
	{
		return yMax;
	}

	/**
	 * The smallest index in z-direction (third index)
	 * 
	 * @return zMin
	 */
	public int getZMin()
	{
		return zMin;
	}

	/**
	 * The largest index in z-direction (third index)
	 * 
	 * @return zMin
	 */
	public int getZMax()
	{
		return zMax;
	}

	/**
	 * The total volume (xSize * ySize * zSize)
	 * 
	 * @return volume
	 */
	public int getVolume()
	{
		return volume;
	}

	/**
	 * The weight of this matrix (a factor to multiply it with when norming)
	 * 
	 * @return weight
	 */
	public double getWeight()
	{
		return weight;
	}

	/**
	 * The weight of this matrix (a factor to multiply it with when norming)
	 * 
	 * @param weight - the weight factor
	 */
	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	/**
	 * The value at index x, y, z
	 * 
	 * @param x - the x-index
	 * @param y - the y-index
	 * @param z - the z-index
	 * @return value(x,y,z)
	 */
	public double get(int x, int y, int z)
	{
		checkRange(x, y, z);
		return this.matrix[x - xMin][y - yMin][z - zMin];
	}

	/**
	 * The value at index i
	 * 
	 * @param i - the "counting"-index
	 * @return value(i)
	 */
	public double get(int i)
	{
		checkRange(i);
		int z = i % zSize;
		int y = (i - z) / zSize % ySize;
		int x = (((i - z) / zSize) - y) / ySize;
		return this.matrix[x][y][z];
	}

	/**
	 * The value at index x, y, z
	 * 
	 * @param x - the x-index
	 * @param y - the y-index
	 * @param z - the z-index
	 * @param value - the value to set for value(x,y,z)
	 */
	public void set(int x, int y, int z, double value)
	{
		checkRange(x, y, z);
		this.matrix[x - xMin][y - yMin][z - zMin] = value;
	}

	/**
	 * The value at index i
	 * 
	 * @param i - the "counting"-index
	 * @param value - the value to set for value(i)
	 */
	public void set(int i, double value)
	{
		checkRange(i);
		int z = i % zSize;
		int y = (i - z) / zSize % ySize;
		int x = (((i - z) / zSize) - y) / ySize;
		this.matrix[x][y][z] = value;
	}

	/**
	 * Add the given addend to the value at index x, y, z
	 * 
	 * @param x - the x-index
	 * @param y - the y-index
	 * @param z - the z-index
	 * @param addend - the value to add to value(x,y,z)
	 */
	public void add(int x, int y, int z, double addend)
	{
		checkRange(x, y, z);
		this.matrix[x - xMin][y - yMin][z - zMin] += addend;
	}

	/**
	 * Add the given addend to the value at index i
	 * 
	 * @param i - the "counting"-index
	 * @param addend - the value to add to value(i)
	 */
	public void add(int i, double addend)
	{
		checkRange(i);
		int z = i % zSize;
		int y = (i - z) / zSize % ySize;
		int x = (((i - z) / zSize) - y) / ySize;
		this.matrix[x][y][z] += addend;
	}

	/**
	 * Multiply the value at index x, y, z with the given factor
	 * 
	 * @param x - the x-index
	 * @param y - the y-index
	 * @param z - the z-index
	 * @param factor - the factor to multiply value(x,y,z) with
	 */
	public void multiply(int x, int y, int z, double factor)
	{
		checkRange(x, y, z);
		this.matrix[x - xMin][y - yMin][z - zMin] *= factor;
	}

	/**
	 * Multiply the value at index i with the given factor
	 * 
	 * @param i - the "counting"-index
	 * @param factor - the factor to multiply value(i) with
	 */
	public void multiply(int i, double factor)
	{
		checkRange(i);
		int z = i % zSize;
		int y = (i - z) / zSize % ySize;
		int x = (((i - z) / zSize) - y) / ySize;
		this.matrix[x][y][z] *= factor;
	}

	/**
	 * Add a complete Array3D to this Array3D value by value. When adding the other Array3D it's
	 * weight will be premultiplied before adding the values:<br>
	 * <code>value(x,y,z) += m.weight * m.value(x,y,z)</code>
	 * 
	 * @param m - the other matrix
	 */
	public void addMatrix(Array3D m)
	{
		Assert.isTrue(m.xSize == this.xSize && m.ySize == this.ySize && m.zSize == this.zSize, "Dimensions must match.");

		for(int x = 0; x < xSize; x++)
			for(int y = 0; y < ySize; y++)
				for(int z = 0; z < zSize; z++)
					this.matrix[x][y][z] += m.weight * m.matrix[x][y][z];
	}

	/**
	 * Multiply all values in this matrix with the given factor
	 * 
	 * @param factor - the factor to multiply with
	 */
	public void multiply(double factor)
	{
		for(int x = 0; x < xSize; x++)
			for(int y = 0; y < ySize; y++)
				for(int z = 0; z < zSize; z++)
					this.matrix[x][y][z] *= factor;
	}

	/**
	 * Check wether the given index combination of x,y,z is within bounds
	 * 
	 * @param x - the x-index
	 * @param y - the y-index
	 * @param z - the z-index
	 * @throws IllegalArgumentException if index is out of bounds
	 */
	protected void checkRange(int x, int y, int z)
	{
		Assert.isTrue(x >= xMin && x <= xMax, "x not in range: " + x + " Range: " + xMin + ".." + xMax);
		Assert.isTrue(y >= yMin && y <= yMax, "y not in range: " + y + " Range: " + yMin + ".." + yMax);
		Assert.isTrue(z >= zMin && z <= zMax, "z not in range: " + z + " Range: " + zMin + ".." + zMax);
	}

	/**
	 * Check wether the given "counting"-index is within bounds
	 * 
	 * @param i - the "counting"-index
	 * @throws IllegalArgumentException if index is out of bounds
	 */
	protected void checkRange(int i)
	{
		Assert.isTrue(i >= 0 && i < volume, "i not in range: " + i + " Range: " + 0 + ".." + volume + " (exclusive)");
	}

	/**
	 * Get the "counting"-index for the index combination of x, y, z
	 * 
	 * @param x - the x-index
	 * @param y - the y-index
	 * @param z - the z-index
	 * @return the "counting"-index
	 */
	public int getIndex(int x, int y, int z)
	{
		checkRange(x, y, z);
		return ((x - xMin) * (ySize) + (y - yMin)) * (zSize) + (z - zMin);
	}

	/**
	 * Get the index combination of x, y, z for the given "counting"-index
	 * 
	 * @param i - the "counting"-index
	 * @return the index combination (x,y,z)
	 */
	public int[] getIndexes(int i)
	{
		int z = i % zSize + zMin;
		int y = (i - (z - zMin)) / zSize % ySize + yMin;
		int x = (((i - (z - zMin)) / zSize) - (y - yMin)) / ySize + xMin;
		return new int[] { x, y, z };
	}

	/**
	 * "Normiert" diese Matrix so, dass anschlieﬂend gilt:
	 * normedSumOfProbabilies(expectedNumberOfResults) = 1
	 * und
	 * sumOfProbabilities() = expectedNumberOfResults
	 * 
	 * @see Array3D#normedSumOfProbabilities(long)
	 * @see Array3D#sumOfProbabilities()
	 * @param expectedNumberOfResults
	 */
	public void norm(long expectedNumberOfResults)
	{
		double factor = getNormedSum(expectedNumberOfResults) * this.weight;
		multiply(factor);
	}

	/**
	 * Die Summe aller Wahrscheinlichkeiten dieser Matrix.
	 * L‰sst man eine Zufallssequenz ¸ber diese Matrix laufen, dann ist die
	 * Anzahl der Eintr‰ge mit p(x,y,z) > Zufallszahl so groﬂ wie
	 * sumOfProbabilities() * weight
	 * 
	 * @see Array3D#normedSumOfProbabilities(long)
	 * @return sumOfProbabilities
	 */
	public double getSum()
	{
		double sum = 0;
		for(int x = 0; x < xSize; x++)
			for(int y = 0; y < ySize; y++)
				for(int z = 0; z < zSize; z++)
					sum += this.matrix[x][y][z];
		return sum;
	}

	/**
	 * Die normierte Summe aller Wahrscheinlichkeiten dieser Matrix:
	 * expectedNumberOfResults / sumOfProbabilities()
	 * Dieser Wert ist 1, wenn expectedNumberOfResults = sumOfProbabilities()
	 * 
	 * @param expectedNumberOfResults
	 * @return normedSumOfProbabilities
	 */
	public double getNormedSum(long expectedNumberOfResults)
	{
		return expectedNumberOfResults / getSum();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Array3D clone()
	{
		Array3D m2 = null;
		try
		{
			m2 = (Array3D) super.clone();
		}
		catch(CloneNotSupportedException e)
		{
		}
		m2.xSize = xSize;
		m2.ySize = ySize;
		m2.zSize = zSize;
		m2.weight = 1.0;
		m2.matrix = this.matrix.clone();
		return m2;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(matrix);
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + xSize;
		result = prime * result + ySize;
		result = prime * result + zSize;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof Array3D))
			return false;
		Array3D other = (Array3D) obj;
		if(xSize != other.xSize)
			return false;
		if(ySize != other.ySize)
			return false;
		if(zSize != other.zSize)
			return false;
		if(Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		for(int x = xMin; x <= xMax; x++)
		{
			for(int y = yMin; y <= yMax; y++)
			{
				for(int z = zMin; z <= zMax; z++)
				{
					if(Math.abs(get(x, y, z) - other.get(x, y, z)) > 0.00000001)
						return false;
				}
			}
		}
		return true;
	}
}
