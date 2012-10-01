package com.syncnapsis.providers.impl;

import com.syncnapsis.constants.GameBaseConstants;
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
	 * {@link GameBaseConstants#SESSION_EMPIRE_KEY}.
	 */
	public SessionBasedEmpireProvider()
	{
		super(GameBaseConstants.SESSION_EMPIRE_KEY);
	}
}
