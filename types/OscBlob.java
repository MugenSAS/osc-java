package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-Blob. */
public class OscBlob extends OscValue {
	/**
	 * Build a new OscBlob from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the Blob byte array position.
	 */
	public OscBlob(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		get();
	}

	/**
	 * Returns the blob data.
	 * 
	 * @return a blob data.
	 */
	public byte[] get() {
		int blobSize = mPacket.getInt(mPos);
		byte[] bytes = new byte[blobSize];
		mPacket.get(bytes, 0, blobSize);
		return bytes;
	}
}