package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-Address-Pattern. */
public class OscAddress extends OscValue {

	/**
	 * Build a new OscAddress from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the address pattern.
	 * @param aPos
	 *            an address pattern string position.
	 */
	public OscAddress(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		get();
	}

	/**
	 * Returns the address pattern string.
	 * 
	 * @return a string
	 */
	public String get() {
		return OscValue.getString(mPacket, mPos);
	}
}