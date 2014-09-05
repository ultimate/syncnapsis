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

import java.util.ArrayList;
import java.util.List;

import com.syncnapsis.utils.data.ExtendedRandom;
import com.syncnapsis.utils.math.Array3D;
import com.syncnapsis.utils.math.Functions;

public class GalaxySpecification2 extends GalaxySpecification
{
	private static final long	serialVersionUID	= 1L;

	final int					Ex					= 0;
	final int					S0					= 1;
	final int					SB0					= 2;
	final int					Sx					= 3;
	final int					SBx					= 4;
	final int					Rx					= 5;
	final int					Ax					= 6;

	final int					weight				= 0;
	final int					sizeLimitation		= 1;
	final int					thickness			= 2;
	final int					rotationOffset		= 3;
	final int					direction			= 4;
	final int					number				= 5;

	double[][]					params				= new double[7][6];

	ExtendedRandom				random				= new ExtendedRandom();

	public GalaxySpecification2(int xSize, int ySize, int zSize, int sectors, int gridSize)
	{
		super(xSize, ySize, zSize, sectors, gridSize);
	}

	public List<int[]> generateCoordinates()
	{
		int type;
		double weightSum = 0;
		for(type = 0; type < params.length; type++)
		{
			weightSum += params[type][weight];
//			logger.debug("type=" + type + " weightSum=" + weightSum);
		}

		Array3D m = new Array3D(this.xSize, this.ySize, this.zSize);
		List<int[]> sectors = new ArrayList<int[]>();

		double r, r2, phi, theta;
		double g1, g2, g3;
		double sum;
		double scale;
		int x, y, z;
		for(int i = 0; i < this.sectors; i++)
		{
			r = random.nextDouble(0, weightSum);
			sum = 0;

			for(type = 0; type < params.length; type++)
			{
				sum += params[type][weight];
				if(r < sum)
					break;
			}

			// x = random.nextInt(m.getXMin(), m.getXMax());
			// y = random.nextInt(m.getYMin(), m.getYMax());
			// z = random.nextInt(m.getZMin(), m.getZMax());

			// logger.debug("i    = " + i);
			// logger.debug("rand = " + r);
			// logger.debug("type = " + type);

			switch(type)
			{
				case Ex:
					// r = random.nextDouble();
					// if(r < 0.25)
					// {
					// r = random.nextDouble() * 2 - 1;
					// x = (int) Math.round(r* xSize * gridSize / 4.0);
					// y = (int) Math.round((1 - Functions.gauss(r)) * ySize * gridSize / 4.0) -
					// ySize * gridSize * 2 / 4;
					// }
					// else if(r < 0.5)
					// {
					// r = random.nextDouble() * 2 - 1;
					// x = (int) Math.round(r* xSize * gridSize / 4.0);
					// y = (int) Math.round((1 - Functions.gaussModified(r)) * ySize * gridSize /
					// 4.0 - ySize * gridSize * 1 / 4);
					// }
					// else if(r < 0.75)
					// {
					// r = random.nextDouble() * 2 - 1;
					// x = (int) Math.round(r* xSize * gridSize / 4.0);
					// y = (int) Math.round((1 - Math.sqrt(r)) * ySize * gridSize / 4.0 + ySize *
					// gridSize * 0 / 4);
					// }
					// else
					// {
					// r = random.nextDouble() * 2 - 1;
					// x = (int) Math.round(r* xSize * gridSize / 4.0);
					// y = (int) Math.round((1 - Math.pow(r, 2)) * ySize * gridSize / 4.0 + ySize *
					// gridSize * 1 / 4);
					// }
					// z = 0;
					//
					// break;
					r2 = random.nextDouble();
					double bound1 = 0.5;
					double bound2 = 0; // relative
					if(r2 > bound1)
					{
						if(r2 > (1-bound1)*bound2+bound1)
							r2 = 1;
						else
							r2 = 0.4;
						r = Math.abs(random.nextGaussian(-1.0, 1.0));
					}
					else
					{
						r2 = 1;
						r = Math.pow(random.nextDouble(), 2);
					}

					g1 = random.nextGaussian();
					g2 = random.nextGaussian();
					g3 = random.nextGaussian();
					r = Math.pow(r, 1 / 3.0);
					scale = Math.sqrt(g1 * g1 + g2 * g2 + g3 * g3);
					x = (int) Math.round(r2 * r / scale * g1 * xSize * gridSize / 2);
					y = (int) Math.round(r2 * r / scale * g2 * ySize * gridSize / 2);
					z = (int) Math.round(r2 * r / scale * g3 * zSize * gridSize / 2);
					break;

				case S0:
				case SB0:
				case Sx:
				case Rx:
				case Ax:
				default:
					continue;
			}
			sectors.add(new int[] { x, y, z });
		}

		return sectors;
	}

