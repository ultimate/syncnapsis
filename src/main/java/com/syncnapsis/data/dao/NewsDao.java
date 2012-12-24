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
package com.syncnapsis.data.dao;

import java.util.Date;
import java.util.List;

import com.syncnapsis.data.model.News;
import com.syncnapsis.enums.EnumLocale;
import com.syncnapsis.enums.EnumNewsAge;

/**
 * Dao-Interface für den Zugriff auf News
 * 
 * @author ultimate
 */
public interface NewsDao extends GenericDao<News, Long>
{
	/**
	 * Lade alle NewsIds bis zu einem bestimmten maximalen Alter, ausgehend von
	 * einem Referenzdatum.
	 * 
	 * @param maxAge - the max age enum value
	 * @param maxAgeValue - the max age in millis for the enum value
	 * @param referenceDate - das Referenzdatum
	 * @return eine Liste mit den NewsIds
	 */
	public List<String> getIdsByMaxAge(EnumNewsAge maxAge, long maxAgeValue, Date referenceDate);

	/**
	 * Lade ein News-Objekt anhand einer NewsId und einer vorgegebene Sprache.
	 * Zu jeder NewsId kann es ein News-Objekt in jeder verfügbaren Sprache
	 * geben, sofern diese vom Verfasser der Nachricht angelegt wurden.
	 * 
	 * @param newsId - die NewsId
	 * @param locale - die Sprache
	 * @return das News-Objekt
	 */
	public News getByNewsIdAndLocale(String newsId, EnumLocale locale);
}
