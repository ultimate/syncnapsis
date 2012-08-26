package com.syncnapsis.providers;


/**
 * Provider-Interface allowing access to the current time.<br>
 * Creating a Bean of this type offers the opportunity to change the way of accessing the time via
 * SpringContext-Configuration.<br>
 * This was time can be manipulated during test cases or for modified timelines.
 * 
 * @author ultimate
 */
// TODO own api-Module?
// toReal(long time)
// fromReal(long time)
public interface TimeProvider extends Provider<Long>
{
	/**
	 * Returns the current time in ms
	 * 
	 * @return the time
	 */
	@Override
	public Long get();

	/**
	 * Sets the current time in ms.<br>
	 * May not be supported by some implementations.
	 * 
	 * @param time - the new time
	 * @return the old time
	 */
	@Override
	public void set(Long time) throws UnsupportedOperationException;
}
