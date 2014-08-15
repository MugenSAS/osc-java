package com.osc;

import java.nio.ByteBuffer;

/**
 * Abstract class to manage packet embedded content as objects.
 */
public abstract class OscContent {

	/** Position of the data in the buffer. */
	protected int mDataIdx = 0;
	/** Data buffer containing the OSC content. */
	protected ByteBuffer mPacket = null;
	/** Starting position of this content in the whole buffer. */
	protected int mStartIdx = 0;

	/** Build the current OscContent object. */
	protected OscContent(ByteBuffer packet) {
		mPacket = packet;
		mStartIdx = mPacket.position();
		mDataIdx = 0;
	}
	
	/**
	 * Returns the current time tag as a 64-bit integer value
	 * 
	 * @return the time tag as a long value.
	 */
	public abstract long getTimeTag();
}