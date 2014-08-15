package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-symbol value. */
public class OscSymbol extends OscValue {
	/**
	 * Build a new OscSymbol from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the Symbol string value position.
	 */
	public OscSymbol(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		get();
	}

	/**
	 * Returns the Symbol value.
	 * 
	 * @return a string value.
	 */
	public String get() {
		return OscValue.getString(mPacket, mPos);
	}
}
