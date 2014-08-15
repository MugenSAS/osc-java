package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-Impulse tag. (OSC-1.1) */
public class OscImpulse extends OscValue {
	/**
	 * Build a new OscImpulse from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the Impulse tag position.
	 */
	public OscImpulse(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
	}
}
