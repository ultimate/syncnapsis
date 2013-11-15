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
package com.syncnapsis.constants;

import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.utils.constants.Constant;
import com.syncnapsis.utils.constants.StringConstant;

/**
 * Class defining constants within universe-conquest
 * 
 * @author ultimate
 */
public class UniverseConquestConstants
{
	/**
	 * The name for the parameter containing the default percentage for rivals in vendetta mode
	 */
	public static final Constant<String>	PARAM_MATCH_VENDETTA_PARAM_DEFAULT		= new StringConstant("match.vendetta.param.default");
	/**
	 * The minimum value for the speed of a match
	 */
	public static final Constant<String>	PARAM_MATCH_SPEED_MIN					= new StringConstant("match.speed.min");
	/**
	 * The maximum value for the speed of a match
	 */
	public static final Constant<String>	PARAM_MATCH_SPEED_MAX					= new StringConstant("match.speed.max");

	/**
	 * The maximum value for the habitability of a SolarSystem
	 * 
	 * @see SolarSystemInfrastructure#getHabitability()
	 */
	public static final Constant<String>	PARAM_SOLARSYSTEM_HABITABILITY_MAX		= new StringConstant("solarsystem.habitability.max");
	/**
	 * The maximum value for the size of a SolarSystem
	 * 
	 * @see SolarSystemInfrastructure#getSize()
	 */
	public static final Constant<String>	PARAM_SOLARSYSTEM_SIZE_MAX				= new StringConstant("solarsystem.size.max");
	/**
	 * The factor for calculating the maximum population of a SolarSystem from the habitability and
	 * the size in the way <code>habitability*size*factor</code>
	 */
	public static final Constant<String>	PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR	= new StringConstant("solarsystem.population.factor");

	/**
	 * The factor for calculating the maximum travel distance from the standard travel distance.
	 */
	public static final Constant<String>	PARAM_TRAVEL_MAX_FACTOR					= new StringConstant("travel.max.factor");
	/**
	 * The factor for calculating the maximum <b>exodus</b> travel distance from the standard travel
	 * distance.
	 */
	public static final Constant<String>	PARAM_TRAVEL_EXODUS_FACTOR				= new StringConstant("travel.exodus.factor");
	/**
	 * The maximum speed for populations traveling.<br>
	 * For calculations the ratio between min and max is most relevant.<br>
	 * The exact values may be used to control the selectable step-size.
	 */
	public static final Constant<String>	PARAM_TRAVEL_SPEED_MAX					= new StringConstant("travel.speed.max");
	/**
	 * The minimum speed for populations traveling.<br>
	 * For calculations the ratio between min and max is most relevant.<br>
	 * The exact values may be used to control the selectable step-size.
	 */
	public static final Constant<String>	PARAM_TRAVEL_SPEED_MIN					= new StringConstant("travel.speed.min");
	/**
	 * The factor for fine-tuning the time need to travel the standard distance
	 */
	public static final Constant<String>	PARAM_TRAVEL_TIME_FACTOR				= new StringConstant("travel.time.factor");
	/**
	 * The factor for calculating population build
	 */
	public static final Constant<String>	PARAM_FACTOR_BUILD						= new StringConstant("factor.build");
	/**
	 * The factor for calculating population attack
	 */
	public static final Constant<String>	PARAM_FACTOR_ATTACK						= new StringConstant("factor.attack");
	/**
	 * The range for randomizing build strength calculation<br>
	 * <ul>
	 * <li>0.0 = no randomizatation</li>
	 * <li>x = randomization by "+/- x" (relative) resulting in the range [(1-x)*value;(1+x)*value]</li>
	 * <li>e.g. 0.1 = randomization from [0.9*value;1.1*value]</li>
	 * <li>e.g. 0.5 = randomization from [0.5*value;1.5*value]</li>
	 * <li>e.g. 1.5 = randomization from [-0.5*value;2.5*value]</li>
	 * </ul>
	 */
	public static final Constant<String>	PARAM_FACTOR_BUILD_RANDOMIZE			= new StringConstant("factor.build.randomize");
	/**
	 * The range for randomizing attack strength calculation<br>
	 * <ul>
	 * <li>0.0 = no randomizatation</li>
	 * <li>x = randomization by "+/- x" (relative) resulting in the range [(1-x)*value;(1+x)*value]</li>
	 * <li>e.g. 0.1 = randomization from [0.9*value;1.1*value]</li>
	 * <li>e.g. 0.5 = randomization from [0.5*value;1.5*value]</li>
	 * <li>e.g. 1.5 = randomization from [-0.5*value;2.5*value]</li>
	 * </ul>
	 */
	public static final Constant<String>	PARAM_FACTOR_ATTACK_RANDOMIZE			= new StringConstant("factor.attack.randomize");
	/**
	 * The length of a norm tick
	 */
	public static final Constant<String>	PARAM_NORM_TICK_LENGTH					= new StringConstant("norm.tick.length");
	/**
	 * The factor for full priority
	 */
	public static final Constant<String>	PARAM_PRIORITY_FULL						= new StringConstant("priority.full");
	/**
	 * The factor for high priority
	 */
	public static final Constant<String>	PARAM_PRIORITY_HIGH						= new StringConstant("priority.high");
	/**
	 * The factor for medium priority
	 */
	public static final Constant<String>	PARAM_PRIORITY_MEDIUM					= new StringConstant("priority.medium");
	/**
	 * The factor for low priority
	 */
	public static final Constant<String>	PARAM_PRIORITY_LOW						= new StringConstant("priority.low");
	/**
	 * The factor for no priority
	 */
	public static final Constant<String>	PARAM_PRIORITY_NONE						= new StringConstant("priority.none");
}
