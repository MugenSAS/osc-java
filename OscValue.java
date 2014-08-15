package com.osc;

import java.nio.ByteBuffer;

import com.osc.exception.OscBadDataTypeRequestException;
import com.osc.types.*;

/**
 * Abstract class representing an OSC value.
 * 
 * This class is derivated to manage all OSC common types.
 */
public class OscValue {

	/**
	 * Returns the position of the next non-null character in the packet.
	 * 
	 * @return the position.
	 */
	private static int getLastStringIdx(ByteBuffer packet) {
		// only check string for last 4th byte
		int pos = packet.position();
		while (packet.get(pos) != 0)
			pos += 1;
		return pos;
	}

	/**
	 * Returns the string located at the passed position in the packet buffer.
	 * 
	 * @param aPacket
	 *            the data buffer where to get the string.
	 * @param aPos
	 *            The position were the string starts
	 * @return The read string.
	 */
	public static String getString(ByteBuffer aPacket, int aPos) {
		aPacket.position(aPos);
		int end = getLastStringIdx(aPacket);
		int alignedEnd = (end + 4) & ~0x03;
		byte[] bytes = new byte[end - aPos];
		aPacket.get(bytes, 0, end - aPos);
		aPacket.position(alignedEnd);
		return new String(bytes);
	}

	/** The associated data buffer. */
	protected ByteBuffer mPacket;

	/** Position of the data in the data buffer. */
	protected int mPos;

	/**
	 * Constructor.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the position of the value in the buffer.
	 */
	protected OscValue(ByteBuffer aPacket, int aPos) {
		mPacket = aPacket;
		mPos = aPos;
	}

	/**
	 * Read this value as an OSC-Address.
	 * 
	 * @return the address pattern string.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public String getAddress() throws OscBadDataTypeRequestException {
		if (this instanceof OscAddress)
			return ((OscAddress) this).get();
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as an OSC-Blob value.
	 * 
	 * @return the byte array associated to this value.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public byte[] getBlob() throws OscBadDataTypeRequestException {
		if (this instanceof OscBlob)
			return ((OscBlob) this).get();
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as a boolean value.
	 * 
	 * @return true if value is OSC-True, and false if value is OSC-False.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public boolean getBool() throws OscBadDataTypeRequestException {
		if (this instanceof OscTrue)
			return true;
		if (this instanceof OscFalse)
			return false;
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as a char value.
	 * 
	 * @return the char value.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public char getChar() throws OscBadDataTypeRequestException {
		if (this instanceof OscChar)
			return ((OscChar) this).get();
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as a double value.
	 * 
	 * @return the double value.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public double getDouble() throws OscBadDataTypeRequestException {
		if (this instanceof OscDouble)
			return ((OscDouble) this).get();
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as a float value.
	 * 
	 * @return the float value.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public float getFloat() throws OscBadDataTypeRequestException {
		if (this instanceof OscFloat)
			return ((OscFloat) this).get();
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as an int32 value.
	 * 
	 * @return the int32 value.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public int getInt32() throws OscBadDataTypeRequestException {
		if (this instanceof OscInt32)
			return ((OscInt32) this).get();
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as an int64 value.
	 * 
	 * @return the int64 value.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public long getInt64() throws OscBadDataTypeRequestException {
		if (this instanceof OscInt64)
			return ((OscInt64) this).get();
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as a Midi Message value.
	 * 
	 * @return the int value representing the midi message.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public int getMidiMessage() throws OscBadDataTypeRequestException {
		if (this instanceof OscMidi)
			return ((OscMidi) this).get();
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as an OSC-RGBA value.
	 * 
	 * @return the int value representing the OSC-RGBA message.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public int getRGBA() throws OscBadDataTypeRequestException {
		if (this instanceof OscRGBA)
			return ((OscRGBA) this).get();
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as a string.
	 * 
	 * @return the string.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public String getString() throws OscBadDataTypeRequestException {
		if (this instanceof OscString)
			return ((OscString) this).get();
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as an OSC-Symbol.
	 * 
	 * @return the string representing the OSC-Symbol value.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public String getSymbol() throws OscBadDataTypeRequestException {
		if (this instanceof OscSymbol)
			return ((OscSymbol) this).get();
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as an OSC-Tags.
	 * 
	 * @return the string value representing the OSC-Tags values.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public String getTags() throws OscBadDataTypeRequestException {
		if (this instanceof OscTimeTag)
			return ((OscTags) this).get();
		throw new OscBadDataTypeRequestException(null);
	}
	
	/**
	 * Read this value as an OSC-TimeTag.
	 * 
	 * @return the long value representing the OSC-TimeTag value.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public long getTimeTag() throws OscBadDataTypeRequestException {
		if (this instanceof OscTimeTag)
			return ((OscTimeTag) this).get();
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as an OSC-ArrayBegin tag.
	 * 
	 * @return true if value is an array begin tag.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	boolean isArrayBegin() throws OscBadDataTypeRequestException {
		if (this instanceof OscArrayBegin)
			return true;
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as an OSC-ArrayEnd tag.
	 * 
	 * @return true if value is an array end tag.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	boolean isArrayEnd() throws OscBadDataTypeRequestException {
		if (this instanceof OscArrayEnd)
			return true;
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as an OSC-False tag.
	 * 
	 * @return true if value OSC-False.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public boolean isFalse() throws OscBadDataTypeRequestException {
		if (this instanceof OscFalse)
			return true;
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as an OSC-Infinitum tag. (OSC 1.0)
	 * 
	 * @return true if value is an OSC-Infinitum tag.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public boolean isInfinitum() throws OscBadDataTypeRequestException {
		if (this instanceof OscInfinitum)
			return true;
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as an OSC-Impulse tag. (OSC 1.1)
	 * 
	 * @return true if value is an OSC-Infinitum tag.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public boolean isImpulse() throws OscBadDataTypeRequestException {
		if (this instanceof OscImpulse)
			return true;
		throw new OscBadDataTypeRequestException(null);
	}
	
	/**
	 * Read this value as an OSC-Nil tag.
	 * 
	 * @return true if value is an OSC-Nil tag.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public boolean isNil() throws OscBadDataTypeRequestException {
		if (this instanceof OscNil)
			return true;
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Read this value as an OSC-True tag.
	 * 
	 * @return true if value is an OSC-True tag.
	 * @throws OscBadDataTypeRequestException
	 *             if the current object is not the expected type.
	 */
	public boolean isTrue() throws OscBadDataTypeRequestException {
		if (this instanceof OscTrue)
			return true;
		throw new OscBadDataTypeRequestException(null);
	}
}