package com.syncnapsis.providers;


/**
 * Simple Interface for Providers. All Providers have to implement <code>public T get()</code>.
 * 
 * @author ultimate
 * @param <T> - the Type this Provider provides
 */
public interface Provider<T>
{
	/**
	 * Get the provided value
	 * 
	 * @return the value
	 */
	public T get();

	/**
	 * Sets the provided value (e.g. for ThreadLocalProviders).
	 * 
	 * @param value - the new value
	 * @return - the old value
	 * @throws UnsupportedOperationException - if the set Operation is not supported by the
	 *             Provider
	 */
	public void set(T t) throws UnsupportedOperationException;
}
