package com.syncnapsis.enums;

import com.syncnapsis.constants.ApplicationBaseConstants;

/**
 * Enum für die Spezifizierung des Alters eines News-Objekts. Bei der Suche nach
 * News kann ein EnumNewsAge-Objekt übergeben werden und es werden alle News
 * zurückgeliefert, die neuer sind als das spezifizierte Alter. Die Auflösung
 * der Enum-Werte als Zeitangeben geschiet über die Tabelle Parameter und das
 * Namens-Pattern Constants.PARAM_NEWS_MAXAGE = "news.%L.maxAge"
 * 
 * @author ultimate
 */
public enum EnumNewsAge
{
	/**
	 * Alters-Interval 1 (kürzester Wert)
	 */
	length1,
	/**
	 * Alters-Interval 2 (2. kürzester Wert)
	 */
	length2,
	/**
	 * Alters-Interval 3
	 */
	length3,
	/**
	 * Alters-Interval 4
	 */
	length4,
	/**
	 * Alters-Interval 5
	 */
	length5,
	/**
	 * Alters-Interval 6
	 */
	length6,
	/**
	 * Alters-Interval 7
	 */
	length7,
	/**
	 * Alters-Interval 8 (2. längster Wert)
	 */
	length8,
	/**
	 * Alters-Interval 9 (längster Wert)
	 */
	length9;

	/**
	 * Wandelt dieses Enum in einen Primary-Key für die Verwendung mit der
	 * Parameter-Tabelle um. Es wird das Namens-Pattern
	 * Constants.PARAM_NEWS_MAXAGE = "news.%L.maxAge" verwendet.
	 * 
	 * @return der Parameter-Key zu diesem Enum zu diesem Enum
	 */
	public String getParameterKey()
	{
		return ApplicationBaseConstants.PARAM_NEWS_MAXAGE.replace("%L", this.toString());
	}
}
