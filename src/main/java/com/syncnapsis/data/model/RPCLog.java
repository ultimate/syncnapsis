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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.model.help.RPCCall;

/**
 * RPCLog-Entity for storing RPC information in the database.<br>
 * All incoming RPCCalls can be logged in the database for:<br>
 * <ul>
 * <li>logging</li>
 * <li>debugging</li>
 * <li>reconstruction</li>
 * <li>security reasons</li>
 * </ul>
 * 
 * @author ultimate
 */
@Entity
@Table(name = "rpclog")
public class RPCLog extends BaseObject<Long>
{
	/**
	 * The RPCCall performed
	 */
	protected RPCCall	rpcCall;

	/**
	 * The date and time the RPCCall was executed
	 */
	protected Date		executionDate;

	/**
	 * The result the RPCCall returned
	 */
	protected String	result;

	/**
	 * The remote address of the client that performed the RPCCall
	 */
	protected String	remoteAddr;

	/**
	 * The user agent (or software) that performed the RPCCall
	 */
	protected String	userAgent;

	/**
	 * The User that performed the RPCCall
	 */
	protected User		user;

	/**
	 * The RPCCall performed
	 * 
	 * @return rpcCall
	 */
	@Embedded
	public RPCCall getRPCCall()
	{
		return rpcCall;
	}

	/**
	 * The date and time the RPCCall was executed
	 * 
	 * @return executionDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getExecutionDate()
	{
		return executionDate;
	}

	/**
	 * The result the RPCCall returned
	 * 
	 * @return result
	 */
	@Column(nullable = false, length = LENGTH_TEXT)
	public String getResult()
	{
		return result;
	}

	/**
	 * The remote address of the client that performed the RPCCall
	 * 
	 * @return remoteAddr
	 */
	@Column(nullable = false, length = LENGTH_NAME_LONG)
	public String getRemoteAddr()
	{
		return remoteAddr;
	}

	/**
	 * The user agent (or software) that performed the RPCCall
	 * 
	 * @return userAgent
	 */
	@Column(nullable = false, length = LENGTH_NAME_LONG)
	public String getUserAgent()
	{
		return userAgent;
	}

	/**
	 * The User that performed the RPCCall
	 * 
	 * @return user
	 */
	@ManyToOne
	@JoinColumn(name = "fkUser", nullable = true)
	public User getUser()
	{
		return user;
	}

	/**
	 * The RPCCall performed
	 * 
	 * @param rpcCall - the RPCCall
	 */
	public void setRPCCall(RPCCall rpcCall)
	{
		this.rpcCall = rpcCall;
	}

	/**
	 * The date and time the RPCCall was executed
	 * 
	 * @param executionDate - the date and time
	 */
	public void setExecutionDate(Date executionDate)
	{
		this.executionDate = executionDate;
	}

	/**
	 * The result the RPCCall returned
	 * 
	 * @param result - the result
	 */
	public void setResult(String result)
	{
		this.result = result;
	}

	/**
	 * The remote address of the client that performed the RPCCall
	 * 
	 * @param remoteAddr - the address
	 */
	public void setRemoteAddr(String remoteAddr)
	{
		this.remoteAddr = remoteAddr;
	}

	/**
	 * The user agent (or software) that performed the RPCCall
	 * 
	 * @param userAgent - the user agent
	 */
	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}

	/**
	 * The User that performed the RPCCall
	 * 
	 * @param user - the User
	 */
	public void setUser(User user)
	{
		this.user = user;
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
		result = prime * result + ((executionDate == null) ? 0 : executionDate.hashCode());
		result = prime * result + ((remoteAddr == null) ? 0 : remoteAddr.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((rpcCall == null) ? 0 : rpcCall.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userAgent == null) ? 0 : userAgent.hashCode());
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
		RPCLog other = (RPCLog) obj;
		if(executionDate == null)
		{
			if(other.executionDate != null)
				return false;
		}
		else if(!executionDate.equals(other.executionDate))
			return false;
		if(remoteAddr == null)
		{
			if(other.remoteAddr != null)
				return false;
		}
		else if(!remoteAddr.equals(other.remoteAddr))
			return false;
		if(result == null)
		{
			if(other.result != null)
				return false;
		}
		else if(!result.equals(other.result))
			return false;
		if(rpcCall == null)
		{
			if(other.rpcCall != null)
				return false;
		}
		else if(!rpcCall.equals(other.rpcCall))
			return false;
		if(user == null)
		{
			if(other.user != null)
				return false;
		}
		else if(!user.equals(other.user))
			return false;
		if(userAgent == null)
		{
			if(other.userAgent != null)
				return false;
		}
		else if(!userAgent.equals(other.userAgent))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("rpcCall", rpcCall).append("executionDate", executionDate).append("result", result).append("remoteAddr", remoteAddr)
				.append("userAgent", userAgent).append("user", user.getId());
		return builder.toString();
	}
}
