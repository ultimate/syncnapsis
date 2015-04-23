package com.syncnapsis.utils.serialization;

import java.util.List;

import com.syncnapsis.data.model.Galaxy;
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
	private JSONGenerator()
	{

	}

	/**
	 * Generate the JSON representation for the given {@link List} of {@link SolarSystem}s in the
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
	public static String toJSON(List<SolarSystem> solarSystems)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		for(SolarSystem sys : solarSystems)
		{
			sb.append(sys.getId());
			sb.append(',');
			sb.append(sys.getCoords().getX());
			sb.append(',');
			sb.append(sys.getCoords().getY());
			sb.append(',');
			sb.append(sys.getCoords().getZ());
		}
		sb.append("]");

		return sb.toString();
	}

	/**
	 * Generate the JSON representation for the given {@link List} of
	 * {@link SolarSystemInfrastructure}s in the
	 * form:<br>
	 * <code><pre> [
	 *   [id, sys.x, sys.y, sys.z, size, hab], // for each solar system infrastructure
	 *   ...
	 * ]
	 * </pre></code> (whitespaces included here for better readability only)
	 * 
	 * @param solarSystems - the {@link List} of {@link SolarSystem}s
	 * @return the JSON string
	 */
	public static String toJSON(List<SolarSystemInfrastructure> solarSystemInfrastructures)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		for(SolarSystemInfrastructure inf : solarSystemInfrastructures)
		{
			sb.append(inf.getId());
			sb.append(',');
			sb.append(inf.getSolarSystem().getCoords().getX());
			sb.append(',');
			sb.append(inf.getSolarSystem().getCoords().getY());
			sb.append(',');
			sb.append(inf.getSolarSystem().getCoords().getZ());
			sb.append(',');
			sb.append(inf.getSize());
			sb.append(',');
			sb.append(inf.getHabitability());
		}
		sb.append("]");

		return sb.toString();
	}
}
