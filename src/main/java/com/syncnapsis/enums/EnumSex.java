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
public enum EnumSex
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
	public static EnumSex getDefault()
	{
		return unknown;
	}
}
