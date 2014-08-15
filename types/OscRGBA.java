package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-RGBA-color value. */
public class OscRGBA extends OscValue {
	/**
	 * Build a new OscRGBA from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the RGBA color value position.
	 */
	public OscRGBA(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		mPacket.getInt();
	}

	/**
	 * Returns the RGBA color value.
	 * 
	 * @return an int value.
	 */
	public int get() {
		return mPacket.getInt(mPos);
	}
}
