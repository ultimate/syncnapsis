package com.syncnapsis.mock;

import com.syncnapsis.providers.TimeProvider;

public class MockTimeProvider implements TimeProvider
{
	private long	time;

	public MockTimeProvider()
	{
		this.time = 0;
	}

	public MockTimeProvider(long time)
	{
		this.time = time;
	}

	@Override
	public void set(Long time)
	{
		this.time = time;
	}

	@Override
	public Long get()
	{
		return time;
	}
}
