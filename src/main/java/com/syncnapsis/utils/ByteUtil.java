package com.syncnapsis.utils;

/**
 * Util to convert primitive types to byte-Arrays and vice versa.<br>
 * <br>
 * Following Code taken from
 * <a href=
 * "http://www.daniweb.com/software-development/java/code/216874/primitive-types-as-byte-arrays"
 * >http://www.daniweb.com/software-development/java/code/216874/primitive-types-as-byte-arrays</a>
 * and slightly modified
 * 
 * @author ultimate
 */
public abstract class ByteUtil
{
	/**
	 * Convert int to byte array
	 * 
	 * @param data - the int value
	 * @return the byte array
	 */
	public static byte[] toByteArray(int data)
	{
		// @formatter:off
		return new byte[] {
			(byte) ((data >> 24) & 0xff), 
			(byte) ((data >> 16) & 0xff), 
			(byte) ((data >>  8) & 0xff), 
			(byte) ((data >>  0) & 0xff)
		};
		// @formatter:on
	}

	/**
	 * Convert long to byte array
	 * 
	 * @param data - the long value
	 * @return the byte array
	 */
	public static byte[] toByteArray(long data)
	{
		// @formatter:off
		return new byte[] {
			(byte) ((data >> 56) & 0xff),
			(byte) ((data >> 48) & 0xff), 
			(byte) ((data >> 40) & 0xff), 
			(byte) ((data >> 32) & 0xff),
			(byte) ((data >> 24) & 0xff), 
			(byte) ((data >> 16) & 0xff), 
			(byte) ((data >>  8) & 0xff), 
			(byte) ((data >>  0) & 0xff)
		};
		// @formatter:on
	}

	/**
	 * Convert float to byte array
	 * 
	 * @param data - the float value
	 * @return the byte array
	 */
	public static byte[] toByteArray(float data)
	{
		return toByteArray(Float.floatToRawIntBits(data));
	}

	/**
	 * Convert double to byte array
	 * 
	 * @param data - the double value
	 * @return the byte array
	 */
	public static byte[] toByteArray(double data)
	{
		return toByteArray(Double.doubleToRawLongBits(data));
	}

	/**
	 * Convert boolean to byte array
	 * 
	 * @param data - the boolean value
	 * @return the byte array
	 */
	public static byte[] toByteArray(boolean data)
	{
		return new byte[] { (byte) (data ? 0x01 : 0x00) };
	}

	/**
	 * Convert byte array to int
	 * 
	 * @param data - the byte array
	 * @return the int value
	 */
	public static int toInt(byte[] data)
	{
		if(data == null || data.length != 4)
			return 0x0;
		// @formatter:off
		return (int) (
			(0xff & data[0]) << 24 | 
			(0xff & data[1]) << 16 | 
			(0xff & data[2]) <<  8 | 
			(0xff & data[3]) <<  0
		);
		// @formatter:on
	}

	/**
	 * Convert byte array to long
	 * 
	 * @param data - the byte array
	 * @return the long value
	 */
	public static long toLong(byte[] data)
	{
		if(data == null || data.length != 8)
			return 0x0;
		// (Below) convert to longs before shift because digits are lost with ints beyond the 32-bit
		// limit
		// @formatter:off
		return (long) (
			(long) (0xff & data[0]) << 56 | 
			(long) (0xff & data[1]) << 48 | 
			(long) (0xff & data[2]) << 40 | 
			(long) (0xff & data[3]) << 32 | 
			(long) (0xff & data[4]) << 24 | 
			(long) (0xff & data[5]) << 16 | 
			(long) (0xff & data[6]) <<  8 | 
			(long) (0xff & data[7]) <<  0
		);
		// @formatter:on
	}

	/**
	 * Convert byte array to float
	 * 
	 * @param data - the byte array
	 * @return the float value
	 */
	public static float toFloat(byte[] data)
	{
		if(data == null || data.length != 4)
			return 0x0;
		return Float.intBitsToFloat(toInt(data));
	}

	/**
	 * Convert byte array to double
	 * 
	 * @param data - the byte array
	 * @return the double value
	 */
	public static double toDouble(byte[] data)
	{
		if(data == null || data.length != 8)
			return 0x0;
		return Double.longBitsToDouble(toLong(data));
	}

	/**
	 * Convert byte array to boolean
	 * 
	 * @param data - the byte array
	 * @return the boolean value
	 */
	public static boolean toBoolean(byte[] data)
	{
		return (data == null || data.length == 0) ? false : data[0] != 0x00;
	}
}
