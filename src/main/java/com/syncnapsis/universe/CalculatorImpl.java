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
package com.syncnapsis.universe;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.Parameter;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.data.service.ParameterManager;
import com.syncnapsis.utils.MathUtil;

/**
 * Universe conquest {@link Calculator} implementation
 * 
 * @author ultimate
 */
public class CalculatorImpl implements Calculator
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger		= LoggerFactory.getLogger(getClass());

	/**
	 * The number of digits to ceil the size to.
	 * 
	 * @see MathUtil#ceil2(int, int)
	 */
	private static final int			SIZE_DIGITS	= 2;

	protected Long						maxPopulation;
	protected Double					maxPopulationExponent;

	/**
	 * The ParameterManager
	 */
	protected ParameterManager			parameterManager;

	private int							HABITABILITY_MAX;
	private int							SIZE_MAX;
	private int							MAX_POPULATION_FACTOR;

	/**
	 * Standard Constructor
	 * 
	 * @param parameterManager - the ParameterManager
	 */
	public CalculatorImpl(ParameterManager parameterManager)
	{
		super();
		this.parameterManager = parameterManager;

		initConstants();
	}

	public void initConstants()
	{
		HABITABILITY_MAX = parameterManager.getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX);
		SIZE_MAX = parameterManager.getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX);
		MAX_POPULATION_FACTOR = parameterManager.getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR);

		double travelMaxFactor = parameterManager.getDouble(UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR);
		double travelExodusFactor = parameterManager.getDouble(UniverseConquestConstants.PARAM_TRAVEL_EXODUS_FACTOR);

	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateSize(java.util.List)
	 */
	@Override
	public Vector.Integer calculateSize(List<Vector.Integer> coords)
	{
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int minZ = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		int maxZ = Integer.MIN_VALUE;

		for(Vector.Integer c : coords)
		{
			if(c.getX() < minX)
				minX = c.getX();
			if(c.getY() < minY)
				minY = c.getY();
			if(c.getZ() < minZ)
				minZ = c.getZ();
			if(c.getX() > maxX)
				maxX = c.getX();
			if(c.getY() > maxY)
				maxY = c.getY();
			if(c.getZ() > maxZ)
				maxZ = c.getZ();
		}

		int sizeX = MathUtil.ceil2(maxX - minX + 1, SIZE_DIGITS);
		int sizeY = MathUtil.ceil2(maxY - minY + 1, SIZE_DIGITS);
		int sizeZ = MathUtil.ceil2(maxZ - minZ + 1, SIZE_DIGITS);

		return new Vector.Integer(sizeX, sizeY, sizeZ);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateMaxGap(java.util.List)
	 */
	@Override
	public int calculateMaxGap(List<Vector.Integer> coords)
	{
		long maxGapSquare = 0;
		long minGapSquare;
		long gapSquare;
		long sqX, sqY, sqZ, dX, dY, dZ;
		for(Vector.Integer c1 : coords)
		{
			minGapSquare = Long.MAX_VALUE;
			for(Vector.Integer c2 : coords)
			{
				if(c2 == c1)
					continue;
				dX = c1.getX() - c2.getX();
				sqX = dX * dX;
				if(sqX > minGapSquare)
					continue;
				dY = c1.getY() - c2.getY();
				sqY = dY * dY;
				if(sqY > minGapSquare)
					continue;
				dZ = c1.getZ() - c2.getZ();
				sqZ = dZ * dZ;
				if(sqZ > minGapSquare)
					continue;

				// calculate the square only to avoid sqrt
				gapSquare = sqX + sqY + sqZ;

				if(gapSquare < minGapSquare)
					minGapSquare = gapSquare;
			}
			if(minGapSquare > maxGapSquare)
				maxGapSquare = minGapSquare;
		}
		return (int) Math.ceil(Math.sqrt(maxGapSquare));
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateMinGap(java.util.List)
	 */
	@Override
	public int calculateMinGap(List<Vector.Integer> coords)
	{
		long minGapSquare = Long.MAX_VALUE;
		long gapSquare;
		long sqX, sqY, sqZ, dX, dY, dZ;
		Vector.Integer[] coordsA = coords.toArray(new Vector.Integer[coords.size()]);
		for(int i1 = 0; i1 < coordsA.length; i1++)
		{
			for(int i2 = i1 + 1; i2 < coordsA.length; i2++)
			{

				dX = coordsA[i1].getX() - coordsA[i2].getX();
				sqX = dX * dX;
				if(sqX > minGapSquare)
					continue;
				dY = coordsA[i1].getY() - coordsA[i2].getY();
				sqY = dY * dY;
				if(sqY > minGapSquare)
					continue;
				dZ = coordsA[i1].getZ() - coordsA[i2].getZ();
				sqZ = dZ * dZ;
				if(sqZ > minGapSquare)
					continue;

				// calculate the square only to avoid sqrt
				gapSquare = sqX + sqY + sqZ;

				if(gapSquare < minGapSquare)
					minGapSquare = gapSquare;
			}
		}
		return (int) Math.ceil(Math.sqrt(minGapSquare));
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateAvgGap(java.util.List)
	 */
	@Override
	public int calculateAvgGap(List<Vector.Integer> coords)
	{
		double avgGap = 0;
		long minGapSquare;
		long gapSquare;
		long sqX, sqY, sqZ, dX, dY, dZ;
		for(Vector.Integer c1 : coords)
		{
			minGapSquare = Long.MAX_VALUE;
			for(Vector.Integer c2 : coords)
			{
				if(c2 == c1)
					continue;
				dX = c1.getX() - c2.getX();
				sqX = dX * dX;
				if(sqX > minGapSquare)
					continue;
				dY = c1.getY() - c2.getY();
				sqY = dY * dY;
				if(sqY > minGapSquare)
					continue;
				dZ = c1.getZ() - c2.getZ();
				sqZ = dZ * dZ;
				if(sqZ > minGapSquare)
					continue;

				// calculate the square only to avoid sqrt
				gapSquare = sqX + sqY + sqZ;

				if(gapSquare < minGapSquare)
					minGapSquare = gapSquare;
			}
			avgGap += Math.sqrt(minGapSquare);
		}
		return (int) Math.round(avgGap / coords.size());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.universe.Calculator#getStandardTravelDistance(com.syncnapsis.data.model.Galaxy
	 * )
	 */
	@Override
	public int getStandardTravelDistance(Galaxy galaxy)
	{
		return galaxy.getMaxGap();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#getMaxPopulation()
	 */
	@Override
	public long getMaxPopulation()
	{
		if(maxPopulation == null)
		{
			// @formatter:off
//			int maxHab = parameterManager.getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX);
//			int maxSize = parameterManager.getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX);
//			int popFactor = parameterManager.getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR);
//
//			maxPopulation = (long) maxHab * maxSize * popFactor;
			// @formatter:on

			maxPopulation = (long) HABITABILITY_MAX * SIZE_MAX * MAX_POPULATION_FACTOR;
		}
		return maxPopulation;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#getMaxPopulation(com.syncnapsis.data.model.
	 * SolarSystemInfrastructure)
	 */
	@Override
	public long getMaxPopulation(SolarSystemInfrastructure infrastructure)
	{
		// int popFactor =
		// parameterManager.getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR);
		// return (long) infrastructure.getHabitability() * infrastructure.getSize() * popFactor;
		return (long) infrastructure.getHabitability() * infrastructure.getSize() * MAX_POPULATION_FACTOR;
	}

	/**
	 * Calculates the maximum travel distance for a moved population based on the formula<br>
	 * <code>dist = ( log10(inf/movedPop) / popExp + 1 ) * travelFac / 2</code><br>
	 * where travelFac is defined as a {@link Parameter} and popExp is evaluated from
	 * max-habitability, max-size and popuation-factor of a solar system.
	 * 
	 * @see CalculatorImpl#getMaxPopulationExponent()
	 */
	@Override
	public double calculateMaxTravelDistance(SolarSystemPopulation origin, long movedPopulation, boolean exodus)
	{
		double travelMaxFactor = parameterManager.getDouble(UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR);
		double travelExodusFactor = parameterManager.getDouble(UniverseConquestConstants.PARAM_TRAVEL_EXODUS_FACTOR);

		double infrastructure = origin.getInfrastructure().getInfrastructure();
		if(infrastructure == 0)
			infrastructure = 1;

		double dist = Math.log10(infrastructure / movedPopulation);

		if(exodus)
			dist = (dist / getMaxPopulationExponent() + 1) * travelExodusFactor / 2;
		else
			dist = (dist / getMaxPopulationExponent() + 1) * travelMaxFactor / 2;

		return dist * getStandardTravelDistance(origin.getInfrastructure().getSolarSystem().getGalaxy());
	}

	/**
	 * Calculates the maximum movable population for the given distance based on the formula<br>
	 * <code>inf / 10^(((dist * 2 / travelFac) - 1) * popExp)</code> where travelFac is defined as a
	 * {@link Parameter} and popExp is evaluated from
	 * max-habitability, max-size and popuation-factor of a solar system.
	 * 
	 * @see CalculatorImpl#getMaxPopulationExponent()
	 */
	@Override
	public long calculateMaxMovablePopulation(SolarSystemPopulation origin, double travelDistance)
	{
		double travelMaxFactor = parameterManager.getDouble(UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR);

		double pop = (travelDistance / getStandardTravelDistance(origin.getInfrastructure().getSolarSystem().getGalaxy())) * 2 / travelMaxFactor - 1;
		pop = origin.getInfrastructure().getInfrastructure() / Math.pow(10, pop * getMaxPopulationExponent());

		if(pop > origin.getPopulation())
			pop = origin.getPopulation();

		return (long) Math.floor(pop);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateTravelTime(com.syncnapsis.data.model.
	 * SolarSystemInfrastructure, com.syncnapsis.data.model.SolarSystemInfrastructure, int)
	 */
	@Override
	public long calculateTravelTime(SolarSystemInfrastructure origin, SolarSystemInfrastructure target, int travelSpeed)
	{
		int minSpeed = parameterManager.getInteger(UniverseConquestConstants.PARAM_TRAVEL_SPEED_MIN);
		int maxSpeed = parameterManager.getInteger(UniverseConquestConstants.PARAM_TRAVEL_SPEED_MAX);
		if(travelSpeed < minSpeed || travelSpeed > maxSpeed)
			throw new IllegalArgumentException("travelSpeed out of bounds: [" + minSpeed + ", " + maxSpeed + "]");

		int matchSpeed = origin.getMatch().getSpeed();

		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Get the maximum population exponent (base 10) needed for travel distance calculations in the
	 * form<br>
	 * <code>log10(maxHab * maxSize * popFactor)</code><br>
	 * where all three constants are defined as a {@link Parameter}
	 * 
	 * @return the exponent
	 */
	protected double getMaxPopulationExponent()
	{
		if(maxPopulationExponent == null) // TODO
		{
			maxPopulationExponent = Math.log10(getMaxPopulation());
		}
		return maxPopulationExponent;
	}

	/**
	 * Get the calculation factor for the given speed
	 * 
	 * @param speed - the match's speed
	 * @return the speed factor
	 */
	public double getSpeedFactor(int speed)
	{
		return Math.pow(10, speed);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateAttackStrength(int, long)
	 */
	@Override
	public double calculateAttackStrength(double speedFactor, long population)
	{
		double fac = parameterManager.getDouble(UniverseConquestConstants.PARAM_FACTOR_ATTACK);
		return speedFactor * fac * population;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateBuildStrength(int, long, long)
	 */
	@Override
	public double calculateBuildStrength(double speedFactor, long population, long maxPopulation)
	{
		double fac = parameterManager.getDouble(UniverseConquestConstants.PARAM_FACTOR_BUILD);
		long maxMaxPopulation = getMaxPopulation();
		int popFactor = parameterManager.getInteger(UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR);
		double strength;
		// logger.debug("pop = " + population + " maxPop = " + maxPopulation + " | < half ? " +
		// (population < maxPopulation / 2));
		if(population < maxPopulation / 2)
			strength = (double) (maxMaxPopulation - population) * population;
		else
			strength = (double) (maxMaxPopulation - (maxPopulation - population)) * (maxPopulation - population);
		// logger.debug("strength = " + strength);
		return speedFactor * fac * strength / maxMaxPopulation / popFactor;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateInfrastructureBuildInfluence(long, long)
	 */
	@Override
	public double calculateInfrastructureBuildInfluence(long population, long infrastructure)
	{
		if(infrastructure == 0)
			return 0.5;
		else if(population == 0)
			return 1.5;
		else
			return (Math.log10((double) infrastructure / population) / getMaxPopulationExponent()) / 2 + 1;
	}
}
