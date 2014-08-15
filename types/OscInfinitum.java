package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-Infinitum tag. */
public class OscInfinitum extends OscValue {
	/**
	 * Build a new OscInfinitum from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the Infinitum tag position.
	 */
	public OscInfinitum(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
	}
}
