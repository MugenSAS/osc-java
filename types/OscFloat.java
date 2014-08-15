package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-Float value. */
public class OscFloat extends OscValue {
	/**
	 * Build a new OscFloat from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the float value position.
	 */
	public OscFloat(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		mPacket.getFloat();
	}

	/**
	 * Returns the float value.
	 * 
	 * @return a float value.
	 */
	public float get() {
		return mPacket.getFloat(mPos);
	}
}
