package com.syncnapsis.utils.serialization;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.constants.UniverseConquestConstants;
import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;
import com.syncnapsis.utils.constants.Constant;

/**
 * Class for generating the JSON representation for different kind of data (e.g. the
 * {@link SolarSystem}s in a {@link Galaxy}).<br>
 * Since this class provides specialized functionality it not inherited from {@link Serializer}.<br>
 * 
 * @author ultimate
 */
public abstract class JSONGenerator
{
	/**
	 * Logger-Instance
	 */
	protected transient final static Logger	logger			= LoggerFactory.getLogger(JSONGenerator.class);
	/**
	 * The line separator to use
	 */
	public static String					LINE_SEPARATOR	= "\r\n";

	/**
	 * Prevent instantiation
	 */
	private JSONGenerator()
	{
	}

	/**
	 * Generate the JSON representation for the given {@link Galaxy}'s {@link List} of
	 * {@link SolarSystem}s in the
	 * form:<br>
	 * <code><pre> [
	 *   [id, x, y, z], // for each solar system
	 *   ...
	 * ]
	 * </pre></code> (whitespaces included here for better readability only)
	 * 
	 * @param solarSystems - the {@link List} of {@link SolarSystem}s
	 * @return the JSON string
	 */
	public static String toJSON(Galaxy galaxy, boolean format)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		if(galaxy.getSolarSystems() != null && galaxy.getSolarSystems().size() > 0)
		{
			Collections.sort(galaxy.getSolarSystems(), SolarSystem.BY_ID);
			for(SolarSystem sys : galaxy.getSolarSystems())
			{
				if(format)
				{
					sb.append(LINE_SEPARATOR);
					sb.append('\t');
				}
				sb.append('[');
				sb.append(sys.getId());
				sb.append(',');
				sb.append(sys.getCoords().getX());
				sb.append(',');
				sb.append(sys.getCoords().getY());
				sb.append(',');
				sb.append(sys.getCoords().getZ());
				sb.append(']');
				sb.append(',');
			}
			// replace last ","
			if(format)
				sb.replace(sb.length() - 1, sb.length(), LINE_SEPARATOR + ']');
			else
				sb.setCharAt(sb.length() - 1, ']');
		}
		else
		{
			sb.append(']');
		}

		return sb.toString();
	}

	/**
	 * Generate the JSON representation for the given {@link Match}'s {@link List} of
	 * {@link SolarSystemInfrastructure}s in the
	 * form:<br>
	 * <code><pre> [
	 *   [id, sys.x, sys.y, sys.z, size, hab], // for each solar system infrastructure
	 *   ...
	 * ]
	 * </pre></code> (whitespaces included here for better readability only)
	 * 
	 * @param solarSystemInfrastructures - the {@link List} of {@link SolarSystemInfrastructure}s
	 * @return the JSON string
	 */
	public static String toJSON(Match match, boolean format)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		if(match.getInfrastructures() != null && match.getInfrastructures().size() > 0)
		{
			Collections.sort(match.getInfrastructures(), SolarSystemInfrastructure.BY_ID);
			for(SolarSystemInfrastructure inf : match.getInfrastructures())
			{
				if(format)
				{
					sb.append(LINE_SEPARATOR);
					sb.append('\t');
				}
				sb.append('[');
				sb.append(inf.getId());
				// sb.append(',');
				// sb.append(inf.getSolarSystem().getId());
				sb.append(',');
				sb.append(inf.getSolarSystem().getCoords().getX());
				sb.append(',');
				sb.append(inf.getSolarSystem().getCoords().getY());
				sb.append(',');
				sb.append(inf.getSolarSystem().getCoords().getZ());
				sb.append(',');
				sb.append(inf.getSize());
				sb.append(',');
				sb.append(inf.getHeat());
				// sb.append(',');
				// sb.append(inf.getHabitability());
				sb.append(']');
				sb.append(',');
			}
			// replace last ","
			if(format)
				sb.replace(sb.length() - 1, sb.length(), LINE_SEPARATOR + ']');
			else
				sb.setCharAt(sb.length() - 1, ']');
		}
		else
		{
			sb.append(']');
		}

		return sb.toString();
	}

	/**
	 * Format the client relevant constants JS-style.
	 * 
	 * @return the constants.js content
	 */
	public static String createConstantJS()
	{
		StringBuilder constants = new StringBuilder();
		constants.append("var UniverseConquestConstants = {};\n");

		writeConstant(constants, UniverseConquestConstants.class, "PARAM_TRAVEL_MAX_FACTOR", "int");
		writeConstant(constants, UniverseConquestConstants.class, "PARAM_TRAVEL_EXODUS_FACTOR", "int");
		writeConstant(constants, UniverseConquestConstants.class, "PARAM_TRAVEL_TIME_FACTOR", "double");
		writeConstant(constants, UniverseConquestConstants.class, "PARAM_SOLARSYSTEM_HABITABILITY_MAX", "int");
		writeConstant(constants, UniverseConquestConstants.class, "PARAM_SOLARSYSTEM_SIZE_MAX", "int");
		writeConstant(constants, UniverseConquestConstants.class, "PARAM_SOLARSYSTEM_MAX_POPULATION_FACTOR", "int");
		writeConstant(constants, UniverseConquestConstants.class, "PARAM_FACTOR_BUILD", "double");

		return constants.toString();
	}

	/**
	 * Add a single line to the constants String.
	 * 
	 * @param constants - the builder for creating the constants.js
	 * @param cls - the class containing the {@link Constant}
	 * @param constantName - the name of the {@link Constant}
	 * @param type - the type of the {@link Constant} to use for the constants String
	 */
	private static void writeConstant(StringBuilder constants, Class<?> cls, String constantName, String type)
	{
		try
		{
			Constant<?> constant = (Constant<?>) cls.getField(constantName).get(null);
			String getterName = "as" + Character.toUpperCase(type.charAt(0)) + type.substring(1);
			Method valueGetter = Constant.class.getMethod(getterName);
			constants.append(cls.getSimpleName());
			constants.append(".");
			constants.append(constantName);
			constants.append(" = ");
			constants.append(valueGetter.invoke(constant));
			constants.append(";");
			constants.append(LINE_SEPARATOR);
		}
		catch(NoSuchFieldException e)
		{
			logger.error("no such constant: " + cls.getSimpleName() + "." + constantName);
		}
		catch(IllegalArgumentException e)
		{
			logger.error("could not access constant: " + cls.getSimpleName() + "." + constantName);
		}
		catch(IllegalAccessException e)
		{
			logger.error("could not access constant: " + cls.getSimpleName() + "." + constantName);
		}
		catch(SecurityException e)
		{
			logger.error("could not access constant: " + cls.getSimpleName() + "." + constantName);
		}
		catch(NoSuchMethodException e)
		{
			logger.error("illegal constant type: " + type);
		}
		catch(InvocationTargetException e)
		{
			logger.error("could not get constant '" + type + "' value: " + cls.getSimpleName() + "." + constantName);
		}
	}
}
