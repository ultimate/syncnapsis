package com.syncnapsis.exceptions;

/**
 * RuntimeException, die geworfen wird, wenn ein User, der anhand seines Namens
 * geladen werden soll nicht in der Datenbank vorhanden ist. Die Fehlermeldung
 * kann so einfacher von anderen Datenbankfehler unterschieden werden.
 * 
 * @author ultimate
 */
public class UserNotFoundException extends ObjectNotFoundException
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Erzeugt eine neue RuntimeException mit gegebenem Benutzernamen.
	 * 
	 * @param username - der Name des Benutzers, der nicht geladen werden konnte
	 */
	public UserNotFoundException(String username)
	{
		super("User", username);
	}
}
