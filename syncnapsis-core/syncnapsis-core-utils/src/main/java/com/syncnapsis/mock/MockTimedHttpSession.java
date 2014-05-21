/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
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
