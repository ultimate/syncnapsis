package com.syncnapsis.providers.impl;

import com.syncnapsis.constants.BaseGameConstants;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.providers.EmpireProvider;
import com.syncnapsis.providers.SessionBasedProvider;

/**
 * Extension of SessionBasedProvider for the current Empire.
 * 
 * @author ultimate
 */
public class SessionBasedEmpireProvider extends SessionBasedProvider<Empire> implements EmpireProvider
{
	/**
	 * Default-Constructor configuring SessionBasedProvider with
	 * {@link BaseGameConstants#SESSION_EMPIRE_KEY}.
	 */
	public SessionBasedEmpireProvider()
	{
		super(BaseGameConstants.SESSION_EMPIRE_KEY);
	}
}
