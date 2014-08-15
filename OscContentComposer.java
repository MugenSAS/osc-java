package com.osc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Abstract class for OSC message or bundle composition.
 * 
 * This class stores message information, and utility methods for OSC message or
 * bundle completion.
 */
abstract class OscContentComposer {

	/**
	 * Complete passed ByteBuffer to the next 4-bytes alignment.
	 * 
	 * @param dst
	 *            The ByteBuffer to complete
	 */
	protected static void fillAlignment(ByteBuffer dst) {
		final byte[] mblank = { 0, 0, 0, 0 };
		int size = 4 - (dst.position() & 0x3);
		if (size != 4) {
			checkBufferSize(dst, size);
			dst.put(mblank, 0, size);
		}
	}

	protected static ByteBuffer checkBufferSize(ByteBuffer aBuffer, int aSize) {
		if (aBuffer.position() + aSize > aBuffer.capacity()) {
			int newDataBufferSize = aBuffer->capacity() + (int) Math.pow(2, Math.ceil(Math.log((double) (aSize)) / Math.log(2)));
			ByteBuffer newDataByteBuffer = ByteBuffer.allocateDirect(newDataBufferSize);
			if (aBuffer.position() > 0)
				newDataByteBuffer.put(aBuffer.array(), 0, aBuffer.position());
			return newDataByteBuffer;
		} else
			return aBuffer;
	}

	/** ByteBuffer used for convenient navigation in byte array. */
	protected ByteBuffer mHeaderByteBuffer = null;

	/**
	 * Build a new Composer. By default, bytes are written in BIG_ENDIAN style.
	 */
	protected OscContentComposer() {
		mHeaderByteBuffer = ByteBuffer.allocate(128);
		mHeaderByteBuffer.order(ByteOrder.BIG_ENDIAN);
	}

	/**
	 * Returns the size of the current OscComposer.
	 * 
	 * @return the current message size
	 */
	protected int computeSize() {
		return mHeaderByteBuffer.position();
	}

	/**
	 * Copy current message buffers to passed ByteBuffer
	 * 
	 * @param aByteBuffer
	 *            The ByteBuffer where to copy current data.
	 */
	protected void fillByteBuffer(ByteBuffer aByteBuffer) {
		aByteBuffer.put(mHeaderByteBuffer.array(), 0, mHeaderByteBuffer.position());
	}

	/**
	 * Gets the complete OSC content (depends on OSC version).
	 * 
	 * @return the current data buffer.
	 */
	public abstract byte[] getBytes(OscVersion aVersion);
}
