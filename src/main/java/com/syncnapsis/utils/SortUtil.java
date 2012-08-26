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
	 * Orientierung für aufsteigende Sortierung
	 */
	public static final int	ASCENDING	= 1;
	/**
	 * Orientierung für abfsteigende Sortierung
	 */
	public static final int	DESCENDING	= -1;

	/**
	 * Sortiert eine Liste aufsteigend. Die übergebene Liste wird direkt sortiert und für die
	 * einfachere Verwendung zusätzlich zurückgegeben.
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
	 * Sortiert eine Liste absteigend. Die übergebene Liste wird direkt sortiert und für die
	 * einfachere Verwendung zusätzlich zurückgegeben.
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
	 * Sortiert eine Liste auf oder absteigend. Die übergebene Liste wird direkt sortiert und für
	 * die einfachere Verwendung zusätzlich zurückgegeben.
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
