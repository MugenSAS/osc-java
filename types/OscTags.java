package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing the type tags string of this message. */
public class OscTags extends OscValue {
	/**
	 * Build a new OscTags from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the message type tags string position.
	 */
	public OscTags(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		get();
	}

	/**
	 * Returns the OSC type tags associated to this message.
	 * 
	 * @return a string value.
	 */
	public String get() {
		return OscValue.getString(mPacket, mPos);
	}
}
