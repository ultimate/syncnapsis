package com.syncnapsis.data.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.web.ActionFilter;
import com.syncnapsis.websockets.service.rpc.RPCCall;
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
	 * The RPCCall-Object
	 * 
	 * @see RPCCall#getObject()
	 */
	protected String	object;
	/**
	 * The RPCCall-Method
	 * 
	 * @see RPCCall#getMethod()
	 */
	protected String	method;
	/**
	 * The RPCCall-Args
	 * 
	 * @see RPCCall#getArgs()
	 */
	protected Object[]	args;

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
	 * The RPCCall-Object
	 * 
	 * @see RPCCall#getObject()
	 * @return object
	 */
	@Column(nullable = false, length = LENGTH_NAME_LONG)
	public String getObject()
	{
		return object;
	}

	/**
	 * The RPCCall-Method
	 * 
	 * @see RPCCall#getMethod()
	 * @return method
	 */
	@Column(nullable = false, length = LENGTH_NAME_LONG)
	public String getMethod()
	{
		return method;
	}

	/**
	 * The RPCCall-Args
	 * 
	 * @see RPCCall#getArgs()
	 * @return args
	 */
	@Column(nullable = false)
	public Object[] getArgs()
	{
		return args;
	}

	/**
	 * Get object, method and args converted to an RPCCall-Object
	 * 
	 * @see RPCCall#getObject()
	 * @see RPCCall#getMethod()
	 * @see RPCCall#getArgs()
	 * @return the RPCCall
	 */
	@Transient
	public RPCCall getRPCCall()
	{
		return new RPCCall(object, method, args);
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
	 * The RPCCall-Object
	 * 
	 * @see RPCCall#getObject()
	 * @param object - the RPC-Object
	 */
	public void setObject(String object)
	{
		this.object = object;
	}

	/**
	 * The RPCCall-Method
	 * 
	 * @see RPCCall#getMethod()
	 * @param method - the RPC-Method
	 */
	public void setMethod(String method)
	{
		this.method = method;
	}

	/**
	 * The RPCCall-Args
	 * 
	 * @see RPCCall#getArgs()
	 * @param args - the RPC-Args
	 */
	public void setArgs(Object[] args)
	{
		this.args = args;
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
		result = prime * result + Arrays.hashCode(args);
		result = prime * result + maxUses;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		if(!Arrays.equals(args, other.args))
			return false;
		if(maxUses != other.maxUses)
			return false;
		if(object == null)
		{
			if(other.object != null)
				return false;
		}
		else if(!object.equals(other.object))
			return false;
		if(method == null)
		{
			if(other.method != null)
				return false;
		}
		else if(!method.equals(other.method))
			return false;
		if(code == null)
		{
			if(other.code != null)
				return false;
		}
		else if(!code.equals(other.code))
			return false;
		if(uses != other.uses)
			return false;
		return true;
	}
}
