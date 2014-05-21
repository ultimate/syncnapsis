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
package com.syncnapsis.websockets.service.rpc;

import java.util.Map;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.utils.serialization.BaseSerializer;

public class GenericRPCHandlerTest extends LoggerTestCase
{
	private final BaseSerializer<String> serializer = new BaseSerializer<String>() {
		// formatter:off
		@Override
		public String serialize(Object prepared) {return null;}
		@Override
		public Map<String, Object> deserialize(String serialization) {return null;}
		// formatter:on
	};

	public void testNothing() throws Exception
	{
		GenericRPCHandler rpcHandler = new GenericRPCHandler() {
			@Override
			public Object getTarget(String objectName)
			{
				return null;
			}
		};
		rpcHandler.setSerializer(serializer);
		
		fail("no test yet");
	}
}
