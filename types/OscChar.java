package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-char. */
public class OscChar extends OscValue {
	/**
	 * Build a new OscChar from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the char value position.
	 */
	public OscChar(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		mPacket.getInt();
	}

	/**
	 * Returns the char value.
	 * 
	 * @return a char value.
	 */
	public char get() {
		return (char) mPacket.getInt(mPos);
	}
}