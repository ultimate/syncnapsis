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
package com.syncnapsis.enums;

/**
 * Enum f�r die Spezifizierung eines Galaxie-Typs. Die Galaxie-Typen sind den
 * folgenden Quellen entnommen. 'x' steht dabei f�r einen Platzhalter, der bei
 * der Generierung durch Parameter ersetzt wird.
 * http://de.wikipedia.org/wiki/Galaxie
 * http://upload.wikimedia.org/wikipedia/commons/8/8a/Hubble_sequence_photo.png
 * 
 * @author ultimate
 */
public enum EnumGalaxyType
{
	/**
	 * Elliptische Galaxien
	 */
	Ex,
	/**
	 * Lentikul�re (linsenf�rmige) Galaxien
	 */
	S0,
	/**
	 * Balkengalaxien (als Grundlage f�r SBx)
	 */
	SB0,
	/**
	 * Spiralgalaxien
	 */
	Sx,
	/**
	 * Balkenspiralgalaxien
	 */
	SBx,
	/**
	 * Irregul�re (unregelm��ige) Galaxien
	 */
	Rx,
	/**
	 * zus�tzliche Bogensegmente f�r die Gestaltung von Galaxien
	 */
	Ax;
}
