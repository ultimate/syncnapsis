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
package com.syncnapsis.universe.galaxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.syncnapsis.enums.EnumGalaxyType;
import com.syncnapsis.utils.MathUtil;
import com.syncnapsis.utils.math.Functions;
import com.syncnapsis.utils.math.Array3D;

/**
 * Diese Klasse repr�sentiert die Definition und Spezifikation einer Galaxie.
 * Die
 * Galaxy kann dabei aus verschiedenen Typen zusammen gesetzt werden und kann
 * auf diese Weise sehr komplexe Formen annehmen. Aus der
 * Wahrscheinlichkeitsverteilung f�r Systeme in diesen Galaxietypen wird so eine
 * Wahrscheinlichkeitsmatrix f�r diese Galaxie errechnet, aus der eine Liste von
 * Sektoren bzw. Systemen generiert werden kann. Der Galaxie k�nnen �ber die
 * Methoden addType* Galaxietypen hinzugef�gt werden.
 * 
 * @see Array3D
 * @author ultimate
 */
public class GalaxySpecification implements Serializable
{
	/**
	 * Default serialVersionUID
	 */
	private static final long					serialVersionUID	= 1L;

	/**
	 * Logger-Instanz
	 */
	protected transient final Logger			logger				= LoggerFactory.getLogger(getClass());

	/**
	 * Die Gr��e in x-Richtung
	 * 
	 * @see Array3D#getXSize()
	 */
	protected int								xSize;
	/**
	 * Die Gr��e in y-Richtung
	 * 
	 * @see Array3D#getYSize()
	 */
	protected int								ySize;
	/**
	 * Die Gr��e in z-Richtung
	 * 
	 * @see Array3D#getZSize()
	 */
	protected int								zSize;
	/**
	 * Die zu erwartende Anzahl von Sektoren bzw. Systemen in dieser Galaxie
	 */
	protected int								sectors;

	/**
	 * Liste der Wahrscheinlichkeitsmatrizen, die aus den verschiedenen
	 * Galaxytypen berechnet wurden
	 */
	protected ArrayList<Array3D>		matrixes;

	/**
	 * Liste der Threads f�r die parallele Berechnung der
	 * Wahrscheinlichkeitsmatrizen
	 */
	protected ArrayList<GalaxyGenerationThread>	threads;

	/**
	 * Eine optionale Gittergr��e, mit der die Galaxie nach der Berechnung
	 * skaliert wird.
	 * Es sind so auch gr��ere Galaxien m�glich, ohne das die Laufzeit der
	 * Berechnung darunter leidet.
	 */
	protected int								gridSize;

	/**
	 * Die endg�ltige berechnete Wahrscheinlichkeitsmatrix
	 */
	protected Array3D					pm;

	/**
	 * Erzeugt eine neue (noch leere) Galaxie.
	 * 
	 * @param xSize - Die Gr��e in x-Richtung
	 * @param ySize - Die Gr��e in y-Richtung
	 * @param zSize - Die Gr��e in z-Richtung
	 * @param density - die zu erwartende Dichte der Galaxie zur Berechnung der
	 *            zu erwartenden Zahl an Sektoren bzw. Systemen
	 */
	public GalaxySpecification(int xSize, int ySize, int zSize, double density)
	{
		super();
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.sectors = (int) (getVolume() * density);
		this.matrixes = new ArrayList<Array3D>();
		this.threads = new ArrayList<GalaxyGenerationThread>();
		this.gridSize = 1;
	}

	/**
	 * Erzeugt eine neue (noch leere) Galaxie.
	 * 
	 * @param xSize - Die Gr��e in x-Richtung
	 * @param ySize - Die Gr��e in y-Richtung
	 * @param zSize - Die Gr��e in z-Richtung
	 * @param sectors - Die zu erwartende Anzahl von Sektoren bzw. Systemen in
	 *            dieser Galaxie
	 */
	public GalaxySpecification(int xSize, int ySize, int zSize, int sectors)
	{
		super();
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.sectors = sectors;
		this.matrixes = new ArrayList<Array3D>();
		this.threads = new ArrayList<GalaxyGenerationThread>();
		this.gridSize = 1;
	}

	/**
	 * Erzeugt eine neue (noch leere) Galaxie.
	 * 
	 * @param xSize - Die Gr��e in x-Richtung
	 * @param ySize - Die Gr��e in y-Richtung
	 * @param zSize - Die Gr��e in z-Richtung
	 * @param density - die zu erwartende Dichte der Galaxie zur Berechnung der
	 *            zu erwartenden Zahl an Sektoren bzw. Systemen
	 * @param gridSize - Eine optionale Gittergr��e, mit der die Galaxie nach
	 *            der Berechnung skaliert wird.
	 */
	public GalaxySpecification(int xSize, int ySize, int zSize, double density, int gridSize)
	{
		super();
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.sectors = (int) (getVolume() * density);
		this.matrixes = new ArrayList<Array3D>();
		this.threads = new ArrayList<GalaxyGenerationThread>();
		this.gridSize = gridSize;
	}

	/**
	 * Erzeugt eine neue (noch leere) Galaxie.
	 * 
	 * @param xSize - Die Gr��e in x-Richtung
	 * @param ySize - Die Gr��e in y-Richtung
	 * @param zSize - Die Gr��e in z-Richtung
	 * @param sectors - Die zu erwartende Anzahl von Sektoren bzw. Systemen in
	 *            dieser Galaxie
	 * @param gridSize - Eine optionale Gittergr��e, mit der die Galaxie nach
	 *            der Berechnung skaliert wird.
	 */
	public GalaxySpecification(int xSize, int ySize, int zSize, int sectors, int gridSize)
	{
		super();
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.sectors = sectors;
		this.matrixes = new ArrayList<Array3D>();
		this.threads = new ArrayList<GalaxyGenerationThread>();
		this.gridSize = gridSize;
	}

