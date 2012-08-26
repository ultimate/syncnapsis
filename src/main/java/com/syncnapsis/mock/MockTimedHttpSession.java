package com.syncnapsis.mock;

import com.syncnapsis.providers.TimeProvider;
import org.springframework.mock.web.MockHttpSession;


public class MockTimedHttpSession extends MockHttpSession
{
	protected long lastAccessedTime = 0;
	
	private TimeProvider timeProvider;
	
	public MockTimedHttpSession(TimeProvider timeProvider)
	{
		this.timeProvider = timeProvider;
	}
	
	@Override
	public long getLastAccessedTime()
	{
		return lastAccessedTime;
	}
	
	public void setLastAccessedTime(long lastAccessedTime)
	{
		this.lastAccessedTime = lastAccessedTime;
	}

	@Override
	public void access()
	{
		this.lastAccessedTime = timeProvider.get();
		super.access();
	}
}