	public void addTypeEx(double weight, double sizeLimitation)
	{
		params[Ex][this.weight] = weight;
		params[Ex][this.sizeLimitation] = sizeLimitation;
		// params[Ex][this.thickness] = thickness;
		// params[Ex][this.rotationOffset] = rotationOffset;
		// params[Ex][this.direction] = direction;
	}

	public void addTypeS0(double weight, double thickness, double sizeLimitation)
	{
		addTypeEx(1.0 * weight, sizeLimitation);

		params[S0][this.weight] = 0.75 * weight;
		params[S0][this.sizeLimitation] = sizeLimitation;
		params[S0][this.thickness] = thickness;
		// params[S0][this.rotationOffset] = rotationOffset;
		// params[S0][this.direction] = direction;
	}

	public void addTypeSB0(double weight, double thickness, double sizeLimitation, double rotationOffset)
	{
		addTypeEx(1.0 * weight, sizeLimitation);

		params[SB0][this.weight] = 0.5 * weight;
		params[SB0][this.sizeLimitation] = sizeLimitation;
		params[SB0][this.thickness] = thickness;
		params[SB0][this.rotationOffset] = rotationOffset;
		// params[SB0][this.direction] = direction;
	}

	public void addTypeSx(double weight, int numberOfArms, double numberOfTurns, double direction, double rotationOffset)
	{
		addTypeS0(0.7 * weight, 0.2, 1.0);

		params[Sx][this.weight] = 0.75 * weight;
		// params[Sx][this.sizeLimitation] = sizeLimitation;
		// params[Sx][this.thickness] = thickness;
		params[Sx][this.rotationOffset] = rotationOffset;
		params[Sx][this.direction] = direction;
	}

	public void addTypeSBx(double weight, double numberOfTurns, double direction, double rotationOffset)
	{
		addTypeS0(0.5 * weight, 0.2, 1.0);
		addTypeSB0(0.3 * weight, 0.2, 0.6, rotationOffset);

		params[SBx][this.weight] = 0.75 * weight;
		// params[SBx][this.sizeLimitation] = sizeLimitation;
		// params[SBx][this.thickness] = thickness;
		params[SBx][this.rotationOffset] = rotationOffset;
		params[SBx][this.direction] = direction;
		params[SBx][this.number] = numberOfTurns;
	}

	public void addTypeRx(double weight, int numberOfRings)
	{
		addTypeS0(0.7 * weight, 0.2, 1.0);

		params[Rx][this.weight] = 0.75 * weight;
		// params[SBx][this.sizeLimitation] = sizeLimitation;
		// params[SBx][this.thickness] = thickness;
		// params[SBx][this.rotationOffset] = rotationOffset;
		// params[SBx][this.direction] = direction;
		params[Rx][this.number] = numberOfRings;
	}

	public void addTypeAx(double weight, double radius, double phiStart, double phiEnd)
	{
		params[Rx][this.weight] = weight;
		// params[SBx][this.sizeLimitation] = sizeLimitation;
		// params[SBx][this.thickness] = thickness;
		params[SBx][this.rotationOffset] = phiStart;
		// params[SBx][this.direction] = direction;
		params[Rx][this.number] = phiEnd;
	}
}
