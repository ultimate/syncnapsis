package com.syncnapsis.exceptions;

/**
 * 
 * RuntimeException, die geworfen werden kann, wenn ein Objekt, das anhand seines Namens oder seiner
 * ID geladen werden soll nicht in der Datenbank vorhanden ist. Die Fehlermeldung
 * kann so einfacher von anderen Datenbankfehler unterschieden werden.
 * 
 * @author ultimate
 *
 */
public class ObjectNotFoundException extends RuntimeException
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Erzeugt eine neue RuntimeException mit gegebenem Suchkriterium.
	 * 
	 * @param nameOrId - der Name oder ID des Objects, der nicht geladen werden konnte
	 */
	public ObjectNotFoundException(String type, String nameOrId)
	{
		super("Could not load " + type + ": " + nameOrId);
	}
}
