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
package com.syncnapsis.mock;

import java.util.LinkedList;
import java.util.List;

import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.Message;
import com.syncnapsis.websockets.service.BaseService;

/**
 * MockService for test purposes.<br>
 * Every call on methods of the kind "onX(..)" will be tracked in an event list.
 * 
 * @author ultimate
 */
public class MockService extends BaseService
{
	/**
	 * The event list
	 */
	protected List<Event>	events	= new LinkedList<Event>();

	/**
	 * The event list
	 * 
	 * @return events
	 */
	public List<Event> getEvents()
	{
		return events;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onOpen(com.syncnapsis.websockets.Connection)
	 */
	@Override
	public void onOpen(Connection connection)
	{
		events.add(new Event("onOpen", connection));
		super.onOpen(connection);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onClose(com.syncnapsis.websockets.Connection,
	 * int, java.lang.String)
	 */
	@Override
	public void onClose(Connection connection, int closeCode, String message)
	{
		events.add(new Event("onClose", connection, closeCode, message));
		super.onClose(connection, closeCode, message);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onHandshake(com.syncnapsis.websockets.Connection
	 * )
	 */
	@Override
	public void onHandshake(Connection connection)
	{
		events.add(new Event("onHandshake", connection));
		super.onHandshake(connection);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onMessage(com.syncnapsis.websockets.Connection,
	 * java.lang.String)
	 */
	@Override
	public void onMessage(Connection connection, String data)
	{
		events.add(new Event("onMessage", connection, data));
		super.onMessage(connection, data);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onMessage(com.syncnapsis.websockets.Connection,
	 * byte[], int, int)
	 */
	@Override
	public void onMessage(Connection connection, byte[] data, int offset, int length)
	{
		events.add(new Event("onMessage", connection, data, offset, length));
		super.onMessage(connection, data, offset, length);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onMessage(com.syncnapsis.websockets.Connection,
	 * com.syncnapsis.websockets.Message)
	 */
	@Override
	public void onMessage(Connection connection, Message message)
	{
		events.add(new Event("onMessage", connection, message));
		super.onMessage(connection, message);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onControl(com.syncnapsis.websockets.Connection,
	 * byte, byte[], int, int)
	 */
	@Override
	public boolean onControl(Connection connection, byte controlCode, byte[] data, int offset, int length)
	{
		events.add(new Event("onControl", connection, controlCode, data, offset, length));
		return super.onControl(connection, controlCode, data, offset, length);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.syncnapsis.websockets.service.BaseService#onFrame(com.syncnapsis.websockets.Connection,
	 * byte, byte, byte[], int, int)
	 */
	@Override
	public boolean onFrame(Connection connection, byte flags, byte opcode, byte[] data, int offset, int length)
	{
		events.add(new Event("onFrame", connection, flags, opcode, data, offset, length));
		return super.onFrame(connection, flags, opcode, data, offset, length);
	}

	/**
	 * Internal class representing service events.<br>
	 * Instances of Event will be added to {@link MockService#events} on every call of "onX(..)"
	 * methods.
	 * 
	 * @author ultimate
	 */
	public class Event
	{
		/**
		 * The name of the event or method
		 */
		private String		name;
		/**
		 * The connection this event was for
		 */
		private Connection	connection;
		/**
		 * The list of addionally passed args
		 */
		private Object[]	args;

		/**
		 * Contruct a new event for tracking purposes.
		 * 
		 * @param name - The name of the event or method
		 * @param connection - The connection this event was for
		 * @param args - The list of addionally passed args
		 */
		public Event(String name, Connection connection, Object... args)
		{
			super();
			this.name = name;
			this.connection = connection;
			this.args = args;
		}

		/**
		 * The name of the event or method
		 * 
		 * @return name
		 */
		public String getName()
		{
			return name;
		}

		/**
		 * The connection this event was for
		 * 
		 * @return connection
		 */
		public Connection getConnection()
		{
			return connection;
		}

		/**
		 * The list of addionally passed args
		 * 
		 * @return args
		 */
		public Object[] getArgs()
		{
			return args;
		}
	}
}
