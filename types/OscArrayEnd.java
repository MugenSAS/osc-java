package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-Array-End. */
public class OscArrayEnd extends OscValue {
	/**
	 * Build a new OscArrayEnd from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            an ArrayEnd tag position.
	 */
	public OscArrayEnd(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
	}
}