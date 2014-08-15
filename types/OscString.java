package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-string value. */
public class OscString extends OscValue {
	/**
	 * Build a new OscString from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the string position.
	 */
	public OscString(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		get();
	}

	/**
	 * Returns the string value.
	 * 
	 * @return a string value.
	 */
	public String get() {
		return OscValue.getString(mPacket, mPos);
	}
}
