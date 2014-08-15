package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-int64 value. */
public class OscInt64 extends OscValue {
	/**
	 * Build a new OscInt64 from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the int64 value position.
	 */
	public OscInt64(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		mPacket.getLong();
	}

	/**
	 * Returns the int64 value.
	 * 
	 * @return a long value.
	 */
	public long get() {
		return mPacket.getLong(mPos);
	}
}
