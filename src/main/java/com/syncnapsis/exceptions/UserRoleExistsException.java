package com.syncnapsis.exceptions;

/**
 * RuntimeException, die geworfen wird, falls versucht wird eine Benutzerrolle
 * mit bereits vergebenem Namen zu speichern.
 * 
 * @author ultimate
 */
public class UserRoleExistsException extends RuntimeException
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
	public UserRoleExistsException(String message)
	{
		super(message);
	}
}
