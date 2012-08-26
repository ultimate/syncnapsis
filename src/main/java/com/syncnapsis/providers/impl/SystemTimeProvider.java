package com.syncnapsis.providers.impl;

import com.syncnapsis.providers.TimeProvider;

/**
 * Provider-Class for accessing the current System time.<br>
 * Creating a Bean-Instance of this class offeres access to the system time via Springs
 * ApplicationContext with the additional opportunity to replace the TimeProvider for test purposes.
 * 
 * @see System#currentTimeMillis()
 * @author ultimate
 */
// TODO own impl-Modul?
public class SystemTimeProvider implements TimeProvider
{
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.TimeProvider#get()
	 */
	@Override
	public Long get()
	{
		return System.currentTimeMillis();
	}

	/**
	 * Operation not supported by this Provider
	 * @throws UnsupportedOperationException 
	 */
	@Override
	public void set(Long time) throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("Setting System time is not supported!");
	}
}
