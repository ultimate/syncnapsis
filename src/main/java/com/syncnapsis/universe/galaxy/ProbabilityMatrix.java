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
package com.syncnapsis.universe.galaxy;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.util.Assert;

/**
 * Diese Klasse repräsentiert eine drei-dimensionale Matrix, in der
 * Wahrscheinlichkeiten gespeichert sind. Sie bietet die Möglichkeit die
 * Wahrscheinlichkeit für einzelne Einträge zu setzten oder zu verändern und
 * darüber hinaus die gesamte Matrix für ein erwartetes Ergebnis zu "normieren".
 * Für die Verwendung der Indizes gilt dabei in allen drei Dimensionen
 * - iMin = (-iSize + 1) / 2
 * - iMax = iSize / 2
 * - iMin <= i <= iMax
 * 
 * @author ultimate
 */
public class ProbabilityMatrix implements Cloneable, Serializable
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Die Matrix der Wahrscheinlichkeiten
	 */
	private double[][][]		matrix;
	/**
	 * Die Größe in x-Richtung
	 */
	private int					xSize;
	/**
	 * Die Größe in y-Richtung
	 */
	private int					ySize;
	/**
	 * Die Größe in z-Richtung
	 */
	private int					zSize;

	private int					xMin;
	private int					xMax;
	private int					yMin;
	private int					yMax;
	private int					zMin;
	private int					zMax;

	/**
	 * The total volume (xSize * ySize * zSize)
	 */
	private int					volume;
	/**
	 * Die Gewichtung dieser Matrix im Vergleich zu anderen
	 * Wahrscheinlichkeitsmatrizen
	 */
	private double				weight;

	/**
	 * Erzeugt eine neue Matrix mit definierter Größe
	 * 
	 * @param xSize - Die Größe in x-Richtung
	 * @param ySize - Die Größe in y-Richtung
	 * @param zSize - Die Größe in z-Richtung
	 */
	public ProbabilityMatrix(int xSize, int ySize, int zSize)
	{
		Assert.isTrue(xSize > 0, "xSize <= 0");
		Assert.isTrue(ySize > 0, "ySize <= 0");
		Assert.isTrue(zSize > 0, "zSize <= 0");

		this.matrix = new double[xSize][ySize][zSize];
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.weight = 1.0;
		// calc volume
		this.volume = xSize * ySize * zSize;
		// calc min max
		this.xMin = (-xSize + 1) / 2;
		this.yMin = (-ySize + 1) / 2;
		this.zMin = (-zSize + 1) / 2;
		this.xMax = xSize / 2;
		this.yMax = ySize / 2;
		this.zMax = zSize / 2;
	}

	/**
	 * Die Größe in x-Richtung
	 * 
	 * @return xSize
	 */
	public int getXSize()
	{
		return xSize;
	}

	/**
	 * Die Größe in y-Richtung
	 * 
	 * @return ySize
	 */
	public int getYSize()
	{
		return ySize;
	}

	/**
	 * Die Größe in y-Richtung
	 * 
	 * @return ySize
	 */
	public int getZSize()
	{
		return zSize;
	}

	/**
	 * Der kleinste Index für x
	 * 
	 * @return xMin
	 */
	public int getXMin()
	{
		return xMin;
	}

	/**
	 * Der größte Index für x
	 * 
	 * @return xMin
	 */
	public int getXMax()
	{
		return xMax;
	}

	/**
	 * Der kleinste Index für y
	 * 
	 * @return yMin
	 */
	public int getYMin()
	{
		return yMin;
	}

	/**
	 * Der größte Index für y
	 * 
	 * @return yMin
	 */
	public int getYMax()
	{
		return yMax;
	}

	/**
	 * Der kleinste Index für z
	 * 
	 * @return zMin
	 */
	public int getZMin()
	{
		return zMin;
	}

	/**
	 * Der größte Index für z
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
	 * *
	 * Die Gewichtung dieser Matrix im Vergleich zu anderen
	 * Wahrscheinlichkeitsmatrizen
	 * 
	 * @return weight
	 */
	public double getWeight()
	{
		return weight;
	}

	/**
	 * *
	 * Die Gewichtung dieser Matrix im Vergleich zu anderen
	 * Wahrscheinlichkeitsmatrizen
	 * 
	 * @param weight
	 */
	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	/**
	 * Der Wahrscheinlichkeitseintrag an der Stelle x,y,z
	 * 
	 * @param x - der Index in x-Richtung
	 * @param y - der Index in y-Richtung
	 * @param z - der Index in z-Richtung
	 * @return p(x,y,z)
	 */
	public double getProbability(int x, int y, int z)
	{
		checkRange(x, y, z);
		return this.matrix[x + (xSize - 1) / 2][y + (ySize - 1) / 2][z + (zSize - 1) / 2];
	}

	public double getProbability(int i)
	{
		checkRange(i);
		int z = i % zSize;
		int y = (i - z) / zSize % ySize;
		int x = (((i - z) / zSize) - y) / ySize;
		return this.matrix[x][y][z];
	}

	/**
	 * Der Wahrscheinlichkeitseintrag an der Stelle x,y,z
	 * 
	 * @param x - der Index in x-Richtung
	 * @param y - der Index in y-Richtung
	 * @param z - der Index in z-Richtung
	 * @param probability - p(x,y,z)
	 */
	public void setProbability(int x, int y, int z, double probability)
	{
		checkRange(x, y, z);
		this.matrix[x + (xSize - 1) / 2][y + (ySize - 1) / 2][z + (zSize - 1) / 2] = probability;
	}

	public void setProbability(int i, double probability)
	{
		checkRange(i);
		int z = i % zSize;
		int y = (i - z) / zSize % ySize;
		int x = (((i - z) / zSize) - y) / ySize;
		this.matrix[x][y][z] = probability;
	}

	/**
	 * Addiert die Wahrscheinlichkeit zu der Stelle x,y,z
	 * 
	 * @param x - der Index in x-Richtung
	 * @param y - der Index in y-Richtung
	 * @param z - der Index in z-Richtung
	 * @param addend - der Summand
	 */
	public void addProbability(int x, int y, int z, double addend)
	{
		checkRange(x, y, z);
		this.matrix[x + (xSize - 1) / 2][y + (ySize - 1) / 2][z + (zSize - 1) / 2] += addend;
	}

	public void addProbability(int i, double addend)
	{
		checkRange(i);
		int z = i % zSize;
		int y = (i - z) / zSize % ySize;
		int x = (((i - z) / zSize) - y) / ySize;
		this.matrix[x][y][z] += addend;
	}

	/**
	 * Multipliziert die Wahrscheinlichkeit ad der Stelle x,y,z
	 * 
	 * @param x - der Index in x-Richtung
	 * @param y - der Index in y-Richtung
	 * @param z - der Index in z-Richtung
	 * @param factor - der Faktor
	 */
	public void multiplyProbability(int x, int y, int z, double factor)
	{
		checkRange(x, y, z);
		this.matrix[x + (xSize - 1) / 2][y + (ySize - 1) / 2][z + (zSize - 1) / 2] *= factor;
	}

	public void multiplyProbability(int i, double factor)
	{
		checkRange(i);
		int z = i % zSize;
		int y = (i - z) / zSize % ySize;
		int x = (((i - z) / zSize) - y) / ySize;
		this.matrix[x][y][z] *= factor;
	}

	/**
	 * Addiert eine vollständige Matrix unter Berücksichtigung der Gewichtungen
	 * zu dieser Matrix dazu. Dabei werden die Wahrscheinlichkeiten dieser
	 * Matrix direkt verändert.
	 * 
	 * @param m - die andere Matrix
	 */
	public void addMatrix(ProbabilityMatrix m)
	{
		Assert.isTrue(m.xSize == this.xSize && m.ySize == this.ySize && m.zSize == this.zSize, "Dimensions must match.");

		for(int x = 0; x < xSize; x++)
			for(int y = 0; y < ySize; y++)
				for(int z = 0; z < zSize; z++)
					this.matrix[x][y][z] += m.weight * m.matrix[x][y][z];
	}

	/**
	 * Prüft, ob die Indizes im gültigen Bereich für diese Matrix liegen
	 * 
	 * @param x - der Index in x-Richtung
	 * @param y - der Index in y-Richtung
	 * @param z - der Index in z-Richtung
	 */
	private void checkRange(int x, int y, int z)
	{
		Assert.isTrue(x >= xMin && x <= xMax, "x not in range: " + x + " Range: " + xMin + ".." + xMax);
		Assert.isTrue(y >= yMin && y <= yMax, "y not in range: " + y + " Range: " + yMin + ".." + yMax);
		Assert.isTrue(z >= zMin && z <= zMax, "z not in range: " + z + " Range: " + zMin + ".." + zMax);
	}

	private void checkRange(int i)
	{
		Assert.isTrue(i >= 0 && i < volume, "i not in range: " + i + " Range: " + 0 + ".." + volume + " (exclusive)");
	}

	public int getIndex(int x, int y, int z)
	{
		checkRange(x, y, z);
		return ((x - xMin) * (ySize) + (y - yMin)) * (zSize) + (z - zMin);
	}

	public int[] getCoords(int i)
	{
		int z = i % zSize + zMin;
		int y = (i - (z - zMin)) / zSize % ySize + yMin;
		int x = (((i - (z - zMin)) / zSize) - (y - yMin)) / ySize + xMin;
		return new int[] { x, y, z };
	}

	/**
	 * "Normiert" diese Matrix so, dass anschließend gilt:
	 * normedSumOfProbabilies(expectedNumberOfResults) = 1
	 * und
	 * sumOfProbabilities() = expectedNumberOfResults
	 * 
	 * @see ProbabilityMatrix#normedSumOfProbabilities(long)
	 * @see ProbabilityMatrix#sumOfProbabilities()
	 * @param expectedNumberOfResults
	 */
	public void norm(int expectedNumberOfResults)
	{
		double factor = normedSumOfProbabilities(expectedNumberOfResults) * this.weight;
		for(int x = 0; x < xSize; x++)
			for(int y = 0; y < ySize; y++)
				for(int z = 0; z < zSize; z++)
					this.matrix[x][y][z] *= factor;
	}

	/**
	 * Die Summe aller Wahrscheinlichkeiten dieser Matrix.
	 * Lässt man eine Zufallssequenz über diese Matrix laufen, dann ist die
	 * Anzahl der Einträge mit p(x,y,z) > Zufallszahl so groß wie
	 * sumOfProbabilities() * weight
	 * 
	 * @see ProbabilityMatrix#normedSumOfProbabilities(long)
	 * @return sumOfProbabilities
	 */
	public double sumOfProbabilities()
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
	public double normedSumOfProbabilities(long expectedNumberOfResults)
	{
		return expectedNumberOfResults / sumOfProbabilities();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ProbabilityMatrix clone()
	{
		ProbabilityMatrix m2 = null;
		try
		{
			m2 = (ProbabilityMatrix) super.clone();
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
		if(!(obj instanceof ProbabilityMatrix))
			return false;
		ProbabilityMatrix other = (ProbabilityMatrix) obj;
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
					if(Math.abs(getProbability(x, y, z) - other.getProbability(x, y, z)) > 0.00000001)
						return false;
				}
			}
		}
		return true;
	}
}
