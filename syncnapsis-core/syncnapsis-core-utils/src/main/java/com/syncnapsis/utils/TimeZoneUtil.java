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
package com.syncnapsis.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util-Klasse, die einen vereinfachten Zugriff auf die von Java zur Verfügung gestellten TimeZones
 * ermöglicht. Dazu werden die in TimeZone vorhanden Zeitzonen nach Regionen aufgegliedert (Teil vor
 * dem '/'). Auf diese Weise ist später an der Oberfläche für den Benutzer eine differenzierte
 * Auswahl seiner Zeitzone möglich.
 * 
 * @author ultimate
 */
public abstract class TimeZoneUtil
{
	/**
	 * Logger-Instanz
	 */
	protected static transient final Logger			logger		= LoggerFactory.getLogger(TimeZoneUtil.class);

	/**
	 * Map, die zu jeder Region die darin enthalten IDs enthält.
	 * Die Map wird einmalig initialisiert um später schneller abrufbar zu sein.
	 */
	private static TreeMap<String, List<String>>	sortedIds;
	/**
	 * Die Liste aller Regionen.
	 * Die Liste wird einmalig initialisiert um später schneller abrufbar zu sein.
	 */
	private static List<String>						regions;
	/**
	 * Wurden die Map und die Liste bereits initialisiert?
	 */
	private static boolean							initialized	= false;

	/**
	 * Gibt eine Zeitzone zur gegebene timeZoneId zurück.
	 * Entspricht TimeZone.getTimeZone(timeZoneId)
	 * 
	 * @param timeZoneId - die ID der Zeitzone
	 * @return das TimeZone-Objekt
	 */
	public static TimeZone getTimeZone(String timeZoneId)
	{
		return TimeZone.getTimeZone(timeZoneId);
	}

	/**
	 * Gibt eine Zeitzone zur gegebene Region und ID zurück.
	 * Kurzform für getTimeZone(region + "/" + id)
	 * 
	 * @param region - die Region
	 * @param id - die ID innerhalb der Region
	 * @return das TimeZone-Objekt
	 */
	public static TimeZone getTimeZone(String region, String id)
	{
		return getTimeZone(region + "/" + id);
	}

	/**
	 * Die Liste aller Regionen.
	 * 
	 * @return die Liste (unmodifizierbar)
	 */
	public static List<String> getRegions()
	{
		if(!initialized)
			initialize();
		return regions;
	}

	/**
	 * Gibt alle IDs zu einer Region zurück, die in der Map gespeichert sind.
	 * 
	 * @param region - die Region
	 * @return die Liste (unmodifizierbar)
	 */
	public static List<String> getIdsByRegions(String region)
	{
		if(!initialized)
			initialize();
		if(sortedIds.containsKey(region))
			return sortedIds.get(region);
		return Collections.unmodifiableList(new ArrayList<String>());
	}

	/**
	 * Return the Default TimeZoneID (e.g. for user registration or first application visits).
	 * 
	 * @return the default ID
	 */
	public static String getDefaultID()
	{
		return TimeZone.getDefault().getID();
	}

	/**
	 * Initialisiert die Liste der Regionen und die Map der IDs für die weitere Benutzung.
	 * Es werden dafür alle verfügbaren timeZondIds aus TimeZone analysiert. Der Teil der timeZoneId
	 * vor '/' wird als Region interpretiert, der Teil dahinter als ID innerhalb der Region.
	 * TimeZoneIds ohne '/' werden ignoriert, da dies allgemeine Zeitzonen sind, welche zu den
	 * anderen vorhanden redundant sind.
	 * Alle hier erzeugten Listen sind unmodifizierbar, damit eine Manipulation ausgeschlossen ist.
	 */
	public static void initialize()
	{
		sortedIds = new TreeMap<String, List<String>>();
		regions = new ArrayList<String>();

		String region, id;
		for(String timeZoneId : TimeZone.getAvailableIDs())
		{
			if(timeZoneId.contains("/") && !timeZoneId.startsWith("System"))
			{
				region = timeZoneId.substring(0, timeZoneId.indexOf("/"));
				id = timeZoneId.substring(timeZoneId.indexOf("/") + 1);
				if(!regions.contains(region))
				{
					regions.add(region);
					sortedIds.put(region, new ArrayList<String>());
				}
				sortedIds.get(region).add(id);
			}
			else
			{
				// ignorieren, so viele brauchen wir nicht...
			}
		}

		// regionen alphabetisch sortieren
		SortUtil.sortListAscending(regions, "toLowerCase");
		regions = Collections.unmodifiableList(regions);

		// regionslisten alphabetisch sortieren
		List<String> regionList;
		for(String regionKey : sortedIds.keySet())
		{
			regionList = sortedIds.get(regionKey);
			SortUtil.sortListAscending(regionList, "toLowerCase");
			sortedIds.put(regionKey, Collections.unmodifiableList(regionList));
		}
		initialized = true;
	}
}
