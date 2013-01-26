/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.data.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.help.RPCCall;
import com.syncnapsis.web.ActionFilter;
import com.syncnapsis.websockets.service.rpc.RPCHandler;

/**
 * Action-Entity containing an RPCCall associated with a unique code.<br>
 * If this code is passed to an {@link ActionFilter} the matching Action will be loaded and the
 * underlying RPCCall can be executed by the {@link RPCHandler}.
 * 
 * @author ultimate
 */
@Entity
@Table(name = "action")
public class Action extends BaseObject<Long>
{
	/**
	 * The unique code of this action
	 */
	protected String	code;

	/**
	 * The number of times this action has been used
	 */
	protected int		uses;
	/**
	 * The number of times this action may be used
	 */
	protected int		maxUses;

	/**
	 * The RPCCall to perform
	 */
	protected RPCCall	rpcCall;

	/**
	 * The unique code of this action
	 * 
	 * @return code
	 */
	@Column(nullable = false, unique = true, length = LENGTH_ACTION)
	public String getCode()
	{
		return code;
	}

	/**
	 * The number of times this action has been used
	 * 
	 * @return uses
	 */
	@Column(nullable = false)
	public int getUses()
	{
		return uses;
	}

	/**
	 * The number of times this action may be used
	 * 
	 * @return maxUses
	 */
	@Column(nullable = false)
	public int getMaxUses()
	{
		return maxUses;
	}

	/**
	 * The RPCCall to perform
	 * 
	 * @return rpcCall
	 */
	@Embedded
	public RPCCall getRPCCall()
	{
		return rpcCall;
	}

	/**
	 * The unique code of this action
	 * 
	 * @param code - the code
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * The number of times this action has been used
	 * 
	 * @param uses - the number of uses
	 */
	public void setUses(int uses)
	{
		this.uses = uses;
	}

	/**
	 * The number of times this action may be used
	 * 
	 * @param maxUses - the max number of uses
	 */
	public void setMaxUses(int maxUses)
	{
		this.maxUses = maxUses;
	}
	
	/**
	 * The RPCCall to perform
	 * 
	 * @param rpcCall - the RPCCall
	 */
	public void setRPCCall(RPCCall rpcCall)
	{
		this.rpcCall = rpcCall;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + maxUses;
		result = prime * result + ((rpcCall == null) ? 0 : rpcCall.hashCode());
		result = prime * result + uses;
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.data.model.base.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if(code == null)
		{
			if(other.code != null)
				return false;
		}
		else if(!code.equals(other.code))
			return false;
		if(maxUses != other.maxUses)
			return false;
		if(rpcCall == null)
		{
			if(other.rpcCall != null)
				return false;
		}
		else if(!rpcCall.equals(other.rpcCall))
			return false;
		if(uses != other.uses)
			return false;
		return true;
	}
}
