/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
package com.syncnapsis.websockets.service.rpc;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.utils.MapUtil;
import com.syncnapsis.utils.serialization.Mapable;
import com.syncnapsis.websockets.Connection;

/**
 * POJO representing RPCMessages send from client to server or vice versa.
 * 
 * @author ultimate
 */
public class RPCMessage implements Mapable<RPCMessage>
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

	/**
	 * The Connection used to send the message
	 */
	protected Connection				connection;
	/**
	 * The Client-Call-ID (unique for this client)
	 */
	protected Long						ccid;
	/**
	 * The Server-Call-ID (unique for this server and all clients)
	 */
	protected Long						scid;
	/**
	 * The data contained in the message (either a RPCCall or a result)
	 */
	protected Object					data;

	/**
	 * Empty-Constructor used for generic instantiation.
	 */
	public RPCMessage()
	{
	}

	/**
	 * RPCMessage with no Connection
	 * 
	 * @see RPCMessage#RPCMessage(Connection, Long, Long, Object)
	 * @param ccid - The Client-Call-ID (unique for this client)
	 * @param scid - The Server-Call-ID (unique for this server and all clients)
	 * @param data - The data contained in the message (either a RPCCall or a result)
	 */
	public RPCMessage(Long ccid, Long scid, Object data)
	{
		this(null, ccid, scid, data);
	}

	/**
	 * RPCMessage with Connection
	 * 
	 * @param connection - the Connection used to send the message
	 * @param ccid - The Client-Call-ID (unique for this client)
	 * @param scid - The Server-Call-ID (unique for this server and all clients)
	 * @param data - The data contained in the message (either a RPCCall or a result)
	 */
	public RPCMessage(Connection connection, Long ccid, Long scid, Object data)
	{
		super();
//		Assert.notNull(data); // message may be null in some cases
		this.connection = connection;
		this.ccid = ccid;
		this.scid = scid;
		this.data = data;
	}

	/**
	 * The Client-Call-ID (unique for this client)
	 * 
	 * @return ccid
	 */
	public Long getCcid()
	{
		return ccid;
	}

	/**
	 * The Server-Call-ID (unique for this server and all clients)
	 * 
	 * @return
	 */
	public Long getScid()
	{
		return scid;
	}

	/**
	 * The data contained in the message (either a RPCCall or a result)
	 * 
	 * @return data
	 */
	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	/**
	 * The Connection used to send the message
	 * 
	 * @return connection
	 */
	public Connection getConnection()
	{
		return connection;
	}

	/**
	 * The Connection used to send the message
	 * 
	 * @param connection - the connection
	 */
	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapable#toMap(java.lang.Object[])
	 */
	@Override
	public Map<String, ?> toMap(Object... authorities)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		if(ccid != null)
			m.put("ccid", this.ccid);
		if(scid != null)
			m.put("scid", this.scid);
		m.put("data", this.data);
		return m;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.serialization.Mapable#fromMap(java.util.Map, java.lang.Object[])
	 */
	@Override
	public RPCMessage fromMap(Map<String, ?> map, Object... authorities)
	{
		this.ccid = MapUtil.getLong(map, "ccid", true);
		this.scid = MapUtil.getLong(map, "scid", true);
		this.data = map.get("data");
		return this;
	}
}
