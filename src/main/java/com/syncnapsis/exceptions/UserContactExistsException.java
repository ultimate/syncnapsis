package com.syncnapsis.exceptions;

import com.syncnapsis.data.model.UserContact;

/**
 * Exception, die geworfen wird, wenn ein UserContact erstellt werden soll, der bereits vorhanden
 * ist.
 * 
 * @author ultimate
 */
public class UserContactExistsException extends RuntimeException
{
	/**
	 * Default serialVersionUID
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Der bereits vorhandene UserContact
	 */
	private UserContact			userContact;

	/**
	 * Erzeugt eine neue RuntimeException mit gegebener Nachricht
	 * 
	 * @param message - die Nachricht
	 * @param UserContact - der bereits vorhandene UserContact
	 */
	public UserContactExistsException(String message, UserContact userContact)
	{
		super(message);
		this.userContact = userContact;
	}

	/**
	 * Der bereits vorhandene UserContact
	 * 
	 * @return userContact
	 */
	public UserContact getUserContact()
	{
		return userContact;
	}
}
