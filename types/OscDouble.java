package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-double. */
public class OscDouble extends OscValue {
	/**
	 * Build a new OscDouble from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the double value position.
	 */
	public OscDouble(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		mPacket.getDouble();
	}

	/**
	 * Returns the double value.
	 * 
	 * @return a double value.
	 */
	public double get() {
		return mPacket.getDouble(mPos);
	}
}