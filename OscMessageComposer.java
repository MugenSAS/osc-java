package com.osc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * OSC message composer class
 * 
 * This class allows to compose an OSC message following the OSC 1.1
 * specification available at <a href=
 * "http://cnmat.berkeley.edu/publication/features_and_future_open_sound_control_version_1_1_nime"
 * >NIME 2009 paper</a>
 * <ul>
 * <li>Some methods are provided to push different data type values.
 * <li>Some methods allows to get back the composed message and its size.
 * </ul>
 */
public class OscMessageComposer extends OscContentComposer {

	private int mArrayLevel = 0;
	private ByteBuffer mDataByteBuffer = null;

	/**
	 * Build a new OSC message
	 * 
	 * @param address
	 *            The address pattern to set to the message.
	 */
	public OscMessageComposer(String address) {
		super();

		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, address.length() + 1);
		mHeaderByteBuffer.put(address.getBytes());
		mHeaderByteBuffer.put((byte) 0);
		fillAlignment(mHeaderByteBuffer);
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) ',');

		mDataByteBuffer = ByteBuffer.allocate(1024);
		mDataByteBuffer.order(ByteOrder.BIG_ENDIAN); // Already default in Java
	}

	/**
	 * @see com.osc.OscContentComposer#computeSize()
	 */
	@Override
	protected int computeSize() {
		return super.computeSize() + mDataByteBuffer.position();
	}

	/**
	 * @see com.osc.OscContentComposer#fillByteBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected void fillByteBuffer(ByteBuffer aByteBuffer) {
		super.fillByteBuffer(aByteBuffer);
		aByteBuffer.put(mDataByteBuffer.array(), 0, mDataByteBuffer.position());
	}

	/**
	 * Gets the complete OSC message for the current message being composed.
	 * 
	 * @return the current OscMessage data.
	 */
	@Override
	public byte[] getBytes(OscVersion aVersion) {
		// Be sure to end all opened arrays
		while (mArrayLevel != 0) {
			pushArrayEnd();
			mArrayLevel--;
		}

		// Be sure to align type tags ending in header
		if (mHeaderByteBuffer.get(mHeaderByteBuffer.position() - 1) != 0) {
			mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
			mHeaderByteBuffer.put((byte) 0);
		}
			
		fillAlignment(mHeaderByteBuffer);

		int size = computeSize();

		byte[] data = null;
		int offset = 0;
		if (aVersion == OscVersion.OSC_10) {
			offset = 4;
			data = new byte[size + 4];
			ByteBuffer.wrap(data).putInt(size - 4);
		} else {
			data = new byte[size];
		}

		System.arraycopy(mHeaderByteBuffer.array(), 0, data, offset, mHeaderByteBuffer.position());
		System.arraycopy(mDataByteBuffer.array(), 0, data, offset + mHeaderByteBuffer.position(),
				mDataByteBuffer.position());
		return data;
	}

	/**
	 * Indicates the beginning of an array in the OscMessage being composed.
	 */
	public void pushArrayBegin() {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) '[');
		mArrayLevel++;
	}

	/**
	 * Indicates the end of an array in the OscMessage being composed.
	 * 
	 * Does nothing if no array has been started.
	 */
	public void pushArrayEnd() {
		if (mArrayLevel > 0) {
			mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
			mHeaderByteBuffer.put((byte) ']');
			mArrayLevel--;
		}
	}

	/**
	 * Pushes the whole passed bytes array into this message.
	 * 
	 * @param src
	 *            The array from which bytes are to be read
	 */
	public void pushBlob(byte[] src) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'b');
		mDataByteBuffer = checkBufferSize(mDataByteBuffer, src.length);
		mDataByteBuffer.putInt(src.length);
		mDataByteBuffer.put(src);
		fillAlignment(mDataByteBuffer);
	}

	/**
	 * Pushes the whole, or part of, bytes array into this message.
	 * 
	 * @param src
	 *            The array from which bytes are to be read
	 * @param offset
	 *            The offset within the array of the first byte to be read; must
	 *            be non-negative and no larger than array.length
	 * @param length
	 *            The number of bytes to be read from the given array; must be
	 *            non-negative and no larger than array.length - offset
	 */
	public void pushBlob(byte[] src, int offset, int length) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'b');
		mDataByteBuffer = checkBufferSize(mDataByteBuffer, length);
		mDataByteBuffer.putInt(length);
		mDataByteBuffer.put(src, offset, length);
		fillAlignment(mDataByteBuffer);
	}

	/**
	 * Appends a <i>True</i> or <i>False</i> flag to OscMessage being composed,
	 * respectively to the passed boolean value.
	 * 
	 * @param b
	 *            The boolean value to add to the message
	 */
	public void pushBool(boolean b) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) (b ? 'T' : 'F'));
	}

	/**
	 * Appends a <i>ASCII character</i> value to OscMessage being composed.
	 * 
	 * @param c
	 *            The ASCII character value to add to the message.
	 */
	public void pushChar(char c) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'c');
		mDataByteBuffer = checkBufferSize(mDataByteBuffer, 4);
		mDataByteBuffer.putInt(c);
	}

	/**
	 * Appends a <i>double</i> value to OscMessage being composed.
	 * 
	 * @param d
	 *            The double value to add to the message.
	 */
	public void pushDouble(double d) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'd');
		mDataByteBuffer = checkBufferSize(mDataByteBuffer, 8);
		mDataByteBuffer.putDouble(d);
	}

	/**
	 * Appends a <i>False</i> flag to OscMessage being composed.
	 */
	public void pushFalse() {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'T');
	}

	/**
	 * Appends a <i>32-bit float</i> value to OscMessage being composed.
	 * 
	 * @param f
	 *            The float value to add to the message.
	 */
	public void pushFloat(float f) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'f');
		mDataByteBuffer = checkBufferSize(mDataByteBuffer, 4);
		mDataByteBuffer.putFloat(f);
	}

	/**
	 * (OSC 1.0) Appends an <i>Infinitum</i> flag to OscMessage being composed.
	 */
	public void pushInfinitum() {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'I');
	}

	/**
	 * (OSC-1.1) Appends an <i>Impulse</i> flag to OscMessage being composed.
	 */
	public void pushImpulse() {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'I');
	}

	/**
	 * Appends a <i>32-bit integer</i> value to OscMessage being composed.
	 * 
	 * @param i
	 *            The int32 value to add to the message.
	 */
	public void pushInt32(int i) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'i');
		mDataByteBuffer = checkBufferSize(mDataByteBuffer, 4);
		mDataByteBuffer.putInt(i);
	}

	/**
	 * Appends a <i>64-bit integer</i> value to OscMessage being composed.
	 * 
	 * @param l
	 *            The long value to add to the message.
	 */
	public void pushInt64(long l) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'h');
		mDataByteBuffer = checkBufferSize(mDataByteBuffer, 8);
		mDataByteBuffer.putLong(l);
	}

	/**
	 * Appends a <i>4-byte MIDI message</i> value to OscMessage being composed.
	 * 
	 * @param m
	 *            The MIDI message value to add to the message.
	 */
	public void pushMidiMessage(int m) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'm');
		mDataByteBuffer = checkBufferSize(mDataByteBuffer, 4);
		mDataByteBuffer.putInt(m);
	}

	/**
	 * Appends an <i>Nil</i> flag to OscMessage being composed.
	 */
	public void pushNil() {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'N');
	}

	/**
	 * Appends a <i>32-bit RGBA color</i> value to OscMessage being composed.
	 * 
	 * @param r
	 *            The RGBA color value to add to the message.
	 */
	public void pushRGBA(int r) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'r');
		mDataByteBuffer = checkBufferSize(mDataByteBuffer, 4);
		mDataByteBuffer.putInt(r);
	}

	/**
	 * Appends a <i>String</i> to OscMessage being composed.
	 * 
	 * @param s
	 *            The string to add to the message.
	 */
	public void pushString(String s) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 's');
		mDataByteBuffer = checkBufferSize(mDataByteBuffer, s.length());
		mDataByteBuffer.put(s.getBytes());
		mDataByteBuffer.put((byte) '\0');
		fillAlignment(mDataByteBuffer);
	}

	/**
	 * Appends a <i>Symbol</i> to OscMessage being composed.
	 * 
	 * @param S
	 *            The symbol to add to the message.
	 */
	public void pushSymbol(String S) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'S');
		mDataByteBuffer = checkBufferSize(mDataByteBuffer, S.length());
		mDataByteBuffer.put(S.getBytes());
		mDataByteBuffer.put((byte) '\0');
		fillAlignment(mDataByteBuffer);
	}

	/**
	 * Appends a <i>Tiem Tag</i> to OscMessage being composed.
	 * 
	 * @param t
	 *            The time tag value as a long value to add to the message.
	 */
	public void pushTimeTag(long t) {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 't');
		mDataByteBuffer = checkBufferSize(mDataByteBuffer, 8);
		mDataByteBuffer.putLong(t);
	}

	/**
	 * Appends a <i>True</i> flag to OscMessage being composed.
	 */
	public void pushTrue() {
		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 1);
		mHeaderByteBuffer.put((byte) 'T');
	}
}
