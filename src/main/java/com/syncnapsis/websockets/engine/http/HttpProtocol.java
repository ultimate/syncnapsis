package com.syncnapsis.websockets.engine.http;

import com.syncnapsis.websockets.engine.BaseProtocol;

/**
 * Implementation of the WebSocketProtocol Interface for the HTTP fallback via HttpWebSocketServlet
 * 
 * @author ultimate
 */
public class HttpProtocol extends BaseProtocol
{
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#isMessageComplete(byte)
	 */
	@Override
	public boolean isMessageComplete(byte flags)
	{
		// no message fragmenting
		return true;
	}
}
