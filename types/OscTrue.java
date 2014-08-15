package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-TRUE tag. */
public class OscTrue extends OscValue {
	/**
	 * Build a new OscTrue from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the TRUE tag position.
	 */
	public OscTrue(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
	}
}
