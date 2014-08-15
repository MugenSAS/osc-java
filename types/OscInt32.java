package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-int32. */
public class OscInt32 extends OscValue {
	/**
	 * Build a new OscInt32 from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the int32 value position.
	 */
	public OscInt32(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		mPacket.getInt();
	}

	/**
	 * Returns the int32 value.
	 * 
	 * @return an int value.
	 */
	public int get() {
		return mPacket.getInt(mPos);
	}
}