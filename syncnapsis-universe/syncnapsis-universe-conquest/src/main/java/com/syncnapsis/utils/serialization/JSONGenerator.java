package com.syncnapsis.utils.serialization;

import java.util.Collections;
import java.util.List;

import com.syncnapsis.data.model.Galaxy;
import com.syncnapsis.data.model.Match;
import com.syncnapsis.data.model.SolarSystem;
import com.syncnapsis.data.model.SolarSystemInfrastructure;

/**
 * Class for generating the JSON representation for different kind of data (e.g. the
 * {@link SolarSystem}s in a {@link Galaxy}).<br>
 * Since this class provides specialized functionality it not inherited from {@link Serializer}.<br>
 * 
 * @author ultimate
 */
public abstract class JSONGenerator
{
	public static String	LINE_SEPARATOR	= "\r\n";

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
				sb.replace(sb.length()-1, sb.length(), LINE_SEPARATOR + ']');
			else
				sb.setCharAt(sb.length()-1, ']');
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
				sb.replace(sb.length()-1, sb.length(), LINE_SEPARATOR + ']');
			else
				sb.setCharAt(sb.length()-1, ']');
		}
		else
		{
			sb.append(']');
		}

		return sb.toString();
	}
}
