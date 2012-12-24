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
		return (-xSize + 1) / 2;
	}

	/**
	 * Der größte Index für x
	 * 
	 * @return xMin
	 */
	public int getXMax()
	{
		return xSize / 2;
	}

	/**
	 * Der kleinste Index für y
	 * 
	 * @return yMin
	 */
	public int getYMin()
	{
		return (-ySize + 1) / 2;
	}

	/**
	 * Der größte Index für y
	 * 
	 * @return yMin
	 */
	public int getYMax()
	{
		return ySize / 2;
	}

	/**
	 * Der kleinste Index für z
	 * 
	 * @return zMin
	 */
	public int getZMin()
	{
		return (-zSize + 1) / 2;
	}

	/**
	 * Der größte Index für z
	 * 
	 * @return zMin
	 */
	public int getZMax()
	{
		return zSize / 2;
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
		Assert.isTrue(x >= getXMin() && x <= getXMax(), "x not in range: " + x + " Range: " + getXMin() + ".." + getXMax());
		Assert.isTrue(y >= getYMin() && y <= getYMax(), "y not in range: " + y + " Range: " + getYMin() + ".." + getYMax());
		Assert.isTrue(z >= getZMin() && z <= getZMax(), "z not in range: " + z + " Range: " + getZMin() + ".." + getZMax());
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
	public void norm(long expectedNumberOfResults)
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
		for(int x = getXMin(); x <= getXMax(); x++)
		{
			for(int y = getYMin(); y <= getYMax(); y++)
			{
				for(int z = getZMin(); z <= getZMax(); z++)
				{
					if(Math.abs(getProbability(x, y, z) - other.getProbability(x, y, z)) > 0.00000001)
						return false;
				}
			}
		}
		return true;
	}
}
