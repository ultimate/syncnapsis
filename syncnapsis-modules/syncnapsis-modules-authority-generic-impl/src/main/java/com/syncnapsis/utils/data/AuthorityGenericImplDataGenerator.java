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
		// @formatter:off
		this.setExcludeTableList(new String[] {
				});
		// @formatter:on
		
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
