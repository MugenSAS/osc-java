package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-Nil tag. */
public class OscNil extends OscValue {
	/**
	 * Build a new OscNil from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the Nil tag position.
	 */
	public OscNil(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
	}
}
