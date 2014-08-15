package com.osc;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.osc.exception.OscBadDataTypeRequestException;
import com.osc.exception.OscMalformedBundleException;
import com.osc.exception.OscMalformedMessageException;

/**
 * Class managing packet data as an OSC bundle object.
 * <p>
 * An OSC bundle object is composed of one or several OSC messages, or bundles.
 * The contained OscMessage and OscBundle object are stored in a list freely
 * accessible to navigate to the current bundle.
 * </p>
 */
public class OscBundle extends OscContent {

	/**
	 * List of all messages or bundles directly accessible from this bundle.
	 */
	private List<OscContent> mContentList = new ArrayList<OscContent>();

	/**
	 * Build a new OscBundle object.
	 * 
	 * @param aPacket
	 *            the data buffer containing the current message
	 * @param aSize
	 *            The size of the buffer containing the bundle to load.
	 * @throws OscMalformedBundleException
	 *             The bundle cannot be read properly.
	 * @throws OscMalformedMessageException
	 *             A contained message cannot be read properly.
	 */
	OscBundle(ByteBuffer aPacket, int aSize) throws OscMalformedBundleException, OscMalformedMessageException {
		super(aPacket);
		if (mPacket.get() != '#' || mPacket.get() != 'b' || mPacket.get() != 'u' || mPacket.get() != 'n'
				|| mPacket.get() != 'd' || mPacket.get() != 'l' || mPacket.get() != 'e' || mPacket.get() != '\0')
			throw new OscMalformedBundleException("bad bundle address pattern");

		// Move Packet cursor after timeTag
		mPacket.getLong();

		while (mPacket.position() < mStartIdx + aSize) {
			int contentSize = mPacket.getInt();
			if (mPacket.get(mPacket.position()) == '#')
				mContentList.add(new OscBundle(mPacket, contentSize));
			else
				mContentList.add(new OscMessage(mPacket, contentSize));
		}
	}

	/**
	 * Conversion type for current OscContent object as an OscBundle object.
	 * 
	 * @return this as an OscBundle object. If this cannot be convert to an
	 *         OscBundle object, returns null.
	 * @throws OscBadDataTypeRequestException
	 */
	public OscBundle getBundle(int index) throws OscBadDataTypeRequestException {
		if (mContentList.get(index) instanceof OscBundle)
			return (OscBundle) mContentList.get(index);
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Conversion type for current OscContent object as an OscMessage object.
	 * 
	 * @return this as an OscMessage object. If this cannot be convert to an
	 *         OscMessage object, returns null.
	 * @throws OscBadDataTypeRequestException
	 */
	public OscMessage getMessage(int index) throws OscBadDataTypeRequestException {
		if (mContentList.get(index) instanceof OscMessage)
			return (OscMessage) mContentList.get(index);
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Gets the number of messages or bundles embedded in this bundle.
	 */
	public int getNum() {
		return mContentList.size();
	}


	/* (non-Javadoc)
	 * @see com.osc.OscContent#getTimeTag()
	 */
	public long getTimeTag() {
		return mPacket.getLong(mStartIdx + 8);
	}
}