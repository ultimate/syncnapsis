package com.syncnapsis.websockets.engine;

import com.syncnapsis.websockets.Protocol;

/**
 * Base-Implementation for own Protocol-Implementations like HttpProtocol
 * 
 * @author ultimate
 */
public class BaseProtocol implements Protocol
{

	/**
	 * OpCode for a continuation frame as of RFC 6455
	 */
	public static final byte	OPCODE_CONTINUATION	= 0x0;
	/**
	 * OpCode for a text frame as of RFC 6455
	 */
	public static final byte	OPCODE_TEXT			= 0x1;
	/**
	 * OpCode for a binary frame as of RFC 6455
	 */
	public static final byte	OPCODE_BINARY		= 0x2;
	/**
	 * OpCode for a close frame as of RFC 6455
	 */
	public static final byte	OPCODE_CLOSE		= 0x8;
	/**
	 * OpCode for a ping frame as of RFC 6455
	 */
	public static final byte	OPCODE_PING			= 0x9;
	/**
	 * OpCode for a pong frame as of RFC 6455
	 */
	public static final byte	OPCODE_PONG			= 0xA;
	/**
	 * The Mask identifying the fin bit in the ws-frame
	 */
	public static final byte	FIN_MASK			= 0x8;

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#isText(byte)
	 */
	@Override
	public boolean isText(byte opcode)
	{
		return opcode == OPCODE_TEXT;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#isBinary(byte)
	 */
	@Override
	public boolean isBinary(byte opcode)
	{
		return opcode == OPCODE_BINARY;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#isControl(byte)
	 */
	@Override
	public boolean isControl(byte opcode)
	{
		return opcode > OPCODE_CLOSE;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#isContinuation(byte)
	 */
	@Override
	public boolean isContinuation(byte opcode)
	{
		return opcode == OPCODE_CONTINUATION;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#isClose(byte)
	 */
	@Override
	public boolean isClose(byte opcode)
	{
		return opcode == OPCODE_CLOSE;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#isPing(byte)
	 */
	@Override
	public boolean isPing(byte opcode)
	{
		return opcode == OPCODE_PING;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#isPong(byte)
	 */
	@Override
	public boolean isPong(byte opcode)
	{
		return opcode == OPCODE_PONG;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#textOpCode()
	 */
	@Override
	public byte textOpCode()
	{
		return OPCODE_TEXT;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.WebSocketProtocol#binaryOpCode()
	 */
	@Override
	public byte binaryOpCode()
	{
		return OPCODE_BINARY;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#continuationOpCode()
	 */
	@Override
	public byte continuationOpCode()
	{
		return OPCODE_CONTINUATION;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#closeOpCode()
	 */
	@Override
	public byte closeOpCode()
	{
		return OPCODE_CLOSE;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#pingOpCode()
	 */
	@Override
	public byte pingOpCode()
	{
		return OPCODE_PING;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#pongOpCode()
	 */
	@Override
	public byte pongOpCode()
	{
		return OPCODE_PONG;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#finMask()
	 */
	@Override
	public byte finMask()
	{
		return FIN_MASK;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.Protocol#isMessageComplete(byte)
	 */
	@Override
	public boolean isMessageComplete(byte flags)
	{
		return (flags&FIN_MASK)!=0;
	}
}
