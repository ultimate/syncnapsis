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
package com.syncnapsis.data.dao;

import java.util.List;

import com.syncnapsis.data.model.Alliance;

/**
 * Dao-Interface f�r den Zugriff auf Alliance
 * 
 * @author ultimate
 */
public interface AllianceDao extends GenericNameDao<Alliance, Long>
{
	/**
	 * Lade alle Allianzen zu einem Imperium.<br/>
	 * Da die Verkn�pfung von Imperium und Allianz �ber Allianzr�nge geschieht,
	 * muss hier eine Filterung erfolgen, die verhindert, dass eine Allianz, zu
	 * der ein Imperium mehrere R�nge hat, doppelt zur�ck gegeben wird.
	 * 
	 * @param empireId - die ID des Imperiums
	 * @return eine Liste aller Allianzen des Imperiums
	 */
	public List<Alliance> getByEmpire(Long empireId);
}
