package com.osc;

import java.nio.ByteBuffer;

import com.osc.exception.OscBadDataTypeRequestException;
import com.osc.exception.OscMalformedBundleException;
import com.osc.exception.OscMalformedMessageException;

/**
 * OSC data reader class
 * 
 * This class allows to decode a buffer as an OSC message or bundle following
 * the OSC 1.1 specification available at <a href=
 * "http://cnmat.berkeley.edu/publication/features_and_future_open_sound_control_version_1_1_nime"
 * >NIME 2009 paper</a> At instance, the OSC message or bundle is loaded.
 * <p>
 * Developer can then navigate to the messages structure using a set of provided
 * methods through the inner classes OscMessage, OscBundle, and classes
 * derivating from class OscValue.
 * </p>
 */
public class OscReader {

	private OscContent mContent = null;
	private ByteBuffer mPacket = null;

	/**
	 * Build a new OscPackerReader object based on the passed byte buffer.
	 * 
	 * @param src
	 *            the byte buffer to parse containing OSC messages
	 * @throws OscMalformedBundleException
	 *             The bundle cannot be read properly.
	 * @throws OscMalformedMessageException
	 *             A contained message cannot be read properly.
	 */
	public OscReader(byte[] src/* , OscVersion aVersion */) throws OscMalformedBundleException,
			OscMalformedMessageException {
		mPacket = ByteBuffer.wrap(src);
		// if (aVersion == OscVersion.OSC_10)
		// mPacket.getInt();
		if (mPacket.get(mPacket.position()) == '#')
			mContent = new OscBundle(mPacket, src.length);
		else
			mContent = new OscMessage(mPacket, src.length);
	}

	/**
	 * Conversion type for current OscContent object as an OscBundle object.
	 * 
	 * @return this as an OscBundle object. If this cannot be convert to an
	 *         OscBundle object, returns null.
	 * @throws OscBadDataTypeRequestException
	 */
	public OscBundle getBundle() throws OscBadDataTypeRequestException {
		if (mContent instanceof OscBundle)
			return (OscBundle) mContent;
		throw new OscBadDataTypeRequestException(null);
	}

	/**
	 * Conversion type for current OscContent object as an OscMessage object.
	 * 
	 * @return this as an OscMessage object. If this cannot be convert to an
	 *         OscMessage object, returns null.
	 */
	public OscMessage getMessage() throws OscBadDataTypeRequestException {
		if (mContent instanceof OscMessage)
			return (OscMessage) mContent;
		throw new OscBadDataTypeRequestException(null);
	}
}
