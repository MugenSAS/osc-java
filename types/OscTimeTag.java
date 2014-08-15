package com.osc.types;

import java.nio.ByteBuffer;

import com.osc.OscValue;

/** Class representing an OSC-TimeTag value. */
public class OscTimeTag extends OscValue {
	/**
	 * Build a new OscTimeTag from the given location in packet.
	 * 
	 * @param aPacket
	 *            the data buffer where to read the value.
	 * @param aPos
	 *            the time tag value position.
	 */
	public OscTimeTag(ByteBuffer aPacket, int aPos) {
		super(aPacket, aPos);
		mPacket.getLong();
	}

	/**
	 * Returns the OSC TimeTag value.
	 * 
	 * @return a long value.
	 */
	public long get() {
		return mPacket.getLong(mPos);
	}

	public static long getImmediateTime() {
		return 1;
	}

	public static long getLargestTimeTag() {
		return Long.MAX_VALUE;
	}

	public static long getCurrentTime() {
		/** baseline NTP time if bit-0=0 -> 7-Feb-2036 @ 06:28:16 UTC */
		final long msb0baseTime = 2085978496000L;

		/** baseline NTP time if bit-0=1 -> 1-Jan-1900 @ 01:00:00 UTC */
		final long msb1baseTime = -2208988800000L;

		long t = System.currentTimeMillis();
		boolean useBase1 = t < msb0baseTime; // time < Feb-2036
		long baseTime;
		if (useBase1) {
			baseTime = t - msb1baseTime; // dates <= Feb-2036
		} else {
			// if base0 needed for dates >= Feb-2036
			baseTime = t - msb0baseTime;
		}

		long seconds = baseTime / 1000;
		long fraction = ((baseTime % 1000) * 0x100000000L) / 1000;

		if (useBase1) {
			seconds |= 0x80000000L; // set high-order bit if msb1baseTime 1900
									// used
		}

		long time = seconds << 32 | fraction;
		return time;

	}
}
