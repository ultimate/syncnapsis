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
package com.syncnapsis.enums;

import com.syncnapsis.utils.math.Functions;

/**
 * Enum für die Spezifizierung und Verwendung von Funktionen. Die Funktion
 * werden für die Modellierung bzw. Berechnung von Verläufen benutzt.
 * Für die Berechnung der Verläufe kann sowohl der Funktionswert selbst, als
 * auch der Gradient (die Steigung) verwendet werden:
 * - 3D-Effekt mit Fase: LINEAR -> Gradient wird berechnet -> Schatteneffekt
 * - Sanfter Auslauf: LINEAR -> Funktionswert wird verwendet
 * 
 * @author ultimate
 */
public enum EnumFunction
{
	/**
	 * @see com.syncnapsis.utils.math.Functions#linear(double)
	 */
	LINEAR,
	/**
	 * @see com.syncnapsis.utils.math.Functions#linearSoft(double)
	 */
	LINEAR_SOFT,
	/**
	 * @see com.syncnapsis.utils.math.Functions#quad(double)
	 *      Die Funktion wird per default durch 2 dividiert um einen besseren
	 *      Effekt zu erhalten.
	 */
	QUAD,
	/**
	 * @see com.syncnapsis.utils.math.Functions#cubic(double)
	 *      Die Funktion wird per default durch 3 dividiert um einen besseren
	 *      Effekt zu erhalten.
	 */
	CUBIC,
	/**
	 * @see com.syncnapsis.utils.math.Functions#circularUnit(double)
	 *      Die Funktion wird per default durch 2 dividiert um einen besseren
	 *      Effekt zu erhalten.
	 */
	CIRCULAR,
	/**
	 * @see com.syncnapsis.utils.math.Functions#gauss(double)
	 */
	GAUSS;

	/**
	 * Berechnet den Funktionswert an der Stelle param mit zusätzlichen default
	 * Parametern.
	 * 
	 * @param param - die Stelle, an der der Funktionswert berechnet werden soll
	 * @return der berechnete Funktionswert
	 */
	public double function(double param)
	{
		return function(param, 0.1, 1);
	}

	/**
	 * Berechnet den Funktionswert an der Stelle param und weiteren Parametern.
	 * 
	 * @param param - die Stelle, an der der Funktionswert berechnet werden soll
	 * @param softness - wenn die Funktion softness unterstütz, gibt dieser
	 *            Parameter den Grad der Weichheit an, andernfalls wird er
	 *            ignoriert
	 * @param scale - die Skalierung der Funktion. Der Funktionswert wird mit
	 *            diesem Wert multipliziert.
	 * @return der berechnete Funktionswert
	 */
	public double function(double param, double softness, double scale)
	{
		switch(this)
		{
			case LINEAR:
				return scale * Functions.linear(param);
			case LINEAR_SOFT:
				return scale * Functions.linearSoft(param, softness);
			case QUAD:
				return scale * Functions.quad(param) / 2.0;
			case CUBIC:
				return scale * Functions.cubic(param) / 3.0;
			case CIRCULAR:
				return scale * Functions.circularUnit(param) / 2.0;
			case GAUSS:
				return scale * Functions.gauss(param);
		}
		return 0;
	}
}
