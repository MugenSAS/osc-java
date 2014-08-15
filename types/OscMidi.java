package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-Midi message. */
public class OscMidi extends OscValue {
	/**
	 * Build a new OscMidi from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the MIDI message position.
	 */
	public OscMidi(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		mPacket.getInt();
	}

	/**
	 * Returns the MIDI message value.
	 * 
	 * @return an int value.
	 */
	public int get() {
		return mPacket.getInt(mPos);
	}
}
