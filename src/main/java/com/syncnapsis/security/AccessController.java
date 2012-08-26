package com.syncnapsis.security;

/**
 * General Interface for Instances controlling the access to other Objects.
 * AccessControllers can be registered for their Object-Type at the SecurityManager
 * 
 * @see com.syncnapsis.security.SecurityManager
 * @author ultimate
 * @param <T> - the Type of Object to access
 */
public interface AccessController<T>
{
	// Operations to be checked - START

	/**
	 * UNDEFINED-Operation
	 */
	public static final int	UNDEFINED	= 0x00;
	/**
	 * READ-Operation
	 */
	public static final int	READ		= 0x01;
	/**
	 * WRITE-Operation
	 */
	public static final int	WRITE		= 0x02;
	/**
	 * Invoke a method
	 */
	public static final int	INVOKE		= 0x03;
	/**
	 * Call a method
	 * 
	 * @see AccessController#INVOKE
	 */
	public static final int	CALL		= INVOKE;

	// Operations to be checked - END

	/**
	 * Get the target Class this AccessController is associated with.
	 * 
	 * @return the target Class
	 */
	public Class<T> getTargetClass();

	/**
	 * Check the accessibility of a target and the given operation for the defined authorities.
	 * 
	 * @param target - the target to access
	 * @param operation - the operation to perform
	 * @param authorities - the authorities to check
	 * @return true or false
	 */
	public boolean isAccessible(T target, int operation, Object... authorities);
}
