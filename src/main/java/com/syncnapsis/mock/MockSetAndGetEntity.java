package com.syncnapsis.mock;

public class MockSetAndGetEntity<V>
{
	private V value;

	public V getValue()
	{
		return value;
	}

	public void setValue(V value)
	{
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	public void setValue0(Object value)
	{
		setValue((V) value);
	}
}
