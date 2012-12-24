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

/**
 * Enum für die Spezifizierung des Geschlechts eines Users. Es können hier
 * sowohl echte Angaben, als auch Spaß-Angaben gemacht werden. Ggf. kann die
 * Liste der Geschlechter auch noch erweitert werden, in Anlehnung an das
 * Spiel...
 * Die Geschlechtsnamen sind selbsterklärend...
 * 
 * @author ultimate
 */
public enum EnumGender
{
	/**
	 * unbekanntes Geschlecht (default)
	 */
	unknown,
	/**
	 * männlich
	 */
	male,
	/**
	 * weiblich
	 */
	female,
	/**
	 * transsexuell
	 */
	transsexual,
	/**
	 * Maschine
	 */
	machine;

	/**
	 * Gibt das default Geschlect zurück: unknown
	 * 
	 * @return das default Geschlect
	 */
	public static EnumGender getDefault()
	{
		return unknown;
	}
}
