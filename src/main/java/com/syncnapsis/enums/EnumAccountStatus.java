package com.syncnapsis.enums;

/**
 * Enum für die Spezifizierung des Accountsstatus eines Users.
 * 
 * @author ultimate
 */
public enum EnumAccountStatus
{
	/**
	 * User ist aktiv (Normalzustand)
	 */
	active,
	/**
	 * User ist gesperrt (wegen Regelverstoß oder nach Neuanmeldung bis zum Freischalten)
	 */
	locked,
	/**
	 * User ist im Urlaubsmodus
	 */
	vacation,
	/**
	 * User ist inaktiv bzw. gelöscht
	 */
	inactive;
}
