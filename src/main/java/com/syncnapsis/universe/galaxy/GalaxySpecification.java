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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.syncnapsis.enums.EnumGalaxyType;
import com.syncnapsis.utils.MathUtil;
import com.syncnapsis.utils.math.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Diese Klasse repräsentiert die Definition und Spezifikation einer Galaxie.
 * Die
 * Galaxy kann dabei aus verschiedenen Typen zusammen gesetzt werden und kann
 * auf diese Weise sehr komplexe Formen annehmen. Aus der
 * Wahrscheinlichkeitsverteilung für Systeme in diesen Galaxietypen wird so eine
 * Wahrscheinlichkeitsmatrix für diese Galaxie errechnet, aus der eine Liste von
 * Sektoren bzw. Systemen generiert werden kann. Der Galaxie können über die
 * Methoden addType* Galaxietypen hinzugefügt werden.
 * 
 * @see ProbabilityMatrix
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
	protected transient final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Die Größe in x-Richtung
	 * 
	 * @see ProbabilityMatrix#getXSize()
	 */
	protected int								xSize;
	/**
	 * Die Größe in y-Richtung
	 * 
	 * @see ProbabilityMatrix#getYSize()
	 */
	protected int								ySize;
	/**
	 * Die Größe in z-Richtung
	 * 
	 * @see ProbabilityMatrix#getZSize()
	 */
	protected int								zSize;
	/**
	 * Die zu erwartende Anzahl von Sektoren bzw. Systemen in dieser Galaxie
	 */
	protected long								sectors;

	/**
	 * Liste der Wahrscheinlichkeitsmatrizen, die aus den verschiedenen
	 * Galaxytypen berechnet wurden
	 */
	protected ArrayList<ProbabilityMatrix>		matrixes;

	/**
	 * Liste der Threads für die parallele Berechnung der
	 * Wahrscheinlichkeitsmatrizen
	 */
	protected ArrayList<GalaxyGenerationThread>	threads;

	/**
	 * Eine optionale Gittergröße, mit der die Galaxie nach der Berechnung
	 * skaliert wird.
	 * Es sind so auch größere Galaxien möglich, ohne das die Laufzeit der
	 * Berechnung darunter leidet.
	 */
	protected int								gridSize;

	/**
	 * Die endgültige berechnete Wahrscheinlichkeitsmatrix
	 */
	protected ProbabilityMatrix					pm;

	/**
	 * Erzeugt eine neue (noch leere) Galaxie.
	 * 
	 * @param xSize - Die Größe in x-Richtung
	 * @param ySize - Die Größe in y-Richtung
	 * @param zSize - Die Größe in z-Richtung
	 * @param density - die zu erwartende Dichte der Galaxie zur Berechnung der
	 *            zu erwartenden Zahl an Sektoren bzw. Systemen
	 */
	public GalaxySpecification(int xSize, int ySize, int zSize, double density)
	{
		super();
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.sectors = (long) (getVolume() * density);
		this.matrixes = new ArrayList<ProbabilityMatrix>();
		this.threads = new ArrayList<GalaxyGenerationThread>();
		this.gridSize = 1;
	}

	/**
	 * Erzeugt eine neue (noch leere) Galaxie.
	 * 
	 * @param xSize - Die Größe in x-Richtung
	 * @param ySize - Die Größe in y-Richtung
	 * @param zSize - Die Größe in z-Richtung
	 * @param sectors - Die zu erwartende Anzahl von Sektoren bzw. Systemen in
	 *            dieser Galaxie
	 */
	public GalaxySpecification(int xSize, int ySize, int zSize, long sectors)
	{
		super();
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.sectors = sectors;
		this.matrixes = new ArrayList<ProbabilityMatrix>();
		this.threads = new ArrayList<GalaxyGenerationThread>();
		this.gridSize = 1;
	}

	/**
	 * Erzeugt eine neue (noch leere) Galaxie.
	 * 
	 * @param xSize - Die Größe in x-Richtung
	 * @param ySize - Die Größe in y-Richtung
	 * @param zSize - Die Größe in z-Richtung
	 * @param density - die zu erwartende Dichte der Galaxie zur Berechnung der
	 *            zu erwartenden Zahl an Sektoren bzw. Systemen
	 * @param gridSize - Eine optionale Gittergröße, mit der die Galaxie nach
	 *            der Berechnung skaliert wird.
	 */
	public GalaxySpecification(int xSize, int ySize, int zSize, double density, int gridSize)
	{
		super();
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.sectors = (long) (getVolume() * density);
		this.matrixes = new ArrayList<ProbabilityMatrix>();
		this.threads = new ArrayList<GalaxyGenerationThread>();
		this.gridSize = gridSize;
	}

	/**
	 * Erzeugt eine neue (noch leere) Galaxie.
	 * 
	 * @param xSize - Die Größe in x-Richtung
	 * @param ySize - Die Größe in y-Richtung
	 * @param zSize - Die Größe in z-Richtung
	 * @param sectors - Die zu erwartende Anzahl von Sektoren bzw. Systemen in
	 *            dieser Galaxie
	 * @param gridSize - Eine optionale Gittergröße, mit der die Galaxie nach
	 *            der Berechnung skaliert wird.
	 */
	public GalaxySpecification(int xSize, int ySize, int zSize, long sectors, int gridSize)
	{
		super();
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.sectors = sectors;
		this.matrixes = new ArrayList<ProbabilityMatrix>();
		this.threads = new ArrayList<GalaxyGenerationThread>();
		this.gridSize = gridSize;
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
	 * Die Größe in z-Richtung
	 * 
	 * @return zSize
	 */
	public int getZSize()
	{
		return zSize;
	}

	/**
	 * Dietatsächliche Größe in x-Richtung unter Berücksichtigung der
	 * Gittergröße
	 * 
	 * @return xSize * gridSize
	 */
	public int getRealXSize()
	{
		return xSize * gridSize;
	}

	/**
	 * Dietatsächliche Größe in y-Richtung unter Berücksichtigung der
	 * Gittergröße
	 * 
	 * @return ySize * gridSize
	 */
	public int getRealYSize()
	{
		return ySize * gridSize;
	}

	/**
	 * Dietatsächliche Größe in z-Richtung unter Berücksichtigung der
	 * Gittergröße
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
	public long getNumberOfSectors()
	{
		return this.sectors;
	}

	/**
	 * Das Volumen der Galaxie repräsentiert durch das Volumen des die Galaxie
	 * umschließenden Ellipsoids
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
	 * Fügt eine Wahrscheinlichkeitsmatrix zu der Liste der gespeicherte
	 * Matrizen hinzu
	 * 
	 * @param m - die Matrix
	 */
	public void addMatrix(ProbabilityMatrix m)
	{
		this.matrixes.add(m);
	}

	/**
	 * Fügt eine Liste aus Wahrscheinlichkeitsmatrizen zu der Liste der
	 * gespeicherte Matrizen hinzu
	 * 
	 * @param ms - die Matrizen
	 */
	public void addMatrixes(List<ProbabilityMatrix> ms)
	{
		this.matrixes.addAll(ms);
	}

	/**
	 * Die Liste aller gespeicherten Matrizen
	 * 
	 * @return die Liste
	 */
	public List<ProbabilityMatrix> getMatrixes()
	{
		return Collections.unmodifiableList(this.matrixes);
	}

	/**
	 * Generiert eine zufällige Liste von Sektoren bzw. Systemen auf Basis der
	 * Wahrscheinlichkeitsmatrix. Die Liste enthält in etwa so viele Einträge,
	 * wie numberOfSectors. Abweichungen entstehen durch Rundungsfehler.
	 * 
	 * @return die Liste von Sektoren bzw. Systemen
	 */
	public List<int[]> generateCoordinates()
	{
		List<int[]> sectors = new ArrayList<int[]>();
		ProbabilityMatrix m = generateMatrix();
		logger.debug("Generating Sectors...");
		for(int x = m.getXMin(); x <= m.getXMax(); x++)
		{
			for(int y = m.getYMin(); y <= m.getYMax(); y++)
			{
				for(int z = m.getZMin(); z <= m.getZMax(); z++)
				{
					if(Math.random() < m.getProbability(x, y, z))
						sectors.add(new int[] { x, y, z });
					if(m.getProbability(x, y, z) >= 1)
						logger.warn("Sector: " + x + "," + y + "," + z + " - Probability > 1");
				}
			}
		}
		logger.debug(sectors.size() + " sectors generated");
		if(this.gridSize > 1)
			processGridSize(sectors, gridSize);
		logger.debug("Sector generation finished");
		return sectors;
	}

	/**
	 * Aktualisiert bzw. berechnet die Wahrscheinlichkeitsmatrix unter
	 * Berücksichtigung aller seit der letzten Berechnung hinzugefügten
	 * Galaxietypen.
	 * 
	 * @return die Wahrscheinlichkeistmatrix
	 */
	public ProbabilityMatrix generateMatrix()
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
		for(ProbabilityMatrix m : matrixes)
		{
			if(this.pm == null)
				this.pm = m.clone();
			else
				this.pm.addMatrix(m);
			logger.debug("Matrix added " + (++count) + " of " + this.matrixes.size());
		}
		this.matrixes = new ArrayList<ProbabilityMatrix>();
		ProbabilityMatrix mRet = pm.clone();
		logger.debug("Norming matrix");
		mRet.norm(this.sectors);
		logger.debug("Matrix generation finished");
		return mRet;
	}

	/**
	 * Verändert eine Liste von generierten Sektoren bzw. System unter
	 * Berücksichtigung der Gittergröße. Dabei werden alle Koordinaten zunächste
	 * um die Gittergröße gestreckt um eine größere Galaxie zu erhalten.
	 * Anschließend wird für eine "natürlichere" Verteilung jeder Sektor bzw.
	 * System zufällig um bis zu einer halben Gittergröße in eine beliebige
	 * Richtung verschoben.
	 * 
	 * @param sectors - die Liste der Sektoren bzw. Systeme
	 * @param gridSize - die Gittergröße
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
	 * Berechnet die Wahrscheinlichkeitsmatrix für den Galaxietyp Ex
	 * 
	 * @see EnumGalaxyType#Ex
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param sizeLimitation - eine Größenbeschränkung für den Galaxietyp
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	protected ProbabilityMatrix generateTypeEx(double weight, double sizeLimitation)
	{
		ProbabilityMatrix m = new ProbabilityMatrix(this.xSize, this.ySize, this.zSize);
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
						m.setProbability(x, y, z, 0);
					else
						m.setProbability(x, y, z, Functions.gaussModified(r / sizeLimitation));
				}
			}
		}
		m.norm(this.sectors);
		return m;
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix für den Galaxietyp S0
	 * 
	 * @see EnumGalaxyType#S0
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param thickness - die Dicke der Linse
	 * @param sizeLimitation - eine Größenbeschränkung für den Galaxietyp
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	protected ProbabilityMatrix generateTypeS0(double weight, double thickness, double sizeLimitation)
	{
		ProbabilityMatrix m = new ProbabilityMatrix(this.xSize, this.ySize, this.zSize);
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
						m.setProbability(x, y, z, p);
					}
				}
			}
		}

		m.norm(this.sectors);
		return m;
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix für den Galaxietyp SB0
	 * 
	 * @see EnumGalaxyType#SB0
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param thickness - die Dicke der Linse
	 * @param sizeLimitation - eine Größenbeschränkung für den Galaxietyp
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	protected ProbabilityMatrix generateTypeSB0(double weight, double thickness, double sizeLimitation)
	{
		ProbabilityMatrix m = new ProbabilityMatrix(this.xSize, this.ySize, this.zSize);
		m.setWeight(weight);

		thickness = thickness / 2.0;
		double r = 0;
		double r2Normed = 0;
		double r2Max = 0;
		double p = 0;
		double sigma = 0;
		for(int x = m.getXMin(); x <= m.getXMax(); x++)
		{
			for(int y = m.getYMin(); y <= m.getYMax(); y++)
			{
				for(int z = m.getZMin(); z <= m.getZMax(); z++)
				{
					r = getRadius(x, y, z);
					r2Normed = Math.abs(Math.sqrt(y * y + z * z) / Math.sqrt(this.ySize * this.ySize / 4.0 + this.zSize * this.zSize / 4.0));
					if(r <= 1 * sizeLimitation)
					{
						r2Max = Functions.circularUnit((double) x * 2 / (double) this.xSize) * sizeLimitation;
						if(r2Max <= 0)
							r2Normed = 0;
						else
							r2Normed = r2Normed / r2Max;
						sigma = thickness * sizeLimitation - r * thickness / 2.0;
						p = Functions.gauss(r2Normed, sigma, true);
						m.setProbability(x, y, z, p);
					}
				}
			}
		}

		m.norm(this.sectors);
		return m;
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix für den Galaxietyp Sx
	 * 
	 * @see EnumGalaxyType#Sx
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param numberOfArms - die Anzahl der Spiralarme
	 * @param numberOfTurns - die Anzahl der Umdrehungen für jeden Arm
	 * @param direction - die Drehrichtung
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	@SuppressWarnings("unchecked")
	protected ProbabilityMatrix generateTypeSx(double weight, double numberOfArms, double numberOfTurns, double direction)
	{
		ProbabilityMatrix m = new ProbabilityMatrix(this.xSize, this.ySize, this.zSize);
		m.setWeight(weight);

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
		for(int x = m.getXMin(); x <= m.getXMax(); x++)
		{
			for(int y = m.getYMin(); y <= m.getYMax(); y++)
			{
				for(int z = m.getZMin(); z <= m.getZMax(); z++)
				{
					rMinToSP = Double.POSITIVE_INFINITY;
					for(int j = 0; j < numberOfArms; j++)
					{
						for(int i = 0; i < spiralPoints[j].size(); i++)
						{
							sP = ((List<int[]>) spiralPoints[j]).get(i);
							rToSP = MathUtil.distance(x, y, z, sP[0], sP[1], sP[2]);
							if(rToSP < rMinToSP)
							{
								rMinToSP = rToSP;
								iMinIndex = i;
								jMinIndex = j;
							}
						}
					}
					rMax = gradient + 0.1 - iMinIndex / (double) spiralPoints[jMinIndex].size() * gradient;
					alpha = Math.atan2(y, x);
					rDisc = Math.abs(Math.sqrt(Math.cos(alpha) * this.xSize / 2 * Math.cos(alpha) * this.xSize / 2 + Math.sin(alpha) * this.ySize / 2
							* Math.sin(alpha) * this.ySize / 2));
					deltaRing = rDisc / numberOfArms / numberOfTurns;
					if(rMinToSP < deltaRing * rMax)
						p = Functions.gauss(rMinToSP / deltaRing / rMax);
					else
						p = 0;
					m.setProbability(x, y, z, p);
				}
			}
		}

		m.norm(this.sectors);
		return m;
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix für den Galaxietyp SBx
	 * 
	 * @see EnumGalaxyType#SBx
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param numberOfTurns - die Anzahl der Umdrehungen für jeden Arm
	 * @param direction - die Drehrichtung
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	@SuppressWarnings("unchecked")
	protected ProbabilityMatrix generateTypeSBx(double weight, double numberOfTurns, double direction)
	{
		ProbabilityMatrix m = new ProbabilityMatrix(this.xSize, this.ySize, this.zSize);
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
		for(int x = m.getXMin(); x <= m.getXMax(); x++)
		{
			for(int y = m.getYMin(); y <= m.getYMax(); y++)
			{
				for(int z = m.getZMin(); z <= m.getZMax(); z++)
				{
					rMinToSP = Double.POSITIVE_INFINITY;
					for(int j = 0; j < numberOfArms; j++)
					{
						for(int i = 0; i < spiralPoints[j].size(); i++)
						{
							sP = ((List<int[]>) spiralPoints[j]).get(i);
							rToSP = MathUtil.distance(x, y, z, sP[0], sP[1], sP[2]);
							if(rToSP < rMinToSP)
							{
								rMinToSP = rToSP;
								iMinIndex = i;
								jMinIndex = j;
							}
						}
					}
					rMax = gradient + 0.1 - iMinIndex / (double) spiralPoints[jMinIndex].size() * gradient;
					alpha = Math.atan2(y, x);
					rDisc = Math.abs(Math.sqrt(Math.cos(alpha) * this.xSize / 2 * Math.cos(alpha) * this.xSize / 2 + Math.sin(alpha) * this.ySize / 2
							* Math.sin(alpha) * this.ySize / 2));
					deltaRing = rDisc / numberOfArms / numberOfTurns;
					if(rMinToSP < deltaRing * rMax)
						p = Functions.gauss(rMinToSP / deltaRing / rMax);
					else
						p = 0;
					m.setProbability(x, y, z, p);
				}
			}
		}

		m.norm(this.sectors);
		return m;
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix für den Galaxietyp Rx
	 * 
	 * @see EnumGalaxyType#Rx
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param numberOfRings - die Anzahl der Ringe
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	@SuppressWarnings("unchecked")
	protected ProbabilityMatrix generateTypeRx(double weight, double numberOfRings)
	{
		ProbabilityMatrix m = new ProbabilityMatrix(this.xSize, this.ySize, this.zSize);
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
					m.setProbability(x, y, z, p);
				}
			}
		}

		m.norm(this.sectors);
		return m;
	}

	/**
	 * Berechnet die Wahrscheinlichkeitsmatrix für den Galaxietyp Ax
	 * 
	 * @see EnumGalaxyType#Ax
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param radius - der Radius für den Bogen
	 * @param phiStart - der Startwinkel für den Bogen in Rad
	 * @param phiEnd - der Endwinkel für den Bogen in Rad
	 * @return die Wahrscheinlichkeitsmatrix
	 */
	protected ProbabilityMatrix generateTypeAx(double weight, double radius, double phiStart, double phiEnd)
	{
		ProbabilityMatrix m = new ProbabilityMatrix(this.xSize, this.ySize, this.zSize);
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
					m.setProbability(x, y, z, p);
				}
			}
		}

		m.norm(this.sectors);
		return m;
	}

	/**
	 * Fügt dieser Galaxie den Galaxietyp Ex hinzu (weight = 1, sizeLimitation =
	 * 1)
	 * 
	 * @see EnumGalaxyType#Ex
	 */
	public void addTypeEx()
	{
		this.addTypeEx(1.0, 1.0);
	}

	/**
	 * Fügt dieser Galaxie den Galaxietyp Ex hinzu (sizeLimitation = 1)
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
	 * Fügt dieser Galaxie den Galaxietyp Ex hinzu
	 * 
	 * @see EnumGalaxyType#Ex
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param sizeLimitation - eine Größenbeschränkung für den Galaxietyp
	 */
	public void addTypeEx(double weight, double sizeLimitation)
	{
		GalaxyGenerationThread tEx = new GalaxyGenerationThread(EnumGalaxyType.Ex, sizeLimitation);
		tEx.start(weight);
		this.threads.add(tEx);
	}

	/**
	 * Fügt dieser Galaxie den Galaxietyp S0 hinzu (weight = 1, sizeLimitation =
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
	 * Fügt dieser Galaxie den Galaxietyp S0 hinzu
	 * 
	 * @see EnumGalaxyType#S0
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param thickness - die Dicke der Linse
	 * @param sizeLimitation - eine Größenbeschränkung für den Galaxietyp
	 */
	public void addTypeS0(double weight, double thickness, double sizeLimitation)
	{
		addTypeEx(1.0 * weight, sizeLimitation);
		GalaxyGenerationThread tS0 = new GalaxyGenerationThread(EnumGalaxyType.S0, thickness, sizeLimitation);
		tS0.start(0.75 * weight);
		this.threads.add(tS0);
	}

	/**
	 * Fügt dieser Galaxie den Galaxietyp SB0 hinzu (weight = 1, sizeLimitation
	 * = 1)
	 * 
	 * @see EnumGalaxyType#SB0
	 * @param thickness - die Dicke der Linse
	 */
	public void addTypeSB0(double thickness)
	{
		this.addTypeSB0(1.0, thickness, 1.0);
	}

	/**
	 * Fügt dieser Galaxie den Galaxietyp SB0 hinzu (weight = 1, sizeLimitation
	 * = 1)
	 * 
	 * @see EnumGalaxyType#SB0
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param thickness - die Dicke der Linse
	 * @param sizeLimitation - eine Größenbeschränkung für den Galaxietyp
	 */
	public void addTypeSB0(double weight, double thickness, double sizeLimitation)
	{
		addTypeEx(1.0 * weight, sizeLimitation);
		GalaxyGenerationThread tSB0 = new GalaxyGenerationThread(EnumGalaxyType.SB0, thickness, sizeLimitation);
		tSB0.start(0.5 * weight);
		this.threads.add(tSB0);
	}

	/**
	 * Fügt dieser Galaxie den Galaxietyp Sx hinzu (weight = 1)
	 * 
	 * @see EnumGalaxyType#Sx
	 * @param numberOfArms - die Anzahl der Spiralarme
	 * @param numberOfTurns - die Anzahl der Umdrehungen für jeden Arm
	 * @param direction - die Drehrichtung
	 */
	public void addTypeSx(int numberOfArms, double numberOfTurns, double direction)
	{
		this.addTypeSx(1.0, numberOfArms, numberOfTurns, direction);
	}

	/**
	 * Fügt dieser Galaxie den Galaxietyp Sx hinzu
	 * 
	 * @see EnumGalaxyType#Sx
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param numberOfArms - die Anzahl der Spiralarme
	 * @param numberOfTurns - die Anzahl der Umdrehungen für jeden Arm
	 * @param direction - die Drehrichtung
	 */
	public void addTypeSx(double weight, int numberOfArms, double numberOfTurns, double direction)
	{
		addTypeS0(0.7 * weight, 0.2, 1.0);
		GalaxyGenerationThread tSx = new GalaxyGenerationThread(EnumGalaxyType.Sx, numberOfArms, numberOfTurns, direction);
		tSx.start(0.75 * weight);
		this.threads.add(tSx);
	}

	/**
	 * Fügt dieser Galaxie den Galaxietyp SBx hinzu (weight = 1)
	 * 
	 * @see EnumGalaxyType#SBx
	 * @param numberOfTurns - die Anzahl der Umdrehungen für jeden Arm
	 * @param direction - die Drehrichtung
	 */
	public void addTypeSBx(double numberOfTurns, double direction)
	{
		this.addTypeSBx(1.0, numberOfTurns, direction);
	}

	/**
	 * Fügt dieser Galaxie den Galaxietyp SBx hinzu
	 * 
	 * @see EnumGalaxyType#SBx
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param numberOfTurns - die Anzahl der Umdrehungen für jeden Arm
	 * @param direction - die Drehrichtung
	 */
	public void addTypeSBx(double weight, double numberOfTurns, double direction)
	{
		addTypeS0(0.5 * weight, 0.2, 1.0);
		addTypeSB0(0.3 * weight, 0.2, 0.6);
		GalaxyGenerationThread tSBx = new GalaxyGenerationThread(EnumGalaxyType.SBx, numberOfTurns, direction);
		tSBx.start(0.8 * weight);
		this.threads.add(tSBx);
	}

	/**
	 * Fügt dieser Galaxie den Galaxietyp Rx hinzu (weight = 1)
	 * 
	 * @see EnumGalaxyType#Rx
	 * @param numberOfRings - die Anzahl der Ringe
	 */
	public void addTypeRx(int numberOfRings)
	{
		this.addTypeRx(1.0, numberOfRings);
	}

	/**
	 * Fügt dieser Galaxie den Galaxietyp Rx hinzu
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
	 * Fügt dieser Galaxie den Galaxietyp Ax hinzu (weight = 1)
	 * 
	 * @see EnumGalaxyType#Ax
	 * @param radius - der Radius für den Bogen
	 * @param phiStart - der Startwinkel für den Bogen in Rad
	 * @param phiEnd - der Endwinkel für den Bogen in Rad
	 */
	public void addTypeAx(double radius, double phiStart, double phiEnd)
	{
		this.addTypeAx(1.0, radius, phiStart, phiEnd);
	}

	/**
	 * Fügt dieser Galaxie den Galaxietyp Ax hinzu
	 * 
	 * @see EnumGalaxyType#Ax
	 * @param weight - die Gewichtung dieses Galaxietyps innerhalb dieser
	 *            Galaxie
	 * @param radius - der Radius für den Bogen
	 * @param phiStart - der Startwinkel für den Bogen in Rad
	 * @param phiEnd - der Endwinkel für den Bogen in Rad
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
	 * Interne Thread-Klasse für die Berechnung der Wahrscheinlichkeitsmatrix
	 * eines Galaxietyps. Auf diese Weise können mehrere Galaxietypen
	 * hinzugefügt werden ohne, dass nach jedem Schritt auf die Berechnung
	 * gewartet werden muss. Die Threads werden in einer Liste gespeichert,
	 * damit sie gejoint werden können, sobald ihr Ergebnis für weitere
	 * Berechnungen notwendig ist.
	 * 
	 * @author ultimate
	 */
	private class GalaxyGenerationThread extends Thread
	{
		/**
		 * Logger-Instanz
		 */
		protected transient final Logger logger = LoggerFactory.getLogger(getClass());

		/**
		 * Der zu generierende Galaxietype
		 */
		private EnumGalaxyType				type;
		/**
		 * Die berechnete Wahrscheinlichkeitsmatrix
		 */
		private ProbabilityMatrix			m;
		/**
		 * Die Gewichtung dieser Wahrscheinlichkeitsmatrix
		 */
		private double						weight;
		/**
		 * Die für die Berechnung notwendigen zusätzlichen Parameter
		 * Siehe generateType*(..) und addType*(..)
		 */
		private double[]					params;

		/**
		 * Erzeugt einen neuen Thread
		 * 
		 * @param type - Der zu generierende Galaxietype
		 * @param params - Die für die Berechnung notwendigen zusätzlichen
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
		 * Führt die Berechnung mit Hilfe von generateType*(..) durch.
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
				m = generateTypeSB0(this.weight, params[0], params[1]);
			if(this.type.equals(EnumGalaxyType.Sx))
				m = generateTypeSx(this.weight, params[0], params[1], params[2]);
			if(this.type.equals(EnumGalaxyType.SBx))
				m = generateTypeSBx(this.weight, params[0], params[1]);
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
		 * zurück
		 * 
		 * @return die berechnete Wahrscheinlichkeitsmatrix
		 */
		public ProbabilityMatrix getMatrix()
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
}
