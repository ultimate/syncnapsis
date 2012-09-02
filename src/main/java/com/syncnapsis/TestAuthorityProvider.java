package com.syncnapsis;

import com.syncnapsis.providers.AuthorityProvider;

public class TestAuthorityProvider implements AuthorityProvider
{
	private Object[] authorities;

	@Override
	public Object[] get()
	{
		if(authorities != null)
			return authorities;
		return new Object[0];
	}

	@Override
	public void set(Object[] t) throws UnsupportedOperationException
	{
		this.authorities = t;
	}
}
