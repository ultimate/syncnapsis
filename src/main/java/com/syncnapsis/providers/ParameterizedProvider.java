package com.syncnapsis.providers;

import javax.naming.OperationNotSupportedException;

/**
 * Abstract subtype of provider allowing parameterized access to variables (e.g. like a Map)
 * 
 * @author ultimate
 * @param <K> - the Type of the Key
 * @param <V> - the Type of the Value
 */
public abstract class ParameterizedProvider<K, V> implements Provider<V>
{
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.Provider#get()
	 */
	@Override
	public V get()
	{
		return get(null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.providers.Provider#set(java.lang.Object)
	 */
	@Override
	public void set(V value) throws UnsupportedOperationException
	{
		set(null, value);
	}

	/**
	 * Get the provided value for the given key
	 * 
	 * @param key - the key
	 * @return the value
	 */
	public abstract V get(K key);

	/**
	 * Set a provided value for the given key
	 * 
	 * @param key - the key
	 * @param value - the value
	 * @throws OperationNotSupportedException - if this Provider does not support the set-Operation
	 */
	public abstract void set(K key, V value) throws UnsupportedOperationException;
}