	/**
	 * Die Gr��e in x-Richtung
	 * 
	 * @return xSize
	 */
	public int getXSize()
	{
		return xSize;
	}

	/**
	 * Die Gr��e in y-Richtung
	 * 
	 * @return ySize
	 */
	public int getYSize()
	{
		return ySize;
	}

	/**
	 * Die Gr��e in z-Richtung
	 * 
	 * @return zSize
	 */
	public int getZSize()
	{
		return zSize;
	}

	/**
	 * Dietats�chliche Gr��e in x-Richtung unter Ber�cksichtigung der
	 * Gittergr��e
	 * 
	 * @return xSize * gridSize
	 */
	public int getRealXSize()
	{
		return xSize * gridSize;
	}

	/**
	 * Dietats�chliche Gr��e in y-Richtung unter Ber�cksichtigung der
	 * Gittergr��e
	 * 
	 * @return ySize * gridSize
	 */
	public int getRealYSize()
	{
		return ySize * gridSize;
	}

	/**
	 * Dietats�chliche Gr��e in z-Richtung unter Ber�cksichtigung der
	 * Gittergr��e
	 * 
	 * @return zSize * gridSize
	 */
	public int getRealZSize()
	{
		return zSize * gridSize;
	}

	/**
	 * Die zu erwartende Anzahl von Sektoren bzw. Systemen in dieser Galaxie
	 * 
	 * @return sectors
	 */
	public int getNumberOfSectors()
	{
		return this.sectors;
	}

	/**
	 * Das Volumen der Galaxie repr�sentiert durch das Volumen des die Galaxie
	 * umschlie�enden Ellipsoids
	 * 
	 * @return das Volumen
	 */
	public double getVolume()
	{
		return 4.0 / 3.0 * Math.PI * xSize / 2.0 * ySize / 2.0 * zSize / 2.0;
	}

	/**
	 * Die zu erwartende Dichte der Galaxie berechnet aus der zu erwartenden
	 * Zahl an Sektoren bzw. Systemen
	 * 
	 * @return density = sectors / volume
	 */
	public double getDensity()
	{
		return (double) this.sectors / getVolume();
	}

	/**
	 * F�gt eine Wahrscheinlichkeitsmatrix zu der Liste der gespeicherte
	 * Matrizen hinzu
	 * 
	 * @param m - die Matrix
	 */
	public void addMatrix(Array3D m)
	{
		this.matrixes.add(m);
	}

	/**
	 * F�gt eine Liste aus Wahrscheinlichkeitsmatrizen zu der Liste der
	 * gespeicherte Matrizen hinzu
	 * 
	 * @param ms - die Matrizen
	 */
	public void addMatrixes(List<Array3D> ms)
	{
		this.matrixes.addAll(ms);
	}

	/**
	 * Die Liste aller gespeicherten Matrizen
	 * 
	 * @return die Liste
	 */
	public List<Array3D> getMatrixes()
	{
		return Collections.unmodifiableList(this.matrixes);
	}

	/**
	 * Generiert eine zuf�llige Liste von Sektoren bzw. Systemen auf Basis der
	 * Wahrscheinlichkeitsmatrix. Die Liste enth�lt in etwa so viele Eintr�ge,
	 * wie numberOfSectors. Abweichungen entstehen durch Rundungsfehler.
	 * 
	 * @return die Liste von Sektoren bzw. Systemen
	 */
	public List<int[]> generateCoordinates()
	{
		List<int[]> sectors = new ArrayList<int[]>();
		Array3D m = generateMatrix();
		logger.debug("Generating Sectors...");
		int probsAbove1 = 0;
		for(int x = m.getXMin(); x <= m.getXMax(); x++)
		{
			for(int y = m.getYMin(); y <= m.getYMax(); y++)
			{
				for(int z = m.getZMin(); z <= m.getZMax(); z++)
				{
					if(Math.random() < m.get(x, y, z))
						sectors.add(new int[] { x, y, z });
					if(m.get(x, y, z) >= 1)
						probsAbove1++;
				}
			}
		}
		logger.debug(probsAbove1 + " probabilities above 1");
		logger.debug(sectors.size() + " sectors generated");
		if(this.gridSize > 1)
			processGridSize(sectors, gridSize);
		logger.debug("Sector generation finished");
		return sectors;
	}

	public List<int[]> generateCoordinates2()
	{
		List<int[]> sectors = new ArrayList<int[]>(this.sectors);
		logger.debug("Generating Sectors...");
		double sum = 0;
		Array3D summedMatrix = new Array3D(this.pm.getXSize(), this.pm.getYSize(), this.pm.getZSize());
		for(int x = summedMatrix.getXMin(); x <= summedMatrix.getXMax(); x++)
		{
			for(int y = summedMatrix.getYMin(); y <= summedMatrix.getYMax(); y++)
			{
				for(int z = summedMatrix.getZMin(); z <= summedMatrix.getZMax(); z++)
				{
					sum += this.pm.get(x, y, z);
					summedMatrix.set(x, y, z, sum);
				}
			}
		}
		Random r = new Random();
		int i;
		int maxStep = (this.pm.getVolume() + 1) / 2;
		int step;
		double randSum;
		int[] iUsed = new int[this.sectors];
		logger.debug("preparation done");
		int collisions = 0;
		for(int s = 0; s < this.sectors; s++)
		{
			do
			{
				randSum = r.nextDouble() * sum;
				step = maxStep;
				i = step - 1;
				do
				{
					step = (step + 1) / 2;

					if(summedMatrix.get(i) < randSum)
						i += step;
					else if(summedMatrix.get(i - 1) > randSum)
						i -= step;
					else
						break;
				} while(true);

				for(int j = 0; j < s; j++)
				{
					if(iUsed[j] == i)
					{
						collisions++;
						continue;
					}
				}
				break;
			} while(true);

			iUsed[s] = i;

			sectors.add(summedMatrix.getIndexes(i));
		}
		logger.debug(collisions + " collisions");
		logger.debug(sectors.size() + " sectors generated");
		if(this.gridSize > 1)
			processGridSize(sectors, gridSize);
		logger.debug("Sector generation finished");
		return sectors;
	}

