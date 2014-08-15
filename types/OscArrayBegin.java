package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-Array-Begin. */
public class OscArrayBegin extends OscValue {
	/**
	 * Build a new OscArrayBegin from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            an ArrayBegin tag position.
	 */
	public OscArrayBegin(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
	}
}