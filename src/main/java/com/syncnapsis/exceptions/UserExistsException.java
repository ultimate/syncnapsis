package com.syncnapsis.exceptions;

/**
 * Exception, die geworfen wird, falls versucht wird einen neuen Benutzer mit
 * bereits vorhandenem Benutzernamen oder vorhandener e-Mail-Adresse zu
 * speichern. Ein Benutzername gilt auch unter Ignorierung der Groﬂ- und
 * Kleinschreibung als vorhanden.
 * 
 * @author ultimate
 */
public class UserExistsException extends RuntimeException
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Erzeugt eine neue RuntimeException mit gegebener Nachricht
	 * 
	 * @param message - die Nachricht
	 */
	public UserExistsException(String message)
	{
		super(message);
	}
}
