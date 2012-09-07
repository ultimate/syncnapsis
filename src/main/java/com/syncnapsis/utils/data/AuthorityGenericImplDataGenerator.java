package com.syncnapsis.utils.data;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.Assert;

import com.syncnapsis.data.model.AuthoritiesGenericImpl;
import com.syncnapsis.data.model.Authority;
import com.syncnapsis.data.service.AuthorityManager;

public class AuthorityGenericImplDataGenerator extends AuthorityDataGenerator<AuthoritiesGenericImpl>
{
	protected AuthorityManager authorityManager;
	
	public AuthorityManager getAuthorityManager()
	{
		return authorityManager;
	}

	public void setAuthorityManager(AuthorityManager authorityManager)
	{
		this.authorityManager = authorityManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		
		Assert.notNull(authorityManager, "authorityManager must not be null!");
	}
	
	@Override
	public AuthoritiesGenericImpl createAuthorities(Map<String, Boolean> authoritiesMap)
	{
		AuthoritiesGenericImpl authorities = new AuthoritiesGenericImpl();
		authorities.setAuthoritiesGranted(new LinkedList<Authority>());
		authorities.setAuthoritiesNotGranted(new LinkedList<Authority>());

		Authority authority;
		for(Entry<String, Boolean> e : authoritiesMap.entrySet())
		{
			try
			{
				if(e.getKey().startsWith("Party"))
					continue;
				authority = authorityManager.getByName(e.getKey());
				if(e.getValue())
					authorities.getAuthoritiesGranted().add(authority);
				else
					authorities.getAuthoritiesNotGranted().add(authority);
			}
			catch(Exception ex)
			{
				logger.error("could not set '" + e.getKey() + "': " + e.getValue());
			}
		}
		return authoritiesManager.save(authorities);
	}

}
