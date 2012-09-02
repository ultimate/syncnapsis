package com.syncnapsis.websockets;

/**
 * The WebSocketProtocol interface used to get information about the connection or messages.
 * 
 * @author ultimate
 */
public interface Protocol
{
	/**
	 * "Upgrade"
	 */
	public static final String	HEADER_UPGRADE					= "Upgrade";
	/**
	 * "Upgrade: websocket"
	 */
	public static final String	HEADER_UPGRADE_WEBSOCKET		= "websocket";
	/**
	 * "Connection"
	 */
	public static final String	HEADER_CONNECTION				= "Connection";
	/**
	 * "Connection: Upgrade"
	 */
	public static final String	HEADER_CONNECTION_UPGRADE		= "Upgrade";
	/**
	 * "Host"
	 */
	public static final String	HEADER_HOST						= "Host";
	/**
	 * "Origin"
	 */
	public static final String	HEADER_ORIGIN					= "Origin";
	/**
	 * "Sec-WebSocket-Key"
	 */
	public static final String	HEADER_SEC_WEBSOCKET_KEY		= "Sec-WebSocket-Key";
	/**
	 * "Sec-WebSocket-Version"
	 */
	public static final String	HEADER_SEC_WEBSOCKET_VERSION	= "Sec-WebSocket-Version";
	/**
	 * "Sec-WebSocket-Protocol"
	 */
	public static final String	HEADER_SEC_WEBSOCKET_PROTOCOL	= "Sec-WebSocket-Protocol";
	/**
	 * "Sec-WebSocket-Extensions"
	 */
	public static final String	HEADER_SEC_WEBSOCKET_EXTENSIONS	= "Sec-WebSocket-Extensions";
	/**
	 * "Sec-WebSocket-Accept"
	 */
	public static final String	HEADER_SEC_WEBSOCKET_ACCEPT		= "Sec-WebSocket-Accept";

	/**
	 * As of RFC6455
	 */
	public static final int CLOSE_CODE_NORMAL = 1000;
	/**
	 * As of RFC6455
	 */
	public static final int CLOSE_CODE_GOING_AWAY = 1001;
	/**
	 * As of RFC6455
	 */
	public static final int CLOSE_CODE_PROTOCOL_ERROR = 1002;
	/**
	 * As of RFC6455
	 */
	public static final int CLOSE_CODE_UNACCEPTABLE_MESSAGE = 1003;
//	/**
//	 * As of RFC6455
//	 */
//	public static final int CLOSE_CODE_RESERVED = 1004;
	/**
	 * As of RFC6455
	 */
	public static final int CLOSE_CODE_NONE = 1005;
	/**
	 * As of RFC6455
	 */
	public static final int CLOSE_CODE_ABNORMAL = 1006;
	/**
	 * As of RFC6455
	 */
	public static final int CLOSE_CODE_INCONSISTENT_DATA = 1007;
	/**
	 * As of RFC6455
	 */
	public static final int CLOSE_CODE_POLICY_VIOLATION = 1008;
	/**
	 * As of RFC6455
	 */
	public static final int CLOSE_CODE_TOO_LARGE_MESSAGE = 1009;
	/**
	 * As of RFC6455
	 */
	public static final int CLOSE_CODE_EXTENSIONS_NOT_NEGOTIATED = 1010;
	/**
	 * As of RFC6455
	 */
	public static final int CLOSE_CODE_UNEXPECTED_CONDITION = 1011;
	/**
	 * As of RFC6455
	 */
	public static final int CLOSE_CODE_HANDSHAKE_FAILURE = 1015;

	/**
	 * Check the given opCode for a text frame
	 * 
	 * @param opCode - the opCode
	 * @return true if the opCode is for a text frame
	 */
	public boolean isText(byte opCode);

	/**
	 * Check the given opCode for a binary frame
	 * 
	 * @param opCode - the opCode
	 * @return true if the opCode is for a binary frame
	 */
	public boolean isBinary(byte opCode);

	/**
	 * Check the given opCode for a control frame
	 * 
	 * @param opCode - the opCode
	 * @return true if the opCode is for a control frame
	 */
	public boolean isControl(byte opCode);

	/**
	 * Check the given opCode for a continuation frame
	 * 
	 * @param opCode - the opCode
	 * @return true if the opCode is for a continuation frame
	 */
	public boolean isContinuation(byte opCode);

	/**
	 * Check the given opCode for a close control
	 * 
	 * @param opCode - the opCode
	 * @return true if the opCode is a close control
	 */
	public boolean isClose(byte opCode);

	/**
	 * Check the given opCode for ping control
	 * 
	 * @param opCode - the opCode
	 * @return true if the opCode is a ping control
	 */
	public boolean isPing(byte opCode);

	/**
	 * Check the given opCode for pong control
	 * 
	 * @param opCode - the opCode
	 * @return true if the opCode is a pong control
	 */
	public boolean isPong(byte opCode);

	/**
	 * The opCode for a text message
	 * 
	 * @return the opCode
	 */
	public byte textOpCode();

	/**
	 * The opCode for a binary message
	 * 
	 * @return the opCode
	 */
	public byte binaryOpCode();

	/**
	 * The opCode for a continuation frame
	 * 
	 * @return the opCode
	 */
	public byte continuationOpCode();

	/**
	 * The opCode for a close frame
	 * 
	 * @return the opCode
	 */
	public byte closeOpCode();

	/**
	 * The opCode for a ping frame
	 * 
	 * @return the opCode
	 */
	public byte pingOpCode();

	/**
	 * The opCode for a pong frame
	 * 
	 * @return the opCode
	 */
	public byte pongOpCode();

	/**
	 * The mask for the FIN bit
	 * 
	 * @return mask
	 */
	public byte finMask();

	/**
	 * Check if a message is complete
	 * 
	 * @param flags - the flags
	 * @return true or false
	 */
	public boolean isMessageComplete(byte flags);
}
