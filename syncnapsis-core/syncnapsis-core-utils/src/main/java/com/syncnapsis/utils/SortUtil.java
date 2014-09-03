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
package com.syncnapsis.utils;

import java.util.Collections;
import java.util.List;

import com.syncnapsis.utils.reflections.MethodComparator;

/**
 * Util-Klasse zum sortieren von Listen anhand eines bestimmten Properties der enthaltenen Objekte.
 * Zum Vergleich der Objekte wird ein MethodComparator verwendet.
 * 
 * @see com.syncnapsis.utils.reflections.MethodComparator
 * @author ultimate
 */
public abstract class SortUtil
{
	/**
	 * Orientierung f�r aufsteigende Sortierung
	 */
	public static final int	ASCENDING	= 1;
	/**
	 * Orientierung f�r abfsteigende Sortierung
	 */
	public static final int	DESCENDING	= -1;

	/**
	 * Sortiert eine Liste aufsteigend. Die �bergebene Liste wird direkt sortiert und f�r die
	 * einfachere Verwendung zus�tzlich zur�ckgegeben.
	 * 
	 * @see com.syncnapsis.utils.reflections.MethodComparator
	 * @param <T> - der Typ der Liste
	 * @param unsorted - die Liste
	 * @param methodName - der Methodenname, der die Methode angibt anhand der sortiert wird
	 * @return die Liste
	 */
	public static <T> List<T> sortListAscending(List<T> unsorted, String methodName)
	{
		return sortList(unsorted, methodName, ASCENDING);
	}

	/**
	 * Sortiert eine Liste absteigend. Die �bergebene Liste wird direkt sortiert und f�r die
	 * einfachere Verwendung zus�tzlich zur�ckgegeben.
	 * 
	 * @see SortUtil#ASCENDING
	 * @see SortUtil#DESCENDING
	 * @see com.syncnapsis.utils.reflections.MethodComparator
	 * @param <T> - der Typ der Liste
	 * @param unsorted - die Liste
	 * @param methodName - der Methodenname, der die Methode angibt anhand der sortiert wird
	 * @return die Liste
	 */
	public static <T> List<T> sortListDescending(List<T> unsorted, String methodName)
	{
		return sortList(unsorted, methodName, DESCENDING);
	}

	/**
	 * Sortiert eine Liste auf oder absteigend. Die �bergebene Liste wird direkt sortiert und f�r
	 * die einfachere Verwendung zus�tzlich zur�ckgegeben.
	 * 
	 * @see com.syncnapsis.utils.reflections.MethodComparator
	 * @param <T> - der Typ der Liste
	 * @param unsorted - die Liste
	 * @param methodName - der Methodenname, der die Methode angibt anhand der sortiert wird
	 * @param orientation - die Orientierung
	 * @return die Liste
	 */
	public static <T extends Object> List<T> sortList(List<T> unsorted, String methodName, int orientation)
	{
		Collections.sort(unsorted, new MethodComparator<T>(methodName, orientation));
		return unsorted;
	}
}
