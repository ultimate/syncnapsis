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
package com.syncnapsis.data.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.constants.GameBaseConstants;
import com.syncnapsis.data.dao.EmpireDao;
import com.syncnapsis.data.model.Empire;
import com.syncnapsis.data.model.Player;
import com.syncnapsis.data.service.EmpireManager;
import com.syncnapsis.security.BaseGameManager;

/**
 * Manager-Implementierung für den Zugriff auf Empire.
 * 
 * @author ultimate
 */
public class EmpireManagerImpl extends GenericNameManagerImpl<Empire, Long> implements EmpireManager, InitializingBean
{
	/**
	 * EmpireDao für den Datenbankzugriff
	 */
	private EmpireDao			empireDao;
	/**
	 * The SecurityManager (BaseGameManager)
	 */
	protected BaseGameManager	securityManager;

	/**
	 * Standard-Constructor
	 * 
	 * @param empireDao - EmpireDao für den Datenbankzugriff
	 */
	public EmpireManagerImpl(EmpireDao empireDao)
	{
		super(empireDao);
		this.empireDao = empireDao;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(securityManager, "securityManager must not be null!");
	}

	/**
	 * The SecurityManager (BaseGameManager)
	 * 
	 * @return securityManager
	 */
	public BaseGameManager getSecurityManager()
	{
		return securityManager;
	}

	/**
	 * The SecurityManager (BaseGameManager)
	 * 
	 * @param securityManager - the SecurityManager
	 */
	public void setSecurityManager(BaseGameManager securityManager)
	{
		this.securityManager = securityManager;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.EmpireManager#getByPlayer(java.lang.Long)
	 */
	@Override
	public List<Empire> getByPlayer(Long userId)
	{
		return empireDao.getByPlayer(userId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.impl.GenericNameManagerImpl#isNameValid(java.lang.String)
	 */
	@Override
	public boolean isNameValid(String name)
	{
		if(name == null)
			return false;
		if(securityManager.getNameValidator() != null)
			return securityManager.getNameValidator().isValid(name);
		else
			return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.service.EmpireManager#create(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Empire create(String fullName, String shortName, String description, String imageURL, String primaryColor, String secondaryColor)
	{
		if(!isNameValid(fullName))
			throw new IllegalArgumentException(GameBaseConstants.ERROR_EMPIRENAME_INVALID);
		if(!isNameValid(shortName))
			throw new IllegalArgumentException(GameBaseConstants.ERROR_EMPIRENAME_INVALID);

		Player player = securityManager.getPlayerProvider().get();
		if(player == null)
			throw new IllegalArgumentException("player must not be null!");

		if(player.getEmpires().size() >= player.getRole().getMaxEmpires())
			throw new IllegalArgumentException(GameBaseConstants.ERROR_MAXEMPIRES);

		Empire empire = new Empire();
		empire.setActivated(true);
		empire.setDescription(description);
		empire.setDissolutionDate(null);
		empire.setFoundationDate(new Date(securityManager.getTimeProvider().get()));
		empire.setFullName(fullName);
		empire.setImageURL(imageURL);
		empire.setPlayer(player);
		empire.setPrimaryColor(primaryColor);
		empire.setSecondaryColor(secondaryColor);
		empire.setShortName(shortName);

		return save(empire);
	}
}
