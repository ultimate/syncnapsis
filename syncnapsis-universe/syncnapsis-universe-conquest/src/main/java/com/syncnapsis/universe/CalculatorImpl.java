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
package com.syncnapsis.universe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.Parameter;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.data.model.SolarSystemPopulation;
import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.utils.MathUtil;
import com.syncnapsis.utils.data.ExtendedRandom;

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
	protected transient final Logger	logger						= LoggerFactory.getLogger(getClass());

	protected TimeProvider				timeProvider;

	/**
	 * The number of digits to ceil the size to.
	 * 
	 * @see MathUtil#ceil2(int, int)
	 */
	private static final int			SIZE_DIGITS					= 2;

	/**
	 * The correction to apply to the travel time.<br>
	 * Since standard travel time is dependent of max population and build factor the correction is
	 * applied to shift the resulting standard travel time to the right scale (approx. 1/100 of the
	 * time needed for full growth).
	 */
	private static final int			CORRECTION_TRAVEL_TIME		= 100000;
	/**
	 * The correction to apply to the calculation of the attack strength.<br>
	 * Correction is required to achieve<br>
	 * <code>attack(maxPop/2) / build(maxPop/2) = fac_attack / fac_build</code>
	 */
	private static final int			CORRECTION_ATTACK_STRENGTH	= 2;

	/**
	 * The max population for a solar system.<br>
	 * Calculated and cached from max habitability, max size and population factor
	 */
	protected Long						maxPopulation;
	/**
	 * The exponent for base 10 for the max population (calculated and cached).
	 */
	protected Double					maxPopulationExponent;

	/**
	 * Standard Constructor
	 */
	public CalculatorImpl()
	{
		super();
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
//			int maxHab = UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX);
//			int maxSize = UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX);
//			int popFactor = UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR);
//
//			maxPopulation = (long) maxHab * maxSize * popFactor;
			// @formatter:on

			maxPopulation = UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABILITY_MAX.asLong()
					* UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX.asLong()
					* UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR.asLong();
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
		// UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR);
		// return (long) infrastructure.getHabitability() * infrastructure.getSize() * popFactor;
		return (long) infrastructure.getHabitability() * infrastructure.getSize()
				* UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR.asLong();
	}
	
	/**
	 * Calculates the habitability by the following rule:<br>
	 * <ul>
	 * <li>planets are equally distributed in a range from [0; size/1000]</li>
	 * <li>the heat reaching a planet decreases with increasing orbit radius</li>
	 * <li>heat is distributed throughout the system as followed<br>
	 * <code>
	 * heat(r) = heat_factor * star_heat / (r + 1)^2
	 * </code>
	 * </li>
	 * <li>only planets with a heat within a given range are habitable (e.g. [0.2;0.8])</li>
	 * </ul>
	 * 
	 * @see UniverseConquestConstants#PARAM_SOLARSYSTEM_HABITABLE_HEAT_MIN
	 * @see UniverseConquestConstants#PARAM_SOLARSYSTEM_HABITABLE_HEAT_MAX
	 */
	@Override
	public int calculateHabitability(int size, int heat)
	{
		double minHeat = UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABLE_HEAT_MIN.asDouble();
		double maxHeat = UniverseConquestConstants.PARAM_SOLARSYSTEM_HABITABLE_HEAT_MAX.asDouble();
		double heatFactor = UniverseConquestConstants.PARAM_SOLARSYSTEM_HEAT_FACTOR.asDouble();
		
		double sizeD = size/UniverseConquestConstants.PARAM_SOLARSYSTEM_SIZE_MAX.asDouble();
		double heatD = heat/UniverseConquestConstants.PARAM_SOLARSYSTEM_HEAT_MAX.asDouble();

		double minHeatRadius = Math.sqrt(heatFactor * heatD / minHeat) - 1;
		double maxHeatRadius = Math.sqrt(heatFactor * heatD / maxHeat) - 1;
		
		double habitableZoneStart = Math.max(maxHeatRadius, 0.0);
		double habitableZoneEnd = Math.min(minHeatRadius, sizeD);

		double habitableZone = habitableZoneEnd - habitableZoneStart;
		if(habitableZone < 0)
			habitableZone = 0;
		
		double habitabilityD = habitableZone / sizeD;
		return (int) (habitabilityD*1000);
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
		double travelMaxFactor = UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR.asDouble();
		double travelExodusFactor = UniverseConquestConstants.PARAM_TRAVEL_EXODUS_FACTOR.asDouble();

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
		double travelMaxFactor = UniverseConquestConstants.PARAM_TRAVEL_MAX_FACTOR.asDouble();

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
		long stdTravelTime = calculateStandardTravelTime(origin.getMatch(), travelSpeed);
		
		double dist = MathUtil.distance(origin.getSolarSystem().getCoords(), target.getSolarSystem().getCoords());
		int stdDist = getStandardTravelDistance(origin.getSolarSystem().getGalaxy());

		return (long) ((dist / stdDist) * stdTravelTime); 
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateStandardTravelTime(com.syncnapsis.data.model.Match, int)
	 */
	@Override
	public long calculateStandardTravelTime(Match match, int travelSpeed)
	{
		double minSpeed = UniverseConquestConstants.PARAM_TRAVEL_SPEED_MIN.asDouble();
		double maxSpeed = UniverseConquestConstants.PARAM_TRAVEL_SPEED_MAX.asDouble();
		double facBuild = UniverseConquestConstants.PARAM_FACTOR_BUILD.asDouble();
		double travelTimeFactor = UniverseConquestConstants.PARAM_TRAVEL_TIME_FACTOR.asDouble();

		if(travelSpeed < minSpeed || travelSpeed > maxSpeed)
			throw new IllegalArgumentException("travelSpeed out of bounds: [" + minSpeed + ", " + maxSpeed + "]");

		double speedFac = getSpeedFactor(match.getSpeed());
		
		double timeBase = getMaxPopulation() / facBuild;
		double travelTime = travelTimeFactor * timeBase / CORRECTION_TRAVEL_TIME;
		travelTime /= (travelSpeed / maxSpeed); // speed in percent
		travelTime /= speedFac; // match speed

		return (long) travelTime;		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateVictoryTimeout(com.syncnapsis.data.model.Match)
	 */
	@Override
	public long calculateVictoryTimeout(Match match)
	{
		if(match.getVictoryCondition().hasTimeout())
		{
			int maxTravelSpeed = UniverseConquestConstants.PARAM_TRAVEL_SPEED_MAX.asInt();
			double timeoutFactor = match.getVictoryCondition().getTimeoutFactor();
			
			long stdTravelTime = calculateStandardTravelTime(match, maxTravelSpeed);
			
			return (long) (stdTravelTime * timeoutFactor);
		}
		else
		{
			return 0;
		}
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
		if(maxPopulationExponent == null)
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
		double fac = UniverseConquestConstants.PARAM_FACTOR_ATTACK.asDouble();
		double popFactor = UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR.asDouble();
		// correction is required to achieve
		// attack(maxPop/2) / build(maxPop/2) = fac_attack / fac_build
		return speedFactor * fac * population / popFactor / CORRECTION_ATTACK_STRENGTH;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateBuildStrength(int, long, long)
	 */
	@Override
	public double calculateBuildStrength(double speedFactor, long population, long maxPopulation)
	{
		double fac = UniverseConquestConstants.PARAM_FACTOR_BUILD.asDouble();
		long maxMaxPopulation = getMaxPopulation();
		double popFactor = UniverseConquestConstants.PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR.asDouble();
		double strength;
		if(population < maxPopulation / 2)
			strength = (double) (maxMaxPopulation - population) * population;
		else
			strength = (double) (maxMaxPopulation - (maxPopulation - population)) * (maxPopulation - population);
		strength = speedFactor * fac * strength / maxMaxPopulation / popFactor;
		// TODO really?
		if(Math.abs(strength) < 1.0)
			strength = Math.signum(strength);
		return strength;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateInfrastructureBuildInfluence(long, long)
	 */
	@Override
	public double calculateInfrastructureBuildInfluence(long population, long infrastructure)
	{
		if(population < 0)
			logger.warn("population " + population + " < 0");
		if(infrastructure < 0)
			logger.warn("infrastructure " + infrastructure + " < 0");

		if(infrastructure == 0)
			return 0.5;
		else if(population == 0)
			return 1.5;
		else
			return (Math.log10((double) infrastructure / population) / getMaxPopulationExponent()) / 2 + 1;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.universe.Calculator#calculateDeltas(com.syncnapsis.data.model.
	 * SolarSystemInfrastructure, com.syncnapsis.utils.data.ExtendedRandom, java.util.Date)
	 */
	@Override
	public List<SolarSystemPopulation> calculateDeltas(SolarSystemInfrastructure infrastructure, ExtendedRandom random, Date time)
	{
		double speedFactor = getSpeedFactor(infrastructure.getMatch().getSpeed());
		long maxPopulation = getMaxPopulation(infrastructure);

		double prio_full = UniverseConquestConstants.PARAM_PRIORITY_FULL.asDouble();
		double prio_high = UniverseConquestConstants.PARAM_PRIORITY_HIGH.asDouble();
		double prio_medium = UniverseConquestConstants.PARAM_PRIORITY_MEDIUM.asDouble();
		double prio_low = UniverseConquestConstants.PARAM_PRIORITY_LOW.asDouble();
		double prio_none = UniverseConquestConstants.PARAM_PRIORITY_NONE.asDouble();
		double randomization_attack = UniverseConquestConstants.PARAM_FACTOR_ATTACK_RANDOMIZE.asDouble();
		double randomization_build = UniverseConquestConstants.PARAM_FACTOR_BUILD_RANDOMIZE.asDouble();
		long norm_tick = UniverseConquestConstants.PARAM_NORM_TICK_LENGTH.asLong();

		List<SolarSystemPopulation> presentPopulations = new ArrayList<SolarSystemPopulation>(infrastructure.getPopulations().size());
		// double[] deltaPop = new double[infrastructure.getPopulations().size()];
		// double deltaInf = 0;
		long totalPopulation = 0;
		long otherPopulation;
		double b, a, aPop;
		long tick, tick2;
		SolarSystemPopulation pop, pop2;

		// first loop
		// - determine total present population
		for(int i = 0; i < infrastructure.getPopulations().size(); i++)
		{
			pop = infrastructure.getPopulations().get(i);
			if(pop.getPopulation() > 0 && pop.isPresent(time))
			{
				totalPopulation += pop.getPopulation();
				presentPopulations.add(pop);
			}
		}
		// logger.debug("time: " + time.getTime());
		// logger.debug("speed: " + infrastructure.getMatch().getSpeed() + " (" + speedFactor +
		// "x)");
		// logger.debug("infrastructure: " + infrastructure.getInfrastructure());
		// logger.debug("total population: " + totalPopulation);
		// logger.debug("max population: " + maxPopulation);
		// logger.debug("home population: " + infrastructure.getHomePopulation().getId());
		
		// second loop
		// - determine build and attack strengths
		// - calculate anti attack (current pop is attacker; attack strength is splitted)
		for(int i = 0; i < presentPopulations.size(); i++)
		{
			pop = presentPopulations.get(i);
			tick = time.getTime() - pop.getLastUpdateDate().getTime();

			b = calculateBuildStrength(speedFactor, pop.getPopulation(), maxPopulation);
			a = calculateAttackStrength(speedFactor, pop.getPopulation());

//			logger.debug("cycle " + i + " population " + pop.getId() + " a = " + a + " b = " + b + " delta = " + pop.getDelta() + " deltaInf = "
//					+ infrastructure.getDelta());

			if(tick < norm_tick)
			{
				b *= tick / (double) norm_tick; // weight strength by tick-length
				a *= tick / (double) norm_tick; // weight strength by tick-length
			}
			else if(tick > norm_tick)
			{
				logger.warn("cycle " + i + " population " + pop.getId() + " has not been updated since " + tick);
			}
			else
			{
				// tick = norm_tick
			}

//			logger.debug("cycle " + i + " population " + pop.getId() + " a = " + a + " b = " + b + " delta = " + pop.getDelta() + " deltaInf = "
//					+ infrastructure.getDelta());

			if(random != null)
			{
				if(randomization_build > 0) // add random variation
					b *= random.nextGaussian(1 - randomization_build, 1 + randomization_build);
				if(randomization_attack > 0) // add random variation
					a *= random.nextGaussian(1 - randomization_attack, 1 + randomization_attack);
			}

//			logger.debug("cycle " + i + " population " + pop.getId() + " a = " + a + " b = " + b + " delta = " + pop.getDelta() + " deltaInf = "
//					+ infrastructure.getDelta());

			if(pop == infrastructure.getHomePopulation())
			{
//				logger.debug("cycle " + i + " population " + pop.getId() + " is home...");
				b *= calculateInfrastructureBuildInfluence(pop.getPopulation(), infrastructure.getInfrastructure());
//				logger.debug("cycle " + i + " population " + pop.getId() + " a = " + a + " b = " + b + " delta = " + pop.getDelta() + " deltaInf = "
//						+ infrastructure.getDelta());
				// split build strength by priority
				switch(pop.getBuildPriority())
				{
					case infrastructure:
						pop.setDelta(pop.getDelta() + b * prio_low);
						infrastructure.setDelta(infrastructure.getDelta() + b * prio_high);
						break;
					case population:
						pop.setDelta(pop.getDelta() + b * prio_high);
						infrastructure.setDelta(infrastructure.getDelta() + b * prio_low);
						break;
					case balanced:
					default:
						pop.setDelta(pop.getDelta() + b * prio_medium);
						infrastructure.setDelta(infrastructure.getDelta() + b * prio_medium);
						break;

				}
				// split attack strength by priority (pop only if home)
				aPop = a * prio_full;
				infrastructure.setDelta(infrastructure.getDelta() - a * prio_none);
			}
			else
			{
				// no build if not home
				pop.setDelta(pop.getDelta() + b * prio_none);
				infrastructure.setDelta(infrastructure.getDelta() + b * prio_none);

				// split attack strength by priority
				switch(pop.getAttackPriority())
				{
					case infrastructure:
						aPop = a * prio_low;
						infrastructure.setDelta(infrastructure.getDelta() - a * prio_high);
						break;
					case population:
						aPop = a * prio_high;
						infrastructure.setDelta(infrastructure.getDelta() - a * prio_low);
						break;
					case balanced:
					default:
						aPop = a * prio_medium;
						infrastructure.setDelta(infrastructure.getDelta() - a * prio_medium);
						break;
				}
			}
//			logger.debug("cycle " + i + " population " + pop.getId() + " a = " + a + " b = " + b + " delta = " + pop.getDelta() + " deltaInf = "
//					+ infrastructure.getDelta());
			// distribute attack strength on other populations
			// (weighted by their amount of population)
			otherPopulation = totalPopulation - pop.getPopulation();
			if(otherPopulation > 0)
			{
				double delta2;
				for(int j = 0; j < presentPopulations.size(); j++)
				{
					pop2 = presentPopulations.get(j);

					if(j != i)
					{
						tick2 = time.getTime() - pop2.getLastUpdateDate().getTime();
						tick2 = Math.min(tick, tick2);
						delta2 = ((double) pop2.getPopulation() / otherPopulation) * aPop;
						if(tick2 < norm_tick)
						{
							delta2 *= tick2 / (double) norm_tick; // weight attack by tick length
						}
						else if(tick2 > norm_tick)
						{
							logger.warn("cycle " + i + " population " + pop.getId() + " has not been updated since " + tick2);
						}
						else
						{
							// tick2 = norm_tick
						}
						pop2.setDelta(pop2.getDelta() - delta2);
					}

//					logger.debug("cycle " + i + " population " + infrastructure.getPopulations().get(j).getId() + ": delta = " + pop2.getDelta());
				}
			}
			else
			{
				// no attack to handle - pop all alone
//				logger.debug("cycle " + i + " population " + pop.getId() + " all alone: delta = " + pop.getDelta());
			}
		}
		// third loop
		// - apply delta
		long delta;
		for(int i = 0; i < presentPopulations.size(); i++)
		{
			pop = presentPopulations.get(i);
			// round and trim to current population if necessary
			delta = Math.round(pop.getDelta());
			if(pop.getPopulation() + delta < 0)
				delta = -pop.getPopulation();
//			logger.debug("population " + pop.getId() + " value = " + pop.getPopulation() + " delta = " + delta + " (" + pop.getDelta() + ")");
			pop.setPopulation(pop.getPopulation() + delta);
			pop.setModified(true);
			pop.setDelta(0.0);
		}

		// infrastructure update
		delta = Math.round(infrastructure.getDelta());
		if(infrastructure.getInfrastructure() + infrastructure.getDelta() < 0)
			infrastructure.setInfrastructure(0);
		else if(infrastructure.getInfrastructure() + infrastructure.getDelta() > maxPopulation)
			infrastructure.setInfrastructure(maxPopulation);
		else
			infrastructure.setInfrastructure(infrastructure.getInfrastructure() + delta);
		infrastructure.setModified(true);
		infrastructure.setDelta(0.0);

		// return modified list
		return infrastructure.getPopulations();
	}
}