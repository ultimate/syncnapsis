package com.syncnapsis.websockets;

import org.springframework.util.Assert;

/**
 * Simple class for wrapping a binary or text message into a single object.
 * 
 * @author ultimate
 */
public class Message
{
	/**
	 * The data as a String if type is text message
	 */
	private String	dataString;
	/**
	 * The data array
	 */
	private byte[]	data;
	/**
	 * The offset for the data in the array
	 */
	private int		offset;
	/**
	 * The length of the message data
	 */
	private int		length;
	/**
	 * The opCode of the message?
	 */
	private byte	opCode;
	/**
	 * The close code, if this message is a close control frame
	 */
	private int		closeCode	= -1;

	/**
	 * Construct a new message with a String
	 * 
	 * @param message - the text message content
	 * @param protocol - the protocol used
	 */
	public Message(String message, Protocol protocol)
	{
		Assert.notNull(protocol, "protocol must not be null!");
		this.dataString = message;
		this.data = message != null ? message.getBytes() : new byte[0];
		this.offset = 0;
		this.length = data.length;
		this.opCode = protocol.textOpCode();
	}

	/**
	 * Construct a new message with a String
	 * 
	 * @param message - the text message content
	 * @param protocol - the protocol used
	 */
	public Message(String message, byte opCode)
	{
		this.dataString = message;
		this.data = message != null ? message.getBytes() : new byte[0];
		this.offset = 0;
		this.length = data.length;
		this.opCode = opCode;
	}

	/**
	 * Construct a new message with a binary Array
	 * 
	 * @param data - the message data
	 * @param offset - an offset
	 * @param length - the message length
	 * @param protocol - the protocol used
	 */
	public Message(byte[] data, int offset, int length, Protocol protocol)
	{
		Assert.notNull(protocol, "protocol must not be null!");
		this.dataString = null;
		this.data = data != null ? data : new byte[0];
		this.offset = offset;
		this.length = length;
		this.opCode = protocol.binaryOpCode();
	}

	/**
	 * Construct a new message with a binary Array and a given control code
	 * 
	 * @param data - the message data
	 * @param offset - an offset
	 * @param length - the message length
	 * @param opCode - the opCode
	 */
	public Message(byte[] data, int offset, int length, byte opCode)
	{
		this.dataString = null;
		this.data = data != null ? data : new byte[0];
		this.offset = offset;
		this.length = length;
		this.opCode = opCode;
	}

	/**
	 * The data array
	 * 
	 * @return data
	 */
	public byte[] getData()
	{
		return data;
	}

	/**
	 * The data as a String if type is text message
	 * 
	 * @return dataString
	 */
	public String getDataString()
	{
		return dataString;
	}

	/**
	 * The offset for the data in the array
	 * 
	 * @return offset
	 */
	public int getOffset()
	{
		return offset;
	}

	/**
	 * The length of the message data
	 * 
	 * @return length
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * The opCode of this message
	 * 
	 * @return opCode
	 */
	public byte getOpCode()
	{
		return opCode;
	}

	/**
	 * The close code, if this message is a close control frame
	 * 
	 * @param closeCode - the closeCode
	 */
	public void setCloseCode(int closeCode)
	{
		this.closeCode = closeCode;
	}

	/**
	 * The close code, if this message is a close control frame
	 * 
	 * @return clsoeCode
	 */
	public int getCloseCode()
	{
		return closeCode;
	}
}
