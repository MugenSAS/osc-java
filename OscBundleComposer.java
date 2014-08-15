package com.osc;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * OSC bundle composer class
 * 
 * This class allows to compose an OSC bundle following the OSC 1.1
 * specification available at <a href=
 * "http://cnmat.berkeley.edu/publication/features_and_future_open_sound_control_version_1_1_nime"
 * >NIME 2009 paper</a>
 * <ul>
 * <li>Some methods are provided to push OSC messages or other OSC bundles to
 * the this OSC bundle.
 * <li>Some methods allows to get back the composed bundle and its size.
 * </ul>
 */
public class OscBundleComposer extends OscContentComposer {

	/** List of all messages or bundles to compose to build the whole Bundle. */
	private ArrayList<OscContentComposer> mContentList = new ArrayList<OscContentComposer>();

	/**
	 * Build a new OSC Bundle.
	 * 
	 * @param timetag
	 *            The long value that applies as a time tag to this bundle.
	 */
	public OscBundleComposer(long timetag) {
		super();

		mHeaderByteBuffer = checkBufferSize(mHeaderByteBuffer, 16);
		mHeaderByteBuffer.put("#bundle\0".getBytes());
		mHeaderByteBuffer.putLong(timetag);
	}

	/**
	 * @see com.osc.OscContentComposer#fillByteBuffer(java.nio.ByteBuffer)
	 */
	@Override
	protected void fillByteBuffer(ByteBuffer aByteBuffer) {
		super.fillByteBuffer(aByteBuffer);

		// Then, loop over all the contents to copy data in the buffer
		for (int i = 0; i < mContentList.size(); i++) {
			// Write content size
			aByteBuffer.putInt(mContentList.get(i).computeSize());
			mContentList.get(i).fillByteBuffer(aByteBuffer);
		}
	}

	/**
	 * Gets the complete OSC bundle for the current bundle being composed.
	 * 
	 * @return the current OscMessage data.
	 */
	@Override
	public byte[] getBytes(OscVersion aVersion) {

		int size = computeSize();

		// Allocate the bundle buffer
		byte[] data = null;
		if (aVersion == OscVersion.OSC_10)
			data = new byte[size + 4];
		else
			data = new byte[size];

		// First, copy this bundle header
		ByteBuffer writeBufferHelper = ByteBuffer.wrap(data);
		if (aVersion == OscVersion.OSC_10)
			writeBufferHelper.putInt(0, size);
		fillByteBuffer(writeBufferHelper);

		return data;
	}

	/**
	 * @see com.osc.OscContentComposer#computeSize()
	 */
	@Override
	protected int computeSize() {

		int size = super.computeSize();

		// Compute whole bundle size
		for (int i = 0; i < mContentList.size(); i++) {
			size += 4; // reserve space to write this content size
			size += mContentList.get(i).computeSize(); // get this content size
		}
		return size;
	}

	/**
	 * Appends a new message to the current bundle.
	 * 
	 * @param timeTag
	 *            The time tag to apply to the new bundle.
	 * @return the new bundle to compose.
	 */
	public OscBundleComposer pushBundle(long timeTag) {
		OscBundleComposer bundle = new OscBundleComposer(timeTag);
		mContentList.add(bundle);
		return bundle;
	}

	/**
	 * Appends a previously built bundle to the current bundle being composed.
	 * 
	 * @param aBundle
	 *            The bundle to add to this bundle.
	 */
	public void pushBundle(OscBundleComposer aBundle) {
		mContentList.add(aBundle);
	}

	/**
	 * Appends a previously built message to the current bundle being composed.
	 * 
	 * @param message
	 *            The message to add to this bundle.
	 */
	public void pushMessage(OscMessageComposer message) {
		mContentList.add(message);
	}

	/**
	 * Appends a new message to the current bundle.
	 * 
	 * @param address
	 * @return the new message to compose.
	 */
	public OscMessageComposer pushMessage(String address) {
		OscMessageComposer message = new OscMessageComposer(address);
		mContentList.add(message);
		return message;
	}
}