	/**
	 * Aktualisiert bzw. berechnet die Wahrscheinlichkeitsmatrix unter
	 * Ber�cksichtigung aller seit der letzten Berechnung hinzugef�gten
	 * Galaxietypen.
	 * 
	 * @return die Wahrscheinlichkeistmatrix
	 */
	public Array3D generateMatrix()
	{
		logger.debug("Generating Matrix...");
		logger.debug("Waiting for Threads to be finished...");
		for(GalaxyGenerationThread t : this.threads)
		{
			t.join2();
			this.matrixes.add(t.getMatrix());
		}
		logger.debug("All " + this.threads.size() + " Threads joined");
		logger.debug("Adding matrixes...");
		int count = 0;
		for(Array3D m : matrixes)
		{
			if(this.pm == null)
				this.pm = m.clone();
			else
				this.pm.addMatrix(m);
			logger.debug("Matrix added " + (++count) + " of " + this.matrixes.size());
		}
		this.matrixes = new ArrayList<Array3D>();
		Array3D mRet = pm.clone();
		logger.debug("Norming matrix");
		mRet.norm(this.sectors);
		logger.debug("Matrix generation finished");
		return mRet;
	}

	/**
	 * Ver�ndert eine Liste von generierten Sektoren bzw. System unter
	 * Ber�cksichtigung der Gittergr��e. Dabei werden alle Koordinaten zun�chste
	 * um die Gittergr��e gestreckt um eine gr��ere Galaxie zu erhalten.
	 * Anschlie�end wird f�r eine "nat�rlichere" Verteilung jeder Sektor bzw.
	 * System zuf�llig um bis zu einer halben Gittergr��e in eine beliebige
	 * Richtung verschoben.
	 * 
	 * @param sectors - die Liste der Sektoren bzw. Systeme
	 * @param gridSize - die Gittergr��e
	 */
	public void processGridSize(List<int[]> sectors, int gridSize)
	{
		logger.debug("Processing grid size...\nSize is " + gridSize);
		if(gridSize <= 1)
			return;
		for(int[] sector : sectors)
		{
			sector[0] = (int) Math.round(sector[0] * gridSize + Math.random() * gridSize - gridSize / 2.0);
			sector[1] = (int) Math.round(sector[1] * gridSize + Math.random() * gridSize - gridSize / 2.0);
			sector[2] = (int) Math.round(sector[2] * gridSize + Math.random() * gridSize - gridSize / 2.0);
		}
		logger.debug(sectors.size() + " sectors processed");
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix f�r den Galaxietyp Ex
	 * 
	 * @see EnumGalaxyType#Ex
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param sizeLimitation - eine Gr��enbeschr�nkung f�r den Galaxietyp
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	protected Array3D generateTypeEx(double weight, double sizeLimitation)
	{
		Array3D m = new Array3D(this.xSize, this.ySize, this.zSize);
		m.setWeight(weight);

		double r = 0;
		for(int x = m.getXMin(); x <= m.getXMax(); x++)
		{
			for(int y = m.getYMin(); y <= m.getYMax(); y++)
			{
				for(int z = m.getZMin(); z <= m.getZMax(); z++)
				{
					r = getRadius(x, y, z);
					if(r > sizeLimitation)
						m.set(x, y, z, 0);
					else
						m.set(x, y, z, Functions.gaussModified(r / sizeLimitation));
				}
			}
		}
		m.norm(this.sectors);
		return m;
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix f�r den Galaxietyp S0
	 * 
	 * @see EnumGalaxyType#S0
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param thickness - die Dicke der Linse
	 * @param sizeLimitation - eine Gr��enbeschr�nkung f�r den Galaxietyp
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	protected Array3D generateTypeS0(double weight, double thickness, double sizeLimitation)
	{
		Array3D m = new Array3D(this.xSize, this.ySize, this.zSize);
		m.setWeight(weight);

		double r = 0;
		double zNormed = 0;
		double zMax = 0;
		double p = 0;
		double sigma = 0;
		for(int x = m.getXMin(); x <= m.getXMax(); x++)
		{
			for(int y = m.getYMin(); y <= m.getYMax(); y++)
			{
				for(int z = m.getZMin(); z <= m.getZMax(); z++)
				{
					r = getRadius(x, y, z);
					zNormed = Math.abs((double) z * 2 / (double) this.zSize);
					if(r <= 1 * sizeLimitation)
					{
						zMax = Functions.circularUnit(Math.sqrt((double) x * 2 / (double) this.xSize * (double) x * 2 / (double) this.xSize
								+ (double) y * 2 / (double) this.ySize * (double) y * 2 / (double) this.ySize)
								* sizeLimitation);
						if(zMax <= 0)
							zNormed = 0;
						else
							zNormed = zNormed / zMax;
						sigma = thickness * sizeLimitation - r * thickness / 2.0;
						p = Functions.gauss(zNormed, sigma, true);
						m.set(x, y, z, p);
					}
				}
			}
		}

		m.norm(this.sectors);
		return m;
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix f�r den Galaxietyp SB0
	 * 
	 * @see EnumGalaxyType#SB0
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param thickness - die Dicke der Linse
	 * @param sizeLimitation - eine Gr��enbeschr�nkung f�r den Galaxietyp
	 * @return die Wahrscheinlichkeitsmatrix
	 * @param rotationOffset - der Drehoffset
	 */
	protected Array3D generateTypeSB0(double weight, double thickness, double sizeLimitation, double rotationOffset)
	{
		Array3D m = new Array3D(this.xSize, this.ySize, this.zSize);
		m.setWeight(weight);

		thickness = thickness / 2.0;
		double r = 0;
		double r2Normed = 0;
		double r2Max = 0;
		double p = 0;
		double sigma = 0;
		double x2, y2;
		double cosOffset = Math.cos(rotationOffset);
		double sinOffset = Math.sin(rotationOffset);
		for(int x = m.getXMin(); x <= m.getXMax(); x++)
		{
			for(int y = m.getYMin(); y <= m.getYMax(); y++)
			{
				for(int z = m.getZMin(); z <= m.getZMax(); z++)
				{
					r = getRadius(x, y, z);
					x2 = cosOffset * x - sinOffset * y;
					y2 = sinOffset * x + cosOffset * y;
					r2Normed = Math.abs(Math.sqrt(y2 * y2 + z * z) / Math.sqrt(this.ySize * this.ySize / 4.0 + this.zSize * this.zSize / 4.0));
					if(r <= 1 * sizeLimitation)
					{
						r2Max = Functions.circularUnit((double) x2 * 2 / (double) this.xSize) * sizeLimitation;
						if(r2Max <= 0)
							r2Normed = 0;
						else
							r2Normed = r2Normed / r2Max;
						sigma = thickness * sizeLimitation - r * thickness / 2.0;
						p = Functions.gauss(r2Normed, sigma, true);
						m.set(x, y, z, p);
					}
				}
			}
		}

		m.norm(this.sectors);
		return m;
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix f�r den Galaxietyp Sx
	 * 
	 * @see EnumGalaxyType#Sx
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param numberOfArms - die Anzahl der Spiralarme
	 * @param numberOfTurns - die Anzahl der Umdrehungen f�r jeden Arm
	 * @param direction - die Drehrichtung
	 * @param rotationOffset - der Drehoffset
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	@SuppressWarnings("unchecked")
	protected Array3D generateTypeSx(double weight, double numberOfArms, double numberOfTurns, double direction, double rotationOffset)
	{
		Array3D m = new Array3D(this.xSize, this.ySize, this.zSize);
		m.setWeight(weight);

		double[] phi0 = new double[(int) numberOfArms];
		double[] phiMax = new double[(int) numberOfArms];
		for(int i = 0; i < numberOfArms; i++)
		{
			phi0[i] = i * Math.PI * 2 / numberOfArms + rotationOffset;
			phiMax[i] = direction * numberOfTurns * Math.PI * 2 + phi0[i];
		}

		double xS = 0;
		double yS = 0;
		double zS = 0;
		double phi = 0;
		double iterations = 100 * numberOfTurns;
		List<?>[] spiralPoints = new List<?>[(int) numberOfArms];
		for(int j = 0; j < numberOfArms; j++)
		{
			spiralPoints[j] = new ArrayList<int[]>();
		}
		for(int i = 0; i < iterations; i++)
		{
			for(int j = 0; j < numberOfArms; j++)
			{
				phi = phi0[j] + (phiMax[j] - phi0[j]) * Math.sqrt(i / iterations);
				xS = -Math.cos(phi) * (this.xSize / 2.0 * 0.9) * (phi - phi0[j]) / (phiMax[j] - phi0[j]);
				yS = Math.sin(phi) * (this.ySize / 2.0 * 0.9) * (phi - phi0[j]) / (phiMax[j] - phi0[j]);
				((List<int[]>) spiralPoints[j]).add(new int[] { (int) xS, (int) yS, (int) zS });
			}
		}

		double rMinToSP = Double.POSITIVE_INFINITY;
		double rToSP = 0;
		int iMinIndex = 0;
		int jMinIndex = 0;
		int[] sP;
		double p = 0;
		double rMax = 0;
		double alpha = 0;
		double rDisc = 0;
		double deltaRing = 0;
		double gradient = 0.3;
		double x2, y2;
		double cosOffset = Math.cos(rotationOffset);
		double sinOffset = Math.sin(rotationOffset);
		for(int x = m.getXMin(); x <= m.getXMax(); x++)
		{
			for(int y = m.getYMin(); y <= m.getYMax(); y++)
			{
				x2 = cosOffset * x - sinOffset * y;
				y2 = sinOffset * x + cosOffset * y;
				for(int z = m.getZMin(); z <= m.getZMax(); z++)
				{
					rMinToSP = Double.POSITIVE_INFINITY;
					for(int j = 0; j < numberOfArms; j++)
					{
						for(int i = 0; i < spiralPoints[j].size(); i++)
						{
							sP = ((List<int[]>) spiralPoints[j]).get(i);
							rToSP = MathUtil.distance(x2, y2, z, sP[0], sP[1], sP[2]);
							if(rToSP < rMinToSP)
							{
								rMinToSP = rToSP;
								iMinIndex = i;
								jMinIndex = j;
							}
						}
					}
					rMax = gradient + 0.1 - iMinIndex / (double) spiralPoints[jMinIndex].size() * gradient;
					alpha = Math.atan2(y2, x2);
					rDisc = Math.abs(Math.sqrt(Math.cos(alpha) * this.xSize / 2 * Math.cos(alpha) * this.xSize / 2 + Math.sin(alpha) * this.ySize / 2
							* Math.sin(alpha) * this.ySize / 2));
					deltaRing = rDisc / numberOfArms / numberOfTurns;
					if(rMinToSP < deltaRing * rMax)
						p = Functions.gauss(rMinToSP / deltaRing / rMax);
					else
						p = 0;
					m.set(x, y, z, p);
				}
			}
		}

		m.norm(this.sectors);
		return m;
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix f�r den Galaxietyp SBx
	 * 
	 * @see EnumGalaxyType#SBx
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param numberOfTurns - die Anzahl der Umdrehungen f�r jeden Arm
	 * @param direction - die Drehrichtung
	 * @param rotationOffset - der Drehoffset
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	@SuppressWarnings("unchecked")
	protected Array3D generateTypeSBx(double weight, double numberOfTurns, double direction, double rotationOffset)
	{
		Array3D m = new Array3D(this.xSize, this.ySize, this.zSize);
		m.setWeight(weight);

		double numberOfArms = 2;

		double[] phi0 = new double[(int) numberOfArms];
		double[] phiMax = new double[(int) numberOfArms];
		for(int i = 0; i < numberOfArms; i++)
		{
			phi0[i] = i * Math.PI * 2 / numberOfArms;
			phiMax[i] = direction * numberOfTurns * Math.PI * 2 + phi0[i];
		}

		double xS = 0;
		double yS = 0;
		double zS = 0;
		double phi = 0;
		double iterations = 100 * numberOfTurns;
		List<?>[] spiralPoints = new List<?>[(int) numberOfArms];
		for(int j = 0; j < numberOfArms; j++)
		{
			spiralPoints[j] = new ArrayList<int[]>();
		}
		for(int i = 0; i < iterations; i++)
		{
			for(int j = 0; j < numberOfArms; j++)
			{
				phi = phi0[j] + (phiMax[j] - phi0[j]) * Math.sqrt(i / iterations);
				xS = -Math.cos(phi) * (this.xSize / 2.0 * 0.9) * (phi - phi0[j]) / (phiMax[j] - phi0[j]);
				xS *= Math.pow((iterations - i) / iterations, 4) + 1;
				yS = Math.sin(phi) * (this.ySize / 2.0 * 0.9) * (phi - phi0[j]) / (phiMax[j] - phi0[j]);
				if(Math.abs(phi - phi0[j]) < Math.PI)
					yS *= (double) i / (double) iterations;
				((List<int[]>) spiralPoints[j]).add(new int[] { (int) xS, (int) yS, (int) zS });
			}
		}

		double rMinToSP = Double.POSITIVE_INFINITY;
		double rToSP = 0;
		int iMinIndex = 0;
		int jMinIndex = 0;
		int[] sP;
		double p = 0;
		double rMax = 0;
		double alpha = 0;
		double rDisc = 0;
		double deltaRing = 0;
		double gradient = 0.3;
		double x2, y2;
		double cosOffset = Math.cos(rotationOffset);
		double sinOffset = Math.sin(rotationOffset);
		for(int x = m.getXMin(); x <= m.getXMax(); x++)
		{
			for(int y = m.getYMin(); y <= m.getYMax(); y++)
			{
				x2 = cosOffset * x - sinOffset * y;
				y2 = sinOffset * x + cosOffset * y;
				for(int z = m.getZMin(); z <= m.getZMax(); z++)
				{
					rMinToSP = Double.POSITIVE_INFINITY;
					for(int j = 0; j < numberOfArms; j++)
					{
						for(int i = 0; i < spiralPoints[j].size(); i++)
						{
							sP = ((List<int[]>) spiralPoints[j]).get(i);
							rToSP = MathUtil.distance(x2, y2, z, sP[0], sP[1], sP[2]);
							if(rToSP < rMinToSP)
							{
								rMinToSP = rToSP;
								iMinIndex = i;
								jMinIndex = j;
							}
						}
					}
					rMax = gradient + 0.1 - iMinIndex / (double) spiralPoints[jMinIndex].size() * gradient;
					alpha = Math.atan2(y2, x2);
					rDisc = Math.abs(Math.sqrt(Math.cos(alpha) * this.xSize / 2 * Math.cos(alpha) * this.xSize / 2 + Math.sin(alpha) * this.ySize / 2
							* Math.sin(alpha) * this.ySize / 2));
					deltaRing = rDisc / numberOfArms / numberOfTurns;
					if(rMinToSP < deltaRing * rMax)
						p = Functions.gauss(rMinToSP / deltaRing / rMax);
					else
						p = 0;
					m.set(x, y, z, p);
				}
			}
		}

		m.norm(this.sectors);
		return m;
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix f�r den Galaxietyp Rx
	 * 
	 * @see EnumGalaxyType#Rx
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param numberOfRings - die Anzahl der Ringe
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	@SuppressWarnings("unchecked")
	protected Array3D generateTypeRx(double weight, double numberOfRings)
	{
		Array3D m = new Array3D(this.xSize, this.ySize, this.zSize);
		m.setWeight(weight);

		double xS = 0;
		double yS = 0;
		double zS = 0;
		double phi = 0;
		double iterations;
		List<?>[] ringPoints = new List<?>[(int) numberOfRings];
		for(int j = 0; j < numberOfRings; j++)
		{
			ringPoints[j] = new ArrayList<int[]>();
		}
		for(int j = 0; j < numberOfRings; j++)
		{
			iterations = 50 * (j + 1);
			for(int i = 0; i < iterations; i++)
			{
				phi = i / iterations * Math.PI * 2;
				xS = -Math.cos(phi) * (this.xSize / 2.0 * 0.9) * (j + 1) / numberOfRings;
				yS = Math.sin(phi) * (this.ySize / 2.0 * 0.9) * (j + 1) / numberOfRings;
				((List<int[]>) ringPoints[j]).add(new int[] { (int) xS, (int) yS, (int) zS });
			}
		}

		double rMinToSP = Double.POSITIVE_INFINITY;
		double rToSP = 0;
		int jMinIndex = 0;
		int[] sP;
		double p = 0;
		double rMax = 0;
		double alpha = 0;
		double rDisc = 0;
		double deltaRing = 0;
		double gradient = 0.3;
		for(int x = m.getXMin(); x <= m.getXMax(); x++)
		{
			for(int y = m.getYMin(); y <= m.getYMax(); y++)
			{
				for(int z = m.getZMin(); z <= m.getZMax(); z++)
				{
					rMinToSP = Double.POSITIVE_INFINITY;
					for(int j = 0; j < numberOfRings; j++)
					{
						for(int i = 0; i < ringPoints[j].size(); i++)
						{
							sP = ((List<int[]>) ringPoints[j]).get(i);
							rToSP = MathUtil.distance(x, y, z, sP[0], sP[1], sP[2]);
							if(rToSP < rMinToSP)
							{
								rMinToSP = rToSP;
								jMinIndex = j;
							}
						}
					}
					rMax = gradient + 0.2 - jMinIndex / numberOfRings * gradient;
					alpha = Math.atan2(y, x);
					rDisc = Math.abs(Math.sqrt(Math.cos(alpha) * this.xSize / 2 * Math.cos(alpha) * this.xSize / 2 + Math.sin(alpha) * this.ySize / 2
							* Math.sin(alpha) * this.ySize / 2));
					deltaRing = rDisc / numberOfRings;
					if(rMinToSP < deltaRing * rMax)
						p = Functions.gauss(rMinToSP / deltaRing / rMax);
					else
						p = 0;
					m.set(x, y, z, p);
				}
			}
		}

		m.norm(this.sectors);
		return m;
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix f�r den Galaxietyp Ax
	 * 
	 * @see EnumGalaxyType#Ax
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param radius - der Radius f�r den Bogen
	 * @param phiStart - der Startwinkel f�r den Bogen in Rad
	 * @param phiEnd - der Endwinkel f�r den Bogen in Rad
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	protected Array3D generateTypeAx(double weight, double radius, double phiStart, double phiEnd)
	{
		Array3D m = new Array3D(this.xSize, this.ySize, this.zSize);
		m.setWeight(weight);

		double xS = 0;
		double yS = 0;
		double zS = 0;
		double phi = 0;
		double iterations = 100;
		List<int[]> arcPoints = new ArrayList<int[]>();
		for(int i = 0; i < iterations; i++)
		{
			phi = i / iterations * (phiEnd - phiStart) + phiStart;
			xS = -Math.cos(phi) * (this.xSize / 2.0 * 0.9) * radius;
			yS = Math.sin(phi) * (this.ySize / 2.0 * 0.9) * radius;
			arcPoints.add(new int[] { (int) xS, (int) yS, (int) zS });
		}

		double rMinToSP = Double.POSITIVE_INFINITY;
		double rToSP = 0;
		int iMinIndex = 0;
		int[] sP;
		double p = 0;
		double rMax = 0;
		double alpha = 0;
		double rDisc = 0;
		double deltaRing = 0;
		double gradient = 0.3;
		for(int x = m.getXMin(); x <= m.getXMax(); x++)
		{
			for(int y = m.getYMin(); y <= m.getYMax(); y++)
			{
				for(int z = m.getZMin(); z <= m.getZMax(); z++)
				{
					rMinToSP = Double.POSITIVE_INFINITY;
					for(int i = 0; i < arcPoints.size(); i++)
					{
						sP = arcPoints.get(i);
						rToSP = MathUtil.distance(x, y, z, sP[0], sP[1], sP[2]);
						if(rToSP < rMinToSP)
						{
							rMinToSP = rToSP;
							iMinIndex = i;
						}
					}
					rMax = radius * gradient * (0.3 - (iMinIndex - iterations / 2) * (iMinIndex - iterations / 2) / iterations / iterations);
					alpha = Math.atan2(y, x);
					rDisc = Math.abs(Math.sqrt(Math.cos(alpha) * this.xSize / 2 * Math.cos(alpha) * this.xSize / 2 + Math.sin(alpha) * this.ySize / 2
							* Math.sin(alpha) * this.ySize / 2));
					deltaRing = rDisc;
					if(rMinToSP < deltaRing * rMax)
						p = Functions.gauss(rMinToSP / deltaRing / rMax);
					else
						p = 0;
					m.set(x, y, z, p);
				}
			}
		}

		m.norm(this.sectors);
		return m;
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp Ex hinzu (weight = 1, sizeLimitation =
	 * 1)
	 * 
	 * @see EnumGalaxyType#Ex
	 */
	public void addTypeEx()
	{
		this.addTypeEx(1.0, 1.0);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp Ex hinzu (sizeLimitation = 1)
	 * 
	 * @see EnumGalaxyType#Ex
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 */
	public void addTypeEx(double weight)
	{
		this.addTypeEx(weight, 1.0);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp Ex hinzu
	 * 
	 * @see EnumGalaxyType#Ex
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param sizeLimitation - eine Gr��enbeschr�nkung f�r den Galaxietyp
	 */
	public void addTypeEx(double weight, double sizeLimitation)
	{
		GalaxyGenerationThread tEx = new GalaxyGenerationThread(EnumGalaxyType.Ex, sizeLimitation);
		tEx.start(weight);
		this.threads.add(tEx);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp S0 hinzu (weight = 1, sizeLimitation =
	 * 1)
	 * 
	 * @see EnumGalaxyType#S0
	 * @param thickness - die Dicke der Linse
	 */
	public void addTypeS0(double thickness)
	{
		this.addTypeS0(1.0, thickness, 1.0);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp S0 hinzu
	 * 
	 * @see EnumGalaxyType#S0
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param thickness - die Dicke der Linse
	 * @param sizeLimitation - eine Gr��enbeschr�nkung f�r den Galaxietyp
	 */
	public void addTypeS0(double weight, double thickness, double sizeLimitation)
	{
		addTypeEx(1.0 * weight, sizeLimitation);
		GalaxyGenerationThread tS0 = new GalaxyGenerationThread(EnumGalaxyType.S0, thickness, sizeLimitation);
		tS0.start(0.75 * weight);
		this.threads.add(tS0);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp SB0 hinzu (weight = 1, sizeLimitation
	 * = 1)
	 * 
	 * @see EnumGalaxyType#SB0
	 * @param thickness - die Dicke der Linse
	 * @param rotationOffset - der Drehoffset
	 */
	public void addTypeSB0(double thickness, double rotationOffset)
	{
		this.addTypeSB0(1.0, thickness, 1.0, rotationOffset);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp SB0 hinzu (weight = 1, sizeLimitation
	 * = 1)
	 * 
	 * @see EnumGalaxyType#SB0
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param thickness - die Dicke der Linse
	 * @param sizeLimitation - eine Gr��enbeschr�nkung f�r den Galaxietyp
	 * @param rotationOffset - der Drehoffset
	 */
	public void addTypeSB0(double weight, double thickness, double sizeLimitation, double rotationOffset)
	{
		addTypeEx(1.0 * weight, sizeLimitation);
		GalaxyGenerationThread tSB0 = new GalaxyGenerationThread(EnumGalaxyType.SB0, thickness, sizeLimitation, rotationOffset);
		tSB0.start(0.5 * weight);
		this.threads.add(tSB0);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp Sx hinzu (weight = 1)
	 * 
	 * @see EnumGalaxyType#Sx
	 * @param numberOfArms - die Anzahl der Spiralarme
	 * @param numberOfTurns - die Anzahl der Umdrehungen f�r jeden Arm
	 * @param direction - die Drehrichtung
	 * @param rotationOffset - der Drehoffset
	 */
	public void addTypeSx(int numberOfArms, double numberOfTurns, double direction, double rotationOffset)
	{
		this.addTypeSx(1.0, numberOfArms, numberOfTurns, direction, rotationOffset);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp Sx hinzu
	 * 
	 * @see EnumGalaxyType#Sx
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param numberOfArms - die Anzahl der Spiralarme
	 * @param numberOfTurns - die Anzahl der Umdrehungen f�r jeden Arm
	 * @param direction - die Drehrichtung
	 * @param rotationOffset - der Drehoffset
	 */
	public void addTypeSx(double weight, int numberOfArms, double numberOfTurns, double direction, double rotationOffset)
	{
		addTypeS0(0.7 * weight, 0.2, 1.0);
		GalaxyGenerationThread tSx = new GalaxyGenerationThread(EnumGalaxyType.Sx, numberOfArms, numberOfTurns, direction, rotationOffset);
		tSx.start(0.75 * weight);
		this.threads.add(tSx);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp SBx hinzu (weight = 1)
	 * 
	 * @see EnumGalaxyType#SBx
	 * @param numberOfTurns - die Anzahl der Umdrehungen f�r jeden Arm
	 * @param direction - die Drehrichtung
	 * @param rotationOffset - der Drehoffset
	 */
	public void addTypeSBx(double numberOfTurns, double direction, double rotationOffset)
	{
		this.addTypeSBx(1.0, numberOfTurns, direction, rotationOffset);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp SBx hinzu
	 * 
	 * @see EnumGalaxyType#SBx
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param numberOfTurns - die Anzahl der Umdrehungen f�r jeden Arm
	 * @param direction - die Drehrichtung
	 * @param rotationOffset - der Drehoffset
	 */
	public void addTypeSBx(double weight, double numberOfTurns, double direction, double rotationOffset)
	{
		addTypeS0(0.5 * weight, 0.2, 1.0);
		addTypeSB0(0.3 * weight, 0.2, 0.6, rotationOffset);
		GalaxyGenerationThread tSBx = new GalaxyGenerationThread(EnumGalaxyType.SBx, numberOfTurns, direction, rotationOffset);
		tSBx.start(0.8 * weight);
		this.threads.add(tSBx);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp Rx hinzu (weight = 1)
	 * 
	 * @see EnumGalaxyType#Rx
	 * @param numberOfRings - die Anzahl der Ringe
	 */
	public void addTypeRx(int numberOfRings)
	{
		this.addTypeRx(1.0, numberOfRings);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp Rx hinzu
	 * 
	 * @see EnumGalaxyType#Rx
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param numberOfRings - die Anzahl der Ringe
	 */
	public void addTypeRx(double weight, int numberOfRings)
	{
		addTypeS0(0.7 * weight, 0.2, 1.0);
		GalaxyGenerationThread tRx = new GalaxyGenerationThread(EnumGalaxyType.Rx, numberOfRings);
		tRx.start(0.75 * weight);
		this.threads.add(tRx);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp Ax hinzu (weight = 1)
	 * 
	 * @see EnumGalaxyType#Ax
	 * @param radius - der Radius f�r den Bogen
	 * @param phiStart - der Startwinkel f�r den Bogen in Rad
	 * @param phiEnd - der Endwinkel f�r den Bogen in Rad
	 */
	public void addTypeAx(double radius, double phiStart, double phiEnd)
	{
		this.addTypeAx(1.0, radius, phiStart, phiEnd);
	}

	/**
	 * F�gt dieser Galaxie den Galaxietyp Ax hinzu
	 * 
	 * @see EnumGalaxyType#Ax
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param radius - der Radius f�r den Bogen
	 * @param phiStart - der Startwinkel f�r den Bogen in Rad
	 * @param phiEnd - der Endwinkel f�r den Bogen in Rad
	 */
	public void addTypeAx(double weight, double radius, double phiStart, double phiEnd)
	{
		GalaxyGenerationThread tAx = new GalaxyGenerationThread(EnumGalaxyType.Ax, radius, phiStart, phiEnd);
		tAx.start(weight);
		this.threads.add(tAx);
	}

	/**
	 * Der Abstand bzw. Radius eines Punktes (x,y,z) zum Mittelpunkt der Galaxie
	 * 
	 * @param x - die Koordinate in y-Richtung
	 * @param y - die Koordinate in y-Richtung
	 * @param z - die Koordinate in y-Richtung
	 * @return der Radius
	 */
	public double getRadius(int x, int y, int z)
	{
		return Math.sqrt((x / (double) this.xSize) * (x / (double) this.xSize) * 4 + (y / (double) this.ySize) * (y / (double) this.ySize) * 4
				+ (z / (double) this.zSize) * (z / (double) this.zSize) * 4);
	}

	/**
	 * Interne Thread-Klasse f�r die Berechnung der Wahrscheinlichkeitsmatrix
	 * eines Galaxietyps. Auf diese Weise k�nnen mehrere Galaxietypen
	 * hinzugef�gt werden ohne, dass nach jedem Schritt auf die Berechnung
	 * gewartet werden muss. Die Threads werden in einer Liste gespeichert,
	 * damit sie gejoint werden k�nnen, sobald ihr Ergebnis f�r weitere
	 * Berechnungen notwendig ist.
	 * 
	 * @author ultimate
	 */
	private class GalaxyGenerationThread extends Thread
	{
		/**
		 * Logger-Instanz
		 */
		protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

		/**
		 * Der zu generierende Galaxietype
		 */
		private EnumGalaxyType				type;
		/**
		 * Die berechnete Wahrscheinlichkeitsmatrix
		 */
		private Array3D			m;
		/**
		 * Die Gewichtung dieser Wahrscheinlichkeitsmatrix
		 */
		private double						weight;
		/**
		 * Die f�r die Berechnung notwendigen zus�tzlichen Parameter
		 * Siehe generateType*(..) und addType*(..)
		 */
		private double[]					params;

		/**
		 * Erzeugt einen neuen Thread
		 * 
		 * @param type - Der zu generierende Galaxietype
		 * @param params - Die f�r die Berechnung notwendigen zus�tzlichen
		 *            Parameter
		 */
		public GalaxyGenerationThread(EnumGalaxyType type, double... params)
		{
			Assert.notNull(type, "type is null");

			this.type = type;
			this.m = null;
			this.weight = 1.0;
			this.params = params;
			logger.debug("type: " + type + " - Thread initialized");
		}

		/**
		 * Startet die Berechnung der Wahrscheinlichkeitsmatrix mit einer
		 * bestimmten Gewichtung.
		 * 
		 * @see Thread#start()
		 * @param weight - Die Gewichtung dieser Wahrscheinlichkeitsmatrix
		 */
		public void start(double weight)
		{
			this.weight = weight;
			this.start();
		}

		/**
		 * F�hrt die Berechnung mit Hilfe von generateType*(..) durch.
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run()
		{
			logger.debug("type: " + type + " - starting generation");
			if(this.type.equals(EnumGalaxyType.Ex))
				m = generateTypeEx(this.weight, params[0]);
			if(this.type.equals(EnumGalaxyType.S0))
				m = generateTypeS0(this.weight, params[0], params[1]);
			if(this.type.equals(EnumGalaxyType.SB0))
				m = generateTypeSB0(this.weight, params[0], params[1], params[2]);
			if(this.type.equals(EnumGalaxyType.Sx))
				m = generateTypeSx(this.weight, params[0], params[1], params[2], params[3]);
			if(this.type.equals(EnumGalaxyType.SBx))
				m = generateTypeSBx(this.weight, params[0], params[1], params[2]);
			if(this.type.equals(EnumGalaxyType.Rx))
				m = generateTypeRx(this.weight, params[0]);
			if(this.type.equals(EnumGalaxyType.Ax))
				m = generateTypeAx(this.weight, params[0], params[1], params[2]);
			m.setWeight(this.weight);
			logger.debug("type: " + type + " - norming matrix");
			m.norm(getNumberOfSectors());
			logger.debug("type: " + type + " - generation finished");
		}

		/**
		 * Join, bei dem die InterruptedException gefangen wird.
		 * 
		 * @see Thread#join()
		 */
		public void join2()
		{
			try
			{
				this.join();
			}
			catch(InterruptedException e)
			{
			}
		}

		/**
		 * Gibt nach der Berechnung die berechnete Wahrscheinlichkeitsmatrix
		 * zur�ck
		 * 
		 * @return die berechnete Wahrscheinlichkeitsmatrix
		 */
		public Array3D getMatrix()
		{
			Assert.notNull(m, "m is null");
			return m;
		}
	}

	/**
	 * @see Serializable
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		ois.defaultReadObject();
	}

	// public static void main(String[] args)
	// {
	// int xMin = -2;
	// int xMax = 5;
	// int yMin = -4;
	// int yMax = 10;
	// int zMin = -6;
	// int zMax = 15;
	//
	// int i;
	// int count = 0;
	// int x2, y2, z2;
	// for(int x = xMin; x <= xMax; x++)
	// {
	// for(int y = yMin; y <= yMax; y++)
	// {
	// for(int z = zMin; z <= zMax; z++)
	// {
	// i = ((x - xMin) * (yMax - yMin + 1) + (y - yMin)) * (zMax - zMin + 1) + (z - zMin);
	// z2 = i % (zMax - zMin + 1) + zMin;
	// y2 = (i - (z2 - zMin)) / (zMax - zMin + 1) % (yMax - yMin + 1) + yMin;
	// x2 = (((i - (z2 - zMin)) / (zMax - zMin + 1)) - (y2 - yMin)) / (yMax - yMin + 1) + xMin;
	// System.out.println("x=" + x + " y=" + y + " z=" + z + " -- i=" + i + " vs. " + count +
	// " -- x2=" + x2 + " y2=" + y2 + " z2=" + z2);
	// if(x != x2) return;
	// if(y != y2) return;
	// if(z != z2) return;
	// if(i != count) return;
	// count++;
	// }
	// }
	// }
	// System.out.println("done!");
	// }
}
