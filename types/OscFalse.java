package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-FALSE tag. */
public class OscFalse extends OscValue {
	/**
	 * Build a new OscTrue from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the TRUE tag position.
	 */
	public OscFalse(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
	}
}
