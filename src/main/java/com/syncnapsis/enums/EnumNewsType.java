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
