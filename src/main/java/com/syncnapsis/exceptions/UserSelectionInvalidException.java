package com.syncnapsis.exceptions;


/**
 * Exception, die geworfen wird, wenn für z.B bei der Erstellung eines UserContacts ein User
 * ausgewählt wird, zu dem bereits ein UserContact mit dem anderen User existiert.
 * 
 * @author ultimate
 */
public class UserSelectionInvalidException extends RuntimeException
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
	public UserSelectionInvalidException(String message)
	{
		super(message);
	}
}
