package com.osc;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.osc.exception.OscMalformedMessageException;
import com.osc.types.*;

/**
 * Class managing packet data as an OSC message object.
 * <p>
 * An OSC message object is composed of:
 * <ul>
 * <li>An address pattern string (aka OscAddress)
 * <li>A type tags string (aka OscTags)
 * <li>A list of values (aka OscValue)
 * <li>...
 * </ul>
 * </p>
 */
public class OscMessage extends OscContent {

	private OscTimeTag mTimeTag = null;

	/** List of values embedded in the current OscMessage. */
	private List<OscValue> mValues = new ArrayList<OscValue>();

	/**
	 * Build a new OscMessage object. While building the message structure, the
	 * packet buffer is parsed, and new OscValue objects are created and
	 * registered in a list, in order to be accessible later.
	 * 
	 * @param aPacket
	 *            the data buffer containing the current message
	 * @param aSize
	 *            The size of the buffer containing the bundle to load.
	 * @throws OscMalformedMessageException
	 *             A contained message cannot be read properly.
	 */
	OscMessage(ByteBuffer aPacket, int aSize) throws OscMalformedMessageException {
		super(aPacket);
		int arrayLevel = 0;
		try {
			mValues.add( new OscAddress(mPacket, mStartIdx) );

			// Current packet position is set to data section.
			int dataIdx = mPacket.position();
			int tagsIdx = dataIdx;
			do {
				if (dataIdx > mStartIdx + aSize)
					throw new BufferUnderflowException();

				byte tag = mPacket.get(tagsIdx);
				dataIdx = mPacket.position();
				OscValue v = null;
				switch (tag) {
				case 0: // null tag... exit loop...
					break;
				case ',':
					v = new OscTags(mPacket, dataIdx);
					break;
				case 'T':
					v = new OscTrue(mPacket, dataIdx);
					break;
				case 'F':
					v = new OscFalse(mPacket, dataIdx);
					break;
				case 'N':
					v = new OscNil(mPacket, dataIdx);
					break;
				case 'I':
					// TODO if (OscVersion == 1.1)
					// v = new OscImpulse(mPacket, dataIdx);
					// else
					v = new OscInfinitum(mPacket, dataIdx);
					break;
				case '[':
					v = new OscArrayBegin(mPacket, dataIdx);
					++arrayLevel;
					break;
				case ']':
					v = new OscArrayEnd(mPacket, dataIdx);
					--arrayLevel;
					break;
				case 'i':
					v = new OscInt32(mPacket, dataIdx);
					break;
				case 'f':
					v = new OscFloat(mPacket, dataIdx);
					break;
				case 'c':
					v = new OscChar(mPacket, dataIdx);
					break;
				case 'r':
					v = new OscRGBA(mPacket, dataIdx);
					break;
				case 'm':
					v = new OscMidi(mPacket, dataIdx);
					break;
				case 'h':
					v = new OscInt64(mPacket, dataIdx);
					break;
				case 't':
					v = mTimeTag = new OscTimeTag(mPacket, dataIdx);
					break;
				case 'd':
					v = new OscDouble(mPacket, dataIdx);
					break;
				case 's':
					v = new OscString(mPacket, dataIdx);
					break;
				case 'S':
					v = new OscSymbol(mPacket, dataIdx);
					break;
				case 'b':
					v = new OscBlob(mPacket, dataIdx);
					break;
				default:
					throw new OscMalformedMessageException("unknown type tag");
				}
				if (v != null)
					mValues.add(v); // push this Value to the Value list
			} while (mPacket.get(tagsIdx++) != 0);
		} catch (BufferUnderflowException e) {
			throw new OscMalformedMessageException("trying to read data beyond packet size limit");
		} catch (IndexOutOfBoundsException e) {
			throw new OscMalformedMessageException("trying to read data beyond packet size limit");
		}
		if (arrayLevel != 0)
			throw new OscMalformedMessageException(
					"array was not terminated before end of message (expected ']' end of array tag)");
	}

	/**
	 * Gets the number of registered values in this messages.
	 * 
	 * The number of values is equal to the number of OSC tags + 1. The extra
	 * value is the address pattern of the message that is always located at
	 * first position in the list (index 0).
	 * 
	 * @return the number of OSC values
	 */
	public int getNumValues() {
		return mValues.size();
	}

	/* (non-Javadoc)
	 * @see com.osc.OscContent#getTimeTag()
	 */
	public long getTimeTag() {
		if (mTimeTag != null)
			return mTimeTag.get();
		else
			return OscTimeTag.getImmediateTime();
	}

	/**
	 * Gets the i-th value in the list of values for this message.
	 * 
	 * @param index
	 *            The requested value.
	 * @return the value located at the passed index.
	 */
	public OscValue getValue(int index) {
		return mValues.get(index);
	}
}