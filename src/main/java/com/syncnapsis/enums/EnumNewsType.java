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
 * Enum für die Spezifizierung der Kategorie eines News-Objektes. Je nach
 * Kategorie einer News, wird dies besonders hervorgehoben oder ist für
 * den Benutzer besonders wichtig. News sind Nachrichten, die für alle 
 * Spieler gelten.
 * 
 * @author ultimate
 */
@Deprecated
public enum EnumNewsType
{
	/**
	 * Systemneuigkeiten:
	 * - Serverwartung
	 * - Serverausfälle
	 * - ...
	 */
	system,
	/**
	 * Neuigkeiten zum Entwicklungsstand des Spiels:
	 * - neue Features
	 * - Bug-Fixes
	 * - Versionsänderungen
	 * - ...
	 */
	development,
	/**
	 * Universumsneuigkeiten:
	 * - Neue Galaxien
	 * - Neue Planeten/Systeme
	 * - Zufallsereignisse (Supernova, Galaktischer Sturm...)
	 * - ...
	 */
	universe,
	/**
	 * Handelsneuigkeiten:
	 * - Besondere Wechselkursschwankungen
	 * - Sonderangebote
	 * - ...
	 */
	trade,
	/**
	 * Besonders wichtige diplomatische Neuigkeiten:
	 * - Diplomatische Änderungen zwischen den größten Allianzen oder Spielern
	 * - ...
	 */
	diplomacy, 
	/**
	 * Neuigkeiten zu Preisen/Gewinnen:
	 * - Allianzen/Spieler mit besonderen Verdiensten
	 * - Allianzen/Spieler mit Wochen-/Monatstiteln
	 * - ...
	 */
	awards,
	/**
	 * Sonstige lustige Neuigkeiten:
	 * - Spaßnachrichten
	 * - Witze
	 * - ...
	 */
	fun, 
	/**
	 * Sonstige Neuigkeiten
	 */
	others;
}
